package frc.team4373.robot.input.filters;

public class XboxAxisFilter extends DoubleTypeFilter {
    @Override
    public Double applyFilter(Double val) {
        return (Math.abs(val) < 0.15) ? 0 : Math.pow(val, 2);
    }
}
