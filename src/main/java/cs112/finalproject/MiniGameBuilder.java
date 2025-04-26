package cs112.finalproject;

import cs112.finalproject.controllers.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Random;

public abstract class MiniGameBuilder extends SceneBuilder {
    protected Image headerImage;
    protected Region StartMenuRegion;
    protected Region TutorialRegion;
    protected Region ChangePlayerRegion;

    protected Random Rand;
    protected int userWins;
    protected int computerWins;
    protected boolean playerTurn;
    protected StartingPlayer firstTurnPlayer;
    private static final StartingPlayer DEFAULT_FIRST_PLAYER = StartingPlayer.USER;

    public MiniGameBuilder() {
        super();
        Rand = new Random();
        userWins = computerWins = 0;
        firstTurnPlayer = DEFAULT_FIRST_PLAYER;
        onExitEvent = event -> SceneController.switchToMainMenu();
        ChangePlayerRegion = this.buildChangePlayer();
        StartMenuRegion = this.buildStartMenu();
    }

    /**
     * Build method for creating the StartMenuRegion.
     * @return Region representing the start menu UI.
     */
    protected abstract Region buildStartMenu();

    /** Sets the active window to this minigame's game screen and initialises first turn data. */
    public void startGame() {
        SceneController.setActiveWindow(this.SceneRegion);
        if (firstTurnPlayer == StartingPlayer.RANDOM) {
            playerTurn = this.Rand.nextBoolean();
        }
        else {
            playerTurn = firstTurnPlayer == StartingPlayer.USER;
        }

        if (playerTurn) {
            SceneController.getWrapper().setFooter("You go first!");
        }
        else {
            SceneController.getWrapper().setFooter("L@@KER goes first!");
        }
    }

    /** Sets the active window to this minigame's start menu screen. */
    public void switchToStartMenu() { SceneController.setActiveWindow(this.StartMenuRegion); }
    /** Sets the active window to this minigame's tutorial screen. */
    protected void switchToTutorial() { SceneController.setActiveWindow(this.TutorialRegion); }

    public Image getHeaderImage() { return headerImage; }

    /** Constructs the nodes used to control which player goes first. */
    private Region buildChangePlayer() {
        Label currentFirstTurn = new Label("Current First Turn Player: ");
        ChoiceBox<StartingPlayer> choiceBox = new ChoiceBox<StartingPlayer>();
        choiceBox.getItems().add(StartingPlayer.USER);
        choiceBox.getItems().add(StartingPlayer.COMPUTER);
        choiceBox.getItems().add(StartingPlayer.RANDOM);
        choiceBox.setValue(firstTurnPlayer);
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number value, Number newValue) {
                firstTurnPlayer = StartingPlayer.values()[newValue.intValue()];
            }
        });
        HBox retval = new HBox(currentFirstTurn, choiceBox);
        retval.setAlignment(Pos.CENTER);
        return retval;
    }

    protected enum StartingPlayer {
        USER,
        COMPUTER,
        RANDOM
    }
}