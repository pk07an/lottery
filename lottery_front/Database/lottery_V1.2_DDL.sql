--增加商铺信息 可否具备注单注销功能
ALTER TABLE TB_SHOPS_INFO ADD ENABLE_BET_CANCEL VARCHAR2(1) DEFAULT 'N';

--增加补货历史表
prompt
prompt Creating table TB_REPLENISH_HIS
prompt ===========================
prompt
create table TB_REPLENISH_HIS
(
  ID                     NUMBER(10) not null,
  ORDER_NO               VARCHAR2(8),
  TYPE_CODE              VARCHAR2(30) not null,
  MONEY                  NUMBER not null,
  ATTRIBUTE              VARCHAR2(50),
  REPLENISH_USER_ID      NUMBER(10) not null,
  REPLENISH_ACC_USER_ID  NUMBER(10),
  PERIODS_NUM            VARCHAR2(11) not null,
  PLATE                  CHAR(1),
  BETTING_DATE           DATE default sysdate not null,
  WIN_STATE              CHAR(1) default '0' not null,
  WIN_AMOUNT             NUMBER,
  ODDS                   NUMBER(12,4) not null,
  COMMISSION             NUMBER,
  RATE                   NUMBER(5,2),
  UPDATE_USER            NUMBER(10),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200),
  CHIEFSTAFF             NUMBER(10),
  BRANCHSTAFF            NUMBER(10),
  STOCKHOLDERSTAFF       NUMBER(10),
  GENAGENSTAFF           NUMBER(10),
  AGENTSTAFF             NUMBER(10),
  RATE_CHIEF             NUMBER(5,2),
  RATE_BRANCH            NUMBER(5,2),
  RATE_STOCKHOLDER       NUMBER(5,2),
  RATE_GEN_AGENT         NUMBER(5,2),
  RATE_AGENT             NUMBER(5,2),
  ODDS2                  NUMBER(12,4),
  COMMISSION_CHIEF       NUMBER,
  COMMISSION_BRANCH      NUMBER,
  COMMISSION_GEN_AGENT   NUMBER,
  COMMISSION_STOCKHOLDER NUMBER,
  COMMISSION_AGENT       NUMBER,
  COMMISSION_MEMBER      NUMBER,
  COMMISSION_TYPE        VARCHAR2(20),
  SELECT_ODDS            VARCHAR2(120)
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
comment on column TB_REPLENISH_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_REPLENISH_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_REPLENISH_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_REPLENISH_HIS.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_REPLENISH_HIS.REPLENISH_USER_ID
  is '补货人ID';
comment on column TB_REPLENISH_HIS.REPLENISH_ACC_USER_ID
  is '接受补货的对象，一般是补货人的上一级';
comment on column TB_REPLENISH_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_REPLENISH_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_REPLENISH_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_REPLENISH_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_REPLENISH_HIS.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_REPLENISH_HIS.ODDS2
  is '赔率2，只有当投注类型为连码时才有效';
comment on column TB_REPLENISH_HIS.SELECT_ODDS
  is '记录选择的球的赔率(用于五不中和过关)';

alter table TB_REPLENISH_HIS
  add constraint PK_TB_REPLENISH_HIS primary key (ID)
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
  
--补货历史表序列 SEQ_TB_REPLENISH_HIS
prompt
prompt Creating sequence SEQ_TB_REPLENISH_HIS
prompt ==========================================
prompt
create sequence SEQ_TB_REPLENISH_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 57781
increment by 1
cache 20;
