package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.ClimberDrive;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Extends rear climber and sets neutral modes.
 */
public class DeployClimberRearAuton extends Command {
    private Climber climber;
    private ClimberDrive cld;
    private Drivetrain drivetrain;

    /**
     * Constructs a command to retract the climber.
     */
    public DeployClimberRearAuton() {
        requires(this.climber = Climber.getInstance());
        requires(this.drivetrain = Drivetrain.getInstance());
        requires(this.cld = ClimberDrive.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        this.climber.deployRear();
        this.drivetrain.setNeutralMode(NeutralMode.Coast);
        this.cld.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}