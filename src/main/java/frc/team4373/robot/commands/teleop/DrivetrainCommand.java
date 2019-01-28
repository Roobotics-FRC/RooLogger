package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A command that controls the drivetrain via the joystick.
 *
 * @author Samasaur
 */
public class DrivetrainCommand extends Command {
    private Drivetrain drivetrain;
    public DrivetrainCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        double x = OI.getOI().getDriveJoystick().rooGetX();
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = Math.signum(OI.getOI().getDriveJoystick().rooGetZ())
                * Math.sqrt(Math.abs(OI.getOI().getDriveJoystick().rooGetZ())) / 2;
        drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1, x);
        drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1, y + z);
        drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1, y - z);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
