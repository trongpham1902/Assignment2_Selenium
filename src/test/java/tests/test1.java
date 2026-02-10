package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class test1 {
    @Test
    public void test1() {
        // Tự động thiết lập trình duyệt Chrome
        WebDriverManager.chromedriver().setup();
        
        // Thêm tùy chọn để chạy ổn định hơn
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        
        try {
            String url = "http://www.google.com";
            String title_exp = "Google";

            // Truy cập vào trang Google
            driver.get(url);

            // Lấy tiêu đề thực tế của trang
            String title = driver.getTitle();

            // So sánh tiêu đề và in kết quả ra Console
            if (title.contentEquals(title_exp)) {
                System.out.println("Test Pass");
            } else {
                System.out.println("Test Fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt sau khi kiểm tra xong
            if (driver != null) {
                driver.quit();
            }
        }
    }
}