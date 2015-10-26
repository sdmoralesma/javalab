-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema labdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `labdb` ;

-- -----------------------------------------------------
-- Schema labdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `labdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `labdb` ;

-- -----------------------------------------------------
-- Table `labdb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `labdb`.`User` ;

CREATE TABLE IF NOT EXISTS `labdb`.`User` (
  `id` INT NOT NULL COMMENT '',
  `username` VARCHAR(45) NULL COMMENT '',
  `password` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labdb`.`Workspace`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `labdb`.`Workspace` ;

CREATE TABLE IF NOT EXISTS `labdb`.`Workspace` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `json` TEXT NULL COMMENT '',
  `description` TEXT NULL COMMENT '',
  `User_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_Workspace_User1_idx` (`User_id` ASC)  COMMENT '',
  CONSTRAINT `fk_Workspace_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `labdb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labdb`.`Tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `labdb`.`Tag` ;

CREATE TABLE IF NOT EXISTS `labdb`.`Tag` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labdb`.`Workspace_Tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `labdb`.`Workspace_Tag` ;

CREATE TABLE IF NOT EXISTS `labdb`.`Workspace_Tag` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `id_workspace` INT NOT NULL COMMENT '',
  `id_tag` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_Workspace_Tag_Workspace_idx` (`id_workspace` ASC)  COMMENT '',
  INDEX `fk_Workspace_Tag_Tag1_idx` (`id_tag` ASC)  COMMENT '',
  CONSTRAINT `fk_Workspace_Tag_Workspace`
    FOREIGN KEY (`id_workspace`)
    REFERENCES `labdb`.`Workspace` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Workspace_Tag_Tag1`
    FOREIGN KEY (`id_tag`)
    REFERENCES `labdb`.`Tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

