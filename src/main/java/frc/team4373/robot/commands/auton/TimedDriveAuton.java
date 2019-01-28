package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update TimedDriveAuton Javadoc.
 *
 * @author Samasaur
 */
public class TimedDriveAuton extends Command {
    private Drivetrain drivetrain;
    private double power;
    public TimedDriveAuton(double power, double seconds) {
        requires(this.drivetrain = Drivetrain.getInstance());
        setTimeout(seconds);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.drivetrain.setSidesPercentOutput(power);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
