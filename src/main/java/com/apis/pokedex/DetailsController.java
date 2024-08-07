package com.apis.pokedex;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DetailsController {

    private PokeApp mainApp;

    @FXML
    private Text pokemonText;
    @FXML
    private ImageView pokemonImage2;
    @FXML
    private ImageView pokemonImage3;
    @FXML
    private Text attackText;
    @FXML
    private Text defenseText;
    @FXML
    private Text speedText;
    @FXML
    private Text movesText;
    @FXML
    private Button goBack;

    public void initData(PokeApp mainApp, String pokemonName, String pokemonId) {
        this.mainApp = mainApp;
        pokemonText.setText(pokemonName);
        String urlDetails = "https://pokeapi.co/api/v2/pokemon/" + pokemonId;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlDetails))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                Details details = parseJsonResponse(jsonResponse);

                String attack = getStat(details, "attack");
                String defense = getStat(details, "defense");
                String speed = getStat(details, "speed");
                List<String> moves = getMoves(details);

                attackText.setText(attack);
                defenseText.setText( defense);
                speedText.setText(speed);
                movesText.setText(String.join(",\n", moves));

            } else {
                pokemonText.setText("Error: " + response.statusCode());
            }

            String imageUrl2 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png";
            String imageUrl3 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + pokemonId + ".png";
            Image image2 = new Image(imageUrl2);
            Image image3 = new Image(imageUrl3);
            pokemonImage2.setImage(image2);
            pokemonImage3.setImage(image3);
        } catch (Exception e) {
            e.printStackTrace();
            pokemonImage2.setImage(null);
            pokemonImage3.setImage(null);
        }
        initialize();
    }

    private static Details parseJsonResponse(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, Details.class);
    }

    private String getStat(Details details, String statName) {
        for (Details.Stats stat : details.getStats()) {
            if (stat.getStat().getName().equals(statName)) {
                return String.valueOf(stat.getBaseStat());
            }
        }
        return "N/A";
    }

    private List<String> getMoves(Details details) {
        return Arrays.stream(details.getMoves())
                .limit(7)
                .map(move -> move.getMove().getName())
                .collect(Collectors.toList());
    }

    @FXML
    public void initialize() {
        goBack.setText("Go Back");
        goBack.setOnAction(this::goBack);
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            mainApp.showScene("primary.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
