package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.Robot;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.ClimberDriveCommand;

public class ClimberDrive extends Subsystem {
    private static ClimberDrive instance;

    public static ClimberDrive getInstance() {
        return instance == null ? instance = new ClimberDrive() : instance;
    }

    private WPI_TalonSRX talon;

    public ClimberDrive() {
        this.talon = new WPI_TalonSRX(RobotMap.CLIMBER_DRIVE_MOTOR);
        this.talon.setNeutralMode(NeutralMode.Brake);
        this.talon.setInverted(RobotMap.CLIMBER_MOTOR_INVERTED);
    }

    public void setPercentOutput(double power) {
        power = Robot.constrainPercentOutput(power);
        this.talon.set(ControlMode.PercentOutput, power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimberDriveCommand());
    }
}
