package cs112.finalproject;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

        StackPane headerWrapper = new StackPane();
        headerWrapper.setStyle("-fx-background-color:black");
        headerWrapper.prefWidthProperty().bind(pane.widthProperty());
        headerWrapper.prefHeightProperty().bind(pane.heightProperty().divide(9));

        header = new ImageView();
        header.setCache(true);
        header.setPreserveRatio(true);
        header.fitHeightProperty().bind(headerWrapper.prefHeightProperty());
        headerWrapper.getChildren().add(header);

        pane.setTop(headerWrapper);
        return pane;
    }

    public void setHeader(Image image) { header.setImage(image); }
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
