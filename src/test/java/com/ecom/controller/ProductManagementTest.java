package com.ecom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductManagementTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "D:/SOFTWARE/chromedriver-win64/chromedriver.exe");

     
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void testProductManagement() {
        try {
            // Add Product
            driver.get("http://localhost:8080/products/add");

            WebElement nameField = driver.findElement(By.name("name"));
            WebElement descriptionField = driver.findElement(By.name("description"));
            WebElement priceField = driver.findElement(By.name("price"));
            WebElement addButton = driver.findElement(By.cssSelector("button[type='submit']"));

            nameField.sendKeys("Test Product");
            descriptionField.sendKeys("This is a test product.");
            priceField.sendKeys("99.99");
            addButton.click();

            // Verify Product Added
            WebElement addSuccessMessage = driver.findElement(By.id("addSuccessMessage"));
            assertTrue(addSuccessMessage.isDisplayed(), "Product add success message not displayed");
            assertTrue(addSuccessMessage.getText().contains("Product added successfully"), "Message doesn't contain expected text");

            // View Product
            driver.get("http://localhost:8080/products");

            WebElement viewProductLink = driver.findElement(By.linkText("Test Product"));
            viewProductLink.click();

            WebElement productName = driver.findElement(By.id("productName"));
            WebElement productDescription = driver.findElement(By.id("productDescription"));
            WebElement productPrice = driver.findElement(By.id("productPrice"));

            assertEquals("Test Product", productName.getText(), "Product name does not match");
            assertEquals("This is a test product.", productDescription.getText(), "Product description does not match");
            assertEquals("99.99", productPrice.getText(), "Product price does not match");

            // Edit Product
            driver.get("http://localhost:8080/products/edit/1"); // Assuming 1 is the product ID

            WebElement editNameField = driver.findElement(By.name("name"));
            WebElement editDescriptionField = driver.findElement(By.name("description"));
            WebElement editPriceField = driver.findElement(By.name("price"));
            WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit']"));

            editNameField.clear();
            editNameField.sendKeys("Updated Product");
            editDescriptionField.clear();
            editDescriptionField.sendKeys("This is an updated test product.");
            editPriceField.clear();
            editPriceField.sendKeys("129.99");
            saveButton.click();

            // Verify Product Edited
            WebElement editSuccessMessage = driver.findElement(By.id("editSuccessMessage"));
            assertTrue(editSuccessMessage.isDisplayed(), "Product edit success message not displayed");
            assertTrue(editSuccessMessage.getText().contains("Product updated successfully"), "Message does not contain expected text");

            // Delete Product
            driver.get("http://localhost:8080/products");

            WebElement deleteProductButton = driver.findElement(By.cssSelector("button.delete-button")); 
            deleteProductButton.click();

            // Confirm deletion in any confirmation dialog if applicable
            driver.switchTo().alert().accept();

            // Verify Product Deleted
            WebElement deleteSuccessMessage = driver.findElement(By.id("deleteSuccessMessage"));
            assertTrue(deleteSuccessMessage.isDisplayed(), "Product delete success message not displayed");
            assertTrue(deleteSuccessMessage.getText().contains("Product deleted successfully"), "Message does not contain expected text");

        } catch (Exception e) {
            // Log exception details for debugging
            e.printStackTrace();
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
