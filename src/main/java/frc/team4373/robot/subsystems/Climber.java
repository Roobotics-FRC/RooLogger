package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;

/**
 * A programmatic representation of the robot's climbing components.
 */
public class Climber extends Subsystem {
    private static Climber instance;

    public static Climber getInstance() {
        return instance == null ? instance = new Climber() : instance;
    }

    private WPI_TalonSRX talon;
    private DoubleSolenoid frontPiston;
    private DoubleSolenoid rearPiston;
    private DigitalInput frontLimitSwitch;
    private DigitalInput rearLimitSwitch;

    private Climber() {
        this.talon = new WPI_TalonSRX(RobotMap.CLIMBER_MOTOR);
        this.frontPiston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.CLIMBER_PISTON_FRONT_FORWARD, RobotMap.CLIMBER_PISTON_FRONT_BACKWARD);
        this.rearPiston = new DoubleSolenoid(RobotMap.PCM_2_PORT,
                RobotMap.CLIMBER_PISTON_REAR_FORWARD, RobotMap.CLIMBER_PISTON_REAR_BACKWARD);
        this.frontLimitSwitch = new DigitalInput(RobotMap.CLIMBER_FRONT_LIMIT_SWITCH_CHANNEL);
        this.rearLimitSwitch = new DigitalInput(RobotMap.CLIMBER_REAR_LIMIT_SWITCH_CHANNEL);

        this.talon.setNeutralMode(NeutralMode.Brake);
        this.talon.setInverted(RobotMap.CLIMBER_MOTOR_INVERTED);
    }

    public void climb() {
        this.frontPiston.set(DoubleSolenoid.Value.kForward);
        this.rearPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void retractFront() {
        this.frontPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void retractRear() {
        this.rearPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void retractAll() {
        this.retractFront();
        this.retractRear();
    }

    public void setPercentOutput(double power) {
        power = Robot.constrainPercentOutput(power);
        this.talon.set(ControlMode.PercentOutput, power);
    }

    public boolean getFrontLimitSwitch() {
        return frontLimitSwitch.get();
    }

    public boolean getRearLimitSwitch() {
        return rearLimitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {
        //TODO
    }
}
