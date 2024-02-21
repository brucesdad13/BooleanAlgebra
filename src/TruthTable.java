/**
 * A class that represents a truth table of a given Boolean Algebra expression.
 * The class can be used to print the truth table
 * TODO: get the simplified expression and get the canonical expression.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
public class TruthTable {
    private final String expression;
    private final List<String> variables;
    private final List<String> truthTable;
    private final String simplifiedExpression;
    private final String canonicalExpression;

    public TruthTable(String expression) {
        this.expression = expression;
        this.variables = getVariables(expression);
        this.truthTable = getTruthTable(expression, variables);
        this.simplifiedExpression = getSimplifiedExpression(); // TODO: Implement this method
        this.canonicalExpression = getCanonicalExpression(); // TODO: Implement this method
    }

    /**
     * Print the truth table header to the standard output
     */
    public void printTruthTable() {
        for (String variable : variables) {
            System.out.print(variable + " | ");
        }
        System.out.println(expression);
        for (String row : truthTable) {
            System.out.println(row);
        }
    }

    /** TODO: Implement this method */
    public String getSimplifiedExpression() {

        return simplifiedExpression;
    }

    /** TODO: Implement this method */
    public String getCanonicalExpression() {
        return canonicalExpression;
    }

    /**
     * Create a list of variables from the given expression
     * The expression is given in the form of a string.
     * The expression can contain the following operators:
     * - NOT: !
     * - AND: &
     * - XOR: ^
     * - OR: |
     *     Example:
     *     Input: "a&b|!a&b"
     *     Output: [a, b]
     * @param expression The expression from which the variables are to be extracted
     */
    private List<String> getVariables(String expression) {
        List<String> variables = new ArrayList<>();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c) && !variables.contains(String.valueOf(c))) {
                variables.add(String.valueOf(c));
            }
        }
        return variables;
    }

    /**
     * Render a truth table for the given expression to the standard output
     * The expression is given in the form of a string.
     * The expression can contain the following operators:
     * - NOT: !
     * - AND: &
     * - XOR: ^
     * - OR: |
     *     Example:
     *     Input: "a&b|!a&b"
     *     Output: TODO: show example output
     * @param expression The expression for which the truth table is to be rendered
     * @param variables The list of variables in the expression
     * @return The truth table for the given expression
     */
    private List<String> getTruthTable(String expression, List<String> variables) {
        List<String> truthTable = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, variables.size()); i++) { // Create a row for each possible combination of values (power of 2)
            String row = "";
            for (int j = 0; j < variables.size(); j++) {
                row += (i / (int) Math.pow(2, j)) % 2 + " | ";
            }
            row += evaluateExpression(expression, variables, i);
            truthTable.add(row);
        }
        return truthTable;
    }

    /**
     * Evaluate the given expression for the given truth table row
     * The expression is given in the form of a string.
     * The expression can contain the following operators:
     * - NOT: !
     * - AND: &
     * - XOR: ^
     * - OR: |
     * @param expression The expression to be evaluated
     * @param variables The list of variables in the expression
     * @param row The row for which the expression is to be evaluated
     * @return The value of the expression for the given row
     */
    private String evaluateExpression(String expression, List<String> variables, int row) {
        String evaluatedExpression = expression;
        System.out.println("Expression: " + expression); // Debugging
        System.out.println("Variables: " + variables); // Debugging
        System.out.println("Row: " + row); // Debugging
        for (int i = 0; i < variables.size(); i++) {
            evaluatedExpression = evaluatedExpression.replaceAll(variables.get(i), String.valueOf((row / (int) Math.pow(2, i)) % 2));
        }
        return String.valueOf(evaluatePostfixExpression(infixToPostfix(evaluatedExpression)));
    }

    /**
     * Convert the infix expression to postfix
     * The infix expression is given in the form of a string.
     * The infix expression can contain the following operators:
     * - NOT: !
     * - AND: &
     * - XOR: ^
     * - OR: |
     *     Example:
     *     Input: "a&b|!a&b"
     *     Output: "ab&!ab&|"
     */
    private String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix += c;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix += stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            postfix += stack.pop();
        }
        System.out.println("Postfix: " + postfix); // Debugging
        return postfix;
    }

    /**
     * Returns the precedence of the given operator.
     * The precedence of the operators is as follows:
     * - NOT: 4
     * - AND: 3
     * - XOR: 2
     * - OR: 1
     * - Others: 0
     */
    private int getPrecedence(char operator) {
        if (operator == '!') {
            return 4;
        } else if (operator == '&') {
            return 3;
        } else if (operator == '^') {
            return 2;
        } else if (operator == '|') {
            return 1;
        } else {
            return 0; // Others e.g. non-operator characters
        }
    }

    /**
     * Evaluate the postfix expression
     * The postfix expression is given in the form of a string.
     * The postfix expression can contain the following operators:
     * - NOT: !
     * - AND: &
     * - XOR: ^
     * - OR: |
     *     Example:
     *     Input: "1&0|1&0"
     *     Output: 0
     *     Input: "1&1|1&0"
     *     Output: 1
     *     Input: "a|!a"
     *     Output: 1
     */
    private int evaluatePostfixExpression(String expression) {
        Stack<Integer> stack = new Stack<>();
        //System.out.println("Expression: " + expression); // Debugging
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
                //System.out.println("c: " + c + ", Stack: " + stack); // Debugging
            } else {
                if (stack.isEmpty())
                    continue;
                    //throw new IllegalArgumentException("Stack is empty"); // Oops, something went wrong
                int operand2 = stack.pop();
                if (stack.isEmpty()) {
                    System.out.printf("Stack is empty after pop into operand2%n");
                    System.out.printf("c: %c, Operand1: undefined, Operand2: %d%n", c, operand2); // Debugging
                    if (c == '!') { // Take the complement of the operand
                        stack.push(operand2 == 0 ? 1 : 0); // Push the complement back to the stack
                    }
                    else
                        stack.push(operand2); // Push the unused operand back to the stack
                    continue; // Need two operands to evaluate the expression
                }
                int operand1 = stack.pop();
                System.out.printf("c: %c, Operand1: %d, Operand2: %d, Stack: %s%n", c, operand1, operand2, stack); // Debugging
                if (c == '!') { // Take the complement of the operand
                    stack.push(operand2 == 0 ? 1 : 0); // Push the complement back to the stack
                    stack.push(operand1); // Push the unused operand back to the stack
                } else if (c == '&') { // Note: test equation a&!b&c|a&b|!c&!b|a&!c
                    stack.push(operand1 & operand2); // 11!&1&11&|1!1!&|11!&|
                } else if (c == '^') {
                    stack.push(operand1 ^ operand2);
                } else if (c == '|') {
                    stack.push(operand1 | operand2);
                }
            }
        }
        int ret = stack.pop();
        System.out.printf("return %d%n", ret); // Debugging
        return ret;
    }
}