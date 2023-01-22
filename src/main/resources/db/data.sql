INSERT IGNORE INTO `roles`(`name`) VALUES ('ROOT');
INSERT IGNORE INTO `roles`(`name`) VALUES ('ADMIN');
INSERT IGNORE INTO `admins`(`id`, `name`, `email`, `password`, `created_at`, `updated_at`) VALUES ('root', 'root', 'root@ecommercy.app', '$2a$10$EyzI4OBNGETy.Q/QutqK8e9r1nU252yFcIWDM0AjOwvh1Q2949OiW', now(), now());
INSERT IGNORE INTO `admins_roles`(`admin_id`, `role_name`) VALUES ('root', 'ROOT');
INSERT IGNORE INTO `admins_roles`(`admin_id`, `role_name`) VALUES ('root', 'ADMIN');