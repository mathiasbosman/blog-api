package be.mathiasbosman.blog.domain;

class BlogUserRestTest extends AbstractMvcTest<BlogUser> {

  @Override
  String getCollectionName() {
    return "users";
  }

  @Override
  BlogUser createRandomEntity() {
    return entityManager.persist(BlogUserMother.randomBlogUser());
  }
}
