package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage extends AbstractBasePage{

    @Value("${application.url}")
    private String url;

    @FindBy(how = How.CSS, using = "H1")
    private WebElement headerText;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void loadHomePage() {
        goToUrl(url);
        assertEquals("Welcome to Shady Meadows B&B", readText(headerText), "Not on home page");
    }
}
