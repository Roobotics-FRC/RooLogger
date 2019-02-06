package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.*;

/**
 * Runs the given command after the given amount of time.
 */
public class DoLaterAuton extends CommandGroup {
    /**
     * Creates a new DoLaterAuton that runs the specified command after the given amount of time.
     * @param command The command to run.
     * @param delay The time to delay.
     */
    public DoLaterAuton(Command command, double delay) {
        // We could technically use `new WaitCommand(delay)`, but I'm not sure if it is reset in
        // `initialize`. From the decompiled bytecode, I also don't see how it works at all, so
        // I'm not inclined to blindly use it. While I trust ... WPI ... yeah, that's not going
        // to happen. I trust `DelayAuton` more than I trust WippyLibe.
        //      — @Samasaur, 1/6/19
        addSequential(new DelayAuton(delay));
        addSequential(command);
    }
}
