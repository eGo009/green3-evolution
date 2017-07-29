CREATE TABLE evolutiondb.ev_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  login varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  email varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;