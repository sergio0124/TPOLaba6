import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage extends PageFactory {

  WebDriverWait wait;

  @FindBy(xpath = "//a[@href=\"/profile/personal/\"]")
  WebElement editPersonalDataButton;

  @FindBy(xpath = "//input[@name='surname']")
  WebElement surnameField;

  @FindBy(xpath = "//input[@name='name']")
  WebElement nameField;

  @FindBy(xpath = "//input[@name='second_name']")
  WebElement secondNameField;

  @FindBy(xpath = "//button[@type='submit']")
  WebElement saveChangesData;

  public UserPage(WebDriver driver) {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    PageFactory.initElements(driver, this);
    wait.until(ExpectedConditions.elementToBeClickable(editPersonalDataButton));
  }

  public void GoToEditPersonalData(){
    editPersonalDataButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(surnameField));
  }

  public void UpdatePersonalData(String surname, String name, String secondName){
    surnameField.clear();
    surnameField.sendKeys(surname);
    nameField.clear();
    nameField.sendKeys(name);
    secondNameField.clear();
    secondNameField.sendKeys(secondName);
    saveChangesData.click();
  }
}
