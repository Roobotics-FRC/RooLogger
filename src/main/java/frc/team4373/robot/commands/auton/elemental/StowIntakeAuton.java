package frc.team4373.robot.commands.auton.elemental;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Retracts and stows the intake.
 */
public class StowIntakeAuton extends CommandGroup {
    public StowIntakeAuton() {
        addSequential(new RetractIntakeAuton());
        addSequential(new SetLiftAuton(SetLiftAuton.Position.STOW));
    }
}
