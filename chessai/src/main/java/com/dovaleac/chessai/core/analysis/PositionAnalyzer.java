package com.dovaleac.chessai.core.analysis;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;

@FunctionalInterface
public interface PositionAnalyzer<RT> {
  RT analyze(Position position) throws NoKingPresentException;
}
