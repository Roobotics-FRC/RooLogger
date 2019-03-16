package frc.team4373.robot.commands.auton.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Extends the font climber and sets neutral modes.
 */
public class DebugClimberCommand extends Command {
    private Climber climber;
    private Drivetrain drivetrain;

    /**
     * Constructs a command to retract the climber.
     */
    public DebugClimberCommand() {
        if (OI.getOI().getDriveJoystick().getRawButton(
                RobotMap.DRIVER_BUTTON_CLIMB_RAISE_BOT_FRONT)
                && OI.getOI().getDriveJoystick().getRawButton(
                    RobotMap.DRIVER_BUTTON_CLIMB_RAISE_BOT_REAR)) {
            this.climber.deployFront();
            this.climber.deployRear();
        } else if (OI.getOI().getDriveJoystick().getRawButton(
                RobotMap.DRIVER_BUTTON_CLIMB_RAISE_BOT_FRONT)) {
            this.climber.deployFront();
            this.climber.neutralizeRear();
        } else if (OI.getOI().getDriveJoystick().getRawButton(
                RobotMap.DRIVER_BUTTON_CLIMB_RAISE_BOT_REAR)) {
            this.climber.deployRear();
            this.climber.neutralizeFront();
        } else {
            this.climber.neutralizeFront();
            this.climber.neutralizeRear();
        }
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        this.climber.deployFront();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}