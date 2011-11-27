USE check-please2;

DROP TABLE IF EXISTS `check-please2`.`Uncommitted_Orders_Items`;
DROP TABLE IF EXISTS `check-please2`.`Uncommitted_Orders`;
DROP TABLE IF EXISTS `check-please2`.`Committed_Orders_Items`;
DROP TABLE IF EXISTS `check-please2`.`Committed_Orders`;
DROP TABLE IF EXISTS `check-please2`.`Items`;
DROP TABLE IF EXISTS `check-please2`.`Stores`;
DROP TABLE IF EXISTS `check-please2`.`Customers`;

CREATE TABLE `check-please2`.`Customers`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (`id`),
    
    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `username` varchar(255) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `firstName` VARCHAR(255) NOT NULL,
    `lastName` VARCHAR(255) NOT NULL,
    `balance` DOUBLE NOT NULL
    
) ENGINE=InnoDB;

CREATE UNIQUE INDEX `uname_index` ON `check-please2`.`Customers` (`username`) USING BTREE;
CREATE UNIQUE INDEX `uemail_index` ON `check-please2`.`Customers` (`email`) USING BTREE;

CREATE TABLE `check-please2`.`Stores`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (`id`),
    
    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(5) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `latitude` DOUBLE NOT NULL DEFAULT 0.0,
    `longitude` DOUBLE NOT NULL DEFAULT 0.0,
    `phoneNumber` VARCHAR(20) NOT NULL,
    `pictureLink` VARCHAR(2083) NOT NULL
    
) ENGINE=InnoDB;

CREATE TABLE `check-please2`.`Items`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    
    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0,
    `etp` BIGINT UNSIGNED NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `pictureLink` VARCHAR(2083) NOT NULL,
    `price` DOUBLE NOT NULL,
    
    `store_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`store_id`)
        REFERENCES `check-please2`.`Stores`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE
        
) ENGINE=InnoDB;

CREATE TABLE `check-please2`.`Committed_Orders`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    
    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0,
    `etp` BIGINT UNSIGNED NOT NULL,
    `pickupTime` DATETIME NOT NULL,
    `price` DOUBLE UNSIGNED NOT NULL,
    
    `customer_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`customer_id`)
        REFERENCES `check-please2`.`Customers`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    
    `store_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`store_id`)
        REFERENCES `check-please2`.`Stores`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE
        
) ENGINE=InnoDB;

CREATE INDEX `corders_put_index` ON `check-please2`.`Committed_Orders` (`pickupTime`) USING BTREE;

CREATE TABLE `check-please2`.`Committed_Orders_Items`
(


    `item_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`item_id`)
        REFERENCES `check-please2`.`Items`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
        
    `order_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`order_id`)
        REFERENCES `check-please2`.`Committed_Orders`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    
    `items_idx` INT(11)
    
) ENGINE=InnoDB;

CREATE TABLE `check-please2`.`Uncommitted_Orders`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    
    `version` BIGINT UNSIGNED NOT NULL DEFAULT 0,
    `etp` BIGINT UNSIGNED NOT NULL,
    `scheduleDay` INT NOT NULL DEFAULT 0, 
    `pickupTime` DATETIME NOT NULL,
    `price` DOUBLE UNSIGNED NOT NULL,
    
    `customer_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`customer_id`)
        REFERENCES `check-please2`.`Customers`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    
    `store_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`store_id`)
        REFERENCES `check-please2`.`Stores`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE
        
) ENGINE=InnoDB;

CREATE UNIQUE INDEX `uorders_id_index` ON `check-please2`.`Uncommitted_Orders` (`id`) USING BTREE;
CREATE INDEX `uorders_put_index` ON `check-please2`.`Uncommitted_Orders` (`pickupTime`) USING BTREE;

CREATE TABLE `check-please2`.`Uncommitted_Orders_Items`
(
    `item_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`item_id`)
        REFERENCES `check-please2`.`Items`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    
    `order_id` BIGINT UNSIGNED NOT NULL,
        FOREIGN KEY (`order_id`)
        REFERENCES `check-please2`.`Uncommitted_Orders`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    
    `items_idx` INT(11)
    
) ENGINE=InnoDB;
