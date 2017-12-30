package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndColorPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractPieceListPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.SimplePositionalFact;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KingSafetyAnalyzer implements PositionAnalyzer {
  @Override
  public Stream<PositionalFact> analyze(Position position)
      throws NoKingPresentException {

    return Stream.of(Color.values())
            .map(color -> {
              try {
                return analyzeSide(color, position);
              } catch (NoKingPresentException e) {
                return null;
              }
            })
            .filter(Objects::nonNull)
            .flatMap(Collection::stream);
  }

  private List<PositionalFact> analyzeSide(Color color, Position position)
      throws NoKingPresentException {
    List<PositionalFact> facts = new ArrayList<>();
    List<Piece> pieces = position.getPiecesByColor(color);

    Piece king = pieces.stream()
        .filter(piece -> piece.getFigure() == Figure.KING)
        .findAny()
        .orElseThrow(NoKingPresentException::new);

    Square kingSquare = king.getSquare();
    boolean isWhite = color == Color.WHITE;

    if (kingSquare.getColumn() > 2 && kingSquare.getColumn() < 6) {
      facts.add(SimplePositionalFact.IS_KING_IN_MIDDLE_COLUMNS);
    }

    if ((isWhite && kingSquare.getRow() > 0) || (!isWhite && kingSquare.getRow() < 7)) {
      facts.add(SimplePositionalFact.IS_KING_IN_MIDDLE_ROWS);
    }

    Stream<Square> relevantSquaresForKingSafety =
        getRelevantSquaresForKingSafety(kingSquare, isWhite);

    if (relevantSquaresForKingSafety != null) {
      if (relevantSquaresForKingSafety
          .map(position::getPieceInSquare)
          .allMatch(piece -> piece != null && piece.getFigure() == Figure.PAWN)) {
        facts.add(SimplePositionalFact.ARE_PROTECTING_PAWNS_IN_LINE);
      } //here we should check fianchetto safety
    }

    relevantSquaresForKingSafety =
        getRelevantSquaresForKingSafety(kingSquare, isWhite);

    if (relevantSquaresForKingSafety != null) {
      relevantSquaresForKingSafety = Stream.concat(
          relevantSquaresForKingSafety,
          nearbyHorizontalSquares(isWhite)
      ).distinct();

      facts.addAll(relevantSquaresForKingSafety
          .map(square ->
              Square.of(square.getColumn(),
                  square.getRow() + (isWhite ? 1 : -1)))
          .flatMap(square -> IntStream.range(2,5).boxed()
              .map(row -> Square.of(square.getColumn(), row))
              .filter(square1 -> square.getRow() ==square1.getRow()))
          .map(position::getPieceInSquare)
          .filter(Objects::nonNull)
          .map(piece -> piece.getColor() == color
              ? new AbstractOnePiecePositionalFact.OwnPieceNearKing(piece)
              : new AbstractOnePiecePositionalFact.EnemyPieceNearKing(piece)
          ).collect(Collectors.toList()));
    }

    return facts;
  }

  private Stream<Square> getRelevantSquaresForKingSafety(Square king, boolean isWhite) {
    int row;
    int kingRow = king.getRow();

    if (isWhite) {
      if (kingRow > 1) {
        return null;
      } else {
        row = kingRow + 1;
      }
    } else {
      if (kingRow < 6) {
        return null;
      } else {
        row = kingRow - 1;
      }
    }

    IntStream columns;
    switch (king.getColumn()) {
      case 0:
        columns = IntStream.of(0, 1);
        break;
      case 1:
        columns = IntStream.range(0,3);
        break;
      case 2:
        columns = IntStream.range(1,4);
        break;
      case 6:
        columns = IntStream.range(5,8);
        break;
      case 7:
        columns = IntStream.of(6,8);
        break;
      default:
        return null;
    }

    return columns.boxed().map(column -> Square.of(column, row));
  }

  private Stream<Square> nearbyHorizontalSquares(boolean isWhite) {
    IntStream rows = isWhite
        ? IntStream.range(0, 2)
        : IntStream.range(5, 7);

    return IntStream.range(2, 5).boxed()
        .flatMap(column -> rows.boxed().map(row -> Square.of(column, row)));
  }

}
