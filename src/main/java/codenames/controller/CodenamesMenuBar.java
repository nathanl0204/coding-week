package codenames.controller;

import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;

public class CodenamesMenuBar extends MenuBar{
    public CodenamesMenuBar() {
        // File Menu
        Menu fileMenu = new Menu("Fichier");
        MenuItem newGame = new MenuItem("Nouvelle partie");
        newGame.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        MenuItem loadGame = new MenuItem("Charger une partie");
        loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        MenuItem saveGame = new MenuItem("Sauvegarder la partie");
        saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        MenuItem exit = new MenuItem("Quitter");
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        fileMenu.getItems().addAll(newGame, loadGame, saveGame, new SeparatorMenuItem(), exit);

        // Edit Menu
        Menu editMenu = new Menu("Édition");
        MenuItem editCards = new MenuItem("Éditer les cartes");
        MenuItem editThemes = new MenuItem("Gérer les thèmes");
        MenuItem preferences = new MenuItem("Préférences");
        editMenu.getItems().addAll(editCards, editThemes, new SeparatorMenuItem(), preferences);

        // Game Menu
        Menu gameMenu = new Menu("Jeu");
        Menu gameMode = new Menu("Mode de jeu");
        RadioMenuItem classicMode = new RadioMenuItem("Classique");
        RadioMenuItem duoMode = new RadioMenuItem("Duo");
        RadioMenuItem soloMode = new RadioMenuItem("Solo");
        ToggleGroup modeGroup = new ToggleGroup();
        classicMode.setToggleGroup(modeGroup);
        duoMode.setToggleGroup(modeGroup);
        soloMode.setToggleGroup(modeGroup);
        classicMode.setSelected(true);
        gameMode.getItems().addAll(classicMode, duoMode, soloMode);

        CheckMenuItem timer = new CheckMenuItem("Mode Blitz");
        MenuItem statistics = new MenuItem("Statistiques");
        gameMenu.getItems().addAll(gameMode, timer, new SeparatorMenuItem(), statistics);

        // Help Menu
        Menu helpMenu = new Menu("Aide");
        MenuItem rules = new MenuItem("Règles du jeu");
        MenuItem about = new MenuItem("À propos");
        helpMenu.getItems().addAll(rules, about);

        getMenus().addAll(fileMenu, editMenu, gameMenu, helpMenu);
    }
}
