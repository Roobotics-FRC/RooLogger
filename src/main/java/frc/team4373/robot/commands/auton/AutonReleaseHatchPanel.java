package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

/**
 * Releases a hatch panel.
 */
public class AutonReleaseHatchPanel extends Command {
    private Intake intake;

    public AutonReleaseHatchPanel() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(1);
    }

    @Override
    protected void execute() {
        this.intake.releaseHatch();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
