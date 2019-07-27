package frc.team4373.robot;

/**
 * Holds various mappings and constants.
 */

public class RobotMap {
    // Motor inversions
    public static final boolean DRIVETRAIN_MOTOR_RIGHT_1_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_RIGHT_2_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_LEFT_1_INVERTED = true;
    public static final boolean DRIVETRAIN_MOTOR_LEFT_2_INVERTED = true;
    public static final boolean DRIVETRAIN_MOTOR_MIDDLE_1_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_MIDDLE_2_INVERTED = false;

    // Sensor phase configuration
    public static final boolean DRIVETRAIN_RIGHT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_LEFT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_MIDDLE_SENSOR_PHASE = true;

    // OI devices
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    // Motor CAN chain identifiers
    public static final int DRIVETRAIN_MOTOR_RIGHT_1 = 13;
    public static final int DRIVETRAIN_MOTOR_RIGHT_2 = 14;
    public static final int DRIVETRAIN_MOTOR_LEFT_1 = 11;
    public static final int DRIVETRAIN_MOTOR_LEFT_2 = 12;
    public static final int DRIVETRAIN_MOTOR_MIDDLE_1 = 15;
    public static final int DRIVETRAIN_MOTOR_MIDDLE_2 = 16;

    // Pneumatic channel mappings
    public static final int DRIVETRAIN_PISTON_FORWARD = 0; // pcm 1
    public static final int DRIVETRAIN_PISTON_BACKWARD = 1; // pcm 1

    // Digital input mappings
    public static final int LIGHT_RING_RELAY_CHANNEL = 3;

    // Control system ports
    public static final int PCM_1_PORT = 2;
    public static final int PCM_2_PORT = 3;

    // PID- and sensor-related constants
    public static class PID {
        public double kP;
        public double kI;
        public double kD;

        PID(double kP, double kI, double kD) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
        }
    }

}
