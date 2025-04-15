package edu.miracosta.cs112.finalproject.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public abstract class WrappedSceneBuilder implements Builder<Region> {
    protected EventHandler<ActionEvent> onExitEvent;


    public WrappedSceneBuilder() { }
    public WrappedSceneBuilder(EventHandler<ActionEvent> event) {
        onExitEvent = event;
    }
}
