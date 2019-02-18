package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

public class ApproachVisionTargetAuton extends Command {
    private Drivetrain drivetrain;
    private double targetDistanceFromVisionTarget;
    private int sampleCount = 0;
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
    protected void initialize() {
        this.drivetrain.setLightRing(true);
        this.sampleCount = 0;
        this.distanceSum = 0;
    }

    @Override
    protected void execute() {
        if (this.sampleCount < RobotMap.VISION_SAMPLE_COUNT) { // polling state
            this.drivetrain.setLightRing(true);
            // TODO: Check whether light ring turns on fast enough for the first fetch to be valid
            double currentDistance = SmartDashboard.getNumber("forward_distance_to_target", 0);
            this.distanceSum += currentDistance;
            ++this.sampleCount;
        } else { // acting on distance state
            this.drivetrain.setLightRing(false);
            double averageCurrentDistance = this.distanceSum / RobotMap.VISION_SAMPLE_COUNT;
            double distanceToDrive = averageCurrentDistance - this.targetDistanceFromVisionTarget;
            if (distanceToDrive < RobotMap.ALLOWABLE_ERR_DISTANCE_TO_VIS_TARGET) {
                this.finished = true;
            } else {
                this.sampleCount = 0;
                this.distanceSum = 0;
                Scheduler.getInstance().add(new DriveDistanceAuton(
                        distanceToDrive, RobotMap.AUTON_VISION_APPROACH_SPEED));
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.drivetrain.setLightRing(false); // safety
        this.drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
