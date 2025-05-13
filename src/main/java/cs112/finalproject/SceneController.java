package cs112.finalproject;

import cs112.finalproject.builders.ApplicationWrapperBuilder;
import cs112.finalproject.builders.ChompGame;
import cs112.finalproject.builders.MainMenuBuilder;
import cs112.finalproject.builders.TicTacToeGame;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SceneController {
    private static ApplicationWrapperBuilder wrapper;
    private static MainMenuBuilder mainMenu;
    private static ChompGame chompGame;
    private static TicTacToeGame ticTacToe;

    public static ApplicationWrapperBuilder getWrapper() { return wrapper; }
    public static void switchToMainMenu() {
        wrapper.setHeader(wrapper.getGamesLogo());
        wrapper.setFooter(SceneUtils.MAIN_FOOTER);
        SceneController.setActiveWindow(mainMenu.getSceneRegion());
    }
    public static void switchToChompGame() {
        wrapper.setHeader(chompGame.getHeaderImage());
        wrapper.setFooter(SceneUtils.CHOMP_FOOTER);
        chompGame.switchToStartMenu();
    }
    public static void switchToTicTacToe() {
        wrapper.setHeader(ticTacToe.getHeaderImage());
        wrapper.setFooter(SceneUtils.TIC_TAC_TOE_FOOTER);
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