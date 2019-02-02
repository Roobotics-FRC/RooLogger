package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Lift;

/**
 * Moves the lift to the given position.
 */
public class SetLiftAuton extends Command {
    private Lift lift;
    private double position;

    public SetLiftAuton(double position) {
        requires(this.lift = Lift.getInstance());
        this.position = position;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.lift.setPositionRelative(this.position);
    }

    @Override
    protected boolean isFinished() {
        return this.lift.closedLoopErrorIsTolerable();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
