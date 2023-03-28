package api.solution.sportradar.client;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class WebClient {

    @Value("${base.url}")
    private transient String basePath;

    @Value("${path.all.sports}")
    private transient String allSports;

    @Value("${path.all.tournaments}")
    private transient String allTournaments;

    @Value("${path.all.match}")
    private transient String allMatches;

    public JsonArray getAllSportsJson() throws IOException {
        return makeRequest(allSports);
    }

    public JsonArray getAllMatchesJson() throws IOException {
        return makeRequest(allMatches);
    }

    public JsonArray getAllTournamentsJson() throws IOException {
        return makeRequest(allTournaments);
    }

    private JsonArray makeRequest(String path) throws IOException {
        URL url = new URL(basePath + path);


        try(InputStream is = url.openStream();
            JsonReader reader = Json.createReader(is)) {

            return reader.readArray();

        }


    }

}
