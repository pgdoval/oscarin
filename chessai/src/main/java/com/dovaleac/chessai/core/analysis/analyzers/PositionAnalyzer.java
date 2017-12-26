package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;

import java.util.Set;

public interface PositionAnalyzer {
  Set<PositionalFact> analyze(Set<PositionalFact> facts, Position position) throws NoKingPresentException;
}
