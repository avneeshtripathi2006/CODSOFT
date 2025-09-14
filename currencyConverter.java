import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class currencyConverter {
    private static final String API_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency (e.g., usd): ");
        String base = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter target currency (e.g., eur): ");
        String target = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        try {
            double rate = fetchExchangeRate(base, target);
            if (rate > 0) {
                double converted = amount * rate;
                System.out.printf("%.2f %s = %.2f %s\n", amount, base.toUpperCase(), converted, target.toUpperCase());
            } else {
                System.out.println("Could not fetch exchange rate.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    private static double fetchExchangeRate(String base, String target) throws Exception {
        String urlStr = API_URL + base + ".json";
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

        if (!json.has(base)) {
            throw new RuntimeException("Invalid base currency: " + base);
        }

        JSONObject rates = json.getJSONObject(base);

        if (!rates.has(target)) {
            throw new RuntimeException("Invalid target currency: " + target);
        }

        return rates.getDouble(target);
    }
}

/*
 * Add the JSON Library (org.json)
 * Java does not include a JSON parser by default. We’ll need org.json (also
 * called json.jar).
 * 
 * ➤ Method 1: Download and Add Manually (Easiest in Codespaces)
 * Run this in the terminal inside your Codespace:
 * 
 * bash
 * Copy
 * Edit
 * curl -O
 * https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar
 * Now you have the jar in your workspace.
 * 
 * ✅ 4. Compile and Run the Java File
 * Use this command in the terminal to compile:
 * 
 * bash
 * Copy
 * Edit
 * javac -cp .:json-20210307.jar currencyConverter.java
 * Then run it:
 * 
 * bash
 * Copy
 * Edit
 * java -cp .:json-20210307.jar currencyConverter
 * ✅ Note for Windows users: If you're on Windows or using the Windows shell in
 * Codespaces, replace : with ; in the classpath.
 */