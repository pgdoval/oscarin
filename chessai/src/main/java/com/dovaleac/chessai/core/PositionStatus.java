package com.dovaleac.chessai.core;

public class PositionStatus {
  private final boolean whiteCanCastleKingside;
  private final boolean whiteCanCastleQueenside;
  private final boolean blackCanCastleKingside;
  private final boolean blackCanCastleQueenside;
  private final int enPassant;

  private PositionStatus(boolean whiteCanCastleKingside, boolean whiteCanCastleQueenside,
                        boolean blackCanCastleKingside, boolean blackCanCastleQueenside,
                        int enPassant) {
    this.whiteCanCastleKingside = whiteCanCastleKingside;
    this.whiteCanCastleQueenside = whiteCanCastleQueenside;
    this.blackCanCastleKingside = blackCanCastleKingside;
    this.blackCanCastleQueenside = blackCanCastleQueenside;
    this.enPassant = enPassant;
  }

  public boolean isWhiteCanCastleKingside() {
    return whiteCanCastleKingside;
  }

  public boolean isWhiteCanCastleQueenside() {
    return whiteCanCastleQueenside;
  }

  public boolean isBlackCanCastleKingside() {
    return blackCanCastleKingside;
  }

  public boolean isBlackCanCastleQueenside() {
    return blackCanCastleQueenside;
  }

  public int getEnPassant() {
    return enPassant;
  }

  public static PositionStatus initialStatus() {
    return new PositionStatus(true, true,
        true, true, -2);
  }

  public static Builder builder(PositionStatus previousPosition) {
    return new Builder(previousPosition);
  }

  public static final class Builder {
    private boolean whiteCanCastleKingside;
    private boolean whiteCanCastleQueenside;
    private boolean blackCanCastleKingside;
    private boolean blackCanCastleQueenside;
    private int enPassant;

    private Builder(PositionStatus previousPosition) {
      this.whiteCanCastleKingside = previousPosition.whiteCanCastleKingside;
      this.blackCanCastleKingside = previousPosition.blackCanCastleKingside;
      this.blackCanCastleQueenside = previousPosition.blackCanCastleQueenside;
      this.whiteCanCastleQueenside = previousPosition.whiteCanCastleQueenside;
      this.enPassant = -2;
    }

    public Builder withoutKingsideCastling(Color color) {
      if (color == Color.WHITE) {
        this.whiteCanCastleKingside = false;
      } else {
        this.blackCanCastleKingside = false;
      }
      return this;
    }

    public Builder withoutQueensideCastling(Color color) {
      if (color == Color.WHITE) {
        this.whiteCanCastleQueenside = false;
      } else {
        this.blackCanCastleQueenside = false;
      }
      return this;
    }

    public Builder withEnPassant(int enPassant) {
      this.enPassant = enPassant;
      return this;
    }

    public PositionStatus build() {
      return new PositionStatus(whiteCanCastleKingside, whiteCanCastleQueenside,
          blackCanCastleKingside, blackCanCastleQueenside, enPassant);
    }
  }
}
