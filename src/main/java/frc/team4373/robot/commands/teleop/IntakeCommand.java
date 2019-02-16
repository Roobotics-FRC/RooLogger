package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Intake;

/**
 * Allows manual control of the intake using the rear left and right triggers and buttons.
 */
public class IntakeCommand extends Command {
    private Intake intake;
    private boolean limitSwitch = false;
    private boolean firstTime = true;

    public IntakeCommand() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        intake.releaseHatch();
        intake.neutralizeCargoMotors();
        this.firstTime = true;
    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getRawButton(
                RobotMap.OPERATOR_BUTTON_COLLECT_CARGO)) {
            if (firstTime) {
                limitSwitch = intake.getLimitSwitch();
                firstTime = false;
            } else if (limitSwitch == intake.getLimitSwitch()) {
                intake.collectCargo();
                intake.releaseHatch();
            } else {
                firstTime = true;
            }
        } else if (OI.getOI().getOperatorJoystick().getRawButton(
                RobotMap.OPERATOR_BUTTON_RELEASE_CARGO)) {
            intake.releaseCargo();
            intake.releaseHatch();
            firstTime = true;
        } else if (OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_TRIGGER_COLLECT_HATCH) > 0.75) {
            intake.collectHatch();
            intake.neutralizeCargoMotors();
            firstTime = true;
        } else if (OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_TRIGGER_RELEASE_HATCH) > 0.75) {
            intake.releaseHatch();
            intake.neutralizeCargoMotors();
            firstTime = true;
        } else {
            intake.neutralizeCargoMotors();
            intake.releaseHatch();
            firstTime = true;
        }
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
