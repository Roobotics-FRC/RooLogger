package frc.team4373.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team4373.robot.subsystems.Drivetrain;

import java.util.Arrays;

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
    private final double[] buffer;

    private volatile boolean enabled;

    /**
     * Constructs a new Logger.
     */
    public Logger() {
        this.drivetrain = Drivetrain.getInstance();
        this.buffer = new double[BUFFER_SIZE];
        this.idx = 0;
        this.enabled = true;
    }

    @Override
    public void run() {
        int sleepTime = 1000 / SAMPLES_PER_SEC;
        synchronized (this.buffer) {
            while (this.enabled) {
                // If we're going to overflow, stop logging and wait for termination/reset
                if (this.idx + NUM_DATA_PTS - 1 > BUFFER_SIZE) {
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
     * @return the buffer of size {@link #BUFFER_SIZE} if safe to fetch; empty array otherwise.
     */
    public double[] getBuffer() {
        if (!this.enabled) {
            synchronized (this.buffer) {
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
            synchronized (this.buffer) {
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
}
