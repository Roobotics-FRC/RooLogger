package frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.DummyCommand;

/**
 * A programmatic representation of the robot's climbing components.
 */
public class Climber extends Subsystem {
    private static Climber instance;

    public static Climber getInstance() {
        return instance == null ? instance = new Climber() : instance;
    }

    private DoubleSolenoid frontPiston;
    private DoubleSolenoid rearPiston;
    private DigitalInput frontLimitSwitch;
    private DigitalInput rearLimitSwitch;

    private Climber() {
        this.frontPiston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.CLIMBER_PISTON_FRONT_FORWARD, RobotMap.CLIMBER_PISTON_FRONT_BACKWARD);
        this.rearPiston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.CLIMBER_PISTON_REAR_FORWARD, RobotMap.CLIMBER_PISTON_REAR_BACKWARD);
        this.frontLimitSwitch = new DigitalInput(RobotMap.CLIMBER_FRONT_LIMIT_SWITCH_CHANNEL);
        this.rearLimitSwitch = new DigitalInput(RobotMap.CLIMBER_REAR_LIMIT_SWITCH_CHANNEL);
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

    /**
     * Whether the front pistons are deployed.
     * @return whether the front pistons are deployed.
     */
    public boolean frontIsDeployed() {
        return this.frontPiston.get() == DoubleSolenoid.Value.kForward;
    }

    /**
     * Whether the rear pistons are deployed.
     * @return whether the rear pistons are deployed.
     */
    public boolean rearIsDeployed() {
        return this.rearPiston.get() == DoubleSolenoid.Value.kForward;
    }

    public boolean getFrontLimitSwitch() {
        return frontLimitSwitch.get();
    }

    public boolean getRearLimitSwitch() {
        return rearLimitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DummyCommand(this));
    }
}
