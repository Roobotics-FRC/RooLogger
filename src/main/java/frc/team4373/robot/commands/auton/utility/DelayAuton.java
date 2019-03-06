package frc.team4373.robot.commands.auton.utility;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Delays the given amount of time.
 */
public class DelayAuton extends Command {
    private double time;

    public DelayAuton(double seconds) {
        time = seconds;
    }

    @Override
    protected void initialize() {
        setTimeout(time);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
