package graphql.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;


import graphql.api.DTOs.AddLikeDTOInput;
import graphql.api.Entities.Likes;
import graphql.api.Entities.UnLikes;
import graphql.api.Services.LikesService;

@Controller
public class LikesController {
  @Autowired
  private LikesService likesService; 

  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public Likes addLike(@Argument AddLikeDTOInput addLikeDTO,Authentication user){
    
    return likesService.addLike(addLikeDTO);
  }
  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public String deleteLike(@Argument Long id) {
    return likesService.deleteLike(id);
  }

  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public UnLikes addUnLike(@Argument AddLikeDTOInput addLikeDTO,Authentication user){
    return likesService.addUnLike(addLikeDTO);
  }
  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public String deleteUnLike(@Argument Long id) {
    return likesService.deleteUnLike(id);
  }

  @QueryMapping
  public List<Likes> likesByEvent(@Argument String event) {
    return likesService.likesByEvent(event);
  }

  @QueryMapping
  public List<Likes> likesByVideoId(@Argument String videoId) {
    return likesService.likesByVideoId(videoId);
  }

  @QueryMapping
  public List<UnLikes> unlikesByEvent(@Argument String event) {
    return likesService.unlikesByEvent(event);
  }

  @QueryMapping
  public List<UnLikes> unlikesByVideoId(@Argument String videoId) {
    return likesService.unlikesByVideoId(videoId);
  }
}
