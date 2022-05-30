import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends PageFactory {

  WebDriverWait wait;
  String http = "https://www.chitai-gorod.ru/";

  @FindBy(xpath = "//img[@alt='Логотип \"Читай-город\"']")
  WebElement logotypeChitayGorod;

  @FindBy(xpath = "//a[@href=\"/profile/\"]")
  WebElement userLogo;

  @FindBy(xpath = "//button[@class=\"js__showPopupLogin\"]")
  WebElement entryButton;

  @FindBy(xpath = "//input[@name='login']")
  WebElement loginField;

  @FindBy(xpath = "//input[@name='password']")
  WebElement passwordField;

  @FindBy(xpath = "//button[@name='Login']")
  WebElement authorizeButton;

  @FindBy(xpath = "//a[@href=\"/catalog/books/\"]")
  WebElement booksButton;

  public MainPage(WebDriver driver) {
    if (driver.getCurrentUrl() != http) {
      driver.get(http);
    }

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
    wait.until(ExpectedConditions.elementToBeClickable(booksButton));
  }

  public UserPage goToUserPage(WebDriver driver) {
    userLogo.click();
    return new UserPage(driver);
  }

  public void authorize(String login, String password){
    entryButton.click();
    loginField.sendKeys(login);
    passwordField.sendKeys(password);
    authorizeButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(userLogo));
  }

  public BookSearchPage goToSearchPage(WebDriver driver){
    booksButton.click();
    return new BookSearchPage(driver);

  }
}
