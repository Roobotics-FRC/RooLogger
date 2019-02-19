package frc.team4373.robot.commands.auton.elemental;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Climber;
import frc.team4373.robot.subsystems.ClimberDrive;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Extends the climber and sets the neutral mode of the drivetrain to coast.
 */
public class ExtendClimberWithPitchAuton extends Command {
    private Climber climber;
    private ClimberDrive cld;
    private Drivetrain drivetrain;

    private long lastPitch = -1;
    private final long COOLDOWN = 250;
    private long initialDeploy = -1;
    private final double TOLERABLE_PITCH = 10;
    private boolean finished = false;


    /**
     * Constructs an ExtendClimberAuton command.
     */
    public ExtendClimberWithPitchAuton() {
        requires(this.climber = Climber.getInstance());
        requires(this.cld = ClimberDrive.getInstance());
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.initialDeploy = -1;
        this.finished = false;
    }

    @Override
    protected void execute() {
        long now = System.currentTimeMillis();
        if (this.initialDeploy == -1) {
            this.climber.climb();
            this.initialDeploy = now;
        } else if (now > this.initialDeploy + COOLDOWN) {
            double pitch = this.drivetrain.getPigeonPitch();
            if (pitch > TOLERABLE_PITCH) {
                this.climber.neutralizeFront();
                lastPitch = now;
            } else if (pitch < -TOLERABLE_PITCH) {
                this.climber.neutralizeRear();
                lastPitch = now;
            } else if (this.lastPitch > -1) {
                this.climber.climb();
                if (now > this.lastPitch + COOLDOWN) {
                    this.finished = true;
                }
            }
            this.drivetrain.setNeutralMode(NeutralMode.Coast);
            this.cld.setNeutralMode(NeutralMode.Brake);
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }
}
