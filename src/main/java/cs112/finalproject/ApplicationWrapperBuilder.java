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
    private Image gamesHeader;
    private Label footer;
    @Override
    public Region build() {
        pane = new BorderPane();
        pane.setPrefWidth(960);
        pane.setPrefHeight(720);

        StackPane headerWrapper = new StackPane();
        headerWrapper.setStyle("-fx-background-color:lightgrey");
        SceneUtils.bindSize(headerWrapper, pane, 0, 9);

        header = SceneUtils.newImageView(gamesHeader = new Image(SceneUtils.GAMES_LOGO_PATH));
        header.fitHeightProperty().bind(headerWrapper.prefHeightProperty());
        headerWrapper.getChildren().add(header);

        StackPane footerWrapper = new StackPane();
        SceneUtils.bindSize(footerWrapper, pane, 0, 12);

        footer = SceneUtils.newLabel("Footer");
        SceneUtils.bindSizePref(footer, footerWrapper, 0, 0);
        footer.textAlignmentProperty().setValue(TextAlignment.CENTER);
        footerWrapper.getChildren().add(footer);

        pane.setTop(headerWrapper);
        pane.setBottom(footerWrapper);
        return pane;
    }

    public Image getGamesLogo() { return gamesHeader; }
    public void setHeader(Image image) { header.setImage(image); }
    public void setFooter(String text) { footer.setText(text); }
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
