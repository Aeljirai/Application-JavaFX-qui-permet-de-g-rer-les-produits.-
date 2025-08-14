# Gestion des Produits — JavaFX

Application JavaFX simple avec formulaire + ListView, contrôleur, FXML et CSS.

## Lancer avec Maven
```bash
mvn clean javafx:run
```

## Structure
- `Product` (POJO: name, price, toString)
- `product-view.fxml` (formulaire + liste)
- `ProductController` (actions: ajouter, modifier, supprimer, vider)
- `styles.css` (styles de base)
- `MainApp` (classe `Application` avec `start`)
