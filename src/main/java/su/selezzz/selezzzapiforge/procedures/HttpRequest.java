package su.selezzz.selezzzapiforge.procedures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String send(String _url) throws IOException {
        final URL url = new URL(_url);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            final StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}