package frc.team4373.robot.commands.auton.utility;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.Arrays;

/**
 * Runs the given commands sequentially.
 */
public class CommandGroupSequentialAuton extends CommandGroup {
    /**
     * Creates a command group that will run the given commands sequentially.
     * @param commands The commands to run.
     */
    public CommandGroupSequentialAuton(Command... commands) {
        Arrays.stream(commands).forEach(this::addSequential);
    }
}
