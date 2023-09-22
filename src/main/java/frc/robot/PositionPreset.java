package frc.robot;

public enum PositionPreset {
  HUMAN_PLAYER_PICKUP_CONE(90.0F, 25.0F),
  HUMAN_PLAYER_PICKUP_CUBE(106.0F, 25.0F),
  FLOOR_CONE(0.0F, 75.0F),
  FLOOR_CUBE(0.0F, 80.0F),
  HIGH_CONE(105.0F, 27.0F),
  MID_CONE(109.0F, 20.0F),
  CUBE(90F, 40.0F),
  STARTING(0.0F, 0.0F),
  DRIVING(60.0F, 0.0F);
  
  public final float upperPivotAngle;
  public final float winchAngle;

  PositionPreset(float upperPivotAngle, float winchAngle) {
    this.upperPivotAngle = upperPivotAngle;
    this.winchAngle = winchAngle;
  }
}