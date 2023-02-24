package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * 
 */
public class VisionSubsystem extends SubsystemBase {
  // TODO: Fill in the camera name, which would be in NT
  private final PhotonCamera camera = new PhotonCamera("cameraName");

}