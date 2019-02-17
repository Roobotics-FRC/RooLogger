package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.elemental.SetLiftAuton;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.input.RooJoystick;
import frc.team4373.robot.subsystems.Lift;

/**
 * Allows manual control of the lift using the left stick on the operator joystick.
 */
public class LiftCommand extends Command {
    private Lift lift;

    private Command liftToHatch3;
    private Command liftToHatch2;
    private Command liftToHatch1;

    /**
     * Constructs a LiftCommand.
     */
    public LiftCommand() {
        requires(this.lift = Lift.getInstance());

        this.liftToHatch3 = new SetLiftAuton(SetLiftAuton.Position.HATCH_3);
        this.liftToHatch2 = new SetLiftAuton(SetLiftAuton.Position.HATCH_2);
        this.liftToHatch1 = new SetLiftAuton(SetLiftAuton.Position.HATCH_1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double power = -OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_AXIS_LIFT_MANUAL_CONTROL);
        lift.setPercentOutput(power);

        switch (OI.getOI().getOperatorJoystick().getPOV()) {
            case 0:
                Scheduler.getInstance().add(liftToHatch3);
                break;
            case 270:
                Scheduler.getInstance().add(liftToHatch2);
                break;
            case 180:
                Scheduler.getInstance().add(liftToHatch1);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // lift.setPercentOutput(0);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
