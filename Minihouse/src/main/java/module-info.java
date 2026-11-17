module com.uie.minihouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    // Autorise JavaFX à accéder aux contrôleurs pour l'injection FXML
    opens com.uie.minihouse.controllers to javafx.fxml;
    // Autorise JavaFX (TableView) à accéder aux propriétés du modèle
    opens com.uie.minihouse.model to javafx.base;
    // Autorise l'accès au package views si nécessaire
    opens com.uie.minihouse.views to javafx.fxml;

    exports com.uie.minihouse;
}
