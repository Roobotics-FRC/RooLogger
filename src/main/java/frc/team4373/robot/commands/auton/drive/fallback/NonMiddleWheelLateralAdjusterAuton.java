package frc.team4373.robot.commands.auton.drive.fallback;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.VisionCommand;

public class NonMiddleWheelLateralAdjusterAuton extends VisionCommand {

    public NonMiddleWheelLateralAdjusterAuton() {
        super("NonMiddleWheelLateralAdjusterAuton", "lateral_distance_to_target");
    }

    @Override
    protected boolean cameraValueIsAcceptable(double value) {
        return value < RobotMap.ALLOWABLE_LATERAL_OFFSET_FROM_VIS_TARGET;
    }

    @Override
    protected void useAverageCameraValue(double value) {
        boolean moveToRight = value > 0;
        Scheduler.getInstance().add(new NonMiddleWheelAdjustmentGroup(value,
                moveToRight));
    }
}
