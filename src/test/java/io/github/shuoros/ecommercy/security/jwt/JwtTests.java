package io.github.shuoros.ecommercy.security.jwt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTests {

    @Autowired
    private Jwt jwt;

    private static Authentication authentication;
    private static final String username = "user";
    private static final String password = "password";
    private static final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

    @BeforeAll
    public static void setUp() {
        authentication = new UsernamePasswordAuthenticationToken(//
                username,//
                password,//
                authorities);
    }

    @Test
    void jwtMustGenerateToken() {
        // Act
        final String token = jwt.generateToken(authentication);

        // Assert
        assertNotNull(token);
    }

    @Test
    void generatedTokenMustHasExpirationDateAfterTokenValidity() {
        // Arrange
        final String token = jwt.generateToken(authentication);

        // Act
        final Date expirationDate = jwt.getExpirationDateFromToken(token);

        // Assert
        assertTrue(expirationDate.after(new Date()));
        assertTrue(expirationDate.before(new Date(System.currentTimeMillis() + jwt.TOKEN_VALIDITY * 1000)));
    }

    @Test
    void generatedTokenMustHasCredibilityAfterCreation() {
        // Arrange
        final String token = jwt.generateToken(authentication);

        // Act & Assert
        assertFalse(jwt.isTokenExpired(token));
    }

    @Test
    void jwtMustExtractCorrectUsernameFromToken() {
        // Arrange
        final String token = jwt.generateToken(authentication);

        // Act
        final String extractedUsername = jwt.getUsernameFromToken(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    void jwtMustValidateATokenWithCorrectCredentials() {
        // Arrange
        final String token = jwt.generateToken(authentication);
        final UserDetails userDetails = new User(username, password,
                authorities);

        // Act
        final Boolean isValid = jwt.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void jwtMustValidateATokenWithInCorrectCredentials() {
        // Arrange
        final String token = jwt.generateToken(authentication);
        final UserDetails userDetails = new User("admin", "drowssap",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        // Act
        final Boolean isValid = jwt.validateToken(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

}
