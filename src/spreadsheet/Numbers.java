package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Set;

public class Numbers implements Expression{

    private final double number;

    /**
     * Evaluate the expression as a double value.
     *
     * <p>The provided `EvaluationContext` may be used to lookup the value of other cells.
     *
     * @param context
     */

    public Numbers(double number) {
        this.number = number;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return number;
    }

    /**
     * Find all cell locations referenced by this expression (and its subexpressions).
     *
     * <p>The cell locations should be added to the `dependencies` set.
     *
     * @param dependencies
     */
    @Override
    public void findCellReferences(Set<CellLocation> dependencies) {
    }

    @Override
    public String toString() {
        return number + "";
    }
}
