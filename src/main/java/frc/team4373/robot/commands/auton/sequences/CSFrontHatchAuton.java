package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.ApproachVisionTargetAuton;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.intake.CollectHatchPanelAuton;
import frc.team4373.robot.commands.auton.intake.DeployIntakeAuton;
import frc.team4373.robot.commands.auton.intake.ReleaseHatchPanelAuton;
import frc.team4373.robot.commands.auton.lift.SetLiftAuton;

public class CSFrontHatchAuton extends CommandGroup {
    /**
     * Constructs a new auton command to place a hatch panel on the front cargo ship hatch.
     */
    public CSFrontHatchAuton() {
        addParallel(new CollectHatchPanelAuton());
        addSequential(new DriveDistanceAuton(96, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new MiddleWheelAdjusterAuton());
        addSequential(new SetLiftAuton(SetLiftAuton.Position.HATCH_1));
        addSequential(new DeployIntakeAuton());
        addSequential(new ApproachVisionTargetAuton(34));
        addSequential(new ReleaseHatchPanelAuton());
    }
}
