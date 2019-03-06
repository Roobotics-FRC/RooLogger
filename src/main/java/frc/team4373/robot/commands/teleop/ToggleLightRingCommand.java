package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Drivetrain;

public class ToggleLightRingCommand extends Command {
    private Drivetrain drivetrain;
    private Boolean explicitState = null;
    private boolean stateToSet;

    public ToggleLightRingCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    public ToggleLightRingCommand(boolean state) {
        requires(this.drivetrain = Drivetrain.getInstance());
        this.explicitState = state;
    }

    @Override
    protected void initialize() {
        this.stateToSet = !this.drivetrain.getLightRingEnabled();
    }

    @Override
    protected void execute() {
        if (this.explicitState != null) {
            this.drivetrain.setLightRing(explicitState);
        } else {
            this.drivetrain.setLightRing(stateToSet);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }
}