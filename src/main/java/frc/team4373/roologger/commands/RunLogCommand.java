package frc.team4373.roologger.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.roologger.logging.Logger;
import frc.team4373.roologger.logging.LoggerProcessor;
import frc.team4373.roologger.subsystems.LoggableDrivetrain;

public class RunLogCommand extends Command {
    private LoggableDrivetrain drivetrain;
    private Logger logger;
    private Thread loggerThread;

    private boolean shouldSetLeft;
    private boolean shouldSetRight;
    private double velocity;
    private double duration;

    private double startTime = -1;

    /**
     * Constructs a new RunLogCommand.
     * @param drivetrain the drivetrain to log.
     * @param duration the duration, in seconds, for which to log.
     * @param velocity the percent output, -1 to 1, at which to run the motors during logging.
     * @param runLeft whether to run the left motors during logging.
     * @param runRight whether to run the right motors during logging.
     */
    public RunLogCommand(LoggableDrivetrain drivetrain, double duration,
                         double velocity, boolean runLeft, boolean runRight) {
        requires(this.drivetrain = drivetrain);
        this.logger = new Logger(drivetrain);
        this.duration = duration;
        this.velocity = velocity;
        this.shouldSetLeft = runLeft;
        this.shouldSetRight = runRight;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initialize() {
        this.logger.reset();
        this.loggerThread = new Thread(this.logger);

        this.drivetrain.setLeft(0);
        this.drivetrain.setRight(0);
        this.startTime = -1; // reset in case the command gets reused

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
