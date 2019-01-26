package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;

/**
 * A programatic representation of the robot's intake.
 *
 * @author benji123abc
 */
public class Intake extends Subsystem {

    private WPI_TalonSRX leftTalon;
    private WPI_TalonSRX rightTalon;

    private static Intake instance;

    public static Intake getInstance() {
        return instance == null ? instance = new Intake() : instance;
    }

    private Intake() {
        leftTalon = new WPI_TalonSRX(RobotMap.LEFT_INTAKE_MOTOR);
        rightTalon = new WPI_TalonSRX(RobotMap.RIGHT_INTAKE_MOTOR);

        this.leftTalon.setNeutralMode(NeutralMode.Brake);
        this.rightTalon.setNeutralMode(NeutralMode.Brake);

        this.leftTalon.setInverted(RobotMap.INTAKE_LEFT_INVERTED);
        this.rightTalon.setInverted(RobotMap.INTAKE_RIGHT_INVERTED);

    }

    private void setLeft(double power) {
        power = Robot.safetyCheckSpeed(power);
    }
    private void setRight(double power) {
        power = Robot.safetyCheckSpeed(power);
    }
    public void collect() {
        setLeft(1);
        setRight(1);
    }
    public void release() {
        setLeft(0);
        setRight(0);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
