package be.mathiasbosman.blog.domain;

public class UserAccountMother {

  public static UserAccount user() {
    return user("foo", "bar");
  }

  public static UserAccount user(String username, String password) {
    return UserAccount.builder()
        .username(username)
        .password(password)
        .build();
  }
}
