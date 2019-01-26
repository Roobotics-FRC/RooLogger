package frc.team4373.robot;

/**
 * Holds various mappings and constants.
 *
 * @author Samasaur
 * @author aaplmath
 */

public class RobotMap {
    // Inversions
    public static boolean CLIMBER_MOTOR_INVERSION = false;
    public static boolean INTAKE_LEFT_INVERSION = false;
    public static boolean INTAKE_RIGHT_INVERSION = false;

    // OI devices
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    // Motor ports
    public static final int CLIMBER_MOTOR = 351;
    public static final int INTAKE_MOTOR_RIGHT = 411;
    public static final int INTAKE_MOTOR_LEFT = 421;

    // Pneumatic ports
    public static final int CLIMBER_PISTON_FRONT_FORWARD = 6;
    public static final int CLIMBER_PISTON_FRONT_BACKWARD = 7;
    public static final int CLIMBER_PISTON_REAR_FORWARD = 0;
    public static final int CLIMBER_PISTON_REAR_BACKWARD = 1;
    public static final int INTAKE_PISTON_RIGHT_FORWARD = 2;
    public static final int INTAKE_PISTON_RIGHT_BACKWARD = 3;
    public static final int INTAKE_PISTON_LEFT_FORWARD = 4;
    public static final int INTAKE_PISTON_LEFT_BACKWARD = 5;

    // Control system ports
    public static final int PCM_1_PORT = 11;
    public static final int PCM_2_PORT = 12;
}
