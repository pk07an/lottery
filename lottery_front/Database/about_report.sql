--------------------------------------------
-- Export file for user LOTTERY1210       --
-- Created by Eric on 2013/7/29, 22:17:46 --
--------------------------------------------

spool about_report.log

prompt
prompt Creating table TB_CLASS_REPORT_PET_LIST
prompt =======================================
prompt
create table TB_CLASS_REPORT_PET_LIST
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
  BETTING_DATE                 DATE,
  USER_ID                      NUMBER(10),
  USER_TYPE                    CHAR(1),
  COMMISSION_TYPE              VARCHAR2(15)
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
comment on column TB_CLASS_REPORT_PET_LIST.COUNT
  is '笔数';
comment on column TB_CLASS_REPORT_PET_LIST.TOTAL_MONEY
  is '有效金額';
comment on column TB_CLASS_REPORT_PET_LIST.MONEY_RATE_AGENT
  is '代理实占';
comment on column TB_CLASS_REPORT_PET_LIST.MONEY_RATE_GENAGENT
  is '总代理实占';
comment on column TB_CLASS_REPORT_PET_LIST.MONEY_RATE_STOCKHOLDER
  is '股东实占';
comment on column TB_CLASS_REPORT_PET_LIST.MONEY_RATE_BRANCH
  is '分公司实占';
comment on column TB_CLASS_REPORT_PET_LIST.MONEY_RATE_CHIEF
  is '总监实占';
comment on column TB_CLASS_REPORT_PET_LIST.RATE_MONEY
  is '本身实占';
comment on column TB_CLASS_REPORT_PET_LIST.MEMBER_AMOUNT
  is '會员輸贏';
comment on column TB_CLASS_REPORT_PET_LIST.SUBORDINATE_AMOUNT_WIN
  is '应收下线-輸贏';
comment on column TB_CLASS_REPORT_PET_LIST.SUBORDINATE_AMOUNT_BACKWATER
  is '应收下线-退水';
comment on column TB_CLASS_REPORT_PET_LIST.REALWIN
  is '实占输赢';
comment on column TB_CLASS_REPORT_PET_LIST.REAL_BACKWATER
  is '实占退水';
comment on column TB_CLASS_REPORT_PET_LIST.COMMISSION
  is '退水';
comment on column TB_CLASS_REPORT_PET_LIST.BETTING_DATE
  is '投注时间';
comment on column TB_CLASS_REPORT_PET_LIST.USER_ID
  is '统计对象的用户ID';
comment on column TB_CLASS_REPORT_PET_LIST.USER_TYPE
  is '统计对象的用户类型';
comment on column TB_CLASS_REPORT_PET_LIST.COMMISSION_TYPE
  is '拥金类型';
alter table TB_CLASS_REPORT_PET_LIST
  add constraint PK_TB_CLASS_REPORT_PET_LIST primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table TB_CLASS_REPORT_R_LIST
