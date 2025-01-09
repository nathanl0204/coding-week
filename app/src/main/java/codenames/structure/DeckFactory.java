package codenames.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeckFactory {

    public DeckFactory(){}


    private class Tmp {
        public String word;
        public List<String> hints;

        public Tmp(String word,List<String> hints){
            this.word = word;
            this.hints = hints;
        }
    }   

    public DeckSinglePlayer createTextDeckSinglePlayer(int numberOfCard){
        List<PlayableCardWithHints> cards = new ArrayList<>();

        List<ThemedDeck> allThemedDeck = ThemedDeckDB.getInstance().getData();

        //allThemedDeck.forEach(deck -> System.out.println(deck.getWords()));
    
        allThemedDeck.removeIf( deck -> deck.getWords().size() == 0);

        int totalWords = allThemedDeck.stream()
                              .mapToInt(deck -> deck.getWords().size())
                              .sum();

        if (totalWords >= numberOfCard){

            List<Tmp> allTmps = allThemedDeck.stream()
            .flatMap(deck -> {
                return deck.getWords().stream()
                        .map(word -> new Tmp(word, deck.getHints())); 
            })
            .collect(Collectors.toList());

            Collections.shuffle(allTmps);

            allTmps = allTmps.subList(0, numberOfCard);

            allTmps.forEach(tmp -> {
                cards.add(new PlayableCardWithHints(new TextCard(tmp.word), CardType.White, tmp.hints));
            });

            for (int i = 0; i < cards.size(); i++) {
                PlayableCard card = cards.get(i);
                if (i == 0) {
                    card.setCardType(CardType.Black);
                } else if (i < numberOfCard*0.3) {
                    card.setCardType(CardType.Blue);
                } else if (i < numberOfCard*0.6) {
                    card.setCardType(CardType.Red);
                }
            }
                                   
        }

        Collections.shuffle(cards);

        return new DeckSinglePlayer(cards);
    }

    public DeckTwoTeams createTextDeckTwoTeams(int numberOfCard) /*throws Exception*/{
        List<PlayableCard> cards = new ArrayList<>();


        List<ThemedDeck> allThemedDeck = ThemedDeckDB.getInstance().getData();

        allThemedDeck.forEach(deck -> System.out.println(deck.getWords()));
    
        allThemedDeck.removeIf( deck -> deck.getWords().size() == 0);

        //if (allThemedDeck.size() == 0) throw new Exception("aucune carte dispo");

        int totalWords = allThemedDeck.stream()
                              .mapToInt(deck -> deck.getWords().size())
                              .sum();

        //if (totalWords < numberOfCard) throw new Exception("Nb de carte insuffisant");

        if (totalWords >= numberOfCard){


            List<String> allWords = allThemedDeck.stream()
                                         .flatMap(deck -> deck.getWords().stream())
                                         .collect(Collectors.toList());
                            
            Collections.shuffle(allWords);

            allWords = allWords.subList(0, numberOfCard);

            allWords.forEach(word -> cards.add(new PlayableCard(new TextCard(word), CardType.White)));

            for (int i = 0; i < cards.size(); i++) {
                PlayableCard card = cards.get(i);
                if (i == 0) {
                    card.setCardType(CardType.Black);
                } else if (i < numberOfCard*0.3) {
                    card.setCardType(CardType.Blue);
                } else if (i < numberOfCard*0.6) {
                    card.setCardType(CardType.Red);
                }
            }
        }

        

        Collections.shuffle(cards);

        return new DeckTwoTeams(cards);
    }
}
