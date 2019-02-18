package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.commands.auton.elemental.ExtendClimberFrontAuton;
import frc.team4373.robot.commands.auton.elemental.ExtendClimberRearAuton;

public class StaggeredClimbAuton extends CommandGroup {
    public StaggeredClimbAuton() {
        addSequential(new ExtendClimberFrontAuton());
        addSequential(new ExtendClimberRearAuton());
    }
}
