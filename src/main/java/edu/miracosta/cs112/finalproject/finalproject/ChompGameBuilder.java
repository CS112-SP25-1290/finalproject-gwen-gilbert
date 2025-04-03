package edu.miracosta.cs112.finalproject.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class ChompGameBuilder extends WrappedSceneBuilder {
    public ChompGameBuilder(EventHandler<ActionEvent> event) {
        super(event);
    }

    @Override
    public Region build() {
        Label label = new Label("Chomp!");
        standardise(label);

        Button button1 = new Button("Play");
        standardise(button1);
        button1.setOnAction(onExitEvent);

        Button button2 = new Button("Change Starting Player");
        standardise(button2);
        button2.setOnAction(onExitEvent);

        Button button3 = new Button("Change Board Size");
        standardise(button3);
        button3.setOnAction(onExitEvent);

        Button button4 = new Button("Exit");
        standardise(button4);
        button4.setOnAction(onExitEvent);

        VBox retval = new VBox(label, button1, button2, button3, button4);
        retval.setAlignment(Pos.CENTER);
        retval.setSpacing(10);
        return retval;
    }
}
