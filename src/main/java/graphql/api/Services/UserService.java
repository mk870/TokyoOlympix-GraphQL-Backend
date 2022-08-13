package graphql.api.Services;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import graphql.api.DTOs.LoginDTOInput;
import graphql.api.DTOs.LoginResponseDTOInput;
import graphql.api.DTOs.UserDTOInput;
import graphql.api.Entities.User;
import graphql.api.Entities.VerificationToken;
import graphql.api.JwtFilter.JwtUtil;
import graphql.api.Repositories.UserRepository;
import graphql.api.Repositories.VerificationTokenRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
    private AuthenticationManager authenticationManager;
  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  EmailService emailService;

  public String saveUser(UserDTOInput userDTO) throws MessagingException {
    User user = new User();
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setEmail(userDTO.getEmail());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setRole("USER");
    user.setEnabled(false);
    User checkingEmail = userRepository.findByEmail(userDTO.getEmail());
    if(checkingEmail != null){
      return "This email already exists";
    }
    User savedUser = userRepository.save(user);
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken(token, user);
    VerificationToken savedVerificationToken = saveVerificationToken(verificationToken);
    String emailStatus = emailService.sendSignUpEmail(savedUser,token);

    if(emailStatus != "ok"){
      verificationTokenRepository.deleteById(savedVerificationToken.getId());
      userRepository.deleteById(savedUser.getId());
      return "Failed to send email please check your network and try again" ;
      
    }
    
    return "Please check your Email for verification" ;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  
  public LoginResponseDTOInput login(LoginDTOInput loginDTO) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
      
    } catch (BadCredentialsException e) {
      
      return new LoginResponseDTOInput(null,"Incorrect Credentials");
    } catch(DisabledException e){
      return new LoginResponseDTOInput(null,"Account disabled");
    }
    final UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(loginDTO.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    
    return new LoginResponseDTOInput(jwt,"login successful");
    
  }

  public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
    return verificationTokenRepository.save(verificationToken);
  }

  public LoginResponseDTOInput validateToken(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    if(verificationToken == null){
      return new LoginResponseDTOInput(null, "invalid");
    }
    //now checking for time expiration
    User user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();
    if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime())<= 0){
      verificationTokenRepository.deleteById(verificationToken.getId());
      userRepository.deleteById(user.getId());
      return new LoginResponseDTOInput(null, "Your verification period has expired please signup again");
    }
    //else if all is well
    user.setEnabled(true);
    User savedUser = userRepository.save(user);
    
    final UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(savedUser.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    
    return new LoginResponseDTOInput(jwt, "verification successful");
  }
}
