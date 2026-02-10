package assmktnc; // Khai báo package đúng theo thư mục bạn chọn

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Headless Mode để chạy mượt trên GitHub Actions (Phần C)
        if (System.getenv("GITHUB_ACTIONS") != null) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.dienmayxanh.com/");

        // Mở form đăng nhập
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("login-user")));
        loginBtn.click();
    }

    @Test(description = "TC01 - Nhập SĐT hợp lệ")
    public void TC01_ValidPhone() {
        submitPhone("0901234777");
        WebElement otpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Mã xác nhận đã được gửi')]")));
        Assert.assertTrue(otpMsg.isDisplayed());
    }

    @Test(description = "TC02 - SĐT không hợp lệ")
    public void TC02_InvalidShortPhone() {
        submitPhone("09123");
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-text")));
        Assert.assertEquals(errorMsg.getText(), "Số điện thoại không hợp lệ");
    }

    @Test(description = "TC04 - SĐT để trống")
    public void TC04_EmptyPhone() {
        submitPhone("");
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-text")));
        Assert.assertEquals(errorMsg.getText(), "Vui lòng nhập số điện thoại");
    }

    private void submitPhone(String phone) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtPhoneNumber")));
        input.clear();
        input.sendKeys(phone);
        driver.findElement(By.className("btn-submit")).click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}