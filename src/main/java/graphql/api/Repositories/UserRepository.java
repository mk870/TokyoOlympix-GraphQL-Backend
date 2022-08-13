package graphql.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import graphql.api.Entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
  User findByEmail(String email);
}
