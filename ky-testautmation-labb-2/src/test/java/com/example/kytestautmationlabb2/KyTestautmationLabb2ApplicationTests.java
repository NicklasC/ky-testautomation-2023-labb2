package com.example.kytestautmationlabb2;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KyTestautmationLabb2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testWebsiteTitle() {
		// Hämta in de webdrivers du vill använda
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(options);
		// Navigera in till den URL du vill testa för respektive driver
		driver.navigate().to("https://svtplay.se");

		assertEquals("SVT Play", driver.getTitle(), "Titeln stämmer inte med förväntat");

		driver.quit();
	}



}
