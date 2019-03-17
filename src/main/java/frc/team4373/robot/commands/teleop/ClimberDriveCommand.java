package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.ClimberDrive;

/**
 * A command that controls the drivetrain of the climber.
 */
public class ClimberDriveCommand extends Command {
    private ClimberDrive cld;

    public ClimberDriveCommand() {
        requires(this.cld = ClimberDrive.getInstance());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        // axis is 0 -> -1, convert to 0 -> 1—use raw axis b/c we don't want filtering
        double power = (OI.getOI().getDriveJoystick().getRawAxis(
                RobotMap.DRIVER_AXIS_SLIDER_CLIMBER_WHEEL) - 1) / -2;

        // make power relative to max speed
        power *= RobotMap.MAXIMUM_CLIMBER_DRIVE_SPEED;

        // if reversal button is held, go backward
        if (OI.getOI().getDriveJoystick().getRawButton(
                RobotMap.DRIVER_BUTTON_CLIMB_DRIVE_BACKWARD)) {
            power *= -1;
        }
        cld.setPercentOutput(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        cld.setPercentOutput(0);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}