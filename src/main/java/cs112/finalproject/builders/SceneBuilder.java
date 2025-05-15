package cs112.finalproject.builders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.util.Builder;

/**
 * Base class for constructing scenes.
 * Has support for a built-in scene exit event
 */
public abstract class SceneBuilder implements Builder<Region> {
    protected EventHandler<ActionEvent> onExitEvent;
    protected Region SceneRegion;

    public SceneBuilder() { this.build(); }

    public SceneBuilder(EventHandler<ActionEvent> event) {
        this();
        this.onExitEvent = event;
        this.build();
    }

    public Region getSceneRegion() { return SceneRegion; }

    /**
     * Method for constructing the scene region
     * @return The Region representing the final constructed scene.
     */
    public abstract Region buildScene();

    /**
     * Calls buildScene and updates the stored region.
     * Should NOT be overridden. The actual scene construction method is buildScene.
     * @return The Region representing the final constructed scene.
     */
    @Override
    public Region build() {
        return SceneRegion = buildScene();
    }
}
