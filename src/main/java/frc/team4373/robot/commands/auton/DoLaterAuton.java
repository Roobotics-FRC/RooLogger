package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Runs the given command after the given amount of time.
 *
 * @author Samasaur
 */
public class DoLaterAuton extends CommandGroup {
    /**
     * Creates a new DoLaterAuton that will run the specified command after the given amount of time.
     * @param command The command to run.
     * @param delay The time to delay.
     */
    public DoLaterAuton(Command command, double delay) {
        addSequential(new DelayAuton(delay));
        addSequential(command);
    }
}
