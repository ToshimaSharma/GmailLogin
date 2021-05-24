package Login;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;

public class GmailLogin {
    WebDriver driver;
    WebDriverWait wait;
    String emailSubject = "This is test subject";
    String emailBody = "This is test mail";
    @Test
    void GmailTestFlow(){

        System.setProperty("webdriver.chrome.driver","chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //login test
        driver.get("https://www.gmail.com");
        driver.findElement(By.id("identifierId")).sendKeys("toshimaupadhyaya14");
        driver.findElement(By.xpath("//*[@id='identifierNext']")).click();
        driver.findElement(By.name("password")).sendKeys("Knoldus@123");
        driver.findElement(By.xpath("//*[@id='passwordNext']")).click();

        //compose email

        //click compose
        By composeElementIdentifier = By.xpath("//*[@role='button' and (.)='Compose']");
        wait.until(ExpectedConditions.presenceOfElementLocated(composeElementIdentifier));
        driver.findElement(composeElementIdentifier).click();

        //enter to field
        By toFieldIdentifier = By.name("to");
        wait.until(ExpectedConditions.presenceOfElementLocated(toFieldIdentifier));
        WebElement txtBoxToField = driver.findElement(toFieldIdentifier);
        txtBoxToField.clear();
        txtBoxToField.sendKeys("toshimaupadhyaya14@gmail.com");

        //Enter subject
        By subjectBoxIdentifier = By.name("subjectbox");
        wait.until(ExpectedConditions.presenceOfElementLocated(subjectBoxIdentifier));
        driver.findElement(subjectBoxIdentifier).clear();
        driver.findElement(subjectBoxIdentifier).sendKeys(emailSubject);

        //Enter email body
//        By emailBodyIdentifier = By.xpath("//*[@id=\":5y\"]");
//        wait.until(ExpectedConditions.presenceOfElementLocated(emailBodyIdentifier));
//        driver.findElement(emailBodyIdentifier).clear();
//        driver.findElement(emailBodyIdentifier).sendKeys(emailBody);

        //click more settings
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-JN-M-I J-J5-Ji Xv L3 T-I-ax7 T-I']/div[2]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@class='J-Ph-hFsbo']"))).click();

        //Choose Social label
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-LC-Jz' and text()='Social']/div[@class='J-LC-Jo J-J5-Ji']"))).click();

        try {
            Thread.sleep(5000);
        } catch ( Exception e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-JK-Jz' and text()='Apply']"))).click();

        //Click Send
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent')]")));
        List<WebElement> inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA zE']"))));

        for(WebElement email : inboxEmails){
            if(email.isDisplayed() && email.getText().contains("This is test subject")){
                email.click();

                //marking the email as star
                driver.findElement(By.cssSelector(".zd > span:nth-child(1)")).click();

                try {
                    Thread.sleep(5000);
                } catch (Exception e){
                    e.printStackTrace();
                }

                //validate the email subject
                String actualSubject = driver.findElement(By.className("hP")).getText();
                Assert.assertEquals(actualSubject, emailSubject);
                break;
            }
        }
        driver.quit();

    }
}