-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2022 at 02:00 PM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommercy`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE IF NOT EXISTS `addresses` (
                                           `id` varchar(255) NOT NULL,
                                           `address_line1` varchar(255) NOT NULL,
                                           `address_line2` varchar(255) DEFAULT NULL,
                                           `building` varchar(255) NOT NULL,
                                           `city` varchar(255) NOT NULL,
                                           `house_number` varchar(255) NOT NULL,
                                           `zip_code` varchar(255) NOT NULL,
                                           `country_id` varchar(255) NOT NULL,
                                           `county_id` varchar(255) NOT NULL,
                                           `customer_id` varchar(255) DEFAULT NULL,
                                           `state_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
                                        `id` varchar(255) NOT NULL,
                                        `created_at` datetime NOT NULL,
                                        `email` varchar(255) NOT NULL,
                                        `last_name` varchar(255) DEFAULT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `password` varchar(255) NOT NULL,
                                        `phone_number` varchar(255) DEFAULT NULL,
                                        `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `admins_roles`
--

CREATE TABLE IF NOT EXISTS `admins_roles` (
                                              `admin_id` varchar(255) NOT NULL,
                                              `role_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `attributes`
--

CREATE TABLE IF NOT EXISTS `attributes` (
                                            `id` varchar(255) NOT NULL,
                                            `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `baskets`
--

CREATE TABLE IF NOT EXISTS `baskets` (
                                         `id` varchar(255) NOT NULL,
                                         `discount` double NOT NULL,
                                         `total_price` double NOT NULL,
                                         `customer_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `basket_items`
--

CREATE TABLE IF NOT EXISTS `basket_items` (
                                              `id` varchar(255) NOT NULL,
                                              `quantity` int(11) NOT NULL,
                                              `basket_id` varchar(255) NOT NULL,
                                              `product_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
                                            `id` varchar(255) NOT NULL,
                                            `description` varchar(255) DEFAULT NULL,
                                            `name` varchar(255) NOT NULL,
                                            `group_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
                                          `id` varchar(255) NOT NULL,
                                          `comment` varchar(255) DEFAULT NULL,
                                          `created_at` datetime NOT NULL,
                                          `star` int(11) NOT NULL,
                                          `updated_at` datetime NOT NULL,
                                          `customer_id` varchar(255) NOT NULL,
                                          `product_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

CREATE TABLE IF NOT EXISTS `counties` (
                                          `id` varchar(255) NOT NULL,
                                          `name` varchar(255) NOT NULL,
                                          `state_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE IF NOT EXISTS `countries` (
                                           `id` varchar(255) NOT NULL,
                                           `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
                                           `id` varchar(255) NOT NULL,
                                           `created_at` datetime NOT NULL,
                                           `email` varchar(255) NOT NULL,
                                           `last_name` varchar(255) DEFAULT NULL,
                                           `name` varchar(255) NOT NULL,
                                           `password` varchar(255) NOT NULL,
                                           `phone_number` varchar(255) DEFAULT NULL,
                                           `updated_at` datetime NOT NULL,
                                           `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
                                        `id` varchar(255) NOT NULL,
                                        `description` varchar(255) DEFAULT NULL,
                                        `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
                                        `id` varchar(255) NOT NULL,
                                        `created_at` datetime NOT NULL,
                                        `discount` double NOT NULL,
                                        `total_price` double NOT NULL,
                                        `updated_at` datetime NOT NULL,
                                        `address_id` varchar(255) DEFAULT NULL,
                                        `customer_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE IF NOT EXISTS `order_items` (
                                             `id` varchar(255) NOT NULL,
                                             `quantity` int(11) NOT NULL,
                                             `order_id` varchar(255) NOT NULL,
                                             `product_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `privliages`
--

CREATE TABLE IF NOT EXISTS `privliages` (
                                            `id` varchar(255) NOT NULL,
                                            `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
                                          `id` varchar(255) NOT NULL,
                                          `created_at` datetime NOT NULL,
                                          `description` varchar(255) DEFAULT NULL,
                                          `name` varchar(255) NOT NULL,
                                          `stars` float NOT NULL,
                                          `updated_at` datetime NOT NULL,
                                          `group_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_attributes`
--

CREATE TABLE IF NOT EXISTS `products_attributes` (
                                                     `id` varchar(255) NOT NULL,
                                                     `name` varchar(255) NOT NULL,
                                                     `attribute_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_attributes_product`
--

CREATE TABLE IF NOT EXISTS `products_attributes_product` (
                                                             `product_non_value_attribute_id` varchar(255) NOT NULL,
                                                             `product_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_categories`
--

CREATE TABLE IF NOT EXISTS `products_categories` (
                                                     `products_id` varchar(255) NOT NULL,
                                                     `categories_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_non_value_attributes`
--

CREATE TABLE IF NOT EXISTS `products_non_value_attributes` (
                                                               `product_id` varchar(255) NOT NULL,
                                                               `non_value_attributes_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_value_attributes`
--

CREATE TABLE IF NOT EXISTS `products_value_attributes` (
                                                           `id` varchar(255) NOT NULL,
                                                           `discount` float NOT NULL,
                                                           `inventory` int(11) NOT NULL,
                                                           `name` varchar(255) NOT NULL,
                                                           `point` int(11) NOT NULL,
                                                           `price` float NOT NULL,
                                                           `attribute_id` varchar(255) DEFAULT NULL,
                                                           `product_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
    `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roles_privileges`
--

CREATE TABLE IF NOT EXISTS `roles_privileges` (
                                                  `role_name` varchar(255) NOT NULL,
                                                  `privilege_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE IF NOT EXISTS `states` (
                                        `id` varchar(255) NOT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `country_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKn3sth7s3kur1rafwbbrqqnswt` (`country_id`),
    ADD KEY IF NOT EXISTS `FKqj9vg45qrhh7c29amn45m5bkt` (`county_id`),
    ADD KEY IF NOT EXISTS `FKhrpf5e8dwasvdc5cticysrt2k` (`customer_id`),
    ADD KEY IF NOT EXISTS `FKqcujk6a19pr29agrlk18h2c9y` (`state_id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_47bvqemyk6vlm0w7crc3opdd4` (`email`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_jld98mhubn4q39ac763tdb8oh` (`phone_number`);

--
-- Indexes for table `admins_roles`
--
ALTER TABLE `admins_roles`
    ADD PRIMARY KEY IF NOT EXISTS (`role_name`, `admin_id`),
    ADD KEY IF NOT EXISTS `FKgl1rfe4yh5207gllc8g4a4eyl` (`role_name`),
    ADD KEY IF NOT EXISTS `FK8535wewsp17vgt2scahieovxu` (`admin_id`);

--
-- Indexes for table `attributes`
--
ALTER TABLE `attributes`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_s9dywou66pe8gmb704v2jspr7` (`name`);

--
-- Indexes for table `baskets`
--
ALTER TABLE `baskets`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKd9n51h863f9df2m0js5v3slv4` (`customer_id`);

--
-- Indexes for table `basket_items`
--
ALTER TABLE `basket_items`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKnx44qatthch57p2bgo873qfxu` (`basket_id`),
    ADD KEY IF NOT EXISTS `FKreybh8m8i647iovvot9grroeb` (`product_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FK36j4852qk859jhclfoocn6i7k` (`group_id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKev1bd87g1c51ujncao608e6qa` (`customer_id`),
    ADD KEY IF NOT EXISTS `FK6uv0qku8gsu6x1r2jkrtqwjtn` (`product_id`);

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_t2fslxc3suv6sis4u1r4j3vxs` (`name`),
    ADD KEY IF NOT EXISTS `FKt9r2d00r3fjs8jl76tl8c7yjn` (`state_id`);

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_1pyiwrqimi3hnl3vtgsypj5r` (`name`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_rfbvkrffamfql7cjmen8v976v` (`email`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_6v6x92wb400iwh6unf5rwiim4` (`phone_number`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
    ADD PRIMARY KEY IF NOT EXISTS (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKhlglkvf5i60dv6dn397ethgpt` (`address_id`),
    ADD KEY IF NOT EXISTS `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
    ADD KEY IF NOT EXISTS `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`);

--
-- Indexes for table `privliages`
--
ALTER TABLE `privliages`
    ADD PRIMARY KEY IF NOT EXISTS (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD KEY IF NOT EXISTS `FKtk0w0k1mrif467aywwbccg7oo` (`group_id`);

--
-- Indexes for table `products_attributes`
--
ALTER TABLE `products_attributes`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_4jddq2hysmf4we1tnwpp4frb4` (`name`),
    ADD KEY IF NOT EXISTS `FKkq7j4hgd8pb1aj0eku3d04v4` (`attribute_id`);

--
-- Indexes for table `products_attributes_product`
--
ALTER TABLE `products_attributes_product`
    ADD KEY IF NOT EXISTS `FKe189j89owtmhr8tfh5h6yclqu` (`product_id`),
    ADD KEY IF NOT EXISTS `FK7eiyy1gjur1qesj5gjj1xdi13` (`product_non_value_attribute_id`);

--
-- Indexes for table `products_categories`
--
ALTER TABLE `products_categories`
    ADD KEY IF NOT EXISTS `FKr2i82ji875a3sdha76w2e2qyn` (`categories_id`),
    ADD KEY IF NOT EXISTS `FKf3gerrr47lrha835uu0ip1uqn` (`products_id`);

--
-- Indexes for table `products_non_value_attributes`
--
ALTER TABLE `products_non_value_attributes`
    ADD KEY IF NOT EXISTS `FKaiptqbj0ppvtmf449q90rp978` (`non_value_attributes_id`),
    ADD KEY IF NOT EXISTS `FKtlr0bf2kctc2rw28akwwauo65` (`product_id`);

--
-- Indexes for table `products_value_attributes`
--
ALTER TABLE `products_value_attributes`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_76lk4bmka7bnuub2gbs0rxtb8` (`name`),
    ADD KEY IF NOT EXISTS `FKndrxve9qawp3f484lt4esjvmv` (`attribute_id`),
    ADD KEY IF NOT EXISTS `FK16jehbev92jgm1kki3pege07v` (`product_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
    ADD PRIMARY KEY IF NOT EXISTS (`name`);

--
-- Indexes for table `roles_privileges`
--
ALTER TABLE `roles_privileges`
    ADD KEY IF NOT EXISTS `FKimhxnr0rnrfnlq6qcdrr6k2ie` (`privilege_id`),
    ADD KEY IF NOT EXISTS `FKatudhqb1d0u7cd08n1dhsmidd` (`role_name`);

--
-- Indexes for table `states`
--
ALTER TABLE `states`
    ADD PRIMARY KEY IF NOT EXISTS (`id`),
    ADD UNIQUE KEY IF NOT EXISTS `UK_nau09mwrvhjj0n0a6gfo5xmp3` (`name`),
    ADD KEY IF NOT EXISTS `FKskkdphjml9vjlrqn4m5hi251y` (`country_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
    ADD CONSTRAINT `FKhrpf5e8dwasvdc5cticysrt2k` FOREIGN KEY IF NOT EXISTS (`customer_id`) REFERENCES `customers` (`id`),
    ADD CONSTRAINT `FKn3sth7s3kur1rafwbbrqqnswt` FOREIGN KEY IF NOT EXISTS (`country_id`) REFERENCES `countries` (`id`),
    ADD CONSTRAINT `FKqcujk6a19pr29agrlk18h2c9y` FOREIGN KEY IF NOT EXISTS (`state_id`) REFERENCES `states` (`id`),
    ADD CONSTRAINT `FKqj9vg45qrhh7c29amn45m5bkt` FOREIGN KEY IF NOT EXISTS (`county_id`) REFERENCES `counties` (`id`);

--
-- Constraints for table `admins_roles`
--
ALTER TABLE `admins_roles`
    ADD CONSTRAINT `FK8535wewsp17vgt2scahieovxu` FOREIGN KEY IF NOT EXISTS (`admin_id`) REFERENCES `admins` (`id`),
    ADD CONSTRAINT `FKgl1rfe4yh5207gllc8g4a4eyl` FOREIGN KEY IF NOT EXISTS (`role_name`) REFERENCES `roles` (`name`);

--
-- Constraints for table `baskets`
--
ALTER TABLE `baskets`
    ADD CONSTRAINT `FKd9n51h863f9df2m0js5v3slv4` FOREIGN KEY IF NOT EXISTS (`customer_id`) REFERENCES `customers` (`id`);

--
-- Constraints for table `basket_items`
--
ALTER TABLE `basket_items`
    ADD CONSTRAINT `FKnx44qatthch57p2bgo873qfxu` FOREIGN KEY IF NOT EXISTS (`basket_id`) REFERENCES `baskets` (`id`),
    ADD CONSTRAINT `FKreybh8m8i647iovvot9grroeb` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products_value_attributes` (`id`);

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
    ADD CONSTRAINT `FK36j4852qk859jhclfoocn6i7k` FOREIGN KEY IF NOT EXISTS (`group_id`) REFERENCES `groups` (`id`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
    ADD CONSTRAINT `FK6uv0qku8gsu6x1r2jkrtqwjtn` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products` (`id`),
    ADD CONSTRAINT `FKev1bd87g1c51ujncao608e6qa` FOREIGN KEY IF NOT EXISTS (`customer_id`) REFERENCES `customers` (`id`);

--
-- Constraints for table `counties`
--
ALTER TABLE `counties`
    ADD CONSTRAINT `FKt9r2d00r3fjs8jl76tl8c7yjn` FOREIGN KEY IF NOT EXISTS (`state_id`) REFERENCES `states` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
    ADD CONSTRAINT `FKhlglkvf5i60dv6dn397ethgpt` FOREIGN KEY IF NOT EXISTS (`address_id`) REFERENCES `addresses` (`id`),
    ADD CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY IF NOT EXISTS (`customer_id`) REFERENCES `customers` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
    ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY IF NOT EXISTS (`order_id`) REFERENCES `orders` (`id`),
    ADD CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
    ADD CONSTRAINT `FKtk0w0k1mrif467aywwbccg7oo` FOREIGN KEY IF NOT EXISTS (`group_id`) REFERENCES `groups` (`id`);

--
-- Constraints for table `products_attributes`
--
ALTER TABLE `products_attributes`
    ADD CONSTRAINT `FKkq7j4hgd8pb1aj0eku3d04v4` FOREIGN KEY IF NOT EXISTS (`attribute_id`) REFERENCES `attributes` (`id`);

--
-- Constraints for table `products_attributes_product`
--
ALTER TABLE `products_attributes_product`
    ADD CONSTRAINT `FK7eiyy1gjur1qesj5gjj1xdi13` FOREIGN KEY IF NOT EXISTS (`product_non_value_attribute_id`) REFERENCES `products_attributes` (`id`),
    ADD CONSTRAINT `FKe189j89owtmhr8tfh5h6yclqu` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products_categories`
--
ALTER TABLE `products_categories`
    ADD CONSTRAINT `FKf3gerrr47lrha835uu0ip1uqn` FOREIGN KEY IF NOT EXISTS (`products_id`) REFERENCES `products` (`id`),
    ADD CONSTRAINT `FKr2i82ji875a3sdha76w2e2qyn` FOREIGN KEY IF NOT EXISTS (`categories_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `products_non_value_attributes`
--
ALTER TABLE `products_non_value_attributes`
    ADD CONSTRAINT `FKaiptqbj0ppvtmf449q90rp978` FOREIGN KEY IF NOT EXISTS (`non_value_attributes_id`) REFERENCES `products_attributes` (`id`),
    ADD CONSTRAINT `FKtlr0bf2kctc2rw28akwwauo65` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products_value_attributes`
--
ALTER TABLE `products_value_attributes`
    ADD CONSTRAINT `FK16jehbev92jgm1kki3pege07v` FOREIGN KEY IF NOT EXISTS (`product_id`) REFERENCES `products` (`id`),
    ADD CONSTRAINT `FKndrxve9qawp3f484lt4esjvmv` FOREIGN KEY IF NOT EXISTS (`attribute_id`) REFERENCES `attributes` (`id`);

--
-- Constraints for table `roles_privileges`
--
ALTER TABLE `roles_privileges`
    ADD CONSTRAINT `FKatudhqb1d0u7cd08n1dhsmidd` FOREIGN KEY IF NOT EXISTS (`role_name`) REFERENCES `roles` (`name`),
    ADD CONSTRAINT `FKimhxnr0rnrfnlq6qcdrr6k2ie` FOREIGN KEY IF NOT EXISTS (`privilege_id`) REFERENCES `privliages` (`id`);

--
-- Constraints for table `states`
--
ALTER TABLE `states`
    ADD CONSTRAINT `FKskkdphjml9vjlrqn4m5hi251y` FOREIGN KEY IF NOT EXISTS (`country_id`) REFERENCES `countries` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
