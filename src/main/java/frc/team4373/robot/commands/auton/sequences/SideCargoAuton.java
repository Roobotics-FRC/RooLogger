package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;
import frc.team4373.robot.commands.auton.elemental.ReleaseCargoAuton;

public class SideCargoAuton extends CommandGroup {

    public SideCargoAuton(RobotMap.Side side, RobotMap.CargoShipPort portLoc) {
        double angleTurn = 90;
        double driveDistance;
        switch (side) {
            case RIGHT:
                angleTurn *= -1;
            default:
                angleTurn *= 1;
        }
        switch (portLoc) {
            case NEAR:
                driveDistance = 180;
                break;
            case MIDDLE:
                driveDistance = 201;
                break;
            case FAR:
                driveDistance = 222;
                break;
            default:
                return;
        }
        addSequential(new DriveDistanceAuton(driveDistance, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new TurnToAngleAuton(angleTurn));
        addSequential(new MiddleWheelAdjusterAuton());
        // TODO: Use vision to detect distance
        addSequential(new ReleaseCargoAuton());
    }
}
