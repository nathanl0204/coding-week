package codenames.structure;

import com.google.gson.*;
import java.lang.reflect.Type;

public class PlayableCardAdapter implements JsonSerializer<PlayableCard>, JsonDeserializer<PlayableCard> {
    private static final String CARD_KEY = "card";
    private static final String CARD_TYPE_KEY = "cardType";
    private static final String GUESSED_KEY = "guessed";

    @Override
    public JsonElement serialize(PlayableCard playableCard, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add(CARD_KEY, context.serialize(playableCard.getCard()));
        json.add(CARD_TYPE_KEY, context.serialize(playableCard.getCardType()));
        json.addProperty(GUESSED_KEY, playableCard.isGuessed());
        return json;
    }

    @Override
    public PlayableCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Card card = context.deserialize(jsonObject.get(CARD_KEY), Card.class);
        CardType cardType = context.deserialize(jsonObject.get(CARD_TYPE_KEY), CardType.class);
        boolean guessed = jsonObject.get(GUESSED_KEY).getAsBoolean();

        PlayableCard playableCard = new PlayableCard(card, cardType);
        if (guessed) {
            playableCard.guessed();
        }
        return playableCard;
    }
}