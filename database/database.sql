-- Khởi tạo Database
DROP DATABASE IF EXISTS mobile_store_online;
CREATE DATABASE mobile_store_online;

use mobile_store_online;
-- Bỏ qua kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa các bảng trước khi tạo tránh lỗi
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `payment`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `preview`;
DROP TABLE IF EXISTS `rep_preview`;
DROP TABLE IF EXISTS `discount`;
DROP TABLE IF EXISTS `color`;
DROP TABLE IF EXISTS `storage`;
DROP TABLE IF EXISTS `product`;

-- Mở kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- Khởi tạo bảng
CREATE TABLE `user` (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        phone_number NVARCHAR(255) UNIQUE,
                        address NVARCHAR(255),
                        full_name NVARCHAR(255),
                        birthday DATE,
                        email VARCHAR(255) UNIQUE,
                        password VARCHAR(255),
                        avatar NVARCHAR(255),
                        create_date timestamp DEFAULT CURRENT_TIMESTAMP,
                        roles varchar(225) default 'USER',
                        is_active BIT DEFAULT 1
);
CREATE TABLE `user_payment`(
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               card_number nvarchar(15),
                               bank_type  nvarchar(255),
                               expiration_date date,
                               CVV int,
                               card_holder_name nvarchar(255),
                               is_default bit,
                               is_active bit,
                               user_id int
);
CREATE TABLE `product` (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name NVARCHAR(255),
                           price FLOAT,
                           quantity INT,
                           description NVARCHAR(255),
                           state BIT,
                           create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           percent_discount INT DEFAULT 0,
                           color_id INT,
                           storage_id INT,
                           category_id INT,
                           trademark_id INT
);
CREATE TABLE `orders`(
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         total float,
                         create_date timestamp DEFAULT CURRENT_TIMESTAMP,
                         modified_date timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         state bit,
                         user_id INT
);
CREATE TABLE `payment`(
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          payment_amount float,
                          payment_method nvarchar(255),
                          state bit,
                          create_date timestamp DEFAULT CURRENT_TIMESTAMP,
                          order_id int
);
CREATE TABLE `order_detail` (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                quantity INT,
                                price FLOAT,
                                create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                order_id INT,
                                product_id INT
);
CREATE TABLE `preview`(
                          id INT PRIMARY KEY auto_increment,
                          rade float,
                          content NVARCHAR(255),
                          user_id INT,
                          create_date timestamp DEFAULT CURRENT_TIMESTAMP,
                          product_id int
);
CREATE TABLE `rep_preview`(
                              id INT PRIMARY KEY auto_increment,
                              create_date timestamp DEFAULT CURRENT_TIMESTAMP,
                              content NVARCHAR(255),
                              admin_id int,
                              preview_id int
);
CREATE TABLE `discount` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(255),
    percent FLOAT,
    product_id INT,
    description NVARCHAR(255),
    is_active BIT,
    is_special BIT DEFAULT FALSE,
    expiration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE `storage`(
                          id INT primary key auto_increment,
                          random_access_memory_value int,
                          random_access_memory_unit nvarchar(2),
                          read_only_memory_value int,
                          read_only_memory_unit nvarchar(2)
);

CREATE TABLE `image`(
                        id INT primary key auto_increment,
                        image_url  nvarchar(255),
                        product_id int
);
CREATE TABLE `color`(
                        id INT primary key auto_increment,
                        color nvarchar(255)
);
CREATE TABLE `category`
(
    id INT PRIMARY KEY auto_increment,
    name NVARCHAR(255),
    description NVARCHAR(255)
);
CREATE TABLE `trademark`
(
    id INT PRIMARY KEY auto_increment,
    name NVARCHAR(255),
    description NVARCHAR(255)
);
CREATE TABLE `cart_detail`
(
    id INT PRIMARY KEY auto_increment,
    product_id int,
    quantity int,
    cart_id int
);
CREATE TABLE `cart` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    session_id NVARCHAR(100)
);
-- Tringer
DELIMITER //
CREATE TRIGGER before_update_product
BEFORE UPDATE ON product FOR EACH ROW
BEGIN
  SET NEW.modified_date = CURRENT_TIMESTAMP;
