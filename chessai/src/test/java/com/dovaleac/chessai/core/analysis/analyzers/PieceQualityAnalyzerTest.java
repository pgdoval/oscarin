package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndPiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.pieces.Piece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PieceQualityAnalyzerTest {

  PieceQualityAnalyzer analyzer = new PieceQualityAnalyzer();

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

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

}