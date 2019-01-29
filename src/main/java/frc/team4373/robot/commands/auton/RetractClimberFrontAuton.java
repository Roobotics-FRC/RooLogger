package frc.team4373.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Retracts the front half of the climber and sets the neutral mode of the drivetrain to coast.
 *
 * @author Samasaur
 */
public class RetractClimberFrontAuton extends Command {
    private Climber climber;
    private Drivetrain drivetrain;

    public RetractClimberFrontAuton() {
        requires(this.climber = Climber.getInstance());
        this.drivetrain = Drivetrain.getInstance();
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        climber.retractFront();
        drivetrain.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        climber.retractFront();
        drivetrain.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
