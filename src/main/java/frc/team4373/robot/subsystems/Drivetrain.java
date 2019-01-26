package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;

/**
 * A programmatic representation of the robot's drivetrain.
 *
 * @author Samasaur
 */
public class Drivetrain extends Subsystem {
    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        return instance == null ? instance = new Drivetrain() : instance;
    }

    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;
    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX middle1;
    private WPI_TalonSRX middle2;
    private DoubleSolenoid piston;
    private boolean middleWheelEngaged;

    private Drivetrain() {
        this.right1 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_RIGHT_1);
        this.right2 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_RIGHT_2);
        this.left1 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_LEFT_1);
        this.left2 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_LEFT_2);
        this.middle1 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_MIDDLE_1);
        this.middle2 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_MOTOR_MIDDLE_2);

        this.piston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.DRIVETRAIN_PISTON_FORWARD, RobotMap.DRIVETRAIN_PISTON_BACKWARD);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.left1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);
        this.left1.setNeutralMode(NeutralMode.Brake);
        this.middle2.setNeutralMode(NeutralMode.Brake);

        this.right2.follow(right1);
        this.left2.follow(left1);
        this.middle2.follow(middle1);

        this.left1.setInverted(RobotMap.DRIVETRAIN_MOTOR_LEFT_1_INVERSION);
        this.left2.setInverted(RobotMap.DRIVETRAIN_MOTOR_LEFT_2_INVERSION);
        this.left1.setInverted(RobotMap.DRIVETRAIN_MOTOR_RIGHT_1_INVERSION);
        this.right2.setInverted(RobotMap.DRIVETRAIN_MOTOR_RIGHT_2_INVERSION);
        this.left1.setInverted(RobotMap.DRIVETRAIN_MOTOR_MIDDLE_1_INVERSION);
        this.middle2.setInverted(RobotMap.DRIVETRAIN_MOTOR_MIDDLE_2_INVERSION);

        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.right1.setSensorPhase(false);
        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.left1.setSensorPhase(false);
        this.middle1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.middle1.setSensorPhase(false);
//        this.middle2.configSelectedFeedbackSensor(RemoteFeedbackDevice) //TODO: PIDGEON
    }

    public void setLeft(double power) {
        power = Robot.safetyCheckSpeed(power);
        this.left1.set(power);
    }

    public void setRight(double power) {
        power = Robot.safetyCheckSpeed(power);
        this.right1.set(power);
    }

    public void setMiddle(double power) {
        if (middleWheelEngaged) {
            power = Robot.safetyCheckSpeed(power);
            this.middle1.set(power);
        } else {
            this.middle1.set(0);
        }
    }

    public void setBoth(double power) {
        this.setLeft(power);
        this.setRight(power);
    }

    public void zeroMotors() {
        this.left1.set(0);
        this.left2.set(0);
        this.left1.set(0);
        this.right2.set(0);
        this.left1.set(0);
        this.middle2.set(0);
    }

    public void retractMiddleWheel() {
        this.piston.set(DoubleSolenoid.Value.kReverse); //I checked with David
        middleWheelEngaged = false;
    }

    public void engageMiddleWheel() {
        this.piston.set(DoubleSolenoid.Value.kForward);
        middleWheelEngaged = true;
    }

    // TODO: encoders

    @Override
    protected void initDefaultCommand() {
        //TODO
    }
}
