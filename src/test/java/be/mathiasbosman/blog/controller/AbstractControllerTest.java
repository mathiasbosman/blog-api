package be.mathiasbosman.blog.controller;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public abstract class AbstractControllerTest extends AbstractSpringBootTest {

  @Autowired
  protected MockMvc mvc;

}
