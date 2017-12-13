package com.dovaleac.chessai.core;

import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionFactory {

  public static Position initialPosition() {
    List<String> white = Arrays.asList(
        "Ra1",
        "Nb1",
        "Bc1",
        "Qd1",
        "Ke1",
        "Bf1",
        "Ng1",
        "Rh1",
        "a2",
        "b2",
        "c2",
        "d2",
        "e2",
        "f2",
        "g2",
        "h2"
    );

    List<String> black = Arrays.asList(
        "Ra8",
        "Nb8",
        "Bc8",
        "Qd8",
        "Ke8",
        "Bf8",
        "Ng8",
        "Rh8",
        "a7",
        "b7",
        "c7",
        "d7",
        "e7",
        "f7",
        "g7",
        "h7"
    );

    return fromPieces(white, black, new ArrayDeque<>(), Color.WHITE, PositionStatus.initialStatus());
  }

  public static Position fromString(String position) {
    String [] positionElements = position.split("#");
    
    List<String> white = Arrays.asList(positionElements[0].split(","));
    List<String> black = Arrays.asList(positionElements[1].split(","));
    Deque<Position.PositionRewritingInfo> moves = new ArrayDeque<>();
    Color turn = Objects.equals(positionElements[3], "b") ? Color.BLACK : Color.WHITE;
    PositionStatus status = positionElements.length == 4
        ? PositionStatus.initialStatus()
        : PositionStatus.fromString(positionElements[4]);

    return fromPieces(white, black, moves, turn, status);
    
  }

  private static Position fromPieces(List<String> white, List<String> black, Deque<Position.PositionRewritingInfo> moves,
                                     Color turn, PositionStatus status) {
    List<Piece> pieces = white.stream().map(notation -> Piece.fromNotation(notation, Color.WHITE))
        .collect(Collectors.toList());

    pieces.addAll(black.stream().map(notation -> Piece.fromNotation(notation, Color.BLACK))
        .collect(Collectors.toList()));

    return Position.fromPieces(pieces, moves, turn, status);    
  }
}
