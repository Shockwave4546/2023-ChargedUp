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
 * 
 */
public class IntakeSubsystem extends SubsystemBase {
  public enum GamePiece {
    CONE, CUBE, NOTHING
  }

  private final ShuffleboardTab tab = Shuffleboard.getTab("IntakeSubsystem");
  private final CANSparkMax intakeMotor = configureSparkMax(new CANSparkMax(Intake.MOTOR_ID, MotorType.kBrushless));
  private GamePiece lastGamePiece = GamePiece.NOTHING;

  /**
   * 
   */
  public IntakeSubsystem() {
    intakeMotor.restoreFactoryDefaults();

    setDefaultCommand(new HoldGamePieceCommand(this));
    
    new DebugMotorCommand(tab, "Debug Intake", intakeMotor, this);
  }

  /**
   * 
   */
  public void holdGamePiece() {
    if (lastGamePiece == GamePiece.NOTHING) return;
    setRawSpeed((lastGamePiece == GamePiece.CONE ? -1.0 : 1.0) * Intake.HOLD_SPEED);
  }

  /**
   * @param gamePiece
   */
  public void pickUpGamePiece(GamePiece gamePiece) {
    this.lastGamePiece = gamePiece;
    setRawSpeed((lastGamePiece == GamePiece.CONE ? -1.0 : 1.0) * Intake.PICK_UP_SPEED);
  }

  /**
   * 
   */
  public void releaseGamePiece() {
    setRawSpeed((lastGamePiece == GamePiece.CONE ? 1.0 : -1.0) * Intake.RELEASE_SPEED);
  }

  /**
   * @param speed
   */
  private void setRawSpeed(double speed) {
    intakeMotor.set(speed);
  }

  /**
   * 
   */
  public void stop() {
    intakeMotor.stopMotor();
  }
}