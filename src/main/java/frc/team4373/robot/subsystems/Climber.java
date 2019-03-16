package frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.DummyCommand;
import frc.team4373.robot.commands.auton.climb.DebugClimberCommand;

/**
 * A programmatic representation of the robot's climbing components.
 */
public class Climber extends Subsystem {
    private static volatile Climber instance;

    /**
     * The getter for the Climber class.
     * @return the singleton Climber object.
     */
    public static Climber getInstance() {
        if (instance == null) {
            synchronized (Climber.class) {
                if (instance == null) {
                    instance = new Climber();
                }
            }
        }
        return instance;
    }

    private DoubleSolenoid frontPiston;
    private DoubleSolenoid rearPiston;

    private Climber() {
        this.frontPiston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.CLIMBER_PISTON_FRONT_FORWARD, RobotMap.CLIMBER_PISTON_FRONT_BACKWARD);
        this.rearPiston = new DoubleSolenoid(RobotMap.PCM_1_PORT,
                RobotMap.CLIMBER_PISTON_REAR_FORWARD, RobotMap.CLIMBER_PISTON_REAR_BACKWARD);
    }

    public void climb() {
        deployFront();
        deployRear();
    }

    public void deployFront() {
        this.frontPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void deployRear() {
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

    public void neutralizeFront() {
        this.frontPiston.set(DoubleSolenoid.Value.kOff);
    }

    public void neutralizeRear() {
        this.rearPiston.set(DoubleSolenoid.Value.kOff);
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

    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(new DummyCommand(this));
        setDefaultCommand(new DebugClimberCommand());
    }
}