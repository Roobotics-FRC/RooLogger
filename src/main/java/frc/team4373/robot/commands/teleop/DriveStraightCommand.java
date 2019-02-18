package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

public class DriveStraightCommand extends PIDCommand {
    private PIDController velocityPIDController;
    private PIDSource velocitySource;
    private PIDOutput velocityOutputLambda;
    private double velocityPIDOutput;

    private long lastManualOp = 0;

    private Drivetrain drivetrain;

    /**
     * Constructs a new teleop drive command that drives straight with PID.
     */
    public DriveStraightCommand() {
        super("DriveStraightCommand", RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI, RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD);
        requires(this.drivetrain = Drivetrain.getInstance());

        // Velocity PID initialization
        velocitySource = new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                return;
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kRate;
            }

            @Override
            public double pidGet() {
                return getAverageVelocity();
            }
        };

        velocityOutputLambda = output -> {
            this.velocityPIDOutput = output;
        };

        this.velocityPIDController = new PIDController(RobotMap.DRIVETRAIN_DIST_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_DIST_PID_GAINS.kI, RobotMap.DRIVETRAIN_DIST_PID_GAINS.kD,
                velocitySource, velocityOutputLambda);
    }

    @Override
    protected void initialize() {
        // Distance PID configuration
        this.velocityPIDController.setOutputRange(-1, 1);
        this.velocityPIDController.setSetpoint(0);
        this.velocityPIDController.enable();
        this.velocityPIDOutput = 0;

        // Angular PID configuration
        this.setSetpoint(drivetrain.getPigeonYaw());
        this.getPIDController().setOutputRange(-1, 1);
    }

    @Override
    protected double returnPIDInput() {
        double percentVelocity = OI.getOI().getDriveJoystick().rooGetY();
        this.velocityPIDController.setSetpoint(
                percentVelocity * RobotMap.DRIVETRAIN_ENC_VEL_TO_INPS);
        return this.drivetrain.getPigeonYaw();
    }

    @Override
    protected void usePIDOutput(double angleOutput) {
        double joyZ = Math.signum(OI.getOI().getDriveJoystick().rooGetZ())
                * Math.sqrt(Math.abs(OI.getOI().getDriveJoystick().rooGetZ())) / 2;
        double joyX = OI.getOI().getDriveJoystick().rooGetX();
        double joyY = OI.getOI().getDriveJoystick().rooGetY();

        double rightOutput;
        double leftOutput;
        if (joyZ == 0 && System.currentTimeMillis() > lastManualOp + 500000) {
            rightOutput = Robot.constrainPercentOutput(this.velocityPIDOutput + angleOutput);
            leftOutput = Robot.constrainPercentOutput(this.velocityPIDOutput - angleOutput);
        } else {
            rightOutput = Robot.constrainPercentOutput(joyY + joyZ);
            leftOutput = Robot.constrainPercentOutput(joyY - joyZ);
            this.lastManualOp = System.currentTimeMillis();
        }
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1, rightOutput);
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1, leftOutput);
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.MIDDLE_1, joyX);
    }

    private double getAverageVelocity() {
        return (this.drivetrain.getSensorVelocity(Drivetrain.TalonID.LEFT_1)
                + this.drivetrain.getSensorVelocity(Drivetrain.TalonID.RIGHT_1)) / 2;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
