package codenames.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeckFactory {

    private List<ThemedDeck> allThemedDeck;

    public DeckFactory(){}


    private class Tmp {
        public Card card;
        public List<String> hints;

        public Tmp(Card card,List<String> hints){
            this.card = card;
            this.hints = hints;
        }
    }   

    public DeckSinglePlayer createTextDeckSinglePlayer(int numberOfCard){
        allThemedDeck = TextThemedDeckDB.getInstance().getData();
        return createDeckSinglePlayer(numberOfCard);
    }

    public DeckSinglePlayer createImageDeckSinglePlayer(int numberOfCard){
        allThemedDeck = ImageThemedDeckDB.getInstance().getData();
        return createDeckSinglePlayer(numberOfCard);
    }

    private DeckSinglePlayer createDeckSinglePlayer(int numberOfCard){
        List<PlayableCardWithHints> cards = new ArrayList<>();

        //allThemedDeck.forEach(deck -> System.out.println(deck.getWords()));
    
        allThemedDeck.removeIf( deck -> deck.getCards().size() == 0);

        int totalWords = allThemedDeck.stream()
                              .mapToInt(deck -> deck.getCards().size())
                              .sum();

        if (totalWords >= numberOfCard){

            List<Tmp> allTmps = allThemedDeck.stream()
            .flatMap(deck -> {
                return deck.getCards().stream()
                        .map(card -> new Tmp(card, deck.getHints())); 
            })
            .collect(Collectors.toList());

            Collections.shuffle(allTmps);

            allTmps = allTmps.subList(0, numberOfCard);

            allTmps.forEach(tmp -> {
                cards.add(new PlayableCardWithHints(tmp.card, CardType.White, tmp.hints));
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

    public DeckTwoTeams createTextDeckTwoTeams(int numberOfCard){
        allThemedDeck = TextThemedDeckDB.getInstance().getData();
        return createDeckTwoTeams(numberOfCard);
    }

    public DeckTwoTeams createImageDeckTwoTeams(int numberOfCard){
        allThemedDeck = ImageThemedDeckDB.getInstance().getData();
        return createDeckTwoTeams(numberOfCard);
    }

    private DeckTwoTeams createDeckTwoTeams(int numberOfCard) /*throws Exception*/{
        List<PlayableCard> cards = new ArrayList<>();
    
        allThemedDeck.removeIf( deck -> deck.getCards().size() == 0);

        int totalWords = allThemedDeck.stream()
                              .mapToInt(deck -> deck.getCards().size())
                              .sum();

        //if (totalWords < numberOfCard) throw new Exception("Nb de carte insuffisant");

        if (totalWords >= numberOfCard){


            List<Card> allWords = allThemedDeck.stream()
                                         .flatMap(deck -> deck.getCards().stream())
                                         .collect(Collectors.toList());
                            
            Collections.shuffle(allWords);

            allWords = allWords.subList(0, numberOfCard);

            allWords.forEach(card -> cards.add(new PlayableCard(card, CardType.White)));

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
