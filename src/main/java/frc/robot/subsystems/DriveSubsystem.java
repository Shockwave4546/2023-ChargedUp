package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Drive;
import frc.robot.commands.CheesyDriveCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;
import frc.robot.utils.telemetry.TelemetricSubsystem;

public class DriveSubsystem extends TelemetricSubsystem {
  private final AHRS gyro = new AHRS();
  private final WPI_VictorSPX frontLeftMotor = configureMotor(new WPI_VictorSPX(Drive.FRONT_LEFT_ID));
  private final WPI_VictorSPX backLeftMotor = configureMotor(new WPI_VictorSPX(Drive.BACK_LEFT_ID));
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(frontLeftMotor, backLeftMotor);
  private final WPI_VictorSPX frontRightMotor = configureMotor(new WPI_VictorSPX(Drive.FRONT_RIGHT_ID));
  private final WPI_VictorSPX backRightMotor = configureMotor(new WPI_VictorSPX(Drive.BACK_RIGHT_ID));
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(frontRightMotor, backRightMotor);
  private final Encoder leftEncoder = new Encoder(Drive.LEFT_ENCODER[0], Drive.LEFT_ENCODER[1]);
  private final Encoder rightEncoder = new Encoder(Drive.RIGHT_ENCODER[0], Drive.RIGHT_ENCODER[1]);
  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Drive.TRACK_WIDTH_METERS);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
  private final ShuffleboardBoolean useCheesyDrive = new ShuffleboardBoolean(telemetryTab, "Use Cheesy Drive?");

  public DriveSubsystem() {
    leftMotors.setInverted(true);

    leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);

    resetEncoders();
    resetGyro();
    resetOdometry(new Pose2d());
  }

  @Override public void telemetryInit() {
    addTelemetry("Left Encoder", leftEncoder);
    addTelemetry("Right Encoder", rightEncoder);
    addTelemetry("Gyro", gyro);
  }

  // TODO: check PDP channels
  @Override public void telemetryPeriodic() {
    try (final var pdp = new PowerDistribution()) {
      logDouble("Front Left Motor Current", pdp.getCurrent(0));
      logDouble("Back Left Motor Current", pdp.getCurrent(1));
      logDouble("Front Right Motor Current", pdp.getCurrent(2));
      logDouble("Back Right Motor Current", pdp.getCurrent(3));
    }

    logDouble("Front Left Motor Voltage", frontLeftMotor.getMotorOutputVoltage());
    logDouble("Back Left Motor Voltage", backLeftMotor.getMotorOutputVoltage());
    logDouble("Front Right Motor Voltage", frontRightMotor.getMotorOutputVoltage());
    logDouble("Back Right Motor Voltage", backRightMotor.getMotorOutputVoltage());
    logDouble("Left Encoder Distance", leftEncoder.getDistance(), false, true);
    logDouble("Right Encoder Distance", rightEncoder.getDistance(), false, true);
    logDouble("Gyro Angle", gyro.getAngle(), false, true);
  }

  @Override public void periodic() {
    super.periodic();
    odometry.update(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
  }

  private WPI_VictorSPX configureMotor(WPI_VictorSPX motor) {
    motor.setNeutralMode(NeutralMode.Brake);
    return motor;
  }

  public void initTeleop(CommandXboxController controller) {
    setDefaultCommand(useCheesyDrive.get() ? new CheesyDriveCommand(this, controller) : new TankDriveCommand(this, controller));
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(rightVolts);
    drive.feed();
  }

  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    resetGyro();
    odometry.resetPosition(gyro.getRotation2d(), 0, 0, pose);
  }

  public double getAverageDistance() {
    return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public DifferentialDriveKinematics getKinematics() {
    return kinematics;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
  }

  public void stop() {
    leftMotors.stopMotor();
    rightMotors.stopMotor();
  }
}