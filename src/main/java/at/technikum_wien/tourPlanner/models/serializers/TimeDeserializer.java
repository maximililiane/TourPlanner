package at.technikum_wien.tourPlanner.models.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Time;

public class TimeDeserializer extends StdDeserializer<Time> {

    public TimeDeserializer() {
        this(null);
    }

    public TimeDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Time deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String time = node.get("totalTime").asText();
        return Time.valueOf(time);
    }
}
