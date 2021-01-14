package com.example.movielingo.configuration;

import io.jsonwebtoken.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("authorization");
        if(header == null )
        {
            throw new ServerException(("Wrong or empty header"));
        }

        else
        {
            if(header.startsWith("Bearer ")){
                header = header.substring(7);
            }
            try {
                // String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey(MyConstants.getTokenSignKey()).parseClaimsJws(header).getBody();
                System.out.println(claims);
                servletRequest.setAttribute("claims", claims);
            }
            catch(SignatureException e)
            {
                e.printStackTrace();
                throw new SignatureException("Wrong key",e);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}