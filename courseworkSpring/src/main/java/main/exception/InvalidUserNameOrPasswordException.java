package main.exception;

public class InvalidUserNameOrPasswordException extends RuntimeException {
  public InvalidUserNameOrPasswordException(String message) {
    super(message);
  }

}