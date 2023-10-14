package io.github.shuoros.ecommercy.security.jwt;

import io.github.shuoros.ecommercy.config.ApplicationConfiguration;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTProvider implements Serializable {

    private final static String AUTHORITIES_KEY = "auth";

    private final SecretKey secretKey;

    private final JwtParser jwtParser;

    private final Long simpleTokenValidity;

    private final Long rememberMeTokenValidity;

    public JWTProvider(final ApplicationConfiguration applicationConfiguration) {
        final ApplicationConfiguration.Security securityConfiguration = applicationConfiguration.getSecurity();

        final byte[] secretKeyByteArray = extractSecretKeyByteArray(securityConfiguration);
        secretKey = Keys.hmacShaKeyFor(secretKeyByteArray);
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
        simpleTokenValidity = 1000 * securityConfiguration.getSimpleTokenValidity();
        rememberMeTokenValidity = 1000 * securityConfiguration.getRememberMeTokenValidity();
    }

    private static byte[] extractSecretKeyByteArray(ApplicationConfiguration.Security securityConfiguration) {
        final byte[] keyBytes;
        if (!ObjectUtils.isEmpty(securityConfiguration.getSecretKey())) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(securityConfiguration.getSecretKey());
        } else {
            log.warn("The JWT key used is not Base64-encoded. ");
            keyBytes = securityConfiguration.getSecretKey().getBytes(StandardCharsets.UTF_8);
        }
        return keyBytes;
    }

    public String generateToken(final Authentication authentication, boolean rememberMe) {
        final String authorities = getAuthoritiesString(authentication);

        final Date now = new Date(System.currentTimeMillis());
        final Date expiration = resolveTokenExpirationDate(now, rememberMe);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token) {
        final Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        final User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Boolean validateToken(final String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.trace("Incoming JWT is invalid!", e);
        }

        return false;
    }

    private Date resolveTokenExpirationDate(final Date now, final boolean rememberMe) {
        if(rememberMe) {
            return new Date(now.getTime() + this.rememberMeTokenValidity);
        }
        return new Date(now.getTime() + this.simpleTokenValidity);
    }

    private String getAuthoritiesString(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
