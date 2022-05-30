import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;

public class ChitayGorodTest {

  private String address;
  private String login;
  private String password;

  WebDriver driver;

  @BeforeClass
  void setup() {

    FileInputStream fis;
    Properties property = new Properties();
    try {
      fis = new FileInputStream(this.getClass().getResource("/config.properties").getFile());
      property.load(fis);
      address = property.getProperty("address");
      login = property.getProperty("login");
      password = property.getProperty("password");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    URL url = getClass().getResource("Selenium/msedgedriver.exe");
    System.setProperty("webdriver.edge.driver", url.getPath());
    driver = new EdgeDriver();
  }

  @Test(priority = 1, testName = "authorization suite")
  void Authorize() {
    MainPage mainPage = new MainPage(driver);
    mainPage.authorize(login, password);
  }

  @Test(priority = 2, testName = "editing suite")
  void EditUserData() {
    MainPage mainPage = new MainPage(driver);
    UserPage userPage = mainPage.goToUserPage(driver);
    userPage.GoToEditPersonalData();

    Random r = new Random();
    int maxLen = 11;
    int surnameLen = (r.nextInt(maxLen) + 2);
    int nameLen = (r.nextInt(maxLen) + 2);
    int secondNameLen = (r.nextInt(maxLen) + 2);

    StringBuilder surname = new StringBuilder();
    StringBuilder name = new StringBuilder();
    StringBuilder secondName = new StringBuilder();

    for (int i = 0; i < surnameLen; i++) {
      char c = (char) (r.nextInt(26) + 'a');
      surname.append(c);
    }

    for (int i = 0; i < nameLen; i++) {
      char c = (char) (r.nextInt(26) + 'a');
      name.append(c);
    }

    for (int i = 0; i < secondNameLen; i++) {
      char c = (char) (r.nextInt(26) + 'a');
      secondName.append(c);
    }

    userPage.UpdatePersonalData(surname.toString(), name.toString(), secondName.toString());
  }

  String randomBookName;

  @Test(priority = 3, testName = "search suite")
  void SearchByCategory() {
    MainPage mainPage = new MainPage(driver);
    BookSearchPage bookSearchPage = mainPage.goToSearchPage(driver);
    List<String> categoriesPage = bookSearchPage.GetCategories();
    Random r = new Random();
    String category = categoriesPage.get(r.nextInt(categoriesPage.size() - 1));
    bookSearchPage.selectCategory(category, driver);
    List<String> bookList = bookSearchPage.getBooks(driver);
    randomBookName = bookList.get(r.nextInt(bookList.size() - 1));
    assert (bookSearchPage.checkCategory(category));
  }

  @Test(priority = 4, testName = "category search suite")
  void SearchSpecialBook() {
    MainPage mainPage = new MainPage(driver);
    BookSearchPage bookSearchPage = mainPage.goToSearchPage(driver);
    bookSearchPage.FindBookInDropRecommendations(driver, randomBookName);
  }

  @Test(priority = 5, testName = "adding to basket suite")
  void AddBookToBasket() {
    MainPage mainPage = new MainPage(driver);
    BookSearchPage bookSearchPage = mainPage.goToSearchPage(driver);
    List<String> bookList = bookSearchPage.getBooks(driver);
    Random r = new Random();
    String bookName = bookList.get(0);
    bookSearchPage.addBookToBasket(driver, bookName);
    BasketPage basketPage = bookSearchPage.goToBasketPage(driver);
    assert basketPage.checkBookName(driver, bookName);
  }

  @AfterClass
  void tearDown() {
    driver.quit();
  }

}
