package frc.robot.utils.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardSpeed extends ShuffleboardDouble {
  /**
   * @param tab
   * @param name
   * @param def
   */
  public ShuffleboardSpeed(ShuffleboardTab tab, String name, double def) {
    super(tab, name, def);
    withMinMax(-1.0, 1.0);
  }

  /**
   * @param tab
   * @param name
   */
  public ShuffleboardSpeed(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  /**
   * @param name
   * @param def
   */
  public ShuffleboardSpeed(String name, double def) {
    this(GlobalTab.DEBUG, name, def);
  }

  /**
   * @param name
   */
  public ShuffleboardSpeed(String name) {
    this(GlobalTab.DEBUG, name, DEFAULT_VALUE);
  }
} 