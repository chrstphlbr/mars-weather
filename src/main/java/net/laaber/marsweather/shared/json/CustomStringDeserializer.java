package net.laaber.marsweather.shared.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class CustomStringDeserializer extends ValueDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws JacksonException {
        String value = p.getString();

        if (value == null || value.trim().isEmpty() || "--".equals(value)) {
            return null;
        }

        return value.trim();
    }

    @Override
    public String getNullValue(DeserializationContext ctx) {
        return null;
    }
}
