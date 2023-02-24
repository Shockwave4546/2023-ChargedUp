package frc.robot.utils.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * 
 */
public class ShuffleboardVoltage extends ShuffleboardDouble {
  /**
   * @param tab
   * @param name
   * @param def
   */
  public ShuffleboardVoltage(ShuffleboardTab tab, String name, double def) {
    super(tab, name, def);
    withMinMax(-12.0, 12.0);
  }

  /**
   * @param tab
   * @param name
   */
  public ShuffleboardVoltage(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  /**
   * @param name
   * @param def
   */
  public ShuffleboardVoltage(String name, double def) {
    this(GlobalTab.DEBUG, name, def);
  }

  /**
   * @param name
   */
  public ShuffleboardVoltage(String name) {
    this(GlobalTab.DEBUG, name, DEFAULT_VALUE);
  }
}