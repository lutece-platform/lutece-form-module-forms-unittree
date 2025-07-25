--liquibase formatted sql
--changeset forms-unittree:update_db_forms_unittree-2.0.0-2.1.0.sql
--preconditions onFail:MARK_RAN onError:WARN
ALTER TABLE forms_unittree_unit_selection_config ADD COLUMN is_multiform SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE forms_unittree_unit_selection_config_value ADD COLUMN code VARCHAR(100);