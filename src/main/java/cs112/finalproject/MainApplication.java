package cs112.finalproject;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    public static SimpleDoubleProperty fontSize = new SimpleDoubleProperty(12);
    @Override
    public void start(Stage stage) throws IOException {
        SceneController.init(stage);
        Scene scene = new Scene(SceneController.getWrapper().build());
        stage.setScene(scene);
        fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(120));
        SceneController.switchToMainMenu();
        stage.setTitle("Mini Game Collection");
        stage.show();
    }

    public static void main(String[] args)  { launch(args); }
}