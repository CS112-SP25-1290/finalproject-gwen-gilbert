package cs112.finalproject;

import cs112.finalproject.controllers.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
        VBox retval = SceneUtils.newVBox();

        Button chompButton = SceneUtils.newButton(null, ev -> SceneController.switchToChompGame());
        SceneUtils.bindSize(chompButton, retval, 8, 8);
        ImageView chompImage = SceneUtils.newImageView(SceneController.getChompGame().getHeaderImage());
        chompImage.fitHeightProperty().bind(chompButton.prefHeightProperty());
        chompButton.setGraphic(chompImage);

        Button ticTacToeButton = SceneUtils.newButton(null, ev -> SceneController.switchToTicTacToe());
        SceneUtils.bindSize(chompButton, retval, 8, 8);
//        ImageView ticTacToeImage = SceneUtils.newImageView(SceneController.getTicTacToe().getHeaderImage());
//        ticTacToeImage.setCache(true);
//        ticTacToeImage.fitHeightProperty().bind(ticTacToeButton.prefHeightProperty());
//        ticTacToeImage.setPreserveRatio(true);
//        ticTacToeButton.setGraphic(ticTacToeImage);

        Button exitButton = SceneUtils.newButton("Quit", onExitEvent);
        SceneUtils.bindSize(exitButton, retval);

        retval.getChildren().addAll(chompButton, ticTacToeButton, exitButton);
        return retval;
    }
}
