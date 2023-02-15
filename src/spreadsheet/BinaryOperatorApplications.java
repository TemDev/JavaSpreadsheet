package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Set;

public class BinaryOperatorApplications implements Expression {

    private final String binaryOperator;
    private final Expression leftTree;
    private final Expression rightTree;

    public static void main(String[] args) {
        BinaryOperatorApplications boa = new BinaryOperatorApplications("+", new Numbers(1), new Numbers(2));
        System.out.println(boa);
    }

    public BinaryOperatorApplications(String binaryOperator, Expression leftTree, Expression rightTree) {
        this.binaryOperator = binaryOperator;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
    }

    /**
     * Evaluate the expression as a double value.
     *
     * <p>The provided `EvaluationContext` may be used to lookup the value of other cells.
     *
     * @param context
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return switch (binaryOperator) {
            case "+" -> leftTree.evaluate(context) + rightTree.evaluate(context);
            case "-" -> leftTree.evaluate(context) - rightTree.evaluate(context);
            case "*" -> leftTree.evaluate(context) * rightTree.evaluate(context);
            case "/" -> leftTree.evaluate(context) / rightTree.evaluate(context);
            case "^" -> Math.pow(leftTree.evaluate(context), rightTree.evaluate(context));
            default -> 0;
        };
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
        leftTree.findCellReferences(dependencies);
        rightTree.findCellReferences(dependencies);
    }

    @Override
    public String toString() {
        return "(" + leftTree.toString() + " " + binaryOperator + " " + rightTree.toString() + ")";
    }

    public String getBinaryOperator() {
        return binaryOperator;
    }
}
