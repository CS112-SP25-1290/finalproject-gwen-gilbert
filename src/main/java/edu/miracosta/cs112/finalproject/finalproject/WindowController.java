package edu.miracosta.cs112.finalproject.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WindowController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onPlayChompClick() {
        ChompGame game = new ChompGame();
        game.startProgram();
    }
    @FXML
    protected void onPlayTicTacToeClick() {
        ChompGame game = new ChompGame();
        game.startProgram();
    }
}