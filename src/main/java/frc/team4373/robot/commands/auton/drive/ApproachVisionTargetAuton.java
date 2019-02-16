package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

public class ApproachVisionTargetAuton extends Command {
    private Drivetrain drivetrain;
    private double targetDistanceFromVisionTarget;
    private int samples = 0;
    private double distanceSum = 0;
    private boolean finished = false;

    /**
     * Constructs a command to approach a vision target up to a specified distance AWAY from it.
     * @param distance the distance, in inches, away from the target to achieve.
     */
    public ApproachVisionTargetAuton(double distance) {
        requires(this.drivetrain = Drivetrain.getInstance());
        this.targetDistanceFromVisionTarget = distance;
    }

    @Override
    protected void execute() {
        double currentDistance = SmartDashboard.getNumber("forward_distance_to_target", 0);
        if (this.samples < RobotMap.VISION_SAMPLE_COUNT) {
            this.distanceSum += currentDistance;
            ++this.samples;
        } else {
            double averageCurrentDistance = this.distanceSum / RobotMap.VISION_SAMPLE_COUNT;
            double distanceToDrive = averageCurrentDistance - this.targetDistanceFromVisionTarget;
            Scheduler.getInstance().add(new DriveDistanceAuton(
                    distanceToDrive, RobotMap.AUTON_VISION_APPROACH_SPEED));
            this.finished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.drivetrain.setLightRing(false);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
