package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;

/**
 * Removes any active commands from the given subsystem(s).
 */
public class ClearSubsystemCommand extends Command {
    /**
     * Creates a new ClearSubsystemCommand to clear the given subsystems.
     * @param subsystems The subsystems to clear.
     */
    public ClearSubsystemCommand(Subsystem... subsystems) {
        Arrays.stream(subsystems).forEach(this::requires);
    }
    @Override
    protected boolean isFinished() {
        return true;
    }
}
