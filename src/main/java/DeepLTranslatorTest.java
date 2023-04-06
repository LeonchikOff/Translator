import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class DeepLTranslatorTest {

    public static void main(String[] args) {

        String query = "In his turn, 402 went through the hatchway, climbed down a long stairway, and found himself on solid ground. He was standing in an open, sunlit square. Guards were forming the disembarked prisoners into files; on all sides, 402 could see a crowd of spectators watching.\n" +
                       "\n" +
                       "A loudspeaker voice boomed, \"Answer when your number is called. Your identity will now be revealed to you. Answer promptly when your number is called.\"";
        translate(query);
    }

    private static void translate(String query) {
        String baseUrl = "https://www.deepl.com/translator#en/ru/";
        String encodedQuery = encodeValue(query);
        String completeUrl = baseUrl + encodedQuery;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(completeUrl);
        By targetTextBy = By.id("target-dummydiv");
        WebDriverWait waitText = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitText.until(webDriver -> !webDriver.findElement(targetTextBy).getAttribute("innerHTML").trim().isEmpty());
        String result = driver.findElement(targetTextBy).getAttribute("innerHTML");
        System.out.println(result);
        driver.quit();
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString()).replace("+", "%20") + "%20";
//            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
