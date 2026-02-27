package assmktnc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.Collections;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        // THÊM DÒNG NÀY: Giả lập trình duyệt Chrome trên Windows để né bị chặn IP
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        options.addArguments("--disable-blink-features=AutomationControlled");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.dienmayxanh.com/lich-su-mua-hang/dang-nhap");
    }

    @Test(priority = 1, description = "TC01 – Nhập SĐT hợp lệ")
    public void TC01_ValidPhone() {
        submitPhone("0827433122");
        
        // Đợi bạn giải Captcha (nếu có). Sau khi có tích xanh, trang sẽ tự chuyển 
        // hoặc bạn nhấn Enter/Click nút Tiếp tục.
        System.out.println("TC01: Đang đợi xác nhận OTP...");
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(45));
        WebElement msg = waitLong.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Mã xác nhận đã được gửi')]")));
        Assert.assertTrue(msg.isDisplayed(), "Lỗi: Không thấy thông báo gửi OTP!");
    }

    @Test(priority = 2, description = "TC02 - SĐT ít hơn 10 số")
    public void TC02_InvalidPhoneShort() {
        submitPhone("09123");
        verifyErrorMessage();
    }

    @Test(priority = 3, description = "TC03 – SĐT chứa chữ cái")
    public void TC03_PhoneWithLetters() {
        submitPhone("09abc12345");
        verifyErrorMessage();
    }

    @Test(priority = 4, description = "TC04 - SĐT để trống")
    public void TC04_EmptyPhone() {
        submitPhone("");
        verifyErrorMessage();
    }

    @Test(priority = 5, description = "TC05 – SĐT ký tự đặc biệt")
    public void TC05_SpecialChars() {
        submitPhone("09012###89");
        verifyErrorMessage();
    }

    // --- HÀM DÙNG CHUNG ĐỂ TỐI ƯU CODE ---

    private void submitPhone(String phone) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtPhoneNumber")));
        input.clear();
        input.sendKeys(phone);
        
        // Nhấn Enter để kích hoạt form (Thay cho việc click nút Tiếp tục hay bị chặn)
        input.sendKeys(Keys.ENTER);
    }

    private void verifyErrorMessage() {
        // Sử dụng nội dung thông báo lỗi thực tế trên web: "Số điện thoại trống/không đúng định dạng"
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'không đúng định dạng')]")));
        Assert.assertTrue(errorMsg.isDisplayed(), "Lỗi: Không hiển thị cảnh báo định dạng SĐT!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}