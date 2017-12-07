package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.ConsolidatedPosition;
import com.dovaleac.chessai.core.PositionStatus;
import com.dovaleac.chessai.core.Side;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Move {
  private final PieceMove mainMove;
  private final PieceMove secondaryMove;
  private final Figure capturedFigure;

  public Move(PieceMove mainMove, PieceMove secondaryMove, Figure capturedFigure) {
    this.mainMove = mainMove;
    this.secondaryMove = secondaryMove;
    this.capturedFigure = capturedFigure;
  }

  public static Move castling(Side side, Color colorToMove) {
    int kingInitial = 4;
    int kingFinal;
    int rookInitial;
    int rookFinal;
    int row = colorToMove.equals(Color.WHITE) ? 0 : 7;

    if (side == Side.KINGSIDE)
    {
      rookInitial = 7;
      rookFinal = 5;
      kingFinal = 6;
    } else {
      rookInitial = 0;
      rookFinal = 3;
      kingFinal = 2;
    }

    PieceMove mainMove = new PieceMove( Piece.king(
        new Square(kingInitial, row), colorToMove), new Square(kingFinal, row));
    PieceMove secondaryMove = new PieceMove( Piece.rook(
        new Square(rookInitial, row), colorToMove), new Square(rookFinal, row));

    return new Move(mainMove, secondaryMove, null);
  }

  public static Move simple(Piece piece, Square square) {
    return new Move(new PieceMove(piece, square), null, null);
  }

  public static Move capture(Piece piece, Square square, Figure capturedFigure) {
    return new Move(new PieceMove(piece, square), null, capturedFigure);
  }

  public Figure getCapturedFigure() {
    return capturedFigure;
  }

  public Square getTargetSquare() {
    return mainMove.getTargetSquare();
  }

  public PositionStatus createNewStatus(PositionStatus formerStatus) {
    Color color = mainMove.getPiece().getColor();
    int secondRow = color == Color.WHITE ? 1 : 6;
    int fourthRow = color == Color.WHITE ? 3 : 4;

    if (mainMove.getPiece().getFigure() == Figure.KING) {
      return PositionStatus.builder(formerStatus)
          .withoutKingsideCastling(color)
          .withoutQueensideCastling(color)
          .build();
    }

    if (mainMove.getPiece().getFigure() == Figure.ROOK) {
      int firstRow = color == Color.WHITE ? 0 : 7;
      Square fromSquare = mainMove.getPiece().getSquare();
      if (fromSquare.getRow() == firstRow) {
        if (fromSquare.getColumn() == 0) {
          return PositionStatus.builder(formerStatus)
              .withoutQueensideCastling(color)
              .build();
        }
        if (fromSquare.getColumn() == 7) {
          return PositionStatus.builder(formerStatus)
              .withoutKingsideCastling(color)
              .build();
        }
      }
      return formerStatus;
    }

    if (mainMove.getPiece().getFigure() == Figure.PAWN) {
      if (mainMove.getPiece().getSquare().getRow() == secondRow
          && mainMove.getTargetSquare().getRow() == fourthRow) {
        return PositionStatus.builder(formerStatus)
            .withEnPassant(mainMove.getTargetSquare().getColumn())
            .build();
      }
    }

    return formerStatus;

  }

  public void consolidateMove(Map<Square, Piece> piecesBySquare,
                                            Map<Color, List<Piece>> piecesByColor) {
    List<PieceMove> singleMoves = new ArrayList<>(2);
    singleMoves.add(mainMove);
    if (secondaryMove != null) {
      singleMoves.add(secondaryMove);
    }

    singleMoves.forEach(move -> {
      //Update moving piece
      Piece pieceToCopy = move.getPiece();
      Piece pieceToUpdate = piecesBySquare.get(pieceToCopy.getSquare());

      pieceToUpdate.setSquare(move.getTargetSquare());
      pieceToUpdate.setFigure(pieceToCopy.getFigure()); //for crowning

      //Remove captured piece
      Piece pieceToRemove = piecesBySquare.get(move.getTargetSquare());
      if (pieceToRemove != null) {
        piecesByColor
            .get(pieceToRemove.getColor())
            .remove(pieceToRemove);
      }

    });


  }
}
