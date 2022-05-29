package io.github.shuoros.ecommercy.security.filter;

import io.github.shuoros.ecommercy.dao.Address;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.repository.AddressRepository;
import io.github.shuoros.ecommercy.dao.repository.UserRepository;
import io.github.shuoros.ecommercy.exception.PayloadException;
import io.github.shuoros.ecommercy.security.jwt.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class RestDataSecurityFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    private final List<String> datasToSecure = List.of("user");
    private final Jwt jwtTokenUtil;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public RestDataSecurityFilter(Jwt jwtTokenUtil, UserRepository userRepository, AddressRepository addressRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String request = req.getRequestURI();
        String[] path = request.split("/");
        if (path.length > 2) {
            String header = req.getHeader(HEADER_STRING);
            if (header != null) {
                String authToken = header.replace(TOKEN_PREFIX, "");
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                if (request.startsWith("/user")) {
                    User user = userRepository.findByEmail(username).get();
                    if (!request.split("/")[2].equals(user.getId()))
                        throw new PayloadException("You are not allowed to access this resource!"//
                                , HttpStatus.FORBIDDEN);
                } else if (request.startsWith("/address")) {
                    Address address = addressRepository.findById(request.split("/")[2]).get();
                    if (!address.getUser().getEmail().equals(username))
                        throw new PayloadException("You are not allowed to access this resource!"//
                                , HttpStatus.FORBIDDEN);
                }
            }
        }
        chain.doFilter(req, res);
    }

}
