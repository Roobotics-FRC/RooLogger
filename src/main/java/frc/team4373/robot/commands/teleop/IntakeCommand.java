package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.input.RooJoystick;
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
        if (OI.getOI().getOperatorJoystick().getRawAxis(2) == 1) {
            intake.collectHatch();
        }
        if (OI.getOI().getOperatorJoystick().getRawAxis(3) == 1) {
            intake.releaseHatch();
        }
        if (OI.getOI().getOperatorJoystick().getRawButton(5) == true) {

        }
        if (OI.getOI().getOperatorJoystick().getRawButton(6) == true) {

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
