module cs112.finalproject.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens cs112.finalproject to javafx.fxml;
    exports cs112.finalproject;
    exports cs112.finalproject.builders;
    opens cs112.finalproject.builders to javafx.fxml;
}