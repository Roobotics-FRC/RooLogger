package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Retracts the rear half of the climber and sets the neutral mode of the drivetrain to brake.
 */
public class RetractClimberRearAuton extends Command {
    private Climber climber;
    private Drivetrain drivetrain;

    public RetractClimberRearAuton() {
        requires(this.climber = Climber.getInstance());
        this.drivetrain = Drivetrain.getInstance();
    }

    @Override
    protected void initialize() {
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        climber.retractFront(); // TODO: This could also be `retractAll()`. Should it be?
        drivetrain.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
