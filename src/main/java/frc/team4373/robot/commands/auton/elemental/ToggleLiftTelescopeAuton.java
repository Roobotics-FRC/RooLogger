package frc.team4373.robot.commands.auton.elemental;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Lift;

public class ToggleLiftTelescopeAuton extends Command {
    private Lift lift;
    private boolean shouldTelescope;
    private Boolean overrideState = null;

    public ToggleLiftTelescopeAuton() {
        requires(this.lift = Lift.getInstance());
    }

    public ToggleLiftTelescopeAuton(boolean state) {
        requires(this.lift = Lift.getInstance());
        this.overrideState = state;
    }

    @Override
    protected void initialize() {
        this.shouldTelescope = !this.lift.isTelescoped();
        setTimeout(0.5);
    }

    @Override
    protected void execute() {
        if (this.overrideState != null) {
            this.shouldTelescope = this.overrideState;
        }
        if (this.shouldTelescope) {
            this.lift.telescope();
        } else {
            this.lift.retract();
        }
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
