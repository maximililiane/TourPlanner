package at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.serializers.TimeDeserializer;
import at.technikum_wien.tourPlanner.models.serializers.TimeSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.sql.Time;

public abstract class Mapper {

    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public Mapper() {
    }

    // format object to json string
    protected String json(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to convert an object into a json-string; Object: " + object + ";\n" + e.getMessage());
        }
        return "";
    }

    // parser json to Object for responses
    protected <T> T toObject(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        // this module is needed for (de-)serializing Time.java for the TourLog totalTime attribute
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerializer());
        module.addDeserializer(Time.class, new TimeDeserializer());
        mapper.registerModule(module);

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        T object = null;
        try {
            object = mapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Json string could not be converted into an object; Json-String: " + json + ";\n" + e.getMessage());

        }

        return object;
    }

}
