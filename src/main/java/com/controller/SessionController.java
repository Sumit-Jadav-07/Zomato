package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/session")
public class SessionController {

  @GetMapping("/log/")
  public String getMethodName(@RequestParam String param) {
      return new String();
  }
  
}
