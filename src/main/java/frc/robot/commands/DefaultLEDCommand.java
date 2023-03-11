package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;

public class DefaultLEDCommand extends CommandBase {
  private final LEDSubsystem led;
  
  public DefaultLEDCommand(LEDSubsystem led) {
    this.led = led;
    addRequirements(led);
  }

  @Override public void execute() {
    led.rainbow();
  } 
}