package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Represents the vision on the robot, responsible for locating and tracking certain field elements, such as AprilTags and reflective tape. 
 */
public class VisionSubsystem extends SubsystemBase {
  // TODO: Fill in the camera name, which would be in NT
  private final PhotonCamera camera = new PhotonCamera("cameraName");

}