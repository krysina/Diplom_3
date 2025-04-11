package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    protected final By loginPageHeader = By.xpath(".//h2[text() = 'Вход']");
    protected final By emailField = By.cssSelector("input[name='name']");
    protected final By passwordField = By.cssSelector("input[name='Пароль']");
    protected final By loginButton = By.cssSelector("form button");
    protected final By registerButton = By.linkText("Зарегистрироваться");
    protected final By recoveryPasswordButton = By.linkText("Восстановить пароль");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу Входа https://stellarburgers.nomoreparties.site/login")
    public void openLoginPage() {
        driver.get(EnvConfig.LOGIN_URL);
    }

    @Step ("Получить название кнопки 'Войти'")
    public String getTextLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        return driver.findElement(loginButton).getText();
    }

    //клик по кнопке "Зарегистрироваться" открывает страницу "Регистрация"
    public RegisterPage clickRegisterBtn() {
        driver.findElement(registerButton).click();
        return new RegisterPage (driver);
    }

    //клик по кнопке "Восстановление пароля" открывает страницу "Восстановление пароля"
    public RecoveryPage clickRecoveryPasswordBtn() {
        driver.findElement(recoveryPasswordButton).click();
        return new RecoveryPage (driver);
    }

    @Step("Дождаться загрузки Заголовка Вход")
    public LoginPage waitLoginPageHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPageHeader));
        return this;
    }


    @Step("Ввод логина, пароля и клик по кнопке Войти открывает Главную страницу")
    public MainPage logIn(String newEmail, String newPassword) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));

        driver.findElement(emailField).sendKeys(newEmail);
        driver.findElement(passwordField).sendKeys(newPassword);
        driver.findElement(loginButton).click();
        return new MainPage(driver);
    }
}
