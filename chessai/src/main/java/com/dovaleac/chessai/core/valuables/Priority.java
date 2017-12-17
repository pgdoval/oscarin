package com.dovaleac.chessai.core.valuables;

import com.dovaleac.chessai.core.constants.impl.PriorityConstantsImpl;
import com.dovaleac.chessai.core.pieces.Figure;

@FunctionalInterface
public interface Priority {
  double getValue();

  static Priority check() {
    return Check.getInstance();
  }

  static Priority capture(Figure captured, Figure capturer) {
    return new Capture(captured, capturer);
  }

  static Priority captureThreat(Figure captured, Figure capturer) {
    return new CaptureThreat(captured, capturer);
  }

  static Priority fork(Figure secondBiggestCapturable, Figure capturer) {
    return new Fork(secondBiggestCapturable, capturer);
  }

  static Priority skewer(Figure skewedPiece, Figure pieceBehindSkewer, Figure capturer) {
    return new Skewer(skewedPiece, pieceBehindSkewer, capturer);
  }

  static Priority none() {
    return None.getInstance();
  }


  class Check implements Priority {

    private static volatile Check mInstance;

    private Check() {
    }

    public static Check getInstance() {
      if (mInstance == null) {
        synchronized (Check.class) {
          if (mInstance == null) {
            mInstance = new Check();
          }
        }
      }
      return mInstance;
    }

    @Override
    public double getValue() {
      return PriorityConstantsImpl.getInstance().getCheckPriority();
    }
  }

  class Capture implements Priority {

    private final Figure captured;
    private final Figure capturer;

    private Capture(Figure captured, Figure capturer) {
      this.captured = captured;
      this.capturer = capturer;
    }

    @Override
    public double getValue() {
      return (captured.getBaseValue() * PriorityConstantsImpl.getInstance().getCaptureMultiplier()) +
          (capturer.getBaseValue() * PriorityConstantsImpl.getInstance().getCapturerMultiplier());
    }

  }
  class CaptureThreat implements Priority {

    private final Figure captured;
    private final Figure capturer;

    public CaptureThreat(Figure captured, Figure capturer) {
      this.captured = captured;
      this.capturer = capturer;
    }

    @Override
    public double getValue() {
      return (captured.getBaseValue() * PriorityConstantsImpl.getInstance().getCaptureThreatMultiplier()) +
          (capturer.getBaseValue() * PriorityConstantsImpl.getInstance().getCapturerThreatMultiplier());
    }

  }
  class Fork implements Priority {

    private final Figure secondBiggestCapturable;
    private final Figure capturer;

    public Fork(Figure secondBiggestCapturable, Figure capturer) {
      this.secondBiggestCapturable = secondBiggestCapturable;
      this.capturer = capturer;
    }

    @Override
    public double getValue() {
      return (secondBiggestCapturable.getBaseValue() * PriorityConstantsImpl.getInstance().getForkLessValuableMultiplier()) +
          (capturer.getBaseValue() * PriorityConstantsImpl.getInstance().getForkThreatenerMultiplier());
    }

  }
  class Skewer implements Priority {

    private final Figure skewedPiece;
    private final Figure pieceBehindSkewer;
    private final Figure capturer;

    public Skewer(Figure skewedPiece, Figure pieceBehindSkewer, Figure capturer) {
      this.skewedPiece = skewedPiece;
      this.pieceBehindSkewer = pieceBehindSkewer;
      this.capturer = capturer;
    }

    @Override
    public double getValue() {
      return (skewedPiece.getBaseValue() * PriorityConstantsImpl.getInstance().getSkewedPieceMultiplier()) +
          (pieceBehindSkewer.getBaseValue() * PriorityConstantsImpl.getInstance().getPieceBehindSkewerMultiplier())+
          (capturer.getBaseValue() * PriorityConstantsImpl.getInstance().getSkeweringPieceMultiplier());
    }

  }

  class None implements Priority {

    private static volatile None mInstance;

    private None() {
    }

    public static None getInstance() {
      if (mInstance == null) {
        synchronized (None.class) {
          if (mInstance == null) {
            mInstance = new None();
          }
        }
      }
      return mInstance;
    }

    @Override
    public double getValue() {
      return PriorityConstantsImpl.getInstance().getNonePriority();
    }
  }
}
