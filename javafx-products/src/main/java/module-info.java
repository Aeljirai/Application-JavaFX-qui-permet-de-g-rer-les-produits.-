module com.example.products {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.products to javafx.fxml;
    exports com.example.products;
}
