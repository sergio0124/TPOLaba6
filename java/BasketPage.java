import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketPage extends PageFactory {

  WebDriverWait wait;
  String http = "https://www.chitai-gorod.ru/personal/basket/";

  @FindBy(xpath = "//li[@class=\"cart-nav__item js__cart-nav__item_cart active\"]")
  List<WebElement> listOfCategories;

  public BasketPage(WebDriver driver) {
    if (driver.getCurrentUrl() != http) {
      driver.get(http);
    }

    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
    wait.until(ExpectedConditions.visibilityOf(listOfCategories.get(0)));
  }

  public boolean checkBookName(WebDriver driver, String name) {
    return driver.findElements(By.xpath("//a[@class=\"basket-item__link\"]")).stream()
        .anyMatch(rec -> rec.getText().contains(name));
  }
}
