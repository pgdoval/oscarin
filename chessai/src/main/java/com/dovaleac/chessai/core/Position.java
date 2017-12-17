package com.dovaleac.chessai.core;

import com.dovaleac.chessai.core.moves.Move;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Position {

  private Map<Square, Piece> piecesBySquare;
  private Map<Color, List<Piece>> piecesByColor;
  private Deque<PositionRewritingInfo> moves;
  private Color turn;
  private PositionStatus currentStatus;

  public Position(Map<Square, Piece> piecesBySquare, Map<Color,
      List<Piece>> piecesByColor, Deque<PositionRewritingInfo> moves, Color turn,
                  PositionStatus currentStatus) {
    this.piecesBySquare = piecesBySquare;
    this.piecesByColor = piecesByColor;
    this.moves = moves;
    this.turn = turn;
    this.currentStatus = currentStatus;
  }

  public static Position fromPieces(List<Piece> pieces, Deque<PositionRewritingInfo> moves,
                                    Color turn, PositionStatus currentStatus) {
    Map<Square, Piece> piecesBySquare =
        pieces.stream().collect(Collectors.toMap(Piece::getSquare, Function.identity()));

    Map<Color, List<Piece>> piecesByColor =
        pieces.stream().collect(Collectors.groupingBy(Piece::getColor));

    return new Position(piecesBySquare, piecesByColor, moves, turn, currentStatus);
  }

  public Position move(Move move){
    if (move == null) {
      return this;
    }
    PositionStatus newStatus = move.createNewStatus(currentStatus);

    moves.add(new PositionRewritingInfo(move, currentStatus));
    this.currentStatus = newStatus;
    move.consolidateMove(piecesBySquare, piecesByColor);
    turn = turn.flip();
    return this;
  }

  public Position unmove(Move move){
    return this;
  }

  public PositionRewritingInfo getLastMove() {
    if (moves.isEmpty()) {
      return null;
    } else {
      return moves.peek();
    }
  }

  public boolean isSquareOccupiedByMovingColor(Square square) {
    Piece piece = occupyingPiece(square);
    return piece != null && piece.getColor().equals(turn);
  }

  public boolean isSquareOccupiedByOppositeColor(Square square) {
    Piece piece = occupyingPiece(square);
    return piece != null && !piece.getColor().equals(turn);
  }

  private Piece occupyingPiece(Square square) {
    return piecesBySquare.get(square);
  }

  public Piece getPieceInSquare(Square square) {
    return piecesBySquare.get(square);
  }

  public Color getTurn() {
    return turn;
  }

  public int getEnPassant() {
    return currentStatus.getEnPassant();
  }

  public boolean canCastle(Side side, Color color) {
    if (side == Side.KINGSIDE) {
      if (color == Color.WHITE) {
        return currentStatus.isWhiteCanCastleKingside();
      } else {
        return currentStatus.isBlackCanCastleKingside();
      }
    } else {
      if (color == Color.WHITE) {
        return currentStatus.isWhiteCanCastleQueenside();
      } else {
        return currentStatus.isBlackCanCastleQueenside();
      }
    }
  }

  public List<Piece> getPiecesByColor(Color color) {
    return piecesByColor.get(color);
  }

  public class PositionRewritingInfo {
    private final Move move;
    private final PositionStatus status;

    public PositionRewritingInfo(Move move, PositionStatus status) {
      this.move = move;
      this.status = status;
    }

    public Move getMove() {
      return move;
    }

    public PositionStatus getStatus() {
      return status;
    }
  }
}
