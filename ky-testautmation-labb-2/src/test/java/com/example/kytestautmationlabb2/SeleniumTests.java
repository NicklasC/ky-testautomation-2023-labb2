package com.example.kytestautmationlabb2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        sleep(5);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void navigate() {
        driver.get("https://svtplay.se");
    }

    // Test 1: Kontrollera att webbplatsens titel stämmer
    @Test
    public void testWebsiteTitle() {
        // Navigera in till den URL du vill testa för respektive driver
        driver.get("https://svtplay.se");//Before each normally navigates to page, but doing it here as well for better test understandability.
        assertEquals("SVT Play", driver.getTitle(), "Page title is not as expected");
    }

    @Test
    // Test 2:  Kontrollera att webbplatsens logotyp är synlig
    public void testLogotypeIsVisible() {
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

        assertEquals(17, h2Elements.size(), "17 categories did not show...");
    }

    // Test 7-: Skriv ytterligare 5 olika test för SVT Play där minst Locators för xpath, CSSselector och className används. Utgå från vad du själv skulle valt att testa om du arbetade för SVT med webbplatsen.

    // 7.1 Verify you can search on the site
    @Test
    public void verifySearchLeadsToSearchResultPage() {
        WebElement searchField = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit' and @title='Sök på svtplay.se']"));

        searchField.sendKeys("ag");
        searchButton.click();

        WebElement searchResult = driver.findElement(By.xpath("//h2[@data-rt='header-search-result']"));

        String searchResultText = searchResult.getText();
        String searchResultTextSubSelection = searchResultText.substring(0, 21);

        assertEquals("Din sökning på ag gav", searchResultTextSubSelection, "Some form of search results seems to display");
        assertEquals("https://www.svtplay.se/sok?q=ag", driver.getCurrentUrl(), "Expected search result page URL is not correct)");
    }

    // 7.2 Verify cookies information link displays.
    @Test
    public void cookieInformationLinkDisplays() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://kontakt.svt.se/guide/cookies-och-personuppgifter']"));
        String text = link.getText();
        String textSelection = text.substring(0, 30);

        assertEquals("Om cookies och personuppgifter", textSelection, "cookie information link text is not correct");
    }

    // 7.3 Verify cookies information page displays.
    @Test
    public void cookieInformationLinkLeadsToCorrectPage() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://kontakt.svt.se/guide/cookies-och-personuppgifter']"));
        link.click();

        WebElement header = driver.findElement(By.className("text-3xl"));

        assertEquals("Om cookies och personuppgiftsbehandling på SVT", header.getText(), "cookie information header text is not correct");
    }

    // 7.4.1 Verify TV tablå for channels displays
    @Test
    public void TVTablåForSVT1Displays() {
        driver.get(("https://www.svtplay.se/kanaler"));
        WebElement section = driver.findElement(By.cssSelector("section[aria-label='Tablå för SVT 1']"));
        assertEquals(true, section.isDisplayed(), "Tablå for SVT1 did not display");
    }

    // 7.4.2
    @Test
    public void TVTablåForSVT2Displays() {
        driver.get(("https://www.svtplay.se/kanaler"));
        WebElement section = driver.findElement(By.cssSelector("section[aria-label='Tablå för SVT 2']"));
        assertEquals(true, section.isDisplayed(), "Tablå for SVT2 did not display");
    }

    //7.4.3
    @Test
    public void TVTablåForSVTBarnDisplays() {
        driver.get(("https://www.svtplay.se/kanaler"));
        WebElement section = driver.findElement(By.cssSelector("section[aria-label='Tablå för SVT Barn']"));
        assertEquals(true, section.isDisplayed(), "Tablå for SVT Barn did not display");
    }

    //7.4.4
    @Test
    public void TVTablåForKunskapskanalenDisplays() {
        driver.get(("https://www.svtplay.se/kanaler"));
        WebElement section = driver.findElement(By.cssSelector("section[aria-label='Tablå för Kunskapskanalen']"));
        assertEquals(true, section.isDisplayed(), "Tablå for Kunskapskanalen did not display");
    }

    // 7.5.1
    @Test
    public void NyhetsbrevLinkDisplays() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://nyhetsbrev.svtplay.se/prenumerera/?utm_source=svtplay&utm_medium=footer-cta']"));
        String text = link.getText();
        String textSelection = text.substring(0, 10);

        assertEquals("Nyhetsbrev", textSelection, "Nyhetsbrev link text is not correct");
    }

    // 7.5.2 Verify cookies information page displays.
    @Test
    public void NyhetsbrevLinkLeadsToCorrectPage() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://nyhetsbrev.svtplay.se/prenumerera/?utm_source=svtplay&utm_medium=footer-cta']"));
        link.click();

        WebElement header = driver.findElement(By.xpath("//h1"));

        //String headerText=header.getText();

        assertEquals("Missa inga program och serier!", header.getText(), "nyhetsbrev title page not correct");
    }

    // 8.1 (VG) Testa sökformuläret genom att söka efter “Agenda” och kontrollera att
    // programmet Agenda dyker upp överst i sökresultatet. Sökformuläret ska
    //  lokaliseras med locatorn för name-attributet.

    @Test
    public void searchForAgenda() {
        WebElement searchField = driver.findElement(By.cssSelector("input[name='q']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit' and @title='Sök på svtplay.se']"));

        searchField.sendKeys("Agenda");
        searchButton.click();

        // Getting all H2 elements
        List<WebElement> h2Elements = driver.findElements(By.tagName("h2"));

        //Getting search result header text
        String searchResultHeaderText = h2Elements.get(0).getText();

        //Getting a subselection only, as we do not know exactly how many search hits we will get.
        String searchResultTextSubSelection = searchResultHeaderText.substring(0, 21);

        assertEquals("Din sökning på Agenda", searchResultTextSubSelection, "search for Agenda header presented");

        // Verifying the first search result is Agenda.
        String firstSearchResultText = h2Elements.get(1).getText();
        assertEquals("Agenda", firstSearchResultText, "First search result should be Agenda, but was not");

        /*for (WebElement element : h2Elements) {
            // Writes all h2 elements text
            System.out.println(element.getText());
        }*/
    }

    // 8.2 (VG) Använd sökförmuläret för att navigera in till programsidan för programmet
    // “Pistvakt”. Kontrollera därefter antalet program i säsong 2 av serien. Kontrollera
    // även namnet på avsnitt 5 i säsong 2

    @Test
    public void searchForPistvaktAndVerifyNumberOfEpisodesAndEpisodeName() {
        WebElement searchField = driver.findElement(By.cssSelector("input[name='q']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit' and @title='Sök på svtplay.se']"));

        searchField.sendKeys("Pistvakt");
        searchButton.click();

        // Getting all H2 object
        List<WebElement> h2Elements = driver.findElements(By.tagName("h2"));

        // Clicking on Pistvakt
        WebElement pistvaktElement = h2Elements.get(1);
        pistvaktElement.click();

        // Initially Season 1 is expanded, but not season 2. Because of this I click on season 1 button to collapse it, and then season 2 to expand it. That way, only season 2 episodes should show.

        WebElement selectSeason1link = driver.findElement(By.cssSelector("a[href='/pistvakt']"));
        selectSeason1link.click();
        // Both Season 1 and Season 2 should now be collapsed

        // Clicking on Season 2 => It should expand
        WebElement selectSeason2link = driver.findElement(By.cssSelector("a[href='/pistvakt?tabs=season-2-jx3za5B']"));
        selectSeason2link.click();

        // Now only season 2 episodes are listed. Fetching the season 2 episodes into a list

        List<WebElement> season2EpisodesList = driver.findElements(By.tagName("h3"));

        // Going through all H3 elements. We are only interested in elements that contains text. I did not know how to filter that using findElements, so I did it programatically instead.

        int numberOfEpisodes = 0;
        for (WebElement episode : season2EpisodesList) {
            if (episode.getText().length() > 0) {
                numberOfEpisodes++;
            }
        }

        // Making requested asserts.
        assertEquals(6, numberOfEpisodes, "Incorrect amount of season 2 number of episodes");
        assertEquals("5. Personalfestan", season2EpisodesList.get(4).getText(), "Season 2 Episode 5 name should be 5. Personalfestan, but it is not");
    }


    // 8.3 Skriv ytterligare 5 olika test för SVT Play med relevanta kontroller
    //
    // Nicklas kommentar: Har skrivit 4 ytterligare tester i föregående steg, dvs
    // 7.4.2
    // 7.4.3
    // 7.4.4
    // 7.5.2

    // Making an additional test (Actually with two verification points) so we have 5 extra tests for VG level.
    @Test
    public void DatorLinkDisplaysAndNavigatesToCorrectPage() {
        WebElement link = driver.findElement(By.xpath("//a[@href='https://kontakt.svt.se/guide/svt-play-webb']"));
        String text = link.getText();
        String textSelection = text.substring(0, 5);

        assertEquals("Dator", textSelection, "Dator information link text starts with Dator, OK!");

        link.click();
        WebElement header = driver.findElement(By.cssSelector("H1"));

        assertEquals("Systemkrav för SVT Play på webben", header.getText(), "Link 'Dator' did not lead to a page with correct text.");
        System.out.println(header.getText());

    }

    public static void sleep(int numberOfSeconds) {
        try {
            Thread.sleep(numberOfSeconds * 1000); // Sleep for number of seconds seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
