package com.sudarshanz.unification.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 *
 * Dialog for about
 *
 * @author Sudarshan
 */
public class AboutDialogController {

    private Stage dialogStage;
    @FXML
    private void initialize() {
    }
    public void showDialog(){
        dialogStage.show();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}