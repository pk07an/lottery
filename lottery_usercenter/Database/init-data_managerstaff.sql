prompt PL/SQL Developer import file
prompt Created on 2013年9月8日 by Administrator
set feedback off
set define off
prompt Loading TB_FRAME_MANAGER_STAFF...
insert into TB_FRAME_MANAGER_STAFF (ID, ACCOUNT, FLAG, USER_TYPE, USER_EXT_INFO_ID, USER_PWD, CHS_NAME, ENG_NAME, EMAIL_ADDR, OFFICE_PHONE, MOBILE_PHONE, FAX, CREATE_DATE, UPDATE_DATE, LOGIN_DATE, LOGIN_IP, COMMENTS, PARENT_STAFF_QRY, PARENT_STAFF_TYPE_QRY, PRE_FLAG, PASSWORD_UPDATE_DATE, PASSWORD_RESET_FLAG)
values (1, 'system', '0', '0', 8, 'E155297D2A6ECB067C74A74F5338962C', '系统管理员', 'system', null, null, null, null, to_date('05-03-2012', 'dd-mm-yyyy'), to_date('09-04-2013 14:25:55', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, '0', to_date('06-05-2013', 'dd-mm-yyyy'), 'Y');
commit;
prompt 1 records loaded
set feedback on
set define on
prompt Done.
