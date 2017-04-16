CREATE TABLE evolutiondb.ev_g_player_card (
  id int(11) NOT NULL AUTO_INCREMENT,
  player_id int(11) DEFAULT NULL,
  card_id int(11) NOT NULL,
  status int(11) NOT NULL,
  game_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;