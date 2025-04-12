package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class RecoveryPage {

    private final WebDriver driver;
    protected final By loginButton = By.linkText("Войти");
    protected final By recoveryPageHeader = By.xpath(".//h2[text() = 'Восстановление пароля']");

    public RecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу Восстановления пароля https://stellarburgers.nomoreparties.site/forgot-password")
    public void openRecoveryPage() {
        driver.get(EnvConfig.BASE_URL + "forgot-password");
    }

    @Step("Дождаться загрузки Заголовка 'Восстановления пароля'")
    public void waitRecoveryPageHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(recoveryPageHeader));
    }

    public LoginPage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new LoginPage(driver);
    }
}
