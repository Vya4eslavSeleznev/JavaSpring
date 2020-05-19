package main.web;

import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController
{
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  UserRepository userRep;

  @PostMapping("/signin")
  public ResponseEntity singIn(@RequestBody AuthRequest request)
  {
    try
    {
      String name = request.getUsername();
      String token = jwtTokenProvider.createToken(
        name,
        userRep.findUserByUserName(name)
          .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
      );

      Map<Object, Object> model = new HashMap<>();
      model.put("username", name);
      model.put("token", token);

      return ResponseEntity.ok(model);
    }
    catch (AuthenticationException e)
    {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
