RooLogger
---

This is a logging package for FRC robot drivetrains. It is especially useful for calculating velocity, acceleration, and jerk for motion profiling. Further documentation is forthcoming.

## Basic Usage

1. Create a drivetrain class that extends `LoggableDrivetrain` and implements the appropriate methods.
2. Construct a `RunLogCommand` with the appropriate parameters. You can choose to log one or both sides of your drivetrain, set the percent output (usually 1), and the time for which you want to log.
3. Run the command. The logger will automatically output a file to `/home/lvuser` on the roboRIO when the command exits.
4. Do some math on the data to produce your desired values (Team 4373 maintains some useful tools in our [RooUtils](https://github.com/Roobotics-FRC/RooUtils/blob/master/robot_movement_calculator.py) repository).