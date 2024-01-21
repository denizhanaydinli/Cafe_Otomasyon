CREATE TABLE status (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `masa_no` int(11) DEFAULT NULL,
  `durum` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=596 DEFAULT CHARSET=utf8$$

CREATE TABLE `satis_ayrinti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `satis_id` int(11) DEFAULT NULL,
  `urun_id` int(11) DEFAULT NULL,
  `adet` int(11) DEFAULT NULL,
  `DateCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=721 DEFAULT CHARSET=utf8$$

CREATE TABLE `satis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tarih` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `iptal` int(11) DEFAULT '0',
  `masa_no` int(11) DEFAULT NULL,
  `fiyat` decimal(8,2) DEFAULT '0.00',
  `status` int(11) DEFAULT '0',
  `indirim` decimal(8,2) DEFAULT '0.00',
  `kapanisTarih` timestamp NULL DEFAULT NULL,
  `adisyon_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8$$

CREATE DATABASE `satis`
CREATE TABLE `urun` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `urun_adi` varchar(150) DEFAULT NULL,
  `fiyat` decimal(8,2) DEFAULT '10.00',
  `status` int(11) DEFAULT '0',
  `urun_kategori_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) 
CREATE TABLE `urun_kategori` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kategori_adi` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
)