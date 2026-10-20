-- liquibase formatted sql
-- changeset forms-unittree:update_db_forms_unittree-3.0.1-3.0.2.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- comment decodes, once, the HTML entities the removed input filter of lutece-core 8 (sanitizeFilterMode, LUT-34109) wrote into the plain text values: the response values the unit selection compares with the form responses (genericattributes decodes the responses: the values must follow, or the selection no longer matches). The question code is left as it is.
-- This changeset rewrites the data of the site. Back up the database before the upgrade. It must run ONCE only: run
-- again, it would decode a second time a text that really holds an entity (a typed &lt; stored as &amp;lt; becomes &lt;
-- once, then < the second time). Only the values matching LIKE '%&%;%' are concerned; to list them before the upgrade,
-- run the WHERE clauses below in a SELECT.
UPDATE forms_unittree_unit_selection_config_value SET response_value = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(response_value, '&#39;', ''''), '&#x27;', ''''), '&#34;', '"'), '&quot;', '"'), '&#96;', '`'), '&#61;', '='), '&#64;', '@'), '&#43;', '+'), '&lt;', '<'), '&gt;', '>'), '&amp;', '&') WHERE response_value LIKE '%&%;%';
