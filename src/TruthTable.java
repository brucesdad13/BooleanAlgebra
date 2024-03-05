/**
 * A class that represents a truth table of a given Boolean Algebra expression.
 * The class can be used to print the truth table
 * by Charles Stevenson (brucesdad13@gmail.com) @brucesdad13
 * Revision History:
 * 2024-03-05: Initial working version (needs further testing to be sure)
 * TODO: add additional operator symbols:
 * - Conjunction (and): ∧ *
 * - Disjunction (or): ∨ +
 * - Negation (not): ¬ ' ~
 * TODO: support implicit AND operator e.g. AB instead of A&B
 * TODO: simplify with laws of Boolean Algebra:
 * - Idempotent Law: A + A = A and A * A = A
 * - Domination (Null) Law: A + 0 = A and A * 1 = A
 * - Identity Law: A + 1 = 1 and A * 0 = 0
 * - Complement Law: A + A' = 1 and A * A' = 0
 * - Commutative Law: A + B = B + A and A * B = B * A
 * - Associative Law: A + (B + C) = (A + B) + C and A * (B * C) = (A * B) * C
 * - Distributive Law: A * (B + C) = A * B + A * C and A + (B * C) = (A + B) * (A + C)
 * - Absorption Law: A + A * B = A and A * (A + B) = A
 * - De Morgan's Law: (A + B)' = A' * B' and (A * B)' = A' + B'
 * - Consensus Theorem: A * B + A' * C + B * C = A * B + A' * C
 * - Involution Law: (A')' = A
 * TODO: get the simplified expression and get the canonical expression.
 * TODO: conversion between POS (Product of Sums) and SOP (Sum of Products) forms.
 */
import java.util.ArrayList;
import java.util.Comparator;
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
     * Print the truth table to the standard output
     */
    public void printTruthTable() {
        for (String variable : variables) {
            System.out.print(variable + " ");
        }
        System.out.println(" | F=" + expression);
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
     *  Example Input: "a&b|!a&b"
     *  Example Output: [a, b]
     * @param expression The expression from which the variables are to be extracted
     */
    private List<String> getVariables(String expression) {
        List<String> variables = new ArrayList<>();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c) && !variables.contains(String.valueOf(c))) {
                variables.add(String.valueOf(c));
            }
        }
        variables.sort(Comparator.naturalOrder()); // Sort in ascending order to match the truth table
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
     * Example Input: "A&B&!C|B&!C|!A&B|C"
     * Example Output:
     * A B C  | F=A&B&!C|B&!C|!A&B|C
     * 0 0 0  | 0
     * 0 0 1  | 1
     * 0 1 0  | 1
     * 0 1 1  | 1
     * 1 0 0  | 0
     * 1 0 1  | 1
     * 1 1 0  | 1
     * 1 1 1  | 1
     * @param expression The expression for which the truth table is to be rendered
     * @param variables The list of variables in the expression
     * @return The truth table for the given expression
     * TEST: (DD+AAC)DB+CC aka (D&D|A&A&C)&D&B|C&C
     */
    private List<String> getTruthTable(String expression, List<String> variables) {
        List<String> truthTable = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, variables.size()); i++) { // Create a row for each possible combination of values (power of 2)
            String row = "";
            for (int j = variables.size() - 1; j >=0; j--) {
                row += (i >> j) % 2 + " "; // Print the binary representation of the row
            }
            row = row + " | " + evaluateExpression(expression, variables, i);
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
        variables = variables.reversed();
        for (int j = variables.size() - 1; j >=0; j--) {
            evaluatedExpression = evaluatedExpression.replaceAll(variables.get(j), String.valueOf((row >> j) % 2));
        }
        //System.out.println("Evaluated expression: " + evaluatedExpression); // Debug before postfix
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
     * @param expression The infix expression to be converted to postfix
     * @return The postfix expression
     */
    private String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";
        for (char c : expression.toCharArray()) {
            if (isOperand(c)) { // '0' or '1'
                postfix += c;
            } else if (c == '(') { // '(' open parenthesis
                stack.push(c);
            } else if (c == ')') { // ')' close parenthesis
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                stack.pop();
            } else if (c == '!') { // '!' (NOT) unary operator
                stack.push(c);
                continue;
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
     * Returns true if the given character is a binary operand, false otherwise
     * The binary operand characters are as follows:
     * - AND: &
     * - XOR: ^
     * - OR: |
     */
    private boolean isBinaryOperator(char c) {
        return c == '&' || c == '^' || c == '|';
    }

    /**
     * Returns true if the given character is a unary operand, false otherwise
     * The unary operand characters are as follows:
     * - NOT: !
     */
    private boolean isUnaryOperator(char c) {
        return c == '!';
    }

    /**
     * Returns true if the given character is a unary operand, false otherwise
     * The unary operand characters are as follows:
     * - NOT: !
     */
    private boolean isUnaryOperator(int c) {
        return c + '0' == '!';
    }
    /**
     * Returns true if the given character is an operand, false otherwise
     * The operand characters are as follows:
     * - 0
     * - 1
     */
    private boolean isOperand(char c) {
        return Character.isDigit(c);
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
        for (char c : expression.toCharArray()) {
            if (isOperand(c)) { // '0' or '1'
                stack.push(c - '0');
            } else if (isBinaryOperator(c)) { // '&' (AND) or '^' (XOR) or '|' (OR)
                if (stack.isEmpty())
                    continue;
                    //throw new IllegalArgumentException("Stack is empty"); // Oops, something went wrong
                int operand2 = stack.pop();
                if (stack.isEmpty()) {
                    System.out.printf("Stack is empty after pop into operand2%n");
                    stack.push(operand2); // Push the unused operand back to the stack
                    continue; // Need two operands to evaluate the expression
                }
                int operand1 = stack.pop();
                if (c == '&') { // Note: test equation a&!b&c|a&b|!c&!b|a&!c
                    stack.push(operand1 & operand2); // bitwise AND
                } else if (c == '^') {
                    stack.push(operand1 ^ operand2); // bitwise XOR
                } else if (c == '|') {
                    stack.push(operand1 | operand2); // bitwise OR
                }
            }
            else if (isUnaryOperator(c)) { // '!' (NOT)
                if (stack.isEmpty())
                    continue;
                    //throw new IllegalArgumentException("Stack is empty"); // Oops, something went wrong
                while(isUnaryOperator(c)) {
                    int operand = stack.pop();
                    stack.push(operand == 0 ? 1 : 0); // Take the complement of the operand and push it back to the stack
                    if (isUnaryOperator(stack.peek())) {
                        continue;
                    }
                    else {
                        break;
                    }
                }
            }
            else {
                throw new IllegalArgumentException("Invalid character: " + c); // Oops, something went wrong
            }
        }
        return stack.pop();
    }
}