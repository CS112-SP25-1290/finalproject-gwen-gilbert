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
        VBox retval = new VBox();
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);

        Button chompButton = SceneUtils.newButton(null, ev -> SceneController.switchToChompGame());
        chompButton.prefHeightProperty().bind(retval.heightProperty().divide(9));
        ImageView chompImage = new ImageView(SceneController.getChompGame().getHeaderImage());
        chompImage.setCache(true);
        chompImage.fitHeightProperty().bind(chompButton.prefHeightProperty());
        chompImage.setPreserveRatio(true);
        chompButton.setGraphic(chompImage);


        Button ticTacToeButton = SceneUtils.newButton(null, ev -> SceneController.switchToTicTacToe());
        ticTacToeButton.prefHeightProperty().bind(retval.heightProperty().divide(9));
//        ImageView ticTacToeImage = new ImageView(SceneController.getTicTacToe().getHeaderImage());
//        ticTacToeImage.setCache(true);
//        ticTacToeImage.fitHeightProperty().bind(ticTacToeButton.prefHeightProperty());
//        ticTacToeImage.setPreserveRatio(true);
//        ticTacToeButton.setGraphic(ticTacToeImage);

        Button exitButton = SceneUtils.newButton("Quit", onExitEvent);

        retval.getChildren().addAll(chompButton, ticTacToeButton, exitButton);
        return retval;
    }
}
