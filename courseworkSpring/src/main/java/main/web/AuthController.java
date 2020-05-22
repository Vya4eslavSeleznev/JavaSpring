package main.web;

import main.entity.User;
import main.exception.InvalidUserNameOrPasswordException;
import main.security.jwt.JwtTokenProvider;
import main.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  CustomUserDetailsService userDetailsService;

  @PostMapping("/signin")
  public ResponseEntity singIn(@RequestBody AuthRequest request) {
    try {
      User user = userDetailsService.getAuthenticatedUser(request.getUsername(), request.getPassword());

      String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

      Map<Object, Object> model = new HashMap<>();
      model.put("username", user.getUsername());
      model.put("token", token);

      return ResponseEntity.ok(model);
    }
    catch(InvalidUserNameOrPasswordException | AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
