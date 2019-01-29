package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.ClimberDrive;

/**
 * A command that controls the drivetrain of the climber.
 *
 * @author Samasaur
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
        double power = (OI.getOI().getDriveJoystick().getRawAxis(
                RobotMap.DRIVER_AXIS_SLIDER_CLIMBER_WHEEL) - 1) / -2;
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
