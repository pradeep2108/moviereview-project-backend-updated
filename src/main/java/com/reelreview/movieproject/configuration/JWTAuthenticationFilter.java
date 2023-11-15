package com.reelreview.movieproject.configuration;

import com.reelreview.movieproject.service.UserDataService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private UserDataService userDataService;

    private JWTTokenHelper jwtTokenHelper;
    public JWTAuthenticationFilter(UserDataService userDataService, JWTTokenHelper jwtTokenHelper) {
        this.userDataService = userDataService;
        this.jwtTokenHelper  = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authToken = jwtTokenHelper.getToken(request);
        if(null != authToken){
            String userName = jwtTokenHelper.getUsernameFromToken(authToken);

            if(null != userName){
                UserDetails userDetails = userDataService.loadUserByUsername(userName);
                if(jwtTokenHelper.validateToken(authToken,userDetails )){
                    UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userName,
                            null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
    filterChain.doFilter(request,response);
    }
}
