package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;

/**
 * A programmatic representation of the robot's intake lift mechanism.
 *
 * @author Samasaur
 */
public class Lift extends Subsystem {
    private static Lift instance;
    public static Lift getInstance() {
        return instance == null ? instance = new Lift() : instance;
    }

    private WPI_TalonSRX talon1;
    private WPI_TalonSRX talon2;
    private DoubleSolenoid piston1;
    private DoubleSolenoid piston2;

    private Lift() {
        this.talon1 = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_1);
        this.talon2 = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_2);

        this.piston1 = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.LIFT_PISTON_1_FORWARD, RobotMap.LIFT_PISTON_1_BACKWARD);
        this.piston2 = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.LIFT_PISTON_2_FORWARD, RobotMap.LIFT_PISTON_2_BACKWARD);

        this.talon1.setNeutralMode(NeutralMode.Brake);
        this.talon2.setNeutralMode(NeutralMode.Brake);

        this.talon2.follow(talon1);

        this.talon1.setInverted(RobotMap.LIFT_MOTOR_1_INVERSION);
        this.talon2.setInverted(RobotMap.LIFT_MOTOR_2_INVERSION);

        this.talon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.talon1.setSensorPhase(false);
    }

    public void raise() {
        this.piston1.set(DoubleSolenoid.Value.kForward);
        this.piston2.set(DoubleSolenoid.Value.kForward);
    }

    public void lower() {
        this.piston1.set(DoubleSolenoid.Value.kReverse);
        this.piston2.set(DoubleSolenoid.Value.kReverse);
    }

    public void set(double power) {
        power = Robot.safetyCheckSpeed(power);
        this.talon1.set(power);
    }

    //TODO: encoder

    @Override
    protected void initDefaultCommand() {
        //TODO
    }
}
