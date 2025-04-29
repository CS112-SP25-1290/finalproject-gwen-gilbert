package cs112.finalproject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Scanner;

public class SceneUtils {
    public static final String CHOMP_LOGO_PATH = "file:./src/main/resources/cs112/finalproject/chompLogo.png";
    public static final String TIC_TAC_TOE_LOGO_PATH = "file:./src/main/resources/cs112/finalproject/ticTacToeLogo.png";

    /**
     * Sets a standard alignment and style for Buttons and Labels.
     * @param labelOrButton The Button or Label to standardise.
     */
    private static void standardise(Labeled labelOrButton) {
        labelOrButton.setAlignment(Pos.CENTER);
        bindFontSize(labelOrButton);
    }

    /**
     *
     * @param node
     */
    public static void bindFontSize(Node node) {
        node.styleProperty().bind(Bindings.concat("-fx-font-size: ", MainApplication.fontSize));
    }
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
     * @param parent The parent Region that the labelOrButton is being bound to.
     * @param wDiv Integer divisor for labelOrButton's prefWidth. Values less than 2 are ignored.
     * @param hDiv Integer divisor for labelOrButton's prefHeight. Values less than 2 are ignored.
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
     * @param parent The parent Region that the labelOrButton is being bound to.
     * @param wDiv Integer divisor for labelOrButton's prefWidth. Values less than 2 are ignored.
     * @param hDiv Integer divisor for labelOrButton's prefHeight. Values less than 2 are ignored.
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
     * @param parent The parent Region that the labelOrButton is being bound to.
     */
    public static void bindSize(Region sceneElem, Region parent) {
        bindSize(sceneElem, parent, 8, 12);
    }
    /**
     * Shortcut method for calling bindSizePref with a default wDiv and hDiv.
     * @param sceneElem The scene element whose size is being bound.
     * @param parent The parent Region that the labelOrButton is being bound to.
     */
    public static void bindSizePref(Region sceneElem, Region parent) {
        bindSizePref(sceneElem, parent, 8, 8);
    }
}
