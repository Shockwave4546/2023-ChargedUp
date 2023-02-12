package frc.robot.utils.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardSpeed extends ShuffleboardDouble {
  public ShuffleboardSpeed(ShuffleboardTab tab, String name, double def) {
    super(tab, name, def);
    withMinMax(-1.0, 1.0);
  }

  public ShuffleboardSpeed(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  public ShuffleboardSpeed(String name, double def) {
    this(Tab.MISC, name, def);
  }

  public ShuffleboardSpeed(String name) {
    this(Tab.MISC, name, DEFAULT_VALUE);
  }
} 