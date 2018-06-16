package com.sudarshanz.unification;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Example Unification
 *
 * @author Sudarshan
 *
 */
public class Unification {

    public final static String FAIL = "FAIL";
    public final static String EMPTY = "EMPTY";

    //Example
    private static String E1 = "(friends X (brother John))";
    private static String E2 = "(friends (sister Nancy) (brother Y))";


    public static void main(String[] args){

        //First Expression
        System.out.println("Please enter the first Expression:");
        Scanner scanExpression1 = new Scanner(System.in);
        String expression1 = scanExpression1.nextLine();

        //Second Expression
        System.out.println("Please enter the second Expression:");
        Scanner scanExpression2 = new Scanner(System.in);
        String expression2 = scanExpression2.nextLine();

        String result = unify(new Expression(expression1), new Expression(expression2));
        System.out.println("Result:");
        System.out.println(result);
    }

    /**
     * Unification method.
     * @param expression1
     * @param expression2
     * @return
     */
    private static String unify( Expression expression1, Expression expression2){

        if((expression1.getType() == Expression.TYPE_ATOM && expression2.getType() == Expression.TYPE_ATOM) || (expression1.isEmptyList() && expression2.isEmptyList())){

            return expression1.isEqualTo(expression2) ? "{}": FAIL;
        }
        if (expression1.getType() == Expression.TYPE_VARIABLE){
            return expression1.occursIn(expression2) ? FAIL  : "{"+expression2+"/"+expression1+"}";
        }
        if (expression2.getType() == Expression.TYPE_VARIABLE){
            return expression2.occursIn(expression1) ? FAIL  : "{"+expression1+"/"+expression2+"}";
        }
        if(expression1.isEmptyList() || expression2.isEmptyList()){
            return "FAIL";
        }else{
            String subs1 = unify(expression1.getFirst(), expression2.getFirst());
            if(subs1.equals(FAIL)){
                return FAIL;
            }
            Expression te1 = applySubstitution(subs1, expression1);
            Expression te2 = applySubstitution(subs1, expression2);

            String subs2 = unify(te1, te2);

            if(subs2.equals(FAIL)){
                return FAIL;
            }else{
                String unification_result = subs1.replace("{}","") + subs2.replace("{}"," ");
                return unification_result.replace("}{", ", ");
            }
        }
    }

    /**
     *
     * Apply the substitution on given expression
     *
     * @param subs
     * @param expression
     * @return
     */
    private static Expression applySubstitution(String subs, Expression expression){
        //remove outer braces
        subs = subs.replace("{","").replace("}","");
        String[] subsArr = subs.split("/");
        String subAppliedExp = "";
        int count = 0;

        if(expression.getSize() == 1){
            expression = new Expression(expression.toString());
        }
        for (int i = 1; i < expression.getExpressionArrayList().size(); i++){
            count ++;
            if(subs.contains("/")){
                if (expression.getExpressionArrayList().get(i).equals(subsArr[0])) {
                    subAppliedExp = subAppliedExp + " " + subsArr[1];
                }else {
                    subAppliedExp = subAppliedExp + " " + expression.getExpressionArrayList().get(i).toString();
                }
            }else {
                subAppliedExp = subAppliedExp + " " + expression.getExpressionArrayList().get(i).toString();
            }
        }

        if(count > 1){
            subAppliedExp = "("+ subAppliedExp.trim() + ")";
        }
        return new Expression(subAppliedExp);
    }

}
