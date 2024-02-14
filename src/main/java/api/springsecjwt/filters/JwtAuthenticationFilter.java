package api.springsecjwt.filters;

import api.springsecjwt.services.JwtService;
import api.springsecjwt.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
private final UserService userService;

    @Autowired
private JwtService jwtService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        if(StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authorizationHeader.substring(7);
        log.debug("jwt: {}", jwt);

        email = jwtService.extractUserName(jwt);

        if(!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
            log.debug("Email: {}", email);
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
            if (jwtService.isTokenValid(jwt, userDetails)) {

                log.debug("UserDetails: {}", userDetails);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

filterChain.doFilter(request,response);

    }
}
