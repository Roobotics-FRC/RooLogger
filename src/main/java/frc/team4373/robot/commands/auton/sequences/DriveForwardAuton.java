package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;

public class DriveForwardAuton extends CommandGroup {
    public DriveForwardAuton() {
        addSequential(new DriveDistanceAuton(60, RobotMap.AUTON_LONG_DRIVE_SPEED));
    }
}
