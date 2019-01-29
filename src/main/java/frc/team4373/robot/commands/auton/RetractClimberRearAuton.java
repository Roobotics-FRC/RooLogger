package frc.team4373.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Retracts the rear half of the climber and sets the neutral mode of the drivetrain to brake.
 *
 * @author Samasaur
 */
public class RetractClimberRearAuton extends Command {
    private Climber climber;

    public RetractClimberRearAuton() {
        requires(this.climber = Climber.getInstance());
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        climber.retractFront(); // TODO: This could also be `retractAll()`. Should it be?
        Drivetrain.getInstance().setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    @Override
    protected void end() {
        climber.retractRear();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
