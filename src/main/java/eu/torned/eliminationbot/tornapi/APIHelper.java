package eu.torned.eliminationbot.tornapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class APIHelper {

    private final ObjectMapper mapper = new ObjectMapper();
    private final OkHttpClient client;
    private String key;

    public APIHelper(String key) {
        this.key = key;

        this.client = new OkHttpClient();
    }

    public <T> T makeRequest(String type, String id, String selections, Class<T> responseType) throws IOException {
        Request request = new Request.Builder()
                .url(String.format("https://api.torn.com/%s/%s?selections=%s&key=%s", type, id, selections, key))
                .build();

        Response response = client.newCall(request).execute();

        return mapper.readValue(response.body().byteStream(), responseType);
    }

    public <T> T makeRequest(String type, String selections, Class<T> responseType) throws IOException {
        return makeRequest(type, "", selections, responseType);
    }

}
