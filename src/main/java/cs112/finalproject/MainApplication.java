package cs112.finalproject;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    // define a standard text size for the program.
    public static SimpleDoubleProperty fontSize = new SimpleDoubleProperty(12);
    @Override
    public void start(Stage stage) {
        SceneController.init(stage); // initialise the SceneController and all mini-programs and scenes.
        Scene scene = new Scene(SceneController.getWrapper().build());
        stage.setScene(scene);
        // allows the font size to scale with the size of the application window.
        fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(120));
        SceneController.switchToMainMenu();
        stage.setTitle("Mini Game Collection");
        stage.show();
    }

    public static void main(String[] args)  { launch(args); }
}