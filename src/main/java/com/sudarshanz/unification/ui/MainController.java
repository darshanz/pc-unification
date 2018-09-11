
package com.sudarshanz.unification.ui;

import com.sudarshanz.unification.Expression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.*;

/**
 *
 * MainController
 * Main screen of the Java FX application
 *
 * @author Sudarshan
 */
public class MainController {

    public final static String FAIL = "FAIL";
    public final static String EMPTY = "EMPTY";

    //Example
    private static String E1 = "(friends X (brother John))";
    private static String E2 = "(friends (sister Nancy) (brother Y))";


    @FXML
    TextField txtFirst;
    @FXML
    TextField txtSecond;
    @FXML
    TextArea txtAreaOutput;

    private ScreenManager manager;

    /**
     * Initialize with session
     * @param screenManager
     */
    public void initWIthSession(final ScreenManager screenManager) {

        Task task = new Task<Void>() {
            @Override
            protected void succeeded() {
                super.succeeded();

            }
            @Override public Void call() {
                 //do initiaizations here

                manager = screenManager;
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    public void handleExitAction(){
         System.exit(0);
    }

    @FXML
    public void handleLoadExampleAction(){
         txtFirst.setText(E1);
         txtSecond.setText(E2);
    }

    @FXML
    public void handleAboutAction(){
        AboutDialogController aboutDialogController =  manager.getAboutDialog();
        aboutDialogController.showDialog();
    }

    @FXML
    public void onUnify(){
        if(txtFirst.getText().isEmpty() && txtSecond.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter the input expressions.", ButtonType.OK);
            alert.setTitle("Empty Expressions");
            alert.show();

            return;
        }


         if(txtFirst.getText().isEmpty()){
             Alert alert = new Alert(Alert.AlertType.NONE,"Please enter first expression.", ButtonType.OK);
             alert.setTitle("Empty Expression");
             alert.show();

             return;
         }

        if(txtSecond.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE,"Please enter second expression.", ButtonType.OK);
            alert.setTitle("Empty Expression");
            alert.show();

            return;
        }


        String result = unify(new Expression(txtFirst.getText()), new Expression(txtSecond.getText()));
        txtAreaOutput.setText(result);

    }



    private static String unify(Expression expression1, Expression expression2){

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
