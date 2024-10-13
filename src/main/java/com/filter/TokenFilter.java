package com.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class TokenFilter implements Filter {
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    HttpServletRequest req = (HttpServletRequest)request;
    String url = req.getRequestURL().toString();

    System.out.println("TokenFilter");

    if(url.contains("/public/")){
      System.out.println("PUBLIC API");
      chain.doFilter(request, response);
    } else {
      String token = req.getHeader("authToken");
      if(token == null){

      }
      System.out.println("PRIVATE API ==> " + token);
      chain.doFilter(request, response);
    }
  }
}
