DROP TABLE IF EXISTS `cart_detail`;
DROP TABLE IF EXISTS `store`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `inventory`;
DROP TABLE IF EXISTS `subcategory`;
DROP TABLE IF EXISTS `major_category`;
DROP TABLE IF EXISTS `order_detail`;
DROP TABLE IF EXISTS `product_review`;
DROP TABLE IF EXISTS `payment`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `product_option`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_id` varchar(50) NOT NULL COMMENT 'Login ID',
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL COMMENT 'Password Salt',
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` enum('USER','SELLER') DEFAULT 'USER' COMMENT 'User Role',
  `phone_number` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_id` (`login_id`),
  UNIQUE KEY `email` (`email`)
);


CREATE TABLE `store` (
  `store_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상점 ID',
  `store_name` varchar(100) NOT NULL COMMENT '상점 이름',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '상점 로고 URL',
  `description` text COMMENT '상점 설명',
  `business_number` varchar(20) NOT NULL COMMENT '사업자 번호',
  `user_id` bigint NOT NULL COMMENT '유저 ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`store_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품 ID',
  `product_name` varchar(255) DEFAULT NULL COMMENT '제품명',
  `description` text NOT NULL COMMENT '상품 설명',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '썸네일 URL',
  `major_category` int DEFAULT NULL COMMENT '대분류 코드',
  `subcategory` int DEFAULT NULL COMMENT '중분류 코드',
  `catalog` text COMMENT '상품 카탈로그',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  `deleted_at` datetime DEFAULT NULL COMMENT '삭제 일시',
  PRIMARY KEY (`id`)
);


CREATE TABLE `product_option` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품 옵션 ID',
  `product_id` bigint NOT NULL COMMENT '상품 ID',
  `option_name` varchar(100) NOT NULL COMMENT '옵션 이름',
  `origin_price` decimal(10,2) NOT NULL COMMENT '원가',
  `sale_price` decimal(10,2) NOT NULL COMMENT '판매가',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  `deleted_at` datetime DEFAULT NULL COMMENT '삭제 일시',
  PRIMARY KEY (`id`),
  CONSTRAINT `product_option_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
);



CREATE TABLE `order` (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '주문 ID',
 `user_id` bigint NOT NULL COMMENT '사용자 ID',
 `order_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '주문 일시',
 `status` enum('PENDING','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING' COMMENT '주문 상태',
 `total_amount` decimal(10,2) NOT NULL COMMENT '총 금액',
 `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
 `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
 PRIMARY KEY (`id`),
 CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);


CREATE TABLE `payment` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '결제 ID',
  `order_id` bigint NOT NULL COMMENT '주문 ID',
  `user_id` bigint NOT NULL COMMENT '유저 ID',
  `amount` decimal(10,2) NOT NULL COMMENT '결제 금액',
  `payment_method` enum('CARD','BANK_TRANSFER','CASH','OTHER') NOT NULL COMMENT '결제 수단',
  `payment_status` enum('PENDING','COMPLETED','FAILED','CANCELLED','REFUNDED') NOT NULL COMMENT '결제 상태',
  `payment_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '결제 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`payment_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `product_review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `rating` int DEFAULT NULL,
  `review_text` text NOT NULL,
  `user_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  CONSTRAINT `product_review_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `product_review_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
);


CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '주문 상세 항목 ID',
  `order_id` bigint NOT NULL COMMENT '주문 ID',
  `product_option_id` bigint NOT NULL COMMENT '상품 옵션 ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '상품 수량',
  `price` decimal(10,2) NOT NULL COMMENT '상품 가격',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`id`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`product_option_id`) REFERENCES `product_option` (`id`) ON DELETE CASCADE
);


CREATE TABLE `major_category` (
  `major_category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '대분류 ID',
  `category_name` varchar(100) NOT NULL COMMENT '대분류 이름',
  `description` text COMMENT '대분류 설명',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`major_category_id`)
);


CREATE TABLE `subcategory` (
  `subcategory_id` bigint NOT NULL AUTO_INCREMENT COMMENT '중분류 ID',
  `category_name` varchar(100) NOT NULL COMMENT '중분류 이름',
  `major_category_id` bigint NOT NULL COMMENT '연관된 대분류 ID',
  `description` text COMMENT '중분류 설명',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`subcategory_id`),
  CONSTRAINT `subcategory_ibfk_1` FOREIGN KEY (`major_category_id`) REFERENCES `major_category` (`major_category_id`) ON DELETE CASCADE
);


CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '재고 ID',
  `quantity` int NOT NULL DEFAULT '0' COMMENT '현재 수량',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  `deleted_at` datetime DEFAULT NULL COMMENT '삭제 일시',
  PRIMARY KEY (`id`)
);


CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '카트 ID',
  `user_id` bigint NOT NULL COMMENT '사용자 ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);



CREATE TABLE `cart_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '카트 상세 항목 ID',
  `cart_id` bigint NOT NULL COMMENT '카트 ID',
  `product_option_id` bigint NOT NULL COMMENT '상품 옵션 ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '상품 수량',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  PRIMARY KEY (`id`),
  CONSTRAINT `cart_detail_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
  CONSTRAINT `cart_detail_ibfk_2` FOREIGN KEY (`product_option_id`) REFERENCES `product_option` (`id`) ON DELETE CASCADE
);