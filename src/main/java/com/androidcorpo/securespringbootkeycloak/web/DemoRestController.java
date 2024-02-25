package com.androidcorpo.securespringbootkeycloak.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@RestController
public class DemoRestController {

  @GetMapping
  public String securedRoute(){
    return "secure route that need authentication";
  }
}
