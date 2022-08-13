package graphql.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import graphql.api.Entities.UnLikes;

public interface UnLikesRepository extends JpaRepository<UnLikes,Long>{
  List<UnLikes> findByEvent(String event);
  List<UnLikes> findByVideoId(String videoId);
}
