package codenames;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import codenames.controller.*;
import codenames.structure.*;
import codenames.structure.AI.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {

                // ManageWordView manageWordView = new ManageWordView();
                // BorderPane manageWordPane = manageWordView.getGraphic();
                //
                // Scene scene = new Scene(manageWordPane);
                //
                // primaryStage.setScene(scene);
                // primaryStage.setMaximized(true);
                // primaryStage.setTitle("CodeName");
                // primaryStage.show();

                // GameTwoTeams game = testGame();

                GameSinglePlayer game = testSPGame();

                LoadingBarController loadingBarController = new LoadingBarController(200,
                                20);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingBar.fxml"));
                loader.setControllerFactory(iC -> loadingBarController);
                loader.load();

                // GameTwoTeamsController gc = new GameTwoTeamsController(game);

                GameSinglePlayerController gc = new GameSinglePlayerController(game);

                gc.setLoadingBarController(loadingBarController);
                loadingBarController.setGameController(gc);

                EasyAllyAI ai = new EasyAllyAI(gc);
                gc.setAllyAI(ai);

                EasyOpponentAI ai2 = new EasyOpponentAI(gc);
                gc.setOpponentAI(ai2);

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
                loader2.setControllerFactory(iC -> gc);
                BorderPane root = loader2.load();

                FXMLLoader loader3 = new FXMLLoader();
                loader3.setLocation(getClass().getResource("/view/BlueTeam.fxml"));
                TeamView blueTeamView = new TeamView(game, true);
                loader3.setControllerFactory(iC -> blueTeamView);
                root.setLeft(loader3.load());

                FXMLLoader loader4 = new FXMLLoader();
                loader4.setLocation(getClass().getResource("/view/RedTeam.fxml"));
                TeamView redTeamView = new TeamView(game, false);
                loader4.setControllerFactory(iC -> redTeamView);
                root.setRight(loader4.load());

                Scene scene = new Scene(root);

                primaryStage.setScene(scene);
                primaryStage.setTitle("CodeName");
                primaryStage.show();

        }

        private GameTwoTeams testGame() {
                List<PlayableCard> listCard = new ArrayList<PlayableCard>();

                String[] texts = {
                                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Football",
                                "Guitar", "Helicopter", "Igloo", "Jacket", "Kangaroo", "Lemon",
                                "Monkey", "Notebook", "Octopus", "Penguin", "Quilt", "Rocket",
                                "Sunflower", "Tiger", "Umbrella", "Violin", "Whale", "Xylophone",
                                "Yacht"
                };

                CardType[] cardTypes = {
                                CardType.Red, CardType.Red, CardType.Blue, CardType.Black, CardType.White,
                                CardType.Blue,
                                CardType.Blue, CardType.Red, CardType.White, CardType.Red, CardType.Blue,
                                CardType.Black,
                                CardType.White, CardType.Red, CardType.Blue, CardType.Blue, CardType.Red,
                                CardType.White,
                                CardType.Blue, CardType.Red, CardType.Red, CardType.White, CardType.Blue, CardType.Red,
                                CardType.Blue
                };

                int i = 0;

                for (String text : texts) {
                        listCard.add(new PlayableCard(new TextCard(text), cardTypes[i]));
                        i++;
                }

                return new GameTwoTeams(new DeckTwoTeams(listCard), 5, 9, 9);

        }

        private GameSinglePlayer testSPGame() {
                List<PlayableCardWithHints> listCard = new ArrayList<PlayableCardWithHints>();
                String[] texts = {
                                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Football",
                                "Guitar", "Helicopter", "Igloo", "Jacket", "Kangaroo", "Lemon",
                                "Monkey", "Notebook", "Octopus", "Penguin", "Quilt", "Rocket",
                                "Sunflower", "Tiger", "Umbrella", "Violin", "Whale", "Xylophone",
                                "Yacht"
                };

                CardType[] cardTypes = {
                                CardType.Red, CardType.Red, CardType.Blue, CardType.Black, CardType.White,
                                CardType.Blue,
                                CardType.Blue, CardType.Red, CardType.White, CardType.Red, CardType.Blue,
                                CardType.Black,
                                CardType.White, CardType.Red, CardType.Blue, CardType.Blue, CardType.Red,
                                CardType.White,
                                CardType.Blue, CardType.Red, CardType.Red, CardType.White, CardType.Blue, CardType.Red,
                                CardType.Blue
                };

                // Define groups of related hints for different categories
                Map<String, List<String>> wordHints = new HashMap<>();

                // Food related
                wordHints.put("Apple", Arrays.asList("fruit", "food", "red", "sweet"));
                wordHints.put("Banana", Arrays.asList("fruit", "food", "yellow", "sweet"));
                wordHints.put("Cherry", Arrays.asList("fruit", "food", "red", "sweet"));
                wordHints.put("Lemon", Arrays.asList("fruit", "food", "yellow", "sour"));

                // Animals
                wordHints.put("Dog", Arrays.asList("animal", "pet", "mammal", "bark"));
                wordHints.put("Elephant", Arrays.asList("animal", "big", "mammal", "gray"));
                wordHints.put("Kangaroo", Arrays.asList("animal", "jump", "mammal", "australia"));
                wordHints.put("Monkey", Arrays.asList("animal", "primate", "mammal", "banana"));
                wordHints.put("Tiger", Arrays.asList("animal", "cat", "mammal", "stripes"));
                wordHints.put("Whale", Arrays.asList("animal", "ocean", "mammal", "big"));
                wordHints.put("Penguin", Arrays.asList("animal", "bird", "cold", "swim"));
                wordHints.put("Octopus", Arrays.asList("animal", "ocean", "tentacles", "swim"));

                // Music related
                wordHints.put("Guitar", Arrays.asList("music", "instrument", "strings", "play"));
                wordHints.put("Violin", Arrays.asList("music", "instrument", "strings", "orchestra"));
                wordHints.put("Xylophone", Arrays.asList("music", "instrument", "percussion", "play"));

                // Transportation
                wordHints.put("Helicopter", Arrays.asList("vehicle", "fly", "air", "transport"));
                wordHints.put("Yacht", Arrays.asList("vehicle", "water", "boat", "luxury"));
                wordHints.put("Rocket", Arrays.asList("vehicle", "fly", "space", "fast"));

                // Other objects
                wordHints.put("Football", Arrays.asList("sport", "ball", "game", "team"));
                wordHints.put("Igloo", Arrays.asList("house", "cold", "ice", "snow"));
                wordHints.put("Jacket", Arrays.asList("clothes", "wear", "warm", "winter"));
                wordHints.put("Notebook", Arrays.asList("school", "paper", "write", "study"));
                wordHints.put("Quilt", Arrays.asList("bed", "warm", "fabric", "sleep"));
                wordHints.put("Umbrella", Arrays.asList("rain", "weather", "protect", "water"));
                wordHints.put("Sunflower", Arrays.asList("plant", "yellow", "garden", "flower"));

                for (int i = 0; i < texts.length; i++) {
                        String text = texts[i];
                        List<String> hints = wordHints.getOrDefault(text, Arrays.asList("default1", "default2"));
                        listCard.add(new PlayableCardWithHints(
                                        new TextCard(text),
                                        cardTypes[i],
                                        hints));
                }

                GameSinglePlayer game = new GameSinglePlayer(new DeckSinglePlayer(listCard), 5, 9, 9);
                return game;
        }
}
