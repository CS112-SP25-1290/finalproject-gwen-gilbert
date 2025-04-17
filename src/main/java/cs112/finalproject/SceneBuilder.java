package cs112.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.util.Builder;

/**
 * Base class that
 */
public abstract class SceneBuilder implements Builder<Region> {
    protected EventHandler<ActionEvent> onExitEvent;
    protected Region SceneRegion;

    public SceneBuilder() { this.build(); }

    public SceneBuilder(EventHandler<ActionEvent> event) {
        this();
        this.onExitEvent = event;
    }

    public Region getSceneRegion() { return SceneRegion; }

    public abstract Region buildScene();

    @Override
    public Region build() {
        SceneRegion = buildScene();
        return SceneRegion;
    }
}
