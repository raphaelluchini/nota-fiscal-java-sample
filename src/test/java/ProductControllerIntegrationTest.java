import com.devsolutions.Main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.devsolutions.database.MySQLAdapter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

import static org.junit.Assert.*;

public class ProductControllerIntegrationTest {
    private static int PORT = 4567;
    private static String DB_TEST_NAME = "test_notafiscal";

    @BeforeClass
    public static void beforeClass() {
        MySQLAdapter mySQLAdapter = new MySQLAdapter(DB_TEST_NAME);
        Main.main(mySQLAdapter);
        Spark.awaitInitialization();
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void shouldHaveHome() {
        UrlResponse response = doMethod("GET", "/products", null);

        String body = response.body.trim();
        assertNotNull(body);
        assertEquals(200, response.status);
    }

    @Test
    public void shouldHaveNew() {
        UrlResponse response = doMethod("GET", "/products/new", null);

        String body = response.body.trim();
        assertNotNull(body);
        assertEquals(200, response.status);
    }


    private static UrlResponse doMethod(String requestMethod, String path, String body) {
        UrlResponse response = new UrlResponse();

        try {
            getResponse(requestMethod, path, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static void getResponse(String requestMethod, String path, UrlResponse response)
            throws MalformedURLException, IOException, ProtocolException {
        try {
            URL url = new URL("http://localhost:" + PORT + path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.connect();
            String res = IOUtils.toString(connection.getInputStream());
            response.body = res;
            response.status = connection.getResponseCode();
            response.headers = connection.getHeaderFields();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());

        }
    }

    private static class UrlResponse {
        public Map<String, List<String>> headers;
        private String body;
        private int status;
    }
}
