package frc.team4373.robot.commands.auton.drive.fallback;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;

public class NonMiddleWheelAdjustmentGroup extends CommandGroup {
    /**
     * Constructs a new non-middle-wheel adjustment command group.
     * @param distance the distance, in inches, to travel.
     * @param rightward whether to travel to the right.
     */
    public NonMiddleWheelAdjustmentGroup(double distance, boolean rightward) {
        addSequential(new TurnToAngleAuton(rightward ? 90 : -90));
        addSequential(new DriveDistanceAuton(distance,
                RobotMap.AUTON_MIDDLE_WHEEL_ADJUSTMENT_SPEED));
        addSequential(new TurnToAngleAuton(rightward ? -90 : 90));
    }
}
