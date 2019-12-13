package frc.team4373.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team4373.robot.subsystems.LoggableDrivetrain;

import java.util.Arrays;

public class Logger implements Runnable {
    public static final int NUM_DATA_PTS = 8;
    public static final int SAMPLES_PER_SEC = 1000;

    public static final String CSV_HEADERS =
            "Time (s), L Output (%), L Pos (ticks), L Vel (ticks), "
            + "R Output (%), R Pos (ticks), R Vel (ticks), Heading (deg)";

    private LoggableDrivetrain drivetrain;
    private int idx = 0;
    private double[] buffer = new double[0];

    private final Object bufferLock = new Object();

    private volatile double duration = 0;
    private volatile boolean enabled = false;

    /**
     * Constructs a new Logger.
     */
    public Logger(LoggableDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void run() {
        int sleepTime = 1000 / SAMPLES_PER_SEC;
        synchronized (this.bufferLock) {
            while (this.enabled) {
                // If we're going to overflow, stop logging and wait for termination/reset
                if (this.idx + NUM_DATA_PTS - 1 > buffer.length) {
                    continue;
                }
                buffer[this.idx++] = Timer.getFPGATimestamp();
                buffer[this.idx++] = this.drivetrain.getLeftPercent();
                buffer[this.idx++] = this.drivetrain.getLeftPosition();
                buffer[this.idx++] = this.drivetrain.getLeftVelocity();
                buffer[this.idx++] = this.drivetrain.getRightPercent();
                buffer[this.idx++] = this.drivetrain.getRightPosition();
                buffer[this.idx++] = this.drivetrain.getRightVelocity();
                buffer[this.idx++] = this.drivetrain.getYaw();
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception exc) {
                    DriverStation.reportError("Failed to sleep in logger thread", false);
                    exc.printStackTrace();
                }
            }
        }
    }

    /**
     * Sets the duration for which the logger will log and resets the logger.
     *
     * <p>Note that this method must be called before starting the logger,
     *    as the default duration is 0.
     *    Also, it is unnecessary (and wasteful) to call {@link #reset()} after this method;
     *    this method fully resets the logger.
     *
     * @param duration the duration for which to log, in seconds.
     */
    public void resetDuration(double duration) {
        if (!this.enabled) {
            this.duration = duration;
            resizeBuffer();
            this.enabled = true;
        } else {
            DriverStation.reportError("An attempt was made to modify the Logger duration "
                    + "while already enabled.", false);
        }
    }

    /**
     * Stops logging and exits the thread.
     *
     * <p>Note that this method <b>must</b> be called before attempting to
     *    fetch the buffer via {@link #getBuffer()}.
     */
    public void stop() {
        this.enabled = false;
    }

    /**
     * Gets the buffer of logged values.
     *
     * <p>Note that <b>this must not be called before calling {@link #stop()}</b>.
     *    For safety, this method will return an empty array if the enabled flag is still true.
     *
     * @return the log buffer if safe to fetch; empty array otherwise.
     */
    public double[] getBuffer() {
        if (!this.enabled) {
            synchronized (this.bufferLock) {
                return this.buffer;
            }
        } else {
            DriverStation.reportError("An attempt was made to retrieve the Logger buffer "
                    + "while already enabled.", false);
            return new double[0];
        }
    }

    /**
     * Clears all entries in the logging buffer.
     *
     * <p><b>Only</b> call this method when the Logger has stopped.
     *    If the logger is not stopped, this method will not flush the buffer.
     */
    public void flushBuffer() {
        if (!this.enabled) {
            synchronized (this.bufferLock) {
                Arrays.fill(buffer, 0);
            }
        } else {
            DriverStation.reportError("An attempt was made to flush the Logger buffer "
                    + "while already enabled.", false);
        }
    }

    /**
     * Resets the Logger for reuse.
     *
     * <p><b>Only</b> call this method when the Logger has stopped.
     *    If the logger is not stopped, this method will not reset the Logger.
     */
    public void reset() {
        if (!this.enabled) {
            this.flushBuffer();
            this.enabled = true;
        } else {
            DriverStation.reportError("An attempt was made to reset Logger while already enabled.",
                    false);
        }
    }

    /**
     * Creates a new zeroed buffer array based on the duration, sample count, and data point count.
     */
    private void resizeBuffer() {
        int bufferSize = (int) Math.ceil(duration) * SAMPLES_PER_SEC * NUM_DATA_PTS;
        this.buffer = new double[bufferSize];
    }
}
