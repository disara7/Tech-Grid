package com.ecom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class UserRegistrationAndLoginTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "D:/SOFTWARE/chromedriver-win64/chromedriver.exe");

        
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        // Set timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
    }

    @Test
    public void testRegistrationAndLogin() {
        driver.get("http://localhost:8080/register");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.name("submit"));

        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpassword");
        submitButton.click();

        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assert successMessage.isDisplayed();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
