package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.*;

/**
 * Climbs onto the level 3 platform.
 *
 * <p>The bot should start facing the level 3 platform, as close to it as possible.
 * If the hardware (i.e. limit switches) is perfect, this will be fine; if not, well, KABOOOOOOOOM!
 */
public class ClimbAuton extends Command {
    private int state = 0;
    private double nextTime = 0;
    private boolean waiting = false;
    private ClimberDrive cld;
    private Climber climber;

    public ClimbAuton() {
        requires(this.cld = ClimberDrive.getInstance());
        requires(this.climber = Climber.getInstance());
    }

    @Override
    protected void initialize() {
        this.state = 0;
        this.nextTime = 0;
        this.waiting = false;
    }

    @Override
    protected void execute() {
        if (this.waiting) {
            if (this.nextTime < Timer.getFPGATimestamp()) {
                this.waiting = false;
            } else {
                return;
            }
        }
        switch (this.state) {
            case 0:
                this.climber.climb();
                this.waiting = true;
                this.nextTime = Timer.getFPGATimestamp() + 0.5;
                this.state++;
                break;
            case 1:
                if (this.climber.getFrontLimitSwitch()) {
                    this.state++;
                    this.cld.setPercentOutput(0);
                }
                this.cld.setPercentOutput(1);
                break;
            case 2:
                this.climber.retractFront();
                this.waiting = true;
                this.nextTime = Timer.getFPGATimestamp() + 0.25;
                this.state++;
                break;
            case 3:
                if (this.climber.getRearLimitSwitch()) {
                    this.state++;
                    this.cld.setPercentOutput(0);
                }
                this.cld.setPercentOutput(1);
                break;
            case 4:
                this.climber.retractRear();
                this.state++;
                break;
            default:
        }
    }

    @Override
    protected boolean isFinished() {
        return this.state == 5;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
