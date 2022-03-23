package be.mathiasbosman.blog;

import be.mathiasbosman.blog.security.TestSecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractSpringBootTest {
  @Autowired
  protected TestSecurityHelper securityHelper;
}
