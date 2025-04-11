package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;
import java.time.Duration;

public class MainPage {

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;

    }

    protected final By accountBtn = By.xpath(".//p[text()='Личный Кабинет']");
    protected final By btnLogin = By.xpath(".//button[text()='Войти в аккаунт']");
    protected final By btnCreateOrder = By.xpath(".//div[@class='BurgerConstructor_basket__container__2fUl3 mt-10']/button");
    protected final By mainPageHeader = By.xpath(".//a[contains(@class, 'AppHeader_header__link_active__1IkJo')]");
    protected final By bunTab = By.cssSelector(".tab_tab__1SPyG:nth-child(1)");
    protected final By sauceTab = By.cssSelector(".tab_tab__1SPyG:nth-child(2)");
    protected final By fillingTab = By.cssSelector(".tab_tab__1SPyG:nth-child(3)");

    @Step ("Открыть главную страницу https://stellarburgers.nomoreparties.site")
    public void openMainPage() {
        driver.get(EnvConfig.BASE_URL);
    }

    @Step ("Дождаться загрузки в header Конструктора")
    public void waitMainPageHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPageHeader));
    }


    @Step ("Получить название кнопки 'Оформить заказ'")
    public String getTextButtonCreateOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(btnCreateOrder));
        return driver.findElement(btnCreateOrder).getText();
    }


    @Step ("Клик по кнопке 'Личный кабинет' открывает страницу Входа https://stellarburgers.nomoreparties.site/login")
    public void clickAccountBtn() {
        driver.findElement(accountBtn).click();
    }

    @Step ("Клик по кнопке 'Войти в аккаунт' открывает страницу Входа https://stellarburgers.nomoreparties.site/login")
    public LoginPage clickBtnLogin() {
        driver.findElement(btnLogin).click();
        return new LoginPage(driver);
    }


    @Step ("Успешный переход в раздел Булки")
    public void switchBunTab() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bunTab));
        driver.findElement(bunTab).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(bunTab, "class", "current"));
    }

    @Step ("Успешный переход в раздел Соусы")
    public void switchSauceTab() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(sauceTab));
        driver.findElement(sauceTab).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(sauceTab, "class", "current"));
    }

    @Step ("Успешный переход в раздел Начинки")
    public void switchFillingTab() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(fillingTab));
        driver.findElement(fillingTab).click();
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.attributeContains(fillingTab, "class", "current"));
    }


}
