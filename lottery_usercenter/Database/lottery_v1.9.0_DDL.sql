prompt
prompt Creating table TB_NC
prompt ====================================
prompt
create table TB_NC
(
  ID                     NUMBER(10) not null,
  ORDER_NO               VARCHAR2(8),
  TYPE_CODE              VARCHAR2(30) not null,
  MONEY                  NUMBER not null,
  COMPOUND_NUM           NUMBER,
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
  ATTRIBUTE              VARCHAR2(50),
  SPLIT_ATTRIBUTE        VARCHAR2(25),
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
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TB_NC.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_NC.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_NC.MONEY
  is '投注金额，单位分';
comment on column TB_NC.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_NC.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_NC.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_NC.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_HIS.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_NC.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（幸运农场）表（TB_NC_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_NC.PLATE
  is 'A、B、C等盘面';
comment on column TB_NC.BETTING_DATE
  is '投注时间';
comment on column TB_NC.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_NC.WIN_AMOUNT
  is '单位分';
comment on column TB_NC.ODDS
  is '当前投注单所对应的赔率';
alter table TB_NC
  add constraint PK_TB_NC primary key (ID)
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
prompt Creating table TB_NC_HIS
prompt ============================
prompt
create table TB_NC_HIS
(
  ID                     NUMBER(10) not null,
  CREATE_DATE            DATE default sysdate,
  ORIGIN_TB_NAME         VARCHAR2(100),
  ORIGIN_ID              NUMBER(10),
  ORDER_NO               VARCHAR2(8),
  TYPE_CODE              VARCHAR2(30) not null,
  MONEY                  NUMBER not null,
  COMPOUND_NUM           NUMBER,
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
  ATTRIBUTE              VARCHAR2(50),
  SPLIT_ATTRIBUTE        VARCHAR2(25),
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
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table TB_NC_HIS
  is '投注历史表';
comment on column TB_NC_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_NC_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_NC_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_NC_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_NC_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_NC_HIS.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_NC_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_NC_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_NC_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_NC_HIS.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_NC_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_NC_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_NC_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_NC_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_NC_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_NC_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_NC_HIS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_NC_HIS
  add constraint PK_TB_NC_HIS primary key (ID)
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
prompt Creating table TB_NC_PERIODS_INFO
prompt =====================================
prompt
create table TB_NC_PERIODS_INFO
(
  ID             NUMBER(10) not null,
  PERIODS_NUM    VARCHAR2(11) not null,
  OPEN_QUOT_TIME DATE not null,
  LOTTERY_TIME   DATE not null,
  STOP_QUOT_TIME DATE not null,
  RESULT1        INTEGER,
  RESULT2        INTEGER,
  RESULT3        INTEGER,
  RESULT4        INTEGER,
  RESULT5        INTEGER,
  RESULT6        INTEGER,
  RESULT7        INTEGER,
  RESULT8        INTEGER,
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
comment on table TB_NC_PERIODS_INFO
  is '广东快乐十分盘期信息表';
comment on column TB_NC_PERIODS_INFO.ID
  is '代理主键';
comment on column TB_NC_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_NC_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_NC_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_NC_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_NC_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_NC_PERIODS_INFO
  add constraint PK_TB_NC_PERIODS_INFO primary key (ID)
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
create index INDEX_LOTTERY_TIME on TB_NC_PERIODS_INFO (LOTTERY_TIME)
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
create index INDEX_OPEN_TIME on TB_NC_PERIODS_INFO (OPEN_QUOT_TIME)
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
create index INDEX_STOP_TIME on TB_NC_PERIODS_INFO (STOP_QUOT_TIME)
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
prompt Creating sequence SEQ_TB_NC
prompt ===========================================
prompt
create sequence SEQ_TB_NC
minvalue 1
maxvalue 999999999999999999999999999
start with 3701
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_NC_HIS
prompt ===================================
prompt
create sequence SEQ_TB_NC_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_NC_PERIODS_INFO
prompt ============================================
prompt
create sequence SEQ_TB_NC_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 20001
increment by 1
cache 20;
--创建投注数据校验表
--北京
CREATE TABLE TB_BJSC_CHECK AS 
	SELECT 	ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS, 
			CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,
			COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE
		FROM  TB_BJSC_HIS WHERE 1=2;

--重庆
CREATE TABLE TB_CQSSC_CHECK AS 
	SELECT ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS,
			CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,
			COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE
		FROM  TB_CQSSC_HIS WHERE 1=2;

--广东
CREATE TABLE TB_GDKLSF_CHECK AS 
	SELECT 	ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS,
			CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,
			COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE,
			ATTRIBUTE,SPLIT_ATTRIBUTE 
		FROM  TB_GDKLSF_HIS WHERE 1=2;
--江苏
CREATE TABLE TB_JSSB_CHECK AS 
	SELECT ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS, 
		   CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,
		   COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE
		FROM  TB_JSSB_HIS WHERE 1=2;
--幸运农场
CREATE TABLE TB_NC_CHECK AS 
	SELECT 	ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS,
			CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,
			COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE,
			ATTRIBUTE,SPLIT_ATTRIBUTE  
	FROM  TB_NC_HIS WHERE 1=2;
CREATE TABLE TB_REPLENISH_CHECK AS SELECT * FROM  TB_REPLENISH_HIS WHERE 1=2;

create sequence SEQ_TB_BJSC_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

create sequence SEQ_TB_CQSSC_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

create sequence SEQ_TB_GDKLSF_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

create sequence SEQ_TB_JSSB_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

create sequence SEQ_TB_NC_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;

create sequence SEQ_TB_REPLENISH_CHECK
minvalue 1
maxvalue 999999999999999999999999999
start with 15001
increment by 1
cache 20;