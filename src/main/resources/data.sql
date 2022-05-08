INSERT IGNORE INTO `countries`(`id`, `name`) VALUES ('1', 'ایران');
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES ('1', 'گیلان', '1');
INSERT IGNORE INTO `cities` (`id`, `name`, `state_id`) VALUES ('1', 'صومعه سرا', '1');