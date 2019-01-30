package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Intake;

public class CollectCargoAuton extends Command {

    private Intake intake;

    public CollectCargoAuton() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        this.intake.collectCargo();
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}