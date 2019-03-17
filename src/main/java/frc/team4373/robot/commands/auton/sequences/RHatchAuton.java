package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.ApproachVisionTargetAuton;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;
import frc.team4373.robot.commands.auton.intake.CollectHatchPanelAuton;
import frc.team4373.robot.commands.auton.intake.DeployIntakeAuton;
import frc.team4373.robot.commands.auton.intake.ReleaseHatchPanelAuton;
import frc.team4373.robot.commands.auton.lift.SetLiftAuton;

public class RHatchAuton extends CommandGroup {
    private RobotMap.Side side;

    /**
     * Constructs a new rocket hatch auton command.
     * @param side the side of the field on which the robot is starting.
     * @param height the height of the hatch to go for.
     * @param panel the side of the rocket to go for.
     */
    public RHatchAuton(RobotMap.Side side, RobotMap.RocketHeight height,
                       RobotMap.RocketHatchPanel panel) {
        this.side = side;

        SetLiftAuton.Position pos;
        switch (height) {
            case MIDDLE:
                pos = SetLiftAuton.Position.HATCH_2;
                break;
            default:
                pos = SetLiftAuton.Position.HATCH_1;
                break;
        }

        addParallel(new CollectHatchPanelAuton());
        if (panel == RobotMap.RocketHatchPanel.FAR) {
            addSequential(new DriveDistanceAuton(196, RobotMap.AUTON_LONG_DRIVE_SPEED));
            addSequential(new TurnToAngleAuton(angleForSide(90)));
            addSequential(new DriveDistanceAuton(70, RobotMap.AUTON_LONG_DRIVE_SPEED));
            addSequential(new TurnToAngleAuton(angleForSide(119)));
            // TODO: Recompute this with the 2-ft offset
        } else {
            addSequential(new TurnToAngleAuton(angleForSide(63)));
            addSequential(new DriveDistanceAuton(111, RobotMap.AUTON_LONG_DRIVE_SPEED));
        }
        addSequential(new MiddleWheelAdjusterAuton());
        addSequential(new SetLiftAuton(pos));
        addSequential(new DeployIntakeAuton());
        addSequential(new ApproachVisionTargetAuton(34));
        addSequential(new ReleaseHatchPanelAuton());
    }

    /**
     * Converts an angle to the appropriate (positive/negative) value for the selected side.
     * @param angle the angle to convert.
     */
    private int angleForSide(int angle) {
        if (this.side == RobotMap.Side.LEFT) {
            return angle * -1;
        } else {
            return angle;
        }
    }
}
