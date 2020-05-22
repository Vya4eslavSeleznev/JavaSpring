package main.service;

import main.entity.User;
import main.exception.InvalidUserNameOrPasswordException;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findUserByUserName(username).orElseThrow(
      () -> new UsernameNotFoundException("Username not found"));
  }

  public User getAuthenticatedUser(String username, String password) {
    Optional<User> user = userRepository.findUserByUserName(username);

    if (!user.isPresent())
      throw new InvalidUserNameOrPasswordException("Invalid username");

    if (!passwordEncoder.matches(password, user.get().getPassword()))
      throw new InvalidUserNameOrPasswordException("Invalid password");

    return user.get();
  }
}
