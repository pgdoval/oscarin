package com.dovaleac.chessai.core.constants.impl;

import com.dovaleac.chessai.core.constants.PriorityConstants;

public class PriorityConstantsImpl implements PriorityConstants {

  private final double checkPriority = 8.0;
  private final double captureMultiplier = 1.0;
  private final double capturerMultiplier = -0.3;
  private final double captureThreatMultiplier = 0.5;
  private final double capturerThreatMultiplier = -0.15;
  private final double forkLessValuableMultiplier = 0.8;
  private final double forkThreatenerMultiplier = -0.5;
  private final double skewedPieceMultiplier = 0.7;
  private final double pieceBehindSkewerMultiplier = 0.4;
  private final double skeweringPieceMultiplier = -0.2;
  private final double noneMultiplier = 0;

  @Override
  public double getCheckPriority() {
    return checkPriority;
  }

  @Override
  public double getCaptureMultiplier() {
    return captureMultiplier;
  }

  @Override
  public double getCapturerMultiplier() {
    return capturerMultiplier;
  }

  @Override
  public double getCaptureThreatMultiplier() {
    return captureThreatMultiplier;
  }

  @Override
  public double getCapturerThreatMultiplier() {
    return capturerThreatMultiplier;
  }

  @Override
  public double getForkLessValuableMultiplier() {
    return forkLessValuableMultiplier;
  }

  @Override
  public double getForkThreatenerMultiplier() {
    return forkThreatenerMultiplier;
  }

  @Override
  public double getSkewedPieceMultiplier() {
    return skewedPieceMultiplier;
  }

  @Override
  public double getPieceBehindSkewerMultiplier() {
    return pieceBehindSkewerMultiplier;
  }

  @Override
  public double getSkeweringPieceMultiplier() {
    return skeweringPieceMultiplier;
  }

  @Override
  public double getNonePriority() {
    return noneMultiplier;
  }

  public static PriorityConstantsImpl getmInstance() {
    return mInstance;
  }

  private static volatile PriorityConstantsImpl mInstance;

  private PriorityConstantsImpl() {
  }

  public static PriorityConstantsImpl getInstance() {
    if (mInstance == null) {
      synchronized (PriorityConstantsImpl.class) {
        if (mInstance == null) {
          mInstance = new PriorityConstantsImpl();
        }
      }
    }
    return mInstance;
  }
}
