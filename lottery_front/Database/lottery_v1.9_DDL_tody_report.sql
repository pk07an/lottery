-- Create table
create table TB_S_REPORT_PET_PERIOD
(
  ID                           NUMBER(10),
  BETTING_USER_ID              NUMBER(10),
  BETTING_USER_TYPE            CHAR(1),
  PARENT_USER_TYPE             CHAR(1),
  ACCOUNT                      NVARCHAR2(50),
  CHNAME                       NVARCHAR2(50),
  COUNT                        NUMBER(10),
  TOTAL_MONEY                  NUMBER,
  MONEY_RATE_AGENT             NUMBER,
  MONEY_RATE_GENAGENT          NUMBER,
  MONEY_RATE_STOCKHOLDER       NUMBER,
  MONEY_RATE_BRANCH            NUMBER,
  MONEY_RATE_CHIEF             NUMBER,
  RATE_MONEY                   NUMBER,
  MEMBER_AMOUNT                NUMBER,
  SUBORDINATE_AMOUNT_WIN       NUMBER,
  SUBORDINATE_AMOUNT_BACKWATER NUMBER,
  REALWIN                      NUMBER,
  REAL_BACKWATER               NUMBER,
  COMMISSION                   NUMBER,
  PERIODS_NUM                  VARCHAR2(11) not null,
  USER_ID                      NUMBER(10),
  USER_TYPE                    CHAR(1),
  REAL_RESULT_PER              NUMBER,
  BETTING_DATE      DATE not null,
  LOTTERY_TYPE      VARCHAR2(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create table
create table TB_S_REPORT_R_PERIOD
(
  ID                NUMBER(10) not null,
  USER_TYPE         CHAR(1),
  COUNT             NUMBER(10),
  AMOUNT            NUMBER,
  MEMBER_AMOUNT     NUMBER,
  WIN_BACK_WATER    NUMBER,
  BACK_WATER_RESULT NUMBER,
  PERIODS_NUM       VARCHAR2(11) not null,
  USER_ID           NUMBER(10),
  BETTING_DATE      DATE not null,
  LOTTERY_TYPE      VARCHAR2(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create table
create table TB_C_REPORT_PET_PERIOD
(
  ID                           NUMBER(10) not null,
  COUNT                        NUMBER(10),
  TOTAL_MONEY                  NUMBER,
  MONEY_RATE_AGENT             NUMBER,
  MONEY_RATE_GENAGENT          NUMBER,
  MONEY_RATE_STOCKHOLDER       NUMBER,
  MONEY_RATE_BRANCH            NUMBER,
  MONEY_RATE_CHIEF             NUMBER,
  RATE_MONEY                   NUMBER,
  MEMBER_AMOUNT                NUMBER,
  SUBORDINATE_AMOUNT_WIN       NUMBER,
  SUBORDINATE_AMOUNT_BACKWATER NUMBER,
  REALWIN                      NUMBER,
  REAL_BACKWATER               NUMBER,
  COMMISSION                   NUMBER,
  PERIODS_NUM                  VARCHAR2(11) not null,
  USER_ID                      NUMBER(10),
  USER_TYPE                    CHAR(1),
  COMMISSION_TYPE              VARCHAR2(15),
  BETTING_DATE      DATE not null,
  LOTTERY_TYPE      VARCHAR2(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create table
create table TB_C_REPORT_R_PERIOD
(
  ID                NUMBER(10) not null,
  USER_TYPE         CHAR(1),
  COUNT             NUMBER(10),
  AMOUNT            NUMBER,
  MEMBER_AMOUNT     NUMBER,
  WIN_BACK_WATER    NUMBER,
  BACK_WATER_RESULT NUMBER,
  PERIODS_NUM       VARCHAR2(11) not null,
  USER_ID           NUMBER(10),
  COMMISSION_TYPE   VARCHAR2(15),
  BETTING_DATE      DATE not null,
  LOTTERY_TYPE      VARCHAR2(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create sequence 
create sequence SEQ_TB_C_REPORT_PET_PERIOD
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_TB_C_REPORT_R_PERIOD
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_TB_S_REPORT_PET_PERIOD
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_TB_S_REPORT_R_PERIOD
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
