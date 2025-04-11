package praktikum;

import java.net.HttpURLConnection;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.*;
import praktikum.user.User;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class RegisterTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private User user;

    @Rule
    public DriverRule factory = new DriverRule();

    @Before
    public void startUp() {
        driver = factory.getDriver();
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        user = User.random();
    }

    public void deleteUser() {
        String accessToken = given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .post(EnvConfig.LOGIN_END_POINT)
                .body().jsonPath().getString("accessToken");
        if (accessToken!=null){
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(EnvConfig.USER_END_POINT)
                .then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
        deleteUser();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void correctRegistration() {
        registerPage.openRegisterPage();
        registerPage.waitRegisterPageHeader();
        loginPage = registerPage.regIn(user.getName(), user.getEmail(),user.getPassword());
        loginPage.waitLoginPageHeader();

        String textButtonLogIn = loginPage.getTextLoginButton();
        assertEquals(EnvConfig.EXPECTED_BTN_TEXT_LOGIN_PAGE, textButtonLogIn);
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля. Минимальный пароль — шесть символов")
    public void incorrectRegistration() {
        user.setPassword(EnvConfig.INCORRECT_PASSWORD);
        registerPage.openRegisterPage();
        registerPage.waitRegisterPageHeader();
        loginPage = registerPage.regIn(user.getName(), user.getEmail(), user.getPassword());

        String errorText = registerPage.getErrorTextIncorrectPassword();
        assertEquals(EnvConfig.INCORRECT_MESSAGE, errorText);
    }
}
