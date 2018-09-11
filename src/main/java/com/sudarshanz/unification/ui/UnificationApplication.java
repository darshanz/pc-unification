package com.sudarshanz.unification.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main FX Application Class
 * @author  Sudarshan
 */
public class UnificationApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(new StackPane());
        ScreenManager screenManager = new ScreenManager(primaryStage, scene);
        screenManager.showMainView();
        primaryStage.setTitle("Unificaton");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
