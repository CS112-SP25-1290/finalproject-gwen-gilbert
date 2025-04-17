package cs112.finalproject;

import cs112.finalproject.controllers.SceneWrapperController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static ApplicationWrapperBuilder wrapper;
    private static MainMenuBuilder mainMenu;
    private static ChompGame chompGame;
    private static Region ticTacToe;

    protected static ApplicationWrapperBuilder getWrapper() { return wrapper; }
    public static void switchToMainMenu() {
        //wrapper.setHeader(null);
        SceneController.setActiveWindow(mainMenu.getSceneRegion());
    }
    public static void switchToChompGame() {
        //wrapper.setHeader(null);
        chompGame.switchToStartMenu();
    }
    public static void switchToTicTacToe() {
        //wrapper.setHeader(null);
        //SceneController.setActiveWindow(ticTacToe.getSceneRegion());
    }

    protected static void init(Stage stage) throws IOException {
        wrapper = new ApplicationWrapperBuilder();
        chompGame = new ChompGame();
        //ticTacToe = new ChompGameBuilder(event -> SceneController.switchToMainMenu()).build();
        mainMenu = new MainMenuBuilder(event -> stage.close());
    }

    /**
     * Changes the currently shown Region.
     * @param region Region to switch to.
     */
    public static void setActiveWindow(Region region) { wrapper.setCenter(region); }
}