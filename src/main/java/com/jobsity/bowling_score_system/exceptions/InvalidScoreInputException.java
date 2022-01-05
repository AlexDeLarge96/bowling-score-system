package com.jobsity.bowling_score_system.exceptions;

import lombok.Getter;

@Getter
public class InvalidScoreInputException extends RuntimeException {

  private final String message;

  public InvalidScoreInputException(String message) {
    super(message);
    this.message = message;
  }
}
