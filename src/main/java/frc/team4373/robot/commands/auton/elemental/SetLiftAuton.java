package frc.team4373.robot.commands.auton.elemental;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Lift;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Moves the lift to the given position.
 */
public class SetLiftAuton extends PIDCommand {
    private Lift lift;
    private double angle;

    private Deque<Double> potenReadings;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 500;
    private static final double COOLDOWN_THRESHOLD = RobotMap.LIFT_MOVEMENT_SPEED * 0.2;

    public enum Position {
        HATCH_2(506), HATCH_1(312),
        CARGO_2(560), CARGO_1(365),
        CARGO_SHIP(506), LOADING(300), GROUND(167);

        private double armAngle;

        /**
         * Initializes a Lift Position preset.
         * @param armAngle the angle of the arm, in degrees.
         */
        Position(double armAngle) {
            this.armAngle = armAngle;
        }
    }

    /**
     * Constructs a new SetLiftAuton command to set the lift to a predefined height.
     * @param position the position to attain.
     */
    public SetLiftAuton(Position position) {
        super("SetLiftAuton", RobotMap.LIFT_PID_GAINS.kP, RobotMap.LIFT_PID_GAINS.kI,
                RobotMap.LIFT_PID_GAINS.kD);
        requires(this.lift = Lift.getInstance());
        this.angle = position.armAngle;
        this.potenReadings = new ArrayDeque<>();
    }

    @Override
    protected void initialize() {
        this.finished = this.coolingDown = false;
        this.potenReadings.clear();
        this.setInputRange(0, RobotMap.LIFT_MAX_POTEN_VALUE);
        this.getPIDController().setOutputRange(-RobotMap.LIFT_MOVEMENT_SPEED,
                RobotMap.LIFT_MOVEMENT_SPEED);
        this.setSetpoint(angle);
        this.getPIDController().setPID(SmartDashboard.getNumber("kP-D", 0),
                SmartDashboard.getNumber("kI-D", 0),
                SmartDashboard.getNumber("kD-D", 0));
        setTimeout(0.5); // ensure that there's enough time for the pistons to potentially move
    }

    @Override
    protected double returnPIDInput() {
        this.potenReadings.add(this.lift.getPotenValue());
        int readingCount = this.potenReadings.size();
        if (readingCount > 10) {
            this.potenReadings.removeFirst();
            readingCount = 10;
        }
        double readingSum = 0;
        for (double reading: potenReadings) {
            readingSum += reading;
        }
        return readingSum / readingCount;
    }

    @Override
    protected void usePIDOutput(double output) {
        if (coolingDown) {
            if (System.currentTimeMillis() - COOLDOWN_TIME > this.cooldownStart) {
                this.finished = true;
                this.lift.setPercentOutputRaw(0);
                return;
            }
        } else if (Math.abs(output) < COOLDOWN_THRESHOLD) {
            this.coolingDown = true;
            this.cooldownStart = System.currentTimeMillis();
        }
        this.lift.setPercentOutputRaw(output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished && this.isTimedOut();
    }

    @Override
    protected void end() {
        this.lift.setPercentOutputRaw(0);
        this.getPIDController().reset();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
