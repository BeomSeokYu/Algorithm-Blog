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

CREATE TABLE `algorithm_category` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name_ko` varchar(100) NOT NULL,
    `name_en` varchar(150) NOT NULL,
    `source` varchar(30) DEFAULT NULL,
    `problem_count` int DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `article_category` (
    `article_id` int unsigned NOT NULL,
    `category_id` int unsigned NOT NULL,
    PRIMARY KEY (`article_id`, `category_id`),
    CONSTRAINT `fk_article_category_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_article_category_category` FOREIGN KEY (`category_id`) REFERENCES `algorithm_category` (`id`) ON DELETE CASCADE
);
