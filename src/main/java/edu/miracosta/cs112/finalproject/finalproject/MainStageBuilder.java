package edu.miracosta.cs112.finalproject.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.*;
import javafx.scene.layout.*;

/**
 * Builds the main Region that wraps all other scenes, used to streamline scene changes
 */
public class MainStageBuilder extends WrappedSceneBuilder {
    public MainStageBuilder(EventHandler<ActionEvent> event) {
        super(event);
    }

    @Override
    public Region build() {
        Label label = new Label("Main Menu");
        standardise(label);

        Button button1 = new Button("Play Chomp");
        standardise(button1);
        button1.setOnAction(event -> SceneController.setActiveWindow(SceneController.getChompGame()));

        Button button2 = new Button("Play Tic-Tac-Toe");
        standardise(button2);
        button2.setOnAction(event -> SceneController.setActiveWindow(SceneController.getTicTacToe()));

        Button button3 = new Button("Play Nothing");
        standardise(button3);
        //button3.setOnAction(onExitEvent);

        Button button4 = new Button("Exit");
        standardise(button4);
        button4.setOnAction(onExitEvent);

        VBox retval = new VBox(label, button1, button2, button3, button4);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }
}
