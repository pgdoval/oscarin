package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.ConsolidatedPosition;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MoveCalculator {

  public abstract List<Move> calculateMoves(ConsolidatedPosition position, Square square,
                                            Piece piece);

  public static MoveCalculator queen() {
    return Queen.getInstance();
  }

  public static MoveCalculator rook() {
    return Rook.getInstance();
  }

  public static MoveCalculator bishop() {
    return Bishop.getInstance();
  }

  public static MoveCalculator knight() {
    return Knight.getInstance();
  }

  public static MoveCalculator king() {
    return King.getInstance();
  }

  public static MoveCalculator pawn() {
    return Pawn.getInstance();
  }

  private static class Queen extends ComposedMoveCalculator {

    private static volatile Queen mInstance;

    public Queen() {
      super(Rook.getInstance(), Bishop.getInstance());
    }

    public static Queen getInstance() {
      if (mInstance == null) {
        synchronized (Queen.class) {
          if (mInstance == null) {
            mInstance = new Queen();
          }
        }
      }
      return mInstance;
    }
  }

  private static class ComposedMoveCalculator extends MoveCalculator {
    private final MoveCalculator type1;
    private final MoveCalculator type2;

    public ComposedMoveCalculator(MoveCalculator type1, MoveCalculator type2) {
      this.type1 = type1;
      this.type2 = type2;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      List<Move> result = type1.calculateMoves(position, square, piece);
      result.addAll(type2.calculateMoves(position, square, piece));
      return result;
    }
  }
  private static class Pawn extends MoveCalculator {

    private static volatile Pawn mInstance;

    private Pawn() {
    }

    public static Pawn getInstance() {
      if (mInstance == null) {
        synchronized (Pawn.class) {
          if (mInstance == null) {
            mInstance = new Pawn();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      List<Move> result = new ArrayList<>(8);
      int r = square.getRow();
      int c = square.getColumn();

      boolean isWhite = position.getTurn() == Color.WHITE;
      Square candidateSquare;
      Piece capturedPiece;

      //Simple move
      if (!(((r == 6) && isWhite)
          || ((r == 1) && !isWhite))) {
        candidateSquare = new Square(c, isWhite ? r + 1 : r - 1);
        if (!position.isSquareOccupiedByMovingColor(candidateSquare)
            && !position.isSquareOccupiedByOppositeColor(candidateSquare)) {
          result.add(Move.simple(piece, candidateSquare));
        }
      }

      //Double advance
      if (((r == 1) && isWhite)
          || ((r == 6) && !isWhite)) {
        candidateSquare = new Square(c, isWhite ? r + 2 : r - 2);
        Square intermediateSquare = new Square(c, isWhite ? r + 1 : r - 1);
        if (!position.isSquareOccupiedByMovingColor(candidateSquare)
            && !position.isSquareOccupiedByOppositeColor(candidateSquare)
            && !position.isSquareOccupiedByMovingColor(candidateSquare)
            && !position.isSquareOccupiedByOppositeColor(candidateSquare)) {
          result.add(Move.simple(piece, candidateSquare));
        }
      }

      //Left capture
      if ((c > 0) && (r > 0) && (r < 7)) {
        Move capture = capture(position, piece, c - 1, isWhite ? r + 1 : r - 1);
        if (capture != null) {
          result.add(capture);
        }
      }

      //Right capture
      if ((c < 7) && (r > 0) && (r < 7)) {
        Move capture = capture(position, piece, c + 1, isWhite ? r + 1 : r - 1);
        if (capture != null) {
          result.add(capture);
        }
      }

      //Left ep capture
      if ((c - 1 == position.getEnPassant()) && (r == (isWhite ? 4 : 3))) {
        Move capture = capture(position, piece, c - 1, r);
        if (capture != null) {
          result.add(capture);
        }
      }

      //Right ep capture
      if ((c + 1 == position.getEnPassant()) && (r == (isWhite ? 4 : 3))) {
        Move capture = capture(position, piece, c + 1, r);
        if (capture != null) {
          result.add(capture);
        }
      }

      //Queening + queening captures
      if (((r == 6) && isWhite) || ((r == 1) && !isWhite)) {
        result.addAll(queening(position, piece, c, isWhite ? r + 1 : r - 1));
        result.addAll(queeningCapture(position, piece, c - 1, isWhite ? r + 1 : r - 1));
        result.addAll(queeningCapture(position, piece, c + 1, isWhite ? r + 1 : r - 1));
      }

      return result;
    }

    private Move capture(ConsolidatedPosition position, Piece piece, int c, int r) {
      Square candidateSquare = new Square(c, r);
      Piece capturedPiece = position.getPieceInSquare(candidateSquare);
      if (capturedPiece != null && capturedPiece.getColor() != piece.getColor()) {
        return Move.capture(piece, candidateSquare, capturedPiece.getFigure());
      }
      return null;
    }

    private List<Move> queening(ConsolidatedPosition position, Piece piece, int c, int r) {
      Square candidateSquare = new Square(c, r);
      if (!position.isSquareOccupiedByMovingColor(candidateSquare)
          && !position.isSquareOccupiedByOppositeColor(candidateSquare)) {
        return Stream.of(Figure.values())
            .map(figure -> Move.simple(Piece.of(figure, piece.getSquare(), piece.getColor()), candidateSquare))
            .collect(Collectors.toList());
      }
      return new ArrayList<>(0);
    }

    private List<Move> queeningCapture(ConsolidatedPosition position, Piece piece, int c, int r) {
      Square candidateSquare;
      Piece capturedPiece;
      candidateSquare = new Square(c, r);

      capturedPiece = position.getPieceInSquare(candidateSquare);
      if (capturedPiece != null && capturedPiece.getColor() != piece.getColor()) {
        return Stream.of(Figure.values())
            .map(figure -> Move.capture(
                Piece.of(figure, piece.getSquare(), piece.getColor())
                , candidateSquare, capturedPiece.getFigure()))
            .collect(Collectors.toList());
      }
      return new ArrayList<>(0);
    }


  }
  private static class King extends MoveCalculator {

    private static volatile King mInstance;

    private King() {
    }

    public static King getInstance() {
      if (mInstance == null) {
        synchronized (King.class) {
          if (mInstance == null) {
            mInstance = new King();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      int r = square.getRow();
      int c = square.getColumn();

      return Stream.of(
          new Square(c-1, r-1),
          new Square(c-1, r),
          new Square(c-1, r+1),
          new Square(c, r-1),
          new Square(c, r+1),
          new Square(c+1, r-1),
          new Square(c+1, r),
          new Square(c+1, r+1)
      ).filter(candidateSquare -> !position.isSquareOccupiedByMovingColor(candidateSquare))
          .map(candidateSquare -> {
            Piece capturedPiece = position.getPieceInSquare(candidateSquare);
            if (capturedPiece == null) {
              return Move.simple(piece, candidateSquare);
            } else {
              return Move.capture(piece, candidateSquare, capturedPiece.getFigure());
            }
          }).collect(Collectors.toList());

    }

  }
  private static class Rook extends MoveCalculator {

    private static volatile Rook mInstance;

    private Rook() {
    }

    public static Rook getInstance() {
      if (mInstance == null) {
        synchronized (Rook.class) {
          if (mInstance == null) {
            mInstance = new Rook();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      List<Move> result = new ArrayList<>(14);
      int r = square.getRow();
      int c = square.getColumn();

      for (int i = r + 1; i < Square.BOARD_SIZE; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c, i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = r - 1; i >= 0; i--) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c, i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = c + 1; i < Square.BOARD_SIZE; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, i, r);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = c - 1; i >= 0; i--) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, i, r);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }

      return result;
    }

  }
  private static class Bishop extends MoveCalculator {

    private static volatile Bishop mInstance;

    private Bishop() {
    }

    public static Bishop getInstance() {
      if (mInstance == null) {
        synchronized (Bishop.class) {
          if (mInstance == null) {
            mInstance = new Bishop();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      List<Move> result = new ArrayList<>(14);
      int r = square.getRow();
      int c = square.getColumn();

      for (int i = 1; i < Square.BOARD_SIZE - r  && i < Square.BOARD_SIZE - c ; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c + i, r + i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = 1; i < Square.BOARD_SIZE - r  && i <= c ; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c - i, r + i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = 1; i <= r  && i < Square.BOARD_SIZE - c ; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c + i, r - i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }
      for (int i = 1; i <= r  && i <= c ; i++) {
        ValueWithBreak<Move> moveWithBreak = expandForRookAndBishop(position, piece, c - i, r - i);

        if (moveWithBreak.value != null) {
          result.add(moveWithBreak.value);
        }
        if (moveWithBreak.breaks) {
          break;
        }
      }

      return result;
    }

  }
  private static class Knight extends MoveCalculator {

    private static volatile Knight mInstance;

    private Knight() {
    }

    public static Knight getInstance() {
      if (mInstance == null) {
        synchronized (Knight.class) {
          if (mInstance == null) {
            mInstance = new Knight();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      int r = square.getRow();
      int c = square.getColumn();

      return Stream.of(
          new Square(c-1, r-2),
      new Square(c-1, r+2),
      new Square(c+1, r-2),
      new Square(c+1, r+2),
      new Square(c-2, r-1),
      new Square(c-2, r+1),
      new Square(c+2, r-1),
      new Square(c+2, r+1)
      ).filter(candidateSquare -> !position.isSquareOccupiedByMovingColor(candidateSquare))
          .map(candidateSquare -> {
            Piece capturedPiece = position.getPieceInSquare(candidateSquare);
            if (capturedPiece == null) {
              return Move.simple(piece, candidateSquare);
            } else {
              return Move.capture(piece, candidateSquare, capturedPiece.getFigure());
            }
          }).collect(Collectors.toList());
    }

  }
  private static class ValueWithBreak<T> {

    private final boolean breaks;
    private final T value;
    public ValueWithBreak(boolean breaks, T value) {
      this.breaks = breaks;
      this.value = value;
    }

  }

  private static ValueWithBreak<Move> expandForRookAndBishop(ConsolidatedPosition position, Piece piece,
                                                      int column, int row) {
    Square candidateSquare = new Square(column, row);
    Piece capturedPiece;
    capturedPiece = position.getPieceInSquare(candidateSquare);

    if (capturedPiece == null) {
      return new ValueWithBreak<>(false, Move.simple(piece, candidateSquare));
    }

    if (capturedPiece.getColor() == piece.getColor()) {
      return new ValueWithBreak<>(true, null);
    } else {
      return new ValueWithBreak<>(true,
          Move.capture(piece, candidateSquare, capturedPiece.getFigure()));
    }
  }
}
