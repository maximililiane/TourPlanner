package at.technikum_wien.tourPlanner.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Time;

public class TimeSerializer extends StdSerializer<Time> {

    public TimeSerializer() {
        this(null);
    }

    public TimeSerializer(Class<Time> t) {
        super(t);
    }

    @Override
    public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("totalTime", time.toString());
        jsonGenerator.writeEndObject();
    }
}
