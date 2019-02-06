package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Drives a provided distance in a straight line at a given speed.
 */
public class DriveDistanceAuton extends PIDCommand {
    private PIDController distancePIDController;
    private PIDSource distanceSource;
    private PIDOutput distanceOutputLambda;

    // should be WHEEL_DIAMETER * GEARBOX_RATIO * pi / 4096
    public static final double POSITION_CONVERSION_FACTOR = 3 * 5.95 * Math.PI / 4096;

    private double setpointRelative;
    private double distancePIDOutput = 1d;
    private double robotSpeed = 0.25d;
    private double cooldownThreshold = 0.0625;

    private Drivetrain drivetrain;

    /**
     * Constructs a new DriveDistanceAuton command and initializes the secondary PID controller.
     * @param distance The distance, in inches, that the robot should drive.
     */
    public DriveDistanceAuton(double distance, double speed) {
        super("DriveDistanceAuton", RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD);
        this.setpointRelative = distance / POSITION_CONVERSION_FACTOR;
        this.robotSpeed = speed;
        this.cooldownThreshold = this.robotSpeed * 0.25;
        requires(this.drivetrain = Drivetrain.getInstance());

        // Distance PID initialization
        distanceSource = new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                return;
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return getAveragePosition();
            }
        };

        distanceOutputLambda = output -> {
            this.distancePIDOutput = output;
        };
        this.distancePIDController = new PIDController(RobotMap.DRIVETRAIN_DIST_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_DIST_PID_GAINS.kI, RobotMap.DRIVETRAIN_DIST_PID_GAINS.kD,
                RobotMap.DRIVETRAIN_DIST_PID_GAINS.kF, distanceSource, distanceOutputLambda);
    }

    @Override
    protected void initialize() {
        // Distance PID configuration
        this.distancePIDController.setOutputRange(-robotSpeed, robotSpeed);
        this.distancePIDController.setSetpoint(getAveragePosition()
                + setpointRelative);
        this.distancePIDController.enable();
        this.distancePIDOutput = 1;

        // Angular PID configuration
        this.setSetpoint(drivetrain.getPigeonYaw());
        this.getPIDController().setOutputRange(-robotSpeed, robotSpeed);
    }

    @Override
    protected double returnPIDInput() {
        return drivetrain.getPigeonYaw();
    }

    @Override
    protected void usePIDOutput(double angleOutput) {
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1,
                distancePIDOutput + angleOutput);
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1,
                distancePIDOutput - angleOutput);
    }

    private int getAveragePosition() {
        return (this.drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1)
                + this.drivetrain.getSensorPosition(Drivetrain.TalonID.LEFT_1)) / 2;
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(this.distancePIDOutput) < cooldownThreshold;
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        this.distancePIDController.reset();
        this.drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}