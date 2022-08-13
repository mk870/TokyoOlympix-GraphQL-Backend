package graphql.api.Services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphql.api.DTOs.AddLikeDTOInput;
import graphql.api.Entities.Likes;
import graphql.api.Entities.UnLikes;
import graphql.api.Entities.User;
import graphql.api.Repositories.LikesRepository;
import graphql.api.Repositories.UnLikesRepository;
import graphql.api.Repositories.UserRepository;

@Service
public class LikesService {
  @Autowired
  private LikesRepository likesRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UnLikesRepository unLikesRepository;
  
  public Likes addLike(AddLikeDTOInput addLikeDTO){
    User user = userRepository.findByEmail(addLikeDTO.getEmail());
    List<Likes> likes = likesRepository.findByVideoId(addLikeDTO.getVideoId());
    if(checkIfLikeExists(likes,addLikeDTO.getEmail()) != null){
      deleteLike(checkIfLikeExists(likes,addLikeDTO.getEmail()));
      return null;
    }else{
      List<UnLikes> unlikesList = unLikesRepository.findByVideoId(addLikeDTO.getVideoId());
      if(unlikesList != null){
        if(searchUnLikesList(unlikesList,user.getEmail()) != null){
          deleteUnLike(searchUnLikesList(unlikesList,user.getEmail()));
        }
      }
      Likes like = new Likes();
      like.setLikeVideo(addLikeDTO.getLikeVideo());
      like.setEvent(addLikeDTO.getEvent());
      like.setVideoId(addLikeDTO.getVideoId());
      like.setUser(user);
      return likesRepository.save(like);
    }
  }

  public String deleteLike(Long id) {
    likesRepository.deleteById(id);
    return "deleted";
  }
  
  public List<Likes> likesByEvent(String event){
    return likesRepository.findByEvent(event);
  }

  public List<Likes> likesByVideoId(String videoId){
    return likesRepository.findByVideoId(videoId);
  }

  public UnLikes addUnLike(AddLikeDTOInput addLikeDTO){
    User user = userRepository.findByEmail(addLikeDTO.getEmail());
    List<UnLikes> unlikes = unLikesRepository.findByVideoId(addLikeDTO.getVideoId());
    if(checkIfUnLikeExists(unlikes,addLikeDTO.getEmail()) != null){
      deleteUnLike(checkIfUnLikeExists(unlikes,addLikeDTO.getEmail()));
      return null;
    }else{
      List<Likes> likesList = likesRepository.findByVideoId(addLikeDTO.getVideoId());
      if(likesList != null){
        if(searchLikesList(likesList,user.getEmail()) != null){
          deleteLike(searchLikesList(likesList,user.getEmail()));
        }
      }
      UnLikes unlike = new UnLikes();
      unlike.setUnlikeVideo(addLikeDTO.getUnlikeVideo());
      unlike.setEvent(addLikeDTO.getEvent());
      unlike.setVideoId(addLikeDTO.getVideoId());
      unlike.setUser(user);
      return unLikesRepository.save(unlike);
    }
  }

  public String deleteUnLike(Long id) {
    unLikesRepository.deleteById(id);
    return "deleted";
  }

  public List<UnLikes> unlikesByEvent(String event) {
    return unLikesRepository.findByEvent(event);
  }

  public List<UnLikes> unlikesByVideoId(String videoId) {
    return unLikesRepository.findByVideoId(videoId);
  }

  private Long searchUnLikesList (List<UnLikes> myList,String email){
    for (UnLikes unlike : myList) {
      if((unlike.getUser().getEmail()).equals(email)){
        return unlike.getId();
      }
    }
    return null;
  }

  private Long searchLikesList(List<Likes> likesList, String email) {
    for (Likes like : likesList) {
      if((like.getUser().getEmail()).equals(email)){
        return like.getId();
      }
    }
    return null;
  }

  private Long checkIfLikeExists (List<Likes> myLikes, String email){
    if(myLikes != null){
      for(Likes like : myLikes){
        if((like.getUser().getEmail()).equals(email)){
          return like.getId();
        }
      }
      return null;
    }
    return null;
  }

  private Long checkIfUnLikeExists (List<UnLikes> myUnLikes, String email){
    if(myUnLikes != null){
      for(UnLikes unlike : myUnLikes){
        if((unlike.getUser().getEmail()).equals(email)){
          return unlike.getId();
        }
      }
      return null;
    }
    return null;
  }
}
