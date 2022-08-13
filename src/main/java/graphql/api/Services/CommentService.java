package graphql.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import graphql.api.DTOs.CommentDTOInput;
import graphql.api.DTOs.EditCommentDTOInput;
import graphql.api.Entities.Comments;
import graphql.api.Entities.User;
import graphql.api.Repositories.CommentRepository;
import graphql.api.Repositories.UserRepository;

@Service
public class CommentService {
  
  @Autowired 
  private CommentRepository commentRepository;

  @Autowired
  private UserRepository userRepository;
  
  public Comments saveComment(CommentDTOInput commentDTO){
    User loggedUser = userRepository.findByEmail(commentDTO.getEmail());
    Comments userComment = new Comments();
    userComment.setComment(commentDTO.getComment());
    userComment.setEvent(commentDTO.getEvent());
    userComment.setVideoId(commentDTO.getVideoId());
    userComment.setUser(loggedUser);
    return commentRepository.save(userComment);
  }

  public List<Comments> comments(){
    List <Comments>comments = commentRepository.findAll();
    return comments;
  }

  public Comments editComment(EditCommentDTOInput editCommentDTOInput) throws NotFoundException{
    Comments existingComment = commentRepository.findById(editCommentDTOInput.getId()).orElseThrow(()->new NotFoundException());
    existingComment.setComment(editCommentDTOInput.getComment());
    return commentRepository.save(existingComment);
  }

  public String deleteComment(Long id){
    commentRepository.deleteById(id);
    return "Your comment was deleted successfully!!!";
  }

  public List<Comments> commentsByEvent(String event) {
    return commentRepository.findByEvent(event);
  }

  public List<Comments> commentsByVideoId(String videoId) {
  return commentRepository.findByVideoId(videoId);
  }
}
