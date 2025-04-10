package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.layout.Region;

import java.util.Random;

public abstract class MiniGame extends WrappedSceneBuilder {
    protected Random Rand;
    protected int userWins;
    protected int computerWins;

    protected Region StartMenuRegion;
    protected Region GameRegion;

    protected boolean exitProgram = false;
    protected StartingPlayer firstTurnPlayer;

    private static final StartingPlayer DEFAULT_FIRST_PLAYER = StartingPlayer.USER;

    public MiniGame()
    {
        Rand = new Random();
        userWins = computerWins = 0;
        firstTurnPlayer = DEFAULT_FIRST_PLAYER;
        onExitEvent = event -> MainApplication.getWrapper().setCenter(MainApplication.getMainMenu());
        StartMenuRegion = this.buildStartMenu();
        GameRegion = this.build();
    }

    /**
     * Main method for starting the minigame's program. Should be the ONLY method called by outside classes.
     */
    public void startProgram() {
        do {
            displayGameMenu();
            //exitProgram = handleGameMenu();
        }
        while (!exitProgram);
    }

    /**
     * Method for handling when the game is first opened, such as displaying a settings menu or other pre-game information.
     * @return True if the program should exit out of the loop.
     */
    protected abstract Region buildStartMenu();
    
    /**
     * Method that should contain the actual game logic.
     */
    protected abstract void play();

    /**
     * Method that displays the starting menu when the game is first opened.
     */
    protected abstract void displayGameMenu();

    protected enum StartingPlayer {
        USER,
        COMPUTER,
        RANDOM
    }
}