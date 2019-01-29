package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Intake;

public class IntakeCommand extends Command {
    private Intake intake;

    public IntakeCommand() {
        requires(this.intake = Intake.getInstance());
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        intake.releaseHatch();
        intake.releaseCargo();
    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getRawAxis(RobotMap.OPERATOR_TRIGGER_COLLECT_HATCH) > 0.75) {
            intake.collectHatch();
        }
        if (OI.getOI().getOperatorJoystick().getRawAxis(RobotMap.OPERATOR_TRIGGER_RELEASE_HATCH) > 0.75) {
            intake.releaseHatch();
        }
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.OPERATOR_BUTTON_COLLECT_HATCH)) {

        }
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.OPERATOR_BUTTON_RELEASE_HATCH)) {

        }

        // TODO: No hatch and cargo at the same time
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
