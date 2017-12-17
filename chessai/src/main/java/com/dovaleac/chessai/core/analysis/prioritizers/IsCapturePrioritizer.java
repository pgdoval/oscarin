package com.dovaleac.chessai.core.analysis.prioritizers;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.MovePrioritizer;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.valuables.Priority;

import java.util.ArrayList;
import java.util.List;

public class IsCapturePrioritizer extends MovePrioritizer {
  @Override
  protected List<Priority> analyze(Position position, Position.PositionRewritingInfo lastMove) throws NoKingPresentException {
    Figure capturedFigure = lastMove.getMove().getCapturedFigure();

    List<Priority> result = new ArrayList<>(1);

    if (capturedFigure != null) {
      result.add(Priority.capture(capturedFigure, lastMove.getMove().getFigure()));
    }
    return result;
  }
}
