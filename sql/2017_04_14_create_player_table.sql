﻿CREATE TABLE evolutiondb.ev_g_player (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id varchar(255) NOT NULL,
  game_id int(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;