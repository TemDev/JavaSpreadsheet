package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class CellReferences implements Expression {

    private final CellLocation reference;

    public CellReferences(CellLocation cell) {
        reference = cell;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return context.getCellValue(reference);
    }

    @Override
    public void findCellReferences(Set<CellLocation> dependencies) {
        dependencies.add(reference);
    }

    @Override
    public String toString() {
        return reference.toString();
    }
}
