package com.jobsity.bowling_score_system;

import com.jobsity.bowling_score_system.domain.PlayerInfo;
import com.jobsity.bowling_score_system.exceptions.InvalidScoreInputException;
import com.jobsity.bowling_score_system.services.BowlingScoreService;
import com.jobsity.bowling_score_system.services.impl.BowlingScoreServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class BowlingScoreApp {

  private static final String SCORES_FILENAME = "src/main/resources/scores.txt";

  public static void main(String[] args) {
    String scoreFileName = getScoreFileName(args);
    BowlingScoreService scoreService = new BowlingScoreServiceImpl();
    List<PlayerInfo> playersInfo = scoreService.loadPlayersInfo(scoreFileName);
    if (playersInfo.isEmpty()) {
      throw new InvalidScoreInputException("No valid scores input provided");
    }
    playersInfo.stream().map(scoreService::getPlayerGameBoard).forEach(LOGGER::info);
  }

  @NotNull
  private static String getScoreFileName(String[] args) {
    return Optional.ofNullable(args)
        .filter(array -> array.length > 0)
        .map(array -> array[0])
        .orElse(SCORES_FILENAME);
  }
}
