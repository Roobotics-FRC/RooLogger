package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.ClimberDriveCommand;

/**
 * A programmatic representation of the drivetrain of the climbing system.
 */
public class ClimberDrive extends Subsystem {
    private static ClimberDrive instance;

    public static ClimberDrive getInstance() {
        return instance == null ? instance = new ClimberDrive() : instance;
    }

    private WPI_TalonSRX talon;

    private ClimberDrive() {
        this.talon = new WPI_TalonSRX(RobotMap.CLIMBER_DRIVE_MOTOR);
        this.talon.setNeutralMode(NeutralMode.Brake);
        this.talon.setInverted(RobotMap.CLIMBER_MOTOR_INVERTED);
    }

    /**
     * Sets the percent output of the motor to the specified value.
     * @param power The percent output on the range [-1, 1].
     */
    public void setPercentOutput(double power) {
        power = Robot.constrainPercentOutput(power);
        this.talon.set(ControlMode.PercentOutput, power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimberDriveCommand());
    }
}
