package io.github.shuoros.ecommercy.security.filter;

import io.github.shuoros.ecommercy.dao.*;
import io.github.shuoros.ecommercy.dao.repository.*;
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
import java.util.Optional;

@Service
public class RestDataSecurityFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    private final Jwt jwtTokenUtil;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public RestDataSecurityFilter(Jwt jwtTokenUtil, UserRepository userRepository, AddressRepository addressRepository,//
                                  BasketRepository basketRepository, BasketItemRepository basketItemRepository,//
                                  CommentRepository commentRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
        this.commentRepository = commentRepository;
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
                if (request.startsWith("/user/")) {
                    Optional<User> user = userRepository.findByEmail(username);
                    if (user.isPresent())
                        if (!request.split("/")[2].equals(user.get().getId()))
                            throw new PayloadException("You are not allowed to access this resource!"//
                                    , HttpStatus.FORBIDDEN);
                } else if (request.startsWith("/address/")) {
                    Optional<Address> address = addressRepository.findById(request.split("/")[2]);
                    if (address.isPresent())
                        if (!address.get().getUser().getEmail().equals(username))
                            throw new PayloadException("You are not allowed to access this resource!"//
                                    , HttpStatus.FORBIDDEN);
                } else if (request.startsWith("/basket/")) {
                    Optional<Basket> basket = basketRepository.findById(request.split("/")[2]);
                    if (basket.isPresent())
                        if (!basket.get().getUser().getEmail().equals(username))
                            throw new PayloadException("You are not allowed to access this resource!"//
                                    , HttpStatus.FORBIDDEN);
                } else if (request.startsWith("/basketItem/")) {
                    Optional<BasketItem> basketItem = basketItemRepository.findById(request.split("/")[2]);
                    if (basketItem.isPresent())
                        if (!basketItem.get().getBasket().getUser().getEmail().equals(username))
                            throw new PayloadException("You are not allowed to access this resource!"//
                                    , HttpStatus.FORBIDDEN);
                } else if (request.startsWith("/comment/")) {
                    Optional<Comment> comment = commentRepository.findById(request.split("/")[2]);
                    if (comment.isPresent())
                        if (!comment.get().getUser().getEmail().equals(username))
                            throw new PayloadException("You are not allowed to access this resource!"//
                                    , HttpStatus.FORBIDDEN);
                }
            }
        }
        chain.doFilter(req, res);
    }

}
