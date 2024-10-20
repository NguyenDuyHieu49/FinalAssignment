CREATE DATABASE quanly_thanhthieunien;
USE quanly_thanhthieunien;

CREATE TABLE thanh_thieu_nien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    gioi_tinh ENUM('Nam', 'Nu') NOT NULL,
    ngay_sinh DATE NOT NULL,
    noi_o VARCHAR(255) NOT NULL,
    ho_khau_thuong_tru VARCHAR(255) NOT NULL,
    nguoi_giam_ho VARCHAR(100) NOT NULL,
    sdt_nguoi_giam_ho VARCHAR(20),
    quan_he_voi_tre VARCHAR(50),
    tinh_trang_gia_dinh VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password) VALUES
('user1', 'password1'),
('user2', 'password2');

INSERT INTO thanh_thieu_nien (ho_ten, gioi_tinh, ngay_sinh, noi_o, ho_khau_thuong_tru, nguoi_giam_ho, sdt_nguoi_giam_ho, quan_he_voi_tre, tinh_trang_gia_dinh)
VALUES 
('Nguyen Van A', 'Nam', '2010-12-15', 'Hà Nội', 'Hà Nội', 'Nguyen Van B', '0123456789', 'Cha', 'Bình thường'),
('Tran Thi B', 'Nu', '2008-05-03', 'Đà Nẵng', 'Đà Nẵng', 'Tran Thi C', '0987654321', 'Mẹ', 'Khó khăn'),
('Le Van D', 'Nam', '2012-07-20', 'Hải Phòng', 'Hải Phòng', 'Le Thi E', '0123987654', 'Bà', 'Gia đình chính sách'),
('Nguyen Thi F', 'Nu', '2009-11-25', 'TP HCM', 'TP HCM', 'Nguyen Van G', '0987123456', 'Cha', 'Bình thường'),
('Hoang Van H', 'Nam', '2011-04-10', 'Đà Nẵng', 'Đà Nẵng', 'Hoang Thi I', '0123456780', 'Mẹ', 'Khó khăn'),
('Phan Thi J', 'Nu', '2013-09-30', 'Hà Nội', 'Hà Nội', 'Phan Van K', '0987654320', 'Cha', 'Bình thường');

select*from thanh_thieu_nien;






