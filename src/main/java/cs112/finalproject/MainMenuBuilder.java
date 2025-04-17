package cs112.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Builds the main Region that wraps all other scenes, used to streamline scene changes
 */
public class MainMenuBuilder extends SceneBuilder {
    public MainMenuBuilder(EventHandler<ActionEvent> event) {
        super(event);
    }

    @Override
    public Region buildScene() {
        Button chompButton = SceneUtils.newButton("Play Chomp!", ev -> SceneController.switchToChompGame());
        Button ticTacToeButton = SceneUtils.newButton("Play Tic-Tac-Toe!", ev -> SceneController.switchToTicTacToe());
        //Button fooButton = SceneUtils.newButton("Play Nothing!", ev -> SceneController.setActiveWindow(SceneController.getMainMenuScene()));
        Button exitButton = SceneUtils.newButton("Quit", onExitEvent);

        VBox retval = new VBox(chompButton, ticTacToeButton, /*fooButton,*/ exitButton);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }
}
