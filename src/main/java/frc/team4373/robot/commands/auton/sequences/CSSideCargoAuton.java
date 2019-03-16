package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.ApproachVisionTargetAuton;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;
import frc.team4373.robot.commands.auton.intake.CollectCargoAuton;
import frc.team4373.robot.commands.auton.intake.DeployIntakeAuton;
import frc.team4373.robot.commands.auton.intake.ReleaseCargoAuton;
import frc.team4373.robot.commands.auton.lift.SetLiftAuton;

public class CSSideCargoAuton extends CommandGroup {

    /**
     * Constructs a new CSSideCargoAuton command group.
     * @param side the side on which the robot is starting (right or left ONLY).
     * @param portLoc the port (near, middle, far) to go for.
     */
    public CSSideCargoAuton(RobotMap.Side side, RobotMap.CargoShipPort portLoc) {
        double angleTurn = 90;
        double driveDistance;
        switch (side) {
            case RIGHT:
                angleTurn *= -1;
                break;
            default:
                angleTurn *= 1;
                break;
        }
        switch (portLoc) {
            case NEAR:
                driveDistance = 156;
                break;
            case MIDDLE:
                driveDistance = 177;
                break;
            case FAR:
                driveDistance = 198;
                break;
            default:
                return;
        }
        addParallel(new CollectCargoAuton());
        addSequential(new DriveDistanceAuton(driveDistance, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new TurnToAngleAuton(angleTurn));
        addSequential(new MiddleWheelAdjusterAuton());
        addSequential(new SetLiftAuton(SetLiftAuton.Position.CARGO_SHIP));
        addSequential(new DeployIntakeAuton());
        addSequential(new ApproachVisionTargetAuton(36));
        addSequential(new ReleaseCargoAuton());
    }
}