prompt =====================================
prompt
create table TB_CLASS_REPORT_R_LIST
(
  ID                NUMBER(10) not null,
  USER_TYPE         CHAR(1),
  COUNT             NUMBER(10),
  AMOUNT            NUMBER,
  MEMBER_AMOUNT     NUMBER,
  WIN_BACK_WATER    NUMBER,
  BACK_WATER_RESULT NUMBER,
  BETTING_DATE      DATE,
  USER_ID           NUMBER(10),
  COMMISSION_TYPE   VARCHAR2(15)
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
comment on column TB_CLASS_REPORT_R_LIST.USER_TYPE
  is '统计的用户类型';
comment on column TB_CLASS_REPORT_R_LIST.COUNT
  is '笔数';
comment on column TB_CLASS_REPORT_R_LIST.AMOUNT
  is '投注总额';
comment on column TB_CLASS_REPORT_R_LIST.MEMBER_AMOUNT
  is '會员輸贏';
comment on column TB_CLASS_REPORT_R_LIST.WIN_BACK_WATER
  is '赚取退水';
comment on column TB_CLASS_REPORT_R_LIST.BACK_WATER_RESULT
  is '退水后结果';
comment on column TB_CLASS_REPORT_R_LIST.BETTING_DATE
  is '投注时间';
comment on column TB_CLASS_REPORT_R_LIST.USER_ID
  is '统计的用户ID';
comment on column TB_CLASS_REPORT_R_LIST.COMMISSION_TYPE
  is '拥金类型';
alter table TB_CLASS_REPORT_R_LIST
  add constraint PK_TB_TB_CLASS_REPORT_R_LIST primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table TB_REPORT_STATUS
prompt ===============================
prompt
create table TB_REPORT_STATUS
(
  ID     NUMBER(10) not null,
  OPT    CHAR(1) default 0 not null,
  STATUS CHAR(1) default 0 not null
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
comment on table TB_REPORT_STATUS
  is '报表状态表';
comment on column TB_REPORT_STATUS.ID
  is '主键';
comment on column TB_REPORT_STATUS.OPT
  is '报表是否使用新方法计算，在总官里设置，0：关闭 1：开启，默认为关闭';
comment on column TB_REPORT_STATUS.STATUS
  is '报表计算的状态，0：未成功 1：成功，默认为未成功';
alter table TB_REPORT_STATUS
  add constraint PK_TB_REPORT_STATUS primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table TB_SETTLED_REPORT_PET_LIST
prompt =========================================
prompt
create table TB_SETTLED_REPORT_PET_LIST
(
  ID                           NUMBER(10) not null,
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
  BETTING_DATE                 DATE,
  USER_ID                      NUMBER(10),
  USER_TYPE                    CHAR(1),
  REAL_RESULT_PER              NUMBER
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
comment on column TB_SETTLED_REPORT_PET_LIST.BETTING_USER_TYPE
  is '用户类型';
comment on column TB_SETTLED_REPORT_PET_LIST.PARENT_USER_TYPE
  is '上级用户类型';
comment on column TB_SETTLED_REPORT_PET_LIST.ACCOUNT
  is '登陆账号';
comment on column TB_SETTLED_REPORT_PET_LIST.CHNAME
  is '名称';
comment on column TB_SETTLED_REPORT_PET_LIST.COUNT
  is '笔数';
comment on column TB_SETTLED_REPORT_PET_LIST.TOTAL_MONEY
  is '有效金額';
comment on column TB_SETTLED_REPORT_PET_LIST.MONEY_RATE_AGENT
  is '代理实占';
comment on column TB_SETTLED_REPORT_PET_LIST.MONEY_RATE_GENAGENT
  is '总代理实占';
comment on column TB_SETTLED_REPORT_PET_LIST.MONEY_RATE_STOCKHOLDER
  is '股东实占';
comment on column TB_SETTLED_REPORT_PET_LIST.MONEY_RATE_BRANCH
  is '分公司实占';
comment on column TB_SETTLED_REPORT_PET_LIST.MONEY_RATE_CHIEF
  is '总监实占';
comment on column TB_SETTLED_REPORT_PET_LIST.RATE_MONEY
  is '本身实占';
comment on column TB_SETTLED_REPORT_PET_LIST.MEMBER_AMOUNT
  is '會员輸贏';
comment on column TB_SETTLED_REPORT_PET_LIST.SUBORDINATE_AMOUNT_WIN
  is '应收下线-輸贏';
comment on column TB_SETTLED_REPORT_PET_LIST.SUBORDINATE_AMOUNT_BACKWATER
  is '应收下线-退水';
comment on column TB_SETTLED_REPORT_PET_LIST.REALWIN
  is '实占输赢';
comment on column TB_SETTLED_REPORT_PET_LIST.REAL_BACKWATER
  is '实占退水';
comment on column TB_SETTLED_REPORT_PET_LIST.COMMISSION
  is '退水';
comment on column TB_SETTLED_REPORT_PET_LIST.BETTING_DATE
  is '投注时间';
comment on column TB_SETTLED_REPORT_PET_LIST.USER_ID
  is '统计对象的用户ID';
comment on column TB_SETTLED_REPORT_PET_LIST.USER_TYPE
  is '统计对象的用户类型';
comment on column TB_SETTLED_REPORT_PET_LIST.REAL_RESULT_PER
  is '占货比';
alter table TB_SETTLED_REPORT_PET_LIST
  add constraint PK_TB_REPORT_HIS primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table TB_SETTLED_REPORT_R_LIST
prompt =======================================
prompt
create table TB_SETTLED_REPORT_R_LIST
(
  ID                NUMBER(10) not null,
  USER_TYPE         CHAR(1),
  COUNT             NUMBER(10),
  AMOUNT            NUMBER,
  MEMBER_AMOUNT     NUMBER,
  WIN_BACK_WATER    NUMBER,
  BACK_WATER_RESULT NUMBER,
  BETTING_DATE      DATE,
  USER_ID           NUMBER(10)
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
comment on column TB_SETTLED_REPORT_R_LIST.USER_TYPE
  is '统计的用户类型';
comment on column TB_SETTLED_REPORT_R_LIST.COUNT
  is '笔数';
comment on column TB_SETTLED_REPORT_R_LIST.AMOUNT
  is '投注总额';
comment on column TB_SETTLED_REPORT_R_LIST.MEMBER_AMOUNT
  is '會员輸贏';
comment on column TB_SETTLED_REPORT_R_LIST.WIN_BACK_WATER
  is '赚取退水';
comment on column TB_SETTLED_REPORT_R_LIST.BACK_WATER_RESULT
  is '退水后结果';
comment on column TB_SETTLED_REPORT_R_LIST.BETTING_DATE
  is '投注时间';
comment on column TB_SETTLED_REPORT_R_LIST.USER_ID
  is '统计的用户ID';
alter table TB_SETTLED_REPORT_R_LIST
  add constraint PK_TB_TB_SETTLED_REPORT_R_LIST primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence SEQ_TB_CLASS_REPORT_PET_LIST
prompt ==============================================
prompt
create sequence SEQ_TB_CLASS_REPORT_PET_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 3101
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CLASS_REPORT_R_LIST
prompt ============================================
prompt
create sequence SEQ_TB_CLASS_REPORT_R_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 4661
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPORT_STATUS
prompt ======================================
prompt
create sequence SEQ_TB_REPORT_STATUS
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SETTLED_REPORT_PET_LIST
prompt ================================================
prompt
create sequence SEQ_TB_SETTLED_REPORT_PET_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 1861
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SETTLED_REPORT_R_LIST
prompt ==============================================
prompt
create sequence SEQ_TB_SETTLED_REPORT_R_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 461
increment by 1
cache 20;

prompt
prompt Creating view VIEW_BJSC_HIS_TODAY
prompt =================================
prompt
create or replace view view_bjsc_his_today as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK"
    from TB_BJSC_HIS
 where trunc(betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour);;

prompt
prompt Creating view VIEW_BJSC_HIS_YESTERDAY
prompt =====================================
prompt
create or replace view view_bjsc_his_yesterday as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK"
    from TB_BJSC_HIS
 where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');

prompt
prompt Creating view VIEW_CQSSC_HIS_TODAY
prompt ==================================
prompt
create or replace view view_cqssc_his_today as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK"
    from TB_CQSSC_HIS
 where trunc(betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour);;

prompt
prompt Creating view VIEW_CQSSC_HIS_YESTERDAY
prompt ======================================
prompt
create or replace view view_cqssc_his_yesterday as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK"
    from TB_CQSSC_HIS
 where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');

prompt
prompt Creating view VIEW_GDKLSF_HIS_TODAY
prompt ===================================
prompt
create or replace view view_gdklsf_his_today as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","COMPOUND_NUM","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","ATTRIBUTE","SPLIT_ATTRIBUTE","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK" from tb_gdklsf_his t
where trunc(betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour);;

prompt
prompt Creating view VIEW_GDKLSF_HIS_YESTERDAY
prompt =======================================
prompt
create or replace view view_gdklsf_his_yesterday as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","COMPOUND_NUM","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","ATTRIBUTE","SPLIT_ATTRIBUTE","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK" from tb_gdklsf_his t
where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');

prompt
prompt Creating view VIEW_JSSB_HIS_TODAY
prompt =================================
prompt
create or replace view view_jssb_his_today as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK","PLUS_ODDS"
    from TB_JSSB_HIS
 where trunc(betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour);;

prompt
prompt Creating view VIEW_JSSB_HIS_YESTERDAY
prompt =====================================
prompt
create or replace view view_jssb_his_yesterday as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK","PLUS_ODDS"
    from TB_JSSB_HIS
 where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');

prompt
prompt Creating view VIEW_REPLENISH_HIS_TODAY
prompt ======================================
prompt
CREATE OR REPLACE VIEW VIEW_REPLENISH_HIS_TODAY AS
select "ID","ORDER_NO","TYPE_CODE","MONEY","ATTRIBUTE","REPLENISH_USER_ID","REPLENISH_ACC_USER_ID","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION","RATE","UPDATE_USER","UPDATE_DATE","REMARK","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","RATE_CHIEF","RATE_BRANCH","RATE_STOCKHOLDER","RATE_GEN_AGENT","RATE_AGENT","ODDS2","COMMISSION_CHIEF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","COMMISSION_TYPE","SELECT_ODDS"
    from TB_REPLENISH_HIS
 where trunc(betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour);;

prompt
prompt Creating view VIEW_REPLENISH_HIS_YESTERDAY
prompt ==========================================
prompt
CREATE OR REPLACE VIEW VIEW_REPLENISH_HIS_YESTERDAY AS
select "ID","ORDER_NO","TYPE_CODE","MONEY","ATTRIBUTE","REPLENISH_USER_ID","REPLENISH_ACC_USER_ID","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION","RATE","UPDATE_USER","UPDATE_DATE","REMARK","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","RATE_CHIEF","RATE_BRANCH","RATE_STOCKHOLDER","RATE_GEN_AGENT","RATE_AGENT","ODDS2","COMMISSION_CHIEF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","COMMISSION_TYPE","SELECT_ODDS"
    from TB_REPLENISH_HIS
 where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');


spool off
