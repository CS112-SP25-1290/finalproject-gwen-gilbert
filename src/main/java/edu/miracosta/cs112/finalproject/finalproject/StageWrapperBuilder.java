package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

/**
 * StageBuilder for a Region that will wrap the entire Scene.
 * Changing to different Regions is done via this class.
 */
public class StageWrapperBuilder implements Builder<Region> {
    private BorderPane pane;
    @Override
    public Region build() {
        return pane = new BorderPane();
    }
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
