package carlook.test;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@ExtendWith(SeleniumExtension.class)
public class LoginViewTest {

    private WebDriver driver;

    public LoginViewTest() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void loginTestKunde() throws InterruptedException {

        //Aufruf der Seite
        driver.get("http://localhost:8080/#!Startseite");
        //Maximieren
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        // wait for the new li to appear
        WebElement btnLogin = wait
                .until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));
        btnLogin.click();

        WebElement fldEmail = wait.until(ExpectedConditions.elementToBeClickable(By.id("fldEmail")));
        fldEmail.sendKeys("leon@web.de");

        WebElement fldPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("fldPassword")));
        fldPassword.sendKeys("passwort");

        WebElement btnLoginLoginPage = driver.findElement(By.id("btnLogin"));
        btnLoginLoginPage.click();

        WebElement btnAutosuche = wait
                .until(ExpectedConditions.elementToBeClickable(By.id("btnAutosuche")));
        Assert.assertTrue(btnAutosuche.getText().contains("Autos suchen"));

        driver.quit();
    }

}