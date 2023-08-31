CREATE TABLE `users` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `email` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    `nickname` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_uni` (`email`),
    UNIQUE KEY `nickname_uni` (`nickname`)
);

CREATE TABLE `refresh` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(50) NOT NULL,
    `refresh_token` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_id_uni` (`user_id`)
);

CREATE TABLE `article` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `content` longtext NOT NULL,
    `author` varchar(50) NOT NULL,
    `type` varchar(10) NOT NULL,
    `created_at` datetime default NULL,
    `updated_at` datetime default NULL,
    PRIMARY KEY (`id`)
);