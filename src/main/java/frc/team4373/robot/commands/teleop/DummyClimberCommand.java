package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;

public class DummyClimberCommand extends Command {

    public DummyClimberCommand() {
        requires(Climber.getInstance());
    }

    @Override
    public void execute() {}

    @Override
    protected boolean isFinished() {
        return false;
    }
}
