# Dự án Kiểm thử Tự động - Điện Máy Xanh (Assignment GĐ2)

## 1. Giới thiệu
Dự án thực hiện kiểm thử tự động chức năng Đăng nhập trên website Điện Máy Xanh.
- **Website:** https://www.dienmayxanh.com/lich-su-mua-hang/dang-nhap
- **Công nghệ:** Java, Selenium WebDriver, TestNG, Maven.
- **CI/CD:** Tích hợp GitHub Actions.

## 2. Các kịch bản kiểm thử (Test Cases)
- **TC01:** Nhập SĐT hợp lệ -> Kiểm tra thông báo gửi OTP thành công.
- **TC02:** SĐT ít hơn 10 số -> Báo lỗi định dạng.
- **TC03:** SĐT chứa chữ cái -> Báo lỗi định dạng.
- **TC04:** SĐT để trống -> Báo lỗi định dạng.
- **TC05:** SĐT ký tự đặc biệt -> Báo lỗi định dạng.

## 3. Hướng dẫn chạy Test
1. Mở terminal hoặc Command Prompt tại thư mục gốc dự án.
2. Chạy lệnh: `mvn test`
3. **Lưu ý quan trọng:** Do website có cơ chế chống Bot (reCAPTCHA), khi chạy trên máy cá nhân, nếu hệ thống yêu cầu giải CAPCHA, vui lòng thực hiện thủ công để bài test tiếp tục.

## 4. Cấu hình CI/CD
Dự án đã được cấu hình chạy tự động trên GitHub Actions (Headless mode) mỗi khi có lệnh `push` code lên nhánh chính.