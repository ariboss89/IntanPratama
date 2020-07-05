# Host: localhost  (Version: 5.5.8)
# Date: 2020-07-05 10:00:17
# Generator: MySQL-Front 5.3  (Build 4.81)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "tb_admin"
#

DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `username` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) DEFAULT NULL,
  `konfirmasi` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tb_admin"
#

INSERT INTO `tb_admin` VALUES ('steven123','12345','12345','admin');

#
# Structure for table "tb_kategori"
#

DROP TABLE IF EXISTS `tb_kategori`;
CREATE TABLE `tb_kategori` (
  `kategori` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`kategori`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tb_kategori"
#

INSERT INTO `tb_kategori` VALUES ('Keramik'),('Manise'),('Pot');

#
# Structure for table "tb_barang"
#

DROP TABLE IF EXISTS `tb_barang`;
CREATE TABLE `tb_barang` (
  `idbarang` varchar(11) NOT NULL DEFAULT '',
  `nama` varchar(255) DEFAULT NULL,
  `kategori` varchar(50) DEFAULT NULL,
  `satuan` varchar(255) DEFAULT NULL,
  `hargabeli` int(11) DEFAULT NULL,
  `hargajual` int(11) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `minstok` int(11) DEFAULT NULL,
  PRIMARY KEY (`idbarang`),
  KEY `kategori` (`kategori`),
  CONSTRAINT `tb_barang_ibfk_1` FOREIGN KEY (`kategori`) REFERENCES `tb_kategori` (`kategori`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tb_barang"
#

INSERT INTO `tb_barang` VALUES ('B0001','Keramik Manise','Keramik',NULL,10000,20000,0,8),('B0002','Keramik Batu','Keramik',NULL,20000,30000,110,10),('B0003','Keramik Buah-Buahan','Keramik',NULL,20000,25000,30,20),('B0004','Topless','Pot',NULL,10000,12000,165,10),('B0005','Semen Tiga Roda','Manise','SAK',60000,70000,200,5);

#
# Structure for table "dt_masuk"
#

DROP TABLE IF EXISTS `dt_masuk`;
CREATE TABLE `dt_masuk` (
  `iddetailmasuk` varchar(20) NOT NULL DEFAULT '',
  `idmasuk` varchar(20) DEFAULT NULL,
  `idbarang` varchar(20) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetailmasuk`),
  KEY `idmasuk` (`idmasuk`),
  KEY `idbarang` (`idbarang`),
  CONSTRAINT `dt_masuk_ibfk_1` FOREIGN KEY (`idbarang`) REFERENCES `tb_barang` (`idbarang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "dt_masuk"
#

INSERT INTO `dt_masuk` VALUES ('DT-IN0001','IN-0001','B0001',4),('DT-IN0002','IN-0002','B0001',6),('DT-IN0003','IN-0002','B0001',5),('DT-IN0004','IN-0002','B0001',7),('DT-IN0005','IN-0003','B0001',4),('DT-IN0006','IN-0003','B0002',5),('DT-IN0007','IN-0004','B0001',4),('DT-IN0008','IN-0005','B0004',100),('DT-IN0009','IN-0006','B0003',45),('DT-IN0010','IN-0006','B0001',50);

#
# Structure for table "tb_pelanggan"
#

DROP TABLE IF EXISTS `tb_pelanggan`;
CREATE TABLE `tb_pelanggan` (
  `idpelanggan` varchar(11) NOT NULL DEFAULT '',
  `nama` varchar(50) DEFAULT NULL,
  `kontak` varchar(12) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tb_pelanggan"
#


#
# Structure for table "tb_supplier"
#

DROP TABLE IF EXISTS `tb_supplier`;
CREATE TABLE `tb_supplier` (
  `idsupplier` varchar(11) NOT NULL DEFAULT '0',
  `nama` varchar(50) DEFAULT NULL,
  `kontak` varchar(12) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idsupplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tb_supplier"
#

INSERT INTO `tb_supplier` VALUES ('S0001','Cv. Tuah Jaya Perdana','081277618851','Jl. Ganet Perum Catur No,208'),('S0002','PT. Utama','980101011','Batu 2');

#
# Structure for table "tr_barang"
#

DROP TABLE IF EXISTS `tr_barang`;
CREATE TABLE `tr_barang` (
  `idtransaksibarang` varchar(11) NOT NULL DEFAULT '',
  `idbarang` varchar(11) DEFAULT NULL,
  `kategori` varchar(50) DEFAULT NULL,
  `satuan` varchar(255) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `idsupplier` varchar(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtransaksibarang`),
  KEY `idbarang` (`idbarang`),
  KEY `kategori` (`kategori`),
  KEY `idsupplier` (`idsupplier`),
  CONSTRAINT `tr_barang_ibfk_1` FOREIGN KEY (`idbarang`) REFERENCES `tb_barang` (`idbarang`),
  CONSTRAINT `tr_barang_ibfk_2` FOREIGN KEY (`kategori`) REFERENCES `tb_kategori` (`kategori`),
  CONSTRAINT `tr_barang_ibfk_3` FOREIGN KEY (`idsupplier`) REFERENCES `tb_supplier` (`idsupplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tr_barang"
#

INSERT INTO `tr_barang` VALUES ('TRB0001','B0003','Keramik',NULL,'2019-12-29','S0001',10),('TRB0002','B0004','Pot',NULL,'2019-12-29','S0002',100),('TRB0003','B0004','Pot',NULL,'2019-12-29','S0002',45),('TRB0004','B0004','Pot',NULL,'2019-12-29','S0002',10),('TRB0005','B0004','Pot',NULL,'2020-04-03','S0002',10),('TRB0006','B0005','Manise','SAK','2020-07-04','S0002',200);

#
# Structure for table "tr_keluar"
#

DROP TABLE IF EXISTS `tr_keluar`;
CREATE TABLE `tr_keluar` (
  `idkeluar` varchar(11) NOT NULL DEFAULT '',
  `tanggal` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `bayar` int(11) DEFAULT NULL,
  `kembalian` int(11) DEFAULT NULL,
  `idpelanggan` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`idkeluar`),
  KEY `idpelanggan` (`idpelanggan`),
  CONSTRAINT `tr_keluar_ibfk_1` FOREIGN KEY (`idpelanggan`) REFERENCES `tb_pelanggan` (`idpelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tr_keluar"
#


#
# Structure for table "dt_keluar"
#

DROP TABLE IF EXISTS `dt_keluar`;
CREATE TABLE `dt_keluar` (
  `iddetailkeluar` varchar(11) NOT NULL DEFAULT '',
  `idkeluar` varchar(11) DEFAULT NULL,
  `idbarang` varchar(11) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetailkeluar`),
  KEY `idkeluar` (`idkeluar`),
  KEY `idbarang` (`idbarang`),
  CONSTRAINT `dt_keluar_ibfk_1` FOREIGN KEY (`idkeluar`) REFERENCES `tr_keluar` (`idkeluar`),
  CONSTRAINT `dt_keluar_ibfk_2` FOREIGN KEY (`idbarang`) REFERENCES `tb_barang` (`idbarang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "dt_keluar"
#


#
# Structure for table "tr_masuk"
#

DROP TABLE IF EXISTS `tr_masuk`;
CREATE TABLE `tr_masuk` (
  `idmasuk` varchar(11) NOT NULL DEFAULT '',
  `tanggal` date DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `idsupplier` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`idmasuk`),
  KEY `idsupplier` (`idsupplier`),
  CONSTRAINT `tr_masuk_ibfk_1` FOREIGN KEY (`idsupplier`) REFERENCES `tb_supplier` (`idsupplier`),
  CONSTRAINT `tr_masuk_ibfk_2` FOREIGN KEY (`idmasuk`) REFERENCES `dt_masuk` (`idmasuk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "tr_masuk"
#

INSERT INTO `tr_masuk` VALUES ('IN-0001','2020-03-12',4,'S0001'),('IN-0002','2020-03-12',17,'S0001'),('IN-0003','2020-03-12',9,'S0001'),('IN-0004','2020-03-14',4,'S0001'),('IN-0005','2020-04-03',100,'S0002'),('IN-0006','2020-04-03',95,'S0001');
