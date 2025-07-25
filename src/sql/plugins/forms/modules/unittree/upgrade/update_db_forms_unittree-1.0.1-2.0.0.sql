--liquibase formatted sql
--changeset forms-unittree:update_db_forms_unittree-1.0.1-2.0.0.sql
--preconditions onFail:MARK_RAN onError:WARN
DROP TABLE IF EXISTS forms_unittree_unit_selection_config;
CREATE TABLE forms_unittree_unit_selection_config
(
	id_config INT AUTO_INCREMENT,
	id_form INT DEFAULT 0 NOT NULL,
	id_task INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (id_config)
);

DROP TABLE IF EXISTS forms_unittree_unit_selection_config_value;
CREATE TABLE forms_unittree_unit_selection_config_value
(
	id_config_value INT AUTO_INCREMENT,
	id_config INT DEFAULT 0 NOT NULL,
	id_step INT DEFAULT 0 NOT NULL,
	id_question INT DEFAULT 0 NOT NULL,
	response_value VARCHAR(255),
	id_unit INT DEFAULT 0 NOT NULL,
	id_order INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (id_config_value)
);
CREATE INDEX index_unit_selection_conf_value ON forms_unittree_unit_selection_config_value ( id_config );