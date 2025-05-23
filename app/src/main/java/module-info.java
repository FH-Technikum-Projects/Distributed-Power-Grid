module at.dists.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires java.net.http;
    requires org.json;
    requires com.fasterxml.jackson.databind;

    opens at.dists.app to javafx.fxml;
    exports at.dists.app;
}