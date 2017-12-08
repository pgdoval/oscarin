package com.dovaleac.chessai.core;

import com.dovaleac.chessai.core.pieces.Piece;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionFactory {

  public Position initialPosition() {
    List<Piece> pieces = Stream.of(
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
    ).map(notation -> Piece.fromNotation(notation, Color.WHITE))
        .collect(Collectors.toList());

    pieces.addAll(Stream.of(
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
    ).map(notation -> Piece.fromNotation(notation, Color.BLACK))
        .collect(Collectors.toList()));

    return Position.fromPieces(pieces, new ArrayDeque<>(), Color.WHITE,
        PositionStatus.initialStatus());
  }
}
