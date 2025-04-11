package praktikum;

import java.net.HttpURLConnection;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import praktikum.pages.*;
import praktikum.user.User;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private RecoveryPage recoveryPage;
    private User user;

    @Rule
    public DriverRule factory = new DriverRule();

    @Before
    public void startUp() {
        driver = factory.getDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        recoveryPage = new RecoveryPage(driver);
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
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginBtnLoginTest() {
        mainPage.openMainPage();
        mainPage.waitMainPageHeader();
        loginPage = mainPage.clickBtnLogin();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void accountBtnLoginTest() {
        mainPage.openMainPage();
        mainPage.waitMainPageHeader();
        mainPage.clickAccountBtn();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void registerPageLoginButtonLoginTest() {
        registerPage.openRegisterPage();
        registerPage.waitRegisterPageHeader();
        loginPage = registerPage.clickOnLoginButton();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void recoveryPageLoginButtonLoginTest() {
        recoveryPage.openRecoveryPage();
        recoveryPage.waitRecoveryPageHeader();
        loginPage = recoveryPage.clickLoginButton();
        loginPage.waitLoginPageHeader();
        mainPage = loginPage.logIn(user.getEmail(),user.getPassword());
        mainPage.waitMainPageHeader();

        String textButtonCteateOrder = mainPage.getTextButtonCreateOrder();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_MAIN_PAGE, textButtonCteateOrder);
    }
}
