package com.jobsity.bowling_score_system.domain.score_strategies;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.ScoreStrategy;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LastScoreStrategy implements ScoreStrategy {

  private final List<Frame> frames;

  @Override
  public void updateScore() {
    int index = frames.size() - 1;
    int previousFrameScore = frames.get(index - 1).getFrameScore();
    Frame currentFrame = frames.get(index);
    int frameScore = currentFrame.getFrameScore() + previousFrameScore;
    currentFrame.setFrameScore(frameScore);
  }
}
