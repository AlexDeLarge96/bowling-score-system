package com.jobsity.bowling_score_system.domain;

import static com.jobsity.bowling_score_system.domain.FrameType.STRIKE;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Frame {

  private final List<Integer> scores;
  private final FrameType type;
  private final ScoreStrategy scoreStrategy;
  @Setter private int frameScore;

  public Frame(@NotNull List<Integer> scores, FrameType type, ScoreStrategy scoreStrategy) {
    this.scores = scores;
    this.type = type;
    this.scoreStrategy = scoreStrategy;
    this.frameScore = scores.stream().reduce(0, Integer::sum);
  }

  @Override
  public String toString() {
    StringBuilder scoreText = new StringBuilder();
    for (int i = 0; i < scores.size(); i++) {
      String score = i == 1 && scores.get(0) + scores.get(1) == 10 ? "/" : scores.get(i).toString();
      score = score.replace("10", "X").replace('0', '-');
      score = type.equals(STRIKE) ? "X" : score;
      scoreText.append(score).append(" ");
    }

    return scoreText.toString();
  }
}
