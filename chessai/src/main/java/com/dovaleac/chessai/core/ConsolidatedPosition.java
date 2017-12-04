package com.dovaleac.chessai.core;

import java.util.List;

public class ConsolidatedPosition {

  private final List<Piece> pieces;
  private final Turn turn;
  private final boolean whiteCanCastleKingSide;
  private final boolean whiteCanCastleQueenSide;
  private final boolean blackCanCastleKingSide;
  private final boolean blackCanCastleQueenSide;
  private final List<Square> enPassant;

  public ConsolidatedPosition(List<Piece> pieces, Turn turn, boolean whiteCanCastleKingSide,
                              boolean whiteCanCastleQueenSide, boolean blackCanCastleKingSide,
                              boolean blackCanCastleQueenSide, List<Square> enPassant) {
    this.pieces = pieces;
    this.turn = turn;
    this.whiteCanCastleKingSide = whiteCanCastleKingSide;
    this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
    this.blackCanCastleKingSide = blackCanCastleKingSide;
    this.blackCanCastleQueenSide = blackCanCastleQueenSide;
    this.enPassant = enPassant;
  }

  public ConsolidatedPosition move(Move move){
    return this;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public Turn getTurn() {
    return turn;
  }

  public boolean isWhiteCanCastleKingSide() {
    return whiteCanCastleKingSide;
  }

  public boolean isWhiteCanCastleQueenSide() {
    return whiteCanCastleQueenSide;
  }

  public boolean isBlackCanCastleKingSide() {
    return blackCanCastleKingSide;
  }

  public boolean isBlackCanCastleQueenSide() {
    return blackCanCastleQueenSide;
  }

  public List<Square> getEnPassant() {
    return enPassant;
  }

  private enum Turn {
    WHITE(true),
    BLACK(false);

    public boolean isInner() {
      return inner;
    }

    private boolean inner;

    Turn(boolean b) {
      inner=b;
    }
  }
}
