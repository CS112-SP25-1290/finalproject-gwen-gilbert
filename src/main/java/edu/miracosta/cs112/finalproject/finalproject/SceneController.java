package edu.miracosta.cs112.finalproject.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SceneController {
    private static StageWrapperBuilder wrapper;
    private static Region mainMenu;
    private static Region chompGame;
    private static Region ticTacToe;

    protected static StageWrapperBuilder getWrapper() { return wrapper; }
    public static Region getMainMenu() {
        return mainMenu;
    }
    public static Region getChompGame() { return chompGame; }
    public static Region getTicTacToe() { return ticTacToe; }

    protected static void init(Stage stage) {
        wrapper = new StageWrapperBuilder();
        chompGame = new ChompGame().build();
        ticTacToe = new ChompGameBuilder(event -> wrapper.setCenter(mainMenu)).build();
        mainMenu = new MainStageBuilder(event -> stage.close()).build();
    }

    /**
     * Changes the currently shown Region.
     * @param region Region to switch to.
     */
    public static void setActiveWindow(Region region) { wrapper.setCenter(region); }
}