package cs112.finalproject;

import cs112.finalproject.controllers.SceneController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Random;

public abstract class MiniGameBuilder extends SceneBuilder {
    protected Image headerImage;
    protected Region StartMenuRegion;
    protected Region ChangePlayerRegion;

    protected Random Rand;
    protected int userWins;
    protected int computerWins;

    protected StartingPlayer firstTurnPlayer;
    private static final StartingPlayer DEFAULT_FIRST_PLAYER = StartingPlayer.USER;

    public MiniGameBuilder()
    {
        super();
        Rand = new Random();
        userWins = computerWins = 0;
        firstTurnPlayer = DEFAULT_FIRST_PLAYER;
        onExitEvent = event -> SceneController.switchToMainMenu();
        StartMenuRegion = this.buildStartMenu();
        ChangePlayerRegion = new ChangePlayerBuilder().build();
    }

    /**
     * Main method for starting the minigame. Should be the ONLY method called by outside classes.
     */
    public void startGame() {
        SceneController.setActiveWindow(this.buildScene());
    }

    /**
     * Method for returning to the start menu.
     */
    protected void returnToStartMenu() { SceneController.setActiveWindow(this.StartMenuRegion); }

    protected void onChangePlayer() {
        SceneController.setActiveWindow(ChangePlayerRegion);
    }
    /**
     * Build method for creating the StartMenuRegion.
     * @return Region representing the start menu UI.
     */
    protected abstract Region buildStartMenu();

    public Image getHeaderImage() { return headerImage; }
    public class ChangePlayerBuilder extends SceneBuilder {
        @Override
        public Region buildScene() {
            Label currentFirstTurn = new Label("Current First Turn Player: " + firstTurnPlayer);
            ChoiceBox<StartingPlayer> choiceBox = new ChoiceBox<StartingPlayer>();
            choiceBox.getItems().add(StartingPlayer.USER);
            choiceBox.getItems().add(StartingPlayer.COMPUTER);
            choiceBox.getItems().add(StartingPlayer.RANDOM);
            choiceBox.setValue(firstTurnPlayer);

            VBox retval = new VBox();
            return retval;
        }

        public void setStartingPlayer() {
            switch (firstTurnPlayer) {
                case USER -> firstTurnPlayer = StartingPlayer.COMPUTER;
                case COMPUTER -> firstTurnPlayer = StartingPlayer.RANDOM;
                case RANDOM -> firstTurnPlayer = StartingPlayer.USER;
            }
        }
    }
    protected enum StartingPlayer {
        USER,
        COMPUTER,
        RANDOM
    }
}