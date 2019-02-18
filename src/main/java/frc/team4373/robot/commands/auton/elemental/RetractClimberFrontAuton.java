package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.ClimberDrive;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Retracts the front half of the climber and sets the neutral mode of the drivetrain to coast.
 */
public class RetractClimberFrontAuton extends Command {
    private Climber climber;
    private ClimberDrive cld;
    private Drivetrain drivetrain;

    /**
     * Constructs a command to retract the front half of the climber.
     */
    public RetractClimberFrontAuton() {
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
        this.climber.retractFront();
        this.drivetrain.setNeutralMode(NeutralMode.Brake);
        this.cld.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
