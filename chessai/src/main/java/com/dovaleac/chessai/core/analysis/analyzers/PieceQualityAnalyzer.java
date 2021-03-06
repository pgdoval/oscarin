package com.dovaleac.chessai.core.analysis.analyzers;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractColorPositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractNumberAndPiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.AbstractOnePiecePositionalFact;
import com.dovaleac.chessai.core.analysis.positional_facts.PositionalFact;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PieceQualityAnalyzer implements PositionAnalyzer {
  @Override
  public Stream<PositionalFact> analyze(Position position) {

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
    List<Piece> ownPieces = position.getPiecesByColor(color);
    List<Piece> enemyPieces = position.getPiecesByColor(color.flip());

    boolean isWhite = color == Color.WHITE;
    Color enemyColor = color.flip();

    List<PositionalFact> facts = new ArrayList<>();
    long ownBishops = ownPieces.stream()
        .filter(piece -> piece.getFigure() == Figure.BISHOP).count();
    long enemyBishops = enemyPieces.stream()
        .filter(piece -> piece.getFigure() == Figure.BISHOP).count();


    if (ownBishops == 2 && enemyBishops < 2) {
      facts.add(new AbstractColorPositionalFact.BishopCouple(color));
    }

    if (ownBishops < 2 && enemyBishops == 2) {
      facts.add(new AbstractColorPositionalFact.BishopCouple(color.flip()));
    }

    ownPieces.stream()
        .filter(piece -> piece.getFigure() != Figure.PAWN)
        .filter(piece -> piece.getFigure() != Figure.KING)
        .forEach(piece -> {

          switch (piece.getFigure()) {
            case QUEEN:
              facts.add(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresQueenCanReach(
                  piece.getPossibleMoves(position).size(), piece
              ));
              break;
            case ROOK:
              facts.add(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresRookCanReach(
                  piece.getPossibleMoves(position).size(), piece
              ));
              int rookRow = piece.getSquare().getRow();
              if ((isWhite && rookRow > 5) || (!isWhite && rookRow <2)) {
                facts.add(new AbstractOnePiecePositionalFact.RookIsInSeventhOrEighth(piece));
              }
              break;
            case BISHOP:
              facts.add(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresBishopCanReach(
                  piece.getPossibleMoves(position).size(), piece
              ));
              if (isOutpost(piece.getSquare(), isWhite, enemyColor, position)) {
                facts.add(new AbstractOnePiecePositionalFact.BishopIsInOutpost(piece));
              }
              break;
            case KNIGHT:
              facts.add(new AbstractNumberAndPiecePositionalFact.NumberOfSquaresKnightCanReach(
                  piece.getPossibleMoves(position).size(), piece
              ));
              if (isOutpost(piece.getSquare(), isWhite, enemyColor, position)) {
                facts.add(new AbstractOnePiecePositionalFact.KnightIsInOutpost(piece));
              }
          }
        });

    return facts;
  }

  private boolean isOutpost (Square square, boolean isWhite, Color enemyColor, Position position) {
    Supplier<IntStream> rows = () -> isWhite
        ? IntStream.range(square.getRow() + 1, 7)
        : IntStream.range(0, square.getRow() - 1);

    return IntStream.of(square.getColumn() - 1, square.getColumn() + 1)
        .boxed()
        .flatMap(column -> rows.get().boxed().map(row -> Square.of(column, row)))
        .map(position::getPieceInSquare)
        .filter(Objects::nonNull)
        .filter(piece -> piece.getFigure() == Figure.PAWN)
        .noneMatch(piece -> piece.getColor() == enemyColor);
  }
}
