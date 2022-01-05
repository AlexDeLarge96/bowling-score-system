package com.jobsity.bowling_score_system.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerInfo {

  private final String name;
  private final List<Integer> scores;
}
