package tests;

import base.BaseTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {

    @Test(priority = 1, description = "Kiểm tra chức năng tìm kiếm")
   
    public void testGoogleSearch() {
        // 1. Truy cập Google
        driver.get("https://www.google.com");

        // 2. KHÔNG DÙNG By.name("q") đơn thuần nữa.
        // Dùng WebDriverWait để bắt trình duyệt ĐỢI cho đến khi ô nhập liệu hiện ra
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Tìm bằng XPath tổng quát (chấp cả input lẫn textarea)
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@name='q']")));
        
        // 3. Thực hiện thao tác
        searchBox.clear();
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.sendKeys(Keys.ENTER);

        // 4. Đợi 2 giây cho tiêu đề trang thay đổi rồi mới kiểm tra
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // 5. Kiểm tra kết quả
        Assert.assertTrue(driver.getTitle().contains("Selenium"), "Lỗi: Tiêu đề không khớp!");
    }

    @Test(priority = 2, description = "Kiểm tra nút Gmail hiển thị")
    public void testGmailLinkDisplay() {
        // Kiểm tra xem link Gmail có tồn tại trên trang chủ không
        WebElement gmailLink = driver.findElement(By.linkText("Gmail"));
        Assert.assertTrue(gmailLink.isDisplayed(), "Không tìm thấy nút Gmail trên trang chủ!");
    }

    @Test(description = "Kiểm tra chuyển hướng sang trang Login Google")
    public void testLoginGoogle() {
        driver.get("https://www.google.com");

        // 1. Khởi tạo lệnh đợi (Wait) để tránh lỗi NoSuchElement
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 2. Tìm nút Đăng nhập bằng cách kết hợp: href chứa 'accounts' VÀ Text (dù Anh hay Việt)
        // Đây là XPath mạnh nhất giúp bạn vượt qua lỗi nãy giờ
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'accounts.google.com') and (contains(., 'Đăng nhập') or contains(., 'Sign in'))]")));

        // 3. Cuộn tới nút đó và Click (dùng JavaScript để click cho chắc chắn không bị block)
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);

        // 4. Kiểm tra xem đã sang trang đăng nhập chưa
        
    }
}