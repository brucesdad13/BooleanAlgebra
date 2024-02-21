/**
 * Takes a boolean algebra expression from the standard input
 * and returns the truth table for it.
 * It also returns the simplified expression and the canonical form.
 * The expression is given in the form of a string.
 * The expression can contain the following operators:
 * - NOT: !
 * - AND: &
 * - XOR: ^
 * - OR: |
 *     Example:
 *     Input: "a&b|!a&b"
 *     Output:
 *     a | b | a&b | !a&b | a&b|!a&b
 *     0 | 0 |  0  |  0   |   0
 *     0 | 1 |  0  |  1   |   1
 *     1 | 0 |  0  |  0   |   0
 *     1 | 1 |  1  |  0   |   1
 *     Simplified: b
 *     Canonical: b
 *     Input: "a&b|!a&b"
 *     Output:
 *     a | b | a&b | !a&b | a&b|!a&b
 *     0 | 0 |  0  |  0   |   0
 *     0 | 1 |  0  |  1   |   1
 *     1 | 0 |  0  |  0   |   0
 *     1 | 1 |  1  |  0   |   1
 *     Simplified: b
 *     Canonical: b
 */
import java.util.*; // For Stack and Scanner
public class Main {
    /** Main method */
    public static void main(String[] args) {
        String expression;
        if (args.length > 0) { // If an expression is given as a command line argument, use it
            expression = args[0];
        } else { // If no expression is given as a command line argument, read it from the standard input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the boolean algebra expression: ");
            expression = scanner.nextLine();
        }
        //System.out.printf("Expression: %s%n", expression); // Debugging
        TruthTable truthTable = new TruthTable(expression);
        truthTable.printTruthTable();
        //System.out.println("Simplified: " + truthTable.getSimplifiedExpression());
        //System.out.println("Canonical: " + truthTable.getCanonicalExpression());
    }
}
