package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.Logger;
import frc.team4373.robot.subsystems.Drivetrain;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RunLogCommand extends Command {
    private Drivetrain drivetrain;
    private Logger logger;

    private boolean shouldSetLeft;
    private boolean shouldSetRight;

    private double startTime = -1;

    public RunLogCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
        this.logger = new Logger();
    }

    @Override
    protected void initialize() {
        this.logger = new Logger();
        this.drivetrain.zeroMotors();
        this.startTime = -1; // reset in case the command gets reused

        SendableChooser<String> modeChooser = (SendableChooser<String>) SmartDashboard.getData("Log Type");
        String mode = modeChooser.getSelected();
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
        }
    }

    @Override
    protected void execute() {
        double now = Timer.getFPGATimestamp();
        if (this.startTime < 0) {
            // We're in the "grace period:" spin up the logger and prepare
            this.startTime = now;
            this.logger.startLogging();
        } else if (now - this.startTime >= 0.5 && now - this.startTime <= 4) {
            // We're ready to start logging. Spin up the appropriate motors.
            this.drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1, shouldSetLeft ? 1 : 0);
            this.drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1, shouldSetRight ? 1 : 0);
        } else if (now - this.startTime <= 4.5) {
            // We're in the closing "grace period:" stop the motors and continue logging
            this.drivetrain.zeroMotors();
        } else {
            this.logger.stopLogging();
        }
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - this.startTime <= 4.5;
    }

    @Override
    protected void end() {
        this.logger.stopLogging();
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
        } finally {
            this.logger.exitThread();
        }
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
