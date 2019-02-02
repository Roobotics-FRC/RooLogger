package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

/**
 * Deploys the intake.
 */
public class DeployIntakeAuton extends Command {
    private Intake intake;

    public DeployIntakeAuton() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.25);
    }

    @Override
    protected void execute() {
        this.intake.deploy();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
