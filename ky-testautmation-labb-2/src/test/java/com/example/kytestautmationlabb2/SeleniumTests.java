package com.example.kytestautmationlabb2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeleniumTests {


    public static WebDriver driver;
    public static WebDriverWait wait;


    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://svtplay.se");
        // Remove popup window

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Acceptera alla')]")));
        //WebElement acceptAllButton = dialog.findElement(By.xpath("//button[contains(text(), 'Acceptera alla')]"));

        WebElement acceptAllButton = driver.findElement(By.xpath("//button[contains(text(), 'Acceptera alla')]"));
        acceptAllButton.click();

        // TODO: Here we are doing an ugly wait to wait for the "saving your selection" after clicking 'acceptera alla' button above. Can be improved at some point.
        try {
            Thread.sleep(5000); // Sleep for 5 seconds (5000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        assertEquals("SVT Play", driver.getTitle(), "Page title is not as expected");
    }

    @Test
    // Test 2:  Kontrollera att webbplatsens logotyp är synlig
    public void testLogotypeIsVisible() {
        //WebElement logo = driver.findElement(By.xpath("//div[@class=\"banner__image\" and @style=\"blahblah\"]"));

        WebElement logo = driver.findElement(By.xpath("//a[@data-rt-spec=\"play-logo\" and @href=\"/\"]"));
        assertEquals(true, logo.isDisplayed(), "I could not find the logo..");
    }

    // Test 3:  Kontrollera namnen på de tre länkarna i huvudmenyn “Start, Program, Kanaler”
    @Test
    public void verifyMainMenuLinkStart() {
        WebElement startLink = driver.findElement(By.xpath("//a[@href=\"/\" and @accesskey=1]"));
        assertEquals("START", startLink.getText(), "Start link did not have correct text");
    }

    @Test
    public void verifyMainMenuLinkProgram() {
        WebElement startLink = driver.findElement(By.xpath("//a[@href=\"/program\" and @accesskey=2]"));
        assertEquals("PROGRAM", startLink.getText(), "PROGRAM link did not have correct text");
    }

    @Test
    public void verifyMainMenuLinkKanaler() {
        WebElement startLink = driver.findElement(By.xpath("//a[@href=\"/kanaler\" and @accesskey=3]"));
        assertEquals("KANALER", startLink.getText(), "KANALER link did not have correct text");
    }


    // Test 4:  Kontrollera att länken för “Tillgänglighet i SVT Play” är synlig och att rätt länktext visas.
    @Test
    public void verifyLinkTillgänglighetISVTPLayExists() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://kontakt.svt.se/guide/tillganglighet']"));
        assertEquals(true, link.isDisplayed(), "The link 'Tillgänglighet i SVT Play' does not display...");
    }

    // Test 5:  Följ länken Tillgänglighet i SVT Play och kontrollera huvudrubriken
    @Test
    public void verifyTillgänglighetISVTPLayHeader() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://kontakt.svt.se/guide/tillganglighet']"));
        link.click();

        WebElement header = driver.findElement(By.xpath("//h1"));
        assertEquals("Så arbetar SVT med tillgänglighet", header.getText(), "Headertexten på tillgänglighetssidan stämmer ej");
    }

    // Test 6:  Använd metoden “click()” för att navigera in till sidan “Program”. Kontrollera antalet kategorier som listas.
    @Test
    public void verifyNumberOfCategoriesShown() {
        WebElement startLink = driver.findElement(By.xpath("//a[@href=\"/program\" and @accesskey=2]"));
        startLink.click();

        WebElement kategorierSection = driver.findElement(By.cssSelector("section[aria-label='Kategorier']"));
        List<WebElement> h2Elements = kategorierSection.findElements(By.tagName("h2"));

        assertEquals(18, h2Elements.size(), "18 categories did not show...");
    }

    // Test 7-: Skriv ytterligare 5 olika test för SVT Play där minst Locators för xpath, CSSselector och className används. Utgå från vad du själv skulle valt att testa om du arbetade för SVT med webbplatsen.
}
