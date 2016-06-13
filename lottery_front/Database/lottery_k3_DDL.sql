--盘期表 TB_JSSB_PERIODS_INFO
prompt
prompt Creating table TB_JSSB_PERIODS_INFO
prompt ===================================
prompt
create table TB_JSSB_PERIODS_INFO
(
  ID             NUMBER(10) not null,
  PERIODS_NUM    VARCHAR2(11) not null,
  OPEN_QUOT_TIME DATE not null,
  LOTTERY_TIME   DATE not null,
  STOP_QUOT_TIME DATE not null,
  RESULT1        INTEGER,
  RESULT2        INTEGER,
  RESULT3        INTEGER,
  STATE          CHAR(1) default '1' not null,
  CREATE_USER    NUMBER(10),
  CREATE_TIME    DATE default sysdate not null
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
comment on table TB_JSSB_PERIODS_INFO
  is '江苏骰宝盘期表';
comment on column TB_JSSB_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 201305200001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_JSSB_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_JSSB_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_JSSB_PERIODS_INFO.RESULT3
  is '盘期所对应的开奖结果中的第三球数值';
comment on column TB_JSSB_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_JSSB_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_JSSB_PERIODS_INFO
  add constraint PK_TB_JSSB_PERIODS_INFO primary key (ID)
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
--当期投注表 TB_JSSB
prompt
prompt Creating table TB_JSSB
prompt ======================
prompt
create table TB_JSSB
(
  ID                     NUMBER(10) not null,
  ORDER_NO               VARCHAR2(8),
  TYPE_CODE              VARCHAR2(30) not null,
  MONEY                  NUMBER not null,
  BETTING_USER_ID        NUMBER(10) not null,
  CHIEFSTAFF             NUMBER(10),
  BRANCHSTAFF            NUMBER(10),
  STOCKHOLDERSTAFF       NUMBER(10),
  GENAGENSTAFF           NUMBER(10),
  AGENTSTAFF             NUMBER(10),
  COMMISSION_BRANCH      NUMBER,
  COMMISSION_GEN_AGENT   NUMBER,
  COMMISSION_STOCKHOLDER NUMBER,
  COMMISSION_AGENT       NUMBER,
  COMMISSION_MEMBER      NUMBER,
  RATE_CHIEF             NUMBER(5,2),
  RATE_BRANCH            NUMBER(5,2),
  RATE_GEN_AGENT         NUMBER(5,2),
  RATE_STOCKHOLDER       NUMBER(5,2),
  RATE_AGENT             NUMBER(5,2),
  PERIODS_NUM            VARCHAR2(11) not null,
  PLATE                  CHAR(1),
  BETTING_DATE           DATE default sysdate not null,
  WIN_STATE              CHAR(1) default '0' not null,
  WIN_AMOUNT             NUMBER,
  ODDS                   NUMBER(12,4) not null,
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
comment on table TB_JSSB
  is '江苏骰宝对应的投注表，记录会员投注信息数据';
comment on column TB_JSSB.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_JSSB.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_JSSB.MONEY
  is '投注金额，单位分';
comment on column TB_JSSB.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_JSSB.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_JSSB.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级§用户所对应的直属会员，则为空';
comment on column TB_JSSB.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB.PERIODS_NUM
  is '投注期数，形如 201305200001，对应于盘期信息（江苏骰宝）表（TB_JSSB_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_JSSB.PLATE
  is 'A、B、C等盘面';
comment on column TB_JSSB.BETTING_DATE
  is '投注时间';
comment on column TB_JSSB.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_JSSB.WIN_AMOUNT
  is '单位分';
comment on column TB_JSSB.ODDS
  is '当前投注单所对应的赔率';
alter table TB_JSSB
  add constraint PK_TB_JSSB primary key (ID)
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
  
--历史投注表 TB_JSSB_HIS
prompt
prompt Creating table TB_JSSB_HIS
prompt ==========================
prompt
create table TB_JSSB_HIS
(
  ID                     NUMBER(10) not null,
  CREATE_DATE            DATE default sysdate,
  ORIGIN_TB_NAME         VARCHAR2(100),
  ORIGIN_ID              NUMBER(10),
  ORDER_NO               VARCHAR2(8),
  TYPE_CODE              VARCHAR2(30) not null,
  MONEY                  NUMBER not null,
  BETTING_USER_ID        NUMBER(10) not null,
  CHIEFSTAFF             NUMBER(10),
  BRANCHSTAFF            NUMBER(10),
  STOCKHOLDERSTAFF       NUMBER(10),
  GENAGENSTAFF           NUMBER(10),
  AGENTSTAFF             NUMBER(10),
  COMMISSION_BRANCH      NUMBER,
  COMMISSION_GEN_AGENT   NUMBER,
  COMMISSION_STOCKHOLDER NUMBER,
  COMMISSION_AGENT       NUMBER,
  COMMISSION_MEMBER      NUMBER,
  RATE_CHIEF             NUMBER(5,2),
  RATE_BRANCH            NUMBER(5,2),
  RATE_GEN_AGENT         NUMBER(5,2),
  RATE_STOCKHOLDER       NUMBER(5,2),
  RATE_AGENT             NUMBER(5,2),
  PERIODS_NUM            VARCHAR2(11) not null,
  PLATE                  CHAR(1),
  BETTING_DATE           DATE default sysdate not null,
  WIN_STATE              CHAR(1) default '0' not null,
  WIN_AMOUNT             NUMBER,
  ODDS                   NUMBER(12,4) not null,
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
comment on table TB_JSSB_HIS
  is '投注历史表，存储如下数据表中的历史数据：';
comment on column TB_JSSB_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_JSSB_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_JSSB_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_JSSB_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_JSSB_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_JSSB_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_JSSB_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_JSSB_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_JSSB_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息 【投注期数（PERIODS_NUM）】字段';
comment on column TB_JSSB_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_JSSB_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_JSSB_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和 默认值 0 — 未开奖';
comment on column TB_JSSB_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_JSSB_HIS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_JSSB_HIS
  add constraint PK_TB_JSSB_HIS primary key (ID)
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
  
--江苏当前投注表序列  SEQ_TB_JSSB
prompt
prompt Creating sequence SEQ_TB_JSSB
prompt =============================
prompt
create sequence SEQ_TB_JSSB
minvalue 1
maxvalue 999999999999999999999999999
start with 15541
increment by 1
cache 20;

--江苏历史投注表序列  SEQ_TB_JSSB_HIS
prompt
prompt Creating sequence SEQ_TB_JSSB_HIS
prompt =================================
prompt
create sequence SEQ_TB_JSSB_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

--江苏盘期表序列 SEQ_TB_JSSB_PERIODS_INFO
prompt
prompt Creating sequence SEQ_TB_JSSB_PERIODS_INFO
prompt ==========================================
prompt
create sequence SEQ_TB_JSSB_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 57781
increment by 1
cache 20;

--江苏投注表增加字段plus_odds
ALTER TABLE TB_JSSB_HIS ADD PLUS_ODDS NUMBER(2) DEFAULT 1;
COMMENT ON COLUMN TB_JSSB_HIS.PLUS_ODDS
  IS '江苏骰宝附加赔率，主要用于三军玩法';
  
ALTER TABLE TB_JSSB ADD PLUS_ODDS NUMBER(2) DEFAULT 1;
COMMENT ON COLUMN TB_JSSB.PLUS_ODDS
  IS '江苏骰宝附加赔率，主要用于三军玩法';
  
--增加记录表字段  
ALTER TABLE  TB_REPLENISH_AUTO_SET_LOG ADD  CHANGE_TYPE VARCHAR2(300);
ALTER TABLE  TB_REPLENISH_AUTO_SET_LOG ADD  CHANGE_SUB_TYPE VARCHAR2(300);
ALTER TABLE  TB_REPLENISH_AUTO_SET_LOG ADD  ORGINAL_VALUE VARCHAR2(300);
ALTER TABLE  TB_REPLENISH_AUTO_SET_LOG ADD  NEW_VALUE VARCHAR2(300);
--20130604
ALTER TABLE TB_REPLENISH_AUTO_SET_LOG ADD UPDATE_USERID VARCHAR2(10);
ALTER TABLE TB_REPLENISH_AUTO_SET_LOG ADD UPDATE_USERTYPE VARCHAR2(10);

--增加商铺信息 可否具备注单删除功能
ALTER TABLE TB_SHOPS_INFO ADD ENABLE_BET_DELETE VARCHAR2(1) DEFAULT 'N';