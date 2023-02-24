package frc.robot.subsystems;

import static frc.robot.utils.Utils.configureSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Intake;
import frc.robot.commands.HoldGamePieceCommand;
import frc.robot.utils.shuffleboard.DebugMotorCommand;

/**
 * Represents the intake of our robot, responsible for picking up and releasing of cubes and cones.
 */
public class IntakeSubsystem extends SubsystemBase {
  /**
   * Represents an individual game piece, which is stored to dynamically adjust the direction of the intake. 
   */
  public enum GamePiece {
    CONE, CUBE, NOTHING
  }

  private final ShuffleboardTab tab = Shuffleboard.getTab("IntakeSubsystem");
  private final CANSparkMax intakeMotor = configureSparkMax(new CANSparkMax(Intake.MOTOR_ID, MotorType.kBrushless));
  private GamePiece lastGamePiece = GamePiece.NOTHING;

  /**
   * Sets the default command to hold the game piece, as otherwise, gravity will slowly make it fall out.
   */
  public IntakeSubsystem() {
    intakeMotor.restoreFactoryDefaults();

    setDefaultCommand(new HoldGamePieceCommand(this));
    
    new DebugMotorCommand(tab, "Debug Intake", intakeMotor, this);
  }

  /**
   * The function used in the default command, responsible for keeping the game piece within the intake.
   */
  public void holdGamePiece() {
    if (lastGamePiece == GamePiece.NOTHING) return;
    setRawSpeed((lastGamePiece == GamePiece.CONE ? -1.0 : 1.0) * Intake.HOLD_SPEED);
  }

  /**
   * Depending on what game piece is being picked up, the intake has to either spin forward or backward.
   * Corresponding controller buttons must be mapped to handle these two inputs.
   * Additionally, the game piece gets cached to be used in other functions.
   * 
   * @param gamePiece the game piece being picked up
   */
  public void pickUpGamePiece(GamePiece gamePiece) {
    this.lastGamePiece = gamePiece;
    setRawSpeed((lastGamePiece == GamePiece.CONE ? -1.0 : 1.0) * Intake.PICK_UP_SPEED);
  }

  /**
   * Depending on what game piece is being released, the intake has to either spin forward or backward.
   * Only one controller button must be mapped for this since during the pick up, the current game piece
   * will get cached and used in this function.
   */
  public void releaseGamePiece() {
    setRawSpeed((lastGamePiece == GamePiece.CONE ? 1.0 : -1.0) * Intake.RELEASE_SPEED);
  }

  /**
   * Note: this should never get called since an abstract override should be utilized instead.
   * Sets the speed of the intake.
   * 
   * @param speed [-1.0 to 1.0] the speed to spin the motor
   */
  private void setRawSpeed(double speed) {
    intakeMotor.set(speed);
  }

  /**
   * Stops the intake.
   */
  public void stop() {
    intakeMotor.stopMotor();
  }
}