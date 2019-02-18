package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

public class VisionRotatorAuton extends VisionCommand {
    private double angleSetpoint = 0;

    public VisionRotatorAuton() {
        super("VisionRotatorAuton", "angle_to_target");
    }

    @Override
    protected void initialize() {
        this.angleSetpoint = 0;
    }

    @Override
    protected boolean cameraValueIsAcceptable(double value) {
        return angleSetpoint < RobotMap.ALLOWABLE_ANGLE_TO_VIS_TARGET;
    }

    @Override
    protected void useAverageCameraValue(double value) {
        Scheduler.getInstance().add(new TurnToAngleAuton(
                this.getDrivetrain().getPigeonYaw() + value));
    }


}
