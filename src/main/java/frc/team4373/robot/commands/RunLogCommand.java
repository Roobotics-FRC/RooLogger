package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.logging.Logger;
import frc.team4373.robot.logging.LoggerProcessor;
import frc.team4373.robot.subsystems.LoggableDrivetrain;

public class RunLogCommand extends Command {
    private static double FP_EQUALITY_THRESHOLD = 1e-5;

    private LoggableDrivetrain drivetrain;
    private Logger logger;
    private Thread loggerThread;

    private boolean shouldSetLeft;
    private boolean shouldSetRight;
    private double velocity = 0;
    private double duration = 0;

    private double startTime = -1;

    public RunLogCommand(LoggableDrivetrain drivetrain) {
        requires(this.drivetrain = drivetrain);
        this.logger = new Logger(drivetrain);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initialize() {
        double oldDuration = this.duration;
        this.duration = SmartDashboard.getNumber("Log Duration (s)", 0);
        // if there's no change in duration, it's far more efficient not to overwrite the array
        if (Math.abs(oldDuration - this.duration) < FP_EQUALITY_THRESHOLD) {
            this.logger.reset();
        } else {
            this.logger.resetDuration(this.duration);
        }
        this.loggerThread = new Thread(this.logger);

        this.drivetrain.setLeft(0);
        this.drivetrain.setRight(0);
        this.startTime = -1; // reset in case the command gets reused

        SendableChooser<String> modeChooser;
        String mode;
        try {
            modeChooser = (SendableChooser<String>) SmartDashboard.getData("Log Type");
            mode = modeChooser.getSelected();
        } catch (ClassCastException exc) {
            DriverStation.reportError("No log type chooser found, or invalid data received.",
                    false);
            exc.printStackTrace();
            this.shouldSetLeft = false;
            this.shouldSetRight = false;
            return;
        }
        switch (mode) {
            case "left_only":
                this.shouldSetLeft = true;
                this.shouldSetRight = false;
                break;
            case "right_only":
                this.shouldSetLeft = false;
                this.shouldSetRight = true;
                break;
            case "both_sides":
                this.shouldSetLeft = true;
                this.shouldSetRight = true;
                break;
            default:
                this.shouldSetLeft = false;
                this.shouldSetRight = false;
                DriverStation.reportError("Invalid log type selection found.", false);
        }
        this.velocity = SmartDashboard.getNumber("Log Speed", 0);

        this.loggerThread.start();
    }

    @Override
    protected void execute() {
        double now = Timer.getFPGATimestamp();
        if (this.startTime < 0) {
            // We're in the "grace period:" spin up the logger and prepare
            this.startTime = now;
        } else if (now - this.startTime >= 0.5 && now - this.startTime <= duration - 0.5) {
            // We're ready to start logging. Spin up the appropriate motors
            this.drivetrain.setLeft(this.shouldSetLeft ? this.velocity : 0);
            this.drivetrain.setRight(this.shouldSetRight ? this.velocity : 0);
        } else if (now - this.startTime <= duration) {
            // We're in the closing "grace period:" stop the motors and continue logging
            this.drivetrain.setLeft(0);
            this.drivetrain.setRight(0);
        } else {
            // We're finished: stop the logger
            this.logger.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - this.startTime >= duration;
    }

    @Override
    protected void end() {
        this.logger.stop();
        LoggerProcessor.writeLogToFile(this.logger);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
