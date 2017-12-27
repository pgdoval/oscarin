package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;

import java.util.stream.Stream;

public interface PositionAnalyzer {
  Stream<PositionalFact> analyze(Position position) throws NoKingPresentException;
}
