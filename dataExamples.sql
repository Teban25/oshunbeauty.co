INSERT INTO
"PUBLIC"."BRANDS"("BRAND_ID","COMPANY_NAME","CREATION_DATE","CREATION_USER","DESCRIPTION",
"LAST_MODIFIED_DATE","LAST_MODIFIED_USER")
VALUES
(2,'test1',parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'),'david','descp',
parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'),'david');



INSERT INTO "PUBLIC"."PRODUCTS"("PRODUCT_ID","BARCODE","CREATION_DATE","CREATION_USER","CURRENT_AMOUNT","CURRENT_PRICE"
,"DESCRIPTION","LAST_MODIFIED_DATE","LAST_MODIFIED_USER","NAME","BRAND_ID")
VALUES(nextval('SEQ_PRODUCT_PK'),'testBarCode',parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'),'david',0,0,'test desc',
parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'),'david','Sombras',1);