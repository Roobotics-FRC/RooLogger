package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.IntakeCommand;

/**
 * A programmatic representation of the robot's intake mechanism for both hatch and cargo.
 */
public class Intake extends Subsystem {

    private WPI_TalonSRX leftTalon;
    private WPI_TalonSRX rightTalon;
    private DoubleSolenoid hatchPiston;
    private DoubleSolenoid deployPiston1;
    private DoubleSolenoid deployPiston2;
    private DigitalInput limitSwitch;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        leftTalon = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_LEFT);
        rightTalon = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_RIGHT);

        hatchPiston = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.INTAKE_PISTON_HATCH_FORWARD, RobotMap.INTAKE_PISTON_HATCH_BACKWARD);
        deployPiston1 = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.INTAKE_PISTON_DEPLOYMENT_1_FORWARD,
                RobotMap.INTAKE_PISTON_DEPLOYMENT_1_BACKWARD);
        deployPiston2 = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.INTAKE_PISTON_DEPLOYMENT_2_FORWARD,
                RobotMap.INTAKE_PISTON_DEPLOYMENT_2_BACKWARD);

        limitSwitch = new DigitalInput(RobotMap.INTAKE_LIMIT_SWITCH_CHANNEL);

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

    /**
     * Extends the hatch arms to grab a hatch. Won't do anything if holding a cargo.
     */
    public void collectHatch() {
        if (!isHoldingCargo()) {
            hatchPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void releaseHatch() {
        hatchPiston.set(DoubleSolenoid.Value.kForward);
    }

    private void setLeftTalon(double power) {
        power = Robot.constrainPercentOutput(power);
        this.leftTalon.set(ControlMode.PercentOutput, power);
    }

    private void setRightTalon(double power) {
        power = Robot.constrainPercentOutput(power);
        this.rightTalon.set(ControlMode.PercentOutput, power);
    }

    public void deploy() {
        this.deployPiston1.set(DoubleSolenoid.Value.kForward);
        this.deployPiston2.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        this.deployPiston1.set(DoubleSolenoid.Value.kReverse);
        this.deployPiston2.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isHoldingCargo() {
        return limitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }
}