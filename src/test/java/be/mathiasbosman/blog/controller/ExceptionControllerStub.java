package be.mathiasbosman.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionControllerStub extends AbstractController {

  @Autowired
  ExceptionServiceStub exceptionServiceStub;

  @GetMapping("/exceptionStub/throw")
  public Throwable throwException() {
    exceptionServiceStub.throwException();
    return null;
  }

}
