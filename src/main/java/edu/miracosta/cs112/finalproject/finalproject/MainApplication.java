package edu.miracosta.cs112.finalproject.finalproject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;

import java.io.IOException;
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneController.init(stage);
        SceneController.setActiveWindow(SceneController.getMainMenu());

        stage.setTitle("Mini Game Collection");
        stage.setScene(new Scene(SceneController.getWrapper().build(), 960, 720));
        stage.show();
    }

    public static void main(String[] args)  { launch(args); }
}