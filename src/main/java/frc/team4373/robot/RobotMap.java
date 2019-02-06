package frc.team4373.robot;

/**
 * Holds various mappings and constants.
 */

public class RobotMap {
    // Motor inversions
    public static final boolean DRIVETRAIN_MOTOR_RIGHT_1_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_RIGHT_2_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_LEFT_1_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_LEFT_2_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_MIDDLE_1_INVERTED = false;
    public static final boolean DRIVETRAIN_MOTOR_MIDDLE_2_INVERTED = false;
    public static final boolean LIFT_MOTOR_1_INVERTED = false;
    public static final boolean LIFT_MOTOR_2_INVERTED = false;
    public static final boolean CLIMBER_MOTOR_INVERTED = false;
    public static final boolean INTAKE_MOTOR_RIGHT_INVERTED = false;
    public static final boolean INTAKE_MOTOR_LEFT_INVERTED = false;

    // Sensor phase configuration
    public static final boolean DRIVETRAIN_RIGHT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_LEFT_ENCODER_PHASE = false;
    public static final boolean DRIVETRAIN_MIDDLE_ENCODER_PHASE = false;
    public static final boolean LIFT_ENCODER_PHASE = false;

    // Speed presets
    public static final int LIFT_PEAK_OUTPUT = 1;
    public static final int INTAKE_MOTOR_OUTPUT = 1;
    public static final double AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED = 0.5;

    // OI devices
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    // Buttons
    public static final int OPERATOR_TRIGGER_COLLECT_HATCH = 2;
    public static final int OPERATOR_TRIGGER_RELEASE_HATCH = 3;
    public static final int OPERATOR_BUTTON_COLLECT_CARGO = 5;
    public static final int OPERATOR_BUTTON_RELEASE_CARGO = 6;
    public static final int OPERATOR_BUTTON_LIFT_CARGO_L3 = 4; // y button
    public static final int OPERATOR_BUTTON_LIFT_CARGO_L2 = 2; // b button
    public static final int OPERATOR_BUTTON_LIFT_CARGO_L1 = 1; // a button
    public static final int OPERATOR_BUTTON_LIFT_TO_LOAD = 8; // start button
    public static final int OPERATOR_AXIS_LIFT_MANUAL_CONTROL = 1; //Left stick Y, up is negative.
    public static final int OPERATOR_BUTTON_STOW_INTAKE = 7;
    public static final int DRIVER_BUTTON_CLIMB_RAISE_BOT = 7;
    public static final int DRIVER_BUTTON_CLIMB_RETRACT_FRONT = 9;
    public static final int DRIVER_BUTTON_CLIMB_RETRACT_REAR = 11;
    public static final int DRIVER_BUTTON_VISION_ALIGNMENT = 2;

    public static final int DRIVER_AXIS_SLIDER_CLIMBER_WHEEL = 3;

    // Motor CAN chain identifiers
    public static final int DRIVETRAIN_MOTOR_RIGHT_1 = 111;
    public static final int DRIVETRAIN_MOTOR_RIGHT_2 = 112;
    public static final int DRIVETRAIN_MOTOR_LEFT_1 = 121;
    public static final int DRIVETRAIN_MOTOR_LEFT_2 = 122;
    public static final int DRIVETRAIN_MOTOR_MIDDLE_1 = 131;
    public static final int DRIVETRAIN_MOTOR_MIDDLE_2 = 132;
    public static final int LIFT_MOTOR_1 = 231;
    public static final int LIFT_MOTOR_2 = 232;
    public static final int CLIMBER_DRIVE_MOTOR = 351;
    public static final int INTAKE_MOTOR_RIGHT = 411;
    public static final int INTAKE_MOTOR_LEFT = 421;

    // Pneumatic channel mappings
    public static final int DRIVETRAIN_PISTON_FORWARD = 0;
    public static final int DRIVETRAIN_PISTON_BACKWARD = 1;
    public static final int LIFT_PISTON_1_FORWARD = 2;
    public static final int LIFT_PISTON_1_BACKWARD = 3;
    public static final int LIFT_PISTON_2_FORWARD = 4;
    public static final int LIFT_PISTON_2_BACKWARD = 5;
    public static final int CLIMBER_PISTON_FRONT_FORWARD = 6;
    public static final int CLIMBER_PISTON_FRONT_BACKWARD = 7;
    public static final int CLIMBER_PISTON_REAR_FORWARD = 0;
    public static final int CLIMBER_PISTON_REAR_BACKWARD = 1;
    public static final int INTAKE_PISTON_HATCH_FORWARD = 2;
    public static final int INTAKE_PISTON_HATCH_BACKWARD = 3;
    public static final int INTAKE_PISTON_DEPLOYMENT_1_FORWARD = 4;
    public static final int INTAKE_PISTON_DEPLOYMENT_1_BACKWARD = 5;
    public static final int INTAKE_PISTON_DEPLOYMENT_2_FORWARD = 6;
    public static final int INTAKE_PISTON_DEPLOYMENT_2_BACKWARD = 7;

    // Digital input mappings
    public static final int CLIMBER_FRONT_LIMIT_SWITCH_CHANNEL = 0;
    public static final int CLIMBER_REAR_LIMIT_SWITCH_CHANNEL = 1;
    public static final int INTAKE_LIMIT_SWITCH_CHANNEL = 2;
    public static final int PTN_CHANNEL = 0;

    // Control system ports
    public static final int PCM_1_PORT = 11;
    public static final int PCM_2_PORT = 12;

    // PID- and sensor-related constants
    public static class PID {
        public double kF;
        public double kP;
        public double kI;
        public double kD;

        PID(double kF, double kP, double kI, double kD) {
            this.kF = kF;
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
        }
    }

    public static final int TALON_TIMEOUT_MS = 1000;
    public static final int LIFT_PID_IDX = 0;
    public static final PID LIFT_PID_GAINS = new PID(0, 1, 0, 0);
    public static final PID DRIVETRAIN_DIST_PID_GAINS = new PID(0, 0.001, 0, 0);
    public static final PID DRIVETRAIN_ANG_PID_GAINS = new PID(0, 0.01, 0, 0);
    public static final double AUTON_TURN_SPEED = 0.25;

    // 6in diameter wheels; 10.71:1 gearbox ratio; 4096 encoder units per rotation
    public static final double DRIVETRAIN_ENC_UNITS_TO_IN = 6 * Math.PI / 4096 / 10.71;
    public static final int LIFT_DEGREES_OF_MOTION = 120;
    public static final int LIFT_INITIAL_ANG_OFFSET = 0;
}
