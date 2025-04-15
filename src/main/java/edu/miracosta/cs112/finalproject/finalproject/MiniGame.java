package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.layout.Region;

import java.util.Random;

public abstract class MiniGame extends WrappedSceneBuilder {
    protected Region StartMenuRegion;
    protected Region GameRegion;
    protected Region ChangePlayerRegion;

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
        onExitEvent = event -> SceneController.setActiveWindow(SceneController.getMainMenu());
        StartMenuRegion = this.buildStartMenu();
        GameRegion = this.build();
        ChangePlayerRegion = new ChangePlayerBuilder().build();
    }

    /**
     * Main method for starting the minigame. Should be the ONLY method called by outside classes.
     */
    public void startGame() {
        SceneController.setActiveWindow(this.GameRegion);
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
    
    /**
     * Method that should contain the actual game logic.
     */
    protected abstract void doGameLogic();

    public class ChangePlayerBuilder extends WrappedSceneBuilder {
        protected Region region;

        @Override
        public Region build() {
            return null;
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