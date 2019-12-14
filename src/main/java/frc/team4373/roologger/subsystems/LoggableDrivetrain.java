package frc.team4373.roologger.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LoggableDrivetrain extends Subsystem {
    public abstract void setLeft(double output);

    public abstract void setRight(double output);

    public abstract double getLeftPercent();

    public abstract double getRightPercent();

    public abstract double getLeftVelocity();

    public abstract double getRightVelocity();

    public abstract double getLeftPosition();

    public abstract double getRightPosition();

    public abstract double getYaw();
}
