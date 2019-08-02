package frc.team4373.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerProcessor {
    /**
     * Captures the log from the specified {@link Logger} and writes it to a CSV.
     * @param logger the Logger whose log to write.
     */
    public static void writeLogToFile(Logger logger) {
        StringBuilder builder = new StringBuilder();
        builder.append(Logger.CSV_HEADERS);

        double[] output = logger.getBuffer();
        if (output.length == 0) {
            DriverStation.reportWarning("Length of logger output is 0. "
                    + "Did you try to process it while the run loop was still executing?", false);
        }
        for (int i = 0; i < output.length; ++i) {
            if (i % output.length == 0) {
                builder.append("\n");
            }
            builder.append(output[i]);
            if (i % output.length != output.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("\n"); // end with a trailing newline

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd_HHmmss");
        String formattedDate = simpleDateFormat.format(date);
        Path outPath = Path.of(System.getenv().get("HOME"),
                String.format("/log%s.txt", formattedDate));
        try {
            Files.writeString(outPath, builder.toString());
        } catch (Exception exc) {
            DriverStation.reportError("Failed to write log file.", false);
            exc.printStackTrace();
        }
    }
}
