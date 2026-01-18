--------------------------------------------------------------------------------------
-- Drop tables                                                                      --
--------------------------------------------------------------------------------------
DROP TABLE IF EXISTS CONTACT;

--------------------------------------------------------------------------------------
-- Drop indexes                                                                     --
--------------------------------------------------------------------------------------
DROP INDEX IF EXISTS X1_CONTACT;

--------------------------------------------------------------------------------------
-- Create tables                                                                    --
--------------------------------------------------------------------------------------
CREATE TABLE CONTACT
(
    ID                           SERIAL NOT NULL PRIMARY KEY,
    VERSION                      BIGINT,
    FIRST_NAME                   VARCHAR(30) NOT NULL,
    LAST_NAME                    VARCHAR(30) NOT NULL,
    ADDRESS_1                    VARCHAR(30) NOT NULL,
    ADDRESS_2                    VARCHAR(30) DEFAULT NULL,
    ADDRESS_3                    VARCHAR(30) DEFAULT NULL,
    ZIPCODE                      VARCHAR(20) NOT NULL,
    CITY                         VARCHAR(30) NOT NULL,
    STATE                        VARCHAR(30) DEFAULT NULL,
    PHONE                        VARCHAR(25) DEFAULT NULL,
    EMAIL                        VARCHAR(50) DEFAULT NULL,
    IBAN                         VARCHAR(34) NOT NULL,
    SOCIAL_SECURITY_NUMBER       VARCHAR(25) DEFAULT NULL,
    DATE_OF_DEATH                DATE DEFAULT NULL,
    DATE_OF_BIRTH                DATE NOT NULL
);

--------------------------------------------------------------------------------------
-- Create indexes                                                                   --
--------------------------------------------------------------------------------------
CREATE UNIQUE INDEX X1_CONTACT ON
    CONTACT
        (ID ASC);
