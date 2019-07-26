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
    public static final boolean LIFT_MOTOR_1_INVERTED = true;
    public static final boolean LIFT_MOTOR_2_INVERTED = true;
    public static final boolean CLIMBER_MOTOR_INVERTED = true;
    public static final boolean INTAKE_MOTOR_RIGHT_INVERTED = false;
    public static final boolean INTAKE_MOTOR_LEFT_INVERTED = false;

    // Sensor phase configuration
    public static final boolean DRIVETRAIN_RIGHT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_LEFT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_MIDDLE_SENSOR_PHASE = true;

    // OI devices
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    // Buttons
    public static final int OPERATOR_BUTTON_LIFT_CARGO_L2 = 2; // b button
    public static final int OPERATOR_BUTTON_LIFT_CARGO_L1 = 1; // a button
    public static final int OPERATOR_BUTTON_LIFT_CARGO_SHIP = 3; // x button
    public static final int OPERATOR_BUTTON_LIFT_TO_LOAD = 8; // start button
    public static final int OPERATOR_BUTTON_LIFT_TO_GROUND = 7; // back button
    public static final int DRIVER_BUTTON_CLIMB_RAISE_BOT = 7;
    public static final int DRIVER_BUTTON_VISION_ALIGNMENT = 2;
    public static final int DRIVER_BUTTON_KILL_AUTON = 6;
    public static final int DRIVER_BUTTON_TOGGLE_LIGHT_RING = 12;
    public static final int DRIVER_BUTTON_BASIC_DRIVE = 4;

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
    public static final int INTAKE_LIMIT_SWITCH_CHANNEL = 2;
    public static final int LIGHT_RING_RELAY_CHANNEL = 3;

    // Other mappings
    public static final int PTN_CHANNEL = 1;

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
