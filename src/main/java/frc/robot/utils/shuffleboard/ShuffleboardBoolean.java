package frc.robot.utils.shuffleboard;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class ShuffleboardBoolean implements ShuffleboardValue {
  private static final boolean DEFAULT_VALUE = false;
  private final SimpleWidget widget;
  private final boolean def;

  public ShuffleboardBoolean(ShuffleboardTab tab, String name, boolean def) {
    this.widget = tab.add(name, def);
    this.def = def;
    widget.withWidget(BuiltInWidgets.kToggleButton);
  }

  public ShuffleboardBoolean(ShuffleboardTab tab, String name) {
    this(tab, name, DEFAULT_VALUE);
  }

  public ShuffleboardBoolean(String name, boolean def) {
    this(Tab.MISC, name, def);
  }

  public ShuffleboardBoolean withSize(int length, int width) {
    widget.withSize(length, width);
    return this;
  }

  public ShuffleboardBoolean withPosition(int x, int y) {
    widget.withPosition(x, y);
    return this;
  }

  public boolean get() {
    return widget.getEntry().getBoolean(def);
  }

  @Override public GenericEntry getRaw() {
    return widget.getEntry();
  }
}