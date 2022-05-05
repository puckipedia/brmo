-- 
-- upgrade PostgreSQL RSGB datamodel van 2.3.0 naar 2.4.0 
--

set search_path = public,bag;


-- onderstaande dienen als laatste stappen van een upgrade uitgevoerd
INSERT INTO brmo_metadata (naam,waarde) SELECT 'upgrade_2.3.0_naar_2.4.0','vorige versie was ' || waarde FROM brmo_metadata WHERE naam='brmoversie';
-- versienummer update
UPDATE brmo_metadata SET waarde='2.4.0' WHERE naam='brmoversie';
