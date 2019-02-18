package frc.team4373.robot.input;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.ClearSubsystemsCommandGroup;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.elemental.*;
import frc.team4373.robot.commands.teleop.ToggleLightRingCommand;
import frc.team4373.robot.input.filters.FineGrainedPiecewiseFilter;
import frc.team4373.robot.input.filters.XboxAxisFilter;

/**
 * OI provides access to operator interface devices.
 */
public class OI {
    private static volatile OI oi = null;
    private RooJoystick<FineGrainedPiecewiseFilter> driveJoystick;
    private RooJoystick operatorJoystick;

    // lift buttons
    private JoystickButton operatorLiftCargoL3;
    private JoystickButton operatorLiftCargoL2;
    private JoystickButton operatorLiftCargoL1;
    private JoystickButton operatorLiftCargoShip;
    private JoystickButton operatorLiftGround;
    private JoystickButton operatorStowIntake;

    // climb buttons
    private JoystickButton driverClimbRaiseBot;
    private JoystickButton driverClimbRetractFront;
    private JoystickButton driverClimbRetractRear;
    private JoystickButton driverClimbRetract;

    // drive buttons
    private JoystickButton driverVisionAlignment;
    private JoystickButton driverToggleLightRing;
    private JoystickButton killAllAuton;

    private OI() {
        this.driveJoystick =
                new RooJoystick<>(RobotMap.DRIVE_JOYSTICK_PORT, new FineGrainedPiecewiseFilter());
        this.operatorJoystick =
                new RooJoystick<>(RobotMap.OPERATOR_JOYSTICK_PORT, new XboxAxisFilter());

        operatorLiftCargoL3 = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_LIFT_CARGO_L3);
        operatorLiftCargoL3.whenPressed(new SetLiftAuton(SetLiftAuton.Position.CARGO_3));

        operatorLiftCargoL2 = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_LIFT_CARGO_L2);
        operatorLiftCargoL2.whenPressed(new SetLiftAuton(SetLiftAuton.Position.CARGO_2));

        operatorLiftCargoL1 = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_LIFT_CARGO_L1);
        operatorLiftCargoL1.whenPressed(new SetLiftAuton(SetLiftAuton.Position.CARGO_1));

        operatorLiftCargoShip = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_LIFT_CARGO_SHIP);
        operatorLiftCargoShip.whenPressed(new SetLiftAuton(SetLiftAuton.Position.CARGO_SHIP));

        operatorLiftGround = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_LIFT_TO_LOAD);
        operatorLiftGround.whenPressed(new SetLiftAuton(SetLiftAuton.Position.LOADING));

        operatorStowIntake = new JoystickButton(operatorJoystick,
                RobotMap.OPERATOR_BUTTON_STOW_INTAKE);
        operatorStowIntake.whenPressed(new StowIntakeAuton());

        driverClimbRaiseBot = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_CLIMB_RAISE_BOT);
        driverClimbRaiseBot.whenPressed(new ExtendClimberAuton());

        driverClimbRetractFront = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_CLIMB_RETRACT_FRONT);
        driverClimbRetractFront.whenPressed(new RetractClimberFrontAuton());

        driverClimbRetractRear = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_CLIMB_RETRACT_REAR);
        driverClimbRetractRear.whenPressed(new RetractClimberRearAuton());

        driverClimbRetract = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_CLIMB_RETRACT_ALL);
        driverClimbRetract.whenPressed(new RetractClimberAuton());

        driverVisionAlignment = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_VISION_ALIGNMENT);
        driverVisionAlignment.whenPressed(new MiddleWheelAdjusterAuton());

        driverToggleLightRing = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_TOGGLE_LIGHT_RING);
        driverToggleLightRing.whenPressed(new ToggleLightRingCommand());

        killAllAuton = new JoystickButton(driveJoystick,
                RobotMap.DRIVER_BUTTON_KILL_AUTON);
        killAllAuton.whenPressed(new ClearSubsystemsCommandGroup());
    }

    /**
     * The getter for the OI singleton.
     *
     * @return The static OI singleton object.
     */
    public static OI getOI() {
        if (oi == null) {
            synchronized (OI.class) {
                if (oi == null) {
                    oi = new OI();
                }
            }
        }
        return oi;
    }

    /**
     * Gets the drive joystick controlling the robot.
     * @return The drive joystick controlling the robot.
     */
    public RooJoystick getDriveJoystick() {
        return this.driveJoystick;
    }

    /**
     * Gets the operator joystick controlling the robot.
     * @return The operator joystick controlling the robot.
     */
    public RooJoystick getOperatorJoystick() {
        return this.operatorJoystick;
    }
}