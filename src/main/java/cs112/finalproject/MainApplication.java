package cs112.finalproject;
import cs112.finalproject.controllers.SceneWrapperController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(SceneController.getWrapper().build()));
        SceneController.init(stage);
        SceneController.switchToMainMenu();
        stage.setTitle("Mini Game Collection");
        stage.show();
    }

    public static void main(String[] args)  { launch(args); }
}