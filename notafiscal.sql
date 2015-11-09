SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema notafiscal
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `notafiscal` DEFAULT CHARACTER SET utf8 ;

USE `notafiscal` ;

-- -----------------------------------------------------
-- Table `notafiscal`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notafiscal`.`customers` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `email` VARCHAR(50) NULL DEFAULT NULL,
  `cpf` VARCHAR(50) NOT NULL DEFAULT '',
  `address` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `notafiscal`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notafiscal`.`products` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL DEFAULT '',
  `price` INT(11) NOT NULL,
  `stock` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `notafiscal`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notafiscal`.`orders` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` DATE NULL DEFAULT NULL,
  `customers_id` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_customers1_idx` (`customers_id` ASC),
  CONSTRAINT `fk_orders_customers1`
    FOREIGN KEY (`customers_id`)
    REFERENCES `notafiscal`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `notafiscal`.`order_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notafiscal`.`order_products` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `quantity` INT(11) NOT NULL,
  `price` INT(11) NOT NULL,
  `products_id` INT(11) UNSIGNED NOT NULL,
  `orders_id` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_products_products_idx` (`products_id` ASC),
  INDEX `fk_order_products_orders1_idx` (`orders_id` ASC),
  CONSTRAINT `fk_order_products_products`
    FOREIGN KEY (`products_id`)
    REFERENCES `notafiscal`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_products_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `notafiscal`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `notafiscal`.`outgoing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notafiscal`.`outgoing` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL DEFAULT '',
  `price` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
