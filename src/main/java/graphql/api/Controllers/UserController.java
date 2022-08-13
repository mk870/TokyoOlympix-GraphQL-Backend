package graphql.api.Controllers;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import graphql.api.DTOs.LoginDTOInput;
import graphql.api.DTOs.LoginResponseDTOInput;
import graphql.api.DTOs.UserDTOInput;
import graphql.api.Entities.User;
import graphql.api.Services.UserService;

@Controller
@CrossOrigin
public class UserController {
  @Autowired
  private UserService userService;

  @MutationMapping
  public String saveUser(@Argument UserDTOInput userDTO) throws MessagingException {
    return userService.saveUser(userDTO);
  }
  
  @MutationMapping
  public LoginResponseDTOInput userLogin (@Argument LoginDTOInput loginDTO){
    return userService.login(loginDTO);
  }

  @QueryMapping
  @PreAuthorize("isAuthenticated()")
  public List<User> users(){
    return userService.getUsers();
  }
  
  @MutationMapping
  public LoginResponseDTOInput verifyRegistration (@Argument String token){
    return userService.validateToken(token);
  }
  
}
