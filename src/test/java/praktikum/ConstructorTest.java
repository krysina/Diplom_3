package praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Rule
    public DriverRule factory = new DriverRule();

    @Before
    public void startUp() {
        driver = factory.getDriver();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
            driver.quit();
    }

    @Test
    @DisplayName("Проверка работы переходов к разделам Конструктора")
    public void switchTabsTest() {
        mainPage.openMainPage();
        mainPage.waitMainPageHeader();
        mainPage.switchSauceTab();
        mainPage.switchBunTab();
        mainPage.switchFillingTab();
    }

}
