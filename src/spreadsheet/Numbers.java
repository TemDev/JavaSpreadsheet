package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class Numbers implements Expression {

    private final double number;

    public Numbers(double number) {
        this.number = number;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return number;
    }

    @Override
    public void findCellReferences(Set<CellLocation> dependencies) {
    }

    @Override
    public String toString() {
        return number + "";
    }
}
