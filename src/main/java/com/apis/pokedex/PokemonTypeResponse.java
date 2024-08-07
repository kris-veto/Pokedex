
package com.apis.pokedex;
import java.util.List;

public class PokemonTypeResponse {
    private List<PokemonEntry> pokemon;
    public List<PokemonEntry> getPokemon() {
        return pokemon;
    }
    public void setPokemon(List<PokemonEntry> pokemon) {
        this.pokemon = pokemon;
    }
}