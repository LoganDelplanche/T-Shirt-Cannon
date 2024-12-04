// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class turretConstants {
    public static final boolean pidEnabled = true;
    public static final int traverseEncoderPort = 0; //TODO: Set Ports and ID
    public static final int tiltCANcoderID = 0;
    public static final int traverseMotorPort = 0;
    public static final int tiltMotorPort = 1;

    public static final double tiltCANcoderOffset = 0; //TODO: Set Offsets
    public static final double traverseEncoderOffest = 0;

    public static final double traversekP = 1.0; //TODO: Set traverse PID values
    public static final double traversekI = 1.0;
    public static final double traversekD = 1.0;

    public static final double tiltkP = 1.0; //TODO: Set tilt PID values
    public static final double tiltkI = 1.0;
    public static final double tiltkD = 1.0;

    public static final double traverseMaxAngle = 45; //Degrees
    public static final double traverseMinAngle = -45; //Degrees

    public static final double tiltMaxAngle = 45; //Degrees
    public static final double tiltMinAngle = 0; //Degrees
  }

  public static class cannonConstants {
    public static enum statusStates{ //Using an enum because it improves readability. 
      READYTOSHOOT, 
      SHOOTING, 
      EMPTY
    }
    
    public static final double waitTime = 0.25; //The time to keep the solenoid open in seconds
    public static final int numberOfShooters = 6;
    public static final int[] cannonPorts = {1, 2, 3, 4, 5, 6};
    public static final boolean[] loadedBarrels = {true, true, true, 
                                                 true, true, true};
  }

  
  public static class drivetrainConstants {
    public static final int[] motorPorts = {0, 1, 2, 3};
    public static final Boolean[] motorInverts = {true, false}; 
  }

}
