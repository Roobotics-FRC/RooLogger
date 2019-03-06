package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A command that exists to do nothing but require a subsystem.
 */
public class DummyCommand extends Command {
    /**
     * Creates a new DummyCommand that requires the given subsystem.
     * @param subsystem The subsystem to require.
     */
    public DummyCommand(Subsystem subsystem) {
        requires(subsystem);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
