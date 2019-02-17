package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Strafes in order to line up with vision targets using data provided via NetworkTables.
 */
public class MiddleWheelAdjusterAuton extends PIDCommand {
    private Drivetrain drivetrain;
    private boolean finished;
    private boolean initiallyDeployed;
    private int sampleCount = 0;
    private double distanceSum = 0;
    private boolean readyForPID = false;
    private double outputThreshold = RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED * 0.1;

    /**
     * Constructs a new auton command to align the robot with the vision target using the H-wheel.
     */
    public MiddleWheelAdjusterAuton() {
        super("MiddleWheelAdjusterAuton", RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kI, RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kD);
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.drivetrain.setLightRing(true);
        resetState();
        this.initiallyDeployed = drivetrain.isMiddleWheelDeployed();
        this.drivetrain.deployMiddleWheel();
        this.getPIDController().setOutputRange(-RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED,
                RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED);
    }

    @Override
    protected double returnPIDInput() {
        return this.drivetrain.getSensorPosition(Drivetrain.TalonID.MIDDLE_1);
    }

    @Override
    protected void usePIDOutput(double output) {
        if (sampleCount < RobotMap.VISION_SAMPLE_COUNT) { // polling state
            this.drivetrain.setLightRing(true);
            // TODO: Check whether light ring turns on fast enough for the first fetch to be valid
            distanceSum += SmartDashboard.getNumber("lateral_distance_to_target", 0);
            ++sampleCount;
        } else if (!readyForPID) { // setpoint setting state
            this.drivetrain.setLightRing(false);
            double setpointInches = distanceSum / RobotMap.VISION_SAMPLE_COUNT;
            if (setpointInches < RobotMap.ALLOWABLE_OFFSET_FROM_VIS_TARGET) {
                this.finished = true;
            } else {
                this.setSetpoint(this.drivetrain.getSensorPosition(Drivetrain.TalonID.MIDDLE_1)
                        + setpointInches / RobotMap.DRIVETRAIN_ENC_UNITS_TO_IN);
                this.drivetrain.setLightRing(false);
                readyForPID = true;
            }
        } else { // PID execution state
            this.drivetrain.setLightRing(false); // safety
            if (Math.abs(output) < outputThreshold) {
                resetState();
            } else {
                drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1, output);
            }
        }
    }

    private void resetState() {
        this.sampleCount = 0;
        this.distanceSum = 0;
        this.readyForPID = false;
        this.finished = false;
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.drivetrain.setLightRing(false); // safety
        if (initiallyDeployed) {
            drivetrain.deployMiddleWheel();
        } else {
            drivetrain.retractMiddleWheel();
        }
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
