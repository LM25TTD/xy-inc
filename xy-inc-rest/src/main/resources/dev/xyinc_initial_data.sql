-- ACCOUNT
INSERT INTO ACCOUNT
(ID, LOGIN  , PASSWORD                                                       , ACCOUNT_ROLE) VALUES
(1 , 'ADMIN', '$2a$06$LGPFJVassAw0.81NI7.u6OXT.AKmxOA6/EZyV4ylvb4VuJjkh/Xcy' , 'ADMIN'),
(2 , 'USER1', '$2a$06$LGPFJVassAw0.81NI7.u6OXT.AKmxOA6/EZyV4ylvb4VuJjkh/Xcy' , 'NON_ADMIN'),
(3 , 'USER2', '$2a$06$LGPFJVassAw0.81NI7.u6OXT.AKmxOA6/EZyV4ylvb4VuJjkh/Xcy' , 'NON_ADMIN');

-- PRODUCT
INSERT INTO PRODUCT
(ID, NAME       , DESCRIPTION                                     , PRICE , CATEGORY) VALUES
(1 , 'ProdOne'  , 'Product of type one, with premium features'    , 1.78  , 'HOME'),
(2 , 'ProdTwo'  , 'Product of type two, with all premium features', 3.78  , 'HEALTH'),
(3 , 'ProdThree', 'Product of type three, with  premium features' , 2.59  , 'SMARTPHONE'),
(4 , 'ProdFour' , 'Product of type four, with no premium features', 12.59 , 'PET'),
(5 , 'ProdFive' , 'Product of type five, with no premium features', 442.59, 'GOLF');