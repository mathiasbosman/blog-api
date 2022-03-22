package be.mathiasbosman.blog.controller;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import be.mathiasbosman.blog.security.TestSecurityHelper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public abstract class AbstractControllerTest extends AbstractSpringBootTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected TestSecurityHelper securityHelper;

  @AfterEach
  void tearDown() {
    securityHelper.clearContext();
  }
}
