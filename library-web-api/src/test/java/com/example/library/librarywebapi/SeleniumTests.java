package com.example.library.librarywebapi;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Ustaw ścieżkę do pliku chromedriver, jeśli nie jest w PATH
        System.setProperty("webdriver.chrome.driver", "C:\\Webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void testCreateBook() {
        // Otwórz stronę dodawania książki
        driver.get("http://localhost:8080/api/books/add");

        // Wypełnij formularz
        driver.findElement(By.name("title")).sendKeys("Test Book");
        driver.findElement(By.name("isbn")).sendKeys("1234567890123");
        driver.findElement(By.name("publishedDate")).sendKeys("2024-01-01");
        driver.findElement(By.name("authors")).sendKeys("Author1, Author2");
        driver.findElement(By.name("genres")).sendKeys("Fiction, Adventure");

        // Kliknij przycisk "Add Book"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Sprawdź komunikat potwierdzający
        WebElement message = driver.findElement(By.cssSelector(".success-message p"));
        assertEquals("Dodano książkę.", message.getText());
    }

    @Test
    @Order(2)
    public void testUpdateBook() {
        // Otwórz stronę edycji książki
        driver.get("http://localhost:8080/api/books/edit");

        // Wprowadź ID książki do edycji
        driver.findElement(By.name("identifier")).sendKeys("Test Book");

        // Wypełnij nowe dane książki
        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys("Updated Test Book");
        driver.findElement(By.name("isbn")).clear();
        driver.findElement(By.name("isbn")).sendKeys("9876543210987");
        driver.findElement(By.name("publishedDate")).clear();
        driver.findElement(By.name("publishedDate")).sendKeys("2025-01-01");
        driver.findElement(By.name("authors")).clear();
        driver.findElement(By.name("authors")).sendKeys("Updated Author1, Updated Author2");
        driver.findElement(By.name("genres")).clear();
        driver.findElement(By.name("genres")).sendKeys("Updated Fiction, Updated Adventure");

        // Kliknij przycisk "Save Changes"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Poczekaj na widoczność komunikatu sukcesu
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".success-message p")));

        // Sprawdź komunikat potwierdzający
        assertEquals("Zaaktulizowano książkę.", message.getText());
    }



    @Test
    @Order(3)
    public void testDeleteBook() {
        // Otwórz stronę usuwania książki
        driver.get("http://localhost:8080/api/books/delete");

        // Wprowadź ID książki do usunięcia
        driver.findElement(By.name("identifier")).sendKeys("Updated Test Book");

        // Kliknij przycisk "Delete"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Sprawdź komunikat potwierdzający
        WebElement message = driver.findElement(By.cssSelector(".success-message p"));
        assertEquals("Usunięto książkę.", message.getText());
    }
}
