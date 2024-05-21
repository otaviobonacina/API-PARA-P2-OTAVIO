package application.config;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import application.service.AppUserDetailsService;
import application.service.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        
        String token = getToken(request);

        System.out.println(token);

        if(token != null) {
            String subject = tokenService.getSubject(token);
            UserDetails usuario = userDetailsService.loadUserByUsername(subject);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}