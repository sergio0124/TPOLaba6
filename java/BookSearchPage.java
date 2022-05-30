import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookSearchPage extends PageFactory {

  WebDriverWait wait;
  String http = "https://www.chitai-gorod.ru/catalog/books/";

  @FindBy(xpath = "//ul[@class='navigation navigation']")
  List<WebElement> listOfCategories;

  @FindBy(xpath = "//input[@class=\"search-form__input js__search-input\"]")
  WebElement searchString;

  @FindBy(xpath = "//button[@class=\"button js__product_card_button product-card__button button_product\"]")
  WebElement buttonBuy;

  @FindBy(xpath = "//li[@class='breadcrumbs__item']")
  List<WebElement> listOfCurrentCategories;

  public BookSearchPage(WebDriver driver) {
    if (driver.getCurrentUrl() != http) {
      driver.get(http);
    }

    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
    wait.until(ExpectedConditions.visibilityOf(listOfCategories.get(0)));
  }

  public ArrayList<String> GetCategories() {
    ArrayList<String> result = new ArrayList<>();
    List<WebElement> listElems = new ArrayList<>();
    for (WebElement webList : listOfCategories
    ) {
      webList.findElements(By.xpath(".//li")).stream().forEach((p) -> listElems.add(p));
    }
    listElems.stream().filter((p) -> p.getText().length() > 0)
        .forEach((elem) -> result.add(elem.getText()));
    return result;
  }

  public void selectCategory(String category, WebDriver driver) {
    WebElement categoryElement = driver
        .findElement(By.xpath("//a[text()='" + category + "']"));
    driver.get(categoryElement.getAttribute("href"));
  }

  public ArrayList<String> getBooks(WebDriver driver) {
    ArrayList<String> result = new ArrayList<>();
    List<WebElement> listElems = driver.findElements(
        By.xpath("//div[@class=\"product-card js_product js__product_card js__slider_item\"]"));
    listElems.stream().forEach((elem) -> result.add(elem.getAttribute("data-chg-book-name")));
    return result;
  }

  public void FindBookInDropRecommendations(WebDriver driver, String randomBookName) {
    searchString.sendKeys(randomBookName);
    WebElement bookDropPanel = driver.findElement(
        By.xpath("//div[@class=\"search-form__tips js__search_tips_results hidden\"]"));
    wait.until(ExpectedConditions.elementToBeClickable(bookDropPanel));
    WebElement bookDropRecommendation = driver.findElement(
        By.xpath("//b[text()='" + randomBookName + "']"));
    bookDropRecommendation.click();
  }

  public int GetNumberOfDesiredBooks(WebDriver driver) {
    WebElement tableWithNumber = driver.findElement(
        By.xpath("//span[@class=\"basket__item-count bookmarks js__bookmarks_count\"]"));
    return Integer.parseInt(tableWithNumber.getText());
  }

  public int GetNumberOfBasketBooks(WebDriver driver) {
    WebElement tableWithNumber = driver.findElement(
        By.xpath("//span[@class=\"basket__item-count count js__basket_count\"]"));
    return Integer.parseInt(tableWithNumber.getText());
  }

  public void AddBookToBasket(WebDriver driver) {
    buttonBuy = driver.findElement(By.xpath(
        "//button[@data-status=\"buy\"]"));
    buttonBuy.click();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  public void Refresh(WebDriver driver) {
    driver.navigate().refresh();
    wait.until(ExpectedConditions.elementToBeClickable(listOfCategories.get(0)));
  }

  public boolean checkCategory(String category) {
    for (WebElement element : listOfCurrentCategories
    ) {
      WebElement child = element.findElement(By.xpath(".//a"));
      if (child.getAttribute("title").contains(category)) {
        return true;
      }
    }
    return false;
  }

  public void addBookToBasket(WebDriver driver, String bookName) {

    WebElement nameOfBook = (WebElement) driver
        .findElements(By.xpath("//div[@class=\"product-card__title js-analytic-product-title\"]")).stream()
        .filter(rec -> ((WebElement)rec).getText().trim().contains(bookName)).toArray()[0];

    WebElement parentOfName = nameOfBook
        .findElement(By.xpath(".//ancestor::div[@class=\"product-card__info\"]"));
    WebElement addButton = parentOfName.findElement(By.xpath(".//button[@type=\"button\"]"));
    addButton.click();
  }

  public BasketPage goToBasketPage(WebDriver driver) {
    return new BasketPage(driver);
  }
}
