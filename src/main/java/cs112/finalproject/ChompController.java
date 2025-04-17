package cs112.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChompController {
    @FXML
    private Label welcomeText;
    private ChompGame chompGame;

    @FXML
    protected void onPlayChompClick() {
        ChompGame game = new ChompGame();
        //game.startProgram();
    }
    @FXML
    protected void onChangePlayerClick() {
        ChompGame game = new ChompGame();
        //game.startProgram();
    }
    @FXML
    protected void onChangeBoardClick() {
        ChompGame game = new ChompGame();
        //game.startProgram();
    }

    @FXML
    protected void onExitClick() {
        ChompGame game = new ChompGame();
        //game.startProgram();
    }
}