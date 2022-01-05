package com.jobsity.bowling_score_system.domain.score_strategies;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.ScoreStrategy;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenScoreStrategy implements ScoreStrategy {

  private final List<Frame> frames;
  private final int index;

  @Override
  public void updateScore() {
    int previousFrameScore = index > 0 ? frames.get(index - 1).getFrameScore() : 0;
    Frame currentFrame = frames.get(index);
    int frameScore = currentFrame.getFrameScore() + previousFrameScore;
    currentFrame.setFrameScore(frameScore);
  }
}
