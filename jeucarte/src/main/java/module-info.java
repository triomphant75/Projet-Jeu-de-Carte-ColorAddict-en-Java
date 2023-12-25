module idmc.fr {
    requires javafx.controls;
    requires javafx.fxml;

    opens idmc.fr.Controller to javafx.fxml;
    exports idmc.fr;
}
