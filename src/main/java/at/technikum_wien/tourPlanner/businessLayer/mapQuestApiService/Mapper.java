package at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Mapper {
    //TODO: potentially rename class

    private final ObjectMapper objectMapper;

    public Mapper() {
        this.objectMapper = new ObjectMapper();
    }

    // parser json to Object for responses
    protected <T> T toObject(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        T object = null;
        try {
            object = mapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return object;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
