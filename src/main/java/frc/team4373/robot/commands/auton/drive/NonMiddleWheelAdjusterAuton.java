package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.ToggleLightRingCommand;
import frc.team4373.robot.subsystems.Drivetrain;

public class NonMiddleWheelAdjusterAuton extends Command {
    private boolean finished = false;
    private Drivetrain drivetrain;
    private int sampleCount = 0;
    private double distanceSum = 0;
    private boolean readyForPID = false;

    private double distanceToTravel = 0;
    private boolean moveToRight = false;

    public NonMiddleWheelAdjusterAuton() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.drivetrain.setLightRing(true);
        resetState();
    }

    @Override
    protected void execute() {
        if (sampleCount < RobotMap.VISION_SAMPLE_COUNT) { // polling state
            this.drivetrain.setLightRing(true);
            // TODO: Check whether light ring turns on fast enough for the first fetch to be valid
            distanceSum += SmartDashboard.getNumber("lateral_distance_to_target", 0);
            ++sampleCount;
        } else if (!readyForPID) { // setpoint setting state
            this.drivetrain.setLightRing(false);
            double setpointInches = distanceSum / RobotMap.VISION_SAMPLE_COUNT;
            if (setpointInches < RobotMap.ALLOWABLE_OFFSET_FROM_VIS_TARGET) {
                this.finished = true;
            } else {
                this.distanceToTravel = setpointInches;
                this.moveToRight = setpointInches > 0;
                this.drivetrain.setLightRing(false);
                this.readyForPID = true;
            }
        } else { // PID execution state
            this.drivetrain.setLightRing(false); // safety
            resetState();
            Scheduler.getInstance().add(new NonMiddleWheelAdjustmentGroup(this.distanceToTravel,
                    this.moveToRight));
        }
    }

    private void resetState() {
        this.sampleCount = 0;
        this.distanceSum = 0;
        this.readyForPID = false;
        this.finished = false;
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
