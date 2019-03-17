package frc.team4373.robot.commands.auton.intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

/**
 * Releases a hatch panel.
 */
public class ReleaseHatchPanelAuton extends Command {
    private Intake intake;

    public ReleaseHatchPanelAuton() {
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
