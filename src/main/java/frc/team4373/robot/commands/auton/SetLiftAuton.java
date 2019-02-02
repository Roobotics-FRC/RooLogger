package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Lift;

/**
 * Moves the lift to the given position.
 */
public class SetLiftAuton extends Command {
    private Lift lift;
    private double position;

    public enum Position {
        HATCH_3(210), HATCH_2(110), HATCH_1(30),
        CARGO_3(200), CARGO_2(100), CARGO_1(20),
        LOADING(10), STOW(0);

        private int value;
        Position(int value) {
            this.value = value;
        }
    }

    public SetLiftAuton(Position position) {
        requires(this.lift = Lift.getInstance());
        this.position = position.value;
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
