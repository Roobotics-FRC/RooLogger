package frc.team4373.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    SendableChooser<String> objectiveChooser = new SendableChooser();
    SendableChooser<String> positionChooser = new SendableChooser();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Initialize subsystems
        Drivetrain.getInstance();
        Climber.getInstance();
        Intake.getInstance();
        Lift.getInstance();

        // Populate dashboard
        objectiveChooser.addOption("CS Side Cargo 1", "cs.cargoS1");
        objectiveChooser.addOption("CS Side Cargo 2", "cs.cargoS2");
        objectiveChooser.addOption("CS Side Cargo 3", "cs.cargoS3");
        objectiveChooser.addOption("CS Side Hatch 1", "cs.hatchS1");
        objectiveChooser.addOption("CS Side Hatch 2", "cs.hatchS2");
        objectiveChooser.addOption("CS Side Hatch 3", "cs.hatchS3");
        objectiveChooser.addOption("CS Front Hatch", "cs.hatchF");
        SmartDashboard.putData("Objective", objectiveChooser);

        positionChooser.addOption("Left", "left");
        positionChooser.addOption("Right", "right");
        positionChooser.addOption("Center", "center");
        SmartDashboard.putData("Start Pos", positionChooser);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want run during disabled,
     * autonomous, teleoperated, and test.
     *
     * <p>This runs after the mode-specific periodic functions but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * This function is called once when Sandstorm mode starts.
     */
    @Override
    public void autonomousInit() {
        String pos = positionChooser.getSelected();
        String objective = objectiveChooser.getSelected();

        /*
            MiddleWheelAdjuster -> Drive Forward -> Release (sth)
         */
    }

    /**
     * This function is called once when teleoperated mode starts.
     */
    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during Sandstorm mode.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * Constrains a percent output to [-1, 1].
     * @param output the percent output value to constrain.
     * @return the input percent output constrained to the safe range.
     */
    public static double constrainPercentOutput(double output) {
        if (output > 1) {
            return 1;
        }
        if (output < -1) {
            return -1;
        }
        return output;
    }
}
