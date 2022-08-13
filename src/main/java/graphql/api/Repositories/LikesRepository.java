package graphql.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import graphql.api.Entities.Likes;

public interface LikesRepository extends JpaRepository<Likes,Long>{
  List<Likes> findByEvent(String event);
  List<Likes> findByVideoId(String videoId);
}
