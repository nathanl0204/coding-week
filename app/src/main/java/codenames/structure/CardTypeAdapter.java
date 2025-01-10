package codenames.structure;

import com.google.gson.*;
import java.lang.reflect.Type;

public class CardTypeAdapter implements JsonSerializer<Card>, JsonDeserializer<Card> {
    private static final String TYPE_KEY = "type";
    private static final String DATA_KEY = "data";
    private static final String TEXT_KEY = "text";
    private static final String URL_KEY = "url";

    @Override
    public JsonElement serialize(Card card, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        JsonObject data = new JsonObject();

        if (card instanceof TextCard) {
            json.addProperty(TYPE_KEY, "TextCard");
            data.addProperty(TEXT_KEY, ((TextCard) card).getText());
        } else if (card instanceof ImageCard) {
            json.addProperty(TYPE_KEY, "ImageCard");
            data.addProperty(URL_KEY, ((ImageCard) card).getUrl());
        }

        json.add(DATA_KEY, data);
        return json;
    }

    @Override
    public Card deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Expected JsonObject");
        }

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has(TEXT_KEY)) {
            return new TextCard(jsonObject.get(TEXT_KEY).getAsString());
        } else if (jsonObject.has(URL_KEY)) {
            return new ImageCard(jsonObject.get(URL_KEY).getAsString());
        }

        if (!jsonObject.has(TYPE_KEY) || !jsonObject.has(DATA_KEY)) {
            throw new JsonParseException("Invalid card format: missing type or data");
        }

        String type = jsonObject.get(TYPE_KEY).getAsString();
        JsonObject data = jsonObject.get(DATA_KEY).getAsJsonObject();

        switch (type) {
            case "TextCard":
                if (!data.has(TEXT_KEY)) {
                    throw new JsonParseException("Invalid TextCard format: missing text");
                }
                return new TextCard(data.get(TEXT_KEY).getAsString());
            case "ImageCard":
                if (!data.has(URL_KEY)) {
                    throw new JsonParseException("Invalid ImageCard format: missing url");
                }
                return new ImageCard(data.get(URL_KEY).getAsString());
            default:
                throw new JsonParseException("Unknown card type: " + type);
        }
    }
}