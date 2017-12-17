package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.PositionStatus;
import com.dovaleac.chessai.core.Side;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Move {
  private final PieceMove mainMove;
  private final PieceMove secondaryMove;
  private final Figure capturedFigure;

  public Move(PieceMove mainMove, PieceMove secondaryMove, Figure capturedFigure) {
    this.mainMove = mainMove;
    this.secondaryMove = secondaryMove;
    this.capturedFigure = capturedFigure;
  }

  public Piece resultingPiece(Position position) {
    if (secondaryMove != null) {
      return null;
    }

    return position.getPieceInSquare(mainMove.getTargetSquare());
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
        Square.of(kingInitial, row), colorToMove), Square.of(kingFinal, row));
    PieceMove secondaryMove = new PieceMove( Piece.rook(
        Square.of(rookInitial, row), colorToMove), Square.of(rookFinal, row));

    return new Move(mainMove, secondaryMove, null);
  }

  public static Move simple(Piece piece, Square square) {
    return simple(piece, square, false);
  }

  public static Move simple(Piece piece, Square square, boolean isCrowning) {
    return new Move(new PieceMove(piece, square, isCrowning), null, null);
  }

  public static Move capture(Piece piece, Square square, Figure capturedFigure) {
    return capture(piece, square, capturedFigure, false);
  }

  public static Move capture(Piece piece, Square square, Figure capturedFigure,
                             boolean isCrowning) {
    return new Move(new PieceMove(piece, square, isCrowning), null, capturedFigure);
  }

  public static Move fromNotation(Position position, String notation) {
    if (notation.length() < 2) {
      return null;
    }
    Color color = position.getTurn();
    char secondChar = notation.charAt(1);
    if (Objects.equals(notation, "0-0")) {
      return castling(Side.KINGSIDE, color);
    }
    if (Objects.equals(notation, "0-0-0")) {
      return castling(Side.QUEENSIDE, color);
    }

    int addForNoPawn = (secondChar > '0' && secondChar < '9') ? 0 : 1;
    boolean isCapture = notation.contains("x");
    int addForCapture = isCapture ? 1 : 0;

    Piece piece = Piece.fromNotation(notation.substring(addForNoPawn, addForNoPawn + 2), color);
    Square targetSquare = Square.fromString(notation
        .substring(addForCapture + addForNoPawn + 2, addForCapture + addForNoPawn + 4));

    if (isCapture) {
      Piece capturedPiece = position.getPieceInSquare(targetSquare);
      return capture(
          piece,
          targetSquare,
          capturedPiece == null ? null : capturedPiece.getFigure()
      );
    } else {
      return simple(piece, targetSquare);
    }
  }

  public String notation() {
    if (secondaryMove != null) {
      if (secondaryMove.getTargetSquare().getColumn() > 4) {
        return "0-0";
      } else {
        return "0-0-0";
      }
    }
    boolean capture = capturedFigure != null;

    StringBuilder sb = new StringBuilder(7);

    if (mainMove.isCrowning())
    {
      sb.append(mainMove.getPiece().getSquare().toString());
    } else {
      sb.append(mainMove.getPiece().toString());
    }

    if (capture) {
      sb.append('x');
    }
    sb.append(mainMove.getTargetSquare().toString());

    if (mainMove.isCrowning()) {
      sb.append("=");
      sb.append(mainMove.getPiece().getFigure().getRepresentation());
    }
    return sb.toString();
  }

  public Figure getCapturedFigure() {
    return capturedFigure;
  }

  public Figure getFigure() {
    return mainMove.getPiece().getFigure();
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

    if (mainMove.getPiece().getFigure() == Figure.PAWN
        && mainMove.getPiece().getSquare().getRow() == secondRow
        && mainMove.getTargetSquare().getRow() == fourthRow) {
      return PositionStatus.builder(formerStatus)
          .withEnPassant(mainMove.getTargetSquare().getColumn())
          .build();

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

      piecesBySquare.remove(pieceToCopy.getSquare());
      piecesBySquare.put(pieceToUpdate.getSquare(), pieceToUpdate);

      if (pieceToRemove != null) {
        piecesByColor
            .get(pieceToRemove.getColor())
            .remove(pieceToRemove);
      }

    });


  }
}
