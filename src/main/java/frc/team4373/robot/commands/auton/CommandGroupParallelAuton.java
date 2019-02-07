package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Runs the given commands in parallel.
 */
public class CommandGroupParallelAuton extends CommandGroup {
    /**
     * Creates a command group that will run the given commands in parallel.
     * @param commands The commands to run.
     */
    public CommandGroupParallelAuton(Command... commands) {
        for (Command c: commands) {
            addParallel(c);
        }
    }
}
