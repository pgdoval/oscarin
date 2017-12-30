package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionFactory;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndColorPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractPieceListPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Piece;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PawnStructureAnalyzerTest {

  PawnStructureAnalyzer analyzer = new PawnStructureAnalyzer();

  @Test
  void analyzePosition() {
    Position position = PositionFactory.fromString("Ka1,a2,a3,c3,d4,e5,f6,Rh1,Rg1#g4,h7,a7,Kd7,e6,d5,c4,Rb8,Rc8##w#");
    List<PositionalFact> facts = analyzer.analyze(position).collect(Collectors.toList());

    facts
        .stream()
        .sorted((f1, f2) -> {
          int typeComparison = f1.getFactType().name().compareTo(f2.getFactType().name());
          if (typeComparison != 0) {
            return typeComparison;
          }
          return f1.toString().compareTo(f2.toString());
        })
        .forEach(System.out::println);

    List<PositionalFact> expectedFacts = Arrays.asList(
        new AbstractNumberAndColorPositionalFact.NumberOfIslands(2, Color.WHITE),
        new AbstractNumberAndColorPositionalFact.NumberOfIslands(3, Color.BLACK),
        new AbstractPieceListPositionalFact.DoubledPawns(Arrays.asList(
            position.getPieceInSquare(Square.fromString("a2")),
            position.getPieceInSquare(Square.fromString("a3")))
        ),
        new AbstractOnePiecePositionalFact.IsolatedPawn(position.getPieceInSquare(Square.fromString("a2"))),
        new AbstractOnePiecePositionalFact.IsolatedPawn(position.getPieceInSquare(Square.fromString("a3"))),
        new AbstractOnePiecePositionalFact.IsolatedPawn(position.getPieceInSquare(Square.fromString("a7"))),
        new AbstractOnePiecePositionalFact.LatePawn(position.getPieceInSquare(Square.fromString("c3"))),
        new AbstractOnePiecePositionalFact.LatePawn(position.getPieceInSquare(Square.fromString("e6"))),
        new AbstractOnePiecePositionalFact.LatePawn(position.getPieceInSquare(Square.fromString("h7"))),
        new AbstractOnePiecePositionalFact.PassedPawn(position.getPieceInSquare(Square.fromString("f6"))),
        new AbstractOnePiecePositionalFact.PassedPawn(position.getPieceInSquare(Square.fromString("g4"))),
        new AbstractOnePiecePositionalFact.PassedPawn(position.getPieceInSquare(Square.fromString("h7"))),
        new AbstractOnePiecePositionalFact.PassedProtectedPawn(position.getPieceInSquare(Square.fromString("f6"))),
        new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(1, Color.WHITE),
        new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(6, Color.WHITE),
        new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(7, Color.WHITE),
        new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(1, Color.BLACK),
        new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(5, Color.BLACK),
        new AbstractOnePiecePositionalFact.RookControlsOpenColumn(position.getPieceInSquare(Square.fromString("g1"))),
        new AbstractOnePiecePositionalFact.RookControlsOpenColumn(position.getPieceInSquare(Square.fromString("h1"))),
        new AbstractOnePiecePositionalFact.RookControlsOpenColumn(position.getPieceInSquare(Square.fromString("b8"))),
        new AbstractPieceListPositionalFact.PassedPawnCouple(Arrays.asList(
            position.getPieceInSquare(Square.fromString("g4")),
            position.getPieceInSquare(Square.fromString("h7")))
        )
    );
    expectedFacts.forEach( fact -> assertTrue(facts.contains(fact), fact::toString));
    assertEquals(expectedFacts.size(), (long) facts.size());
  }

}