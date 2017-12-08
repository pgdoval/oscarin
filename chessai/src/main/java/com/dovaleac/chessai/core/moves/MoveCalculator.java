package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.ConsolidatedPosition;
import com.dovaleac.chessai.core.Side;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class MoveCalculator {

  public abstract List<Move> calculateMoves(ConsolidatedPosition position, Square square,
                                            Piece piece);
  public abstract boolean isAttacking(ConsolidatedPosition position, Square targetSquare,
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
  private static class King extends ComposedMoveCalculator {

    private static volatile King mInstance;

    public King() {
      super(SimpleKing.getInstance(), Castling.getInstance());
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      return type1.isAttacking(position, targetSquare, piece)
          || type2.isAttacking(position, targetSquare, piece);
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square square, Piece piece) {
      int squareColumn = square.getColumn();
      int pieceColumn = piece.getSquare().getColumn();

      if (squareColumn != pieceColumn + 1 && squareColumn != pieceColumn - 1) {
        return false;
      }

      int pieceRow = piece.getSquare().getRow();
      int squareRow = square.getRow();
      return squareRow == pieceRow + (piece.getColor() == Color.WHITE ? 1 : -1) ||
          (squareColumn == position.getEnPassant() && pieceRow == squareRow);

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
  private static class SimpleKing extends MoveCalculator {

    private static volatile SimpleKing mInstance;

    private SimpleKing() {
    }

    public static SimpleKing getInstance() {
      if (mInstance == null) {
        synchronized (SimpleKing.class) {
          if (mInstance == null) {
            mInstance = new SimpleKing();
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      int squareColumn = targetSquare.getColumn();
      int pieceColumn = piece.getSquare().getColumn();
      int pieceRow = piece.getSquare().getRow();
      int squareRow = targetSquare.getRow();

      List<Integer> ranges = Arrays.asList(-1, 0, 1);

      return (ranges.contains(squareColumn - pieceColumn)
          && ranges.contains(squareRow - pieceRow)
          && !(squareColumn == pieceColumn && squareRow == pieceRow));

    }

  }
  private static class Castling extends MoveCalculator {

    private static volatile Castling mInstance;

    private Castling() {
    }

    public static Castling getInstance() {
      if (mInstance == null) {
        synchronized (Castling.class) {
          if (mInstance == null) {
            mInstance = new Castling();
          }
        }
      }
      return mInstance;
    }

    @Override
    public List<Move> calculateMoves(ConsolidatedPosition position, Square square, Piece piece) {
      final Color color = piece.getColor();

      return Stream.of(Side.values())
          .filter(side -> position.canCastle(side, color)
              && areSquaresNecessaryForCastlingFree(position, side, color))
          .map(side -> Move.castling(side, color))
          .collect(Collectors.toList());
    }

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      return false;
    }

    private boolean areSquaresNecessaryForCastlingFree(ConsolidatedPosition position, Side side,
                                                       Color castlingColor) {
      int firstRow = castlingColor == Color.WHITE ? 0 : 7;
      Stream<Square> necessarySquares = side == Side.KINGSIDE
          ? Stream.of(new Square(4, firstRow), new Square(5, firstRow), new Square(6, firstRow))
          : Stream.of(new Square(4, firstRow), new Square(3, firstRow),
          new Square(2, firstRow), new Square(1, firstRow));

      return necessarySquares.noneMatch(square -> position.isSquareOccupiedByMovingColor(square)
          || position.isSquareOccupiedByOppositeColor(square)
          || position.getPiecesByColor(castlingColor).stream()
          .noneMatch(piece -> piece.isAttacking(position, square)));
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      int squareColumn = targetSquare.getColumn();
      int pieceColumn = piece.getSquare().getColumn();
      int squareRow = targetSquare.getRow();
      int pieceRow = piece.getSquare().getRow();

      if (squareColumn == pieceColumn) {
        int rowDiff = squareRow - pieceRow;
        if (rowDiff == 0) {
          return false;
        }
        if (rowDiff > 0) {
          return IntStream.range(pieceRow + 1, squareRow).noneMatch(row ->
              position.getPieceInSquare(new Square(squareColumn, row)) != null);
        } else {
          return IntStream.range(squareRow + 1, pieceRow).noneMatch(row ->
              position.getPieceInSquare(new Square(squareColumn, row)) != null);
        }
      }

      if (squareRow == pieceRow) {
        int columnDiff = squareColumn - pieceColumn;
        if (columnDiff == 0) {
          return false;
        }
        if (columnDiff > 0) {
          return IntStream.range(pieceColumn + 1, squareColumn).noneMatch(column ->
              position.getPieceInSquare(new Square(squareRow, column)) != null);
        } else {
          return IntStream.range(squareColumn + 1, pieceColumn).noneMatch(column ->
              position.getPieceInSquare(new Square(squareRow, column)) != null);
        }
      }
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      int squareColumn = targetSquare.getColumn();
      int pieceColumn = piece.getSquare().getColumn();
      int squareRow = targetSquare.getRow();
      int pieceRow = piece.getSquare().getRow();

      int rowDiff = squareRow - pieceRow;
      int columnDiff = squareColumn - pieceColumn;


      if (rowDiff == columnDiff) {
        if (rowDiff > 0) {
          return IntStream.range(1, rowDiff).noneMatch(i ->
              position.getPieceInSquare(new Square(pieceColumn + i, pieceRow + i)) != null);
        } else {
          return IntStream.range(1, -rowDiff).noneMatch(i ->
              position.getPieceInSquare(new Square(pieceColumn - i, pieceRow - i)) != null);
        }
      }

      if (rowDiff == -columnDiff) {
        if (rowDiff > 0) {
          return IntStream.range(1, rowDiff).noneMatch(i ->
              position.getPieceInSquare(new Square(pieceColumn - i, pieceRow + i)) != null);
        } else {
          return IntStream.range(1, columnDiff).noneMatch(i ->
              position.getPieceInSquare(new Square(pieceColumn + i, pieceRow - i)) != null);
        }
      }

      return false;
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

    @Override
    public boolean isAttacking(ConsolidatedPosition position, Square targetSquare, Piece piece) {
      int squareColumn = targetSquare.getColumn();
      int pieceColumn = piece.getSquare().getColumn();
      int pieceRow = piece.getSquare().getRow();
      int squareRow = targetSquare.getRow();

      int rowDiff = Math.abs(squareRow - pieceRow);
      int columnDiff = Math.abs(squareColumn - pieceColumn);

      return (rowDiff == 2 && columnDiff == 1)
          || (rowDiff == 1 && columnDiff == 2);
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
