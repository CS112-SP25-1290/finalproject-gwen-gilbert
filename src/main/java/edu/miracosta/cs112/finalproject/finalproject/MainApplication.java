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

    private static StageWrapperBuilder wrapper;
    private static Region mainMenu;
    private static Region chompGame;
    private static Region ticTacToe;
    //private static Region mainMenu;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Application");
        MainApplication.wrapper = new StageWrapperBuilder();
        MainApplication.chompGame = new ChompGameBuilder(event -> wrapper.setCenter(mainMenu)).build();
        MainApplication.ticTacToe = new ChompGameBuilder(event -> wrapper.setCenter(mainMenu)).build();

        MainApplication.mainMenu = new MainStageBuilder(event -> stage.close()).build();

        stage.setScene(new Scene(MainApplication.wrapper.build(), 960, 720));

        wrapper.setCenter(MainApplication.mainMenu);

        stage.show();
    }

    public static StageWrapperBuilder getWrapper() {
        return wrapper;
    }
    public static Region getMainMenu() {
        return mainMenu;
    }
    public static Region getChompGame() {
        return chompGame;
    }
    public static Region getTicTacToe() {
        return ticTacToe;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}