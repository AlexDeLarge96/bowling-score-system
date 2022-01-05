package com.jobsity.bowling_score_system.utils;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public final class BowlingScoreUtils {

  private BowlingScoreUtils() {}

  public static List<String> getFileContent(@NotNull String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      return reader.lines().collect(toList());
    } catch (IOException e) {
      return emptyList();
    }
  }

  public static int getScoreNumberFromText(@NotNull String scoreText) {
    try {
      scoreText = scoreText.replace('F', '0').replace("X", "10");
      return parseInt(scoreText);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
