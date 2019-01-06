package frc.team4373.robot.input.filters;

/**
 * A generic, abstract Double-based filter type.
 *
 * @author Rui-Jie Fang
 */
public abstract class DoubleTypeFilter implements GenericFilter<Double> {
    public abstract Double applyFilter(Double val);
}