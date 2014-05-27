-- -----------------------------------------------------
-- procedure to create index
-- -----------------------------------------------------


DROP PROCEDURE IF EXISTS komea.createIndex #

CREATE PROCEDURE komea.createIndex(in p_tableName VARCHAR(128), in p_indexName VARCHAR(128), in p_columnName VARCHAR(128) )
BEGIN

PREPARE stmt FROM 'SELECT @indexCount := COUNT(1) from information_schema.statistics WHERE `table_name` = ? AND `index_name` = ?';
SET @table_name = p_tableName;
SET @index_name = p_indexName;
EXECUTE stmt USING @table_name, @index_name;
DEALLOCATE PREPARE stmt;

-- select @indexCount;

IF( @indexCount = 0 ) THEN
  SELECT 'Creating index';
  SET @createIndexStmt = CONCAT('CREATE INDEX ', p_indexName, ' ON ', p_tableName, ' ( ', p_columnName ,')');
  PREPARE stmt FROM @createIndexStmt;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;
END IF;

END
#