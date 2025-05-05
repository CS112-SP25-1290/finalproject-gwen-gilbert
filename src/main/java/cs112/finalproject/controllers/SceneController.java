package cs112.finalproject.controllers;

import cs112.finalproject.ApplicationWrapperBuilder;
import cs112.finalproject.ChompGame;
import cs112.finalproject.MainMenuBuilder;
import cs112.finalproject.TicTacToeGame;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static ApplicationWrapperBuilder wrapper;
    private static MainMenuBuilder mainMenu;
    private static ChompGame chompGame;
    private static TicTacToeGame ticTacToe;

    public static ApplicationWrapperBuilder getWrapper() { return wrapper; }
    public static void switchToMainMenu() {
        wrapper.setHeader(wrapper.getGamesLogo());
        SceneController.setActiveWindow(mainMenu.getSceneRegion());
    }
    public static void switchToChompGame() {
        wrapper.setHeader(chompGame.getHeaderImage());
        chompGame.switchToStartMenu();
    }
    public static void switchToTicTacToe() {
        wrapper.setHeader(ticTacToe.getHeaderImage());
        ticTacToe.switchToStartMenu();
    }

    public static ChompGame getChompGame() { return chompGame; };
    public static TicTacToeGame getTicTacToe() { return ticTacToe; };
    public static void init(Stage stage) {
        wrapper = new ApplicationWrapperBuilder();
        chompGame = new ChompGame();
        ticTacToe = new TicTacToeGame();
        mainMenu = new MainMenuBuilder(event -> stage.close());
    }

    /**
     * Changes the currently shown Region.
     * @param region Region to switch to.
     */
    public static void setActiveWindow(Region region) { wrapper.setCenter(region); }
}