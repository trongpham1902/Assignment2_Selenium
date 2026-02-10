package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.time.Duration;

public class TestLoginLab6 {
    WebDriver driver;

   
    @Test
    public void runLab6() throws Exception {
        // 1. Khởi tạo Driver (Xử lý lỗi NoSuchDriver)
        WebDriverManager.chromedriver().setup(); 
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Khởi tạo bộ đợi (Wait) - Cực kỳ quan trọng để tránh lỗi NoSuchElement
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        String status1 = "FAIL";
        String status2 = "FAIL";

        try {
            // --- TRANG 1 (Google) ---
            driver.get("https://www.google.com");
            // Đợi ô tìm kiếm hiện ra mới nhập liệu
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox.sendKeys("FPT Polytechnic", Keys.ENTER);
            status1 = "PASS";

            // --- TRANG 2 (Practice Test) ---
            driver.get("https://practicetestautomation.com/practice-test-login/");
            
            // Đợi và nhập Username
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")))
                .sendKeys("student");
            
            // Nhập Password
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Password123");
            
            // Click Submit
            driver.findElement(By.cssSelector("button[id='submit']")).click();
            
            // Đợi tiêu đề thành công hiện ra
            WebElement resultMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("post-title")));
            Assert.assertEquals(resultMsg.getText(), "Logged In Successfully");
            status2 = "PASS";

        } catch (Exception e) {
            System.out.println("Lỗi trong quá trình test: " + e.getMessage());
        } finally {
            // --- XUẤT EXCEL & ĐÓNG TRÌNH DUYỆT ---
            exportExcel(status1, status2);
            driver.quit();
        }
    }

    public void exportExcel(String s1, String s2) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("KetQua");
        
        // Tạo Header
        Row r0 = sheet.createRow(0);
        r0.createCell(0).setCellValue("Trang Test");
        r0.createCell(1).setCellValue("Kết quả");

        // Ghi dữ liệu trang 1
        Row r1 = sheet.createRow(1);
        r1.createCell(0).setCellValue("Google (TestLogin1)");
        r1.createCell(1).setCellValue(s1);

        // Ghi dữ liệu trang 2
        Row r2 = sheet.createRow(2);
        r2.createCell(0).setCellValue("Practice (TestLogin2)");
        r2.createCell(1).setCellValue(s2);

        // Xuất file
        FileOutputStream out = new FileOutputStream("KetQuaLab6.xlsx");
        workbook.write(out);
        out.close();
        workbook.close();
        System.out.println("Đã tạo file Excel thành công tại thư mục dự án!");
    }
}