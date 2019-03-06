package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.commands.ClearSubsystemCommand;
import frc.team4373.robot.subsystems.*;

public class ClearSubsystemsCommandGroup extends CommandGroup {
    /**
     * Constructs a ClearSubsystemsCommandGroup.
     */
    public ClearSubsystemsCommandGroup() {
        addParallel(new ClearSubsystemCommand(Drivetrain.getInstance()));
        addParallel(new ClearSubsystemCommand(Intake.getInstance()));
        addParallel(new ClearSubsystemCommand(Lift.getInstance()));
    }
}
