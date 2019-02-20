package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.*;

/**
 * Extends the climber and sets the neutral mode of the drivetrain to coast.
 */
public class ExtendFrontFirstAuton extends Command {
    private Climber climber;
    private ClimberDrive cld;
    private Drivetrain drivetrain;

    private double initialPitch = -1;
    private final double TOLERABLE_PITCH = 6;
    private boolean started;


    /**
     * Constructs an ExtendClimberAuton command.
     */
    public ExtendFrontFirstAuton() {
        requires(this.climber = Climber.getInstance());
        requires(this.cld = ClimberDrive.getInstance());
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.initialPitch = drivetrain.getPigeonPitch();
        this.setTimeout(25);
        started = false;
    }

    @Override
    protected void execute() {
        climber.deployFront();
        if ((drivetrain.getPigeonPitch() - initialPitch) > TOLERABLE_PITCH) {
            climber.deployRear();
            started = true;
        } else if (started == false) {
            climber.neutralizeRear();
        }

        drivetrain.setNeutralMode(NeutralMode.Coast);
        cld.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
