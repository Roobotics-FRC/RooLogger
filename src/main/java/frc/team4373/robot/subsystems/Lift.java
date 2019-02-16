package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.LiftCommand;

/**
 * A programmatic representation of the robot's lift mechanism that supports the intake.
 */
public class Lift extends Subsystem {
    private static Lift instance;

    public static Lift getInstance() {
        return instance == null ? instance = new Lift() : instance;
    }

    private WPI_TalonSRX talon1;
    private WPI_TalonSRX talon2;
    private DoubleSolenoid piston1;
    private Potentiometer poten;

    private Lift() {
        this.talon1 = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_1);
        this.talon2 = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_2);

        this.piston1 = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.LIFT_PISTON_FORWARD, RobotMap.LIFT_PISTON_BACKWARD);

        this.poten = new AnalogPotentiometer(RobotMap.PTN_CHANNEL,
                RobotMap.LIFT_DEGREES_OF_MOTION, RobotMap.LIFT_INITIAL_ANG_OFFSET);

        this.talon1.setNeutralMode(NeutralMode.Brake);
        this.talon2.setNeutralMode(NeutralMode.Brake);

        this.talon2.follow(talon1);

        this.talon1.setInverted(RobotMap.LIFT_MOTOR_1_INVERTED);
        this.talon2.setInverted(RobotMap.LIFT_MOTOR_2_INVERTED);
    }

    /**
     * Telescopes the lift up.
     */
    public void telescope() {
        this.piston1.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Retracts the telescoping pistons.
     */
    public void retract() {
        this.piston1.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Sets the raw percent output of the lift motors.
     * @param power the percent output to which to set the motors.
     */
    public void setPercentOutput(double power) {
        if (getPotenAngle() > 0 && getPotenAngle() < RobotMap.LIFT_MAXIMUM_SAFE_ANGLE) {
            power = Robot.constrainPercentOutput(power);
            this.talon1.set(ControlMode.PercentOutput, power);
        }
    }

    /**
     * Gets the current angle from the potentiometer.
     * @return the angle from the potentiometer.
     */
    public double getPotenAngle() {
        return poten.get();
    }

    /**
     * Gets the current height of the arm based on the angle.
     * @return the height of the arm.
     */
    public double getComputedArmHeight() {
        return RobotMap.LIFT_ARM_MOUNT_HEIGHT
                - RobotMap.LIFT_ARM_LENGTH * Math.cos(getPotenAngle());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftCommand());
    }
}
