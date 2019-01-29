package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;

/**
 * A Javadoc template. TODO: Update RetractClimberRearAuton Javadoc.
 *
 * @author Samasaur
 */
public class RetractClimberRearAuton extends Command {
    private Climber climber;

    public RetractClimberRearAuton() {
        requires(this.climber = Climber.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        climber.retractFront(); // TODO: This could also be `retractAll()`. Should it be?
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        climber.retractRear();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
