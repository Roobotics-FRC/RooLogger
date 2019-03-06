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

    private long deploymentTimeout = 0;
    private static final long BUTTON_COOLDOWN_TIME = 2000;

    public IntakeCommand() {
        requires(this.intake = Intake.getInstance());
    }

    @Override
    protected void initialize() {
        intake.neutralizeCargoMotors();
        this.firstTime = true;
    }

    @Override
    protected void execute() {
        long now = System.currentTimeMillis();
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.OPERATOR_BUTTON_TOGGLE_INTAKE)
                && now > deploymentTimeout + BUTTON_COOLDOWN_TIME) {
            if (intake.isDeployed()) {
                intake.retract();
            } else {
                intake.deploy();
            }
            this.deploymentTimeout = now;
        }
        if (OI.getOI().getOperatorJoystick().getRawButton(
                RobotMap.OPERATOR_BUTTON_COLLECT_CARGO)) {
            if (firstTime) {
                limitSwitch = intake.getLimitSwitch();
                firstTime = false;
            } else if (limitSwitch == intake.getLimitSwitch()) {
                intake.releaseHatch();
                intake.collectCargo();
            }
        } else if (OI.getOI().getOperatorJoystick().getRawButton(
                RobotMap.OPERATOR_BUTTON_RELEASE_CARGO)) {
            intake.releaseHatch();
            intake.releaseCargo();
            firstTime = true;
        } else if (OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_TRIGGER_COLLECT_HATCH) > 0.75) {
            intake.neutralizeCargoMotors();
            intake.collectHatch();
            firstTime = true;
        } else if (OI.getOI().getOperatorJoystick().getRawAxis(
                RobotMap.OPERATOR_TRIGGER_RELEASE_HATCH) > 0.75) {
            intake.neutralizeCargoMotors();
            intake.releaseHatch();
            firstTime = true;
        } else {
            intake.neutralizeCargoMotors();
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
