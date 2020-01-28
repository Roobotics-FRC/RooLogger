package frc.team4373.roologger.subsystems;

public interface LoggableDrivetrain {
    /**
     * Sets the percent output of the left side.
     * @param output the percent output [-1, 1] of the left side.
     */
    void setLeft(double output);

    /**
     * Sets the percent output of the right side.
     * @param output the percent output [-1, 1] of the right side.
     */
    void setRight(double output);

    /**
     * Gets the percent output of the left side.
     * @return the percent output [-1, 1] of the left side.
     */
    double getLeftPercent();

    /**
     * Gets the percent output of the right side.
     * @return the percent output [-1, 1] of the right side.
     */
    double getRightPercent();

    /**
     * Gets the velocity of the left side.
     * @return the velocity of the left side.
     */
    double getLeftVelocity();

    /**
     * Gets the velocity of the right side.
     * @return the velocity of the right side.
     */
    double getRightVelocity();

    /**
     * Gets the position of the left side.
     * @return the position of the left side.
     */
    double getLeftPosition();

    /**
     * Gets the position of the right side.
     * @return the position of the right side.
     */
    double getRightPosition();

    /**
     * Gets the current yaw of the robot.
     * @return the robot's current yaw.
     */
    double getYaw();

    /**
     * Gets the current current output of the motor.
     * @return the current current.
     */
    double getCurrent();
}
