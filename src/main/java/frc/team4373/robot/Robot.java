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

import java.util.LinkedHashMap;
import java.util.Map;

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

    Map<String, String> autonEntries;

    /**
     * Constructor for the Robot class. Variable initialization occurs here;
     * WPILib-related setup should occur in {@link #robotInit}.
     */
    public Robot() {
        autonEntries = new LinkedHashMap<>();
        autonEntries.put("Drive Forward", "drive");
        autonEntries.put("CS Side Cargo 1", "cs.cargo.s1");
        autonEntries.put("CS Side Cargo 2", "cs.cargo.s2");
        autonEntries.put("CS Side Cargo 3", "cs.cargo.s3");
        autonEntries.put("CS Side Hatch 1", "cs.hatch.s1");
        autonEntries.put("CS Side Hatch 2", "cs.hatch.s2");
        autonEntries.put("CS Side Hatch 3", "cs.hatch.s3");
        autonEntries.put("CS Front Hatch", "cs.hatchF");
        autonEntries.put("R Cargo Low", "r.cargo.low");
        autonEntries.put("R Cargo Med", "r.cargo.med");
        autonEntries.put("R Cargo Hi", "r.cargo.hi");
        autonEntries.put("R Hatch 1 Low", "r.hatch.near.low");
        autonEntries.put("R Hatch 1 Mid", "r.hatch.near.mid");
        autonEntries.put("R Hatch 1 Hi", "r.hatch.near.hi");
        autonEntries.put("R Hatch 2 Low", "r.hatch.far.low");
        autonEntries.put("R Hatch 2 Mid", "r.hatch.far.mid");
        autonEntries.put("R Hatch 2 Hi", "r.hatch.far.hi");
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     *
     * <p>All SmartDashboard fields should be initially added here.</p>
     */
    @Override
    public void robotInit() {
        // Initialize subsystems
        Drivetrain.getInstance();
        Climber.getInstance();
        Intake.getInstance();
        Lift.getInstance();

        // Populate dashboard
        boolean isFirstEntry = true;
        for (Map.Entry<String, String> entry: autonEntries.entrySet()) {
            if (isFirstEntry) {
                objectiveChooser.setDefaultOption(entry.getKey(), entry.getValue());
                isFirstEntry = false;
            } else {
                objectiveChooser.addOption(entry.getKey(), entry.getValue());
            }
        }
        SmartDashboard.putData("Objective", objectiveChooser);

        positionChooser.setDefaultOption("Center", "center");
        positionChooser.addOption("Left", "left");
        positionChooser.addOption("Right", "right");
        SmartDashboard.putData("Start Pos", positionChooser);

        SmartDashboard.putBoolean("Auton OK", true);
        SmartDashboard.putString("Activated Auton", "None");
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
        String[] components = objective.split("\\.");

        if (pos == Side.MIDDLE) {
            if (objective.equals("cs.hatchF")) {
                autonCommand = new CSFrontHatchAuton();
            } else if (objective.equals("drive")) {
                autonCommand = new DriveForwardAuton();
            }
        } else if (components.length > 2) {
            switch (components[0]) {
                case "cs":
                    CargoShipPort port;
                    switch (components[2]) {
                        case "s1":
                            port = CargoShipPort.NEAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Near");
                            break;
                        case "s2":
                            port = CargoShipPort.MIDDLE;
                            SmartDashboard.putString("Activated Auton", "CS Side Middle");
                            break;
                        case "s3":
                            port = CargoShipPort.FAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Far");
                            break;
                        default:
                            port = CargoShipPort.NEAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Near");
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
                                SmartDashboard.putString("Activated Auton", "R Cargo High");
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                SmartDashboard.putString("Activated Auton", "R Cargo Middle");
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                SmartDashboard.putString("Activated Auton", "R Cargo Low");
                                break;
                        }
                        autonCommand = new RCargoAuton(pos, height);
                    } else if (components[1].equals("hatch") && components.length > 3) {
                        StringBuilder activatedAuton = new StringBuilder();
                        activatedAuton.append("R Hatch");

                        RobotMap.RocketHatchPanel panelLoc;
                        RobotMap.RocketHeight height;
                        if (components[2].equals("far")) {
                            panelLoc = RobotMap.RocketHatchPanel.FAR;
                            activatedAuton.append(" Far ");
                        } else {
                            panelLoc = RobotMap.RocketHatchPanel.NEAR;
                            activatedAuton.append(" Near ");
                        }
                        switch (components[3]) {
                            case "hi":
                                height = RobotMap.RocketHeight.HIGH;
                                activatedAuton.append("High");
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                activatedAuton.append("Middle");
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                activatedAuton.append("Low");
                                break;
                        }
                        SmartDashboard.putString("Activated Auton", activatedAuton.toString());
                        autonCommand = new RHatchAuton(pos, height, panelLoc);
                    }
                    break;
                case "drive":
                    autonCommand = new DriveForwardAuton();
                    SmartDashboard.putString("Activated Auton", "Drive");
                    break;
                default:
                    autonCommand = new DriveForwardAuton();
                    SmartDashboard.putString("Activated Auton", "Drive");
            }
        }

        if (autonCommand == null) {
            System.out.println("No auton command selected—using DriveForward");
            autonCommand = new DriveForwardAuton();
            SmartDashboard.putString("Activated Auton", "Drive");
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
     * This function runs periodically in disabled mode.
     * It is used to verify that the selected auton configuration is legal.
     * <br>
     * <b>UNDER NO CIRCUMSTANCES SHOULD SUBSYSTEMS BE ACCESSED OR ENGAGED IN THIS METHOD.</b>
     */
    @Override
    public void disabledPeriodic() {
        String positionSelection = positionChooser.getSelected();
        Side pos;
        switch (positionSelection) {
            case "left":
                pos = Side.LEFT;
                break;
            case "right":
                pos = Side.RIGHT;
                break;
            case "center":
                pos = Side.MIDDLE;
                break;
            default:
                SmartDashboard.putBoolean("Auton OK", false);
                return;
        }

        String objective = objectiveChooser.getSelected();
        String[] components = objective.split("\\.");
        
        if (components.length == 0) {
            SmartDashboard.putBoolean("Auton OK", false);
            return;
        }

        if (pos == Side.MIDDLE) {
            if (components[0].equals("cs.hatchF") || components[0].equals("drive")) {
                SmartDashboard.putBoolean("Auton OK", true);
                return;
            } else {
                SmartDashboard.putBoolean("Auton OK", false);
                return;
            }
        } else if (pos == Side.LEFT || pos == Side.RIGHT) {
            if (!objective.equals("cs.hatchF") && autonEntries.containsValue(objective)) {
                SmartDashboard.putBoolean("Auton OK", true);
                return;
            } else {
                SmartDashboard.putBoolean("Auton OK", false);
                return;
            }
        }

        SmartDashboard.putBoolean("Auton OK", false);
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
