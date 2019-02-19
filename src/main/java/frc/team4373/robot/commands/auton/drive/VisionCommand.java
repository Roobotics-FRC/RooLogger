package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

public abstract class VisionCommand extends Command {

    private Drivetrain drivetrain;

    private String dashboardId;

    private int sampleCount = 0;
    private double distanceSum = 0;
    private double averageSetpoint = 0;
    private boolean readyForPID = false;
    private boolean finished = false;

    /**
     * Constructs a vision-based auton command.
     * @param name the name of the command.
     * @param smartDashboardId the ID published by the vision script on NetworkTables to query.
     */
    public VisionCommand(String name, String smartDashboardId) {
        super(name);
        requires(this.drivetrain = Drivetrain.getInstance());
        this.dashboardId = smartDashboardId;
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
            distanceSum += SmartDashboard.getNumber(dashboardId, 0);
            ++sampleCount;
        } else if (!readyForPID) { // setpoint setting state
            this.drivetrain.setLightRing(false);
            this.averageSetpoint = distanceSum / RobotMap.VISION_SAMPLE_COUNT;
            if (cameraValueIsAcceptable(this.averageSetpoint)) {
                this.finished = true;
            } else {
                this.readyForPID = true;
            }
        } else { // PID execution state—NEEDS to be separate so that we can reset the state
            this.drivetrain.setLightRing(false);
            resetState();
            // if uACV creates a new command, initialize() will be called again
            useAverageCameraValue(this.averageSetpoint);
        }
    }

    protected abstract boolean cameraValueIsAcceptable(double value);

    protected abstract void useAverageCameraValue(double value);

    protected Drivetrain getDrivetrain() {
        return this.drivetrain;
    }

    protected void resetState() {
        this.sampleCount = 0;
        this.distanceSum = 0;
        this.readyForPID = false;
        this.finished = false;
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }
}
