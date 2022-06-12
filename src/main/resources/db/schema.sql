CREATE TABLE IF NOT EXISTS `admins` (
                          `id` varchar(255) NOT NULL,
                          `created_at` datetime NOT NULL,
                          `email` varchar(255) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `password` varchar(255) NOT NULL,
                          `roles` varchar(255) DEFAULT NULL,
                          `updated_at` datetime NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `users` (
                         `id` varchar(255) NOT NULL,
                         `created_at` datetime NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `last_name` varchar(255) DEFAULT NULL,
                         `name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `phone_number` varchar(255) DEFAULT NULL,
                         `points` int(11) NOT NULL,
                         `roles` varchar(255) DEFAULT NULL,
                         `updated_at` datetime NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY (`email`),
                         UNIQUE KEY (`phone_number`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `countries` (
                                           `id` varchar(255) NOT NULL,
                                           `name` varchar(255) NOT NULL,
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `states` (
                                        `id` varchar(255) NOT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `country_id` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY (`country_id`),
                                        FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `cities` (
                                        `id` varchar(255) NOT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `state_id` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY (`state_id`),
                                        FOREIGN KEY (`state_id`) REFERENCES `states` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `addresses` (
                             `id` varchar(255) NOT NULL,
                             `address_line1` varchar(255) NOT NULL,
                             `address_line2` varchar(255) DEFAULT NULL,
                             `building` varchar(255) NOT NULL,
                             `house_number` varchar(255) NOT NULL,
                             `zip_code` varchar(255) NOT NULL,
                             `city_id` varchar(255) NOT NULL,
                             `country_id` varchar(255) NOT NULL,
                             `state_id` varchar(255) NOT NULL,
                             `user_id` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY (`user_id`),
                             KEY (`city_id`),
                             KEY (`country_id`),
                             KEY (`state_id`),
                             FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                             FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
                             FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
                             FOREIGN KEY (`state_id`) REFERENCES `states` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `baskets` (
                           `id` varchar(255) NOT NULL,
                           `discount` double NOT NULL,
                           `total_price` double NOT NULL,
                           `user_id` varchar(255) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY (`user_id`),
                           FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `groups` (
                                        `id` varchar(255) NOT NULL,
                                        `description` varchar(255) DEFAULT NULL,
                                        `name` varchar(255) NOT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `categories` (
                                            `id` varchar(255) NOT NULL,
                                            `description` varchar(255) DEFAULT NULL,
                                            `name` varchar(255) NOT NULL,
                                            `group_id` varchar(255) NOT NULL,
                                            PRIMARY KEY (`id`),
                                            KEY (`group_id`),
                                            FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `products` (
                                          `id` varchar(255) NOT NULL,
                                          `created_at` datetime NOT NULL,
                                          `description` varchar(255) DEFAULT NULL,
                                          `discount` float NOT NULL,
                                          `inventory` int(11) NOT NULL,
                                          `name` varchar(255) NOT NULL,
                                          `point` int(11) NOT NULL,
                                          `price` float NOT NULL,
                                          `stars` float NOT NULL,
                                          `updated_at` datetime NOT NULL,
                                          `group_id` varchar(255) NOT NULL,
                                          PRIMARY KEY (`id`),
                                          KEY (`group_id`),
                                          FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `products_categories` (
                                                     `products_id` varchar(255) NOT NULL,
                                                     `categories_id` varchar(255) NOT NULL,
                                                     KEY (`categories_id`),
                                                     KEY (`products_id`),
                                                     FOREIGN KEY (`products_id`) REFERENCES `products` (`id`),
                                                     FOREIGN KEY (`categories_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `basketitems` (
                               `id` varchar(255) NOT NULL,
                               `quantity` int(11) NOT NULL,
                               `basket_id` varchar(255) NOT NULL,
                               `product_id` varchar(255) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY (`basket_id`),
                               KEY (`product_id`),
                               FOREIGN KEY (`basket_id`) REFERENCES `baskets` (`id`),
                               FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `comments` (
                            `id` varchar(255) NOT NULL,
                            `comment` varchar(255) DEFAULT NULL,
                            `created_at` datetime NOT NULL,
                            `star` int(11) NOT NULL,
                            `updated_at` datetime NOT NULL,
                            `product_id` varchar(255) NOT NULL,
                            `user_id` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY (`product_id`),
                            KEY (`user_id`),
                            FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `orders` (
                          `id` varchar(255) NOT NULL,
                          `discount` double NOT NULL,
                          `total_price` double NOT NULL,
                          `address_id` varchar(255) DEFAULT NULL,
                          `user_id` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY (`address_id`),
                          KEY (`user_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                          FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `orderitems` (
                              `id` varchar(255) NOT NULL,
                              `quantity` int(11) NOT NULL,
                              `order_id` varchar(255) NOT NULL,
                              `product_id` varchar(255) NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY (`order_id`),
                              KEY (`product_id`),
                              FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                              FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB;