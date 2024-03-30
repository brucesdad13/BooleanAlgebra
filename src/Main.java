/**
 * Takes a boolean algebra expression from the standard input
 * and prints the truth table for it.
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
 * The expression is given in the form of a string.
 * The expression can contain the following operators:
 * - NOT: !
 * - AND: &
 * - XOR: ^
 * - OR: |
 *  Example:
 * TODO: update examples
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
            System.out.println("Use & for AND, | for OR, ^ for XOR, and ! for NOT.");
            System.out.println("Every operand must be separated by an operator.");
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
