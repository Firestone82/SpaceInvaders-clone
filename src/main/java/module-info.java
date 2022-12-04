module lab {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens lab to javafx.fxml;
    opens lab.enums to javafx.fxml;
    opens lab.interfaces to javafx.fxml;
    opens lab.object to javafx.fxml, javafx.base;
    opens lab.object.controller to javafx.fxml;
    opens lab.object.controller.list to javafx.fxml;
    opens lab.object.entity to javafx.fxml;
    opens lab.object.entity.list to javafx.fxml;
    exports lab;
}
