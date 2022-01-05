package com.jobsity.bowling_score_system.domain.score_strategies;

import static com.jobsity.bowling_score_system.domain.FrameType.STRIKE;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.ScoreStrategy;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpareScoreStrategy implements ScoreStrategy {

  private final List<Frame> frames;
  private final int index;

  @Override
  public void updateScore() {
    int score = 10;
    int previousFrameScore = index > 0 ? frames.get(index - 1).getFrameScore() : 0;
    if (index + 1 < frames.size() && frames.get(index + 1).getType().equals(STRIKE)) {
      score += 10;
    } else if (index + 1 < frames.size()) {
      score += frames.get(index + 1).getScores().get(0);
    }

    Frame currentFrame = frames.get(index);
    int frameScore = score + previousFrameScore;
    currentFrame.setFrameScore(frameScore);
  }
}
