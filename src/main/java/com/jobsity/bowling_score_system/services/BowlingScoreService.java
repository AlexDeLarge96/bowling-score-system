package com.jobsity.bowling_score_system.services;

import com.jobsity.bowling_score_system.domain.Frame;
import com.jobsity.bowling_score_system.domain.PlayerInfo;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface BowlingScoreService {

  List<PlayerInfo> loadPlayersInfo(@NotNull String fileName);

  List<Frame> getFrames(@NotNull List<Integer> scores);

  String getPlayerGameBoard(@NotNull PlayerInfo playerInfo);
}
