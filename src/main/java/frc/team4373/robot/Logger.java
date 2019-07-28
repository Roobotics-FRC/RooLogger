package frc.team4373.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team4373.robot.subsystems.Drivetrain;

public class Logger implements Runnable {
    public static final int NUM_DATA_PTS = 8;
    public static final int SAMPLE_TIME_SECS = 4;
    public static final int SAMPLES_PER_SEC = 1000;

    public static final int BUFFER_SIZE = SAMPLE_TIME_SECS * SAMPLES_PER_SEC * NUM_DATA_PTS;
    public static final String CSV_HEADERS =
            "Time (s), L Output (%), L Pos (ticks), L Vel (ticks), "
            + "R Output (%), R Pos (ticks), R Vel (ticks), Heading (deg)";

    private Drivetrain drivetrain;
    private int idx;
    // TODO: Array volatility is useless for thread-safety
    private volatile double[] buffer;

    private volatile boolean isEnabled;
    private volatile boolean isLogging;

    /**
     * Constructs a new Logger.
     */
    public Logger() {
        this.drivetrain = Drivetrain.getInstance();
        this.buffer = new double[BUFFER_SIZE];
        this.idx = 0;
        this.isEnabled = true;
        this.isLogging = false;
    }

    @Override
    public void run() {
        int sleepTime = 1000 / SAMPLES_PER_SEC;
        while (this.isEnabled) {
            if (this.isLogging) {
                // If we're going to overflow, stop logging and wait for termination/reset
                if (this.idx + NUM_DATA_PTS - 1 > BUFFER_SIZE) {
                    this.isLogging = false;
                    continue;
                }
                buffer[this.idx++] = Timer.getFPGATimestamp();
                buffer[this.idx++] = this.drivetrain.getPercentOutput(Drivetrain.TalonID.LEFT_1);
                buffer[this.idx++] = this.drivetrain.getSensorPosition(Drivetrain.TalonID.LEFT_1);
                buffer[this.idx++] = this.drivetrain.getSensorVelocity(Drivetrain.TalonID.LEFT_1);
                buffer[this.idx++] = this.drivetrain.getPercentOutput(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getSensorVelocity(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getPigeonYaw();
            }
            try {
                Thread.sleep(sleepTime);
            } catch (Exception exc) {
                DriverStation.reportError("Failed to sleep in logger thread", false);
                exc.printStackTrace();
            }
        }
    }

    /**
     * Enables the logging flag.
     */
    public void startLogging() {
        this.isLogging = true;
    }

    /**
     * Stops logging and exits the thread.
     */
    public void stop() {
        this.isLogging = false;
        this.isEnabled = false;
    }

    /**
     * Gets the buffer of logged values.
     * @return the buffer of size {@link #BUFFER_SIZE}.
     */
    public double[] getBuffer() {
        return this.buffer;
    }

    /**
     * Clears all entries in the logging buffer.
     */
    public void flushBuffer() {
        this.buffer = new double[BUFFER_SIZE];
    }

    /**
     * Resets the Logger for reuse.
     */
    public void reset() {
        this.flushBuffer();
        this.isLogging = false;
        this.isEnabled = true;
    }
}
