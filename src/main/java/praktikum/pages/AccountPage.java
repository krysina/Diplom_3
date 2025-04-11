package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class AccountPage {

    private final WebDriver driver;
    protected final By accountPageHeader = By.xpath(".//a[text()='Профиль']");
    protected final By btnExit = By.xpath(".//button[text()='Выход']");
    protected final By logoLink = By.cssSelector(".AppHeader_header__logo__2D0X2");
    protected final By constructorLink = By.xpath(".//nav[@class='AppHeader_header__nav__g5hnF']//p[text()='Конструктор']");
    protected final By saveBtn = By.xpath(".//div[@class='Profile_buttonBox__1JlBI']/button[2]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Дождаться загрузки Заголовка 'Профиль'")
    public void waitAccountPageHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(accountPageHeader));
    }

    @Step("Клик ссылки Конструктор")
    public MainPage clickBuilderLink() {
        driver.findElement(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("Клик кнопки Выйти")
    public LoginPage clickBtnExit() {
        driver.findElement(btnExit).click();
        return new LoginPage(driver);
    }

    @Step("Клик Лого")
    public MainPage clickLogoLink() {
        driver.findElement(logoLink).click();
        return new MainPage(driver);
    }

    @Step ("Получить название кнопки 'Сохранить'")
    public String getTextSaveBtn() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(saveBtn));
        return driver.findElement(saveBtn).getText();
    }

}
