package frc.robot.subsystems;

import static frc.robot.RobotContainer.PDP;
import static frc.robot.utils.Utils.configureVictorSPX;
import static frc.robot.utils.telemetry.Telemetry.logDouble;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Drive;
import frc.robot.commands.CheesyDriveCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.utils.Utils;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;

/**
 * Represents the Differential Drive used in the Kit Chassis.
 */
public class DriveSubsystem extends SubsystemBase {
  private final ShuffleboardTab tab = Shuffleboard.getTab("DriveSubsystem");
  private final AHRS gyro = new AHRS();
  private final WPI_VictorSPX frontLeftMotor = configureVictorSPX(new WPI_VictorSPX(Drive.FRONT_LEFT_ID));
  private final WPI_VictorSPX backLeftMotor = configureVictorSPX(new WPI_VictorSPX(Drive.BACK_LEFT_ID));
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(frontLeftMotor, backLeftMotor);
  private final WPI_VictorSPX frontRightMotor = configureVictorSPX(new WPI_VictorSPX(Drive.FRONT_RIGHT_ID));
  private final CANSparkMax backRightMotor = Utils.configureSparkMax(new CANSparkMax(Drive.BACK_RIGHT_ID, MotorType.kBrushed));
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(frontRightMotor, backRightMotor);
  private final Encoder leftEncoder = new Encoder(Drive.LEFT_ENCODER[0], Drive.LEFT_ENCODER[1]);
  private final Encoder rightEncoder = new Encoder(Drive.RIGHT_ENCODER[0], Drive.RIGHT_ENCODER[1]);
  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Drive.TRACK_WIDTH_METERS);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
  private final ShuffleboardBoolean useCheesyDrive = new ShuffleboardBoolean(tab, "Use Cheesy Drive?");

  /**
   * Initalizes and configures all the values for Encoders (@see Encoder#setDistancePerPulse) and motors (@see WPI_VictorSPX#setInverted).
   * Additionally, all measurement devices are zeroed in the event of previous data stored on them.
   */
  public DriveSubsystem() {
    rightMotors.setInverted(true);

    leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);

    resetEncoders();
    resetGyro();
    resetOdometry(new Pose2d());

    tab.add("Left Encoder", leftEncoder);
    tab.add("Right Encoder", rightEncoder);
    tab.add("Gyro", gyro);
  }

  /*
   * Updates the odometry assoiciated with the drivetrain.
   * Logs debugging information about motor power, encoder distances, and gyroscope's angle. 
   */
  @Override public void periodic() {
    odometry.update(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
  
    // TODO: fix channels
    logDouble("Front Left Motor Current", PDP.getCurrent(14));
    logDouble("Back Left Motor Current", PDP.getCurrent(13));
    logDouble("Front Right Motor Current", PDP.getCurrent(15));
    logDouble("Back Right Motor Current", PDP.getCurrent(12));

    logDouble("Front Left Motor Voltage", frontLeftMotor.getMotorOutputVoltage());
    logDouble("Back Left Motor Voltage", backLeftMotor.getMotorOutputVoltage());
    logDouble("Front Right Motor Voltage", frontRightMotor.getMotorOutputVoltage());
    logDouble("Back Right Motor Voltage", backRightMotor.getBusVoltage());
    logDouble("Left Encoder Distance", leftEncoder.getDistance());
    logDouble("Right Encoder Distance", rightEncoder.getDistance());
    logDouble("Gyro Angle", gyro.getAngle());
  }

  /**
   * Depending on the preference of the driver, either cheesy drive or regular tank drive can be used in tele-op.
   * In Shuffleboard, a boolean toggle swtich is used to switch between the two.
   * Note: the toggle switch must be clicked before the enabling of tele-op, otherwise, the preference won't apply.
   * 
   * @param controller the controller used to control the robot during tele-operated period.
   */
  public void initTeleop(CommandXboxController controller) {
    setDefaultCommand(useCheesyDrive.get() ? new CheesyDriveCommand(this, controller) : new TankDriveCommand(this, controller));
  }

  /**
   * A typical tank drive function to control the speeds of the left and right side of the drivetrain.
   * 
   * @param leftSpeed [-1.0 to 1.0] the speed of the left side.
   * @param rightSpeed [-1.0 to 1.0] the speed of the right side.
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * A typical tank drive function but voltage is used to control the motors rather than "speed".
   * 
   * @param leftVolts [-12.0 to 12.0] the voltage applied to the left side motors.
   * @param rightVolts [-12.0 to 12.0] the voltage applied to the right side motors.
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(rightVolts);
    drive.feed();
  }

  /**
   * Zeros the encoders.
   */
  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /**
   * Zeros the gyroscope.
   */
  public void resetGyro() {
    gyro.reset();
  }

  /**
   * Resets the odometry (relative position) of the robot.
   * Note: this will zero the encoders and gyroscope as well.
   * 
   * @param pose the pose to reset the odometry to. In most instances, a blank {@link edu.wpi.first.math.geometry.Pose2D} would be used.
   * However, when running multiple autonomous routines, the pose can definetely be set to the current pose as well. 
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    resetGyro();
    odometry.resetPosition(gyro.getRotation2d(), 0, 0, pose);
  }

  /**
   * Returns the average distance traveled by the robot.
   * 
   * @return the average distance of the left and ride encoders in inches.
   */
  public double getAverageDistance() {
    return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
  }

  /**
   * Returns the current pose of the robot.
   * 
   * @return the current pose of the robot in meters.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  /**
   * Returns the kinematics of the drivetrain.
   * 
   * @return the kinematics of the drivetrain.
   */
  public DifferentialDriveKinematics getKinematics() {
    return kinematics;
  }

  /**
   * Returns the wheel speeds of the robot in m/s^2.
   * 
   * @return the current wheel speeds of the robot in m/s^2.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
  }

  /**
   * Stops the drivetrain.
   */
  public void stop() {
    leftMotors.stopMotor();
    rightMotors.stopMotor();
  }
}