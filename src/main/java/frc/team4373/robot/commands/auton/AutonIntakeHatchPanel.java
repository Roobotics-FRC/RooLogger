package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

public class AutonIntakeHatchPanel extends Command {

    private Intake intake;

    public AutonIntakeHatchPanel() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(1);
    }

    @Override
    protected void execute() {
        this.intake.collectHatch();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
