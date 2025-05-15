package cs112.finalproject.builders;

import cs112.finalproject.SceneUtils;
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
 * Scene changes for the application will be done via this class.
 */
public class ApplicationWrapperBuilder implements Builder<Region> {
    public BorderPane pane; // main scene element, controls scene changes
    private ImageView header; // image shown at top of program
    private Image gamesHeader; // default image when on the main menu
    private Label footer; // text shown at bottom of program
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

        footer = SceneUtils.newLabel(SceneUtils.MAIN_FOOTER);
        SceneUtils.bindSizePref(footer, footerWrapper, 0, 0);
        footer.textAlignmentProperty().setValue(TextAlignment.CENTER);
        footerWrapper.getChildren().add(footer);

        pane.setTop(headerWrapper);
        pane.setBottom(footerWrapper);
        return pane;
    }

    public Image getGamesLogo() { return gamesHeader; }

    /**
     * Sets the application's header to the given image.
     * @param image The image to display at the top of the program.
     */
    public void setHeader(Image image) { header.setImage(image); }

    /**
     * Sets the text displayed at the bottom of the application.
     * @param text The text to display in the footer.
     */
    public void setFooter(String text) { footer.setText(text); }

    /**
     * Changes the element/scene currently displayed by the program.
     * @param node The scene to switch to.
     */
    public void setCenter(Node node) {
        pane.setCenter(node);
    }
}
