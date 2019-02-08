package frc.team4373.robot.commands.auton.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

public class ApproachVisionTargetAuton extends PIDCommand {
    private Drivetrain drivetrain;
    private PIDController distanceController;

    private double targetDistanceFromVisionTarget;
    private double distancePIDOutput;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 500;
    private static final double COOLDOWN_THRESHOLD = RobotMap.AUTON_VISION_APPROACH_SPEED * 0.25;

    private int visionErrors;

    /**
     * Initializes a command to approach a vision target up to a set distance.
     * @param distance the distance away from the vision target to reach.
     */
    public ApproachVisionTargetAuton(double distance) {
        super("ApproachVisionTargetAuton", RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI, RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kF);
        requires(this.drivetrain = Drivetrain.getInstance());
        this.targetDistanceFromVisionTarget = distance;

        PIDSource source = new PIDSource() {
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
                return SmartDashboard.getNumber("distance_to_target", 0);
            }
        };

        PIDOutput outputLambda = output -> {
            this.distancePIDOutput = output;
        };

        this.distanceController = new PIDController(RobotMap.DRIVETRAIN_DIST_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_DIST_PID_GAINS.kI, RobotMap.DRIVETRAIN_DIST_PID_GAINS.kD,
                RobotMap.DRIVETRAIN_DIST_PID_GAINS.kF, source, outputLambda);
    }

    @Override
    protected void initialize() {
        this.finished = this.coolingDown = false;

        this.distanceController.setOutputRange(-RobotMap.AUTON_VISION_APPROACH_SPEED,
                RobotMap.AUTON_VISION_APPROACH_SPEED);
        this.distanceController.setSetpoint(targetDistanceFromVisionTarget);
        this.distancePIDOutput = RobotMap.AUTON_VISION_APPROACH_SPEED;
        this.distanceController.enable();

        this.setSetpoint(drivetrain.getPigeonYaw());
        this.getPIDController().setOutputRange(-RobotMap.AUTON_VISION_APPROACH_SPEED,
                RobotMap.AUTON_VISION_APPROACH_SPEED);
    }

    @Override
    protected double returnPIDInput() {
        return drivetrain.getPigeonYaw();
    }

    @Override
    protected void usePIDOutput(double angleOutput) {
        if (coolingDown) {
            if (System.currentTimeMillis() - COOLDOWN_TIME > this.cooldownStart) {
                this.finished = true;
                this.drivetrain.setSidesPercentOutput(0);
                return;
            }
        } else if (Math.abs(angleOutput) < COOLDOWN_THRESHOLD) {
            this.coolingDown = true;
            this.cooldownStart = System.currentTimeMillis();
        }

        this.drivetrain.setPercentOutput(Drivetrain.TalonID.RIGHT_1,
                distancePIDOutput + angleOutput);
        this.drivetrain.setPercentOutput(Drivetrain.TalonID.LEFT_1,
                distancePIDOutput - angleOutput);

        if (!SmartDashboard.getString("vision_error", "none").equals("none")) {
            ++this.visionErrors;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished || this.visionErrors > RobotMap.ALLOWABLE_VISION_ERRORS;
    }

    @Override
    protected void end() {
        this.distanceController.reset();
        this.getPIDController().reset();
        this.drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
