package cs112.finalproject.controllers;

import cs112.finalproject.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SceneWrapperController {
    @FXML
    public BorderPane wrapper;
    @FXML
    public ImageView headerImage;

    public void changeHeader(Image image) {
        headerImage.setImage(image);
    }

    public void setActiveWindow(Node node) {
        wrapper.setCenter(node);
    }

    public Parent initialise() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneWrapperController.class.getResource("wrapper-view.fxml"));
        return loader.load();
    }
}
