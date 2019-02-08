package frc.team4373.robot.commands.auton.elemental;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

public class ReleaseCargoAuton extends Command {

    private Intake intake;

    public ReleaseCargoAuton() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        this.intake.releaseCargo();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

}