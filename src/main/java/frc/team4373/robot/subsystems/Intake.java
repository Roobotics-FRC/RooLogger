package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;

/**
 * A programmatic representation of the robot's intake mechanism for both hatch and cargo.
 */
public class Intake extends Subsystem {

    private WPI_TalonSRX leftTalon;
    private WPI_TalonSRX rightTalon;
    private DoubleSolenoid leftPiston;
    private DoubleSolenoid rightPiston;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        leftTalon = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_LEFT);
        rightTalon = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_RIGHT);

        leftPiston = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.INTAKE_PISTON_LEFT_FORWARD, RobotMap.INTAKE_PISTON_LEFT_BACKWARD);
        rightPiston = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.INTAKE_PISTON_RIGHT_FORWARD, RobotMap.INTAKE_PISTON_RIGHT_BACKWARD);

        this.leftTalon.setNeutralMode(NeutralMode.Brake);
        this.rightTalon.setNeutralMode(NeutralMode.Brake);

        this.leftTalon.setInverted(RobotMap.INTAKE_MOTOR_LEFT_INVERTED);
        this.rightTalon.setInverted(RobotMap.INTAKE_MOTOR_RIGHT_INVERTED);

    }

    public void collectCargo() {
        setLeftTalon(RobotMap.INTAKE_MOTOR_OUTPUT);
        setRightTalon(-RobotMap.INTAKE_MOTOR_OUTPUT);
    }

    public void releaseCargo() {
        setLeftTalon(-RobotMap.INTAKE_MOTOR_OUTPUT);
        setRightTalon(RobotMap.INTAKE_MOTOR_OUTPUT);
    }

    public void collectHatch() {
        leftPiston.set(DoubleSolenoid.Value.kForward);
        rightPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void releaseHatch() {
        leftPiston.set(DoubleSolenoid.Value.kReverse);
        rightPiston.set(DoubleSolenoid.Value.kReverse);
    }

    private void setLeftTalon(double power) {
        power = Robot.constrainPercentOutput(power);
        this.leftTalon.set(ControlMode.PercentOutput, power);
    }

    private void setRightTalon(double power) {
        power = Robot.constrainPercentOutput(power);
        this.rightTalon.set(ControlMode.PercentOutput, power);
    }

    @Override
    protected void initDefaultCommand() {
        //TODO
    }
}