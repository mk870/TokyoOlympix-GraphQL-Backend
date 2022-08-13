package graphql.api.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import graphql.api.Entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long>{
  VerificationToken findByToken(String token);
}
