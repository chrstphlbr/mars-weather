package net.laaber.marsweather.shared.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.exc.InvalidFormatException;

public class CustomIntegerDeserializer extends ValueDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctx) throws JacksonException {
        String value = p.getString();

        if (value == null || value.trim().isEmpty() || "--".equals(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw InvalidFormatException.from(
                    p, "Cannot deserialize value %s to Integer".formatted(value), value, Integer.class);
        }
    }

    @Override
    public Integer getNullValue(DeserializationContext ctx) {
        return null;
    }
}
