package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest extends BaseTest {

    // Hàm hỗ trợ để đợi và tìm phần tử, giúp code chạy ổn định hơn
    private WebElement findElementSafe(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Test(priority = 1)
    public void loginWithValidAccount() {
        // Tìm ô nhập Số điện thoại bằng placeholder vì eNetViet hay thay đổi ID/Name
        WebElement userField = findElementSafe(By.xpath("//input[contains(@placeholder, 'Số điện thoại')]"));
        userField.clear();
        userField.sendKeys("0912345678");

        // Tìm ô mật khẩu bằng thuộc tính type='password'
        WebElement passField = driver.findElement(By.xpath("//input[@type='password']"));
        passField.sendKeys("password123");

        // Click nút Đăng nhập dựa trên nội dung chữ bên trong nút
        driver.findElement(By.xpath("//button[contains(., 'Đăng nhập')]")).click();

        // Chờ URL thay đổi sang dashboard để xác nhận thành công
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean success = wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(success, "Đăng nhập không thành công!");
    }

    @Test(priority = 2)
    public void loginWithInvalidPassword() {
        // Lặp lại bước tìm kiếm bằng XPath linh hoạt
        WebElement userField = findElementSafe(By.xpath("//input[contains(@placeholder, 'Số điện thoại')]"));
        userField.clear();
        userField.sendKeys("0912345678");

        WebElement passField = driver.findElement(By.xpath("//input[@type='password']"));
        passField.sendKeys("saimatkhau");

        driver.findElement(By.xpath("//button[contains(., 'Đăng nhập')]")).click();

        // Chờ thông báo lỗi xuất hiện (thường là một thẻ chứa chữ 'Sai thông tin')
        WebElement errorMsg = findElementSafe(By.xpath("//*[contains(text(), 'không chính xác') or contains(text(), 'Sai thông tin')]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Không hiển thị thông báo lỗi!");
    }

    @Test(priority = 3)
    public void loginWithEmptyData() {
        // Case này của bạn đã pass, mình giữ nguyên logic nhưng tối ưu locator
        findElementSafe(By.xpath("//button[contains(., 'Đăng nhập')]")).click();
        
        WebElement validateMsg = findElementSafe(By.xpath("//*[contains(text(), 'Vui lòng nhập')]"));
        Assert.assertTrue(validateMsg.isDisplayed());
    }
}