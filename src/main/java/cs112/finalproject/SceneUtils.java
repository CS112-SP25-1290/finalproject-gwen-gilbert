package cs112.finalproject;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SceneUtils {
    public static final String GAMES_LOGO_PATH = "file:./src/main/resources/cs112/finalproject/gamesLogo.png";
    public static final String CHOMP_LOGO_PATH = "file:./src/main/resources/cs112/finalproject/chompLogo.png";
    public static final String CHOMP_TILE_FULL = "file:./src/main/resources/cs112/finalproject/chompTile_full.png";
    public static final String CHOMP_TILE_EMPTY = "file:./src/main/resources/cs112/finalproject/chompTile_empty.png";
    public static final String CHOMP_TILE_POISON = "file:./src/main/resources/cs112/finalproject/chompTile_poison.png";
    public static final String TIC_TAC_TOE_LOGO_PATH = "file:./src/main/resources/cs112/finalproject/ticTacToeLogo.png";
    public static final String TIC_TAC_TOE_EMPTY = "file:./src/main/resources/cs112/finalproject/ticTacToe_empty.png";
    public static final String TIC_TAC_TOE_X = "file:./src/main/resources/cs112/finalproject/ticTacToe_x.png";
    public static final String TIC_TAC_TOE_O = "file:./src/main/resources/cs112/finalproject/ticTacToe_o.png";
    public static final String MAIN_FOOTER = "Pick a game to play!";
    public static final String CHOMP_FOOTER = "Take turns taking bites out of a chocolate bar!\nThe top-left piece is poisoned though, so don't eat it or you'll lose!";
    public static final String TIC_TAC_TOE_FOOTER = "Take turns playing X's or O's onto the board!\nFirst to complete a full row wins!";

    /**
     * Sets a standard alignment and style for Buttons and Labels.
     * @param labelOrButton The Button or Label to standardise.
     */
    private static void standardise(Labeled labelOrButton) {
        labelOrButton.setAlignment(Pos.CENTER);
        bindFontSize(labelOrButton);
    }

    /**
     * Binds the font size of the given node to a central standardised size.
     * @param node The node whose font should scale.
     */
    public static void bindFontSize(Node node) {
        node.styleProperty().bind(Bindings.concat("-fx-font-size: ", MainApplication.fontSize));
    }

    /**
     * Creates a VBox objects with spacing 10 and centred alignment.
     * @return The created VBox.
     */
    public static VBox newVBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10.0);
        return vbox;
    }
    /**
     * Creates a new Button object with standardised parameters.
     * @param label The text to display on the button.
     * @param event The action that will trigger when the button is clicked.
     * @return The newly created Button.
     */
    public static Button newButton(String label, EventHandler<ActionEvent> event) {
        Button retval = new Button(label);
        retval.setOnAction(event);
        standardise(retval);
        return retval;
    }
    /**
     * Creates a new Label object with standardised parameters.
     * @param label The text to display.
     * @return The newly created Label.
     */
    public static Label newLabel(String label) {
        Label retval = new Label(label);
        standardise(retval);
        return retval;
    }
    /**
     * Creates a new ImageView object with standardised parameters.
     * @param img The image to display.
     * @return The newly created ImageView.
     */
    public static ImageView newImageView(Image img) {
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setCache(true);
        return view;
    }

    /**
     * Shortcut method for binding the given scene element's preferred size properties.
     * @param sceneElem The scene element whose size is being bound.
     * @param parent The parent Region that the sceneElem is being bound to.
     * @param wDiv Integer divisor for sceneElem's prefWidth. Values less than 2 are ignored.
     * @param hDiv Integer divisor for sceneElem's prefHeight. Values less than 2 are ignored.
     */
    public static void bindSize(Region sceneElem, Region parent, int wDiv, int hDiv) {
        if (wDiv > 1) {
            sceneElem.prefWidthProperty().bind(parent.widthProperty().divide(wDiv));
        }
        if (hDiv > 1) {
            sceneElem.prefHeightProperty().bind(parent.heightProperty().divide(hDiv));
        }
    }
    /**
     * Shortcut method for binding the given scene element's preferred size properties.
     * Binds using the parent's preferred size properties instead of its actual size properties.
     * @param sceneElem The scene element whose size is being bound.
     * @param parent The parent Region that the sceneElem is being bound to.
     * @param wDiv Integer divisor for sceneElem's prefWidth. Values less than 2 are ignored.
     * @param hDiv Integer divisor for sceneElem's prefHeight. Values less than 2 are ignored.
     */
    public static void bindSizePref(Region sceneElem, Region parent, int wDiv, int hDiv) {
        if (wDiv > 0) {
            sceneElem.prefWidthProperty().bind(parent.prefWidthProperty().divide(wDiv));
        }
        if (hDiv > 0) {
            sceneElem.prefHeightProperty().bind(parent.prefHeightProperty().divide(hDiv));
        }
    }
    /**
     * Shortcut method for calling bindSize with a default wDiv and hDiv.
     * @param sceneElem The scene element whose size is being bound.
     * @param parent The parent Region that the sceneElem is being bound to.
     */
    public static void bindSize(Region sceneElem, Region parent) {
        bindSize(sceneElem, parent, 8, 12);
    }
}
