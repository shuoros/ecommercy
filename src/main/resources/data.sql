INSERT IGNORE INTO `admins`(`id`, `name`, `email`, `password`, `roles`, `created_at`, `updated_at`) VALUES ('root', 'root', 'root@ecommercy.app', '$2a$10$EyzI4OBNGETy.Q/QutqK8e9r1nU252yFcIWDM0AjOwvh1Q2949OiW', 'ROOT;ADMIN', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i:%s'), DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i:%s'));
INSERT IGNORE INTO `countries`(`id`, `name`) VALUES ('1', 'ایران');
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES ('1', 'گیلان', '1');
INSERT IGNORE INTO `cities` (`id`, `name`, `state_id`) VALUES ('1', 'صومعه سرا', '1');