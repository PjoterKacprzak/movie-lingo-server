package com.example.movielingo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;

public class JwtFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("authorization");
        if(httpServletRequest == null ||!header.startsWith("Bearer "))
        {
            throw new ServerException(("Wrong or empty header"));
        }
        else
        {
            try {
                String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(token).getBody();
                servletRequest.setAttribute("claims", claims);
            }
            catch(Exception e)
            {
                throw new ServerException(("Wrong key"));
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
