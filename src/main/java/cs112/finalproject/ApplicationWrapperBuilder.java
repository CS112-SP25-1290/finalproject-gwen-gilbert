package cs112.finalproject;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Builder;

/**
 * StageBuilder for a Region that will wrap the entire Scene.
 * Changing to different Regions is done via this class.
 */
public class ApplicationWrapperBuilder implements Builder<Region> {
    public BorderPane pane;
    private ImageView header;
    private Label footer;
    @Override
    public Region build() {
        pane = new BorderPane();
        pane.setPrefWidth(960);
        pane.setPrefHeight(720);

        StackPane headerWrapper = new StackPane();
        headerWrapper.setStyle("-fx-background-color:lightgrey");
        headerWrapper.prefWidthProperty().bind(pane.widthProperty());
        headerWrapper.prefHeightProperty().bind(pane.heightProperty().divide(9));
        header = new ImageView();
        header.setCache(true);
        header.setPreserveRatio(true);
        header.fitHeightProperty().bind(headerWrapper.prefHeightProperty());
        headerWrapper.getChildren().add(header);

        StackPane footerWrapper = new StackPane();
        footerWrapper.prefWidthProperty().bind(pane.widthProperty());
        footerWrapper.prefHeightProperty().bind(pane.heightProperty().divide(9));

        footer = new Label("Footer");
        footer.prefWidthProperty().bind(footerWrapper.prefWidthProperty());
        footer.prefHeightProperty().bind(footerWrapper.prefHeightProperty());
        footer.setAlignment(Pos.CENTER);
        footer.textAlignmentProperty().setValue(TextAlignment.CENTER);
        footerWrapper.getChildren().add(footer);

        pane.setTop(headerWrapper);
        pane.setBottom(footerWrapper);
        return pane;
    }

    public void setHeader(Image image) { header.setImage(image); }
    public void setFooter(String text) { footer.setText(text); }
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
