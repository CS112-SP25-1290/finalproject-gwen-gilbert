package cs112.finalproject;

import cs112.finalproject.builders.ApplicationWrapperBuilder;
import cs112.finalproject.builders.ChompGame;
import cs112.finalproject.builders.MainMenuBuilder;
import cs112.finalproject.builders.TicTacToeGame;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Helper class that stores the current instances of all relevant scenes and controls switching between them.
 */
public class SceneController {
    private static ApplicationWrapperBuilder wrapper;
    private static MainMenuBuilder mainMenu;
    private static ChompGame chompGame;
    private static TicTacToeGame ticTacToe;

    // The Region that wraps the entire application.
    public static ApplicationWrapperBuilder getWrapper() { return wrapper; }

    /**
     * Switches the current scene to the main menu.
     */
    public static void switchToMainMenu() {
        wrapper.setHeader(wrapper.getGamesLogo());
        wrapper.setFooter(SceneUtils.MAIN_FOOTER);
        SceneController.setActiveWindow(mainMenu.getSceneRegion());
    }

    /**
     * Switches the current scene to Chomp!
     */
    public static void switchToChompGame() {
        wrapper.setHeader(chompGame.getHeaderImage());
        wrapper.setFooter(SceneUtils.CHOMP_FOOTER);
        chompGame.switchToStartMenu();
    }

    /**
     * Switches the current scene to Tic-Tac-Toe.
     */
    public static void switchToTicTacToe() {
        wrapper.setHeader(ticTacToe.getHeaderImage());
        wrapper.setFooter(SceneUtils.TIC_TAC_TOE_FOOTER);
        ticTacToe.switchToStartMenu();
    }

    public static ChompGame getChompGame() { return chompGame; };
    public static TicTacToeGame getTicTacToe() { return ticTacToe; };

    /**
     * Initialises the program's games and major scenes.
     * @param stage The Stage for the current Application
     */
    public static void init(Stage stage) {
        wrapper = new ApplicationWrapperBuilder();
        chompGame = new ChompGame();
        ticTacToe = new TicTacToeGame();
        mainMenu = new MainMenuBuilder(event -> stage.close());
    }

    /**
     * Changes the currently shown scene.
     * @param region Region for the scene to switch to.
     */
    public static void setActiveWindow(Region region) { wrapper.setCenter(region); }
}