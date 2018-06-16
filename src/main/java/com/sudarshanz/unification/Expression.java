package com.sudarshanz.unification;

import java.util.ArrayList;

/**
 * Expression
 * Representation of the Predicate calculus expression.
 * Uses LIST SYNTAX
 *
 * @author Sudarshan
 */
public class Expression {
    public final static int TYPE_ATOM = 0;
    public final static int TYPE_VARIABLE = 1;
    public final static int TYPE_LIST = 2;

    private ArrayList<Expression> expressionArrayList = new ArrayList<>();
    private String expressionStr = "";


    /**
     * Constructor
     * Takes the expression string and prepares a list if type list
     *
     * @param expressionStr
     */
    public Expression(String expressionStr) {
        this.expressionStr = expressionStr.trim();
        prepareList();
    }

    /**
     * Prepare the list of the Expressions from the given expression
     * when the type of expression is TYPE_LIST
     */
    private void prepareList() {
        if (getType() == TYPE_LIST) {
            String tempStr = expressionStr.substring(1, expressionStr.length() - 1) + " "; //Remove outer parenthesis
            ArrayList<Integer> starts = new ArrayList<>();
            int lastSpaceChar = -1; //just intitialized
            for (int i = 0; i < tempStr.length(); i++) {

                if (tempStr.charAt(i) == ' ') {
                    if (starts.isEmpty()) {
                        String currentExpression = tempStr.substring(lastSpaceChar + 1, i + 1).trim();
                        if (currentExpression.contains("(") && !currentExpression.contains(")")) {
                            //SKIP
                        } else if (currentExpression.contains(")") && !currentExpression.contains("(")) {
                            //SKIP
                        } else {
                            expressionArrayList.add(new Expression(currentExpression));

                        }
                    }
                    lastSpaceChar = i;
                }

                if (tempStr.charAt(i) == '(') {
                    if (i > 0) {
                        if (tempStr.charAt(i - 1) == ' ') {
                            starts.add(i);
                        }
                    } else {
                        starts.add(i);
                    }

                }
                if (tempStr.charAt(i) == ')') {
                    if (starts.size() > 0) {
                        String currentExpression = tempStr.substring(starts.get(starts.size() - 1), i + 1);
                        expressionArrayList.add(new Expression(currentExpression));
                        starts.remove(starts.size() - 1);
                    }
                }
            }
        }
    }


    /**
     * Determine the type of the Expression
     * This method follows simple rules
     * such as :
     * If the expression begins with '(', based ont he LIST SYNTAX, it is considered a TYPE_LIST
     * The expressions with single characters such as X, Y etc are TYPE_VARIABLES
     * Other expressions are considered as TYPE_ATOM
     *
     * @return
     */
    public int getType() {
        String first = expressionStr.substring(0, 1);
        if (first.equals("(")) {
            return TYPE_LIST;
        } else if (expressionStr.length() == 1) {
            return TYPE_VARIABLE;
        } else {
            return TYPE_ATOM;
        }
    }


    /**
     * This is used to determine if given expression is empty list type.
     *
     * @return whether the expression is an empty list
     */
    public boolean isEmptyList() {
        if (getType() == TYPE_LIST) {
            return expressionArrayList.size() == 0;
        } else {
            return false;
        }
    }


    /**
     * For checking equality of two expressions
     *
     * @param expression
     * @return
     */
    public boolean isEqualTo(Expression expression) {
        return expressionStr.equals(expression.toString());
    }

    /**
     * Check if one expression occurs in another
     *
     * @param expression
     * @return
     */
    public boolean occursIn(Expression expression) {
        if (expression.getType() == Expression.TYPE_LIST) {
            for (Expression exp : expression.getExpressionArrayList()) {
                if (expressionStr.equals(exp)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the list of expressions in a given expression of type TYPE_LIST
     * For non TYPE_LIST expressions this method returns an empty ArrayList
     * <p>
     * CAUTION : for checking if the Expression is empty list, emEmptyList() method should be used
     *
     * @return the expressions in a TYPE_LIST type expression.
     */
    public ArrayList<Expression> getExpressionArrayList() {
        return expressionArrayList;
    }

    /**
     * Get the first Expression in the given Expression List
     *
     * @return
     */
    public Expression getFirst() {
        return expressionArrayList.get(0);
    }

    /**
     * Get the size of given expression
     *
     * @return size of the expression
     */
    public int getSize() {
        return expressionArrayList.size();
    }

    /**
     * String representation of the Expression
     *
     * @return
     */
    public String toString() {
        return expressionStr;
    }

}
