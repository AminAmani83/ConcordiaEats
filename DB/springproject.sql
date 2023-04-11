-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema springproject
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `springproject` ;

-- -----------------------------------------------------
-- Schema springproject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springproject` DEFAULT CHARACTER SET utf8 ;
USE `springproject` ;

-- -----------------------------------------------------
-- Table `springproject`.`promotion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`promotion` ;

CREATE TABLE IF NOT EXISTS `springproject`.`promotion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NULL,
  `discountPercentage` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springproject`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`user` ;

CREATE TABLE IF NOT EXISTS `springproject`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `role` ENUM('CUSTOMER', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER',
  `email` VARCHAR(320) NOT NULL,
  `address` VARCHAR(1000) NULL,
  `phone` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `springproject`.`purchase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`purchase` ;

CREATE TABLE IF NOT EXISTS `springproject`.`purchase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `timeStamp` TIMESTAMP(6) NOT NULL,
  `total_price` DECIMAL(6,2) NOT NULL,
  `promotionId` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_purchase_promotions_idx` (`promotionId` ASC) INVISIBLE,
  INDEX `fk_purchase_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `FK_purchase_promotions`
    FOREIGN KEY (`promotionId`)
    REFERENCES `springproject`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `springproject`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springproject`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`category` ;

CREATE TABLE IF NOT EXISTS `springproject`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `springproject`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`product` ;

CREATE TABLE IF NOT EXISTS `springproject`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `imagePath` VARCHAR(255) NOT NULL,
  `categoryid` INT NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `salesCount` INT NULL,
  `isOnSale` TINYINT NULL,
  `discountPercent` DECIMAL NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_category1_idx` (`categoryid` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`categoryid`)
    REFERENCES `springproject`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `springproject`.`purchase_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`purchase_details` ;

CREATE TABLE IF NOT EXISTS `springproject`.`purchase_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `purchaseId` INT NULL,
  `productId` INT NOT NULL,
  `quantity` INT NULL,
  `price` DECIMAL(6,2) NULL,
  `isOnSale` TINYINT NULL,
  `discountPercent` DECIMAL NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_purchase_details_purchase_idx` (`purchaseId` ASC) VISIBLE,
  INDEX `fk_purchase_details_product1_idx` (`productId` ASC) VISIBLE,
  CONSTRAINT `FK_purchase_details_purchase`
    FOREIGN KEY (`purchaseId`)
    REFERENCES `springproject`.`purchase` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_details_product1`
    FOREIGN KEY (`productId`)
    REFERENCES `springproject`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springproject`.`favorite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`favorite` ;

CREATE TABLE IF NOT EXISTS `springproject`.`favorite` (
  `userId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`userId`, `productId`),
  INDEX `fk_favorite_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_favorite_product1_idx` (`productId` ASC) VISIBLE,
  CONSTRAINT `fk_favorite_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `springproject`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favorite_product1`
    FOREIGN KEY (`productId`)
    REFERENCES `springproject`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springproject`.`rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`rating` ;

CREATE TABLE IF NOT EXISTS `springproject`.`rating` (
  `userId` INT NOT NULL,
  `productId` INT NOT NULL,
  `rating` TINYINT(5) NULL,
  PRIMARY KEY (`userId`, `productId`),
  INDEX `fk_rating_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_rating_product1_idx` (`productId` ASC) VISIBLE,
  CONSTRAINT `fk_rating_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `springproject`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating_product1`
    FOREIGN KEY (`productId`)
    REFERENCES `springproject`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springproject`.`search_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`search_history` ;

CREATE TABLE IF NOT EXISTS `springproject`.`search_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `phrase` VARCHAR(255) NOT NULL,
  `timeStamp` TIMESTAMP(6) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_search_history_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_search_history_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `springproject`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `springproject` ;

-- -----------------------------------------------------
-- Placeholder table for view `springproject`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springproject`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `springproject`.`view1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `springproject`.`view1`;
DROP VIEW IF EXISTS `springproject`.`view1` ;
USE `springproject`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
