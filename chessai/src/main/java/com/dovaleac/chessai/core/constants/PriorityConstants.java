package com.dovaleac.chessai.core.constants;

public interface PriorityConstants {

  double getCheckPriority();

  double getCaptureMultiplier();

  double getCapturerMultiplier();

  double getCaptureThreatMultiplier();

  double getCapturerThreatMultiplier();

  double getForkLessValuableMultiplier();

  double getForkThreatenerMultiplier();

  double getSkewedPieceMultiplier();

  double getPieceBehindSkewerMultiplier();

  double getSkeweringPieceMultiplier();

  double getNonePriority();
}
