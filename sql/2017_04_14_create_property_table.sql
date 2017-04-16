CREATE TABLE evolutiondb.ev_g_property (
  id varchar(255) NOT NULL,
  type int(11) NOT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX description (description),
  UNIQUE INDEX type (type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;