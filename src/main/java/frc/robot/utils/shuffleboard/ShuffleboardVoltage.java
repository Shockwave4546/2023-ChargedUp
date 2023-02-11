package frc.robot.utils.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardVoltage extends ShuffleboardDouble {
  public ShuffleboardVoltage(ShuffleboardTab tab, String name, double def) {
    super(tab, name, def);
    withMinMax(-12.0, 12.0);
  }

  public ShuffleboardVoltage(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  public ShuffleboardVoltage(String name, double def) {
    this(Tab.MISC, name, def);
  }
}