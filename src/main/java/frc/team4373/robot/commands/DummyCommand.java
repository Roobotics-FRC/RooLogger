package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A Javadoc template. TODO: Update DummyCommand Javadoc.
 *
 * @author Samasaur
 */
public class DummyCommand extends Command {
    public DummyCommand(Subsystem... subsystems) {
        for (Subsystem s: subsystems) {
            requires(s);
        }
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}
