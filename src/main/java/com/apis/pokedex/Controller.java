package com.apis.pokedex;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {

    private PokeApp mainApp;
    private String currentFxml;

    @FXML
    private TextField pokemonInput;
    @FXML
    private TextField pokemonNameInput;
    @FXML
    private Text pokemonText;
    @FXML
    private ListView<String> listView;
    @FXML
    private ImageView pokemonImage1;
    @FXML
    private Button searchType;
    @FXML
    private Button searchName;
    @FXML
    private Button showDetails;
    //Initializes the Controller with the main application and the FXML file.
    public void initData(PokeApp mainApp, String fxml) {
        this.mainApp = mainApp;
        this.currentFxml = fxml;
        initialize();
    }

    @FXML
    public void initialize() {
        if (currentFxml != null) {
            // Set actions for the buttons
            searchType.setOnAction(this::searchPokemons);
            searchName.setOnAction(this::searchPokemonByName);
            showDetails.setText("Show Details");
            // Set action for showDetails button
            showDetails.setOnAction(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String[] parts = selectedItem.split(" \\(ID: ");
                    String name = parts[0];
                    String id = parts[1].replace(")", "");
                    showDetails(event, name, id); // call showDetails function and send name and id to the new scene logic
                }
            });
            // Set action for mouse click on listView to pre-visualize pokemon
            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String[] parts = selectedItem.split(" \\(ID: ");
                    if (parts.length > 1) {      // addresses error if user clicks on error message
                        String pokeId = parts[1].replace(")", "");
                        showPokemonImage(pokeId);
                    } else {
                        System.out.println("Selected item format is incorrect: " + selectedItem);
                    }
                }

            });
        }
    }
    //Searches for Pokemons based on type
    @FXML
    public void searchPokemons(ActionEvent event) {
        String type = pokemonInput.getText().toLowerCase();
        String url = "https://pokeapi.co/api/v2/type/" + type;
        if (type.isEmpty()) {
            listView.getItems().clear();
            listView.getItems().add("Enter a Type into the space");
            return;
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                PokemonTypeResponse pokemonTypeResponse = parseTypeResponse(jsonResponse);

                listView.getItems().clear();
                for (PokemonEntry entry : pokemonTypeResponse.getPokemon()) {
                    String name = entry.getPokemon().getName();
                    String id = extractPokemonIdFromUrl(entry.getPokemon().getUrl());
                    listView.getItems().add(name + " (ID: " + id + ")");
                }

            } else {
                listView.getItems().clear();
                listView.getItems().add("Pokemon type not found, you can try with:\n" + // show valid types to the user
                        "Normal\n" +
                        "Fighting \n" +
                        "Flying \n" +
                        "Poison \n" +
                        "Ground \n" +
                        "Rock \n" +
                        "Bug \n" +
                        "Ghost \n" +
                        "Steel \n" +
                        "Fire \n" +
                        "Water\n" +
                        "Grass \n" +
                        "Electric \n" +
                        "Psychic \n" +
                        "Ice \n" +
                        "Dragon \n" +
                        "Dark \n" +
                        "Fairy");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    //Searches for Pokemons based on name
    @FXML
    public void searchPokemonByName(ActionEvent event) {
        String name = pokemonNameInput.getText().toLowerCase();
        String url = "https://pokeapi.co/api/v2/pokemon/" + name;
        if (name.isEmpty()) {
            listView.getItems().clear();
            listView.getItems().add("Enter a Pokemon");
            return;
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                Pokemon pokemon = parsePokemonResponse(jsonResponse);
                listView.getItems().clear();
                listView.getItems().add(pokemon.getName() + " (ID: " + pokemon.getId() + ")");
            } else {
                listView.getItems().clear();
                listView.getItems().add("Pokemon not found");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private String extractPokemonIdFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private PokemonTypeResponse parseTypeResponse(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, PokemonTypeResponse.class);
    }

    private Pokemon parsePokemonResponse(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, Pokemon.class);
    }
    //pre-visualization of pokemon
    private void showPokemonImage(String pokeId) {
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + pokeId + ".png";
        try {
            Image image = new Image(imageUrl);
            pokemonImage1.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            pokemonImage1.setImage(null);
        }
    }
    //Shows details of a selected Pokemon on a new scene.
    @FXML
    public void showDetails(ActionEvent event, String name, String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("details.fxml"));
            Parent root = loader.load();

            DetailsController detailsController = loader.getController();
            detailsController.initData(mainApp, name, id);

            mainApp.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
