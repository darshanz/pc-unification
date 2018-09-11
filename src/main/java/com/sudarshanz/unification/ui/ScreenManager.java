package com.sudarshanz.unification.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Screen manager is used to transition between the screens
 *
 * @author Sudarshan
 */
public class ScreenManager {
    private Scene scene;
    private Stage stage;

    public ScreenManager(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    /**
     * Display Main Window
     */
    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/main.fxml")
            );
            stage.setResizable(false);
            stage.setWidth(520);
            stage.setHeight(400);
           scene.setRoot((Parent) loader.load());
    MainController controller =
                    loader.<MainController>getController();
            controller.initWIthSession(this);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ScreenManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public AboutDialogController getAboutDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            BorderPane page = (BorderPane) loader.load();
            Stage dlgAbout = new Stage();
            dlgAbout.setResizable(false);
            dlgAbout.setTitle("About");
            dlgAbout.initModality(Modality.WINDOW_MODAL);
            dlgAbout.initOwner(stage);
            Scene scene = new Scene(page);
            dlgAbout.setScene(scene);

            AboutDialogController controller = loader.getController();
            controller.setDialogStage(dlgAbout);


            return controller;
        } catch (IOException e) {
             e.printStackTrace();
            return null;
        }
    }
}