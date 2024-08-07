module com.apis.pokedesk {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires java.net.http;


    opens com.apis.pokedex to javafx.fxml, com.google.gson;
    exports com.apis.pokedex;
}

