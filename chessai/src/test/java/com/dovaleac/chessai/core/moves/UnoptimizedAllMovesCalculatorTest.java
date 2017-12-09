package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UnoptimizedAllMovesCalculatorTest {
  @ParameterizedTest
  @MethodSource(value = "positions")
  public void calculateAllMoves(String positions, String moves) {

    UnoptimizedAllMovesCalculator calculator =
        new UnoptimizedAllMovesCalculator();

    Position currentPosition = Stream.of(positions.split(", "))
        .reduce(PositionFactory.initialPosition(),
            (position, move) -> position.move(Move.fromNotation(position, move)),
            (m1, m2) -> m2);
    assertEquals(moves, calculator.calculateAllMoves(currentPosition)
        .map(Move::notation).sorted().collect(Collectors.joining(", ")));
  }



  public static Stream<Object []> positions(){
    return Stream.of(
        new Object[][]{
            new Object[]{"e2e4, d7d5", "Bf1a6, Bf1b5, Bf1c4, Bf1d3, Bf1e2, Ke1e2, Nb1a3, Nb1c3, Ng1e2, Ng1f3, Ng1h3, Qd1e2, Qd1f3, Qd1g4, Qd1h5, a2a3, a2a4, b2b3, b2b4, c2c3, c2c4, d2d3, d2d4, e4e5, e4xd5, f2f3, f2f4, g2g3, g2g4, h2h3, h2h4"},
            new Object[]{"", "Nb1a3, Nb1c3, Ng1f3, Ng1h3, a2a3, a2a4, b2b3, b2b4, c2c3, c2c4, d2d3, d2d4, e2e3, e2e4, f2f3, f2f4, g2g3, g2g4, h2h3, h2h4"},
            new Object[]{"e2e4", "Nb8a6, Nb8c6, Ng8f6, Ng8h6, a7a5, a7a6, b7b5, b7b6, c7c5, c7c6, d7d5, d7d6, e7e5, e7e6, f7f5, f7f6, g7g5, g7g6, h7h5, h7h6"}
        }
    );
  }
}