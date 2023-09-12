package frc.robot.utils.shuffleboard;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/**
 *
 */
public class ShuffleboardFloat implements ShuffleboardValue {
  protected static final float DEFAULT_VALUE = 0.0F;
  private final SimpleWidget widget;
  private final float def;

  /**
   * @param tab
   * @param name
   * @param def
   */
  public ShuffleboardFloat(ShuffleboardTab tab, String name, float def) {
    this.widget = tab.add(name, def);
    this.def = def;
  }

  /**
   * @param tab
   * @param name
   */
  public ShuffleboardFloat(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  /**
   * @param name
   * @param def
   */
  public ShuffleboardFloat(String name, float def) {
    this(GlobalTab.DEBUG, name, def);
  }

  /**
   * @param name
   */
  public ShuffleboardFloat(String name) {
    this(GlobalTab.DEBUG, name, DEFAULT_VALUE);
  }

  /**
   * @param length
   * @param width
   * @return
   */
  public ShuffleboardFloat withSize(int length, int width) {
    widget.withSize(length, width);
    return this;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public ShuffleboardFloat withPosition(int x, int y) {
    widget.withPosition(x, y);
    return this;
  }

  /**
   * @param min
   * @param max
   * @return
   */
  public ShuffleboardFloat withMinMax(float min, float max) {
    widget.withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", String.valueOf(min), "max", String.valueOf(max)));
    return this;
  }

  /**
   * @return
   */
  public float get() {
    return widget.getEntry().getFloat(def);
  }

  /**
   * @param value
   */
  public void set(float value) {
    widget.getEntry().setFloat(value);
  }

  /* (non-Javadoc)
   * @see frc.robot.utils.shuffleboard.ShuffleboardValue#getRaw()
   */
  @Override public GenericEntry getRaw() {
    return widget.getEntry();
  }
}