package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractColorPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndPiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.pieces.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PieceQualityAnalyzerTest {

  PieceQualityAnalyzer analyzer = new PieceQualityAnalyzer();

  @Test
  void analyzeOutposts() {
    Position position = PositionFactory.fromString("Nd4,Na5,Ke1,a2,b2,c2,d2,e3,Bg2#Na4,Bf5,Ka8,a7,b7,d5,e4,Bh4##w#");
    List<PositionalFact> facts = analyzer.analyze(position).collect(Collectors.toList());

    facts.forEach(System.out::println);
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.KnightIsInOutpost(Piece.fromNotation("Nd4", Color.WHITE))));
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.BishopIsInOutpost(Piece.fromNotation("Bg2", Color.WHITE))));
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.BishopIsInOutpost(Piece.fromNotation("Bh4", Color.BLACK))));
    assertEquals(3, facts
        .stream()
        .filter(it -> it instanceof AbstractOnePiecePositionalFact.KnightIsInOutpost
            || it instanceof AbstractOnePiecePositionalFact.BishopIsInOutpost)
        .count());
  }

  @Test
  void analyzeAdvancedRooks() {
    Position position = PositionFactory.fromString("Ra1,Rf7,Ke1,Rg8#Ra4,Kb5,Rc8,Rd2##w#");
    List<PositionalFact> facts = analyzer.analyze(position).collect(Collectors.toList());

    facts.forEach(System.out::println);
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.RookIsInSeventhOrEighth(Piece.fromNotation("Rf7", Color.WHITE))));
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.RookIsInSeventhOrEighth(Piece.fromNotation("Rg8", Color.WHITE))));
    assertTrue(facts.contains(new AbstractOnePiecePositionalFact.RookIsInSeventhOrEighth(Piece.fromNotation("Rd2", Color.BLACK))));
    assertEquals(3, facts
        .stream()
        .filter(it -> it instanceof AbstractOnePiecePositionalFact.RookIsInSeventhOrEighth)
        .count());
  }

  @Test
  void analyzeNumberOfMoves() {
    Position position = PositionFactory.fromString("Qa1,a2,b2,Nb1,d2,Kf1,Rh1,g2#Bh4,f6,Rd8,Rd7,Kf8##w#");
    List<PositionalFact> facts = analyzer.analyze(position).collect(Collectors.toList());

    facts.forEach(System.out::println);
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresKnightCanReach(
        2, Piece.fromNotation("Nb1", Color.WHITE))));
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresQueenCanReach(
        0, Piece.fromNotation("Qa1", Color.WHITE))));
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresRookCanReach(
        4, Piece.fromNotation("Rh1", Color.WHITE))));
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresBishopCanReach(
        4, Piece.fromNotation("Bh4", Color.BLACK))));
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresRookCanReach(
        4, Piece.fromNotation("Rd8", Color.BLACK))));
    assertTrue(facts.contains(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresRookCanReach(
        12, Piece.fromNotation("Rd7", Color.BLACK))));
    assertEquals(6, facts
        .stream()
        .filter(it -> it instanceof AbstractNumberAndPiecePositionalFact.NumberOfSquaresKnightCanReach
            || it instanceof AbstractNumberAndPiecePositionalFact.NumberOfSquaresBishopCanReach
            || it instanceof AbstractNumberAndPiecePositionalFact.NumberOfSquaresRookCanReach
            || it instanceof AbstractNumberAndPiecePositionalFact.NumberOfSquaresQueenCanReach)
        .count());
  }

  @ParameterizedTest
  @MethodSource(value = "bnPositions")
  public void analyzeBishopNumber(String positionString, Color color) {
    Position position = PositionFactory.fromString(positionString);
    List<PositionalFact> facts = analyzer.analyze(position).distinct().collect(Collectors.toList());

    facts.forEach(System.out::println);
    if (color != null) {
      assertTrue(facts.contains(new AbstractColorPositionalFact.BishopCouple(color)));
    }
    assertEquals(color == null ? 0 : 1, facts
        .stream()
        .filter(it -> it instanceof AbstractColorPositionalFact.BishopCouple)
        .count());
  }

  public static Stream<Object []> bnPositions() {
    return Stream.of(
        new Object[]{"Ba3,Ba2,Ke8#Bf4,Kd1##w#", Color.WHITE},
        new Object[]{"Ba3,Ba2,Ke8#Nf4,Kd1##w#", Color.WHITE},
        new Object[]{"Ba3,Ke8#Ba2,Bf4,Kd1##w#", Color.BLACK},
        new Object[]{"Na3,Ke8#Ba2,Bf4,Kd1##w#", Color.BLACK},
        new Object[]{"Ba3,Ke8#a2,Bf4,Kd1##w#", null},
        new Object[]{"Ba3,Ke8#a2,f4,Kd1##w#", null},
        new Object[]{"a3,Ke8#a2,Bf4,Kd1##w#", null},
        new Object[]{"a3,Ke8#a2,f4,Kd1##w#", null},
        new Object[]{"Ba3,Ba2,Ke8#Be4,Bf4,Kd1##w#", null}
        );
  }
}