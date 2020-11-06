package com.example.movielingo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("authorization");
        if(header == null ||header.startsWith("Bearer "))
        {
            throw new ServerException(("Wrong or empty header"));
        }
        else
        {

            try {
               // String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey(MyConstants.TOKEN_SIGN_KEY).parseClaimsJws(header).getBody();
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
