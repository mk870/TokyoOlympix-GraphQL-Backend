package graphql.api.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import graphql.api.Entities.Comments;

public interface CommentRepository extends JpaRepository<Comments,Long>{
  Optional<Comments> findById(Long id);
  List<Comments> findByEvent(String event);
  List<Comments> findByVideoId(String videoId);
}
