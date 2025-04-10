package edu.miracosta.cs112.finalproject.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public abstract class WrappedSceneBuilder implements Builder<Region> {
    protected EventHandler<ActionEvent> onExitEvent;
    public static final double PREFERRED_WIDTH = 150;
    public static final double PREFERRED_HEIGHT = 30;

    public WrappedSceneBuilder() { }
    public WrappedSceneBuilder(EventHandler<ActionEvent> event) {
        onExitEvent = event;
    }

    public void standardise(Labeled button) {
        button.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        button.setAlignment(Pos.CENTER);
    }
}
