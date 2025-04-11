package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User random() {
        String randomName = RandomStringUtils.randomAlphabetic(6);
        String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.randomAlphabetic(5));
        String randomPassword = RandomStringUtils.randomAlphanumeric(8);

        return new User(randomName, randomEmail, randomPassword);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
