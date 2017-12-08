package com.dovaleac.chessai.core;

import com.dovaleac.chessai.core.moves.Move;
import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ConsolidatedPosition {

  private Map<Square, Piece> piecesBySquare;
  private Map<Color, List<Piece>> piecesByColor;
  private Deque<PositionRewritingInfo> moves;
  private Color turn;
  private PositionStatus currentStatus;

  public ConsolidatedPosition(Map<Square, Piece> piecesBySquare, Map<Color,
      List<Piece>> piecesByColor, Deque<PositionRewritingInfo> moves, Color turn,
                              PositionStatus currentStatus) {
    this.piecesBySquare = piecesBySquare;
    this.piecesByColor = piecesByColor;
    this.moves = moves;
    this.turn = turn;
    this.currentStatus = currentStatus;
  }

  public ConsolidatedPosition move(Move move){
    PositionStatus newStatus = move.createNewStatus(currentStatus);
    PositionRewritingInfo info = new PositionRewritingInfo(move, currentStatus);

    move.consolidateMove(piecesBySquare, piecesByColor);
    turn = turn.flip();
  }

  public ConsolidatedPosition unmove(Move move){
    return this;
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

  private class PositionRewritingInfo {
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
