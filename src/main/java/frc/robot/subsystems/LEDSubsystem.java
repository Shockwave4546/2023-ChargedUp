package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LED;

/*
 * 
 */
public class LEDSubsystem extends SubsystemBase {
  private final ShuffleboardTab tab = Shuffleboard.getTab("LEDSubsystem");
  private final AddressableLED led = new AddressableLED(LED.LED_PORT);
  private final AddressableLEDBuffer buffer = new AddressableLEDBuffer(60);
  private int rainbowFirstPixelHue;

  /**
   * 
   */
  public LEDSubsystem() {
    led.setLength(buffer.getLength());
    // setStaticColor(buffer, LED.SHOCKWAVE_GREEN);
    led.setData(buffer);
    led.start();
  }

  /*
   * 
   */
  @Override public void periodic() {
    rainbow();
    led.setData(buffer);
  }

  /**
   * Source: https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html
   */
  private void rainbow() {
    for (var i = 0; i < buffer.getLength(); i++) {
      final var hue = (rainbowFirstPixelHue + (i * 180 / buffer.getLength())) % 180;
      buffer.setHSV(i, hue, 255, 128);
    }

    rainbowFirstPixelHue += 3;
    rainbowFirstPixelHue %= 180;
  }

  /*
   * 
   */
  private static void setStaticColor(AddressableLEDBuffer buffer, Color color) {
    for (var i = 0; i < buffer.getLength(); i++) {
      buffer.setLED(i, color);
    }
  }
}