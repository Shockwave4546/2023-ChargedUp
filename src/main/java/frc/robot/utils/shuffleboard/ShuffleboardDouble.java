package frc.robot.utils.shuffleboard;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import java.util.Map;

/**
 * 
 */
public class ShuffleboardDouble implements ShuffleboardValue {
  protected static final double DEFAULT_VALUE = 0.0;
  private final SimpleWidget widget;
  private final double def;

  /**
   * @param tab
   * @param name
   * @param def
   */
  public ShuffleboardDouble(ShuffleboardTab tab, String name, double def) {
    this.widget = tab.add(name, def);
    this.def = def;
  }

  /**
   * @param tab
   * @param name
   */
  public ShuffleboardDouble(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  /**
   * @param name
   * @param def
   */
  public ShuffleboardDouble(String name, double def) {
    this(GlobalTab.DEBUG, name, def);
  }

  /**
   * @param name
   */
  public ShuffleboardDouble(String name) {
    this(GlobalTab.DEBUG, name, DEFAULT_VALUE);
  }

  /**
   * @param length
   * @param width
   * @return
   */
  public ShuffleboardDouble withSize(int length, int width) {
    widget.withSize(length, width);
    return this;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public ShuffleboardDouble withPosition(int x, int y) {
    widget.withPosition(x, y);
    return this;
  }

  /**
   * @param min
   * @param max
   * @return
   */
  public ShuffleboardDouble withMinMax(double min, double max) {
    widget.withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", String.valueOf(min), "max", String.valueOf(max)));
    return this;
  }

  /**
   * @return
   */
  public double get() {
    return widget.getEntry().getDouble(def);
  }

  /**
   * @param value
   */
  public void set(double value) {
    widget.getEntry().setDouble(value);
  }

  /* (non-Javadoc)
   * @see frc.robot.utils.shuffleboard.ShuffleboardValue#getRaw()
   */
  @Override public GenericEntry getRaw() {
    return widget.getEntry();
  }
}