END;
//
DELIMITER ;
-- Thêm khóa ngoại
ALTER TABLE `user_payment` ADD CONSTRAINT fk_userPayment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE `orders` ADD CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE `payment` ADD CONSTRAINT fk_payment_orders FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;
ALTER TABLE `order_detail` ADD CONSTRAINT fk_orderDeatil_orders FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;
ALTER TABLE `order_detail` ADD CONSTRAINT fk_orderDeatil_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;
ALTER TABLE `cart_detail` ADD CONSTRAINT fk_cartDetail_product  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;
ALTER TABLE `cart_detail` ADD CONSTRAINT fk_cartDetail_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE;
ALTER TABLE `cart` ADD CONSTRAINT fk_cart_user  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE `preview` ADD CONSTRAINT fk_preview_user  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE `preview` ADD CONSTRAINT fk_preview_product  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;
ALTER TABLE `rep_preview` ADD CONSTRAINT fk_repPreview_admin  FOREIGN KEY (admin_id) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE `rep_preview` ADD CONSTRAINT fk_repPreview_preview  FOREIGN KEY (preview_id) REFERENCES preview(id) ON DELETE CASCADE;
ALTER TABLE `discount` ADD CONSTRAINT fk_discount_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;
ALTER TABLE `product` ADD CONSTRAINT fk_product_storage FOREIGN KEY (storage_id) REFERENCES storage(id) ON DELETE CASCADE;
ALTER TABLE `product` ADD CONSTRAINT fk_product_color  FOREIGN KEY (color_id) REFERENCES color(id) ON DELETE CASCADE;
ALTER TABLE `product` ADD CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE;
ALTER TABLE `product` ADD CONSTRAINT fk_product_trademark  FOREIGN KEY (trademark_id) REFERENCES trademark(id) ON DELETE CASCADE;
-- Thêm dữ liệu
use mobile_store_online;
INSERT INTO user (phone_number ,address ,full_name , birthday, email ,password ,avatar ,create_date, is_active) VALUES 
("0387887142","TP. Hồ Chí Minh","Huỳnh Thanh Phi","2003-10-10","phihtps23333@gmail.com","1234","phiht.png","2023-10-22 01:44:34",true),
("0355333455","Hà Nội","Nguyễn Ánh Hoa","2002-05-10","hoanaps33444@gmail.com","1234","hoana.png","2023-10-20 01:44:34",true),
("0387887435","TP. Hồ Chí Minh","Nguyễn Minh Hậu","2003-10-10","haunmps23333@gmail.com","1234","haunm.png","2023-10-22 01:44:34",true);
-- Thêm dữ liệu cho bảng color
INSERT INTO color (color) VALUES 
('Đen'), ('Trắng'), ('Vàng'), ('Bạc'), ('Xám Đen'), ('Hồng Vàng'), ('Xanh Đen Nửa Đêm'), ('Xanh Dương') ,("Chàm"),("Tím"),("Hồng"),("Đỏ"),("Xanh lá");
-- Thêm dữ liệu cho bảng storage
INSERT INTO storage (random_access_memory_value, random_access_memory_unit, read_only_memory_value, read_only_memory_unit) VALUES 
(1, 'GB', 16, 'GB'),
(1, 'GB', 32, 'GB'), 
(1, 'GB', 64, 'GB'), 
(2, 'GB', 32, 'GB'),
(2, 'GB', 64, 'GB'), 
(2, 'GB', 128, 'GB'), 
(4, 'GB', 64, 'GB'),
(4, 'GB', 128, 'GB'), 
(4, 'GB', 256, 'GB'), 
(6, 'GB', 64, 'GB'), 
(6, 'GB', 128, 'GB'), 
(6, 'GB', 256, 'GB') , 
(8, 'GB', 64, 'GB'), 
(8, 'GB', 128, 'GB'), 
(8, 'GB', 256, 'GB'), 
(8, 'GB', 526, 'GB'), 
(8, 'GB', 1, 'TB'), 
(12, 'GB', 64, 'GB'), 
(12, 'GB', 128, 'GB'), 
(12, 'GB', 256, 'GB'), 
(12, 'GB', 526, 'GB'), 
(12, 'GB', 1, 'TB'), 
(16, 'GB', 64, 'GB'), 
(16, 'GB', 128, 'GB'), 
(16, 'GB', 256, 'GB'),
(16, 'GB', 526, 'TB'),
(32, 'GB', 64, 'GB'), 
(32, 'GB', 128, 'GB'), 
(32, 'GB', 256, 'GB'),
(32, 'GB', 526, 'GB'),
(32, 'GB', 1, 'TB');
-- Thêm dữ liệu cho bảng category (danh mục iPhone)
INSERT INTO category (name, description) VALUES
('iPhone 6', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 6.'),
('iPhone 7', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 7.'),
('iPhone 8', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 8.'),
('iPhone X', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 10.'),
('iPhone XS', 'Danh mục chứa các sản phẩm thuộc dòng iPhone XS.'),
('iPhone 11', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 11.'),
('iPhone 12', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 12.'),
('iPhone 13 Pro', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 13.'),
('iPhone 13 Pro Max', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 13.'),
('iPhone 13', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 13.'),
('iPhone 14', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 14.'),
('iPhone 14 Pro', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 14.'),
('iPhone 14 Pro Max', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 14.'),
('iPhone 15 Pro', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 15.'),
('iPhone 15 Pro Max', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 15.'),
('iPhone 15', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 15.'),
('Samsung Galaxy S8', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S8.'),
('Samsung Galaxy S9', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S9.'),
('Samsung Galaxy S10', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S10.'),
('Samsung Galaxy S20', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S20.'),
('Samsung Galaxy S21', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S21.'),
('Samsung Galaxy S22', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S22.'),
('Samsung Galaxy S23', 'Danh mục chứa các sản phẩm thuộc dòng Samsung Galaxy S23.'),
('Huawei P20', 'Danh mục chứa các sản phẩm thuộc dòng Huawei P20.'),
('Huawei P30', 'Danh mục chứa các sản phẩm thuộc dòng Huawei P30.'),
('Huawei P40', 'Danh mục chứa các sản phẩm thuộc dòng Huawei P40.'),
('Huawei Mate 10', 'Danh mục chứa các sản phẩm thuộc dòng Huawei Mate 10.'),
('Huawei Mate 20', 'Danh mục chứa các sản phẩm thuộc dòng Huawei Mate 20.'),
('iPad', 'Danh mục chứa các sản phẩm thuộc dòng iPad.'),
('Apple Watch', 'Danh mục chứa các sản phẩm thuộc dòng Apple Watch.');


-- Thêm dữ liệu cho bảng trademark
INSERT INTO trademark (name, description) VALUES 
('Apple', 'Công ty công nghệ nổi tiếng với dòng sản phẩm iPhone'),
('Samsung', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Galaxy'),
('Xiaomi', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Mi'),
('Huawei', 'Công ty công nghệ nổi tiếng với dòng sản phẩm P30'),
('Sony', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Xperia'),
('LG', 'Công ty công nghệ nổi tiếng với dòng sản phẩm G series'),
('Nokia', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Nokia 9'),
('Google', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Pixel'),
('OnePlus', 'Công ty công nghệ nổi tiếng với dòng sản phẩm OnePlus 8'),
('Motorola', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Moto G'),
('HTC', 'Công ty công nghệ nổi tiếng với dòng sản phẩm HTC U'),
('BlackBerry', 'Công ty công nghệ nổi tiếng với dòng sản phẩm BlackBerry Key'),
('Asus', 'Công ty công nghệ nổi tiếng với dòng sản phẩm ROG Phone'),
('Lenovo', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Lenovo Z'),
('OPPO', 'Công ty công nghệ nổi tiếng với dòng sản phẩm Find X');
-- Thêm dữ liệu cho bảng product (iPhone) - Từ iPhone 7 trở lên
INSERT INTO product (name, price, quantity, description, state, percent_discount, color_id, storage_id, category_id, trademark_id) VALUES 
('iPhone 7', 8990000, 60, 'iPhone 7 - Một trong những mô hình phổ biến nhất với thiết kế tinh tế, camera nâng cấp và hiệu suất ổn định. Trải nghiệm iOS đáng tin cậy.', 1, 0,5, 17, 6, 10),
('iPhone 7 Plus', 12990000, 50, 'iPhone 7 Plus - Phiên bản lớn với màn hình lớn, camera kép độ phân giải cao và pin dung lượng cao. Cho trải nghiệm giải trí tốt nhất.', 1, 0, 8, 17, 6, 10),
('iPhone 8', 11990000, 45, 'iPhone 8 - Mô hình tiếp theo với tính năng sạc không dây, camera chất lượng và hiệu suất tốt. Thiết kế sang trọng.', 1, 0, 8, 17, 7, 10),
('iPhone 8 Plus', 15990000, 40, 'iPhone 8 Plus - Phiên bản lớn của iPhone 8 với màn hình lớn và camera chất lượng cao. Cho trải nghiệm đa phương tiện mạnh mẽ.', 1, 0, 8, 17, 7, 10),
('iPhone X', 17990000, 35, 'iPhone X - Bước đột phá với màn hình OLED, thiết kế không viền và Face ID. Cho trải nghiệm người dùng hiện đại.', 1, 0, 8, 17, 8, 10),
('iPhone XS', 21990000 , 30, 'iPhone XS - Mô hình tiếp theo với hiệu suất mạnh mẽ, camera kép nâng cấp và màn hình OLED. Sự lựa chọn cao cấp.', 1, 0, 8, 17, 8, 10),
('iPhone XS Max', 24990000 , 25, 'iPhone XS Max - Phiên bản lớn nhất với màn hình OLED lớn, pin dung lượng cao và camera chất lượng. Điện thoại đa phương tiện hàng đầu.', 1, 0, 8, 17, 8, 10),
('iPhone XR', 16990000 , 50, 'iPhone XR - Sự kết hợp giữa hiệu suất ổn định và giá trị tốt. Màn hình Liquid Retina đẹp và camera chất lượng.', 1, 0, 8, 17, 8, 10),
('iPhone 11', 19990000 , 40, 'iPhone 11 - Mô hình nổi bật với hệ thống camera kép, pin dung lượng cao và hiệu suất mạnh mẽ. Sự lựa chọn tốt cho cả giải trí và công việc.', 1, 0, 8, 17, 9, 10),
('iPhone 11 Pro', 24990000, 35, 'iPhone 11 Pro - Điện thoại chuyên nghiệp với hệ thống camera ba cảm biến, màn hình OLED và hiệu suất ấn tượng. Cho người dùng đòi hỏi cao.', 1, 0, 8, 17, 9, 10),
('iPhone 11 Pro Max', 27990000 , 30, 'iPhone 11 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Sự lựa chọn cao cấp nhất của Apple.', 1, 0, 8, 17, 9, 10),
('iPhone 12', 23990000 , 25, 'iPhone 12 - Mô hình với thiết kế mới, hỗ trợ 5G và hiệu suất mạnh mẽ. Màn hình Super Retina XDR cho trải nghiệm tuyệt vời.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Mini', 21990000 , 20, 'iPhone 12 Mini - Phiên bản nhỏ gọn với màn hình nhỏ, nhẹ và hiệu suất tốt. Dễ dàng mang theo và sử dụng một tay.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Pro', 30990000 , 30, 'iPhone 12 Pro - Sự chuyên nghiệp với hệ thống camera nâng cấp, màn hình ProMotion và hiệu suất ấn tượng. Cho người dùng sáng tạo.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Pro Max', 33990000 , 25, 'iPhone 12 Pro Max - Phiên bản lớn nhất với camera chất lượng cao, màn hình lớn và pin dung lượng cao. Điện thoại chụp ảnh xuất sắc.', 1, 0, 8, 17, 10, 10),
('iPhone 13 Mini', 22990000 , 15, 'iPhone 13 Mini - Mô hình nhỏ gọn nhưng mạnh mẽ với hiệu suất ổn định, camera cải tiến và màn hình Super Retina XDR.', 1, 0, 8, 17, 11, 10),
('iPhone 13', 27990000 , 20, 'iPhone 13 - Mô hình mới với thiết kế hiện đại, camera nâng cấp và hiệu suất mạnh mẽ. Trải nghiệm iOS tốt nhất.', 1, 0, 8, 17, 11, 10),
('iPhone 13 Pro', 30990000 , 15, 'iPhone 13 Pro - Sự chuyên nghiệp với hệ thống camera đa cảm biến, màn hình ProMotion và hiệu suất ấn tượng. Cho người dùng sáng tạo.', 1, 0, 8, 17, 11, 10),
('iPhone 13 Pro Max', 33990000 , 10, 'iPhone 13 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Điện thoại chụp ảnh xuất sắc.', 1, 0, 8, 17, 11, 10),
('iPhone 14', 35990000 , 25, 'iPhone 14 - Dòng sản phẩm tiếp theo với thiết kế đột phá và công nghệ tiên tiến. Màn hình đẹp và hiệu suất ấn tượng.', 1, 0, 8, 17, 12, 10),
('iPhone 14 Pro', 38990000 , 20, 'iPhone 14 Pro - Mô hình chuyên nghiệp với hệ thống camera tiên tiến, màn hình OLED và khả năng xử lý mạnh mẽ. Cho trải nghiệm sáng tạo.', 1, 0, 8, 17, 12, 10),
('iPhone 14 Pro Max', 41990000 , 15, 'iPhone 14 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Điện thoại chụp ảnh và quay video đỉnh cao.', 1, 0, 8, 17, 12, 10),
('iPhone 15', 44990000 , 10, 'iPhone 15 - Dòng sản phẩm đỉnh cao với công nghệ mới, camera vô cùng nâng cao và hiệu suất không giới hạn. Trải nghiệm di động hoàn hảo.', 1, 0, 8, 17, 13, 10);
INSERT INTO discount (name,percent,is_active, expiration_date) VALUES
("MAGIAMGIA1",10,true,"2024-10-20 01:44:34"),
("MAGIAMGIA2",10,true,"2024-10-20 01:44:34");
INSERT INTO preview (rade,content,user_id,product_id) VALUES
(5,"Sản phẩm rất tốt",1,1),
(4.5,"Phục vụ tốt",1,2),
(5,"Sản phẩm rất tốt",2,2),
(4.5,"Phục vụ tốt",2,1);
INSERT INTO rep_preview (content,admin_id,preview_id) VALUES
("Cảm ơn quý khách",3,1),
("Cảm ơn quý khách",3,3);
