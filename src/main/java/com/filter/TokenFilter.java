package com.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.service.CustomerService;
import com.service.RestaurantService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter implements Filter {

  // @Autowired
  // private JWTService jwtService;

  // @Autowired
  // private Services service;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private RestaurantService restaurantService;

  // ----------------------- This method uses a JWT Way -----------------------
  // @Override
  // public void doFilter(ServletRequest request, ServletResponse response,
  // FilterChain chain)
  // throws IOException, ServletException {

  // HttpServletRequest req = (HttpServletRequest) request;
  // String url = req.getRequestURL().toString();

  // if (url.contains("/public/")) {
  // chain.doFilter(request, response);
  // } else if (url.contains("/private/")) {
  // String token = req.getHeader("Authorization");

  // if (token != null && token.startsWith("Bearer ")) {
  // token = token.substring(7);

  // try {
  // // Validate token and extract email and role
  // String email = jwtService.validateTokeAndGetEmail(token);
  // String role = jwtService.validateTokenAndGetRole(token);
  // System.out.println(email);
  // System.out.println(role);

  // // Set the role in Spring Security context
  // Authentication authentication = new UsernamePasswordAuthenticationToken(
  // email, null, List.of(new SimpleGrantedAuthority(role)) // Use List.of()
  // );
  // SecurityContextHolder.getContext().setAuthentication(authentication);

  // chain.doFilter(request, response);
  // } catch (Exception e) {
  // HttpServletResponse res = (HttpServletResponse) response;
  // res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  // res.getWriter().write("Invalid or expired token");
  // }
  // } else {
  // HttpServletResponse res = (HttpServletResponse) response;
  // res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  // res.getWriter().write("Missing or invalid Authorization header");
  // }
  // }
  // }

  // This method uses a normal way
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    String url = req.getRequestURL().toString();

    if (url.contains("/private/")) {
      String token = req.getHeader("Authorization");
      System.out.println("TokenFilter Token --> " + token);
      if (token != null && token.startsWith("Bearer ")) {

        token = token.substring(7);

        String customerEmail = customerService.getEmailByToken(token);
        System.out.println("Token Customer Email " + customerEmail);
        if (customerEmail != null) {
          Integer customerId = customerService.getCustomerIdByEmail(customerEmail);
          System.out.println("Token CustomerId " + customerId);
          if (customerId != null) { // Ensure customerId is also not null
            request.setAttribute("customerId", customerId);
            chain.doFilter(request, response);
            return;
          }
        }

        String restaurantEmail = restaurantService.getEmailByToken(token);
        if (restaurantEmail != null) {
          chain.doFilter(request, response);
          return;
        }

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("Invalid or expired token");
        return;

      } else {
        // Token is missing or invalid; respond with 401
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("Invalid or missing token");
        return;
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}
