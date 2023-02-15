package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Set;

public class CellReferences implements Expression {

    private final CellLocation reference;

    /**
     * Evaluate the expression as a double value.
     *
     * <p>The provided `EvaluationContext` may be used to lookup the value of other cells.
     *
     * @param context
     */

    public CellReferences(CellLocation cell) {
        reference = cell;
    }
    @Override
    public double evaluate(EvaluationContext context) {
        return context.getCellValue(reference);
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
        dependencies.add(reference);
    }

    @Override
    public String toString() {
        return reference.toString();
    }
}
