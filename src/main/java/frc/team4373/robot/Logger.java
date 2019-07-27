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
    private volatile double[] buffer;
    private volatile boolean isLogging;
    private volatile boolean enabled;

    /**
     * Constructs a new Logger.
     */
    public Logger() {
        this.drivetrain = Drivetrain.getInstance();
        this.buffer = new double[BUFFER_SIZE];
        this.idx = 0;
        this.enabled = true;
        this.isLogging = false;
    }

    @Override
    public void run() {
        int sleepTime = 1000 / SAMPLES_PER_SEC;
        while (enabled) {
            if (this.isLogging) {
                // If we're going to overflow, stop logging and wait for termination/reset
                if (this.idx + NUM_DATA_PTS - 1 > BUFFER_SIZE) this.isLogging = false;
                buffer[this.idx++] = Timer.getFPGATimestamp();
                buffer[this.idx++] = this.drivetrain.getPercentOutput(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getSensorVelocity(Drivetrain.TalonID.RIGHT_1);
                buffer[this.idx++] = this.drivetrain.getPercentOutput(Drivetrain.TalonID.LEFT_1);
                buffer[this.idx++] = this.drivetrain.getSensorPosition(Drivetrain.TalonID.LEFT_1);
                buffer[this.idx++] = this.drivetrain.getSensorVelocity(Drivetrain.TalonID.LEFT_1);
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
     *
     * <p>Writes to the logging flag are <b>not</b> atomic; only ONE thread should take ownership
     * of logging state modifications.
     */
    public void startLogging() {
        this.isLogging = true;
    }

    /**
     * Disables the logging flag.
     * 
     * <p>Writes to the logging flag are <b>not</b> atomic; only ONE thread should take ownership
     * of logging state modifications.
     */
    public void stopLogging() {
        this.isLogging = false;
    }

    /**
     * Gets the buffer of logged values.
     * @return the buffer of size {@link #BUFFER_SIZE}.
     */
    public double[] getBuffer() {
        return this.buffer;
    }

    /**
     * Clears buffer entries.
     */
    public void flushBuffer() {
        this.buffer = new double[BUFFER_SIZE];
    }

    /**
     * Terminates the logging thread.
     */
    public void exitThread() {
        this.enabled = false;
    }
}
