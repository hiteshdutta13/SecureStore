CREATE SCHEMA `secure_store` ;
INSERT INTO `secure_store`.`global`(`keyword`,`value`) VALUES('DOCUMENT_PATH_PREFIX','C:/Users/HiteshDutta/Desktop/Personal');

INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Basic','0.00','0','Active','2','Gigabyte','Lifetime');
INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Pro','399.00','1','Active','50','Gigabyte','Yearly');
INSERT INTO `secure_store`.`plan`(NAME, PRICE, SHARE, STATUS, STORAGE, STORAGE_TYPE, PRICING_TYPE) VALUES('Premium','599.00','1','Active','100','Gigabyte','Yearly');