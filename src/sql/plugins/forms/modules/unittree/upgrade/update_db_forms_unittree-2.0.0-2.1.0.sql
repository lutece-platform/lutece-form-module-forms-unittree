ALTER TABLE forms_unittree_unit_selection_config ADD COLUMN is_multiform SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE forms_unittree_unit_selection_config_value ADD COLUMN code VARCHAR(100);