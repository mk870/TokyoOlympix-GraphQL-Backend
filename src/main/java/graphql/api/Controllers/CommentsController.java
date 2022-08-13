package graphql.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import graphql.api.DTOs.CommentDTOInput;
import graphql.api.DTOs.EditCommentDTOInput;
import graphql.api.Entities.Comments;
import graphql.api.Services.CommentService;


@Controller
@CrossOrigin
public class CommentsController {
  
  @Autowired
  private CommentService commentService;

  @QueryMapping
  public List<Comments> comments(){
    return commentService.comments();
  }
  
  @QueryMapping
  public List<Comments> commentsByEvent(@Argument String event) {
    return commentService.commentsByEvent(event);
  }

  @QueryMapping
  public List<Comments> commentsByVideoId(@Argument String videoId) {
    return commentService.commentsByVideoId(videoId);
  }

  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public Comments editComment(@Argument EditCommentDTOInput editCommentDTOInput,Authentication user) throws NotFoundException{
    return commentService.editComment(editCommentDTOInput);
  }

  
  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public Comments saveComment(@Argument CommentDTOInput commentDTO,Authentication user) {
    return commentService.saveComment(commentDTO);
  }

  @MutationMapping
  @PreAuthorize("isAuthenticated()")
  public String deleteComment(@Argument Long id,Authentication user) {
    return commentService.deleteComment(id);
  }
}
