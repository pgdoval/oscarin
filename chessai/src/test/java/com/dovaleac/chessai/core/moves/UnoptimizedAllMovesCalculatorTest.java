package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UnoptimizedAllMovesCalculatorTest {

  @Test
  public void calculateAllMoves() {

    String positions = "";
    String moves = "Nb1a3, Nb1c3, Ng1f3, Ng1h3, a2a3, a2a4, b2b3, b2b4, c2c3, c2c4, d2d3, d2d4, " +
        "e2e3, e2e4, f2f3, f2f4, g2g3, g2g4, h2h3, h2h4";
    UnoptimizedAllMovesCalculator calculator =
        new UnoptimizedAllMovesCalculator();

    Position currentPosition = Stream.of(positions.split(", "))
        .reduce(PositionFactory.initialPosition(),
            (position, move) -> position.move(Move.fromNotation(position, move)),
            (m1, m2) -> m2);
    assertEquals(moves, calculator.calculateAllMoves(currentPosition)
        .map(Move::notation).sorted().collect(Collectors.joining(", ")));
  }

/*
  public static Stream<Arguments> positions(){
    return Stream.of(
        ObjectArrayArguments.create("", "a2a4, b2b4, c2c4, d2d4, e2e4, f2f4, g2g4, h2h4, Nb1c3, Ng1f3"),
        ObjectArrayArguments.create("", "a2a4, b2b4, c2c4, d2d4, e2e4, f2f4, g2g4, h2h4, Nb1c3, Ng1f3")
    );
  }*/
}