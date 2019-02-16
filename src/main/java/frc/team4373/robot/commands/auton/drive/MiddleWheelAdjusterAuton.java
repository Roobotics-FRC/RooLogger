package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
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
    private int samples = 0;
    private double distanceSum = 0;
    private boolean readyForPID = false;

    public MiddleWheelAdjusterAuton() {
        super("MiddleWheelAdjusterAuton", RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kI, RobotMap.DRIVETRAIN_MIDDLE_PID_GAINS.kD);
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.drivetrain.setLightRing(true);
        this.finished = false;
        this.initiallyDeployed = drivetrain.isMiddleWheelDeployed();
        this.drivetrain.deployMiddleWheel();
    }

    @Override
    protected double returnPIDInput() {
        return this.drivetrain.getSensorPosition(Drivetrain.TalonID.MIDDLE_1);
    }

    @Override
    protected void usePIDOutput(double output) {
        if (samples < RobotMap.VISION_SAMPLE_COUNT) {
            distanceSum += SmartDashboard.getNumber("lateral_distance_to_target", 0);
            ++samples;
        } else if (!readyForPID) {
            double setpointInches = samples / RobotMap.VISION_SAMPLE_COUNT;
            this.setSetpoint(this.drivetrain.getSensorPosition(Drivetrain.TalonID.MIDDLE_1) +
                    setpointInches / RobotMap.DRIVETRAIN_ENC_UNITS_TO_IN);
            this.drivetrain.setLightRing(false);
        } else {
            drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1,
                    RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED);
        }
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
