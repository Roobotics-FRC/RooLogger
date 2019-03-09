package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

public class DriveStraightCommand extends PIDCommand {
    private static final long COOLDOWN = 500;

    private long lastManualOp = 0;

    private Drivetrain drivetrain;

    /**
     * Constructs a new teleop drive command that drives straight with PID.
     */
    public DriveStraightCommand() {
        super("DriveStraightCommand", RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI, RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD);
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        // Angular PID configuration
        this.setSetpoint(drivetrain.getPigeonYaw());
        this.getPIDController().setOutputRange(-1, 1);
    }

    @Override
    protected double returnPIDInput() {
        return this.drivetrain.getPigeonYaw();
    }

    @Override
    protected void usePIDOutput(double angleOutput) {
        double joyZ = OI.getOI().getDriveJoystick().rooGetZFiltered();
        double joyX = OI.getOI().getDriveJoystick().rooGetX();
        double joyY = OI.getOI().getDriveJoystick().rooGetY();

        boolean driveStraightOverride = OI.getOI().getDriveJoystick().getRawButton(
                RobotMap.DRIVER_BUTTON_DRIVE_STRAIGHT);

        double rightOutput;
        double leftOutput;
        if (driveStraightOverride
                || (joyZ == 0 && System.currentTimeMillis() > lastManualOp + COOLDOWN)) {
            rightOutput = Robot.constrainPercentOutput(joyY - angleOutput);
            leftOutput = Robot.constrainPercentOutput(joyY + angleOutput);
        } else {
            rightOutput = Robot.constrainPercentOutput(joyY + joyZ);
            leftOutput = Robot.constrainPercentOutput(joyY - joyZ);
            if (joyZ != 0) this.lastManualOp = System.currentTimeMillis();
        }
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1, rightOutput);
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1, leftOutput);
        if (!driveStraightOverride) {
            switch (OI.getOI().getDriveJoystick().getPOV()) {
                case 315:
                case 0:
                case 45:
                    drivetrain.retractMiddleWheel();
                    break;
                case 135:
                case 180:
                case 225:
                    drivetrain.deployMiddleWheel();
                    break;
                default:
                    break;
            }
            this.drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1, joyX);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
