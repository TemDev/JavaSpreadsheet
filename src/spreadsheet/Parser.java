package spreadsheet;

import common.api.Expression;
import common.lexer.InvalidTokenException;
import common.lexer.Lexer;
import common.lexer.Token;

import javax.swing.text.Caret;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Stack;


public class Parser {
  /**
   * Parse a string into an Expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  static Expression parse(String input) throws InvalidSyntaxException{

    final Lexer lexer = new Lexer(input);
    final HashMap<Token.Kind, String> kindOp = new HashMap<>();
    final HashMap<Token.Kind, Integer> opPrecedence = new HashMap<>();
    final var binaryOperators = List.of(Token.Kind.PLUS, Token.Kind.MINUS, Token.Kind.STAR, Token.Kind.SLASH,
            Token.Kind.CARET, Token.Kind.LPARENTHESIS, Token.Kind.RPARENTHESIS);
    final var rightAssociative = List.of(Token.Kind.CARET);

    kindOp.put(Token.Kind.PLUS, "+");
    kindOp.put(Token.Kind.MINUS, "-");
    kindOp.put(Token.Kind.STAR, "*");
    kindOp.put(Token.Kind.SLASH, "/");
    kindOp.put(Token.Kind.CARET, "^");
    kindOp.put(Token.Kind.LPARENTHESIS, "(");
    kindOp.put(Token.Kind.RPARENTHESIS, ")");

    opPrecedence.put(Token.Kind.PLUS, 1);
    opPrecedence.put(Token.Kind.MINUS, 1);
    opPrecedence.put(Token.Kind.STAR, 2);
    opPrecedence.put(Token.Kind.SLASH, 2);
    opPrecedence.put(Token.Kind.CARET, 3);
    opPrecedence.put(Token.Kind.LPARENTHESIS, 4);
    opPrecedence.put(Token.Kind.RPARENTHESIS, 0);

    Stack<Expression> operandStack = new Stack<Expression>();
    Stack<Token.Kind> operatorStack = new Stack<Token.Kind>();

    while (true) {
      try  {

        Token token = lexer.nextToken();
        if (token == null) {
          break;
        }

        if (token.kind == Token.Kind.LANGLE) {
          throw new InvalidSyntaxException("This token is not supported.");
        }

        if (token.kind == Token.Kind.NUMBER) {

          operandStack.push(new Numbers(token.numberValue));

        } else if (token.kind == Token.Kind.CELL_LOCATION) {

          operandStack.push(new CellReferences(token.cellLocationValue));

        } else if (binaryOperators.contains(token.kind) ){
          while (operatorStack.size() > 0 && (opPrecedence.get(operatorStack.peek()) > opPrecedence.get(token.kind)
                  || (Objects.equals(opPrecedence.get(operatorStack.peek()), opPrecedence.get(token.kind))
                  && !rightAssociative.contains(operatorStack.peek())))) {
            if (operatorStack.peek() == Token.Kind.LPARENTHESIS && token.kind != Token.Kind.RPARENTHESIS) {
              break;
            }
            Token.Kind op = operatorStack.pop();
            if (op == Token.Kind.LPARENTHESIS && token.kind == Token.Kind.RPARENTHESIS) {
              break;
            }
            Expression exp1 = operandStack.pop();
            Expression exp2 = operandStack.pop();
            BinaryOperatorApplications boa = new BinaryOperatorApplications(kindOp.get(op), exp2, exp1);
            operandStack.push(boa);
          }
          if (token.kind != Token.Kind.RPARENTHESIS) {
            operatorStack.push(token.kind);
          }
        }
      } catch (InvalidTokenException tokenException) {
        break;
      }
    }
    for (int i = operatorStack.size() - 1; i > -1; i--) {
      Expression exp1 = operandStack.pop();
      Expression exp2 = operandStack.pop();
      BinaryOperatorApplications boa = new BinaryOperatorApplications(kindOp.get(operatorStack.pop()), exp2, exp1);
      operandStack.push(boa);
    }
    return operandStack.pop();
  }
}
