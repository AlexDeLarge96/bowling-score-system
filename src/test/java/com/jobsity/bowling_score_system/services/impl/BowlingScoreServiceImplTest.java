package com.jobsity.bowling_score_system.services.impl;

import static com.jobsity.bowling_score_system.utils.BowlingScoreUtils.getFileContent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jobsity.bowling_score_system.domain.PlayerInfo;
import com.jobsity.bowling_score_system.exceptions.InvalidScoreInputException;
import com.jobsity.bowling_score_system.services.BowlingScoreService;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class BowlingScoreServiceImplTest {

  private BowlingScoreService scoreService;

  @BeforeEach
  void setup() {
    scoreService = new BowlingScoreServiceImpl();
  }

  @Test
  void shouldLoadPlayersInfo() {
    List<PlayerInfo> playersInfo =
        scoreService.loadPlayersInfo("src/test/resources/positive/perfect.txt");
    String playerName = playersInfo.get(0).getName();
    List<Integer> scores = playersInfo.get(0).getScores();
    assertEquals(1, playersInfo.size());
    assertEquals("Carl", playerName);
    assertEquals(12, scores.size());
    assertEquals(120, scores.stream().reduce(0, Integer::sum));
  }

  @ParameterizedTest
  @MethodSource("gameBoardTestProvider")
  void shouldGetPlayerGameBoard(String inputGameBoardFile, String expectedGameBoard) {
    List<PlayerInfo> playerInfo = scoreService.loadPlayersInfo(inputGameBoardFile);
    String gameBoard = scoreService.getPlayerGameBoard(playerInfo.get(0)).trim();
    assertEquals(expectedGameBoard, gameBoard);
  }

  @Test
  void shouldThrowExceptionForExtraScore() {
    List<PlayerInfo> playerInfo =
        scoreService.loadPlayersInfo("src/test/resources/negative/extra-score.txt");
    boolean exceptionWasThrown = false;
    String errorMsg = "";
    try {
      scoreService.getPlayerGameBoard(playerInfo.get(0));
    } catch (InvalidScoreInputException e) {
      exceptionWasThrown = true;
      errorMsg = e.getMessage();
    }

    assertEquals("Extra scores provided (1 extra score(s))", errorMsg);
    assertTrue(exceptionWasThrown);
  }

  @ParameterizedTest
  @CsvSource({
    "src/test/resources/negative/negative.txt",
    "src/test/resources/negative/invalid-score.txt"
  })
  void shouldThrowExceptionForIncompleteScores(String fileName) {
    List<PlayerInfo> playerInfo = scoreService.loadPlayersInfo(fileName);
    boolean exceptionWasThrown = false;
    String errorMsg = "";
    try {
      scoreService.getPlayerGameBoard(playerInfo.get(0));
    } catch (InvalidScoreInputException e) {
      exceptionWasThrown = true;
      errorMsg = e.getMessage();
    }

    assertEquals("Incomplete list of scores provided (only 9 frames specified)", errorMsg);
    assertTrue(exceptionWasThrown);
  }

  @Test
  void shouldGetPlayersGameBoardForTwoPlayers() {
    String expectedGameBoard1 =
        getExpectedGameBoard("src/test/resources/expectedResults/player1Score.txt");
    String expectedGameBoard2 =
        getExpectedGameBoard("src/test/resources/expectedResults/player2Score.txt");
    List<PlayerInfo> playerInfo =
        scoreService.loadPlayersInfo("src/test/resources/positive/scores.txt");
    String gameBoard1 = scoreService.getPlayerGameBoard(playerInfo.get(0)).trim();
    String gameBoard2 = scoreService.getPlayerGameBoard(playerInfo.get(1)).trim();
    assertEquals(expectedGameBoard1, gameBoard1);
    assertEquals(expectedGameBoard2, gameBoard2);
  }

  @ParameterizedTest
  @CsvSource({
    "src/test/resources/negative/empty.txt",
    "src/test/resources/negative/free-text.txt",
    "src/test/resources/negative/unclosed-store.txt"
  })
  void shouldReturnThatPlayerInfoIsEmpty(String fileName) {
    List<PlayerInfo> playerInfo = scoreService.loadPlayersInfo(fileName);
    assertTrue(playerInfo.isEmpty());
  }

  private static @NotNull Stream<Arguments> gameBoardTestProvider() {
    return Stream.of(
        Arguments.of(
            "src/test/resources/positive/perfect.txt",
            getExpectedGameBoard("src/test/resources/expectedResults/perfectScoreResult.txt")),
        Arguments.of(
            "src/test/resources/positive/customScore2.txt",
            getExpectedGameBoard("src/test/resources/expectedResults/customScore2Result.txt")));
  }

  @NotNull
  private static String getExpectedGameBoard(String resultFile) {
    return String.join("\n", getFileContent(resultFile));
  }
}
