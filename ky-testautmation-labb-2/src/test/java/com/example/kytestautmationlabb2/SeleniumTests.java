package com.example.kytestautmationlabb2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeleniumTests {


    public static WebDriver driver;


    @BeforeAll
    public static void setUp() {
        // Hämta in de webdrivers du vill använda
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //options.addArguments("incognito");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void navigate() {
        driver.get("https://svtplay.se");
    }

    @Test
    public void testWebsiteTitle() {
        // Navigera in till den URL du vill testa för respektive driver
        driver.get("https://svtplay.se");//Before each normally navigates to page, but doing it here as well for better test understandability.
        assertEquals("SVT Play", driver.getTitle(), "Titeln stämmer inte med förväntat");
    }
}
