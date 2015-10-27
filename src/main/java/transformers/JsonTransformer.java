package transformers;

import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.Map;

public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public Map toJson (String json){
        return new Gson().fromJson(json, Map.class);
    }
}
