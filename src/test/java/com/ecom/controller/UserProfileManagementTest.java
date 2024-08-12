// package com.ecom.controller;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import java.time.Duration;

// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class UserProfileManagementTest {

//     private WebDriver driver;

//     @BeforeEach
//     public void setUp() {
        
//         System.setProperty("webdriver.chrome.driver", "D:/SOFTWARE/chromedriver-win64/chromedriver.exe");

        
//         ChromeOptions options = new ChromeOptions();
//         options.addArguments("--disable-gpu");
//         options.addArguments("--no-sandbox");

//         driver = new ChromeDriver(options);
//         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//     }

//     @Test
//     public void testUserProfileManagement() {
//         try {
//             // Step 1: Register a New User
//             driver.get("http://localhost:8080/register");

//             WebElement usernameField = driver.findElement(By.name("username"));
//             WebElement emailField = driver.findElement(By.name("email"));
//             WebElement passwordField = driver.findElement(By.name("password"));
//             WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));

//             usernameField.sendKeys("testuser");
//             emailField.sendKeys("testuser@example.com");
//             passwordField.sendKeys("password123");
//             registerButton.click();

//             // Verify Registration Success
//             WebElement registrationSuccessMessage = driver.findElement(By.id("registrationSuccessMessage"));
//             assertTrue(registrationSuccessMessage.isDisplayed(), "Registration success message not displayed");
//             assertTrue(registrationSuccessMessage.getText().contains("Registration successful"), "Message does not contain expected text");

//             // Step 2: View User Profile
//             driver.get("http://localhost:8080/profile");

//             WebElement profileUsername = driver.findElement(By.id("profileUsername"));
//             WebElement profileEmail = driver.findElement(By.id("profileEmail"));

//             assertEquals("testuser", profileUsername.getText(), "Profile username does not match");
//             assertEquals("testuser@example.com", profileEmail.getText(), "Profile email does not match");

//             // Step 3: Update User Profile
//             WebElement editProfileButton = driver.findElement(By.id("editProfileButton"));
//             editProfileButton.click();

//             WebElement editUsernameField = driver.findElement(By.name("username"));
//             WebElement editEmailField = driver.findElement(By.name("email"));
//             WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit']"));

//             editUsernameField.clear();
//             editUsernameField.sendKeys("updateduser");
//             editEmailField.clear();
//             editEmailField.sendKeys("updateduser@example.com");
//             saveButton.click();

//             // Verify Profile Update Success
//             WebElement updateSuccessMessage = driver.findElement(By.id("updateSuccessMessage"));
//             assertTrue(updateSuccessMessage.isDisplayed(), "Profile update success message not displayed");
//             assertTrue(updateSuccessMessage.getText().contains("Profile updated successfully"), "Message does not contain expected text");

//             // Re-check the updated profile information
//             driver.get("http://localhost:8080/profile");

//             WebElement updatedProfileUsername = driver.findElement(By.id("profileUsername"));
//             WebElement updatedProfileEmail = driver.findElement(By.id("profileEmail"));

//             assertEquals("updateduser", updatedProfileUsername.getText(), "Updated profile username does not match");
//             assertEquals("updateduser@example.com", updatedProfileEmail.getText(), "Updated profile email does not match");

//         } catch (Exception e) {
//             // Log exception details for debugging
//             e.printStackTrace();
//             throw e;
//         }
//     }

//     @AfterEach
//     public void tearDown() {
//         if (driver != null) {
//             driver.quit();
//         }
//     }
// }
