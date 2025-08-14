package com.example.products;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ProductController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ListView<Product> productList;
    @FXML private Label statusLabel;

    private final ObservableList<Product> items = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productList.setItems(items);
        productList.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                nameField.setText(sel.getName());
                priceField.setText(String.valueOf(sel.getPrice()));
                status("Produit sélectionné : " + sel.getName());
            }
        });
        // Enter key adds product from price field for quick input
        priceField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onAdd(null);
                e.consume();
            }
        });
    }

    @FXML
    public void onAdd(ActionEvent e) {
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty()) { warn("Le nom est obligatoire."); return; }
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) { warn("Le prix doit être >= 0."); return; }
        } catch (NumberFormatException ex) {
            warn("Prix invalide.");
            return;
        }

        items.add(new Product(name, price));
        productList.getSelectionModel().selectLast();
        clearFields(false);
        status("Produit ajouté.");
    }

    @FXML
    public void onUpdate(ActionEvent e) {
        Product sel = productList.getSelectionModel().getSelectedItem();
        if (sel == null) { warn("Sélectionnez un produit à modifier."); return; }

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        if (name.isEmpty()) { warn("Le nom est obligatoire."); return; }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) { warn("Le prix doit être >= 0."); return; }
        } catch (NumberFormatException ex) {
            warn("Prix invalide.");
            return;
        }

        sel.setName(name);
        sel.setPrice(price);
        // Refresh ListView cell text
        productList.refresh();
        status("Produit mis à jour.");
    }

    @FXML
    public void onDelete(ActionEvent e) {
        int idx = productList.getSelectionModel().getSelectedIndex();
        if (idx < 0) { warn("Sélectionnez un produit à supprimer."); return; }
        items.remove(idx);
        clearFields(true);
        status("Produit supprimé.");
    }

    @FXML
    public void onClear(ActionEvent e) {
        clearFields(true);
        status("Formulaire vidé.");
    }

    private void clearFields(boolean clearSelection) {
        nameField.clear();
        priceField.clear();
        if (clearSelection) productList.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    private void status(String msg) {
        statusLabel.setText(msg);
    }

    private void warn(String msg) {
        statusLabel.setText(msg);
        // Optional: visual alert
        // new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
