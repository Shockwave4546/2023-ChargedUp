package frc.robot;

public enum PositionPreset {
  HUMAN_PLAYER_PICKUP_CONE(90.0),
  HUMAN_PLAYER_PICKUP_CUBE(106.0),
  FLOOR_CONE(0.0),
  FLOOR_CUBE(0.0),
  HIGH_CONE(105.0),
//  MID_CONE(109.0),
  CUBE(90),
  DRIVING(60.0),

  /**
   * Presets being used
   */

  MID_CONE(120.0),
  STARTING(0.0);

  public final double upperPivotAngle;

  PositionPreset(double upperPivotAngle) {
    this.upperPivotAngle = upperPivotAngle;
  }
}