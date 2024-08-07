PokeDesk Project

Overview
PokeDesk is a JavaFX application that interacts with the PokeAPI to provide users with information about Pokémon. Users can search for Pokémon by type or name and view details including images, stats, and moves. The project leverages the JavaFX framework for the user interface and HTTP requests to fetch data from the PokeAPI.

Features
Search by Type: Users can search for Pokémon by entering a type (e.g., fire, water, grass) and receive a list of Pokémon of that type.
Search by Name: Users can search for a specific Pokémon by name and get its details.
Pokémon Details: View detailed information about a selected Pokémon, including stats (attack, defense, speed), moves, and images.
Pre-visualization: Preview Pokémon images by clicking on the list items.

Structure
Controller: Handles the main application logic and user interactions.
DetailsController: Manages the details view for a selected Pokémon.

FXML Files: Defines the UI layout and components for the main and details scenes.
Models: Java classes representing the data structure of Pokémon and their details.
Usage

Search by Type:
Enter a Pokémon type in the provided text field.
Click the "Search by Type" button.
The list view will populate with Pokémon of the entered type.

Search by Name:
Enter a Pokémon name in the provided text field.
Click the "Search by Name" button.
The list view will display the searched Pokémon.
View Pokémon Details:

Select a Pokémon from the list view.
Click the "Show Details" button to view detailed information about the selected Pokémon.
Technical Details

HTTP Requests: Utilizes Java's HttpClient for making HTTP requests to the PokeAPI.
JSON Parsing: Uses the Gson library to parse JSON responses from the PokeAPI.
JavaFX: Provides the graphical user interface for the application.

Dependencies
JavaFX SDK
Gson library