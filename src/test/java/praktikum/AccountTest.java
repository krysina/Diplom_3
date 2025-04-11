package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.*;
import praktikum.user.User;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AccountTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private AccountPage accountPage;
    private User user;

    @Rule
    public DriverRule factory = new DriverRule();

    @Before
    public void startUp() {
        driver = factory.getDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        createUser();
    }

    public void createUser() {
        user = User.random();
        given().contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(EnvConfig.REGISTER_END_POINT)
                .then().log().all();
    }

    public void deleteUser() {
        String accessToken = given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .post(EnvConfig.LOGIN_END_POINT)
                .body().jsonPath().getString("accessToken");

        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(EnvConfig.USER_END_POINT)
                .then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED);
    }

    @After
    public void tearDown() {
        driver.quit();
        deleteUser();
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void clickThroughAccountBtnTest() {
        mainPage.openMainPage();
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        accountPage.waitAccountPageHeader();

        String textSaveBtn = accountPage.getTextSaveBtn();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_ACCOUNT_PAGE, textSaveBtn);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void moveFromAccountToBuilderTest() {
        loginPage.openLoginPage();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        accountPage.waitAccountPageHeader();
        mainPage = accountPage.clickBuilderLink();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void moveFromAccountToLogoTest() {
        loginPage.openLoginPage();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        accountPage.waitAccountPageHeader();
        mainPage = accountPage.clickLogoLink();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void exitFromAccountTest() {
        loginPage.openLoginPage();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        accountPage.waitAccountPageHeader();
        loginPage = accountPage.clickBtnExit();

        String textButtonLogIn = loginPage.getTextLoginButton();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_LOGIN_PAGE, textButtonLogIn);
    }
}
