package frc.team4373.roologger.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LoggableDrivetrain extends Subsystem {
    /**
     * Sets the percent output of the left side.
     * @param output the percent output [-1, 1] of the left side.
     */
    public abstract void setLeft(double output);

    /**
     * Sets the percent output of the right side.
     * @param output the percent output [-1, 1] of the right side.
     */
    public abstract void setRight(double output);

    /**
     * Gets the percent output of the left side.
     * @return the percent output [-1, 1] of the left side.
     */
    public abstract double getLeftPercent();

    /**
     * Gets the percent output of the right side.
     * @return the percent output [-1, 1] of the right side.
     */
    public abstract double getRightPercent();

    /**
     * Gets the velocity of the left side.
     * @return the velocity of the left side.
     */
    public abstract double getLeftVelocity();

    /**
     * Gets the velocity of the right side.
     * @return the velocity of the right side.
     */
    public abstract double getRightVelocity();

    /**
     * Gets the position of the left side.
     * @return the position of the left side.
     */
    public abstract double getLeftPosition();

    /**
     * Gets the position of the right side.
     * @return the position of the right side.
     */
    public abstract double getRightPosition();

    /**
     * Gets the current yaw of the robot.
     * @return the robot's current yaw.
     */
    public abstract double getYaw();

    /**
     * Gets the current current output of the motor.
     * @return the current current.
     */
    public abstract double getCurrent();
}
