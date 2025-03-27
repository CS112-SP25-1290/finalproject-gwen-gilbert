package edu.miracosta.cs112.finalproject.finalproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;

import java.io.IOException;
public class MainApplication extends Application {
    public static Stage stage;
    public static Dimension screenSize;
    private static SceneController _sceneController;
    public static SceneController sceneController() {
        return _sceneController;
    }

    public static void main(String[] arg)
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        MainApplication.screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), MainApplication.screenSize.getWidth() / 2, MainApplication.screenSize.getHeight() / 2);
        _sceneController = new SceneController(scene);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}