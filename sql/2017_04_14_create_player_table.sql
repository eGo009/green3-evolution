CREATE TABLE evolutiondb.ev_g_player (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id varchar(255) NOT NULL,
  game_id int(11) NOT NULL,
  stage_order int(11) DEFAULT NULL,
  round_order int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 8
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;