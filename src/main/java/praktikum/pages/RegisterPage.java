package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    protected final By registerPageHeader = By.xpath(".//h2[text() = 'Регистрация']");
    protected final By nameField = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']/fieldset[1]//input");
    protected final By emailField = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']/fieldset[2]//input");
    protected final By passwordField = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']/fieldset[3]//input");
    protected final By registerButton = By.xpath(".//button[text() = 'Зарегистрироваться']");
    protected final By loginButton = By.linkText("Войти");
    protected final By errorTextIncorrectPassword = By.xpath(".//p[@class='input__error text_type_main-default']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу Регистрации https://stellarburgers.nomoreparties.site/register")
    public void openRegisterPage() {
        driver.get(EnvConfig.REGISTER_URL);
    }

    @Step("Дождаться загрузки Заголовка Регистрации")
    public void waitRegisterPageHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(registerPageHeader));
    }

    @Step("Ввод логина, пароля и клик по кнопке 'Зарегистрироваться' открывает страницу Входа")
    public LoginPage regIn(String name,String email, String password) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registerButton).click();
        return new LoginPage (driver);
    }
    @Step("Клик кнопки Войти")
    public LoginPage clickOnLoginButton() {
        driver.findElement(loginButton).click();
        return new LoginPage(driver);
    }

    @Step("Получение текста ошибки ввода пароля")
    public String getErrorTextIncorrectPassword() {
        return driver.findElement(errorTextIncorrectPassword).getText();
    }
}
