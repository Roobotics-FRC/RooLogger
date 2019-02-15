package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.elemental.DeployIntakeAuton;

public class DriveForwardAuton extends CommandGroup {
    public DriveForwardAuton() {
        addParallel(new DeployIntakeAuton());
        addSequential(new DriveDistanceAuton(60, RobotMap.AUTON_LONG_DRIVE_SPEED));
    }
}
