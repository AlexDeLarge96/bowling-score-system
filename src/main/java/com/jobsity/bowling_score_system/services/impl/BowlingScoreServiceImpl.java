package com.jobsity.bowling_score_system.services.impl;

import static com.jobsity.bowling_score_system.domain.FrameType.LAST;
import static com.jobsity.bowling_score_system.domain.FrameType.OPEN;
import static com.jobsity.bowling_score_system.domain.FrameType.SPARE;
import static com.jobsity.bowling_score_system.domain.FrameType.STRIKE;
import static com.jobsity.bowling_score_system.utils.BowlingScoreUtils.getFileContent;
import static com.jobsity.bowling_score_system.utils.BowlingScoreUtils.getScoreNumberFromText;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.PlayerInfo;
import com.jobsity.bowling_score_system.domain.ScoreStrategy;
import com.jobsity.bowling_score_system.domain.score_strategies.LastScoreStrategy;
import com.jobsity.bowling_score_system.domain.score_strategies.OpenScoreStrategy;
import com.jobsity.bowling_score_system.domain.score_strategies.SpareScoreStrategy;
import com.jobsity.bowling_score_system.domain.score_strategies.StrikeScoreStrategy;
import com.jobsity.bowling_score_system.exceptions.InvalidScoreInputException;
import com.jobsity.bowling_score_system.services.BowlingScoreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
@SuppressWarnings("SpellCheckingInspection")
public class BowlingScoreServiceImpl implements BowlingScoreService {

  private static final String SCORE_INPUT_REGEX = "^[A-Za-z]+[\\s]+(\\b([0-9]|10)\\b|F|X)$";
  private static final String BOARD_FORMAT = "%n%s%n%s%n%s%n%s%n";
  private static final String EXTRA_SCORE_ERROR = "Extra scores provided (%s extra score(s))";
  private static final String MISSING_FRAMES_ERROR =
      "Incomplete list of scores provided (only %s frames specified)";

  @Override
  public List<PlayerInfo> loadPlayersInfo(@NotNull String fileName) {
    return getFileContent(fileName).stream()
        .filter(Objects::nonNull)
        .map(String::trim)
        .filter(line -> line.matches(SCORE_INPUT_REGEX))
        .map(line -> line.split("\\s"))
        .map(input -> entry(input[0], getScoreNumberFromText(input[1])))
        .collect(groupingBy(Entry::getKey, mapping(Map.Entry::getValue, toList())))
        .entrySet()
        .stream()
        .filter(info -> info.getValue().size() > 10 && info.getValue().size() < 22)
        .map(info -> new PlayerInfo(info.getKey(), info.getValue()))
        .collect(toList());
  }

  @Override
  public List<Frame> getFrames(@NotNull List<Integer> scores) {
    List<Frame> frames = new ArrayList<>();
    int i = 0;
    int frameCount = 0;
    while (i < scores.size() && frames.size() < 10) {
      ScoreStrategy scoreStrategy;
      List<Integer> frameScores;
      int currentScore = scores.get(i);
      if (currentScore == 10 && frames.size() < 9) {
        frameScores = singletonList(currentScore);
        scoreStrategy = new StrikeScoreStrategy(frames, frameCount);
        frames.add(new Frame(frameScores, STRIKE, scoreStrategy));
      } else if (frameCount == 9 && i + 3 <= scores.size()) {
        frameScores = asList(currentScore, scores.get(i + 1), scores.get(i + 2));
        scoreStrategy = new LastScoreStrategy(frames);
        frames.add(new Frame(frameScores, LAST, scoreStrategy));
        i += 2;
      } else if (i + 1 < scores.size() && currentScore + scores.get(i + 1) == 10) {
        frameScores = asList(currentScore, scores.get(i + 1));
        scoreStrategy = new SpareScoreStrategy(frames, frameCount);
        frames.add(new Frame(frameScores, SPARE, scoreStrategy));
        i++;
      } else if (i + 1 < scores.size() && !(frameCount == 9 && currentScore == 10)) {
        frameScores = asList(currentScore, scores.get(i + 1));
        scoreStrategy = new OpenScoreStrategy(frames, frameCount);
        frames.add(new Frame(frameScores, OPEN, scoreStrategy));
        i++;
      }

      i++;
      frameCount++;
    }

    frameCount = frames.size();
    if (scores.size() > i) {
      int extraScores = scores.size() - i;
      throw new InvalidScoreInputException(format(EXTRA_SCORE_ERROR, extraScores));
    } else if (frameCount < 10) {
      throw new InvalidScoreInputException(format(MISSING_FRAMES_ERROR, frameCount));
    }

    return frames;
  }

  @Override
  public String getPlayerGameBoard(@NotNull PlayerInfo playerInfo) {
    String playerName = playerInfo.getName();
    List<Integer> playerScores = playerInfo.getScores();
    List<Frame> frames = getFrames(playerScores);
    StringBuilder framesNumbers = new StringBuilder("Frame   \t");
    StringBuilder framesPinFalls = new StringBuilder("Pinfalls\t");
    StringBuilder framesScores = new StringBuilder("Score   \t");
    for (int i = 0; i < frames.size(); i++) {
      Frame frame = frames.get(i);
      frame.getScoreStrategy().updateScore();
      String frameText = frame.toString().trim();
      frameText = frameText.equals("X") ? " X " : frameText;
      String frameScore = frame.getFrameScore() + " ";
      int blankSpaces = 4 - frameScore.length();
      frameScore = frameScore + " ".repeat(blankSpaces);
      framesNumbers.append(i + 1).append("    ");
      framesPinFalls.append(frameText).append("  ");
      framesScores.append(frameScore).append(" ");
    }

    return format(
        BOARD_FORMAT,
        playerName,
        framesNumbers.toString().trim(),
        framesPinFalls.toString().trim(),
        framesScores.toString().trim());
  }
}
