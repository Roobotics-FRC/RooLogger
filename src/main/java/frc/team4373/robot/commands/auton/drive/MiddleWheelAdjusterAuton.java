package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Strafes in order to line up with vision targets using data provided via NetworkTables.
 */
public class MiddleWheelAdjusterAuton extends Command {
    private Drivetrain drivetrain;
    private boolean finished;
    private boolean initiallyDeployed;

    public MiddleWheelAdjusterAuton() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.finished = false;
        this.drivetrain.enableLightRing(true);
        this.initiallyDeployed = drivetrain.isMiddleWheelDeployed();
        this.drivetrain.deployMiddleWheel();
    }

    @Override
    protected void execute() {
        String adjustment = SmartDashboard.getString("vision_lateral_correction", "none");
        if (adjustment.equalsIgnoreCase("right")) {
            drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1,
                    RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED);
        } else if (adjustment.equalsIgnoreCase("left")) {
            drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1,
                    -RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED);
        } else {
            this.finished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.drivetrain.enableLightRing(false);
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
