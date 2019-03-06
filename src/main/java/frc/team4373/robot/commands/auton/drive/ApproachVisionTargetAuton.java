package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4373.robot.RobotMap;

public class ApproachVisionTargetAuton extends VisionCommand {
    private double targetDistanceFromVisionTarget;

    /**
     * Constructs a command to approach a vision target up to a specified distance AWAY from it.
     * @param distance the distance, in inches, away from the target to achieve.
     */
    public ApproachVisionTargetAuton(double distance) {
        super("ApproachVisionTargetAuton", "forward_distance_to_target");
        this.targetDistanceFromVisionTarget = distance;
    }

    @Override
    protected boolean cameraValueIsAcceptable(double value) {
        return value < RobotMap.ALLOWABLE_ERR_DISTANCE_TO_VIS_TARGET;
    }

    @Override
    protected void useAverageCameraValue(double value) {
        double distanceToDrive = value - this.targetDistanceFromVisionTarget;
        Scheduler.getInstance().add(new DriveDistanceAuton(
                distanceToDrive, RobotMap.AUTON_VISION_APPROACH_SPEED));
    }


}
