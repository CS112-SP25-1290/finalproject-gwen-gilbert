package cs112.finalproject;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

/**
 * StageBuilder for a Region that will wrap the entire Scene.
 * Changing to different Regions is done via this class.
 */
public class ApplicationWrapperBuilder implements Builder<Region> {
    public BorderPane pane;
    private ImageView header;

    @Override
    public Region build() {
        pane = new BorderPane();
        pane.setPrefWidth(960);
        pane.setPrefHeight(720);
        header = new ImageView();
        header.setFitWidth(960);
        header.setFitHeight(120);

        pane.setTop(header);
        return pane;
    }

    public void setHeader(Image image) { header.setImage(image); }
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
