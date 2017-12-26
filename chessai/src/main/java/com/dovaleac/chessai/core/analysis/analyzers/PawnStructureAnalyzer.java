package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndColorPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractPieceListPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PawnStructureAnalyzer implements PositionAnalyzer {
  @Override
  public Set<PositionalFact> analyze(Set<PositionalFact> facts, Position position) {

    Map<Integer, List<Square>> whitePawns = getPawnsOfColor(position, Color.WHITE);
    Map<Integer, List<Square>> blackPawns = getPawnsOfColor(position, Color.BLACK);

    List<PositionalFact> whiteColumns = checkSemiOpenColumns(whitePawns, Color.WHITE);
    List<PositionalFact> blackColumns = checkSemiOpenColumns(blackPawns, Color.BLACK);

    List<PositionalFact> whiteDoubledPawns = checkDoubledPawns(whitePawns, position);
    List<PositionalFact> blackDoubledPawns = checkDoubledPawns(blackPawns, position);

    List<PositionalFact> whitePawnIslands = checkPawnIslands(whitePawns, Color.WHITE, position);
    List<PositionalFact> blackPawnIslands = checkPawnIslands(blackPawns, Color.BLACK, position);

    List<PositionalFact> whitePassedPawns = checkPassedPawns(whitePawns, blackPawns, Color.WHITE, position);
    List<PositionalFact> blackPassedPawns = checkPassedPawns(blackPawns, whitePawns, Color.WHITE, position);

    List<PositionalFact> whiteLatePawns = checkLatePawns(whitePawns, Color.WHITE, position);
    List<PositionalFact> blackLatePawns = checkLatePawns(blackPawns, Color.WHITE, position);

    List<PositionalFact> addendum = Stream.of(whiteColumns, blackColumns, whiteDoubledPawns,
        blackDoubledPawns, whitePawnIslands, blackPawnIslands, whitePassedPawns, blackPassedPawns,
        whiteLatePawns, blackLatePawns).flatMap(Collection::stream).collect(Collectors.toList());

    facts.addAll(addendum);
    return facts;
  }

  private Map<Integer, List<Square>> getPawnsOfColor(Position position, Color color) {
    return position.getPiecesByColor(color).stream()
        .filter(piece -> piece.getFigure() == Figure.PAWN)
        .map(Piece::getSquare)
        .collect(Collectors.groupingBy(Square::getColumn));
  }

  private List<PositionalFact> checkSemiOpenColumns(Map<Integer, List<Square>> pawns,
                                                    Color color) {
    return IntStream.range(0,7).boxed()
        .filter(column -> pawns.get(column) == null)
        .map(column ->
            new AbstractNumberAndColorPositionalFact.OpenOrSemiopenColumn(column, color))
        .collect(Collectors.toList());
  }

  private List<PositionalFact> checkDoubledPawns(Map<Integer, List<Square>> pawns,
                                                 Position position) {
    return pawns.values().stream()
        .filter(pawnsInColumn -> pawnsInColumn.size() > 1)
        .map(squares -> squares.stream()
            .map(position::getPieceInSquare).collect(Collectors.toList()))
        .map(AbstractPieceListPositionalFact.DoubledPawns::new)
        .collect(Collectors.toList());
  }

  private List<PositionalFact> checkPawnIslands(Map<Integer, List<Square>> pawns,
                                                Color color, Position position) {
    List<List<Integer>> islands = new ArrayList<>(4);
    List<Integer> currentIsland = new ArrayList<>(8);
    List<Square> pawnsInColumn;

    for (int i = 0; i < 8; i++) {
      pawnsInColumn = pawns.get(i);

      if (pawnsInColumn == null) {
        if (!currentIsland.isEmpty()) {
          islands.add(currentIsland);
          currentIsland = new ArrayList<>(8 - i);
        }
      } else {
        currentIsland.add(i);
      }
    }

    List<PositionalFact> result = new ArrayList<>();
    result.add(new AbstractNumberAndColorPositionalFact.NumberOfIslands(islands.size(), color));
    islands.forEach(island -> {
      if (island.size() == 1) {
        result.addAll(pawns.get(island.get(0)).stream()
            .map(position::getPieceInSquare)
            .map(AbstractOnePiecePositionalFact.IsolatedPawn::new)
            .collect(Collectors.toList()));
      }
    });

    return result;

  }

  private List<PositionalFact> checkLatePawns(Map<Integer, List<Square>> pawns,
                                              Color color, Position position) {
    List<PositionalFact> result = new ArrayList<>();
    List<Square> pawnsInColumn;
    List<Square> pawnsInRightColumn;
    List<Square> pawnsInLeftColumn;

    boolean isWhite = color == Color.WHITE;

    for (int i = 0; i < 8; i++) {
      pawnsInColumn = pawns.get(i);

      if (pawnsInColumn != null) {
        pawnsInLeftColumn = pawns.get(i - 1);
        pawnsInRightColumn = pawns.get(i + 1);

        if (pawnsInLeftColumn == null && pawnsInRightColumn == null) {
          continue;
        }

        result.addAll(pawnsInColumn.stream()
            .filter(square -> pawnsInLeftColumn == null || pawnsInLeftColumn.stream().allMatch(
                leftSquare -> allowsForLatePawn(leftSquare, square, isWhite)))
            .filter(square -> pawnsInRightColumn == null || pawnsInRightColumn.stream().allMatch(
                rightSquare -> allowsForLatePawn(rightSquare, square, isWhite)))
            .map(position::getPieceInSquare)
            .map(AbstractOnePiecePositionalFact.LatePawn::new)
            .collect(Collectors.toList()));
      }
    }

    return result;
  }

  private boolean allowsForLatePawn(Square leftSquare, Square square, boolean isWhite) {
    int rowDiff = leftSquare.getRow() - square.getRow();
    return (isWhite && rowDiff >=0) || (!isWhite && rowDiff <=0);
  }

  private List<PositionalFact> checkPassedPawns(Map<Integer, List<Square>> pawns,
                                                Map<Integer, List<Square>> rivalPawns,
                                                Color color,
                                                Position position) {
    List<PositionalFact> result = new ArrayList<>();
    boolean isWhite = color == Color.WHITE;

    List<Square> passedPawns = pawns.entrySet().stream()
        .map(Map.Entry::getValue)
        .flatMap(Collection::stream)
        .filter(square ->
            pawnsLetFreeWayToCrown(square, rivalPawns.get(square.getColumn() - 1), isWhite))
        .collect(Collectors.toList());

    passedPawns.forEach(square -> {
      result.add(new AbstractOnePiecePositionalFact.PassedPawn(position.getPieceInSquare(square)));
      if (checkIsProtected(pawns.get(square.getColumn() - 1), isWhite, square)
          || checkIsProtected(pawns.get(square.getColumn() + 1), isWhite, square)) {
        result.add(new AbstractOnePiecePositionalFact.PassedProtectedPawn(
            position.getPieceInSquare(square)));
      }
      Optional<Square> rightPassedPawn = passedPawns.stream()
          .filter(pawn -> pawn.getColumn() == square.getColumn() + 1)
          .findAny();
      rightPassedPawn.ifPresent(rightSquare ->
          result.add(new AbstractPieceListPositionalFact.PassedPawnCouple(
              Stream.of(square, rightSquare)
              .map(position::getPieceInSquare)
              .collect(Collectors.toList())
          )
          )
      );
    });

    return result;
  }

  private boolean checkIsProtected(List<Square> candidatePawns, boolean isWhite, Square square) {
    return candidatePawns != null && candidatePawns.stream().anyMatch(
        leftSquare -> leftSquare.getRow() == (isWhite ? square.getRow() -1 : square.getRow() + 1));
  }

  private boolean pawnsLetFreeWayToCrown(Square crowningPawn, List<Square> stopper, boolean isWhite) {
    return stopper
        .stream()
        .allMatch(square -> pawnLetsFreeWayToCrown(crowningPawn, square, isWhite));
  }

  private boolean pawnLetsFreeWayToCrown(Square crowningPawn, Square stopper, boolean isWhite) {
    if (stopper == null) {
      return true;
    }
    int rowDiff = stopper.getRow() - crowningPawn.getRow();
    return isWhite ? rowDiff >= 0 : rowDiff <= 0;
  }


}
