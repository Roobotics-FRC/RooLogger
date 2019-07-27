package frc.team4373.robot;

import edu.wpi.first.wpilibj.DriverStation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerProcessor {
    public static void writeLogToFile(Logger logger) {
        StringBuilder builder = new StringBuilder();
        builder.append(Logger.CSV_HEADERS);

        double[] output = logger.getBuffer();
        for (int i = 0; i < output.length; ++i) {
            if (i % Logger.BUFFER_SIZE == 0) {
                builder.append("\n");
            }
            builder.append(output[i]);
            if (i % Logger.BUFFER_SIZE != Logger.BUFFER_SIZE - 1) {
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
