package cs112.finalproject.builders;

import cs112.finalproject.SceneController;
import cs112.finalproject.SceneUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.Random;

public abstract class MiniGameBuilder extends SceneBuilder {
    protected Image headerImage;
    protected Region StartMenuRegion;
    protected Region ChangePlayerRegion;
    protected Label statsLabel;
    protected Button endGameButton;
    protected Random Rand;
    protected String START_MENU_FOOTER;
    protected IntegerProperty userWins; // stores times user has won, has built-in listener for stats screen
    protected IntegerProperty computerWins;
    protected boolean playerTurn;
    protected boolean gameHasTied;
    protected StartingPlayer firstTurnPlayer;
    private static final StartingPlayer DEFAULT_FIRST_PLAYER = StartingPlayer.USER;

    public MiniGameBuilder() {
        super();
        Rand = new Random();
        userWins = new SimpleIntegerProperty(0);
        computerWins = new SimpleIntegerProperty(0);
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

    public Image getHeaderImage() { return headerImage; }

    /** Sets the active window to this minigame's game screen and initialises first turn data. */
    public void initialiseGame() throws InterruptedException {
        gameHasTied = false;
        playerTurn = (firstTurnPlayer == StartingPlayer.RANDOM ? this.Rand.nextBoolean() : firstTurnPlayer == StartingPlayer.USER);
        SceneController.setActiveWindow(this.build());
        if (playerTurn) {
            SceneController.getWrapper().setFooter("You go first!");
        }
        else {
            SceneController.getWrapper().setFooter("L@@KER goes first!");
        }

        startGame();
    }
    protected void startGame() throws InterruptedException {
        if (playerTurn) {
            onPlayerTurn();
        }
        else {
            onComputerTurn();
        }
    }

    /**
     * Method that should be called when the minigame has ended.
     * Iterates the win stats and lets the player return to the game's starting menu.
     */
    protected void endGame() {
        if (gameHasTied) {
            SceneController.getWrapper().setFooter("Tie! Everyone loses !!!");
        }
        else if (playerTurn) {
            userWins.setValue(userWins.getValue() + 1);
            SceneController.getWrapper().setFooter("You win !!!");
        }
        else {
            computerWins.setValue(computerWins.getValue() + 1);
            SceneController.getWrapper().setFooter("You lose, bwamp-bwamp!");
        }
        endGameButton.setVisible(true);
        endGameButton.setDisable(false);
    }

    /**
     * Determines whether the game has ended or not.
     * @return Whether the game has ended or not.
     */
    protected abstract boolean gameHasEnded();
    protected abstract void onPlayerTurn();
    protected abstract void onComputerTurn();

    protected void changePlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
        if (playerTurn) {
            SceneController.getWrapper().setFooter("Your turn!");
            onPlayerTurn();
        }
        else {
            SceneController.getWrapper().setFooter("L@@KER's turn!");
            onComputerTurn();
        }
    }

    /**
     * Initialises the Button that will be displayed at the end of the minigame.
     * MUST be called before endGame() is called.
     * @param parent The Region to bind the endGameButton to.
     */
    protected void initialiseEndButton(Region parent) {
        endGameButton = SceneUtils.newButton("Return to Start Menu", ev -> switchToStartMenu());
        SceneUtils.bindSize(endGameButton, parent, 0, 12);
        endGameButton.setDisable(true);
        endGameButton.setVisible(false);
    }

    /** Sets the active window to this minigame's start menu screen. */
    public void switchToStartMenu() {
        SceneController.setActiveWindow(this.StartMenuRegion);
        SceneController.getWrapper().setFooter(START_MENU_FOOTER);
        if (statsLabel != null) {
            statsLabel.setText("Player wins: " + userWins + "\nComputer wins: " + computerWins);
        }
    }

    /** Constructs the nodes used to control which player goes first. */
    private Region buildChangePlayer() {
        HBox retval = new HBox();
        Label currentFirstTurn = SceneUtils.newLabel("Current First Turn Player: ");
        SceneUtils.bindSize(currentFirstTurn, retval, 0, 12);

        ChoiceBox<StartingPlayer> choiceBox = new ChoiceBox<StartingPlayer>();
        choiceBox.getItems().addAll(StartingPlayer.USER, StartingPlayer.COMPUTER,StartingPlayer.RANDOM);
        choiceBox.setValue(firstTurnPlayer);
        SceneUtils.bindFontSize(choiceBox);
        SceneUtils.bindSize(choiceBox, retval, 0, 12);
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number value, Number newValue) {
                firstTurnPlayer = StartingPlayer.values()[newValue.intValue()];
            }
        });
        retval.getChildren().addAll(currentFirstTurn, choiceBox);
        retval.setAlignment(Pos.CENTER);
        return retval;
    }

    protected enum StartingPlayer {
        USER,
        COMPUTER,
        RANDOM
    }
}