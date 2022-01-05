package com.jobsity.bowling_score_system.domain.score_strategies;

import static com.jobsity.bowling_score_system.domain.FrameType.LAST;
import static com.jobsity.bowling_score_system.domain.FrameType.STRIKE;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.ScoreStrategy;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StrikeScoreStrategy implements ScoreStrategy {

  private final List<Frame> frames;
  private final int index;

  @Override
  public void updateScore() {
    int score = 10;
    int previousFrameScore = index > 0 ? frames.get(index - 1).getFrameScore() : 0;
    if (index + 2 < frames.size() && frames.get(index + 1).getType().equals(STRIKE)) {
      score += 10 + frames.get(index + 2).getScores().get(0);
    } else if (index + 1 < frames.size() && frames.get(index + 1).getType().equals(LAST)) {
      List<Integer> lastFrameScores = frames.get(index + 1).getScores();
      score += lastFrameScores.get(0) + lastFrameScores.get(1);
    } else if (index + 1 < frames.size()) {
      score += frames.get(index + 1).getFrameScore();
    }

    Frame currentFrame = frames.get(index);
    int frameScore = score + previousFrameScore;
    currentFrame.setFrameScore(frameScore);
  }
}
