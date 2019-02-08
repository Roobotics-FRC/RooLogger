package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A command that dives forward for the specified power for the specified amount of time.
 */
public class TimedDriveAuton extends Command {
    private Drivetrain drivetrain;
    private double power;

    /**
     * Creates a new TimedDriveAuton with the given power and time of running.
     * @param power The power to drive at.
     * @param seconds The time to drive for.
     */
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
