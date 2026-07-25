package calculator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class NbpApiService {

    public record CurrencyRate(String code, String name, double mid, double bid, double ask) {}

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<CurrencyRate> fetchRates() {
        try {
            Map<String, Double> tableA = fetchTable("http://api.nbp.pl/api/exchangerates/tables/A/?format=json", "mid");
            Map<String, Double> bids = fetchTable("http://api.nbp.pl/api/exchangerates/tables/C/?format=json", "bid");
            Map<String, Double> asks = fetchTable("http://api.nbp.pl/api/exchangerates/tables/C/?format=json", "ask");

            List<CurrencyRate> list = new ArrayList<>();
            list.add(new CurrencyRate("PLN", "Złoty polski", 1.0, 1.0, 1.0));

            for (String code : tableA.keySet()) {
                double mid = tableA.get(code);
                double bid = bids.getOrDefault(code, mid * 0.98);
                double ask = asks.getOrDefault(code, mid * 1.02);
                list.add(new CurrencyRate(code, code, mid, bid, ask));
            }

            list.sort(Comparator.comparing(CurrencyRate::code));
            return list;
        } catch (Exception e) {
            return List.of(new CurrencyRate("PLN", "Złoty polski", 1.0, 1.0, 1.0));
        }
    }

    private Map<String, Double> fetchTable(String url, String field) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, Double> map = new HashMap<>();
        JsonNode rates = mapper.readTree(response.body()).get(0).get("rates");

        for (JsonNode node : rates) {
            map.put(node.get("code").asText(), node.get(field).asDouble());
        }
        return map;
    }
}