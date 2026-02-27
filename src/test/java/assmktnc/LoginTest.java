package assmktnc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
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
        
        // Cấu hình bắt buộc để chạy trên GitHub Actions
        options.addArguments("--headless=new"); 
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Trang web test tiêu chuẩn, cực kỳ ổn định cho CI/CD
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test(priority = 1, description = "TC01 - Đăng nhập thành công với tài khoản chuẩn")
    public void TC01_LoginSuccess() {
        login("tomsmith", "SuperSecretPassword!");
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        Assert.assertTrue(msg.getText().contains("You logged into a secure area!"), "Lỗi TC01!");
    }

    @Test(priority = 2, description = "TC02 - Sai mật khẩu")
    public void TC02_WrongPassword() {
        login("tomsmith", "InvalidPassword");
        verifyError("Your password is invalid!");
    }

    @Test(priority = 3, description = "TC03 - Sai tên đăng nhập")
    public void TC03_WrongUsername() {
        login("wronguser", "SuperSecretPassword!");
        verifyError("Your username is invalid!");
    }

    @Test(priority = 4, description = "TC04 - Để trống cả Username và Password")
    public void TC04_EmptyLogin() {
        login("", "");
        verifyError("Your username is invalid!");
    }

    @Test(priority = 5, description = "TC05 - Nhập username đúng nhưng để trống password")
    public void TC05_EmptyPassword() {
        login("tomsmith", "");
        verifyError("Your password is invalid!");
    }

    // --- HÀM TRỢ GIÚP TỐI ƯU CODE ---

    private void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    private void verifyError(String expectedText) {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        Assert.assertTrue(errorMsg.getText().contains(expectedText), "Lỗi: Thông báo không khớp!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}