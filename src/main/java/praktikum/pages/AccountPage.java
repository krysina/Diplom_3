package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import praktikum.EnvConfig;

public class AccountProfilePage {

    private final WebDriver driver;
    protected final By userHeader = By.xpath(".//a[text()='Профиль']");
    protected final By emailField = By.cssSelector("input[name='name']");
    protected final By passwordField = By.cssSelector("input[name='Пароль']");
    protected final By loginButton = By.cssSelector("form button");
    protected final By registerButton = By.linkText("Зарегистрироваться");
    protected final By recoveryPasswordButton = By.linkText("Восстановить пароль");

    public AccountProfilePage(WebDriver driver) {
        this.driver = driver;
    }


}
