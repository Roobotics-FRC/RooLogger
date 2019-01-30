package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Lift;

/**
 * Allows manual control of the lift using the left stick on the operator joystick.
 *
 * @author Samasaur
 */
public class LiftCommand extends Command {
    private Lift lift;

    public LiftCommand() {
        requires(this.lift = Lift.getInstance());
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        double power = OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_AXIS_LIFT_MANUAL_CONTROL);
        lift.setPercentOutput(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        lift.setPercentOutput(0);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
