package frc.team4373.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap.CargoShipPort;
import frc.team4373.robot.RobotMap.Side;
import frc.team4373.robot.commands.auton.sequences.*;
import frc.team4373.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonCommand = null;
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
        objectiveChooser.addOption("Drive Forward", "drive");
        objectiveChooser.addOption("CS Side Cargo 1", "cs.cargo.s1");
        objectiveChooser.addOption("CS Side Cargo 2", "cs.cargo.s2");
        objectiveChooser.addOption("CS Side Cargo 3", "cs.cargo.s3");
        objectiveChooser.addOption("CS Side Hatch 1", "cs.hatch.s1");
        objectiveChooser.addOption("CS Side Hatch 2", "cs.hatch.s2");
        objectiveChooser.addOption("CS Side Hatch 3", "cs.hatch.s3");
        objectiveChooser.addOption("CS Front Hatch", "cs.hatchF");
        objectiveChooser.addOption("R Cargo Low", "r.cargo.low");
        objectiveChooser.addOption("R Cargo Med", "r.cargo.med");
        objectiveChooser.addOption("R Cargo Hi", "r.cargo.hi");
        objectiveChooser.addOption("R Hatch 1 Low", "r.hatch.near.low");
        objectiveChooser.addOption("R Hatch 1 Mid", "r.hatch.near.mid");
        objectiveChooser.addOption("R Hatch 1 Hi", "r.hatch.near.hi");
        objectiveChooser.addOption("R Hatch 2 Low", "r.hatch.far.low");
        objectiveChooser.addOption("R Hatch 2 Mid", "r.hatch.far.mid");
        objectiveChooser.addOption("R Hatch 2 Hi", "r.hatch.far.hi");
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
        Side pos;
        switch (positionChooser.getSelected()) {
            case "left":
                pos = Side.LEFT;
                break;
            case "right":
                pos = Side.RIGHT;
                break;
            default:
                pos = Side.MIDDLE;
        }
        String objective = objectiveChooser.getSelected();

        if (pos == Side.MIDDLE) {
            if (objective.equals("cs.hatchF")) {
                autonCommand = new CSFrontHatchAuton();
            } else if (objective.equals("drive")) {
                autonCommand = new DriveForwardAuton();
            }
        } else {
            String[] components = objective.split(".");
            switch (components[0]) {
                case "cs":
                    CargoShipPort port;
                    switch (components[2]) {
                        case "s1":
                            port = CargoShipPort.NEAR;
                            break;
                        case "s2":
                            port = CargoShipPort.MIDDLE;
                            break;
                        case "s3":
                            port = CargoShipPort.FAR;
                            break;
                        default:
                            port = CargoShipPort.NEAR;
                    }
                    if (components[1].equals("cargo")) {
                        autonCommand = new CSSideCargoAuton(pos, port);
                    } else if (components[1].equals("hatch")) {
                        autonCommand = new CSSideHatchAuton(pos, port);
                    }
                    break;
                case "r":
                    if (components[1].equals("cargo")) {
                        RobotMap.RocketHeight height;
                        switch (components[2]) {
                            case "hi":
                                height = RobotMap.RocketHeight.HIGH;
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                break;
                        }
                        autonCommand = new RCargoAuton(pos, height);
                    } else if (components[1].equals("hatch")) {
                        RobotMap.RocketHatchPanel panelLoc;
                        RobotMap.RocketHeight height;
                        if (components[2].equals("far")) {
                            panelLoc = RobotMap.RocketHatchPanel.FAR;
                        } else {
                            panelLoc = RobotMap.RocketHatchPanel.NEAR;
                        }
                        switch (components[3]) {
                            case "hi":
                                height = RobotMap.RocketHeight.HIGH;
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                break;
                        }
                        autonCommand = new RHatchAuton(pos, height, panelLoc);
                    }
                    break;
                case "drive":
                    autonCommand = new DriveForwardAuton();
                    break;
                default:
                    autonCommand = new DriveForwardAuton();
            }
        }

        if (autonCommand == null) {
            System.out.println("No auton command selected—using DriveForward");
            autonCommand = new DriveForwardAuton();
        }
        autonCommand.start();
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
