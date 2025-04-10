package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.layout.Region;

import java.util.Random;

public abstract class MiniGame extends WrappedSceneBuilder {
    protected Region StartMenuRegion;
    protected Region GameRegion;


    protected Random Rand;
    protected int userWins;
    protected int computerWins;

    protected StartingPlayer firstTurnPlayer;

    private static final StartingPlayer DEFAULT_FIRST_PLAYER = StartingPlayer.USER;

    public MiniGame()
    {
        Rand = new Random();
        userWins = computerWins = 0;
        firstTurnPlayer = DEFAULT_FIRST_PLAYER;
        onExitEvent = event -> MainApplication.setActiveWindow(MainApplication.getMainMenu());
        StartMenuRegion = this.buildStartMenu();
        GameRegion = this.build();
    }

    /**
     * Main method for starting the minigame's program. Should be the ONLY method called by outside classes.
     */
    public void startGame() {
        MainApplication.setActiveWindow(GameRegion);
    }

    public void setStartingPlayer() {
        switch (firstTurnPlayer) {
            case USER -> firstTurnPlayer = StartingPlayer.COMPUTER;
            case COMPUTER -> firstTurnPlayer = StartingPlayer.RANDOM;
            case RANDOM -> firstTurnPlayer = StartingPlayer.USER;
        }
    }
    /**
     * Build method for creating the StartMenuRegion.
     * @return Region representing the start menu UI.
     */
    protected abstract Region buildStartMenu();
    
    /**
     * Method that should contain the actual game logic.
     */
    protected abstract void doGameLogic();

    protected enum StartingPlayer {
        USER,
        COMPUTER,
        RANDOM
    }
}