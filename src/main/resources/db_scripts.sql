CREATE SCHEMA `secure_store` ;
INSERT INTO `secure_store`.`global`(`keyword`,`value`) VALUES('DOCUMENT_PATH_PREFIX','C:/Users/HiteshDutta/Desktop/Personal');

INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Basic Vault','0.00','1','Active','20','GB','Lifetime');
INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Standard Safe','399.00','1','Active','50','GB','Yearly');
INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Premium Locker','799.00','1','Active','100','GB','Yearly');