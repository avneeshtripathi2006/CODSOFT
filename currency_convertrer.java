import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class currency_convertrer {
    // Replace with your API key from https://exchangerate-api.com or similar
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency (e.g., USD): ");
        String base = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter target currency (e.g., EUR): ");
        String target = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        try {
            double rate = fetchExchangeRate(base, target);
            if (rate > 0) {
                double converted = amount * rate;
                System.out.printf("%.2f %s = %.2f %s\n", amount, base, converted, target);
            } else {
                System.out.println("Could not fetch exchange rate.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        scanner.close();
    }

    private static double fetchExchangeRate(String base, String target) throws Exception {
        String urlStr = API_URL + base;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("HTTP error code: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        if (!json.getString("result").equals("success")) {
            throw new RuntimeException("API error: " + json.getString("error-type"));
        }
        JSONObject rates = json.getJSONObject("conversion_rates");
        return rates.getDouble(target);
    }
}