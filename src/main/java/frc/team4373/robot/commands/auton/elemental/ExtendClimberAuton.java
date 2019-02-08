package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Extends the climber and sets the neutral mode of the drivetrain to coast.
 */
public class ExtendClimberAuton extends Command {
    private Climber climber;
    private Drivetrain drivetrain;

    public ExtendClimberAuton() {
        requires(this.climber = Climber.getInstance());
        this.drivetrain = Drivetrain.getInstance();
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        climber.climb();
        drivetrain.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
