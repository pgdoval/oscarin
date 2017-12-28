package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
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

}