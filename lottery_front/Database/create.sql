----------------------------------------------------
-- Export file for user LOTTERY6677               --
-- Created by Administrator on 2013-9-8, 22:38:12 --
----------------------------------------------------

spool create.log

prompt
prompt Creating table TB_AGENT_STAFF_EXT
prompt =================================
prompt
create table TB_AGENT_STAFF_EXT
(
  PARENT_STAFF          NUMBER(10),
  REPLENISHMENT         CHAR(1) not null,
  MANAGER_STAFF_ID      NUMBER(10) not null,
  PURE_ACCOUNTED        CHAR(1),
  AGENT_RATE            NUMBER(5,2),
  TOTAL_CREDIT_LINE     NUMBER(10),
  AVAILABLE_CREDIT_LINE NUMBER(10),
  GEN_AGENT_RATE        NUMBER(5,2),
  CHIEF_STAFF           NUMBER(10),
  BRANCH_STAFF          NUMBER(10),
  STOCKHOLDER_STAFF     NUMBER(10),
  RATE_RESTRICT         CHAR(1),
  BELOW_RATE_LIMIT      NUMBER(2)
)
;
comment on table TB_AGENT_STAFF_EXT
  is '此表中记录代理用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了代理用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
代理用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “6 — 代理用户”';
comment on column TB_AGENT_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_AGENT_STAFF_EXT.REPLENISHMENT
  is '走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞';
comment on column TB_AGENT_STAFF_EXT.MANAGER_STAFF_ID
  is '代理用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_AGENT_STAFF_EXT.PURE_ACCOUNTED
  is '0 — 纯占；1 — 非纯占';
comment on column TB_AGENT_STAFF_EXT.AGENT_RATE
  is '代理占成';
comment on column TB_AGENT_STAFF_EXT.CHIEF_STAFF
  is '代理所对应的总监ID';
comment on column TB_AGENT_STAFF_EXT.BRANCH_STAFF
  is '代理所对应的分公司ID';
comment on column TB_AGENT_STAFF_EXT.STOCKHOLDER_STAFF
  is '代理所对应的股东ID';
comment on column TB_AGENT_STAFF_EXT.RATE_RESTRICT
  is '是否限制占成';
comment on column TB_AGENT_STAFF_EXT.BELOW_RATE_LIMIT
  is '限制占成限额';
alter table TB_AGENT_STAFF_EXT
  add constraint PK_TB_AGENT_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_BJSC
prompt ======================
prompt
create table TB_BJSC
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
;
comment on table TB_BJSC
  is '北京赛车对应的投注表，记录会员投注信息数据';
comment on column TB_BJSC.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_BJSC.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_BJSC.MONEY
  is '投注金额，单位分';
comment on column TB_BJSC.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_BJSC.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_BJSC.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级§用户所对应的直属会员，则为空';
comment on column TB_BJSC.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（北京赛车）表（TB_BJSC_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_BJSC.PLATE
  is 'A、B、C等盘面';
comment on column TB_BJSC.BETTING_DATE
  is '投注时间';
comment on column TB_BJSC.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_BJSC.WIN_AMOUNT
  is '单位分';
comment on column TB_BJSC.ODDS
  is '当前投注单所对应的赔率';
alter table TB_BJSC
  add constraint PK_TB_BJSC primary key (ID);

prompt
prompt Creating table TB_BJSC_HIS
prompt ==========================
prompt
create table TB_BJSC_HIS
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
;
comment on table TB_BJSC_HIS
  is '投注历史表，存储如下数据表中的历史数据：';
comment on column TB_BJSC_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_BJSC_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_BJSC_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_BJSC_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_BJSC_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_BJSC_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_BJSC_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_BJSC_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_BJSC_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息 【投注期数（PERIODS_NUM）】字段';
comment on column TB_BJSC_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_BJSC_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_BJSC_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和 默认值 0 — 未开奖';
comment on column TB_BJSC_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_BJSC_HIS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_BJSC_HIS
  add constraint PK_TB_BJSC_HIS primary key (ID);
create index INDEX_BJ_HIS_BETTING_DATE_USER on TB_BJSC_HIS (BETTING_USER_ID, BETTING_DATE);
create index INDEX_BJ_HIS_BETTING_USER on TB_BJSC_HIS (BETTING_USER_ID);

prompt
prompt Creating table TB_BJSC_PERIODS_INFO
prompt ===================================
prompt
create table TB_BJSC_PERIODS_INFO
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
  RESULT9        INTEGER,
  RESULT10       INTEGER,
  STATE          CHAR(1) default '1' not null,
  CREATE_USER    NUMBER(10),
  CREATE_TIME    DATE default sysdate not null
)
;
comment on table TB_BJSC_PERIODS_INFO
  is '北京塞车盘期表';
comment on column TB_BJSC_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_BJSC_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_BJSC_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_BJSC_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_BJSC_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_BJSC_PERIODS_INFO
  add constraint PK_TB_BJSC_PERIODS_INFO primary key (ID);

prompt
prompt Creating table TB_BOSS_LOG
prompt ==========================
prompt
create table TB_BOSS_LOG
(
  ID          NUMBER(10) not null,
  LOG_MESSAGE NVARCHAR2(200),
  LOG_STATE   CHAR(1) default '0',
  LOG_LEVEL   CHAR(1) default '1',
  CREATEDATE  DATE default sysdate
)
;
comment on column TB_BOSS_LOG.LOG_STATE
  is '状态：0：初始，1：已删除';
comment on column TB_BOSS_LOG.LOG_LEVEL
  is '日志的级别，1、2、3，级别越高数字越大';
alter table TB_BOSS_LOG
  add constraint PK_TB_BOSS_LOG primary key (ID);

prompt
prompt Creating table TB_BRANCH_STAFF_EXT
prompt ==================================
prompt
create table TB_BRANCH_STAFF_EXT
(
  MANAGER_STAFF_ID      NUMBER(10) not null,
  PARENT_STAFF          NUMBER(10),
  REPLENISHMENT         CHAR(1) not null,
  CHIEF_RATE            NUMBER(5,2),
  COMPANY_RATE          NUMBER(5,2),
  TOTAL_CREDIT_LINE     NUMBER(10),
  AVAILABLE_CREDIT_LINE NUMBER(10),
  OPENREPORT            CHAR(1),
  LEFTOWNER             CHAR(1)
)
;
comment on table TB_BRANCH_STAFF_EXT
  is '此表中记录分公司用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了分公司用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
分公司用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “3 — 分公司用户”';
comment on column TB_BRANCH_STAFF_EXT.MANAGER_STAFF_ID
  is '分公司用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_BRANCH_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_BRANCH_STAFF_EXT.REPLENISHMENT
  is '走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞';
comment on column TB_BRANCH_STAFF_EXT.COMPANY_RATE
  is '公司占成';
comment on column TB_BRANCH_STAFF_EXT.OPENREPORT
  is '是否开放公司报表';
comment on column TB_BRANCH_STAFF_EXT.LEFTOWNER
  is '占余成数归';
alter table TB_BRANCH_STAFF_EXT
  add constraint PK_TB_BRANCH_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_CHIEF_STAFF_EXT
prompt =================================
prompt
create table TB_CHIEF_STAFF_EXT
(
  MANAGER_STAFF_ID NUMBER(10) not null,
  SHOPS_CODE       CHAR(4) not null
)
;
comment on table TB_CHIEF_STAFF_EXT
  is '此表中记录总监用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了总监用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
总监用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “2 — 总监用户”';
comment on column TB_CHIEF_STAFF_EXT.MANAGER_STAFF_ID
  is '总监用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_CHIEF_STAFF_EXT.SHOPS_CODE
  is '总监所隶属的商铺号码，对应商铺信息表（TB_SHOPS_INFO）的商铺号码（SHOPS_CODE）字段';
alter table TB_CHIEF_STAFF_EXT
  add constraint PK_TB_CHIEF_STAFF_EXT primary key (MANAGER_STAFF_ID);

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
;
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
  add constraint PK_TB_CLASS_REPORT_PET_LIST primary key (ID);

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
;
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
  add constraint PK_TB_TB_CLASS_REPORT_R_LIST primary key (ID);

prompt
prompt Creating table TB_CQSSC_BALL_FIFTH
prompt ==================================
prompt
create table TB_CQSSC_BALL_FIFTH
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
;
comment on column TB_CQSSC_BALL_FIFTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_BALL_FIFTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_BALL_FIFTH.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_BALL_FIFTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_BALL_FIFTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_BALL_FIFTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIFTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIFTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIFTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIFTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_BALL_FIFTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_BALL_FIFTH.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_BALL_FIFTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_BALL_FIFTH.WIN_AMOUNT
  is '单位分';
comment on column TB_CQSSC_BALL_FIFTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_BALL_FIFTH
  add constraint PK_TB_CQSSC_BALL_FIFTH primary key (ID);

prompt
prompt Creating table TB_CQSSC_BALL_FIRST
prompt ==================================
prompt
create table TB_CQSSC_BALL_FIRST
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
;
comment on table TB_CQSSC_BALL_FIRST
  is '重庆时时彩第一球对应的投注表，记录会员投注信息数据';
comment on column TB_CQSSC_BALL_FIRST.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_BALL_FIRST.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_BALL_FIRST.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_BALL_FIRST.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_BALL_FIRST.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_BALL_FIRST.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIRST.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级§用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIRST.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIRST.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FIRST.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_BALL_FIRST.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_BALL_FIRST.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_BALL_FIRST.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_BALL_FIRST.WIN_AMOUNT
  is '单位分';
comment on column TB_CQSSC_BALL_FIRST.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_BALL_FIRST
  add constraint PK_TB_CQSSC_BALL_FIRST primary key (ID);

prompt
prompt Creating table TB_CQSSC_BALL_FORTH
prompt ==================================
prompt
create table TB_CQSSC_BALL_FORTH
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
;
comment on column TB_CQSSC_BALL_FORTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_BALL_FORTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_BALL_FORTH.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_BALL_FORTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_BALL_FORTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_BALL_FORTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FORTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FORTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FORTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_FORTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_BALL_FORTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_BALL_FORTH.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_BALL_FORTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_BALL_FORTH.WIN_AMOUNT
  is '单位分';
comment on column TB_CQSSC_BALL_FORTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_BALL_FORTH
  add constraint PK_TB_CQSSC_BALL_FORTH primary key (ID);

prompt
prompt Creating table TB_CQSSC_BALL_SECOND
prompt ===================================
prompt
create table TB_CQSSC_BALL_SECOND
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
;
comment on column TB_CQSSC_BALL_SECOND.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_BALL_SECOND.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_BALL_SECOND.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_BALL_SECOND.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_BALL_SECOND.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_BALL_SECOND.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_SECOND.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_SECOND.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_SECOND.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_SECOND.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_BALL_SECOND.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_BALL_SECOND.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_BALL_SECOND.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_BALL_SECOND.WIN_AMOUNT
  is '单位分';
comment on column TB_CQSSC_BALL_SECOND.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_BALL_SECOND
  add constraint PK_TB_CQSSC_BALL_SECOND primary key (ID);

prompt
prompt Creating table TB_CQSSC_BALL_THIRD
prompt ==================================
prompt
create table TB_CQSSC_BALL_THIRD
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
;
comment on column TB_CQSSC_BALL_THIRD.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_BALL_THIRD.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_BALL_THIRD.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_BALL_THIRD.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_BALL_THIRD.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_BALL_THIRD.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_THIRD.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_THIRD.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_THIRD.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_BALL_THIRD.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_BALL_THIRD.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_BALL_THIRD.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_BALL_THIRD.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_BALL_THIRD.WIN_AMOUNT
  is '单位分';
comment on column TB_CQSSC_BALL_THIRD.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_BALL_THIRD
  add constraint PK_TB_CQSSC_BALL_THIRD primary key (ID);

prompt
prompt Creating table TB_CQSSC_HIS
prompt ===========================
prompt
create table TB_CQSSC_HIS
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
;
comment on table TB_CQSSC_HIS
  is '投注历史表，存储如下数据表中的历史数据：
1、投注（重庆时时彩第一球）表（TB_CQSSC_BALL_FIRST）
2、投注（重庆时时彩第二球）表（TB_CQSSC_BALL_SECOND）
3、投注（重庆时时彩第三球）表（TB_CQSSC_BALL_THIRD）
4、投注（重庆时时彩第四球）表（TB_CQSSC_BALL_FORTH）
5、投注（重庆时时彩第五球）表（TB_CQSSC_BALL_FIFTH）';
comment on column TB_CQSSC_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_CQSSC_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_CQSSC_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_CQSSC_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_CQSSC_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_CQSSC_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_CQSSC_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_CQSSC_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_CQSSC_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_CQSSC_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_CQSSC_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_CQSSC_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_CQSSC_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_CQSSC_HIS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_CQSSC_HIS
  add constraint PK_TB_CQSSC_HIS primary key (ID);
create index INDEX_CQ_HIS_BETTING_DATE_USER on TB_CQSSC_HIS (BETTING_USER_ID, BETTING_DATE);
create index INDEX_CQ_HIS_BETTING_USER on TB_CQSSC_HIS (BETTING_USER_ID);

prompt
prompt Creating table TB_CQSSC_PERIODS_INFO
prompt ====================================
prompt
create table TB_CQSSC_PERIODS_INFO
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
  STATE          CHAR(1) default '1' not null,
  CREATE_USER    NUMBER(10),
  CREATE_TIME    DATE default sysdate not null
)
;
comment on column TB_CQSSC_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_CQSSC_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_CQSSC_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_CQSSC_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_CQSSC_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_CQSSC_PERIODS_INFO
  add constraint PK_TB_CQSSC_PERIODS_INFO primary key (ID);
create index INDEX_CQ_LOTTERY_TIME on TB_CQSSC_PERIODS_INFO (LOTTERY_TIME);
create index INDEX_CQ_OPEN_TIME on TB_CQSSC_PERIODS_INFO (OPEN_QUOT_TIME);
create index INDEX_CQ_STOP_TIME on TB_CQSSC_PERIODS_INFO (STOP_QUOT_TIME);

prompt
prompt Creating table TB_DEFAULT_PLAY_ODDS
prompt ===================================
prompt
create table TB_DEFAULT_PLAY_ODDS
(
  ID               NUMBER(10) not null,
  PLAY_TYPE_CODE   VARCHAR2(30) not null,
  ODDS             NUMBER(12,4) not null,
  ODDS_TYPE        VARCHAR2(30),
  ODDS_TYPE_X      VARCHAR2(30),
  AUTO_ODDS_QUOTAS NUMBER,
  AUTO_ODDS        NUMBER(12,4),
  LOWEST_ODDS      NUMBER(12,4),
  BIGEST_ODDS      NUMBER(12,4),
  CUT_ODDS_B       NUMBER(12,4),
  CUT_ODDS_C       NUMBER(12,4),
  UPDATE_DATE      DATE,
  UPDATE_USER      NUMBER(10),
  REMARK           NVARCHAR2(200)
)
;
comment on table TB_DEFAULT_PLAY_ODDS
  is ' ';
comment on column TB_DEFAULT_PLAY_ODDS.PLAY_TYPE_CODE
  is '玩法类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_DEFAULT_PLAY_ODDS.ODDS
  is '默认赔率值';
comment on column TB_DEFAULT_PLAY_ODDS.ODDS_TYPE
  is '赔率主类型（第一球 等）';
comment on column TB_DEFAULT_PLAY_ODDS.ODDS_TYPE_X
  is '存放一些特殊玩法的子类型，如：过关，正码1（单双大小红绿蓝）';
comment on column TB_DEFAULT_PLAY_ODDS.AUTO_ODDS_QUOTAS
  is '自动降赔率额度';
comment on column TB_DEFAULT_PLAY_ODDS.AUTO_ODDS
  is '每次降赔率';
comment on column TB_DEFAULT_PLAY_ODDS.LOWEST_ODDS
  is '最低赔率';
comment on column TB_DEFAULT_PLAY_ODDS.BIGEST_ODDS
  is 'A盘赔率上限';
comment on column TB_DEFAULT_PLAY_ODDS.CUT_ODDS_B
  is 'B盘赔率降';
comment on column TB_DEFAULT_PLAY_ODDS.CUT_ODDS_C
  is 'C盘赔率降';
comment on column TB_DEFAULT_PLAY_ODDS.UPDATE_USER
  is '更新人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_DEFAULT_PLAY_ODDS
  add constraint PK_TB_DEFAULT_PLAY_ODDS primary key (ID);

prompt
prompt Creating table TB_FRAME_AREA_ROLE
prompt =================================
prompt
create table TB_FRAME_AREA_ROLE
(
  ID              NUMBER(10) not null,
  AREA_ID         NUMBER(10) not null,
  ROLE_ID         NUMBER(10) not null,
  AREA_ENTITY_ID  NUMBER(10) not null,
  AUTHORIZ_TYPE   CHAR(1) default '1',
  AREA_VALUE_TYPE CHAR(1),
  REMARK          NVARCHAR2(200)
)
;
comment on table TB_FRAME_AREA_ROLE
  is '权限域和角色的关联表，记录角色所拥有的权限域信息';
comment on column TB_FRAME_AREA_ROLE.ID
  is '代理主键';
comment on column TB_FRAME_AREA_ROLE.AREA_ID
  is '域ID，根据权限域值类型的不同，此处存储的数据含义也不同';
comment on column TB_FRAME_AREA_ROLE.ROLE_ID
  is '角色ID，外键，关联到角色表的主键';
comment on column TB_FRAME_AREA_ROLE.AREA_ENTITY_ID
  is '权限域实体ID，外键，记录授权相关的权限域ID所对应的权限域实体ID。（此字段部分信息冗余）';
comment on column TB_FRAME_AREA_ROLE.AUTHORIZ_TYPE
  is '授权类型，由于权限域是树型结构，故由此字段决定授权是否传递到子节点。
0-级联授权（即对父节点的授权需要传递到子节点）；1-独立授权（授权不传递到子节点）；默认值 1
（此字段暂时不使用，目前权限域值除了系统关联的机构和用户，不支持层级）';
comment on column TB_FRAME_AREA_ROLE.AREA_VALUE_TYPE
  is '0-普通值（域ID中存储权限域值ID，最终所返回给用户使用的权限信息是权限域中对应的权限域值编码）；1-机构数据（域ID中存储机构表中的机构ID，最终所返回给用户使用的权限信息是此表中域ID所对应的值）；2-用户数据（域ID中存储用户表中的用户ID，最终所返回给用户使用的权限信息是此表中域ID所对应的值）';
alter table TB_FRAME_AREA_ROLE
  add constraint PK_TB_FRAME_AREA_ROLE primary key (ID);

prompt
prompt Creating table TB_FRAME_AUTHORIZ_AREA
prompt =====================================
prompt
create table TB_FRAME_AUTHORIZ_AREA
(
  ID               NUMBER(10) not null,
  AREA_CODE        VARCHAR2(20) not null,
  AREA_NAME        NVARCHAR2(40) not null,
  AREA_TYPE        CHAR(1) not null,
  DEFAULT_AUTHORIZ CHAR(1) default '1',
  LINK_DATA        CHAR(1) default '0',
  AREA_STATE       CHAR(1) default '0' not null,
  PARENT_AREA      NUMBER(10) not null,
  SORT_NUM         NUMBER(4) default 1,
  REMARK           NVARCHAR2(200)
)
;
comment on table TB_FRAME_AUTHORIZ_AREA
  is '1、此表中存放权限域信息
';
comment on column TB_FRAME_AUTHORIZ_AREA.ID
  is '域ID';
comment on column TB_FRAME_AUTHORIZ_AREA.AREA_CODE
  is '域代码';
comment on column TB_FRAME_AUTHORIZ_AREA.AREA_NAME
  is '域名称';
comment on column TB_FRAME_AUTHORIZ_AREA.AREA_TYPE
  is '1-分类；2-业务；3-权限域实体；4-权限域取值。分类和业务主要用来组织权限树，也就是说，读取权限域信息的时候，只要关注取值为3、4的即可';
comment on column TB_FRAME_AUTHORIZ_AREA.DEFAULT_AUTHORIZ
  is '0-开放权限；1-禁用权限；默认值 1；此字段只在域类型为3-权限域实体是才有效';
comment on column TB_FRAME_AUTHORIZ_AREA.LINK_DATA
  is '0-无外部链接数据；1-链接机构表数据；2-链接用户表数据；默认值取0；此字段只在域类型为3-权限域实体时才有效，';
comment on column TB_FRAME_AUTHORIZ_AREA.AREA_STATE
  is '域状态，0-启用；1-未启用，默认值为0';
comment on column TB_FRAME_AUTHORIZ_AREA.PARENT_AREA
  is '上级域，对应本表的域ID（AREA_ID），如果值为自身ID，则表示是根节点';
comment on column TB_FRAME_AUTHORIZ_AREA.SORT_NUM
  is '排序序号，默认值 1';
alter table TB_FRAME_AUTHORIZ_AREA
  add constraint PK_TB_FRAME_AUTHORIZ_AREA primary key (ID);
create index INDEX_AUTHORIZ_AREA_CODE on TB_FRAME_AUTHORIZ_AREA (AREA_CODE);

prompt
prompt Creating table TB_FRAME_DEMO
prompt ============================
prompt
create table TB_FRAME_DEMO
(
  ID          NUMBER(10) not null,
  NAME        NVARCHAR2(40),
  CODE        VARCHAR2(20),
  STATE       CHAR(1),
  CREATE_TIME DATE,
  REMARK      NVARCHAR2(200)
)
;
comment on table TB_FRAME_DEMO
  is '测试表';
comment on column TB_FRAME_DEMO.STATE
  is '0-启用；1-禁用';
alter table TB_FRAME_DEMO
  add constraint PK_TB_FRAME_DEMO primary key (ID);

prompt
prompt Creating table TB_FRAME_FUNCTION
prompt ================================
prompt
create table TB_FRAME_FUNCTION
(
  ID          NUMBER(10) not null,
  FUNC_CODE   VARCHAR2(40) not null,
  FUNC_NAME   NVARCHAR2(40) not null,
  FUNC_STATE  CHAR(1) default '0' not null,
  FUNC_URL    NVARCHAR2(200),
  PARENT_FUNC NUMBER(10) not null,
  ICON_PATH   NVARCHAR2(150) default '/sysmenu/images/pub_3_1.gif',
  SORT_NUM    NUMBER(4) default 1,
  FUNC_DESC   NVARCHAR2(200)
)
;
comment on table TB_FRAME_FUNCTION
  is '功能表，对应具体的功能信息';
comment on column TB_FRAME_FUNCTION.ID
  is '代理主键';
comment on column TB_FRAME_FUNCTION.FUNC_STATE
  is '功能状态，0-启用；1-未启用；默认值0';
comment on column TB_FRAME_FUNCTION.FUNC_URL
  is '功能可以包括多个资源，此处存储此功能的入口URL（可以是Servlet）';
comment on column TB_FRAME_FUNCTION.PARENT_FUNC
  is '功能所对应的上级节点，对应本表的ID,如果值为自身，则表示是根节点';
comment on column TB_FRAME_FUNCTION.ICON_PATH
  is '图标地址';
comment on column TB_FRAME_FUNCTION.SORT_NUM
  is '排序序号，默认值 1';
alter table TB_FRAME_FUNCTION
  add constraint PK_TB_FRAME_FUNCTION primary key (ID);

prompt
prompt Creating table TB_FRAME_MANAGER_STAFF
prompt =====================================
prompt
create table TB_FRAME_MANAGER_STAFF
(
  ID                    NUMBER(10) not null,
  ACCOUNT               NVARCHAR2(50) not null,
  FLAG                  CHAR(1) default '0' not null,
  USER_TYPE             CHAR(1) not null,
  USER_EXT_INFO_ID      NUMBER(10),
  USER_PWD              VARCHAR2(32) not null,
  CHS_NAME              NVARCHAR2(50),
  ENG_NAME              VARCHAR2(50),
  EMAIL_ADDR            NVARCHAR2(50),
  OFFICE_PHONE          NVARCHAR2(50),
  MOBILE_PHONE          NVARCHAR2(50),
  FAX                   NVARCHAR2(50),
  CREATE_DATE           DATE,
  UPDATE_DATE           DATE,
  LOGIN_DATE            DATE,
  LOGIN_IP              VARCHAR2(15),
  COMMENTS              CHAR(1),
  PARENT_STAFF_QRY      NUMBER(10),
  PARENT_STAFF_TYPE_QRY CHAR(1),
  PRE_FLAG              CHAR(1) default '0',
  PASSWORD_UPDATE_DATE  DATE default TRUNC(SYSDATE),
  PASSWORD_RESET_FLAG   VARCHAR2(1) default 'Y'
)
;
comment on table TB_FRAME_MANAGER_STAFF
  is '管理用户基础表，存储管理用户的基本信息内容';
comment on column TB_FRAME_MANAGER_STAFF.FLAG
  is '是否有效，表示此用户的状态：0 — 有效；1 — 禁用；2 ——冻结；3 — 已删除；默认值为0';
comment on column TB_FRAME_MANAGER_STAFF.USER_TYPE
  is '0 — 系统管理员，1 — 总管用户，2 — 总监用户，3 — 分公司用户，4 — 股东用户，5 — 总代理用户，6 — 代理用户，7 — 子账号';
comment on column TB_FRAME_MANAGER_STAFF.USER_EXT_INFO_ID
  is '用户扩展信息ID，系统中，不同的用户类型所对应的个性化扩展信息记录在各自对应的信息表中（系统用户无扩展信息），此处记录对应的记录ID。

根据目前的系统逻辑结构设计，各种用户类型所对应的扩展表使用基础表的ID作为主键，故此处的取值与本表中的ID值相同';
comment on column TB_FRAME_MANAGER_STAFF.USER_PWD
  is '加密的用户密码';
comment on column TB_FRAME_MANAGER_STAFF.LOGIN_DATE
  is '最后登录时间';
comment on column TB_FRAME_MANAGER_STAFF.LOGIN_IP
  is '最后登录IP';
comment on column TB_FRAME_MANAGER_STAFF.PRE_FLAG
  is '上一次用户状态操作操作前的状态';
alter table TB_FRAME_MANAGER_STAFF
  add constraint PK_TB_FRAME_MANAGER_STAFF primary key (ID);

prompt
prompt Creating table TB_FRAME_MEMBER_STAFF
prompt ====================================
prompt
create table TB_FRAME_MEMBER_STAFF
(
  ID                    NUMBER(10) not null,
  ACCOUNT               NVARCHAR2(50) not null,
  FLAG                  CHAR(1) default '0' not null,
  USER_TYPE             CHAR(1) not null,
  USER_EXT_INFO_ID      NUMBER(10),
  USER_PWD              VARCHAR2(32) not null,
  CHS_NAME              NVARCHAR2(50),
  ENG_NAME              VARCHAR2(50),
  EMAIL_ADDR            NVARCHAR2(50),
  OFFICE_PHONE          NVARCHAR2(50),
  MOBILE_PHONE          NVARCHAR2(50),
  FAX                   NVARCHAR2(50),
  CREATE_DATE           DATE,
  UPDATE_DATE           DATE,
  LOGIN_DATE            DATE,
  LOGIN_IP              VARCHAR2(15),
  COMMENTS              NVARCHAR2(200),
  PARENT_STAFF_QRY      NUMBER(10),
  PARENT_STAFF_TYPE_QRY CHAR(1),
  PRE_FLAG              CHAR(1) default '0',
  PASSWORD_UPDATE_DATE  DATE default TRUNC(SYSDATE),
  PASSWORD_RESET_FLAG   VARCHAR2(1) default 'Y'
)
;
comment on column TB_FRAME_MEMBER_STAFF.FLAG
  is '是否有效，表示此用户的状态：0 — 有效；1 — 禁用；2 — 冻结；3 — 已删除；默认值为0';
comment on column TB_FRAME_MEMBER_STAFF.USER_TYPE
  is '9 — 普通会员用户（注意取值编码继承自管理用户基础表中用户类型，以便系统维护）';
comment on column TB_FRAME_MEMBER_STAFF.USER_EXT_INFO_ID
  is '用户扩展信息ID，记录会员扩展信息表中对应的数据记录的ID';
comment on column TB_FRAME_MEMBER_STAFF.USER_PWD
  is '加密的用户密码';
comment on column TB_FRAME_MEMBER_STAFF.LOGIN_DATE
  is '最后登录时间';
comment on column TB_FRAME_MEMBER_STAFF.LOGIN_IP
  is '最后登录IP';
comment on column TB_FRAME_MEMBER_STAFF.PARENT_STAFF_QRY
  is '父ID';
comment on column TB_FRAME_MEMBER_STAFF.PARENT_STAFF_TYPE_QRY
  is '父ID  TYPE';
comment on column TB_FRAME_MEMBER_STAFF.PRE_FLAG
  is '上一次用户状态操作操作前的状态';
alter table TB_FRAME_MEMBER_STAFF
  add constraint PK_TB_FRAME_MEMBER_STAFF primary key (ID);

prompt
prompt Creating table TB_FRAME_ORG
prompt ===========================
prompt
create table TB_FRAME_ORG
(
  ORGID      NUMBER(10) not null,
  UPORGID    NUMBER(10),
  ADOUNAME   NVARCHAR2(254),
  ORGNAM     NVARCHAR2(254),
  SHORTNAME  NVARCHAR2(50),
  SHOWORDER  NUMBER(10),
  ORGTYPE    NUMBER(5),
  ORGAREA    NVARCHAR2(254),
  SUBORGNUM  NUMBER(5),
  SAPID      NVARCHAR2(50),
  ISREALORG  CHAR(1),
  CREATEDATE DATE
)
;
comment on table TB_FRAME_ORG
  is '机构信息表，与EIAC机构表数据一致';
comment on column TB_FRAME_ORG.ORGID
  is '机构ID';
comment on column TB_FRAME_ORG.ORGTYPE
  is '0 公司 Company ；1 分公司 Filiale ；2 部门 Departments ；3 处室 Divisions ；4 科组 Sections ；5 中心 Center
';
alter table TB_FRAME_ORG
  add constraint PK_TB_FRAME_ORG primary key (ORGID);

prompt
prompt Creating table TB_FRAME_ORG_EXT
prompt ===============================
prompt
create table TB_FRAME_ORG_EXT
(
  ORGID         NUMBER(10) not null,
  CHANNEL_ID    NVARCHAR2(3),
  SALE_TYPE     NVARCHAR2(3),
  CMMS_ORG_ID   NUMBER(10),
  CMMS_ORG_NAME NVARCHAR2(60),
  ORG_TYPE1     NVARCHAR2(3),
  ORG_TYPE2     NVARCHAR2(3),
  ACCESS_ORG    NUMBER(10)
)
;
comment on table TB_FRAME_ORG_EXT
  is '机构扩展属性表，记录机构表中不存在，但实际业务中使用到的相关信息';
comment on column TB_FRAME_ORG_EXT.ORGID
  is '机构ID,和tb_crm_org保持一一对应';
comment on column TB_FRAME_ORG_EXT.CHANNEL_ID
  is '机构渠道：000-全部、001-政企、002-家庭、003-个人、004-其他';
comment on column TB_FRAME_ORG_EXT.SALE_TYPE
  is '是否销售部门（营销机构），000-是、001-不是';
comment on column TB_FRAME_ORG_EXT.CMMS_ORG_ID
  is '营销机构ID，对应CMMS机构tb_sm_org 的ORG_ID,非营销机构该字段为空';
comment on column TB_FRAME_ORG_EXT.CMMS_ORG_NAME
  is '营销机构名称，非营销机构该字段为空';
comment on column TB_FRAME_ORG_EXT.ORG_TYPE1
  is '机构横向分类：001-市场运营线、002-网络运营线、003-服务管控线、004-区域分公司';
comment on column TB_FRAME_ORG_EXT.ORG_TYPE2
  is '机构纵向分类：001-经营、002-运营、003-支撑';
comment on column TB_FRAME_ORG_EXT.ACCESS_ORG
  is '默认访问机构，该部门人员默认的访问机构';
alter table TB_FRAME_ORG_EXT
  add constraint PK_TB_FRAME_ORG_EXT primary key (ORGID);

prompt
prompt Creating table TB_FRAME_PARAM
prompt =============================
prompt
create table TB_FRAME_PARAM
(
  ID           NUMBER(10) not null,
  CODE         VARCHAR2(50) not null,
  NAME         NVARCHAR2(50) not null,
  TYPE         CHAR(1) default '2' not null,
  STATE        CHAR(1) default '0' not null,
  VALUE_TYPE   CHAR(1) default '9',
  PARENT_PARAM NUMBER(10),
  SORT_NUM     NUMBER(4) default 1,
  REMARK       NVARCHAR2(200)
)
;
comment on table TB_FRAME_PARAM
  is '参数类别表，存放参数的信息，如“状态”是一个参数类别';
comment on column TB_FRAME_PARAM.ID
  is '代理主键';
comment on column TB_FRAME_PARAM.CODE
  is '参数代码，不可重复。
为了防止命名混乱，本系统做如下约定，参数类别的code命名规则为：模块名_具体的参数类别名称，如 Demo_State';
comment on column TB_FRAME_PARAM.NAME
  is '参数名称';
comment on column TB_FRAME_PARAM.TYPE
  is '参数类型，1-系统初始化参数；2-普通参数';
comment on column TB_FRAME_PARAM.STATE
  is '状态，0-启用；1-未启用；默认值0';
comment on column TB_FRAME_PARAM.VALUE_TYPE
  is '参数值类型，1-整型；2-字符类型；9-其他；默认值9；此字段暂时不使用';
comment on column TB_FRAME_PARAM.PARENT_PARAM
  is '上级参数节点，对应本表的ID,如果值为自身，则表示是根节点';
comment on column TB_FRAME_PARAM.SORT_NUM
  is '排序序号，默认值1';
comment on column TB_FRAME_PARAM.REMARK
  is '备注';
alter table TB_FRAME_PARAM
  add constraint PK_TB_FRAME_PARAM primary key (ID);
create unique index INDEX_PARAM_CODE on TB_FRAME_PARAM (CODE);

prompt
prompt Creating table TB_FRAME_PARAM_VALUE
prompt ===================================
prompt
create table TB_FRAME_PARAM_VALUE
(
  ID       NUMBER(10) not null,
  PARAM_ID NUMBER(10) not null,
  CODE     VARCHAR2(50) not null,
  NAME     NVARCHAR2(50) not null,
  VALUE    NVARCHAR2(20) not null,
  SORT_NUM NUMBER(4) default 1,
  REMARK   NVARCHAR2(200)
)
;
comment on table TB_FRAME_PARAM_VALUE
  is '参数值表，存放参数的值的信息，如参数类别为“状态”的参数，它的取值可以是“启用”“禁用”等，这些数据存储再此表中';
comment on column TB_FRAME_PARAM_VALUE.ID
  is '代理主键';
comment on column TB_FRAME_PARAM_VALUE.PARAM_ID
  is '参数ID，外键，对应于参数类别表（TB_CRM_PARAM）的ID';
comment on column TB_FRAME_PARAM_VALUE.CODE
  is '参数值代码';
comment on column TB_FRAME_PARAM_VALUE.NAME
  is '参数值的名称';
comment on column TB_FRAME_PARAM_VALUE.SORT_NUM
  is '排序序号，默认值1';
comment on column TB_FRAME_PARAM_VALUE.REMARK
  is '备注';
alter table TB_FRAME_PARAM_VALUE
  add constraint PK_TB_FRAME_PARAM_VALUE primary key (ID);
alter table TB_FRAME_PARAM_VALUE
  add constraint FK_FRAME_REF_FRAME4 foreign key (PARAM_ID)
  references TB_FRAME_PARAM (ID);

prompt
prompt Creating table TB_FRAME_RESOURCE
prompt ================================
prompt
create table TB_FRAME_RESOURCE
(
  ID        NUMBER(10) not null,
  FUNC_ID   NUMBER(10) not null,
  RES_CODE  VARCHAR2(60) not null,
  RES_NAME  NVARCHAR2(60) not null,
  RES_STATE CHAR(1) default '0' not null,
  RES_TYPE  CHAR(1) default '0' not null,
  SORT_NUM  NUMBER(4) default 1,
  URL       NVARCHAR2(2000) not null,
  RES_DESC  NVARCHAR2(200)
)
;
comment on table TB_FRAME_RESOURCE
  is '系统资源表，存放系统的功能模块、功能按钮等资源信息';
comment on column TB_FRAME_RESOURCE.ID
  is '代理主键';
comment on column TB_FRAME_RESOURCE.FUNC_ID
  is '资源所对应的功能ID，对应功能表（TB_FRAME_FUNCTION）ID';
comment on column TB_FRAME_RESOURCE.RES_NAME
  is '功能名称';
comment on column TB_FRAME_RESOURCE.RES_STATE
  is '资源状态，0-启用；1-禁用；默认值0';
comment on column TB_FRAME_RESOURCE.RES_TYPE
  is '资源类型；0-页面（url，包括servlet），1-页面资源（功能按钮、超链接等），默认值为0';
comment on column TB_FRAME_RESOURCE.SORT_NUM
  is '排序序号，默认值为1';
comment on column TB_FRAME_RESOURCE.URL
  is 'URL，只有当资源类型（RES_TYPE）字段的值为0时，才有意义，不同URL之间使用半角的;;分割';
comment on column TB_FRAME_RESOURCE.RES_DESC
  is '资源描述';
alter table TB_FRAME_RESOURCE
  add constraint PK_TB_FRAME_RESOURCE primary key (ID);

prompt
prompt Creating table TB_FRAME_ROLES
prompt =============================
prompt
create table TB_FRAME_ROLES
(
  ID          NUMBER(10) not null,
  ROLE_CODE   NVARCHAR2(40) not null,
  ROLE_NAME   NVARCHAR2(40) not null,
  ROLE_LEVEL  CHAR(1) not null,
  ROLE_TYPE   CHAR(1) not null,
  PARENT_ROLE NUMBER(10),
  SORT_NUM    NUMBER(4) default 1,
  REMARK      NVARCHAR2(200)
)
;
comment on table TB_FRAME_ROLES
  is '角色数据表';
comment on column TB_FRAME_ROLES.ID
  is '代理主键，前4000的值预留作初始化角色';
comment on column TB_FRAME_ROLES.ROLE_CODE
  is '角色代码，对于角色类型为私有类型的角色，此处存放对应私有实体ID，否则存放一个自定义的字符串';
comment on column TB_FRAME_ROLES.ROLE_NAME
  is '角色名称';
comment on column TB_FRAME_ROLES.ROLE_LEVEL
  is '0 — 系统管理员；1 — 管理用户；2 — 会员用户';
comment on column TB_FRAME_ROLES.ROLE_TYPE
  is '角色类型：
0 — 资源角色
1 — 标志角色，此类型的角色没有对应的功能信息，只是用来标志一个群体用户具有某个相同的属性而已，此角色在页面上不提供维护（但用户角色授权时需要列出），而在系统初始化时生成，并且所有角色类型为用户群的角色之间，角色代码具有唯一性。
2 — 私有角色，对应具体用户的私有角色，角色代码中存放此私有角色所对应的用户ID。
3 — 内置角色，系统初始化时所生成的内置角色，如系统管理员默认角色、总管默认角色等

说明：私有角色指的是与特定用户关联的角色，这些角色只能使用于对应的私有用户，而不能象普通角色那样被授予给其他用户，对于私有角色，角色代码中存放的即为拥有此私有权限实体的ID';
comment on column TB_FRAME_ROLES.PARENT_ROLE
  is '上级角色节点，对应本表的ID,如果值为 自身，则表示是根节点';
comment on column TB_FRAME_ROLES.SORT_NUM
  is '排序序号，默认值1';
alter table TB_FRAME_ROLES
  add constraint PK_TB_FRAME_ROLES primary key (ID);

prompt
prompt Creating table TB_FRAME_ROLE_FUNC
prompt =================================
prompt
create table TB_FRAME_ROLE_FUNC
(
  ID            NUMBER(10) not null,
  ROLE_ID       NUMBER(10) not null,
  FUNC_ID       NUMBER(10) not null,
  AUTHORIZ_TYPE CHAR(1) default '1' not null,
  REMARK        NVARCHAR2(200)
)
;
comment on table TB_FRAME_ROLE_FUNC
  is '角色所拥有的功能';
comment on column TB_FRAME_ROLE_FUNC.ID
  is '代理主键';
comment on column TB_FRAME_ROLE_FUNC.ROLE_ID
  is '角色ID，外键，对应角色表的ID';
comment on column TB_FRAME_ROLE_FUNC.FUNC_ID
  is '功能ID，外键，对应功能表的ID';
comment on column TB_FRAME_ROLE_FUNC.AUTHORIZ_TYPE
  is '授权类型，由于功能是树型结构，故由此字段决定授权是否传递到子节点。
0-级联授权（即对父节点的授权会自动传递到子节点）；1-独立授权（授权不传递到子节点）；默认值 1';
alter table TB_FRAME_ROLE_FUNC
  add constraint PK_TB_FRAME_ROLE_FUNC primary key (ID);
alter table TB_FRAME_ROLE_FUNC
  add constraint FK_TB_CRM_R_REFEREE_TB_CRM_F foreign key (FUNC_ID)
  references TB_FRAME_FUNCTION (ID);
alter table TB_FRAME_ROLE_FUNC
  add constraint FK_TB_CRM_R_REFERE_TB_CRM_R foreign key (ROLE_ID)
  references TB_FRAME_ROLES (ID);

prompt
prompt Creating table TB_FRAME_STAFF
prompt =============================
prompt
create table TB_FRAME_STAFF
(
  ID           NUMBER(10) not null,
  ORG_ID       NUMBER(10) not null,
  ACCOUNT      NVARCHAR2(50) not null,
  FLAG         CHAR(1) default '0' not null,
  USER_PWD     VARCHAR2(32) not null,
  CHS_NAME     NVARCHAR2(50),
  ENG_NAME     VARCHAR2(50),
  EMAIL_ADDR   NVARCHAR2(50),
  OFFICE_PHONE NVARCHAR2(50),
  MOBILE_PHONE NVARCHAR2(50),
  FAX          NVARCHAR2(50),
  CREATE_DATE  DATE,
  UPDATE_DATE  DATE,
  LOGIN_DATE   DATE,
  LOGIN_IP     VARCHAR2(15),
  COMMENTS     NVARCHAR2(200)
)
;
comment on table TB_FRAME_STAFF
  is '人员信息表';
comment on column TB_FRAME_STAFF.ID
  is '用户工号';
comment on column TB_FRAME_STAFF.ORG_ID
  is '所属机构，对应于机构表（tb_crm_org）的机构ID（ORG_ID）';
comment on column TB_FRAME_STAFF.FLAG
  is '是否有效，表示此用户的状态：0 — 有效；1 — 禁用；2 — 已删除；默认值为0';
comment on column TB_FRAME_STAFF.USER_PWD
  is '加密的用户密码';
comment on column TB_FRAME_STAFF.LOGIN_DATE
  is '最后登录时间';
comment on column TB_FRAME_STAFF.LOGIN_IP
  is '最后登录IP';
alter table TB_FRAME_STAFF
  add constraint PK_TB_FRAME_STAFF primary key (ID);

prompt
prompt Creating table TB_FRAME_STAFF_ROLE
prompt ==================================
prompt
create table TB_FRAME_STAFF_ROLE
(
  ID        NUMBER(10) not null,
  ROLE_ID   NUMBER(10) not null,
  STAFF_ID  NUMBER(10) not null,
  USER_TYPE CHAR(1),
  REMARK    NVARCHAR2(200)
)
;
comment on table TB_FRAME_STAFF_ROLE
  is '存放人员和角色的对应信息';
comment on column TB_FRAME_STAFF_ROLE.ID
  is '代理主键';
comment on column TB_FRAME_STAFF_ROLE.ROLE_ID
  is '角色ID，外键，对应角色表的ID';
comment on column TB_FRAME_STAFF_ROLE.STAFF_ID
  is '员工工号，外键，对应人员表的员工工号';
comment on column TB_FRAME_STAFF_ROLE.USER_TYPE
  is '0 — 系统管理员，1 — 总管用户，2 — 总监用户，3 — 分公司用户，4 — 股东用户，5 — 总代理用户，6 — 代理用户，7 — 普通会员用户';
alter table TB_FRAME_STAFF_ROLE
  add constraint PK_TB_FRAME_STAFF_ROLE primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_EIGHTH
prompt ====================================
prompt
create table TB_GDKLSF_BALL_EIGHTH
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
;
comment on column TB_GDKLSF_BALL_EIGHTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_EIGHTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_EIGHTH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_EIGHTH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_EIGHTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_EIGHTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_EIGHTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_EIGHTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_EIGHTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_EIGHTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_EIGHTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_EIGHTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_EIGHTH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_EIGHTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_EIGHTH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_EIGHTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_EIGHTH
  add constraint PK_TB_GDKLSF_BALL_EIGHTH primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_FIFTH
prompt ===================================
prompt
create table TB_GDKLSF_BALL_FIFTH
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
;
comment on column TB_GDKLSF_BALL_FIFTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_FIFTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_FIFTH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_FIFTH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_FIFTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_FIFTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_FIFTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIFTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIFTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIFTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIFTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_FIFTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_FIFTH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_FIFTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_FIFTH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_FIFTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_FIFTH
  add constraint PK_TB_GDKLSF_BALL_FIFTH primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_FIRST
prompt ===================================
prompt
create table TB_GDKLSF_BALL_FIRST
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
;
comment on table TB_GDKLSF_BALL_FIRST
  is '广东快乐十分第一球对应的投注表，记录会员投注信息数据';
comment on column TB_GDKLSF_BALL_FIRST.ID
  is '代理主键';
comment on column TB_GDKLSF_BALL_FIRST.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_FIRST.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_FIRST.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_FIRST.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_FIRST.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_FIRST.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_FIRST.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIRST.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIRST.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIRST.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FIRST.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_FIRST.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_FIRST.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_FIRST.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_FIRST.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_FIRST.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_FIRST
  add constraint PK_TB_GDKLSF_BALL_FIRST primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_FORTH
prompt ===================================
prompt
create table TB_GDKLSF_BALL_FORTH
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
;
comment on column TB_GDKLSF_BALL_FORTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_FORTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_FORTH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_FORTH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_FORTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_FORTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_FORTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FORTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FORTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FORTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_FORTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_FORTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_FORTH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_FORTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_FORTH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_FORTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_FORTH
  add constraint PK_TB_GDKLSF_BALL_FORTH primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_SECOND
prompt ====================================
prompt
create table TB_GDKLSF_BALL_SECOND
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
  RATE_STOCKHOLDER       NUMBER(5,2),
  RATE_GEN_AGENT         NUMBER(5,2),
  RATE_BRANCH            NUMBER(5,2),
  RATE_CHIEF             NUMBER(5,2),
  RATE_AGENT             NUMBER(5,2),
  COMMISSION_MEMBER      NUMBER,
  COMMISSION_AGENT       NUMBER,
  COMMISSION_STOCKHOLDER NUMBER,
  COMMISSION_GEN_AGENT   NUMBER,
  COMMISSION_BRANCH      NUMBER,
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
;
comment on column TB_GDKLSF_BALL_SECOND.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_SECOND.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_SECOND.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_SECOND.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_SECOND.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_SECOND.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_SECOND.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SECOND.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SECOND.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SECOND.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SECOND.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_SECOND.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_SECOND.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_SECOND.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_SECOND.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_SECOND.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_SECOND
  add constraint PK_TB_GDKLSF_BALL_SECOND primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_SEVENTH
prompt =====================================
prompt
create table TB_GDKLSF_BALL_SEVENTH
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
;
comment on column TB_GDKLSF_BALL_SEVENTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_SEVENTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_SEVENTH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_SEVENTH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_SEVENTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_SEVENTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_SEVENTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SEVENTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SEVENTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SEVENTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SEVENTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_SEVENTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_SEVENTH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_SEVENTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_SEVENTH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_SEVENTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_SEVENTH
  add constraint PK_TB_GDKLSF_BALL_SEVENTH primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_SIXTH
prompt ===================================
prompt
create table TB_GDKLSF_BALL_SIXTH
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
;
comment on column TB_GDKLSF_BALL_SIXTH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_SIXTH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_SIXTH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_SIXTH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_SIXTH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_SIXTH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_SIXTH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SIXTH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SIXTH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SIXTH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_SIXTH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_SIXTH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_SIXTH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_SIXTH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_SIXTH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_SIXTH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_SIXTH
  add constraint PK_TB_GDKLSF_BALL_SIXTH primary key (ID);

prompt
prompt Creating table TB_GDKLSF_BALL_THIRD
prompt ===================================
prompt
create table TB_GDKLSF_BALL_THIRD
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
;
comment on column TB_GDKLSF_BALL_THIRD.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_BALL_THIRD.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_BALL_THIRD.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_BALL_THIRD.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_BALL_THIRD.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_BALL_THIRD.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_BALL_THIRD.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_THIRD.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_THIRD.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_THIRD.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_BALL_THIRD.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_BALL_THIRD.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_BALL_THIRD.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_BALL_THIRD.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_BALL_THIRD.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_BALL_THIRD.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_BALL_THIRD
  add constraint PK_TB_GDKLSF_BALL_THIRD primary key (ID);

prompt
prompt Creating table TB_GDKLSF_DOUBLE_SIDE
prompt ====================================
prompt
create table TB_GDKLSF_DOUBLE_SIDE
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
;
comment on column TB_GDKLSF_DOUBLE_SIDE.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_DOUBLE_SIDE.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_DOUBLE_SIDE.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_DOUBLE_SIDE.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_DOUBLE_SIDE.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_DOUBLE_SIDE.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_DOUBLE_SIDE.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_DOUBLE_SIDE.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_DOUBLE_SIDE.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_DOUBLE_SIDE.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_DOUBLE_SIDE.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_DOUBLE_SIDE.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_DOUBLE_SIDE.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_DOUBLE_SIDE.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_DOUBLE_SIDE.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_DOUBLE_SIDE.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_DOUBLE_SIDE
  add constraint PK_TB_GDKLSF_DOUBLE_SIDE primary key (ID);

prompt
prompt Creating table TB_GDKLSF_HIS
prompt ============================
prompt
create table TB_GDKLSF_HIS
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
;
comment on table TB_GDKLSF_HIS
  is '投注历史表，存储如下数据表中的历史数据：
1、投注（广东快乐十分连码）表（TB_GDKLSF_STRAIGHTTHROUGH）
2、投注（广东快乐十分第一球）表（TB_GDKLSF_BALL_FIRST）
3、投注（广东快乐十分第二球）表（TB_GDKLSF_BALL_SECOND）
4、投注（广东快乐十分第三球）表（TB_GDKLSF_BALL_THIRD）
5、投注（广东快乐十分第四球）表（TB_GDKLSF_BALL_FORTH）
6、投注（广东快乐十分第五球）表（TB_GDKLSF_BALL_FIFTH）
7、投注（广东快乐十分第六球）表（TB_GDKLSF_BALL_SIXTH）
8、投注（广东快乐十分第七球）表（TB_GDKLSF_BALL_SEVENTH）
9、投注（广东快乐十分第八球）表（TB_GDKLSF_BALL_EIGHTH）
10、投注（广东快乐十分两面盘及龙虎）表（TB_GDKLSF_DOUBLE_SIDE）';
comment on column TB_GDKLSF_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_GDKLSF_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_GDKLSF_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_HIS.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_HIS.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_GDKLSF_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_GDKLSF_HIS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_HIS
  add constraint PK_TB_GDKLSF_HIS primary key (ID);
create index INDEX_GD_HIS_BETTING_DATE_USER on TB_GDKLSF_HIS (BETTING_USER_ID, BETTING_DATE);
create index INDEX_GD_HIS_BETTING_USER on TB_GDKLSF_HIS (BETTING_USER_ID);

prompt
prompt Creating table TB_GDKLSF_PERIODS_INFO
prompt =====================================
prompt
create table TB_GDKLSF_PERIODS_INFO
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
;
comment on table TB_GDKLSF_PERIODS_INFO
  is '广东快乐十分盘期信息表';
comment on column TB_GDKLSF_PERIODS_INFO.ID
  is '代理主键';
comment on column TB_GDKLSF_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_GDKLSF_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_GDKLSF_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_GDKLSF_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_GDKLSF_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_GDKLSF_PERIODS_INFO
  add constraint PK_TB_GDKLSF_PERIODS_INFO primary key (ID);
create index INDEX_LOTTERY_TIME on TB_GDKLSF_PERIODS_INFO (LOTTERY_TIME);
create index INDEX_OPEN_TIME on TB_GDKLSF_PERIODS_INFO (OPEN_QUOT_TIME);
create index INDEX_STOP_TIME on TB_GDKLSF_PERIODS_INFO (STOP_QUOT_TIME);

prompt
prompt Creating table TB_GDKLSF_STRAIGHTTHROUGH
prompt ========================================
prompt
create table TB_GDKLSF_STRAIGHTTHROUGH
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
;
comment on column TB_GDKLSF_STRAIGHTTHROUGH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.MONEY
  is '投注金额，单位分';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.PLATE
  is 'A、B、C等盘面';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.BETTING_DATE
  is '投注时间';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.WIN_AMOUNT
  is '单位分';
comment on column TB_GDKLSF_STRAIGHTTHROUGH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_GDKLSF_STRAIGHTTHROUGH
  add constraint PK_TB_GDKLSF_STRAIGHTTHROUGH primary key (ID);

prompt
prompt Creating table TB_GEN_AGENT_STAFF_EXT
prompt =====================================
prompt
create table TB_GEN_AGENT_STAFF_EXT
(
  MANAGER_STAFF_ID      NUMBER(10) not null,
  PARENT_STAFF          NUMBER(10),
  REPLENISHMENT         CHAR(1) not null,
  GEN_AGENT_RATE        NUMBER(5,2),
  PURE_ACCOUNTED        CHAR(1),
  SHAREHOLDER_RATE      NUMBER(5,2),
  TOTAL_CREDIT_LINE     NUMBER(10),
  AVAILABLE_CREDIT_LINE NUMBER(10),
  CHIEF_STAFF           NUMBER(10),
  BRANCH_STAFF          NUMBER(10),
  RATE_RESTRICT         CHAR(1),
  BELOW_RATE_LIMIT      NUMBER(2)
)
;
comment on table TB_GEN_AGENT_STAFF_EXT
  is '此表中记录总代理用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了总代理用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
总代理用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “5 — 总代理用户”';
comment on column TB_GEN_AGENT_STAFF_EXT.MANAGER_STAFF_ID
  is '总代理用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_GEN_AGENT_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_GEN_AGENT_STAFF_EXT.REPLENISHMENT
  is '走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞';
comment on column TB_GEN_AGENT_STAFF_EXT.PURE_ACCOUNTED
  is '0 — 纯占；1 — 非纯占';
comment on column TB_GEN_AGENT_STAFF_EXT.SHAREHOLDER_RATE
  is '股东占成';
comment on column TB_GEN_AGENT_STAFF_EXT.CHIEF_STAFF
  is '总代理所对应的总监ID';
comment on column TB_GEN_AGENT_STAFF_EXT.BRANCH_STAFF
  is '总代理所对应的分公司ID';
comment on column TB_GEN_AGENT_STAFF_EXT.RATE_RESTRICT
  is '是否限制占成';
comment on column TB_GEN_AGENT_STAFF_EXT.BELOW_RATE_LIMIT
  is '限制占成限额';
alter table TB_GEN_AGENT_STAFF_EXT
  add constraint PK_TB_GEN_AGENT_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_HKLHC_BB
prompt ==========================
prompt
create table TB_HKLHC_BB
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
;
comment on column TB_HKLHC_BB.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_BB.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_BB.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_BB.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_BB.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_BB.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_BB.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_BB.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_BB.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_BB.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_BB.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_BB.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_BB.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_BB.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_BB.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_BB.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_BB
  add constraint PK_TB_HKLHC_BB primary key (ID);

prompt
prompt Creating table TB_HKLHC_GG
prompt ==========================
prompt
create table TB_HKLHC_GG
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
  ATTRIBUTE              VARCHAR2(60),
  PERIODS_NUM            VARCHAR2(11) not null,
  PLATE                  CHAR(1),
  BETTING_DATE           DATE default sysdate not null,
  WIN_STATE              CHAR(1) default '0' not null,
  WIN_AMOUNT             NUMBER,
  ODDS                   NUMBER(12,4) not null,
  ODDS2                  NUMBER(12,4),
  SELECT_ODDS            VARCHAR2(120),
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
;
comment on column TB_HKLHC_GG.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_GG.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_GG.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_GG.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_GG.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_GG.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_GG.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_GG.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_GG.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_GG.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_GG.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_GG.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_GG.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_GG.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_GG.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_GG.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_GG.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_HKLHC_GG.ODDS2
  is '过关投注是插入和赔率';
comment on column TB_HKLHC_GG.SELECT_ODDS
  is '记录用户下注所有球的赔率 ';
alter table TB_HKLHC_GG
  add constraint PK_TB_HKLHC_GG primary key (ID);

prompt
prompt Creating table TB_HKLHC_HIS
prompt ===========================
prompt
create table TB_HKLHC_HIS
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
  ATTRIBUTE              VARCHAR2(60),
  SPLIT_ATTRIBUTE        VARCHAR2(25),
  PERIODS_NUM            VARCHAR2(11) not null,
  PLATE                  CHAR(1),
  BETTING_DATE           DATE default sysdate not null,
  WIN_STATE              CHAR(1) default '0' not null,
  WIN_AMOUNT             NUMBER,
  ODDS                   NUMBER(12,4) not null,
  ODDS2                  NUMBER(12,4),
  UPDATE_DATE            DATE,
  COMMISSION_TYPE        VARCHAR2(20),
  REMARK                 NVARCHAR2(200),
  SELECT_ODDS            VARCHAR2(120)
)
;
comment on table TB_HKLHC_HIS
  is '投注历史表，存储如下数据表中的历史数据：
1、投注（香港六合彩特码）表（TB_HKLHC_TE_MA）
2、投注（香港六合彩正码）表（TB_HKLHC_Z_MA）
3、投注（香港六合彩正特码）表（TB_HKLHC_Z_TE_MA）
4、投注（香港六合彩正码一至六）表（TB_HKLHC_ZM16）
5、投注（香港六合彩特码生肖）表（TB_HKLHC_TM_SX）
6、投注（香港六合彩生肖尾数）表（TB_HKLHC_SX_WS）
7、投注（香港六合彩半波）表（TB_HKLHC_BB）
8、投注（香港六合彩生肖连）表（TB_HKLHC_SXL）
9、投注（香港六合彩尾数连）表（TB_HKLHC_WSL）
10、投注（香港六合彩连码）表（TB_HKLHC_LM）
11、投注（香港六合彩过关）表（TB_HKLHC_GG）
12、投注（香港六合彩六肖）表（TB_HKLHC_LX）
13、投注（香港六合彩五不中）表（TB_HKLHC_WBZ）';
comment on column TB_HKLHC_HIS.ORIGIN_TB_NAME
  is '此历史数据所对应的原始存储数据表名称';
comment on column TB_HKLHC_HIS.ORIGIN_ID
  is '此历史数据所对应的原始存储数据ID';
comment on column TB_HKLHC_HIS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_HIS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_HIS.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_HIS.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_HIS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_HIS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_HIS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_HIS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_HIS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_HIS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_HIS.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_HIS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_HIS.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_HIS.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_HIS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_HIS.WIN_AMOUNT
  is '单位元';
comment on column TB_HKLHC_HIS.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_HKLHC_HIS.ODDS2
  is '赔率2，只有当投注类型为连码时才有效';
comment on column TB_HKLHC_HIS.SELECT_ODDS
  is '记录用户下注所有球的赔率 ';
alter table TB_HKLHC_HIS
  add constraint PK_TB_HKLHC_HIS primary key (ID);

prompt
prompt Creating table TB_HKLHC_LM
prompt ==========================
prompt
create table TB_HKLHC_LM
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
  ODDS2                  NUMBER(12,4),
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
;
comment on column TB_HKLHC_LM.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_LM.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_LM.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_LM.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_LM.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_LM.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_LM.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LM.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LM.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LM.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LM.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_LM.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_LM.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_LM.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_LM.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_LM.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_LM.ODDS
  is '当前投注单所对应的赔率（需要存两种赔率）';
comment on column TB_HKLHC_LM.ODDS2
  is '赔率2，只有当投注类型为连码时才有效';
alter table TB_HKLHC_LM
  add constraint PK_TB_HKLHC_LM primary key (ID);

prompt
prompt Creating table TB_HKLHC_LX
prompt ==========================
prompt
create table TB_HKLHC_LX
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
;
comment on column TB_HKLHC_LX.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_LX.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_LX.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_LX.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_LX.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_LX.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_LX.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LX.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LX.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LX.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_LX.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_LX.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_LX.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_LX.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_LX.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_LX.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_LX.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_LX
  add constraint PK_TB_HKLHC_LX primary key (ID);

prompt
prompt Creating table TB_HKLHC_PERIODS_INFO
prompt ====================================
prompt
create table TB_HKLHC_PERIODS_INFO
(
  ID                NUMBER(10) not null,
  PERIODS_NUM       VARCHAR2(11) not null,
  OPEN_QUOT_TIME    DATE,
  LOTTERY_TIME      DATE not null,
  STOP_QUOT_TIME    DATE not null,
  AUTO_STOP_QUOT    INTEGER,
  LOTTERY_TIME_INFO VARCHAR2(5),
  PERIODS_INFO      VARCHAR2(3),
  RESULT1           INTEGER,
  RESULT2           INTEGER,
  RESULT3           INTEGER,
  RESULT4           INTEGER,
  RESULT5           INTEGER,
  RESULT6           INTEGER,
  RESULT7           INTEGER,
  STATE             CHAR(1) default '1' not null,
  CREATE_USER       NUMBER(10),
  CREATE_TIME       DATE default sysdate not null
)
;
comment on column TB_HKLHC_PERIODS_INFO.PERIODS_NUM
  is '投注期数，形如 20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号';
comment on column TB_HKLHC_PERIODS_INFO.RESULT1
  is '盘期所对应的开奖结果中的第一球数值';
comment on column TB_HKLHC_PERIODS_INFO.RESULT2
  is '盘期所对应的开奖结果中的第二球数值';
comment on column TB_HKLHC_PERIODS_INFO.STATE
  is '盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3 — 已封盘；4 — 已开奖。默认值为 1 — 未开盘';
comment on column TB_HKLHC_PERIODS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_HKLHC_PERIODS_INFO
  add constraint PK_TB_HKLHC_PERIODS_INFO primary key (ID);

prompt
prompt Creating table TB_HKLHC_STRAIGHTTHROUGH
prompt =======================================
prompt
create table TB_HKLHC_STRAIGHTTHROUGH
(
  ID               NUMBER(10) not null,
  ORDER_NO         VARCHAR2(8),
  TYPE_CODE        VARCHAR2(100) not null,
  MONEY            NUMBER not null,
  BETTING_USER_ID  NUMBER(10) not null,
  CHIEFSTAFF       NUMBER(10),
  BRANCHSTAFF      NUMBER(10),
  STOCKHOLDERSTAFF NUMBER(10),
  GENAGENSTAFF     NUMBER(10),
  AGENTSTAFF       NUMBER(10),
  ATTRIBUTE        VARCHAR2(50),
  PERIODS_NUM      VARCHAR2(11) not null,
  PLATE            CHAR(1),
  BETTING_DATE     DATE default sysdate not null,
  WIN_STATE        CHAR(1) default '0' not null,
  ODDS             VARCHAR2(30) not null,
  UPDATE_DATE      DATE,
  REMARK           NVARCHAR2(200),
  COMMISSION       VARCHAR2(20),
  RATE             VARCHAR2(20)
)
;
comment on column TB_HKLHC_STRAIGHTTHROUGH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_STRAIGHTTHROUGH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_STRAIGHTTHROUGH.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_STRAIGHTTHROUGH.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_STRAIGHTTHROUGH.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_STRAIGHTTHROUGH.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_STRAIGHTTHROUGH.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_STRAIGHTTHROUGH.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_STRAIGHTTHROUGH.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_STRAIGHTTHROUGH.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_STRAIGHTTHROUGH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_STRAIGHTTHROUGH.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_STRAIGHTTHROUGH.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_STRAIGHTTHROUGH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖，默认值 0 — 未开奖';
comment on column TB_HKLHC_STRAIGHTTHROUGH.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_STRAIGHTTHROUGH
  add constraint PK_TB_HKLHC_STRAIGHTTHROUGH primary key (ID);

prompt
prompt Creating table TB_HKLHC_SXL
prompt ===========================
prompt
create table TB_HKLHC_SXL
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
  SELECT_ODDS            VARCHAR2(120),
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
;
comment on column TB_HKLHC_SXL.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_SXL.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_SXL.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_SXL.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_SXL.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_SXL.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_SXL.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SXL.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SXL.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SXL.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SXL.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_SXL.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_SXL.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_SXL.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_SXL.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_SXL.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_SXL.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_HKLHC_SXL.SELECT_ODDS
  is '记录用户下注所有球的赔率 ';
alter table TB_HKLHC_SXL
  add constraint PK_TB_HKLHC_SXL primary key (ID);

prompt
prompt Creating table TB_HKLHC_SX_WS
prompt =============================
prompt
create table TB_HKLHC_SX_WS
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
;
comment on column TB_HKLHC_SX_WS.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_SX_WS.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_SX_WS.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_SX_WS.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_SX_WS.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_SX_WS.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_SX_WS.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SX_WS.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SX_WS.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SX_WS.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_SX_WS.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_SX_WS.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_SX_WS.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_SX_WS.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_SX_WS.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_SX_WS.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_SX_WS
  add constraint PK_TB_HKLHC_SX_WS primary key (ID);

prompt
prompt Creating table TB_HKLHC_TE_MA
prompt =============================
prompt
create table TB_HKLHC_TE_MA
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
;
comment on column TB_HKLHC_TE_MA.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_TE_MA.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_TE_MA.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_TE_MA.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_TE_MA.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_TE_MA.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_TE_MA.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TE_MA.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TE_MA.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TE_MA.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TE_MA.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_TE_MA.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_TE_MA.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_TE_MA.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_TE_MA.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_TE_MA.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_TE_MA
  add constraint PK_TB_HKLHC_TE_MA primary key (ID);

prompt
prompt Creating table TB_HKLHC_TM_SX
prompt =============================
prompt
create table TB_HKLHC_TM_SX
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
;
comment on column TB_HKLHC_TM_SX.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_TM_SX.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_TM_SX.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_TM_SX.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_TM_SX.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_TM_SX.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_TM_SX.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TM_SX.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TM_SX.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TM_SX.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_TM_SX.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_TM_SX.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_TM_SX.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_TM_SX.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_TM_SX.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_TM_SX.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_TM_SX
  add constraint PK_TB_HKLHC_TM_SX primary key (ID);

prompt
prompt Creating table TB_HKLHC_WBZ
prompt ===========================
prompt
create table TB_HKLHC_WBZ
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
  SELECT_ODDS            VARCHAR2(120),
  COMMISSION_TYPE        VARCHAR2(20),
  UPDATE_DATE            DATE,
  REMARK                 NVARCHAR2(200)
)
;
comment on column TB_HKLHC_WBZ.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_WBZ.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_WBZ.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_WBZ.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_WBZ.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_WBZ.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_WBZ.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WBZ.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WBZ.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WBZ.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WBZ.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_WBZ.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_WBZ.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_WBZ.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_WBZ.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_WBZ.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_WBZ.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_HKLHC_WBZ.SELECT_ODDS
  is '记录用户下注所有球的赔率 ';
alter table TB_HKLHC_WBZ
  add constraint PK_TB_HKLHC_WBZ primary key (ID);

prompt
prompt Creating table TB_HKLHC_WSL
prompt ===========================
prompt
create table TB_HKLHC_WSL
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
  REMARK                 NVARCHAR2(200),
  SELECT_ODDS            VARCHAR2(120)
)
;
comment on column TB_HKLHC_WSL.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_WSL.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_WSL.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_WSL.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_WSL.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_WSL.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_WSL.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WSL.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WSL.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WSL.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_WSL.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_HKLHC_WSL.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_WSL.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_WSL.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_WSL.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_WSL.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_WSL.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_HKLHC_WSL.SELECT_ODDS
  is '记录用户下注所有球的赔率 ';
alter table TB_HKLHC_WSL
  add constraint PK_TB_HKLHC_WSL primary key (ID);

prompt
prompt Creating table TB_HKLHC_ZM16
prompt ============================
prompt
create table TB_HKLHC_ZM16
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
;
comment on column TB_HKLHC_ZM16.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_ZM16.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_ZM16.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_ZM16.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_ZM16.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_ZM16.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_ZM16.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_ZM16.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_ZM16.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_ZM16.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_ZM16.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_ZM16.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_ZM16.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_ZM16.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_ZM16.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_ZM16.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_ZM16
  add constraint PK_TB_HKLHC_ZM16 primary key (ID);

prompt
prompt Creating table TB_HKLHC_Z_MA
prompt ============================
prompt
create table TB_HKLHC_Z_MA
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
;
comment on column TB_HKLHC_Z_MA.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_Z_MA.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_Z_MA.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_Z_MA.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_Z_MA.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_Z_MA.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_Z_MA.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_MA.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_MA.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_MA.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_MA.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_Z_MA.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_Z_MA.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_Z_MA.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_Z_MA.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_Z_MA.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_Z_MA
  add constraint PK_TB_HKLHC_Z_MA primary key (ID);

prompt
prompt Creating table TB_HKLHC_Z_TE_MA
prompt ===============================
prompt
create table TB_HKLHC_Z_TE_MA
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
;
comment on column TB_HKLHC_Z_TE_MA.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_HKLHC_Z_TE_MA.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_HKLHC_Z_TE_MA.MONEY
  is '投注金额，单位分';
comment on column TB_HKLHC_Z_TE_MA.COMPOUND_NUM
  is '复式笔数，只有在复式投注时才有效';
comment on column TB_HKLHC_Z_TE_MA.BETTING_USER_ID
  is '投注会员ID，对应于人员表（TB_FRAME_STAFF）的主键';
comment on column TB_HKLHC_Z_TE_MA.CHIEFSTAFF
  is '会员用户层次上所对应的总监';
comment on column TB_HKLHC_Z_TE_MA.BRANCHSTAFF
  is '会员用户层次上所对应的分公司，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_TE_MA.STOCKHOLDERSTAFF
  is '会员用户层次上所对应的股东，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_TE_MA.GENAGENSTAFF
  is '会员用户层次上所对应的总代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_TE_MA.AGENTSTAFF
  is '会员用户层次上所对应的代理，如果是上级用户所对应的直属会员，则为空';
comment on column TB_HKLHC_Z_TE_MA.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_HKLHC_Z_TE_MA.PLATE
  is 'A、B、C等盘面';
comment on column TB_HKLHC_Z_TE_MA.BETTING_DATE
  is '投注时间';
comment on column TB_HKLHC_Z_TE_MA.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_HKLHC_Z_TE_MA.WIN_AMOUNT
  is '单位分';
comment on column TB_HKLHC_Z_TE_MA.ODDS
  is '当前投注单所对应的赔率';
alter table TB_HKLHC_Z_TE_MA
  add constraint PK_TB_HKLHC_Z_TE_MA primary key (ID);

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
  REMARK                 NVARCHAR2(200),
  PLUS_ODDS              NUMBER(2) default 1
)
;
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
comment on column TB_JSSB.PLUS_ODDS
  is '江苏骰宝附加赔率，主要用于三军玩法';
alter table TB_JSSB
  add constraint PK_TB_JSSB primary key (ID);

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
  REMARK                 NVARCHAR2(200),
  PLUS_ODDS              NUMBER(2) default 1
)
;
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
comment on column TB_JSSB_HIS.PLUS_ODDS
  is '江苏骰宝附加赔率，主要用于三军玩法';
alter table TB_JSSB_HIS
  add constraint PK_TB_JSSB_HIS primary key (ID);
create index INDEX_JS_HIS_BETTING_DATE_USER on TB_JSSB_HIS (BETTING_USER_ID, BETTING_DATE);
create index INDEX_JS_HIS_BETTING_USER on TB_JSSB_HIS (BETTING_USER_ID);

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
;
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
  add constraint PK_TB_JSSB_PERIODS_INFO primary key (ID);

prompt
prompt Creating table TB_LOGIN_LOG_INFO
prompt ================================
prompt
create table TB_LOGIN_LOG_INFO
(
  ID                    NUMBER(10) not null,
  USER_ID               NUMBER(10),
  ACCOUNT               NVARCHAR2(50) not null,
  USER_TYPE             CHAR(1) not null,
  SHOPS_CODE            VARCHAR2(10),
  LOGIN_DATE            DATE,
  LOGIN_IP              VARCHAR2(15),
  SESSION_ID            VARCHAR2(50),
  LOGOUT_DATE           DATE,
  LOGIN_STATE           CHAR(1),
  SUB_LOGIN_STATE       CHAR(1),
  INFO                  NVARCHAR2(500),
  CHIEF_STAFF_ID        NUMBER(10),
  BRANCH_STAFF_ID       NUMBER(10),
  STOCKHOLDER_STAFF_ID  NUMBER(10),
  GEN_AGENT_STAFF_ID    NUMBER(10),
  AGENT_STAFF_ID        NUMBER(10),
  CHIEF_STAFF_ACC       NVARCHAR2(50),
  AGENT_STAFF_ACC       NVARCHAR2(50),
  GEN_AGENT_STAFF_ACC   NVARCHAR2(50),
  STOCKHOLDER_STAFF_ACC NVARCHAR2(50),
  BRANCH_STAFF_ACC      NVARCHAR2(50),
  REMARK                NVARCHAR2(200)
)
;
comment on table TB_LOGIN_LOG_INFO
  is '登陆日志信息表';
comment on column TB_LOGIN_LOG_INFO.USER_TYPE
  is '0 — 系统管理员，1 — 总管用户，2 — 总监用户，3 — 分公司用户，4 — 股东用户，5 — 总代理用户，6 — 代理用户，7 — 子账号，9 — 会员用户';
comment on column TB_LOGIN_LOG_INFO.LOGIN_DATE
  is '登录时间';
comment on column TB_LOGIN_LOG_INFO.LOGIN_IP
  is '登录IP';
comment on column TB_LOGIN_LOG_INFO.LOGOUT_DATE
  is '登出时间';
comment on column TB_LOGIN_LOG_INFO.LOGIN_STATE
  is '0 — 登陆成功；1 — 登陆失败';
comment on column TB_LOGIN_LOG_INFO.SUB_LOGIN_STATE
  is '登陆子状态，暂不使用';
comment on column TB_LOGIN_LOG_INFO.CHIEF_STAFF_ID
  is '所对应的总监ID';
comment on column TB_LOGIN_LOG_INFO.BRANCH_STAFF_ID
  is '所对应的分公司ID';
comment on column TB_LOGIN_LOG_INFO.STOCKHOLDER_STAFF_ID
  is '所对应的股东ID';
comment on column TB_LOGIN_LOG_INFO.GEN_AGENT_STAFF_ID
  is '所对应的总代理ID';
comment on column TB_LOGIN_LOG_INFO.AGENT_STAFF_ID
  is '所对应的代理ID';
comment on column TB_LOGIN_LOG_INFO.CHIEF_STAFF_ACC
  is '所对应的总监账号';
comment on column TB_LOGIN_LOG_INFO.AGENT_STAFF_ACC
  is '所对应的代理账号';
comment on column TB_LOGIN_LOG_INFO.GEN_AGENT_STAFF_ACC
  is '所对应的总代理账号';
comment on column TB_LOGIN_LOG_INFO.STOCKHOLDER_STAFF_ACC
  is '所对应的股东账号';
comment on column TB_LOGIN_LOG_INFO.BRANCH_STAFF_ACC
  is '所对应的分公司账号';
alter table TB_LOGIN_LOG_INFO
  add constraint PK_TB_LOGIN_LOG_INFO primary key (ID);

prompt
prompt Creating table TB_LOTTERY_STATUS
prompt ================================
prompt
create table TB_LOTTERY_STATUS
(
  ID          NUMBER(10) not null,
  PLAY_TYPE   VARCHAR2(10) not null,
  STATE       CHAR(1) default '0' not null,
  MODIFY_DATE DATE default sysdate
)
;
comment on table TB_LOTTERY_STATUS
  is '彩种状态表，可以开放或关闭某个彩种';

prompt
prompt Creating table TB_MANAGER_STAFF_EXT
prompt ===================================
prompt
create table TB_MANAGER_STAFF_EXT
(
  MANAGER_STAFF_ID NUMBER(10) not null
)
;
comment on table TB_MANAGER_STAFF_EXT
  is '记录总管用户扩展信息';
comment on column TB_MANAGER_STAFF_EXT.MANAGER_STAFF_ID
  is '总管用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
alter table TB_MANAGER_STAFF_EXT
  add constraint PK_TB_MANAGER_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_MEMBER_STAFF_EXT
prompt ==================================
prompt
create table TB_MEMBER_STAFF_EXT
(
  MEMBER_STAFF_ID       NUMBER(10) not null,
  PARENT_STAFF          NUMBER(10),
  PARENT_USER_TYPE      CHAR(1),
  PLATE                 CHAR(1),
  TOTAL_CREDIT_LINE     NUMBER(10),
  AVAILABLE_CREDIT_LINE NUMBER(10),
  RATE                  NUMBER(5,2),
  BACK_WATER            NUMBER(5,2),
  CHIEF_STAFF           NUMBER(10),
  BRANCH_STAFF          NUMBER(10),
  STOCKHOLDER_STAFF     NUMBER(10),
  GEN_AGENT_STAFF       NUMBER(10),
  AGENT_STAFF           NUMBER(10)
)
;
comment on table TB_MEMBER_STAFF_EXT
  is '此表中记录会员用户扩展信息，会员用户基础表（TB_FRAME_MEMBER_STAFF）中存储了会员用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
-----------------
代理用户在会员用户基础表中所对应的用户类型（USER_TYPE）的取值为 “0 — 普通会员用户”';
comment on column TB_MEMBER_STAFF_EXT.MEMBER_STAFF_ID
  is '普通会员用户基础信息表所对应的记录ID，对应会员用户基础表（TB_FRAME_MEMBER_STAFF）的ID';
comment on column TB_MEMBER_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_MEMBER_STAFF_EXT.CHIEF_STAFF
  is '会员所对应的总监ID';
comment on column TB_MEMBER_STAFF_EXT.BRANCH_STAFF
  is '会员所对应的分公司ID';
comment on column TB_MEMBER_STAFF_EXT.STOCKHOLDER_STAFF
  is '会员所对应的股东ID';
comment on column TB_MEMBER_STAFF_EXT.GEN_AGENT_STAFF
  is '会员所对应的总代理ID';
comment on column TB_MEMBER_STAFF_EXT.AGENT_STAFF
  is '会员所对应的代理ID';
alter table TB_MEMBER_STAFF_EXT
  add constraint PK_TB_MEMBER_STAFF_EXT primary key (MEMBER_STAFF_ID);

prompt
prompt Creating table TB_OPEN_PLAY_ODDS
prompt ================================
prompt
create table TB_OPEN_PLAY_ODDS
(
  ID                  NUMBER(10) not null,
  SHOPS_CODE          CHAR(4) not null,
  AUTO_ODDS_QUOTAS    NUMBER not null,
  AUTO_ODDS           NUMBER(12,4),
  ODDS_TYPE           VARCHAR2(30) not null,
  LOWEST_ODDS         NUMBER(12,4),
  OPENING_ODDS        NUMBER(12,4),
  BIGEST_ODDS         NUMBER(12,4),
  CUT_ODDS_B          NUMBER(12,4),
  CUT_ODDS_C          NUMBER(12,4),
  OPENING_UPDATE_DATE DATE,
  OPENING_UPDATE_USER NUMBER(10),
  CREATE_USER         NUMBER(10) not null,
  CREATE_TIME         DATE default sysdate not null,
  REMARK              NVARCHAR2(200)
)
;
comment on table TB_OPEN_PLAY_ODDS
  is '开盘赔率设置信息';
comment on column TB_OPEN_PLAY_ODDS.ODDS_TYPE
  is '赔率主类型（总和大，大小 等）';
comment on column TB_OPEN_PLAY_ODDS.OPENING_ODDS
  is '开盘赔率值';
comment on column TB_OPEN_PLAY_ODDS.OPENING_UPDATE_USER
  is '开盘赔率更新人ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）中的主键';
comment on column TB_OPEN_PLAY_ODDS.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_OPEN_PLAY_ODDS
  add constraint PK_TB_OPEN_PLAY_ODDS primary key (ID);

prompt
prompt Creating table TB_OUT_REPLENISH_STAFF_EXT
prompt =========================================
prompt
create table TB_OUT_REPLENISH_STAFF_EXT
(
  PARENT_STAFF     NUMBER(10),
  MANAGER_STAFF_ID NUMBER(10) not null,
  PLATE            CHAR(1)
)
;
comment on table TB_OUT_REPLENISH_STAFF_EXT
  is '此表中记录出货会员扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了代理用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
出货会员在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “8 — 出货会员”';
comment on column TB_OUT_REPLENISH_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_OUT_REPLENISH_STAFF_EXT.MANAGER_STAFF_ID
  is '出货会员基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_OUT_REPLENISH_STAFF_EXT.PLATE
  is '盘口';
alter table TB_OUT_REPLENISH_STAFF_EXT
  add constraint PK_TB_OUT_REPLENISH_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_PERIODS_AUTO_ODDS
prompt ===================================
prompt
create table TB_PERIODS_AUTO_ODDS
(
  ID          NUMBER(10) not null,
  SHOPS_CODE  CHAR(4) not null,
  TYPE        VARCHAR2(30),
  NAME        VARCHAR2(30),
  AUTO_ODDS   NUMBER(12,4),
  CREATE_USER NUMBER(10) not null,
  CREATE_TIME DATE default sysdate not null
)
;
comment on table TB_PERIODS_AUTO_ODDS
  is '此表存储根据未出球连续期自动降赔率设置信息';
comment on column TB_PERIODS_AUTO_ODDS.NAME
  is '所需设置的名称';
comment on column TB_PERIODS_AUTO_ODDS.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_PERIODS_AUTO_ODDS
  add constraint PK_TB_PERIODS_AUTO_ODDS primary key (ID);

prompt
prompt Creating table TB_PLAYODDS_LOG
prompt ==============================
prompt
create table TB_PLAYODDS_LOG
(
  LOGID           NUMBER(10) not null,
  CREATE_USERID   NUMBER(10) not null,
  CREATE_DATE     DATE not null,
  PLAY_TYPE_CODE  VARCHAR2(50) not null,
  REALODDS_BEFORE NUMBER(12,4) not null,
  REALODDS_AFTER  NUMBER(12,4) not null,
  SHOPS_CODE      CHAR(4) not null,
  REMARK          VARCHAR2(500)
)
;
comment on table TB_PLAYODDS_LOG
  is '操盘日志';
alter table TB_PLAYODDS_LOG
  add constraint PK_TB_PLAYODDS_LOG primary key (LOGID);

prompt
prompt Creating table TB_PLAY_AMOUNT
prompt =============================
prompt
create table TB_PLAY_AMOUNT
(
  ID              NUMBER(10) not null,
  TYPE_CODE       VARCHAR2(30) not null,
  PLAY_TYPE       VARCHAR2(10) not null,
  PERIODS_NUM     VARCHAR2(11),
  COMMISSION_TYPE VARCHAR2(15),
  SHOPS_CODE      CHAR(4),
  MONEY_AMOUNT    NUMBER not null,
  UPDATE_TIME     DATE
)
;
comment on table TB_PLAY_AMOUNT
  is '记录对应投注类型的投注总额';
comment on column TB_PLAY_AMOUNT.TYPE_CODE
  is '投注总额所对应的投注类型编码，对应于投注类型表（TB_PLAY_TYPE）中的类型编码';
comment on column TB_PLAY_AMOUNT.PLAY_TYPE
  is '玩法（时时彩，十分钟，六合彩）';
comment on column TB_PLAY_AMOUNT.PERIODS_NUM
  is '对应盘期表的期数字段（PERIODS_NUM）';
comment on column TB_PLAY_AMOUNT.MONEY_AMOUNT
  is '投注金额，单位分';
alter table TB_PLAY_AMOUNT
  add constraint PK_TB_PLAY_AMOUNT primary key (ID);

prompt
prompt Creating table TB_PLAY_TYPE
prompt ===========================
prompt
create table TB_PLAY_TYPE
(
  ID                            NUMBER(10) not null,
  TYPE_CODE                     VARCHAR2(30) not null,
  TYPE_NAME                     VARCHAR2(100) not null,
  PLAY_TYPE                     VARCHAR2(10) not null,
  PLAY_SUB_TYPE                 VARCHAR2(15),
  PLAY_FINAL_TYPE               VARCHAR2(10) not null,
  ODDS_TYPE                     VARCHAR2(30),
  STATE                         CHAR(1) default '0' not null,
  SUB_TYPE_NAME                 VARCHAR2(20),
  FINAL_TYPE_NAME               VARCHAR2(20),
  COMMISSION_TYPE               VARCHAR2(15),
  REMARK                        NVARCHAR2(200),
  AUTO_REPLENISH_TYPE           VARCHAR2(30),
  DISPLAY_ORDER                 NUMBER,
  COMMISSION_TYPE_DISPLAY_ORDER NUMBER
)
;
comment on table TB_PLAY_TYPE
  is '投注类型表，记录系统支持的投注类型，投注类型数据使用初始化的方式生成';
comment on column TB_PLAY_TYPE.ID
  is '代理主键';
comment on column TB_PLAY_TYPE.TYPE_CODE
  is '投注类型的名称，具有唯一性，命名规则形如：CQSSC_BALL_FIRST_XXX';
comment on column TB_PLAY_TYPE.TYPE_NAME
  is '类型的名称';
comment on column TB_PLAY_TYPE.PLAY_TYPE
  is '玩法（时时彩，十分钟，六合彩）';
comment on column TB_PLAY_TYPE.PLAY_SUB_TYPE
  is '子玩法（第一球，两面盘，连码 ）';
comment on column TB_PLAY_TYPE.PLAY_FINAL_TYPE
  is '下注类型（总和大，大小 等）';
comment on column TB_PLAY_TYPE.ODDS_TYPE
  is '赔率主类型（第一球 等）';
comment on column TB_PLAY_TYPE.STATE
  is '此投注类型当前是否有效，0 — 有效，1 — 无效，2 — 删除；默认值 0';
comment on column TB_PLAY_TYPE.FINAL_TYPE_NAME
  is '下注类型描述';
comment on column TB_PLAY_TYPE.AUTO_REPLENISH_TYPE
  is '自动补货类型';
comment on column TB_PLAY_TYPE.DISPLAY_ORDER
  is '页面显示顺序用';
alter table TB_PLAY_TYPE
  add constraint PK_TB_PLAY_TYPE primary key (ID);

prompt
prompt Creating table TB_PLAY_WIN_INFO
prompt ===============================
prompt
create table TB_PLAY_WIN_INFO
(
  ID          NUMBER(10) not null,
  TYPE_CODE   VARCHAR2(30) not null,
  PLAY_TYPE   VARCHAR2(10) not null,
  PERIODS_NUM VARCHAR2(11) not null,
  WIN         CHAR(1) not null,
  UPDATE_TIME DATE
)
;
comment on table TB_PLAY_WIN_INFO
  is '记录投注类型所对应的中奖情况，兑奖时 用来临时记录投注（非连码，没有复式）中奖状态，通过该表跟投注表关联（避免表扫描） 加快兑奖速度';
comment on column TB_PLAY_WIN_INFO.TYPE_CODE
  is '投注总额所对应的投注类型编码，对应于投注类型表（TB_PLAY_TYPE）中的类型编码';
comment on column TB_PLAY_WIN_INFO.PLAY_TYPE
  is '玩法（时时彩，十分钟，六合彩）';
comment on column TB_PLAY_WIN_INFO.PERIODS_NUM
  is '对应盘期表的期数字段（PERIODS_NUM）';
comment on column TB_PLAY_WIN_INFO.WIN
  is '是否中奖，0 — 中奖；1 — 未中奖';
alter table TB_PLAY_WIN_INFO
  add constraint PK_TB_PLAY_WIN_INFO primary key (ID);

prompt
prompt Creating table TB_REPLENISH
prompt ===========================
prompt
create table TB_REPLENISH
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
;
comment on column TB_REPLENISH.ORDER_NO
  is '不同的期数可以重复';
comment on column TB_REPLENISH.TYPE_CODE
  is '投注类型编码，对应于玩法类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_REPLENISH.MONEY
  is '投注金额，单位分';
comment on column TB_REPLENISH.ATTRIBUTE
  is '纪录连码投注时所选择的球，球之间使用半角的|分割，如，连码投注选择了5球和7球，则此处纪录的数据为 5|7';
comment on column TB_REPLENISH.REPLENISH_USER_ID
  is '补货人ID';
comment on column TB_REPLENISH.REPLENISH_ACC_USER_ID
  is '接受补货的对象，一般是补货人的上一级';
comment on column TB_REPLENISH.PERIODS_NUM
  is '投注期数，形如 20120203001，对应于盘期信息（广东快乐十分）表（TB_GDKLSF_PERIODS_INFO）的【投注期数（PERIODS_NUM）】字段';
comment on column TB_REPLENISH.PLATE
  is 'A、B、C等盘面';
comment on column TB_REPLENISH.BETTING_DATE
  is '投注时间';
comment on column TB_REPLENISH.WIN_STATE
  is '中奖状态，0 — 未开奖、1 — 中奖、2 — 未中奖、3 — 已兑奖、4 — 注销（在注单搜索时生成）、5 — 作废（该盘期停开生成）、9 — 打和（重庆时时彩龙虎和的和及香港六合彩的特码和局）默认值 0 — 未开奖';
comment on column TB_REPLENISH.ODDS
  is '当前投注单所对应的赔率';
comment on column TB_REPLENISH.ODDS2
  is '赔率2，只有当投注类型为连码时才有效';
comment on column TB_REPLENISH.SELECT_ODDS
  is '记录选择的球的赔率(用于五不中和过关)';
alter table TB_REPLENISH
  add constraint PK_TB_REPLENISH primary key (ID);

prompt
prompt Creating table TB_REPLENISH_AUTO
prompt ================================
prompt
create table TB_REPLENISH_AUTO
(
  ID               NUMBER(10) not null,
  SHOPS_ID         VARCHAR2(30) not null,
  TYPE             VARCHAR2(10) not null,
  MONEY_LIMIT      NUMBER(10) not null,
  CREATE_USER      NUMBER(10) not null,
  CREATE_TIME      DATE not null,
  TYPE_CODE        VARCHAR2(30) not null,
  MONEY_REP        NUMBER(10),
  STATE            CHAR(1),
  CREATE_USER_TYPE CHAR(1)
)
;
comment on column TB_REPLENISH_AUTO.TYPE
  is '取值为 GDKLSF、HKLHC、CQSSC';
comment on column TB_REPLENISH_AUTO.STATE
  is '取值，0 — 禁用、1 — 起用';
alter table TB_REPLENISH_AUTO
  add constraint PK_TB_REPLENISH_AUTO primary key (ID);

prompt
prompt Creating table TB_REPLENISH_AUTO_LOG
prompt ====================================
prompt
create table TB_REPLENISH_AUTO_LOG
(
  ID            NUMBER(10) not null,
  SHOP_ID       VARCHAR2(10) not null,
  PLAY_TYPE     VARCHAR2(10) not null,
  TYPE_CODE     VARCHAR2(30) not null,
  MONEY         NUMBER not null,
  CREATE_USERID VARCHAR2(10) not null,
  CREATE_DATE   DATE not null,
  PERIODS_NUM   VARCHAR2(11),
  TYPE          CHAR(1)
)
;
comment on column TB_REPLENISH_AUTO_LOG.PLAY_TYPE
  is '取值为：HKLHC、CQSSC、GDKLSF';
comment on column TB_REPLENISH_AUTO_LOG.TYPE
  is '1:自动捕获日志，2手动捕获日志';
alter table TB_REPLENISH_AUTO_LOG
  add constraint PK_TB_REPLENISH_AUTO_LOG primary key (ID);

prompt
prompt Creating table TB_REPLENISH_AUTO_SET_LOG
prompt ========================================
prompt
create table TB_REPLENISH_AUTO_SET_LOG
(
  ID              NUMBER(10) not null,
  SHOP_ID         VARCHAR2(10) not null,
  TYPE            VARCHAR2(10) not null,
  TYPE_CODE       VARCHAR2(30) not null,
  MONEY_ORGIN     NUMBER not null,
  MONEY_NEW       NUMBER not null,
  CREATE_USERID   VARCHAR2(10) not null,
  CREATE_USERTYPE VARCHAR2(10) not null,
  CREATE_TIME     DATE not null,
  STATE_ORGIN     CHAR(1),
  STATE_NEW       CHAR(1),
  IP              VARCHAR2(20),
  CHANGE_TYPE     VARCHAR2(300),
  CHANGE_SUB_TYPE VARCHAR2(300),
  ORGINAL_VALUE   VARCHAR2(300),
  NEW_VALUE       VARCHAR2(300),
  UPDATE_USERTYPE VARCHAR2(10),
  UPDATE_USERID   VARCHAR2(10)
)
;
comment on column TB_REPLENISH_AUTO_SET_LOG.SHOP_ID
  is ' 店铺Id';
comment on column TB_REPLENISH_AUTO_SET_LOG.TYPE
  is '类型 ：GDKLSF、BJ、CQSSC';
comment on column TB_REPLENISH_AUTO_SET_LOG.MONEY_ORGIN
  is '修改之前的money';
comment on column TB_REPLENISH_AUTO_SET_LOG.MONEY_NEW
  is '修改之后的money';
comment on column TB_REPLENISH_AUTO_SET_LOG.CREATE_TIME
  is '创建时间';
comment on column TB_REPLENISH_AUTO_SET_LOG.STATE_ORGIN
  is '修改之前的 状态，复选框是否选中';
comment on column TB_REPLENISH_AUTO_SET_LOG.STATE_NEW
  is '修改之后的 状态，复选框是否选中';

prompt
prompt Creating table TB_REPLENISH_HIS
prompt ===============================
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
;
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
  add constraint PK_TB_REPLENISH_HIS primary key (ID);
create index INDEX_BH_HIS_BETTING_DATE on TB_REPLENISH_HIS (BETTING_DATE);
create index INDEX_BH_HIS_BETTING_DATE_USER on TB_REPLENISH_HIS (REPLENISH_USER_ID, BETTING_DATE);
create index INDEX_BH_HIS_BETTING_USER on TB_REPLENISH_HIS (REPLENISH_USER_ID);

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
;
comment on table TB_REPORT_STATUS
  is '报表状态表';
comment on column TB_REPORT_STATUS.ID
  is '主键';
comment on column TB_REPORT_STATUS.OPT
  is '报表是否使用新方法计算，在总官里设置，0：关闭 1：开启，默认为关闭';
comment on column TB_REPORT_STATUS.STATUS
  is '报表计算的状态，0：未成功 1：成功，默认为未成功';
alter table TB_REPORT_STATUS
  add constraint PK_TB_REPORT_STATUS primary key (ID);

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
;
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
  add constraint PK_TB_REPORT_HIS primary key (ID);

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
;
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
  add constraint PK_TB_TB_SETTLED_REPORT_R_LIST primary key (ID);

prompt
prompt Creating table TB_SHOPS_DECLARATION
prompt ===================================
prompt
create table TB_SHOPS_DECLARATION
(
  ID                     NUMBER(10) not null,
  SHOPS_CODE             CHAR(4),
  MANAGER_MESSAGE_STATUS CHAR(1),
  POPUP_MENUS            CHAR(1),
  MEMBER_MESSAGE_STATUS  CHAR(1),
  CONTENT_INFO           NVARCHAR2(2000),
  START_DATE             DATE,
  END_DATE               DATE,
  TYPE                   CHAR(1) default '1',
  FONT_COLOR             CHAR(7),
  REMARK                 NVARCHAR2(200),
  CREATE_USER            NUMBER(10),
  CREATE_TIME            DATE
)
;
comment on table TB_SHOPS_DECLARATION
  is '商铺公告信息';
comment on column TB_SHOPS_DECLARATION.START_DATE
  is '公告开始生效时间';
comment on column TB_SHOPS_DECLARATION.END_DATE
  is '公告结束时间';
comment on column TB_SHOPS_DECLARATION.TYPE
  is '0 — 默认公告（当不存在其他生效消息时使用），1 — 普通公告；默认值 1';
comment on column TB_SHOPS_DECLARATION.FONT_COLOR
  is '字体的颜色，如：#FFFF00';
comment on column TB_SHOPS_DECLARATION.CREATE_USER
  is '创建人员ID';
alter table TB_SHOPS_DECLARATION
  add constraint PK_TB_SHOPS_DECLARATION primary key (ID);

prompt
prompt Creating table TB_SHOPS_HKLHC_PERIODS
prompt =====================================
prompt
create table TB_SHOPS_HKLHC_PERIODS
(
  ID              NUMBER(10) not null,
  PERIODS_INFO_ID NUMBER(10) not null,
  SHOPS_CODE      CHAR(4) not null,
  PERIODS_STATE   CHAR(1) not null,
  MODIFY_USER     NUMBER(10),
  MODIFY_TIME     DATE default sysdate not null,
  STOP_QUOT_TIME  DATE,
  OPEN_QUOT_TIME  DATE,
  AUTO_STOP_QUOT  INTEGER
)
;
comment on table TB_SHOPS_HKLHC_PERIODS
  is '记录各个商铺各自所对应的盘期信息（香港六合彩）内容';
comment on column TB_SHOPS_HKLHC_PERIODS.PERIODS_INFO_ID
  is '盘期ID，对应盘期信息（香港六合彩）表（TB_HKLHC_PERIODS_INFO）中的ID';
comment on column TB_SHOPS_HKLHC_PERIODS.PERIODS_STATE
  is '0 — 开盘；1 — 封盘';
comment on column TB_SHOPS_HKLHC_PERIODS.MODIFY_USER
  is '编辑人ID，对应人员表（TB_FRAME_STAFF）中的主键';
alter table TB_SHOPS_HKLHC_PERIODS
  add constraint PK_TB_SHOPS_HKLHC_PERIODS primary key (ID);

prompt
prompt Creating table TB_SHOPS_INFO
prompt ============================
prompt
create table TB_SHOPS_INFO
(
  ID                NUMBER(10) not null,
  SHOPS_CODE        VARCHAR2(10) not null,
  SHOPS_NAME        NVARCHAR2(10) not null,
  STATE             CHAR(1) default '0' not null,
  CREATE_USER       NUMBER(10) not null,
  CREATE_TIME       DATE default sysdate not null,
  CSS               CHAR(1) default '0' not null,
  REMARK            NVARCHAR2(200),
  ENABLE_BET_DELETE VARCHAR2(1) default 'N',
  ENABLE_BET_CANCEL VARCHAR2(1) default 'N'
)
;
comment on table TB_SHOPS_INFO
  is '商铺信息表，存放商铺信息';
comment on column TB_SHOPS_INFO.ID
  is '代理主键';
comment on column TB_SHOPS_INFO.STATE
  is '商铺状态：0 — 开放；1 — 冻结；2 — 关闭，默认为 0 — 开放';
comment on column TB_SHOPS_INFO.CREATE_USER
  is '创建人ID，对应人员表（TB_FRAME_STAFF）中的主键';
comment on column TB_SHOPS_INFO.CSS
  is '选择商铺的样式，系统根据样式匹配商铺风格，默认值为 0';
alter table TB_SHOPS_INFO
  add constraint PK_TB_SHOPS_INFO primary key (ID);
alter table TB_SHOPS_INFO
  add constraint AK_UQ_SHOPS_CODE_TB_SHOPS unique (SHOPS_CODE);

prompt
prompt Creating table TB_SHOPS_PLAY_ODDS
prompt =================================
prompt
create table TB_SHOPS_PLAY_ODDS
(
  ID               NUMBER(10) not null,
  SHOPS_CODE       CHAR(4),
  PLAY_TYPE_CODE   VARCHAR2(30) not null,
  ODDS_TYPE_X      VARCHAR2(30),
  ODDS_TYPE        VARCHAR2(30),
  REAL_ODDS        NUMBER(12,4) not null,
  REAL_UPDATE_DATE DATE,
  REAL_UPDATE_USER NUMBER(10) not null,
  STATE            CHAR(1) default '0',
  REMARK           NVARCHAR2(200)
)
;
comment on column TB_SHOPS_PLAY_ODDS.SHOPS_CODE
  is '商铺编号，对应商铺信息表（TB_SHOPS_INFO）的商铺号码（SHOPS_CODE）字段';
comment on column TB_SHOPS_PLAY_ODDS.PLAY_TYPE_CODE
  is '投注类型编码，对应于投注类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_SHOPS_PLAY_ODDS.ODDS_TYPE_X
  is '存放一些特殊玩法的子类型，如：过关，正码1（单双大小红绿蓝）';
comment on column TB_SHOPS_PLAY_ODDS.ODDS_TYPE
  is '赔率主类型（第一球 等）';
comment on column TB_SHOPS_PLAY_ODDS.REAL_ODDS
  is '实时赔率值';
comment on column TB_SHOPS_PLAY_ODDS.REAL_UPDATE_USER
  is '实时赔率更新人ID，对应人员表（TB_FRAME_STAFF）中的主键';
comment on column TB_SHOPS_PLAY_ODDS.STATE
  is '此投注类型当前是否有效，0 — 有效，1 — 无效，2 — 删除；默认值 0
';
alter table TB_SHOPS_PLAY_ODDS
  add constraint PK_TB_SHOPS_PLAY_ODDS primary key (ID);

prompt
prompt Creating table TB_SHOPS_PLAY_ODDS_LOG
prompt =====================================
prompt
create table TB_SHOPS_PLAY_ODDS_LOG
(
  ID                      NUMBER(10) not null,
  SHOPS_CODE              CHAR(4),
  PLAY_TYPE_CODE          VARCHAR2(30) not null,
  ODDS_TYPE_X             VARCHAR2(30),
  ODDS_TYPE               VARCHAR2(30),
  REAL_ODDS_ORIGIN        NUMBER(12,4) not null,
  REAL_UPDATE_DATE_ORIGIN DATE,
  REAL_UPDATE_USER_ORIGIN NUMBER(10) not null,
  REAL_ODDS_NEW           NUMBER(12,4) not null,
  REAL_UPDATE_DATE_NEW    DATE,
  REAL_UPDATE_USER_NEW    NUMBER(10) not null,
  PERIODS_NUM             VARCHAR2(11),
  IP                      VARCHAR2(20),
  REMARK                  NVARCHAR2(200),
  TYPE                    CHAR(1)
)
;
comment on table TB_SHOPS_PLAY_ODDS_LOG
  is '商铺赔率历史表，记录商铺赔率表（TB_SHOPS_PLAY_ODDS）中数据的变动情况';
comment on column TB_SHOPS_PLAY_ODDS_LOG.SHOPS_CODE
  is '商铺编号，对应商铺信息表（TB_SHOPS_INFO）的商铺号码（SHOPS_CODE）字段';
comment on column TB_SHOPS_PLAY_ODDS_LOG.PLAY_TYPE_CODE
  is '投注类型编码，对应于投注类型表（TB_PLAY_TYPE）表中的类型编码';
comment on column TB_SHOPS_PLAY_ODDS_LOG.ODDS_TYPE_X
  is '存放一些特殊玩法的子类型，如：过关，正码1（单双大小红绿蓝）';
comment on column TB_SHOPS_PLAY_ODDS_LOG.ODDS_TYPE
  is '赔率主类型（第一球 等）';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_ODDS_ORIGIN
  is '原来的实时赔率值';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_UPDATE_DATE_ORIGIN
  is '原来赔率更新时间';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_UPDATE_USER_ORIGIN
  is '原实时赔率更新人ID，对应人员表（TB_FRAME_STAFF）中的主键';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_ODDS_NEW
  is '新的实时赔率值';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_UPDATE_DATE_NEW
  is '新赔率更新时间';
comment on column TB_SHOPS_PLAY_ODDS_LOG.REAL_UPDATE_USER_NEW
  is '新实时赔率更新人ID，对应人员表（TB_FRAME_STAFF）中的主键';
comment on column TB_SHOPS_PLAY_ODDS_LOG.PERIODS_NUM
  is '投注期数';
comment on column TB_SHOPS_PLAY_ODDS_LOG.TYPE
  is '1:自动降配日志，2手动修改日志';
alter table TB_SHOPS_PLAY_ODDS_LOG
  add constraint PK_TB_SHOPS_PLAY_ODDS_LOG primary key (ID);

prompt
prompt Creating table TB_SHOPS_RENT
prompt ============================
prompt
create table TB_SHOPS_RENT
(
  ID                  NUMBER(10) not null,
  SHOPS_CODE          CHAR(4) not null,
  EXPITY_TIME         DATE not null,
  EXPITY_WARNING_TIME DATE not null,
  WARNING_TYPE        CHAR(1),
  WARNING_EMAIL       NVARCHAR2(50),
  WARNING_MOBILE      VARCHAR2(15),
  LAST_MODIFY_USER    NUMBER(10) not null,
  LAST_MODIFY_DATE    DATE not null,
  REMARK              NVARCHAR2(200)
)
;
comment on table TB_SHOPS_RENT
  is '商铺租赁信息表';
comment on column TB_SHOPS_RENT.SHOPS_CODE
  is '商铺编号，对应商铺信息表（TB_SHOPS_INFO）的商铺号码（SHOPS_CODE）';
comment on column TB_SHOPS_RENT.EXPITY_TIME
  is '商铺到期时间';
comment on column TB_SHOPS_RENT.EXPITY_WARNING_TIME
  is '到期提醒时间';
comment on column TB_SHOPS_RENT.WARNING_TYPE
  is '0 — 系统处理；1 — 手机短信；2 — 邮件，默认值0
此字段暂时不使用';
comment on column TB_SHOPS_RENT.WARNING_EMAIL
  is '接收提醒信息的邮件地址，只有当提醒方式为 2 — 邮件 时有效，暂不使用';
comment on column TB_SHOPS_RENT.WARNING_MOBILE
  is '接收提醒信息的手机号码，只有当提醒方式的取值为 1—手机短信 时有效';
comment on column TB_SHOPS_RENT.LAST_MODIFY_USER
  is '最后编辑人员ID，对应人员表（TB_FRAME_STAFF）ID，-1 表示系统维护';
alter table TB_SHOPS_RENT
  add constraint PK_TB_SHOPS_RENT primary key (ID);

prompt
prompt Creating table TB_STOCKHOLDER_STAFF_EXT
prompt =======================================
prompt
create table TB_STOCKHOLDER_STAFF_EXT
(
  MANAGER_STAFF_ID      NUMBER(10) not null,
  PARENT_STAFF          NUMBER(10),
  REPLENISHMENT         CHAR(1) not null,
  BRANCH_RATE           NUMBER(5,2),
  PURE_ACCOUNTED        CHAR(1),
  SHAREHOLDER_RATE      NUMBER(5,2),
  TOTAL_CREDIT_LINE     NUMBER(10),
  AVAILABLE_CREDIT_LINE NUMBER(10),
  CHIEF_STAFF           NUMBER(10),
  RATE_RESTRICT         CHAR(1),
  BELOW_RATE_LIMIT      NUMBER(2)
)
;
comment on table TB_STOCKHOLDER_STAFF_EXT
  is '此表中记录股东用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了股东用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
-----------------
股东用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “4 — 股东用户”';
comment on column TB_STOCKHOLDER_STAFF_EXT.MANAGER_STAFF_ID
  is '股东用户用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_STOCKHOLDER_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_STOCKHOLDER_STAFF_EXT.REPLENISHMENT
  is '走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞';
comment on column TB_STOCKHOLDER_STAFF_EXT.PURE_ACCOUNTED
  is '0 — 纯占；1 — 非纯占';
comment on column TB_STOCKHOLDER_STAFF_EXT.SHAREHOLDER_RATE
  is '股东占成';
comment on column TB_STOCKHOLDER_STAFF_EXT.CHIEF_STAFF
  is '股东所对应的总监ID';
comment on column TB_STOCKHOLDER_STAFF_EXT.RATE_RESTRICT
  is '是否限制占成';
comment on column TB_STOCKHOLDER_STAFF_EXT.BELOW_RATE_LIMIT
  is '限制占成限额';
alter table TB_STOCKHOLDER_STAFF_EXT
  add constraint PK_TB_STOCKHOLDER_STAFF_EXT primary key (MANAGER_STAFF_ID);

prompt
prompt Creating table TB_SUB_STAFF_EXT
prompt ===============================
prompt
create table TB_SUB_STAFF_EXT
(
  PARENT_STAFF      NUMBER(10),
  MANAGER_STAFF_ID  NUMBER(10) not null,
  CHIEF_STAFF       NUMBER(10),
  BRANCH_STAFF      NUMBER(10),
  STOCKHOLDER_STAFF NUMBER(10),
  GEN_AGENT_STAFF   NUMBER(10),
  PARENT_USER_TYPE  CHAR(1)
)
;
comment on table TB_SUB_STAFF_EXT
  is '此表中记录代理用户扩展信息，管理用户基础表（TB_FRAME_MANAGER_STAFF）中存储了代理用户的基本信息，相关字段对应关系如下：
1、登陆账号
2、用户密码
3、最后登录时间
4、创建时间
5、中文名字
6、状态：（开启/禁止）
7、最后登录IP
-----------------
代理用户在管理用户基础表中所对应的用户类型（USER_TYPE）的取值为 “7 — 子账号”';
comment on column TB_SUB_STAFF_EXT.PARENT_STAFF
  is '上级用户';
comment on column TB_SUB_STAFF_EXT.MANAGER_STAFF_ID
  is '代理用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID';
comment on column TB_SUB_STAFF_EXT.CHIEF_STAFF
  is '子账号所对应的总监ID';
comment on column TB_SUB_STAFF_EXT.BRANCH_STAFF
  is '子账号所对应的分公司ID';
comment on column TB_SUB_STAFF_EXT.STOCKHOLDER_STAFF
  is '子账号所对应的股东ID';
comment on column TB_SUB_STAFF_EXT.GEN_AGENT_STAFF
  is '子账号所对应的总代理ID';

prompt
prompt Creating table TB_USER_COMMISSION
prompt =================================
prompt
create table TB_USER_COMMISSION
(
  ID              NUMBER(10) not null,
  USER_ID         NUMBER(10) not null,
  USER_TYPE       CHAR(1) not null,
  PLAY_TYPE       VARCHAR2(30),
  PLAY_FINAL_TYPE VARCHAR2(100) not null,
  COMMISSION_A    NUMBER,
  COMMISSION_B    NUMBER,
  COMMISSION_C    NUMBER,
  BETTING_QUOTAS  NUMBER,
  ITEM_QUOTAS     NUMBER,
  CREATE_USER     NUMBER(10),
  CREATE_TIME     DATE,
  MODIFY_USER     NUMBER(10),
  MODIFY_TIME     DATE,
  CHIEF_ID        NUMBER(10)
)
;
comment on table TB_USER_COMMISSION
  is '保存用户佣金设置信息';
comment on column TB_USER_COMMISSION.USER_ID
  is '用户ID，对应用户表中的ID（需要根据用户类型判断是普通会员还是管理会员），当用户类型字段（USER_TYPE）的取值为1~6时，此处对应于管理用户基础表（TB_FRAME_MANAGER_STAFF）中的ID，当用户类型字段的取值为7时，此处对应于会员用户基础表（TB_FRAME_MEMBER_STAFF）中的ID';
comment on column TB_USER_COMMISSION.USER_TYPE
  is '0 — 系统管理员，1 — 总管用户，2 — 总监用户，3 — 分公司用户，4 — 股东用户，5 — 总代理用户，6 — 代理用户，7 —会员用户
';
comment on column TB_USER_COMMISSION.PLAY_TYPE
  is '广东时时彩、重庆快乐十分、香港六合彩等';
comment on column TB_USER_COMMISSION.PLAY_FINAL_TYPE
  is '下注类型（总和大，大小 等）';
comment on column TB_USER_COMMISSION.CREATE_USER
  is '创建人员ID';
alter table TB_USER_COMMISSION
  add constraint PK_TB_USER_COMMISSION primary key (ID);

prompt
prompt Creating table TB_USER_COMMISSION_DEFAULT
prompt =========================================
prompt
create table TB_USER_COMMISSION_DEFAULT
(
  ID              NUMBER(10) not null,
  USER_ID         NUMBER(10),
  USER_TYPE       CHAR(1),
  PLAY_TYPE       VARCHAR2(30),
  PLAY_FINAL_TYPE VARCHAR2(100) not null,
  COMMISSION_A    NUMBER,
  COMMISSION_B    NUMBER,
  COMMISSION_C    NUMBER,
  BETTING_QUOTAS  NUMBER,
  ITEM_QUOTAS     NUMBER,
  TOTAL_QUOTAS    NUMBER,
  LOWEST_QUOTAS   NUMBER,
  WIN_QUOTAS      NUMBER,
  LOSE_QUOTAS     NUMBER,
  CREATE_USER     NUMBER(10),
  CREATE_TIME     DATE,
  MODIFY_USER     NUMBER(10),
  MODIFY_TIME     DATE
)
;
comment on column TB_USER_COMMISSION_DEFAULT.USER_ID
  is '用户ID，对应用户表中的ID（需要根据用户类型判断是普通会员还是管理会员），当用户类型字段（USER_TYPE）的取值为1~6时，此处对应于管理用户基础表（TB_FRAME_MANAGER_STAFF）中的ID，当用户类型字段的取值为7时，此处对应于会员用户基础表（TB_FRAME_MEMBER_STAFF）中的ID';
comment on column TB_USER_COMMISSION_DEFAULT.USER_TYPE
  is '0 — 系统管理员，1 — 总管用户，2 — 总监用户，3 — 分公司用户，4 — 股东用户，5 — 总代理用户，6 — 代理用户，7 —会员用户
';
comment on column TB_USER_COMMISSION_DEFAULT.PLAY_TYPE
  is '广东时时彩、重庆快乐十分、香港六合彩等';
comment on column TB_USER_COMMISSION_DEFAULT.PLAY_FINAL_TYPE
  is '下注类型（总和大，大小 等）';
comment on column TB_USER_COMMISSION_DEFAULT.CREATE_USER
  is '创建人员ID';
alter table TB_USER_COMMISSION_DEFAULT
  add constraint PK_TB_USER_COMMISSION_DEFAULT primary key (ID);

prompt
prompt Creating table TEMP_DELIVERYREPORT
prompt ==================================
prompt
create global temporary table TEMP_DELIVERYREPORT
(
  PARENT_ID                  NUMBER(10),
  USER_ID                    NUMBER(10),
  USER_TYPE                  CHAR(1),
  RECORD_TYPE                CHAR(1),
  SUBORDINATE                NVARCHAR2(50),
  USER_NAME                  NVARCHAR2(50),
  TURNOVER                   NUMBER,
  AMOUNT                     NUMBER,
  VALID_AMOUNT               NUMBER,
  MEMBER_AMOUNT              NUMBER,
  MEMBER_BACK_WATER          NUMBER,
  SUBORDINATE_AMOUNT         NUMBER,
  WIN_BACK_WATER             NUMBER,
  REAL_RESULT                NUMBER,
  WIN_BACK_WATER_RESULT      NUMBER,
  PAY_SUPERIOR               NUMBER,
  COMMISSION_BRANCH          NUMBER,
  COMMISSION_GEN_AGENT       NUMBER,
  COMMISSION_STOCKHOLDER     NUMBER,
  COMMISSION_AGENT           NUMBER,
  COMMISSION_MEMBER          NUMBER,
  WIN_COMMISSION_BRANCH      NUMBER,
  WIN_COMMISSION_GEN_AGENT   NUMBER,
  WIN_COMMISSION_STOCKHOLDER NUMBER,
  WIN_COMMISSION_AGENT       NUMBER,
  WIN_COMMISSION_MEMBER      NUMBER,
  RATE_CHIEF                 NUMBER,
  RATE_BRANCH                NUMBER,
  RATE_GEN_AGENT             NUMBER,
  RATE_STOCKHOLDER           NUMBER,
  RATE_AGENT                 NUMBER,
  MONEY_RATE_CHIEF           NUMBER,
  MONEY_RATE_BRANCH          NUMBER,
  MONEY_RATE_GEN_AGENT       NUMBER,
  MONEY_RATE_STOCKHOLDER     NUMBER,
  MONEY_RATE_AGENT           NUMBER,
  SUBORDINATE_CHIEF          NUMBER,
  SUBORDINATE_BRANCH         NUMBER,
  SUBORDINATE_STOCKHOLDER    NUMBER,
  SUBORDINATE_GEN_AGENT      NUMBER,
  SUBORDINATE_AGENT          NUMBER,
  RATE                       NUMBER,
  RATE_CHIEF_SET             NUMBER,
  RATE_BRANCH_SET            NUMBER,
  RATE_STOCKHOLDER_SET       NUMBER,
  RATE_GEN_AGENT_SET         NUMBER,
  RATE_AGENT_SET             NUMBER,
  COMMISSION_BRANCH_SET      NUMBER,
  COMMISSION_STOCKHOLDER_SET NUMBER,
  COMMISSION_GEN_AGENT_SET   NUMBER,
  COMMISSION_AGENT_SET       NUMBER
)
on commit delete rows;

prompt
prompt Creating sequence SEQ_ORDER_NO
prompt ==============================
prompt
create sequence SEQ_ORDER_NO
minvalue 50000
maxvalue 99999999999
start with 55000
increment by 1
cache 5000;

prompt
prompt Creating sequence SEQ_TB_BJSC
prompt =============================
prompt
create sequence SEQ_TB_BJSC
minvalue 1
maxvalue 999999999999999999999999999
start with 228461
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_BJSC_HIS
prompt =================================
prompt
create sequence SEQ_TB_BJSC_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_BJSC_PERIODS_INFO
prompt ==========================================
prompt
create sequence SEQ_TB_BJSC_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 79199
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_BOSS_LOG
prompt =================================
prompt
create sequence SEQ_TB_BOSS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CLASS_REPORT_PET_LIST
prompt ==============================================
prompt
create sequence SEQ_TB_CLASS_REPORT_PET_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 20241
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CLASS_REPORT_R_LIST
prompt ============================================
prompt
create sequence SEQ_TB_CLASS_REPORT_R_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 5201
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_BALL_FIFTH
prompt =========================================
prompt
create sequence SEQ_TB_CQSSC_BALL_FIFTH
minvalue 1
maxvalue 999999999999999999999999999
start with 23909
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_BALL_FIRST
prompt =========================================
prompt
create sequence SEQ_TB_CQSSC_BALL_FIRST
minvalue 1
maxvalue 999999999999999999999999999
start with 40235
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_BALL_FORTH
prompt =========================================
prompt
create sequence SEQ_TB_CQSSC_BALL_FORTH
minvalue 1
maxvalue 999999999999999999999999999
start with 21512
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_BALL_SECOND
prompt ==========================================
prompt
create sequence SEQ_TB_CQSSC_BALL_SECOND
minvalue 1
maxvalue 999999999999999999999999999
start with 22451
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_BALL_THIRD
prompt =========================================
prompt
create sequence SEQ_TB_CQSSC_BALL_THIRD
minvalue 1
maxvalue 999999999999999999999999999
start with 22224
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_HIS
prompt ==================================
prompt
create sequence SEQ_TB_CQSSC_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 343564
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_CQSSC_PERIODS_INFO
prompt ===========================================
prompt
create sequence SEQ_TB_CQSSC_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 38521
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_DEFAULT_PLAY_ODDS
prompt ==========================================
prompt
create sequence SEQ_TB_DEFAULT_PLAY_ODDS
minvalue 1
maxvalue 999999999999999999999999999
start with 5870
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_AREA_ROLE
prompt ========================================
prompt
create sequence SEQ_TB_FRAME_AREA_ROLE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_AUTHORIZ_AREA
prompt ============================================
prompt
create sequence SEQ_TB_FRAME_AUTHORIZ_AREA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_DEMO
prompt ===================================
prompt
create sequence SEQ_TB_FRAME_DEMO
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_FUNCTION
prompt =======================================
prompt
create sequence SEQ_TB_FRAME_FUNCTION
minvalue 1
maxvalue 999999999999999999999999999
start with 5410
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_MANAGER_STAFF
prompt ============================================
prompt
create sequence SEQ_TB_FRAME_MANAGER_STAFF
minvalue 1
maxvalue 999999999999999999999999999
start with 800002180
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_MEMBER_STAFF
prompt ===========================================
prompt
create sequence SEQ_TB_FRAME_MEMBER_STAFF
minvalue 1
maxvalue 999999999999999999999999999
start with 100002840
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_MENU
prompt ===================================
prompt
create sequence SEQ_TB_FRAME_MENU
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_ORG
prompt ==================================
prompt
create sequence SEQ_TB_FRAME_ORG
minvalue 1
maxvalue 999999999999999999999999999
start with 360000200
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_PARAM
prompt ====================================
prompt
create sequence SEQ_TB_FRAME_PARAM
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_PARAM_VALUE
prompt ==========================================
prompt
create sequence SEQ_TB_FRAME_PARAM_VALUE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_RESOURCE
prompt =======================================
prompt
create sequence SEQ_TB_FRAME_RESOURCE
minvalue 1
maxvalue 999999999999999999999999999
start with 6291
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_RES_FUNC
prompt =======================================
prompt
create sequence SEQ_TB_FRAME_RES_FUNC
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_ROLES
prompt ====================================
prompt
create sequence SEQ_TB_FRAME_ROLES
minvalue 1
maxvalue 999999999999999999999999999
start with 930
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_ROLE_FUNC
prompt ========================================
prompt
create sequence SEQ_TB_FRAME_ROLE_FUNC
minvalue 1
maxvalue 999999999999999999999999999
start with 6190
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_STAFF
prompt ====================================
prompt
create sequence SEQ_TB_FRAME_STAFF
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_FRAME_STAFF_ROLE
prompt =========================================
prompt
create sequence SEQ_TB_FRAME_STAFF_ROLE
minvalue 1
maxvalue 999999999999999999999999999
start with 1481
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_EIGHTH
prompt ===========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_EIGHTH
minvalue 1
maxvalue 999999999999999999999999999
start with 12001
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_FIFTH
prompt ==========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_FIFTH
minvalue 1
maxvalue 999999999999999999999999999
start with 10081
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_FIRST
prompt ==========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_FIRST
minvalue 1
maxvalue 999999999999999999999999999
start with 453046
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_FORTH
prompt ==========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_FORTH
minvalue 1
maxvalue 999999999999999999999999999
start with 10001
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_SECOND
prompt ===========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_SECOND
minvalue 1
maxvalue 999999999999999999999999999
start with 10441
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_SEVENTH
prompt ============================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_SEVENTH
minvalue 1
maxvalue 999999999999999999999999999
start with 10781
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_SIXTH
prompt ==========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_SIXTH
minvalue 1
maxvalue 999999999999999999999999999
start with 11241
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_BALL_THIRD
prompt ==========================================
prompt
create sequence SEQ_TB_GDKLSF_BALL_THIRD
minvalue 1
maxvalue 999999999999999999999999999
start with 10061
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_DOUBLE_SIDE
prompt ===========================================
prompt
create sequence SEQ_TB_GDKLSF_DOUBLE_SIDE
minvalue 1
maxvalue 999999999999999999999999999
start with 15061
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_HIS
prompt ===================================
prompt
create sequence SEQ_TB_GDKLSF_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 100861
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_PERIODS_INFO
prompt ============================================
prompt
create sequence SEQ_TB_GDKLSF_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 31825
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_GDKLSF_STRAIGHTTHROUGH
prompt ===============================================
prompt
create sequence SEQ_TB_GDKLSF_STRAIGHTTHROUGH
minvalue 1
maxvalue 999999999999999999999999999
start with 11801
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_BB
prompt =================================
prompt
create sequence SEQ_TB_HKLHC_BB
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_GG
prompt =================================
prompt
create sequence SEQ_TB_HKLHC_GG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_HIS
prompt ==================================
prompt
create sequence SEQ_TB_HKLHC_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_LM
prompt =================================
prompt
create sequence SEQ_TB_HKLHC_LM
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_LX
prompt =================================
prompt
create sequence SEQ_TB_HKLHC_LX
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_PERIODS_INFO
prompt ===========================================
prompt
create sequence SEQ_TB_HKLHC_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_STRAIGHTTHROUGH
prompt ==============================================
prompt
create sequence SEQ_TB_HKLHC_STRAIGHTTHROUGH
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_SXL
prompt ==================================
prompt
create sequence SEQ_TB_HKLHC_SXL
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_SX_WS
prompt ====================================
prompt
create sequence SEQ_TB_HKLHC_SX_WS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_TE_MA
prompt ====================================
prompt
create sequence SEQ_TB_HKLHC_TE_MA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_TM_SX
prompt ====================================
prompt
create sequence SEQ_TB_HKLHC_TM_SX
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_WBZ
prompt ==================================
prompt
create sequence SEQ_TB_HKLHC_WBZ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_WSL
prompt ==================================
prompt
create sequence SEQ_TB_HKLHC_WSL
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_ZM16
prompt ===================================
prompt
create sequence SEQ_TB_HKLHC_ZM16
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_Z_MA
prompt ===================================
prompt
create sequence SEQ_TB_HKLHC_Z_MA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_HKLHC_Z_TE_MA
prompt ======================================
prompt
create sequence SEQ_TB_HKLHC_Z_TE_MA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_JSSB
prompt =============================
prompt
create sequence SEQ_TB_JSSB
minvalue 1
maxvalue 999999999999999999999999999
start with 20221
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_JSSB_HIS
prompt =================================
prompt
create sequence SEQ_TB_JSSB_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 3761
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_JSSB_PERIODS_INFO
prompt ==========================================
prompt
create sequence SEQ_TB_JSSB_PERIODS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 68785
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_LOGIN_LOG_INFO
prompt =======================================
prompt
create sequence SEQ_TB_LOGIN_LOG_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 46182
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_LOTTERY_STATUS
prompt =======================================
prompt
create sequence SEQ_TB_LOTTERY_STATUS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_OPEN_PLAY_ODDS
prompt =======================================
prompt
create sequence SEQ_TB_OPEN_PLAY_ODDS
minvalue 1
maxvalue 999999999999999999999999999
start with 7941
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_PERIODS_AUTO_ODDS
prompt ==========================================
prompt
create sequence SEQ_TB_PERIODS_AUTO_ODDS
minvalue 1
maxvalue 999999999999999999999999999
start with 2541
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_PLAYODDS_LOG
prompt =====================================
prompt
create sequence SEQ_TB_PLAYODDS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_PLAY_AMOUNT
prompt ====================================
prompt
create sequence SEQ_TB_PLAY_AMOUNT
minvalue 1
maxvalue 999999999999999999999999999
start with 63501
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_PLAY_TYPE
prompt ==================================
prompt
create sequence SEQ_TB_PLAY_TYPE
minvalue 1
maxvalue 999999999999999999999999999
start with 3730
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_PLAY_WIN_INFO
prompt ======================================
prompt
create sequence SEQ_TB_PLAY_WIN_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 5870
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPLENISH
prompt ==================================
prompt
create sequence SEQ_TB_REPLENISH
minvalue 1
maxvalue 999999999999999999999999999
start with 98861
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPLENISH_AUTO
prompt =======================================
prompt
create sequence SEQ_TB_REPLENISH_AUTO
minvalue 1
maxvalue 999999999999999999999999999
start with 6321
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPLENISH_AUTO_LOG
prompt ===========================================
prompt
create sequence SEQ_TB_REPLENISH_AUTO_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 49461
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPLENISH_AUTO_SET_LOG
prompt ===============================================
prompt
create sequence SEQ_TB_REPLENISH_AUTO_SET_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 30251
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_REPLENISH_HIS
prompt ======================================
prompt
create sequence SEQ_TB_REPLENISH_HIS
minvalue 1
maxvalue 999999999999999999999999999
start with 68641
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
start with 5441
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SETTLED_REPORT_R_LIST
prompt ==============================================
prompt
create sequence SEQ_TB_SETTLED_REPORT_R_LIST
minvalue 1
maxvalue 999999999999999999999999999
start with 821
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_DECLARATION
prompt ==========================================
prompt
create sequence SEQ_TB_SHOPS_DECLARATION
minvalue 1
maxvalue 999999999999999999999999999
start with 641
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_HKLHC_PERIODS
prompt ============================================
prompt
create sequence SEQ_TB_SHOPS_HKLHC_PERIODS
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_INFO
prompt ===================================
prompt
create sequence SEQ_TB_SHOPS_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 821
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_PLAY_ODDS
prompt ========================================
prompt
create sequence SEQ_TB_SHOPS_PLAY_ODDS
minvalue 1
maxvalue 999999999999999999999999999
start with 71981
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_PLAY_ODDS_LOG
prompt ============================================
prompt
create sequence SEQ_TB_SHOPS_PLAY_ODDS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 8653
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SHOPS_RENT
prompt ===================================
prompt
create sequence SEQ_TB_SHOPS_RENT
minvalue 1
maxvalue 999999999999999999999999999
start with 821
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_SUB_STAFF_EXT
prompt ======================================
prompt
create sequence SEQ_TB_SUB_STAFF_EXT
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_USER_COMMISSION
prompt ========================================
prompt
create sequence SEQ_TB_USER_COMMISSION
minvalue 1
maxvalue 999999999999999999999999999
start with 93141
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TB_USER_COMMISSION_DEFAULT
prompt ================================================
prompt
create sequence SEQ_TB_USER_COMMISSION_DEFAULT
minvalue 1
maxvalue 999999999999999999999999999
start with 4081
increment by 1
cache 20;

prompt
prompt Creating view VIEW_BJSC_HIS_TODAY
prompt =================================
prompt
create or replace view view_bjsc_his_today as
select "ID","CREATE_DATE","ORIGIN_TB_NAME","ORIGIN_ID","ORDER_NO","TYPE_CODE","MONEY","BETTING_USER_ID","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","RATE_CHIEF","RATE_BRANCH","RATE_GEN_AGENT","RATE_STOCKHOLDER","RATE_AGENT","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION_TYPE","UPDATE_DATE","REMARK"
    from TB_BJSC_HIS
 where trunc(betting_date- interval '6' hour)=trunc(sysdate- interval '6' hour);

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
 where trunc(betting_date- interval '6' hour)=trunc(sysdate- interval '6' hour);

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
where trunc(betting_date- interval '6' hour)=trunc(sysdate- interval '6' hour);

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
 where trunc(betting_date- interval '6' hour)=trunc(sysdate- interval '6' hour);

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
 where trunc(betting_date- interval '6' hour)=trunc(sysdate- interval '6' hour);

prompt
prompt Creating view VIEW_REPLENISH_HIS_YESTERDAY
prompt ==========================================
prompt
CREATE OR REPLACE VIEW VIEW_REPLENISH_HIS_YESTERDAY AS
select "ID","ORDER_NO","TYPE_CODE","MONEY","ATTRIBUTE","REPLENISH_USER_ID","REPLENISH_ACC_USER_ID","PERIODS_NUM","PLATE","BETTING_DATE","WIN_STATE","WIN_AMOUNT","ODDS","COMMISSION","RATE","UPDATE_USER","UPDATE_DATE","REMARK","CHIEFSTAFF","BRANCHSTAFF","STOCKHOLDERSTAFF","GENAGENSTAFF","AGENTSTAFF","RATE_CHIEF","RATE_BRANCH","RATE_STOCKHOLDER","RATE_GEN_AGENT","RATE_AGENT","ODDS2","COMMISSION_CHIEF","COMMISSION_BRANCH","COMMISSION_GEN_AGENT","COMMISSION_STOCKHOLDER","COMMISSION_AGENT","COMMISSION_MEMBER","COMMISSION_TYPE","SELECT_ODDS"
    from TB_REPLENISH_HIS
 where betting_date between to_date(to_char(sysdate-1,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00')
and to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')+to_dsinterval('0 2:00:00');

prompt
prompt Creating package STATREPORTRESULT
prompt =================================
prompt
CREATE OR REPLACE PACKAGE statReportResult IS
  type resultRef is ref cursor;

END statReportResult;
/

prompt
prompt Creating procedure DELIVERY_REPORT_AGENT
prompt ========================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Agent(
/*==============================================================*/
/*                  代理交收报表存储过程                        */
/*==============================================================*/
     userID IN varchar2,                -- 代理ID
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2,           -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
     statReportAgent OUT statReportResult.resultRef         -- 返回结果集
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover_gdklsf NUMBER;            -- 成交笔数（广东快乐十分）
     turnover_cqssc NUMBER;             -- 成交笔数（重庆时时彩）
     turnover_hklhc NUMBER;             -- 成交笔数（香港六合彩）
     turnover_bjsc NUMBER;              -- 成交笔数（北京赛车）
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     type   mycursor   is   ref   cursor;

     sql_str varchar2(2000);                    -- 临时SQL语句

     sql_gdklsf varchar2(2000);                 -- 广东快乐十分查询 sql
     cursor_his_gdklsf  mycursor;               -- 广东快乐十分游标
     member_pos_gdklsf TB_GDKLSF_HIS%rowtype;   -- 广东快乐十分数据对象

     sql_cqssc varchar2(2000);                  -- 重庆时时彩查询 sql
     cursor_his_cqssc  mycursor;                -- 重庆时时彩游标
     member_pos_cqssc TB_CQSSC_HIS%rowtype;     -- 重庆时时彩数据对象

     sql_hklhc varchar2(2000);                  -- 香港六合彩查询 sql
     cursor_his_hklhc  mycursor;                -- 香港六合彩游标
     member_pos_hklhc TB_HKLHC_HIS%rowtype;     -- 香港六合彩数据对象

     sql_bjsc varchar2(2000);                   -- 北京赛车查询 sql
     cursor_his_bjsc  mycursor;                 -- 北京赛车游标
     member_pos_bjsc TB_BJSC_HIS%rowtype;       -- 北京赛车数据对象

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）
     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值
     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     -- 占成设置值只取最后一个值，故总和值无效
     recNum_total NUMBER;                     -- 临时变量，有效会员数
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）

BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     -- 赚取退水
     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 2. 查询代理对应的会员信息
     declare
     cursor agent_cursor
     IS
     SELECT * FROM
         (TB_MEMBER_STAFF_EXT ext INNER JOIN TB_FRAME_MEMBER_STAFF member ON ext.MEMBER_STAFF_ID = member.ID)
     WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.1 删除临时表中的本级统计数据
          DELETE FROM TEMP_DELIVERYREPORT WHERE PARENT_ID = userID;

          -- 2.2 循环处理所有的会员信息
          FOR agent_member_pos IN agent_cursor LOOP
              -- 3.1 填充数据记录
              subordinate := agent_member_pos.ACCOUNT;             -- 下级登陆账号
              userName := agent_member_pos.CHS_NAME;               -- 用户名称
              subID := agent_member_pos.ID;                        -- 记录ID

              -- 3.2 初始化数据
              turnover_gdklsf := 0;           -- 成交笔数（广东快乐十分）
              turnover_cqssc := 0;            -- 成交笔数（重庆时时彩）
              turnover_hklhc := 0;            -- 成交笔数（香港六合彩）
              turnover_bjsc := 0;             -- 成交笔数（北京赛车）
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;
              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              /******** 广东快乐十分 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'GDKLSF' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_GDKLSF_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_gdklsf := 'SELECT * FROM TB_GDKLSF_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_gdklsf := sql_gdklsf || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_gdklsf
              FOR
              sql_gdklsf;

              -- 统计投注笔数
              execute immediate sql_str into turnover_gdklsf;
              turnover_total := turnover_total + turnover_gdklsf;

              BEGIN
                   LOOP
                   FETCH cursor_his_gdklsf INTO member_pos_gdklsf;
                       -- 无数据则退出
                       IF (cursor_his_gdklsf%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_gdklsf.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_gdklsf.WIN_STATE = 1 OR member_pos_gdklsf.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_gdklsf.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100;
                          winBackWater := winBackWater + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100) * (1 - member_pos_gdklsf.RATE_AGENT / 100);

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_gdklsf.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_gdklsf.WIN_AMOUNT;
                          memberAmount_temp := member_pos_gdklsf.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_gdklsf.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_gdklsf.MONEY;
                          memberAmount_temp := - member_pos_gdklsf.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_gdklsf.RATE_CHIEF + member_pos_gdklsf.RATE_BRANCH + member_pos_gdklsf.RATE_GEN_AGENT + member_pos_gdklsf.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_gdklsf.WIN_STATE = 1 OR member_pos_gdklsf.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_BRANCH - member_pos_gdklsf.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_GEN_AGENT - member_pos_gdklsf.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_STOCKHOLDER - member_pos_gdklsf.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_BRANCH - member_pos_gdklsf.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_gdklsf.RATE_STOCKHOLDER/100 - member_pos_gdklsf.RATE_GEN_AGENT/100 -  member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_STOCKHOLDER - member_pos_gdklsf.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_gdklsf.RATE_GEN_AGENT/100 -  member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_GEN_AGENT - member_pos_gdklsf.COMMISSION_AGENT) / 100) * (1 - member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_MEMBER - 0) / 100);
                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_BRANCH / 100)) * member_pos_gdklsf.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_STOCKHOLDER / 100)) * member_pos_gdklsf.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_AGENT / 100)) * member_pos_gdklsf.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_GEN_AGENT / 100)) * member_pos_gdklsf.RATE_STOCKHOLDER/100;

                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_BRANCH / 100)) * member_pos_gdklsf.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100 - member_pos_gdklsf.RATE_GEN_AGENT/100 - member_pos_gdklsf.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_AGENT / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100 - member_pos_gdklsf.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       winBackWaterResult := rateAgent - winBackWater;

                       rate := member_pos_gdklsf.RATE_AGENT ;-- 代理占成（只需要存最后一条记录的占成即可）
                       rateChiefSet := rateChiefSet + member_pos_gdklsf.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_gdklsf.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_gdklsf.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_gdklsf.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_gdklsf.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_gdklsf.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_gdklsf.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_gdklsf.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_gdklsf.COMMISSION_AGENT;

                   END LOOP;
                   CLOSE cursor_his_gdklsf;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接填充代理实占结果值
              realResult := rateAgent;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 广东快乐十分 结束 ********/

              /******** 重庆时时彩 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'CQSSC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_CQSSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_cqssc := 'SELECT * FROM TB_CQSSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_cqssc := sql_cqssc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_cqssc
              FOR
              sql_cqssc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_cqssc;
              turnover_total := turnover_total + turnover_cqssc;

              BEGIN
                   LOOP
                   FETCH cursor_his_cqssc INTO member_pos_cqssc;
                       -- 无数据则退出
                       IF (cursor_his_cqssc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_cqssc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_cqssc.WIN_STATE = 1 OR member_pos_cqssc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_cqssc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100;
                          winBackWater := winBackWater + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100) * (1 - member_pos_cqssc.RATE_AGENT / 100);

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_cqssc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_cqssc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_cqssc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_cqssc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_cqssc.MONEY;
                          memberAmount_temp := - member_pos_cqssc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_cqssc.RATE_CHIEF + member_pos_cqssc.RATE_BRANCH + member_pos_cqssc.RATE_GEN_AGENT + member_pos_cqssc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_cqssc.WIN_STATE = 1 OR member_pos_cqssc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_BRANCH - member_pos_cqssc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_GEN_AGENT - member_pos_cqssc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_STOCKHOLDER - member_pos_cqssc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_BRANCH - member_pos_cqssc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_cqssc.RATE_STOCKHOLDER/100 - member_pos_cqssc.RATE_GEN_AGENT/100 -  member_pos_cqssc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_STOCKHOLDER - member_pos_cqssc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_cqssc.RATE_GEN_AGENT/100 -  member_pos_cqssc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_GEN_AGENT - member_pos_cqssc.COMMISSION_AGENT) / 100) * (1 - member_pos_cqssc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_BRANCH / 100)) * member_pos_cqssc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_STOCKHOLDER / 100)) * member_pos_cqssc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_AGENT / 100)) * member_pos_cqssc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_GEN_AGENT / 100)) * member_pos_cqssc.RATE_STOCKHOLDER/100;
                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100)) * member_pos_cqssc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_BRANCH / 100)) * member_pos_cqssc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100 - member_pos_cqssc.RATE_GEN_AGENT/100 - member_pos_cqssc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_AGENT / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100 - member_pos_cqssc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       winBackWaterResult := rateAgent - winBackWater;

                       rate := member_pos_cqssc.RATE_AGENT ;-- 代理占成（只需要存最后一条记录的占成即可）
                       rateChiefSet := rateChiefSet + member_pos_cqssc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_cqssc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_cqssc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_cqssc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_cqssc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_cqssc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_cqssc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_cqssc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_cqssc.COMMISSION_AGENT;
                   END LOOP;
                   CLOSE cursor_his_cqssc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接填充代理实占结果值
              realResult := rateAgent;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 重庆时时彩 结束 ********/

              /******** 香港六合彩 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'HKLHC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_HKLHC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_hklhc := 'SELECT * FROM TB_HKLHC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_hklhc := sql_hklhc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_hklhc
              FOR
              sql_hklhc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_hklhc;
              turnover_total := turnover_total + turnover_hklhc;

              BEGIN
                   LOOP
                   FETCH cursor_his_hklhc INTO member_pos_hklhc;

                       -- 无数据则退出
                       IF (cursor_his_hklhc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_hklhc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_hklhc.WIN_STATE = 1 OR member_pos_hklhc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_hklhc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100;
                          winBackWater := winBackWater + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100) * (1 - member_pos_hklhc.RATE_AGENT / 100);

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_hklhc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_hklhc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_hklhc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_hklhc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_hklhc.MONEY;
                          memberAmount_temp := - member_pos_hklhc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                       -- 累积上级的占成
                       --            * ((member_pos_hklhc.RATE_CHIEF + member_pos_hklhc.RATE_BRANCH + member_pos_hklhc.RATE_GEN_AGENT + member_pos_hklhc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_hklhc.WIN_STATE = 1 OR member_pos_hklhc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_BRANCH - member_pos_hklhc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_GEN_AGENT - member_pos_hklhc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_STOCKHOLDER - member_pos_hklhc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_BRANCH - member_pos_hklhc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_hklhc.RATE_STOCKHOLDER/100 - member_pos_hklhc.RATE_GEN_AGENT/100 -  member_pos_hklhc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_STOCKHOLDER - member_pos_hklhc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_hklhc.RATE_GEN_AGENT/100 -  member_pos_hklhc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_GEN_AGENT - member_pos_hklhc.COMMISSION_AGENT) / 100) * (1 - member_pos_hklhc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_BRANCH / 100)) * member_pos_hklhc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_STOCKHOLDER / 100)) * member_pos_hklhc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_AGENT / 100)) * member_pos_hklhc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_GEN_AGENT / 100)) * member_pos_hklhc.RATE_STOCKHOLDER/100;
                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100)) * member_pos_hklhc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_BRANCH / 100)) * member_pos_hklhc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100 - member_pos_hklhc.RATE_GEN_AGENT/100 - member_pos_hklhc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_AGENT / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100 - member_pos_hklhc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       winBackWaterResult := rateAgent - winBackWater;

                       rate := member_pos_hklhc.RATE_AGENT ;-- 代理占成（只需要存最后一条记录的占成即可）
                       rateChiefSet := rateChiefSet + member_pos_hklhc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_hklhc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_hklhc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_hklhc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_hklhc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_hklhc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_hklhc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_hklhc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_hklhc.COMMISSION_AGENT;
                   END LOOP;
                   CLOSE cursor_his_hklhc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接填充代理实占结果值
              realResult := rateAgent;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 香港六合彩 结束 ********/

              /******** 北京赛车 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'BJSC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_BJSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_bjsc := 'SELECT * FROM TB_BJSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_bjsc := sql_bjsc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_bjsc
              FOR
              sql_bjsc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_bjsc;
              turnover_total := turnover_total + turnover_bjsc;

              BEGIN
                   LOOP
                   FETCH cursor_his_bjsc INTO member_pos_bjsc;
                       -- 无数据则退出
                       IF (cursor_his_bjsc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_bjsc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_bjsc.WIN_STATE = 1 OR member_pos_bjsc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_bjsc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100;
                          winBackWater := winBackWater + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100) * (1 - member_pos_bjsc.RATE_AGENT / 100);

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_bjsc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_bjsc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_bjsc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_bjsc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_bjsc.MONEY;
                          memberAmount_temp := - member_pos_bjsc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_bjsc.RATE_CHIEF + member_pos_bjsc.RATE_BRANCH + member_pos_bjsc.RATE_GEN_AGENT + member_pos_bjsc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_bjsc.WIN_STATE = 1 OR member_pos_bjsc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_BRANCH - member_pos_bjsc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_GEN_AGENT - member_pos_bjsc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_STOCKHOLDER - member_pos_bjsc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_BRANCH - member_pos_bjsc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_bjsc.RATE_STOCKHOLDER/100 - member_pos_bjsc.RATE_GEN_AGENT/100 -  member_pos_bjsc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_STOCKHOLDER - member_pos_bjsc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_bjsc.RATE_GEN_AGENT/100 -  member_pos_bjsc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_GEN_AGENT - member_pos_bjsc.COMMISSION_AGENT) / 100) * (1 - member_pos_bjsc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_BRANCH / 100)) * member_pos_bjsc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_STOCKHOLDER / 100)) * member_pos_bjsc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_AGENT / 100)) * member_pos_bjsc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_GEN_AGENT / 100)) * member_pos_bjsc.RATE_STOCKHOLDER/100;

                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100)) * member_pos_bjsc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_BRANCH / 100)) * member_pos_bjsc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100 - member_pos_bjsc.RATE_GEN_AGENT/100 - member_pos_bjsc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_AGENT / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100 - member_pos_bjsc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       winBackWaterResult := rateAgent - winBackWater;

                       rate := member_pos_bjsc.RATE_AGENT ;-- 代理占成（只需要存最后一条记录的占成即可）
                       rateChiefSet := rateChiefSet + member_pos_bjsc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_bjsc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_bjsc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_bjsc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_bjsc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_bjsc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_bjsc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_bjsc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_bjsc.COMMISSION_AGENT;

                   END LOOP;
                   CLOSE cursor_his_bjsc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接填充代理实占结果值
              realResult := rateAgent;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 北京赛车 结束 ********/

              -- 计算占成设置值的平均值
              IF (recNum > 0) THEN
                 rateChiefSet := rateChiefSet / recNum;
                 rateBranchSet := rateBranchSet / recNum;
                 rateStockholderSet := rateStockholderSet / recNum;
                 rateGenAgentSet := rateGenAgentSet / recNum;
                 rateAgentSet := rateAgentSet / recNum;

                 -- rate的值设置为代理的值
                 rate := rateAgentSet;
                 -- 累加有效投注会员数
                 recNum_total := recNum_total + 1;
                 -- 累加占着设置值的总值
                 --rate_total := rate_total + rate;
                 --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                 --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                 --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                 --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                 --rateAgentSet_total := rateAgentSet_total + rateAgentSet;
              END IF;

              -- 5. 累加总额
              amount_total := amount_total + amount;
              validAmount_total := validAmount_total + validAmount;
              memberAmount_total := memberAmount_total + memberAmount;
              memberBackWater_total := memberBackWater_total + memberBackWater;
              subordinateAmount_total := subordinateAmount_total + subordinateAmount;
              winBackWater_total := winBackWater_total + winBackWater;
              realResult_total := realResult_total + realResult;
              winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
              paySuperior_total := paySuperior_total + paySuperior;

              -- 6. 累积上线所对应的计算后的佣金、占成（总额）
              commissionBranch_total := commissionBranch_total + commissionBranch;
              commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
              commissionStockholder_total := commissionStockholder_total + commissionStockholder;
              commissionAgent_total := commissionAgent_total + commissionAgent;
              commissionMember_total := commissionMember_total + commissionMember;

              -- 赚取退水
              winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
              winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
              winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
              winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;
              winCommissionMember_total := winCommissionMember_total + winCommissionMember;

              rateChief_total := rateChief_total + rateChief;
              rateBranch_total := rateBranch_total + rateBranch;
              rateGenAgent_total := rateGenAgent_total + rateGenAgent;
              rateStockholder_total := rateStockholder_total + rateStockholder;
              rateAgent_total := rateAgent_total + rateAgent;

              moneyRateChief_total := moneyRateChief_total + moneyRateChief;
              moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
              moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
              moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
              moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

              subordinateChief_total := subordinateChief_total + subordinateChief;
              subordinateBranch_total := subordinateBranch_total + subordinateBranch;
              subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
              subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
              subordinateAgent_total := subordinateAgent_total + subordinateAgent;

              -- 如果成交笔数大于0，则保存数据
              IF ((turnover_gdklsf + turnover_cqssc + turnover_hklhc + turnover_bjsc) > 0) THEN

                 -- 7. 数据插入临时表
                 INSERT INTO TEMP_DELIVERYREPORT
                        (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                        COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT, RATE, RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET, COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                        MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                 VALUES
                        (userID, subID, '9', '0', subordinate, userName, (turnover_gdklsf + turnover_cqssc + turnover_hklhc + turnover_bjsc), amount, validAmount, memberAmount, memberBackWater, subordinateAmount, winBackWater, realResult, winBackWaterResult, paySuperior,
                        commissionBranch, commissionGenAgent, commissionStockholder, commissionAgent, commissionMember, winCommissionBranch, winCommissionGenAgent, winCommissionStockholder, winCommissionAgent, winCommissionMember, rateChief, rateBranch, rateGenAgent, rateStockholder, rateAgent, subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent, rate, rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet, commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                        moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent
                        );
              END IF;

          END LOOP;
      END;

      -- 6.1 数据不存在（成交笔数为0）则返回
      IF (turnover_total < 1) THEN
         resultFlag := 2; -- 设置结果
         return;
      END IF;
      -- dbms_output.put_line('rateAgent_total：'||rateAgent_total);

      -- 计算占成设置值的平均值
      --IF (recNum_total > 0) THEN
      --   rate_total := rate_total / recNum_total;
      --   rateChiefSet_total := rateChiefSet_total / recNum_total;
      --   rateBranchSet_total := rateBranchSet_total / recNum_total;
      --   rateStockholderSet_total := rateStockholderSet_total / recNum_total;
      --   rateGenAgentSet_total := rateGenAgentSet_total / recNum_total;
      --   rateAgentSet_total := rateAgentSet_total / recNum_total;
      --END IF;

      /******** 补货数据 开始 ********/
      -- 6.2 查询补货数据（补货后续需要区分玩法类型查询）
      sql_replenish := 'SELECT * FROM TB_REPLENISH WHERE REPLENISH_USER_ID = ' || userID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
      -- 只查询结算了的补货数据
      sql_replenish := sql_replenish || ' AND WIN_STATE IN (''1'',''2'',''3'') ';

      IF playType IS NOT NULL THEN
         sql_replenish := sql_replenish || ' AND TYPE_CODE IN (' || playType || ')';
      END IF;

      -- 判断彩票种类
      IF LOTTERY1688Type = 'GDKLSF' THEN
         -- 广东快乐十分
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''GDKLSF_%'' ';
      END IF;
      IF LOTTERY1688Type = 'HKLHC' THEN
         -- 香港六合彩
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''HK_%'' ';
      END IF;
      IF LOTTERY1688Type = 'CQSSC' THEN
         -- 重庆时时彩
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''CQSSC_%'' ';
      END IF;
      IF LOTTERY1688Type = 'BJSC' THEN
         -- 北京赛车
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''BJSC_%'' ';
      END IF;

      -- 初始化
      turnover_replenish := 0;                         -- 补货笔数
      amount_replenish := 0;                           -- 补货投注总额
      validAmount_replenish :=0;                       -- 补货有效金额
      amount_win_replenish := 0;                       -- 补货输赢
      backWater_replenish := 0;                        -- 补货退水
      backWaterResult_replenish := 0;                  -- 退水后结果
      backWater_replenish_temp := 0;                   -- 补货输赢（临时）
      amount_win_replenish_temp := 0;                  -- 补货输赢（临时）

      -- 退水相关数据
      commissionBranch := 0;
      commissionStockholder := 0;
      commissionGenAgent := 0;
      commissionAgent := 0;

      -- 赚取佣金（退水）
      winCommissionBranch := 0;
      winCommissionGenAgent := 0;
      winCommissionStockholder := 0;
      winCommissionAgent := 0;

      -- 实占结果
      rateChief := 0;
      rateBranch := 0;
      rateGenAgent := 0;
      rateStockholder := 0;
      rateAgent := 0;

      -- 实占注额
      moneyRateChief := 0;
      moneyRateBranch := 0;
      moneyRateGenAgent := 0;
      moneyRateStockholder := 0;
      moneyRateAgent := 0;

      -- 应收下线
      subordinateChief := 0;
      subordinateBranch := 0;
      subordinateGenAgent := 0;
      subordinateStockholder := 0;
      subordinateAgent := 0;

      -- 执行查询，打开游标
      OPEN cursor_replenish
      FOR
      sql_replenish;

      BEGIN
           LOOP
           FETCH cursor_replenish INTO member_pos_replenish;
               -- 无数据则退出
               IF (cursor_replenish%found = false) THEN
                   EXIT;
               END IF;

               -- 累加补货总额
               amount_replenish := amount_replenish + member_pos_replenish.MONEY;

               -- 累加补货笔数
               turnover_replenish := turnover_replenish + 1;

               -- 补货输赢，对应该代理所有补货的总和(不计退水)
               IF (member_pos_replenish.WIN_STATE = 1) THEN
                  -- 累加“中奖”的投注额
                  amount_win_replenish := amount_win_replenish + member_pos_replenish.WIN_AMOUNT;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_AGENT / 100;
                  amount_win_replenish_temp := member_pos_replenish.WIN_AMOUNT;
               END IF;
               IF (member_pos_replenish.WIN_STATE = 2) THEN
                  -- 减去“未中奖”的投注额
                  amount_win_replenish := amount_win_replenish - member_pos_replenish.MONEY;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_AGENT / 100;
                  amount_win_replenish_temp := - member_pos_replenish.MONEY;
               END IF;

               -- 补货有效金额，不计算打和
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  validAmount_replenish := validAmount_replenish + member_pos_replenish.MONEY;
               END IF;
               -- TODO 退水需要补全所有级别的退水信息
               -- 补货退水（代理佣金*投注额，除了打和，其他的都要退水）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  backWater_replenish := backWater_replenish + backWater_replenish_temp;
                  --rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;
               END IF;
               -- 打和则不计算佣金（退水）（当做直属会员处理）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  commissionBranch := commissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER) / 100);
                  commissionStockholder := commissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - member_pos_replenish.COMMISSION_GEN_AGENT) / 100);
                  commissionGenAgent := commissionGenAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_GEN_AGENT - member_pos_replenish.COMMISSION_AGENT) / 100);
                  commissionAgent := commissionAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_AGENT - 0) / 100);

                  -- 赚取佣金（退水）
                  winCommissionBranch := winCommissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_replenish.RATE_STOCKHOLDER/100 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);
                  winCommissionStockholder := winCommissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - member_pos_replenish.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);
                  winCommissionGenAgent := winCommissionGenAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_GEN_AGENT - member_pos_replenish.COMMISSION_AGENT) / 100) * (1 - member_pos_replenish.RATE_AGENT/100);
                  winCommissionAgent := winCommissionAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_AGENT - member_pos_replenish.COMMISSION_MEMBER) / 100);
                  winCommissionMember := winCommissionMember + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_MEMBER - 0) / 100);

                  -- 实占结果（退水后结果，也即是 退水 + 输赢）*占成%
                  rateChief := rateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  rateBranch := rateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * member_pos_replenish.RATE_BRANCH/100;
                  rateStockholder := rateStockholder + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100)) * member_pos_replenish.RATE_STOCKHOLDER/100;
                  rateGenAgent := rateGenAgent + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_AGENT / 100)) * member_pos_replenish.RATE_GEN_AGENT/100;

                  -- 实占注额（有效的投注金额 * 占成%）
                  moneyRateChief := moneyRateChief + (member_pos_replenish.MONEY * member_pos_replenish.RATE_CHIEF / 100);
                  moneyRateBranch := moneyRateBranch + (member_pos_replenish.MONEY * member_pos_replenish.RATE_BRANCH / 100);
                  moneyRateStockholder := moneyRateStockholder + (member_pos_replenish.MONEY * member_pos_replenish.RATE_STOCKHOLDER / 100);
                  moneyRateGenAgent := moneyRateGenAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_GEN_AGENT / 100);
                  moneyRateAgent := moneyRateAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_AGENT / 100);

                  -- 各级应收下线
                  subordinateChief := subordinateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  subordinateBranch := subordinateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_replenish.RATE_GEN_AGENT/100 - member_pos_replenish.RATE_STOCKHOLDER/100);
                  subordinateStockholder := subordinateStockholder + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_replenish.RATE_GEN_AGENT/100);
                  subordinateGenAgent := subordinateGenAgent + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_AGENT / 100)) * (1);
               END IF;

           END LOOP;
           CLOSE cursor_replenish;
      END;

      -- 补货退水后结果（退水 + 输赢）
      backWaterResult_replenish := backWater_replenish + amount_win_replenish;

      -- 4. 补货数据插入临时表（处理数据类型）（数据对应的 USER_TYPE 为实际值 + a，即如果实际值为1，则填充值 b，实际值为0，则填充a）
      -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
      IF (turnover_replenish > 0) THEN
         INSERT INTO TEMP_DELIVERYREPORT
              (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, WIN_BACK_WATER_RESULT,
               COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
               MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
         VALUES
              (userID, userID, 'g', '1', '补货', '补货', turnover_replenish, amount_replenish, validAmount_replenish, amount_win_replenish, backWater_replenish, backWaterResult_replenish,
              commissionBranch, commissionGenAgent, commissionStockholder, commissionAgent, commissionMember, winCommissionBranch, winCommissionGenAgent, winCommissionStockholder, winCommissionAgent, winCommissionMember, rateChief, rateBranch, rateGenAgent, rateStockholder, rateAgent, subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent,
              moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent);
      END IF;

      -- 5. 累加总额（增加补货数据）
      turnover_total := turnover_total + turnover_replenish;
      amount_total := amount_total + amount_replenish;
      validAmount_total := validAmount_total + validAmount_replenish;
      memberAmount_total := memberAmount_total + amount_win_replenish;
      memberBackWater_total := memberBackWater_total + backWater_replenish;
      winBackWaterResult_total := winBackWaterResult_total + backWaterResult_replenish;
      -- commissionAgent_total := commissionAgent_total + backWater_replenish; -- 增加代理补货退水

      -- 6. 累积补货上线所对应的计算后的佣金、占成（总额）
      commissionBranch_total := commissionBranch_total + commissionBranch;
      commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
      commissionStockholder_total := commissionStockholder_total + commissionStockholder;
      commissionAgent_total := commissionAgent_total + commissionAgent;

      winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
      winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
      winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
      winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;

      rateChief_total := rateChief_total + rateChief;
      rateBranch_total := rateBranch_total + rateBranch;
      rateGenAgent_total := rateGenAgent_total + rateGenAgent;
      rateStockholder_total := rateStockholder_total + rateStockholder;
      rateAgent_total := rateAgent_total + rateAgent;

      moneyRateChief_total := moneyRateChief_total + moneyRateChief;
      moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
      moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
      moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
      moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

      subordinateChief_total := subordinateChief_total + subordinateChief;
      subordinateBranch_total := subordinateBranch_total + subordinateBranch;
      subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
      subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
      subordinateAgent_total := subordinateAgent_total + subordinateAgent;
      /******** 补货数据 结束 ********/

      -- 6.3 总额数据插入临时表（为方便排序，总额数据对应的 USER_TYPE 为实际值 + A，即如果实际值为1，则填充值 B，实际值为0，则填充A）
      -- C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
      IF (turnover_total > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT, RATE, RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET, COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
        VALUES
            (userID, userID, 'G', '0', '', '合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total, rate, rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet, commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;

      -- 7. 从临时表中查询数据
      OPEN statReportAgent
      FOR
      SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 8. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/

prompt
prompt Creating procedure DELIVERY_REPORT_DIR_MEMBER
prompt =============================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Dir_Member(
/*==============================================================*/
/*   直属会员交收报表存储过程（此存储过程提供其他管理用户调用） */
/*==============================================================*/
     userID IN varchar2,                -- 管理用户ID
     userType IN varchar2,              -- 管理用户类型
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2            -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover_gdklsf NUMBER;            -- 成交笔数（广东快乐十分）
     turnover_cqssc NUMBER;             -- 成交笔数（重庆时时彩）
     turnover_hklhc NUMBER;             -- 成交笔数（香港六合彩）
     turnover_bjsc NUMBER;              -- 成交笔数（北京赛车）
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     type   mycursor   is   ref   cursor;

     sql_str varchar2(2000);                    -- 临时SQL语句

     sql_gdklsf varchar2(2000);                 -- 广东快乐十分查询 sql
     cursor_his_gdklsf  mycursor;               -- 广东快乐十分游标
     member_pos_gdklsf TB_GDKLSF_HIS%rowtype;   -- 广东快乐十分数据对象

     sql_cqssc varchar2(2000);                  -- 重庆时时彩查询 sql
     cursor_his_cqssc  mycursor;                -- 重庆时时彩游标
     member_pos_cqssc TB_CQSSC_HIS%rowtype;     -- 重庆时时彩数据对象

     sql_hklhc varchar2(2000);                  -- 香港六合彩查询 sql
     cursor_his_hklhc  mycursor;                -- 香港六合彩游标
     member_pos_hklhc TB_HKLHC_HIS%rowtype;     -- 香港六合彩数据对象

     sql_bjsc varchar2(2000);                   -- 北京赛车十分查询 sql
     cursor_his_bjsc  mycursor;                 -- 北京赛车十分游标
     member_pos_bjsc TB_BJSC_HIS%rowtype;       -- 北京赛车十分数据对象

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）
     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值

     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     -- 占成设置值只取最后一个值，故总和值无效
     recNum_total NUMBER;                     -- 临时变量，有效会员数
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）

BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     -- 赚取退水
     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 2 查询管理对应的直属会员信息
      declare
      cursor directly_cursor
      IS
      SELECT * FROM
         (TB_MEMBER_STAFF_EXT ext INNER JOIN TB_FRAME_MEMBER_STAFF member ON ext.MEMBER_STAFF_ID = member.ID)
      WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.2 循环处理所有的会员信息
          FOR dir_member_pos IN directly_cursor LOOP
              -- 3.1 填充数据记录
              subordinate := dir_member_pos.ACCOUNT;             -- 下级登陆账号
              userName := dir_member_pos.CHS_NAME;               -- 用户名称
              subID := dir_member_pos.ID;                        -- 记录ID

              -- 3.2 初始化数据
              turnover_gdklsf := 0;           -- 成交笔数（广东快乐十分）
              turnover_cqssc := 0;            -- 成交笔数（重庆时时彩）
              turnover_hklhc := 0;            -- 成交笔数（香港六合彩）
              turnover_bjsc := 0;             -- 成交笔数（北京赛车）
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;

              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              /******** 广东快乐十分 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'GDKLSF' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_GDKLSF_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_gdklsf := 'SELECT * FROM TB_GDKLSF_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_gdklsf := sql_gdklsf || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_gdklsf
              FOR
              sql_gdklsf;

              -- 统计投注笔数
              execute immediate sql_str into turnover_gdklsf;
              turnover_total := turnover_total + turnover_gdklsf;

              BEGIN
                   LOOP
                   FETCH cursor_his_gdklsf INTO member_pos_gdklsf;
                       -- 无数据则退出
                       IF (cursor_his_gdklsf%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_gdklsf.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_gdklsf.WIN_STATE = 1 OR member_pos_gdklsf.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_gdklsf.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100;
                          -- 根据不同的管理用户类型，计算对应的佣金
                          IF (userType = 2) THEN
                            -- 总监
                            winBackWater := winBackWater + (member_pos_gdklsf.MONEY * (1 - member_pos_gdklsf.COMMISSION_MEMBER) / 100) * (1 - member_pos_gdklsf.RATE_CHIEF / 100);
                          ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWater := winBackWater + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_BRANCH - member_pos_gdklsf.COMMISSION_MEMBER) / 100) * (1 - member_pos_gdklsf.RATE_BRANCH / 100);
                          ELSIF(userType = 4) THEN
                            -- 股东
                            winBackWater := winBackWater + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_STOCKHOLDER - member_pos_gdklsf.COMMISSION_MEMBER) / 100) * (1 - member_pos_gdklsf.RATE_STOCKHOLDER / 100);
                          ELSIF(userType = 5) THEN
                            -- 总代理
                            winBackWater := winBackWater + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_GEN_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100) * (1 - member_pos_gdklsf.RATE_GEN_AGENT / 100);
                          END IF;

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_gdklsf.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_gdklsf.WIN_AMOUNT;
                          memberAmount_temp := member_pos_gdklsf.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_gdklsf.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_gdklsf.MONEY;
                          memberAmount_temp := - member_pos_gdklsf.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_gdklsf.RATE_CHIEF + member_pos_gdklsf.RATE_BRANCH + member_pos_gdklsf.RATE_GEN_AGENT + member_pos_gdklsf.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_gdklsf.WIN_STATE = 1 OR member_pos_gdklsf.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_BRANCH - member_pos_gdklsf.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_GEN_AGENT - member_pos_gdklsf.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_STOCKHOLDER - member_pos_gdklsf.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_BRANCH - member_pos_gdklsf.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_gdklsf.RATE_STOCKHOLDER/100 - member_pos_gdklsf.RATE_GEN_AGENT/100 -  member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_STOCKHOLDER - member_pos_gdklsf.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_gdklsf.RATE_GEN_AGENT/100 -  member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_GEN_AGENT - member_pos_gdklsf.COMMISSION_AGENT) / 100) * (1 - member_pos_gdklsf.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_AGENT - member_pos_gdklsf.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_gdklsf.MONEY * (member_pos_gdklsf.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_BRANCH / 100)) * member_pos_gdklsf.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_STOCKHOLDER / 100)) * member_pos_gdklsf.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_AGENT / 100)) * member_pos_gdklsf.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_GEN_AGENT / 100)) * member_pos_gdklsf.RATE_STOCKHOLDER/100;

                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_gdklsf.MONEY * member_pos_gdklsf.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_BRANCH / 100)) * member_pos_gdklsf.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100 - member_pos_gdklsf.RATE_GEN_AGENT/100 - member_pos_gdklsf.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_AGENT / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_gdklsf.RATE_AGENT/100 - member_pos_gdklsf.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       IF (userType = 2) THEN
                            -- 总监
                            winBackWaterResult := rateChief - winBackWater;
                       ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWaterResult := rateBranch - winBackWater;
                       END IF;

                       rateChiefSet := rateChiefSet + member_pos_gdklsf.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_gdklsf.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_gdklsf.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_gdklsf.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_gdklsf.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_gdklsf.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_gdklsf.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_gdklsf.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_gdklsf.COMMISSION_AGENT;

                       IF (userType = 2) THEN
                          -- 总监
                          rate := rateChiefSet;
                       ELSIF(userType = 3) THEN
                          -- 分公司
                          rate := rateBranchSet;
                       ELSIF(userType = 4) THEN
                          -- 股东
                          rate := rateStockholderSet;
                       ELSIF(userType = 5) THEN
                          -- 总代理
                          rate := rateGenAgentSet;
                       END IF;

                   END LOOP;
                   CLOSE cursor_his_gdklsf;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接设置直属会员对应的上级实占结果值
              IF (userType = 2) THEN
                -- 总监
                realResult := rateChief;
              ELSIF(userType = 3) THEN
                -- 分公司
                realResult := rateBranch;
              ELSIF(userType = 4) THEN
                -- 股东
                realResult := rateStockholder;
              ELSIF(userType = 5) THEN
                -- 总代理
                realResult := rateGenAgent;
              END IF;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 广东快乐十分 结束 ********/

              /******** 重庆时时彩 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'CQSSC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_CQSSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_cqssc := 'SELECT * FROM TB_CQSSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_cqssc := sql_cqssc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_cqssc
              FOR
              sql_cqssc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_cqssc;
              turnover_total := turnover_total + turnover_cqssc;

              BEGIN
                   LOOP
                   FETCH cursor_his_cqssc INTO member_pos_cqssc;
                       -- 无数据则退出
                       IF (cursor_his_cqssc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_cqssc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_cqssc.WIN_STATE = 1 OR member_pos_cqssc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_cqssc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100;
                          IF (userType = 2) THEN
                            -- 总监
                            winBackWater := winBackWater + (member_pos_cqssc.MONEY * (0 - member_pos_cqssc.COMMISSION_MEMBER) / 100) * (1 - member_pos_cqssc.RATE_CHIEF / 100);
                          ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWater := winBackWater + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100) * (1 - member_pos_cqssc.RATE_AGENT / 100);
                          END IF;
                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_cqssc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_cqssc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_cqssc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_cqssc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_cqssc.MONEY;
                          memberAmount_temp := - member_pos_cqssc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_cqssc.RATE_CHIEF + member_pos_cqssc.RATE_BRANCH + member_pos_cqssc.RATE_GEN_AGENT + member_pos_cqssc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_cqssc.WIN_STATE = 1 OR member_pos_cqssc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_BRANCH - member_pos_cqssc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_GEN_AGENT - member_pos_cqssc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_STOCKHOLDER - member_pos_cqssc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_BRANCH - member_pos_cqssc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_cqssc.RATE_STOCKHOLDER/100 - member_pos_cqssc.RATE_GEN_AGENT/100 -  member_pos_cqssc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_STOCKHOLDER - member_pos_cqssc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_cqssc.RATE_GEN_AGENT/100 -  member_pos_cqssc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_GEN_AGENT - member_pos_cqssc.COMMISSION_AGENT) / 100) * (1 - member_pos_cqssc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_AGENT - member_pos_cqssc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_cqssc.MONEY * (member_pos_cqssc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_BRANCH / 100)) * member_pos_cqssc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_STOCKHOLDER / 100)) * member_pos_cqssc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_AGENT / 100)) * member_pos_cqssc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_GEN_AGENT / 100)) * member_pos_cqssc.RATE_STOCKHOLDER/100;
                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100)) * member_pos_cqssc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_cqssc.MONEY * member_pos_cqssc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_BRANCH / 100)) * member_pos_cqssc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100 - member_pos_cqssc.RATE_GEN_AGENT/100 - member_pos_cqssc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_AGENT / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_cqssc.RATE_AGENT/100 - member_pos_cqssc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_cqssc.MONEY * member_pos_cqssc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       IF (userType = 2) THEN
                            -- 总监
                            winBackWaterResult := rateChief - winBackWater;
                       ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWaterResult := rateBranch - winBackWater;
                       END IF;

                       rateChiefSet := rateChiefSet + member_pos_cqssc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_cqssc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_cqssc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_cqssc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_cqssc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_cqssc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_cqssc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_cqssc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_cqssc.COMMISSION_AGENT;

                       IF (userType = 2) THEN
                          -- 总监
                          rate := rateChiefSet;
                       ELSIF(userType = 3) THEN
                          -- 分公司
                          rate := rateBranchSet;
                       ELSIF(userType = 4) THEN
                          -- 股东
                          rate := rateStockholderSet;
                       ELSIF(userType = 5) THEN
                          -- 总代理
                          rate := rateGenAgentSet;
                       END IF;

                   END LOOP;
                   CLOSE cursor_his_cqssc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接设置直属会员对应的上级实占结果值
              IF (userType = 2) THEN
                -- 总监
                realResult := rateChief;
              ELSIF(userType = 3) THEN
                -- 分公司
                realResult := rateBranch;
              ELSIF(userType = 4) THEN
                -- 股东
                realResult := rateStockholder;
              ELSIF(userType = 5) THEN
                -- 总代理
                realResult := rateGenAgent;
              END IF;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 重庆时时彩 结束 ********/

              /******** 香港六合彩 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'HKLHC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_HKLHC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_hklhc := 'SELECT * FROM TB_HKLHC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_hklhc := sql_hklhc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_hklhc
              FOR
              sql_hklhc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_hklhc;
              turnover_total := turnover_total + turnover_hklhc;

              BEGIN
                   LOOP
                   FETCH cursor_his_hklhc INTO member_pos_hklhc;

                       -- 无数据则退出
                       IF (cursor_his_hklhc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_hklhc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_hklhc.WIN_STATE = 1 OR member_pos_hklhc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_hklhc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100;

                          IF (userType = 2) THEN
                            -- 总监
                            winBackWater := winBackWater + (member_pos_hklhc.MONEY * (0 - member_pos_hklhc.COMMISSION_MEMBER) / 100) * (1 - member_pos_hklhc.RATE_CHIEF / 100);
                          ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWater := winBackWater + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100) * (1 - member_pos_hklhc.RATE_AGENT / 100);
                          END IF;

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_hklhc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_hklhc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_hklhc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_hklhc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_hklhc.MONEY;
                          memberAmount_temp := - member_pos_hklhc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                       -- 累积上级的占成
                       --            * ((member_pos_hklhc.RATE_CHIEF + member_pos_hklhc.RATE_BRANCH + member_pos_hklhc.RATE_GEN_AGENT + member_pos_hklhc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_hklhc.WIN_STATE = 1 OR member_pos_hklhc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_BRANCH - member_pos_hklhc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_GEN_AGENT - member_pos_hklhc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_STOCKHOLDER - member_pos_hklhc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_BRANCH - member_pos_hklhc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_hklhc.RATE_STOCKHOLDER/100 - member_pos_hklhc.RATE_GEN_AGENT/100 -  member_pos_hklhc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_STOCKHOLDER - member_pos_hklhc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_hklhc.RATE_GEN_AGENT/100 -  member_pos_hklhc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_GEN_AGENT - member_pos_hklhc.COMMISSION_AGENT) / 100) * (1 - member_pos_hklhc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_AGENT - member_pos_hklhc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_hklhc.MONEY * (member_pos_hklhc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_BRANCH / 100)) * member_pos_hklhc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_STOCKHOLDER / 100)) * member_pos_hklhc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_AGENT / 100)) * member_pos_hklhc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_GEN_AGENT / 100)) * member_pos_hklhc.RATE_STOCKHOLDER/100;
                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100)) * member_pos_hklhc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_hklhc.MONEY * member_pos_hklhc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_BRANCH / 100)) * member_pos_hklhc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100 - member_pos_hklhc.RATE_GEN_AGENT/100 - member_pos_hklhc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_AGENT / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_hklhc.RATE_AGENT/100 - member_pos_hklhc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_hklhc.MONEY * member_pos_hklhc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       IF (userType = 2) THEN
                            -- 总监
                            winBackWaterResult := rateChief - winBackWater;
                       ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWaterResult := rateBranch - winBackWater;
                       END IF;

                       rateChiefSet := rateChiefSet + member_pos_hklhc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_hklhc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_hklhc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_hklhc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_hklhc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_hklhc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_hklhc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_hklhc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_hklhc.COMMISSION_AGENT;

                       IF (userType = 2) THEN
                          -- 总监
                          rate := rateChiefSet;
                       ELSIF(userType = 3) THEN
                          -- 分公司
                          rate := rateBranchSet;
                       ELSIF(userType = 4) THEN
                          -- 股东
                          rate := rateStockholderSet;
                       ELSIF(userType = 5) THEN
                          -- 总代理
                          rate := rateGenAgentSet;
                       END IF;

                   END LOOP;
                   CLOSE cursor_his_hklhc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接设置直属会员对应的上级实占结果值
              IF (userType = 2) THEN
                -- 总监
                realResult := rateChief;
              ELSIF(userType = 3) THEN
                -- 分公司
                realResult := rateBranch;
              ELSIF(userType = 4) THEN
                -- 股东
                realResult := rateStockholder;
              ELSIF(userType = 5) THEN
                -- 总代理
                realResult := rateGenAgent;
              END IF;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 香港六合彩 结束 ********/


              /******** 北京赛车 开始 ********/
              IF LOTTERY1688Type = 'ALL' OR LOTTERY1688Type = 'BJSC' THEN

              -- 3.3 从投注历史表中查询对应会员的投注信息数据
              -- 构造查询语句
              sql_str := 'SELECT COUNT(Distinct ORDER_NO) FROM TB_BJSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
              sql_bjsc := 'SELECT * FROM TB_BJSC_HIS WHERE BETTING_USER_ID = ' || subID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';

              IF playType IS NOT NULL THEN
                 sql_str := sql_str || ' AND TYPE_CODE IN (' || playType || ')';
                 sql_bjsc := sql_bjsc || ' AND TYPE_CODE IN (' || playType || ')';
              END IF;

              -- 执行查询，打开游标
              OPEN cursor_his_bjsc
              FOR
              sql_bjsc;

              -- 统计投注笔数
              execute immediate sql_str into turnover_bjsc;
              turnover_total := turnover_total + turnover_bjsc;

              BEGIN
                   LOOP
                   FETCH cursor_his_bjsc INTO member_pos_bjsc;
                       -- 无数据则退出
                       IF (cursor_his_bjsc%found = false) THEN
                           EXIT;
                       END IF;
                       -- 累加投注总额
                       amount := amount + member_pos_bjsc.MONEY;

                       -- 累加投注笔数（存在连码，故不能在此处累加投注笔数）
                       -- turnover := turnover + 1;

                       -- 累加记录数目
                       recNum := recNum + 1;

                       memberAmount_temp := 0;
                       memberBackWater_temp := 0;
                       -- 有效金额、赚取退水
                       IF (member_pos_bjsc.WIN_STATE = 1 OR member_pos_bjsc.WIN_STATE = 2) THEN
                          -- 统计状态为“中奖”、“未中奖”的投注额
                          -- 有效金额
                          validAmount := validAmount + member_pos_bjsc.MONEY;
                          -- 赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                          memberBackWater_temp := member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100;
                          -- 根据不同的管理用户类型，计算对应的佣金
                          IF (userType = 2) THEN
                            -- 总监
                            winBackWater := winBackWater + (member_pos_bjsc.MONEY * (1 - member_pos_bjsc.COMMISSION_MEMBER) / 100) * (1 - member_pos_bjsc.RATE_CHIEF / 100);
                          ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWater := winBackWater + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_BRANCH - member_pos_bjsc.COMMISSION_MEMBER) / 100) * (1 - member_pos_bjsc.RATE_BRANCH / 100);
                          ELSIF(userType = 4) THEN
                            -- 股东
                            winBackWater := winBackWater + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_STOCKHOLDER - member_pos_bjsc.COMMISSION_MEMBER) / 100) * (1 - member_pos_bjsc.RATE_STOCKHOLDER / 100);
                          ELSIF(userType = 5) THEN
                            -- 总代理
                            winBackWater := winBackWater + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_GEN_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100) * (1 - member_pos_bjsc.RATE_GEN_AGENT / 100);
                          END IF;

                          -- 会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                          memberBackWater := memberBackWater + memberBackWater_temp;
                       END IF;

                       -- 会员输赢，对应该会员所有输赢的总和(不计退水)
                       IF (member_pos_bjsc.WIN_STATE = 1) THEN
                          -- 累加“中奖”的投注额
                          memberAmount := memberAmount + member_pos_bjsc.WIN_AMOUNT;
                          memberAmount_temp := member_pos_bjsc.WIN_AMOUNT;
                       END IF;
                       IF (member_pos_bjsc.WIN_STATE = 2) THEN
                          -- 减去“未中奖”的投注额
                          memberAmount := memberAmount - member_pos_bjsc.MONEY;
                          memberAmount_temp := - member_pos_bjsc.MONEY;
                       END IF;

                       -- 应付上级（【指会员输赢+会员退水+赚取退水(实际就是水差)】*上级占的成数*（-1））
                       -- paySuperior := paySuperior + ((memberAmount_temp + memberBackWater_temp) * (-1)
                                   -- 累积上级的占成
                       --            * ((member_pos_bjsc.RATE_CHIEF + member_pos_bjsc.RATE_BRANCH + member_pos_bjsc.RATE_GEN_AGENT + member_pos_bjsc.RATE_STOCKHOLDER)/100));

                       -- 计算上线所对应的计算后的佣金、占成
                       -- 打和则不计算佣金（退水）
                       IF (member_pos_bjsc.WIN_STATE = 1 OR member_pos_bjsc.WIN_STATE = 2) THEN
                          commissionBranch := commissionBranch + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_BRANCH - member_pos_bjsc.COMMISSION_STOCKHOLDER)/ 100);
                          commissionGenAgent := commissionGenAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_GEN_AGENT - member_pos_bjsc.COMMISSION_AGENT) / 100);
                          commissionStockholder := commissionStockholder + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_STOCKHOLDER - member_pos_bjsc.COMMISSION_GEN_AGENT) / 100);
                          commissionAgent := commissionAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100);
                          commissionMember := commissionMember + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_MEMBER - 0) / 100);

                          -- 赚取佣金
                          winCommissionBranch := winCommissionBranch + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_BRANCH - member_pos_bjsc.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_bjsc.RATE_STOCKHOLDER/100 - member_pos_bjsc.RATE_GEN_AGENT/100 -  member_pos_bjsc.RATE_AGENT/100);
                          winCommissionStockholder := winCommissionStockholder + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_STOCKHOLDER - member_pos_bjsc.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_bjsc.RATE_GEN_AGENT/100 -  member_pos_bjsc.RATE_AGENT/100);
                          winCommissionGenAgent := winCommissionGenAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_GEN_AGENT - member_pos_bjsc.COMMISSION_AGENT) / 100) * (1 - member_pos_bjsc.RATE_AGENT/100);
                          winCommissionAgent := winCommissionAgent + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_AGENT - member_pos_bjsc.COMMISSION_MEMBER) / 100);
                          winCommissionMember := winCommissionMember + (member_pos_bjsc.MONEY * (member_pos_bjsc.COMMISSION_MEMBER - 0) / 100);

                          -- 实占结果（指会员输赢 + 会员退水）*占成%
                          rateChief := rateChief + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_BRANCH / 100)) * member_pos_bjsc.RATE_CHIEF/100;
                          rateBranch := rateBranch + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_STOCKHOLDER / 100)) * member_pos_bjsc.RATE_BRANCH/100;
                          rateGenAgent := rateGenAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_AGENT / 100)) * member_pos_bjsc.RATE_GEN_AGENT/100;
                          rateStockholder := rateStockholder + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_GEN_AGENT / 100)) * member_pos_bjsc.RATE_STOCKHOLDER/100;

                          rateAgent := rateAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100)) * member_pos_bjsc.RATE_AGENT/100;

                          -- 实占注额（有效的投注金额 * 占成%）
                          moneyRateChief := moneyRateChief + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_CHIEF / 100);
                          moneyRateBranch := moneyRateBranch + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_BRANCH / 100);
                          moneyRateStockholder := moneyRateStockholder + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_STOCKHOLDER / 100);
                          moneyRateGenAgent := moneyRateGenAgent + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_GEN_AGENT / 100);
                          moneyRateAgent := moneyRateAgent + (member_pos_bjsc.MONEY * member_pos_bjsc.RATE_AGENT / 100);

                          -- 各级应收下线
                          subordinateChief := subordinateChief + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_BRANCH / 100)) * member_pos_bjsc.RATE_CHIEF/100;
                          subordinateBranch := subordinateBranch + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100 - member_pos_bjsc.RATE_GEN_AGENT/100 - member_pos_bjsc.RATE_STOCKHOLDER/100);
                          subordinateGenAgent := subordinateGenAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_AGENT / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100);
                          subordinateStockholder := subordinateStockholder + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_GEN_AGENT / 100)) * (1 - member_pos_bjsc.RATE_AGENT/100 - member_pos_bjsc.RATE_GEN_AGENT/100);
                          subordinateAgent := subordinateAgent + (memberAmount_temp + (member_pos_bjsc.MONEY * member_pos_bjsc.COMMISSION_MEMBER / 100)) * 1;
                       END IF;
                       -- dbms_output.put_line('memberAmount_temp'||memberAmount_temp||' memberBackWater_temp'||memberBackWater_temp);
                       -- dbms_output.put_line('rateChief：'||rateChief||' rateBranch:'||rateBranch||' rateGenAgent:'||rateGenAgent||' rateStockholder'||rateStockholder||' rateAgent'||rateAgent);

                       -- 赚水后结果（实占结果（代理实占）－赚取退水）
                       IF (userType = 2) THEN
                            -- 总监
                            winBackWaterResult := rateChief - winBackWater;
                       ELSIF(userType = 3) THEN
                            -- 分公司
                            winBackWaterResult := rateBranch - winBackWater;
                       END IF;

                       rateChiefSet := rateChiefSet + member_pos_bjsc.RATE_CHIEF;
                       rateBranchSet := rateBranchSet + member_pos_bjsc.RATE_BRANCH;
                       rateStockholderSet := rateStockholderSet + member_pos_bjsc.RATE_STOCKHOLDER;
                       rateGenAgentSet := rateGenAgentSet + member_pos_bjsc.RATE_GEN_AGENT;
                       rateAgentSet := rateAgentSet + member_pos_bjsc.RATE_AGENT;

                       -- 退水设置值
                       commissionBranchSet := member_pos_bjsc.COMMISSION_BRANCH;
                       commissionStockholderSet := member_pos_bjsc.COMMISSION_STOCKHOLDER;
                       commissionGenAgentSet := member_pos_bjsc.COMMISSION_GEN_AGENT;
                       commissionAgentSet := member_pos_bjsc.COMMISSION_AGENT;

                       IF (userType = 2) THEN
                          -- 总监
                          rate := rateChiefSet;
                       ELSIF(userType = 3) THEN
                          -- 分公司
                          rate := rateBranchSet;
                       ELSIF(userType = 4) THEN
                          -- 股东
                          rate := rateStockholderSet;
                       ELSIF(userType = 5) THEN
                          -- 总代理
                          rate := rateGenAgentSet;
                       END IF;

                   END LOOP;
                   CLOSE cursor_his_bjsc;
              END;

              -- 应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
              subordinateAmount := memberAmount + memberBackWater;

              -- 实占结果，会员退水后结果*占成%，此处可以直接设置直属会员对应的上级实占结果值
              IF (userType = 2) THEN
                -- 总监
                realResult := rateChief;
              ELSIF(userType = 3) THEN
                -- 分公司
                realResult := rateBranch;
              ELSIF(userType = 4) THEN
                -- 股东
                realResult := rateStockholder;
              ELSIF(userType = 5) THEN
                -- 总代理
                realResult := rateGenAgent;
              END IF;

              -- 应付上级（应收下线－赚水后结果）
              paySuperior := subordinateAmount - winBackWaterResult;

              END IF;
              /******** 北京赛车 结束 ********/

              -- 计算占成设置值的平均值
              IF (recNum > 0) THEN
                 rateChiefSet := rateChiefSet / recNum;
                 rateBranchSet := rateBranchSet / recNum;
                 rateStockholderSet := rateStockholderSet / recNum;
                 rateGenAgentSet := rateGenAgentSet / recNum;
                 rateAgentSet := rateAgentSet / recNum;

                 IF (userType = 2) THEN
                    -- 总监
                    rate := rateChiefSet;
                 ELSIF(userType = 3) THEN
                    -- 分公司
                    rate := rateBranchSet;
                 ELSIF(userType = 4) THEN
                    -- 股东
                    rate := rateStockholderSet;
                 ELSIF(userType = 5) THEN
                    -- 总代理
                    rate := rateGenAgentSet;
                 END IF;
                 -- 累加有效投注会员数
                 recNum_total := recNum_total + 1;
                 -- 累加占着设置值的总值
                 --rate_total := rate_total + rate;
                 --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                 --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                 --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                 --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                 --rateAgentSet_total := rateAgentSet_total + rateAgentSet;
              END IF;

              -- 5. 累加总额
              amount_total := amount_total + amount;
              validAmount_total := validAmount_total + validAmount;
              memberAmount_total := memberAmount_total + memberAmount;
              memberBackWater_total := memberBackWater_total + memberBackWater;
              subordinateAmount_total := subordinateAmount_total + subordinateAmount;
              winBackWater_total := winBackWater_total + winBackWater;
              realResult_total := realResult_total + realResult;
              winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
              paySuperior_total := paySuperior_total + paySuperior;

              -- 6. 累积上线所对应的计算后的佣金、占成（总额）
              commissionBranch_total := commissionBranch_total + commissionBranch;
              commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
              commissionStockholder_total := commissionStockholder_total + commissionStockholder;
              commissionAgent_total := commissionAgent_total + commissionAgent;
              commissionMember_total := commissionMember_total + commissionMember;

              -- 赚取退水
              winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
              winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
              winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
              winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;
              winCommissionMember_total := winCommissionMember_total + winCommissionMember;

              rateChief_total := rateChief_total + rateChief;
              rateBranch_total := rateBranch_total + rateBranch;
              rateGenAgent_total := rateGenAgent_total + rateGenAgent;
              rateStockholder_total := rateStockholder_total + rateStockholder;
              rateAgent_total := rateAgent_total + rateAgent;

              -- 实占注额
              moneyRateChief_total := moneyRateChief_total + moneyRateChief;
              moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
              moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
              moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
              moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

              subordinateChief_total := subordinateChief_total + subordinateChief;
              subordinateBranch_total := subordinateBranch_total + subordinateBranch;
              subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
              subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
              subordinateAgent_total := subordinateAgent_total + subordinateAgent;

              -- 如果成交笔数大于0，则保存数据
              IF ((turnover_gdklsf + turnover_cqssc + turnover_hklhc + turnover_bjsc) > 0) THEN

                 -- 7. 数据插入临时表
                 INSERT INTO TEMP_DELIVERYREPORT
                        (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                        COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT, RATE, RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
                        COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                        MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                 VALUES
                        (userID, subID, '9', '0', subordinate, userName, (turnover_gdklsf + turnover_cqssc + turnover_hklhc + turnover_bjsc), amount, validAmount, memberAmount, memberBackWater, subordinateAmount, winBackWater, realResult, winBackWaterResult, paySuperior,
                        commissionBranch, commissionGenAgent, commissionStockholder, commissionAgent, commissionMember, winCommissionBranch, winCommissionGenAgent, winCommissionStockholder, winCommissionAgent, winCommissionMember, rateChief, rateBranch, rateGenAgent, rateStockholder, rateAgent, subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent, rate, rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
                        commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                        moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent);
              END IF;

          END LOOP;
      END;

      -- 6.1 数据不存在（成交笔数为0）则返回
      IF (turnover_total < 1) THEN
         resultFlag := 2; -- 设置结果
         return;
      END IF;
      -- dbms_output.put_line('rateAgent_total：'||rateAgent_total);

      -- 计算占成设置值的平均值
      --IF (recNum_total > 0) THEN
         --rate_total := rate_total / recNum_total;
         --rateChiefSet_total := rateChiefSet_total / recNum_total;
         --rateBranchSet_total := rateBranchSet_total / recNum_total;
         --rateStockholderSet_total := rateStockholderSet_total / recNum_total;
         --rateGenAgentSet_total := rateGenAgentSet_total / recNum_total;
         --rateAgentSet_total := rateAgentSet_total / recNum_total;
      --END IF;

      -- 6.3 总额数据插入临时表（USER_TYPE设置为 Z）
      IF (turnover_total > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT, RATE, RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
            COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
        VALUES
            (userID, userID, 'Z', '0', '', '直属会员合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total, rate, rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
            commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;

      -- 7. 从临时表中查询数据
      --OPEN statReportAgent
      --FOR
      --SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 8. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/

prompt
prompt Creating procedure DELIVERY_REPORT_GEN_AGENT
prompt ============================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Gen_Agent(
/*==============================================================*/
/*                  总代理交收报表存储过程                      */
/*==============================================================*/
     userID IN varchar2,                -- 代理ID
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2,           -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
     statReportAgent OUT statReportResult.resultRef         -- 返回结果集
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover NUMBER;                   -- 成交笔数
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     sqlStr varchar2(10000);
     type   mycursor   is   ref   cursor;
     gdklsf_his_cursor  mycursor;
     member_pos TB_GDKLSF_HIS%rowtype;

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     -- 赚取佣金
     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）

     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值

     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     -- 赚取佣金
     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     -- 占成设置值只取最后一个值，故总和值无效
     recNum_total NUMBER;                     -- 临时变量，有效会员数
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）
BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     -- 初始化占成设置值相关数据
     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 1.3 删除临时表中的数据
     DELETE FROM TEMP_DELIVERYREPORT WHERE PARENT_ID = userID;

     -- 2.1 查询总代理对应的代理信息
     declare
     cursor agent_cursor
     IS
     SELECT * FROM
         (TB_AGENT_STAFF_EXT ext INNER JOIN TB_FRAME_MANAGER_STAFF managerStaff ON ext.MANAGER_STAFF_ID = managerStaff.ID)
     WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.1.1 循环处理所有的代理信息
          FOR agent_pos IN agent_cursor LOOP
              -- 2.1.2 填充数据记录
              subordinate := agent_pos.ACCOUNT;             -- 下级登陆账号
              userName := agent_pos.CHS_NAME;               -- 用户名称
              subID := agent_pos.ID;                        -- 记录ID
              -- 2.1.3 初始化数据
              turnover := 0;                  -- 成交笔数
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              rate := 0;                      -- 占成
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              -- 赚取退水
              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;

              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              -- 2.1.4 调用代理交收报表存储过程
              Delivery_Report_Agent(subID, LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag, statReportAgent);
              -- 读取代理交收报表存储过程所形成的数据
              declare
              cursor report_Agent_cursor
              IS
              -- 过滤掉代理对应的补货信息
              -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
              SELECT * FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = agent_pos.ID AND t.USER_TYPE NOT IN ('g');

              BEGIN
              FOR report_Agent_pos IN report_Agent_cursor LOOP

                  -- 会员退水
                  memberBackWater := report_Agent_pos.COMMISSION_AGENT + report_Agent_pos.COMMISSION_MEMBER;
                  -- 应收下线（直接读取下线所计算的本级及上线占成结果，此处不能计算，因为不同的投注占成值不同）
                  -- subordinateAmount := report_Agent_pos.RATE_CHIEF + report_Agent_pos.RATE_BRANCH + report_Agent_pos.RATE_STOCKHOLDER + report_Agent_pos.RATE_GEN_AGENT;
                  subordinateAmount := report_Agent_pos.SUBORDINATE_GEN_AGENT;
                  -- 实占结果（直接读取下线所计算的代理占成结果，此处不能计算，因为不同的投注占成值不同）
                  realResult := report_Agent_pos.RATE_GEN_AGENT;
                  -- 赚取退水（需要确认，可能需要减去下面的退水值）
                  winBackWater := report_Agent_pos.WIN_COMMISSION_GEN_AGENT;
                  -- 赚水后结果（实占结果-赚取退水）
                  winBackWaterResult := winBackWaterResult + realResult - winBackWater;
                  -- 应付上线（应收下线－赚水后结果）
                  paySuperior := subordinateAmount - winBackWaterResult;

                  -- 实占注额
                  moneyRateChief := report_Agent_pos.MONEY_RATE_CHIEF;
                  moneyRateBranch := report_Agent_pos.MONEY_RATE_BRANCH;
                  moneyRateGenAgent := report_Agent_pos.MONEY_RATE_GEN_AGENT;
                  moneyRateStockholder := report_Agent_pos.MONEY_RATE_STOCKHOLDER;
                  moneyRateAgent := report_Agent_pos.MONEY_RATE_AGENT;

                  subordinateChief := report_Agent_pos.SUBORDINATE_CHIEF;
                  subordinateBranch := report_Agent_pos.SUBORDINATE_BRANCH;
                  subordinateStockholder := report_Agent_pos.SUBORDINATE_STOCKHOLDER;
                  subordinateGenAgent := report_Agent_pos.SUBORDINATE_GEN_AGENT;
                  subordinateAgent := report_Agent_pos.SUBORDINATE_AGENT;

                  -- 占成设置值
                  rateChiefSet := report_Agent_pos.RATE_CHIEF_SET;
                  rateBranchSet := report_Agent_pos.RATE_BRANCH_SET;
                  rateStockholderSet := report_Agent_pos.RATE_STOCKHOLDER_SET;
                  rateGenAgentSet := report_Agent_pos.RATE_GEN_AGENT_SET;
                  rateAgentSet := report_Agent_pos.RATE_AGENT_SET;

                  -- 退水设置值
                  commissionBranchSet := report_Agent_pos.COMMISSION_BRANCH_SET;
                  commissionStockholderSet := report_Agent_pos.COMMISSION_STOCKHOLDER_SET;
                  commissionGenAgentSet := report_Agent_pos.COMMISSION_GEN_AGENT_SET;
                  commissionAgentSet := report_Agent_pos.COMMISSION_AGENT_SET;

                  rate := report_Agent_pos.RATE_GEN_AGENT_SET;
                  -- 赋值，TODO 考虑如果补货数据在投注数据之前出现是否会有问题
                  turnover := report_Agent_pos.TURNOVER;          -- 成交笔数
                  amount := report_Agent_pos.AMOUNT;              -- 投注总额
                  validAmount := report_Agent_pos.VALID_AMOUNT;   -- 有效金额
                  memberAmount := report_Agent_pos.MEMBER_AMOUNT; -- 会员输赢

                  /*
                  -- 合并相同用户的补货数据和投注数据
                  declare
                  cursor report_Agent_Inner_cursor
                  IS
                  SELECT * FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = agent_pos.ID AND t.PARENT_ID = userID;
                  BEGIN
                       FOR report_Agent_Inner_pos IN report_Agent_Inner_cursor LOOP
                           turnover := turnover + report_Agent_Inner_pos.TURNOVER;
                           amount := amount + report_Agent_Inner_pos.AMOUNT;
                           validAmount := validAmount + report_Agent_Inner_pos.VALID_AMOUNT;
                           memberAmount := memberAmount + report_Agent_Inner_pos.MEMBER_AMOUNT;
                       END LOOP;
                  END;

                  -- 删除之前存入的数据（相同用户的补货数据和投注数据合成一条记录）
                  EXECUTE IMMEDIATE 'DELETE FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = ' || agent_pos.ID || 'AND t.PARENT_ID = ' || userID;
                  */

                  -- 2.1.4.1 数据插入临时表
                  INSERT INTO TEMP_DELIVERYREPORT
                      (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                      COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT, RATE, RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
                      COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                      MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                  VALUES (
                      userID,
                      agent_pos.ID,
                      '6',
                      agent_pos.ACCOUNT,
                      agent_pos.CHS_NAME,
                      turnover,
                      amount,
                      validAmount,
                      memberAmount,
                      memberBackWater,
                      subordinateAmount,
                      winBackWater,
                      realResult,
                      winBackWaterResult,
                      paySuperior,
                      report_Agent_pos.COMMISSION_BRANCH, report_Agent_pos.COMMISSION_GEN_AGENT, report_Agent_pos.COMMISSION_STOCKHOLDER, report_Agent_pos.COMMISSION_AGENT, report_Agent_pos.COMMISSION_MEMBER, report_Agent_pos.WIN_COMMISSION_BRANCH, report_Agent_pos.WIN_COMMISSION_GEN_AGENT, report_Agent_pos.WIN_COMMISSION_STOCKHOLDER, report_Agent_pos.WIN_COMMISSION_AGENT, report_Agent_pos.WIN_COMMISSION_MEMBER, report_Agent_pos.RATE_CHIEF, report_Agent_pos.RATE_BRANCH, report_Agent_pos.RATE_GEN_AGENT, report_Agent_pos.RATE_STOCKHOLDER, report_Agent_pos.RATE_AGENT,
                      subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent,
                      rate, rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
                      commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                      moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent
                      );

                   -- 2.1.4.2 累加总额
                   turnover_total := turnover_total + report_Agent_pos.TURNOVER;
                   amount_total := amount_total + report_Agent_pos.AMOUNT;
                   validAmount_total := validAmount_total + report_Agent_pos.VALID_AMOUNT;
                   memberAmount_total := memberAmount_total + report_Agent_pos.MEMBER_AMOUNT;
                   memberBackWater_total := memberBackWater_total + memberBackWater;
                   subordinateAmount_total := subordinateAmount_total + subordinateAmount;
                   winBackWater_total := winBackWater_total + winBackWater;
                   realResult_total := realResult_total + realResult;
                   winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
                   paySuperior_total := paySuperior_total + paySuperior;

                   -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
                   commissionBranch_total := commissionBranch_total + report_Agent_pos.COMMISSION_BRANCH;
                   commissionGenAgent_total := commissionGenAgent_total + report_Agent_pos.COMMISSION_GEN_AGENT;
                   commissionStockholder_total := commissionStockholder_total + report_Agent_pos.COMMISSION_STOCKHOLDER;
                   commissionAgent_total := commissionAgent_total + report_Agent_pos.COMMISSION_AGENT;
                   commissionMember_total := commissionMember_total + report_Agent_pos.COMMISSION_MEMBER;

                   winCommissionBranch_total := winCommissionBranch_total + report_Agent_pos.WIN_COMMISSION_BRANCH;
                   winCommissionGenAgent_total := winCommissionGenAgent_total + report_Agent_pos.WIN_COMMISSION_GEN_AGENT;
                   winCommissionStockholder_total := winCommissionStockholder_total + report_Agent_pos.WIN_COMMISSION_STOCKHOLDER;
                   winCommissionAgent_total := winCommissionAgent_total + report_Agent_pos.WIN_COMMISSION_AGENT;
                   winCommissionMember_total := winCommissionMember_total + report_Agent_pos.WIN_COMMISSION_MEMBER;

                   rateChief_total := rateChief_total + report_Agent_pos.RATE_CHIEF;
                   rateBranch_total := rateBranch_total + report_Agent_pos.RATE_BRANCH;
                   rateGenAgent_total := rateGenAgent_total + report_Agent_pos.RATE_GEN_AGENT;
                   rateStockholder_total := rateStockholder_total + report_Agent_pos.RATE_STOCKHOLDER;
                   rateAgent_total := rateAgent_total + report_Agent_pos.RATE_AGENT;

                   moneyRateChief_total := moneyRateChief_total + report_Agent_pos.MONEY_RATE_CHIEF;
                   moneyRateBranch_total := moneyRateBranch_total + report_Agent_pos.MONEY_RATE_BRANCH;
                   moneyRateGenAgent_total := moneyRateGenAgent_total + report_Agent_pos.MONEY_RATE_GEN_AGENT;
                   moneyRateStockholder_total := moneyRateStockholder_total + report_Agent_pos.MONEY_RATE_STOCKHOLDER;
                   moneyRateAgent_total := moneyRateAgent_total + report_Agent_pos.MONEY_RATE_AGENT;

                   -- 实占注额
                   moneyRateChief_total := moneyRateChief_total + moneyRateChief;
                   moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
                   moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
                   moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
                   moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

                   subordinateChief_total := subordinateChief_total + subordinateChief;
                   subordinateBranch_total := subordinateBranch_total + subordinateBranch;
                   subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
                   subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
                   subordinateAgent_total := subordinateAgent_total + subordinateAgent;

                   -- 占成设置值
                   recNum_total := recNum_total + 1;
                   --rate_total := rate_total + rate;
                   --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                   --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                   --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                   --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                   --rateAgentSet_total := rateAgentSet_total + rateAgentSet;

                   -- 2.1.4.3 删除无效临时数据
                   DELETE FROM TEMP_DELIVERYREPORT t WHERE t.PARENT_ID = agent_pos.ID;
              END LOOP;
              END;
          END LOOP;
      END;

      /******** 总代理对应的直属会员数据 开始 ********/
      -- 调用直属会员交收报表存储过程
      Delivery_Report_Dir_Member(userID, '5', LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag);
      -- 查询直属会员统计数据
      declare
         cursor dir_total_cursor
         IS
         SELECT * FROM
             TEMP_DELIVERYREPORT report
         WHERE
             report.USER_ID = userID AND report.USER_TYPE = 'Z';
      BEGIN
          -- 累加直属会员统计数据
          FOR dir_total_pos IN dir_total_cursor LOOP
               -- 2.1.4.2 累加总额
               turnover_total := turnover_total + dir_total_pos.TURNOVER;
               amount_total := amount_total + dir_total_pos.AMOUNT;
               validAmount_total := validAmount_total + dir_total_pos.VALID_AMOUNT;
               memberAmount_total := memberAmount_total + dir_total_pos.MEMBER_AMOUNT;
               memberBackWater_total := memberBackWater_total + dir_total_pos.MEMBER_BACK_WATER;
               subordinateAmount_total := subordinateAmount_total + dir_total_pos.SUBORDINATE_AMOUNT;
               winBackWater_total := winBackWater_total + dir_total_pos.WIN_BACK_WATER;
               realResult_total := realResult_total + dir_total_pos.REAL_RESULT;
               winBackWaterResult_total := winBackWaterResult_total + dir_total_pos.WIN_BACK_WATER_RESULT;
               paySuperior_total := paySuperior_total + dir_total_pos.PAY_SUPERIOR;

               -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
               commissionBranch_total := commissionBranch_total + dir_total_pos.COMMISSION_BRANCH;
               commissionGenAgent_total := commissionGenAgent_total + dir_total_pos.COMMISSION_GEN_AGENT;
               commissionStockholder_total := commissionStockholder_total + dir_total_pos.COMMISSION_STOCKHOLDER;
               commissionAgent_total := commissionAgent_total + dir_total_pos.COMMISSION_AGENT;
               commissionMember_total := commissionMember_total + dir_total_pos.COMMISSION_MEMBER;

               winCommissionBranch_total := winCommissionBranch_total + dir_total_pos.WIN_COMMISSION_BRANCH;
               winCommissionGenAgent_total := winCommissionGenAgent_total + dir_total_pos.WIN_COMMISSION_GEN_AGENT;
               winCommissionStockholder_total := winCommissionStockholder_total + dir_total_pos.WIN_COMMISSION_STOCKHOLDER;
               winCommissionAgent_total := winCommissionAgent_total + dir_total_pos.WIN_COMMISSION_AGENT;
               winCommissionMember_total := winCommissionMember_total + dir_total_pos.WIN_COMMISSION_MEMBER;

               rateChief_total := rateChief_total + dir_total_pos.RATE_CHIEF;
               rateBranch_total := rateBranch_total + dir_total_pos.RATE_BRANCH;
               rateGenAgent_total := rateGenAgent_total + dir_total_pos.RATE_GEN_AGENT;
               rateStockholder_total := rateStockholder_total + dir_total_pos.RATE_STOCKHOLDER;
               rateAgent_total := rateAgent_total + dir_total_pos.RATE_AGENT;

               moneyRateChief_total := moneyRateChief_total + dir_total_pos.MONEY_RATE_CHIEF;
               moneyRateBranch_total := moneyRateBranch_total + dir_total_pos.MONEY_RATE_BRANCH;
               moneyRateGenAgent_total := moneyRateGenAgent_total + dir_total_pos.MONEY_RATE_GEN_AGENT;
               moneyRateStockholder_total := moneyRateStockholder_total + dir_total_pos.MONEY_RATE_STOCKHOLDER;
               moneyRateAgent_total := moneyRateAgent_total + dir_total_pos.MONEY_RATE_AGENT;

               -- 实占注额
               moneyRateChief_total := moneyRateChief_total + dir_total_pos.MONEY_RATE_CHIEF;
               moneyRateBranch_total := moneyRateBranch_total + dir_total_pos.MONEY_RATE_BRANCH;
               moneyRateGenAgent_total := moneyRateGenAgent_total + dir_total_pos.MONEY_RATE_GEN_AGENT;
               moneyRateStockholder_total := moneyRateStockholder_total + dir_total_pos.MONEY_RATE_STOCKHOLDER;
               moneyRateAgent_total := moneyRateAgent_total + dir_total_pos.MONEY_RATE_AGENT;

               subordinateChief_total := subordinateChief_total + dir_total_pos.SUBORDINATE_CHIEF;
               subordinateBranch_total := subordinateBranch_total + dir_total_pos.SUBORDINATE_BRANCH;
               subordinateStockholder_total := subordinateStockholder_total + dir_total_pos.SUBORDINATE_STOCKHOLDER;
               subordinateGenAgent_total := subordinateGenAgent_total + dir_total_pos.SUBORDINATE_GEN_AGENT;
               subordinateAgent_total := subordinateAgent_total + dir_total_pos.SUBORDINATE_AGENT;

               -- 占成设置值
               recNum_total := recNum_total + 1;
               --rate_total := rate_total + dir_total_pos.RATE;
               --rateChiefSet_total := rateChiefSet_total + dir_total_pos.RATE_CHIEF_SET;
               --rateBranchSet_total := rateBranchSet_total + dir_total_pos.RATE_BRANCH_SET;
               --rateStockholderSet_total := rateStockholderSet_total + dir_total_pos.RATE_STOCKHOLDER_SET;
               --rateGenAgentSet_total := rateGenAgentSet_total + dir_total_pos.RATE_GEN_AGENT_SET;
               --rateAgentSet_total := rateAgentSet_total + dir_total_pos.RATE_AGENT_SET;

               -- 2.1.4.3 删除无效临时数据
               DELETE FROM TEMP_DELIVERYREPORT report WHERE report.USER_ID = userID AND report.USER_TYPE = 'Z';
          END LOOP;
      END;
      /******** 总代理对应的直属会员数据 结束 ********/

      /******** 补货数据 开始 ********/
      -- 6.2 查询补货数据（补货后续需要区分玩法类型查询）
      sql_replenish := 'SELECT * FROM TB_REPLENISH WHERE REPLENISH_USER_ID = ' || userID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
      -- 只查询结算了的补货数据
      sql_replenish := sql_replenish || ' AND WIN_STATE IN (''1'',''2'',''3'') ';

      IF playType IS NOT NULL THEN
         sql_replenish := sql_replenish || ' AND TYPE_CODE IN (' || playType || ')';
      END IF;

      -- 判断彩票种类
      IF LOTTERY1688Type = 'GDKLSF' THEN
         -- 广东
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''GDKLSF_%'' ';
      END IF;
      IF LOTTERY1688Type = 'HKLHC' THEN
         -- 香港
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''HK_%'' ';
      END IF;
      IF LOTTERY1688Type = 'CQSSC' THEN
         -- 重庆
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''CQSSC_%'' ';
      END IF;

      -- 初始化
      turnover_replenish := 0;                         -- 补货笔数
      amount_replenish := 0;                           -- 补货投注总额
      validAmount_replenish :=0;                       -- 补货有效金额
      amount_win_replenish := 0;                       -- 补货输赢
      backWater_replenish := 0;                        -- 补货退水
      backWaterResult_replenish := 0;                  -- 退水后结果
      backWater_replenish_temp := 0;                   -- 补货输赢（临时）
      amount_win_replenish_temp := 0;                  -- 补货输赢（临时）

      -- 退水相关数据
      commissionBranch := 0;
      commissionStockholder := 0;
      commissionGenAgent := 0;
      commissionAgent := 0;

      winCommissionBranch := 0;
      winCommissionGenAgent := 0;
      winCommissionStockholder := 0;
      winCommissionAgent := 0;

      -- 实占结果
      rateChief := 0;
      rateBranch := 0;
      rateGenAgent := 0;
      rateStockholder := 0;
      rateAgent := 0;

      -- 实占注额
      moneyRateChief := 0;
      moneyRateBranch := 0;
      moneyRateGenAgent := 0;
      moneyRateStockholder := 0;
      moneyRateAgent := 0;

      -- 应收下线
      subordinateChief := 0;
      subordinateBranch := 0;
      subordinateGenAgent := 0;
      subordinateStockholder := 0;
      subordinateAgent := 0;

      -- 执行查询，打开游标
      OPEN cursor_replenish
      FOR
      sql_replenish;

      BEGIN
           LOOP
           FETCH cursor_replenish INTO member_pos_replenish;
               -- 无数据则退出
               IF (cursor_replenish%found = false) THEN
                   EXIT;
               END IF;

               -- 累加补货总额
               amount_replenish := amount_replenish + member_pos_replenish.MONEY;

               -- 累加补货笔数
               turnover_replenish := turnover_replenish + 1;

               -- 补货输赢，对应该代理所有补货的总和(不计退水)
               IF (member_pos_replenish.WIN_STATE = 1) THEN
                  -- 累加“中奖”的投注额
                  amount_win_replenish := amount_win_replenish + member_pos_replenish.WIN_AMOUNT;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100;
                  amount_win_replenish_temp := member_pos_replenish.WIN_AMOUNT;
               END IF;
               IF (member_pos_replenish.WIN_STATE = 2) THEN
                  -- 减去“未中奖”的投注额
                  amount_win_replenish := amount_win_replenish - member_pos_replenish.MONEY;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100;
                  amount_win_replenish_temp := - member_pos_replenish.MONEY;
               END IF;

               -- 补货有效金额，不计算打和
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  validAmount_replenish := validAmount_replenish + member_pos_replenish.MONEY;
               END IF;
               -- TODO 退水需要补全所有级别的退水信息
               -- 补货退水（代理佣金*投注额，除了打和，其他的都要退水）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  backWater_replenish := backWater_replenish + backWater_replenish_temp;
                  --rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;
               END IF;
               -- 打和则不计算佣金（退水）（当做直属会员处理）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  commissionBranch := commissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER) / 100);
                  commissionStockholder := commissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - member_pos_replenish.COMMISSION_GEN_AGENT) / 100);
                  commissionGenAgent := commissionGenAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_GEN_AGENT - 0) / 100);

                  winCommissionBranch := winCommissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_replenish.RATE_STOCKHOLDER/100 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);
                  winCommissionStockholder := winCommissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - member_pos_replenish.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);
                  winCommissionGenAgent := winCommissionGenAgent + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_GEN_AGENT - member_pos_replenish.COMMISSION_AGENT) / 100) * (1 - member_pos_replenish.RATE_AGENT/100);

                  -- 实占结果（退水后结果，也即是 退水 + 输赢）*占成%
                  rateChief := rateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  rateBranch := rateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * member_pos_replenish.RATE_BRANCH/100;
                  rateStockholder := rateStockholder + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100)) * member_pos_replenish.RATE_STOCKHOLDER/100;

                  -- 实占注额（有效的投注金额 * 占成%）
                  moneyRateChief := moneyRateChief + (member_pos_replenish.MONEY * member_pos_replenish.RATE_CHIEF / 100);
                  moneyRateBranch := moneyRateBranch + (member_pos_replenish.MONEY * member_pos_replenish.RATE_BRANCH / 100);
                  moneyRateStockholder := moneyRateStockholder + (member_pos_replenish.MONEY * member_pos_replenish.RATE_STOCKHOLDER / 100);
                  moneyRateGenAgent := moneyRateGenAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_GEN_AGENT / 100);
                  moneyRateAgent := moneyRateAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_AGENT / 100);

                  -- 各级应收下线
                  subordinateChief := subordinateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  subordinateBranch := subordinateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * (1 - member_pos_replenish.RATE_STOCKHOLDER/100);
                  subordinateStockholder := subordinateStockholder + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_GEN_AGENT / 100)) * (1);
               END IF;

           END LOOP;
           CLOSE cursor_replenish;
      END;

      -- 补货退水后结果（退水 + 输赢）
      backWaterResult_replenish := backWater_replenish + amount_win_replenish;

      -- 4. 补货数据插入临时表（处理数据类型）（数据对应的 USER_TYPE 为实际值 + a，即如果实际值为1，则填充值 b，实际值为0，则填充a）
      -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
      IF (turnover_replenish > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
              (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, WIN_BACK_WATER_RESULT)
        VALUES
              (userID, userID, 'f', '1', '补货', '补货', turnover_replenish, amount_replenish, validAmount_replenish, amount_win_replenish, backWater_replenish, backWaterResult_replenish);
      END IF;

      -- 5. 累加总额（增加补货数据）
      turnover_total := turnover_total + turnover_replenish;
      amount_total := amount_total + amount_replenish;
      validAmount_total := validAmount_total + validAmount_replenish;
      memberAmount_total := memberAmount_total + amount_win_replenish;
      memberBackWater_total := memberBackWater_total + backWater_replenish;
      winBackWaterResult_total := winBackWaterResult_total + backWaterResult_replenish;
      -- commissionAgent_total := commissionAgent_total + backWater_replenish; -- 增加代理补货退水

      -- 6. 累积补货上线所对应的计算后的佣金、占成（总额）
      commissionBranch_total := commissionBranch_total + commissionBranch;
      commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
      commissionStockholder_total := commissionStockholder_total + commissionStockholder;
      commissionAgent_total := commissionAgent_total + commissionAgent;

      winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
      winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
      winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
      winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;

      rateChief_total := rateChief_total + rateChief;
      rateBranch_total := rateBranch_total + rateBranch;
      rateGenAgent_total := rateGenAgent_total + rateGenAgent;
      rateStockholder_total := rateStockholder_total + rateStockholder;
      rateAgent_total := rateAgent_total + rateAgent;

      -- 实占注额
      moneyRateChief_total := moneyRateChief_total + moneyRateChief;
      moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
      moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
      moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
      moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

      subordinateChief_total := subordinateChief_total + subordinateChief;
      subordinateBranch_total := subordinateBranch_total + subordinateBranch;
      subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
      subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
      subordinateAgent_total := subordinateAgent_total + subordinateAgent;
      /******** 补货数据 结束 ********/

      -- 4. 总额数据插入临时表（为方便排序，总额数据对应的 USER_TYPE 为实际值 + A，即如果实际值为1，则填充值 B）
      -- C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
      IF (turnover_total > 0) THEN
         INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
            SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
            RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
            COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
         VALUES
            (userID, userID, 'F', '', '合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, rate,
            subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total,
            rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
            commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;
      -- 5. 从临时表中查询数据
      OPEN statReportAgent
      FOR
      SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 6. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/

prompt
prompt Creating procedure DELIVERY_REPORT_STOCKHOLDER
prompt ==============================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Stockholder(
/*==============================================================*/
/*                    股东交收报表存储过程                      */
/*==============================================================*/
     userID IN varchar2,                -- 代理ID
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2,           -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
     statReportGenAgent OUT statReportResult.resultRef         -- 返回结果集
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover NUMBER;                   -- 成交笔数
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     sqlStr varchar2(10000);
     type   mycursor   is   ref   cursor;
     his_cursor  mycursor;
     member_pos TB_HKLHC_HIS%rowtype;

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     -- 赚取佣金
     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）

     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值

     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     -- 赚取佣金
     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     -- 占成设置值只取最后一个值，故总和值无效
     recNum_total NUMBER;                     -- 临时变量，有效会员数
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）
BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     -- 初始化占成设置值相关数据
     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 1.3 删除临时表中的数据
     DELETE FROM TEMP_DELIVERYREPORT WHERE PARENT_ID = userID;

     -- 2.1 查询股东对应的总代理信息
     declare
     cursor gen_agent_cursor
     IS
     SELECT * FROM
         (TB_GEN_AGENT_STAFF_EXT ext INNER JOIN TB_FRAME_MANAGER_STAFF managerStaff ON ext.MANAGER_STAFF_ID = managerStaff.ID)
     WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.1.1 循环处理所有的总代理信息
          FOR gen_agent_pos IN gen_agent_cursor LOOP
              -- 2.1.2 填充数据记录
              subordinate := gen_agent_pos.ACCOUNT;         -- 下级登陆账号
              userName := gen_agent_pos.CHS_NAME;           -- 用户名称
              subID := gen_agent_pos.ID;                    -- 记录ID
              -- 2.1.3 初始化数据
              turnover := 0;                  -- 成交笔数
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              rate := 0;                      -- 占成
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              -- 赚取退水
              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;

              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              -- 2.1.4 调用总代理交收报表存储过程
              Delivery_Report_Gen_Agent(subID, LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag, statReportGenAgent);
              -- 读取代理交收报表存储过程所形成的数据
              declare
              cursor report_gen_agent_cursor
              IS
              -- 过滤掉代理对应的补货信息
              -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
              SELECT * FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = gen_agent_pos.ID AND t.USER_TYPE NOT IN ('g','f');

              BEGIN
              FOR report_gen_agent_pos IN report_gen_agent_cursor LOOP

                  -- 会员退水
                  memberBackWater := report_gen_agent_pos.COMMISSION_GEN_AGENT + report_gen_agent_pos.COMMISSION_AGENT + report_gen_agent_pos.COMMISSION_MEMBER;
                  -- 应收下线（直接读取下线所计算的本级及上线占成结果，此处不能计算，因为不同的投注占成值不同）
                  -- subordinateAmount := report_gen_agent_pos.RATE_CHIEF + report_gen_agent_pos.RATE_BRANCH + report_gen_agent_pos.RATE_STOCKHOLDER + report_gen_agent_pos.RATE_GEN_AGENT;
                  subordinateAmount := report_gen_agent_pos.SUBORDINATE_STOCKHOLDER;
                  -- 实占结果（直接读取下线所计算的股东占成结果，此处不能计算，因为不同的投注占成值不同）
                  realResult := report_gen_agent_pos.RATE_STOCKHOLDER;
                  -- 赚取退水
                  winBackWater := report_gen_agent_pos.WIN_COMMISSION_STOCKHOLDER;
                  -- 赚水后结果（实占结果-赚取退水）
                  winBackWaterResult := winBackWaterResult + realResult - winBackWater;
                  -- 应付上线（应收下线－赚水后结果）
                  paySuperior := subordinateAmount - winBackWaterResult;

                  moneyRateChief_total := moneyRateChief_total + report_gen_agent_pos.MONEY_RATE_CHIEF;
                  moneyRateBranch_total := moneyRateBranch_total + report_gen_agent_pos.MONEY_RATE_BRANCH;
                  moneyRateGenAgent_total := moneyRateGenAgent_total + report_gen_agent_pos.MONEY_RATE_GEN_AGENT;
                  moneyRateStockholder_total := moneyRateStockholder_total + report_gen_agent_pos.MONEY_RATE_STOCKHOLDER;
                  moneyRateAgent_total := moneyRateAgent_total + report_gen_agent_pos.MONEY_RATE_AGENT;

                  subordinateChief := report_gen_agent_pos.SUBORDINATE_CHIEF;
                  subordinateBranch := report_gen_agent_pos.SUBORDINATE_BRANCH;
                  subordinateStockholder := report_gen_agent_pos.SUBORDINATE_STOCKHOLDER;
                  subordinateGenAgent := report_gen_agent_pos.SUBORDINATE_GEN_AGENT;
                  subordinateAgent := report_gen_agent_pos.SUBORDINATE_AGENT;

                  -- 占成设置值
                  rateChiefSet := report_gen_agent_pos.RATE_CHIEF_SET;
                  rateBranchSet := report_gen_agent_pos.RATE_BRANCH_SET;
                  rateStockholderSet := report_gen_agent_pos.RATE_STOCKHOLDER_SET;
                  rateGenAgentSet := report_gen_agent_pos.RATE_GEN_AGENT_SET;
                  rateAgentSet := report_gen_agent_pos.RATE_AGENT_SET;

                  -- 退水设置值
                  commissionBranchSet := report_gen_agent_pos.COMMISSION_BRANCH_SET;
                  commissionStockholderSet := report_gen_agent_pos.COMMISSION_STOCKHOLDER_SET;
                  commissionGenAgentSet := report_gen_agent_pos.COMMISSION_GEN_AGENT_SET;
                  commissionAgentSet := report_gen_agent_pos.COMMISSION_AGENT_SET;

                  rate := report_gen_agent_pos.RATE_STOCKHOLDER_SET;
                  -- 赋值，TODO 考虑如果补货数据在投注数据之前出现是否会有问题
                  turnover := report_gen_agent_pos.TURNOVER;          -- 成交笔数
                  amount := report_gen_agent_pos.AMOUNT;              -- 投注总额
                  validAmount := report_gen_agent_pos.VALID_AMOUNT;   -- 有效金额
                  memberAmount := report_gen_agent_pos.MEMBER_AMOUNT; -- 会员输赢

                  -- 2.1.4.1 数据插入临时表
                  INSERT INTO TEMP_DELIVERYREPORT
                      (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                      COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
                      SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
                      RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
                      COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                      MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                  VALUES (
                      userID,
                      gen_agent_pos.ID,
                      '5',
                      gen_agent_pos.ACCOUNT,
                      gen_agent_pos.CHS_NAME,
                      turnover,
                      amount,
                      validAmount,
                      memberAmount,
                      memberBackWater,
                      subordinateAmount,
                      winBackWater,
                      realResult,
                      winBackWaterResult,
                      paySuperior,
                      report_gen_agent_pos.COMMISSION_BRANCH, report_gen_agent_pos.COMMISSION_GEN_AGENT, report_gen_agent_pos.COMMISSION_STOCKHOLDER, report_gen_agent_pos.COMMISSION_AGENT, report_gen_agent_pos.COMMISSION_MEMBER, report_gen_agent_pos.WIN_COMMISSION_BRANCH, report_gen_agent_pos.WIN_COMMISSION_GEN_AGENT, report_gen_agent_pos.WIN_COMMISSION_STOCKHOLDER, report_gen_agent_pos.WIN_COMMISSION_AGENT, report_gen_agent_pos.WIN_COMMISSION_MEMBER, report_gen_agent_pos.RATE_CHIEF, report_gen_agent_pos.RATE_BRANCH, report_gen_agent_pos.RATE_GEN_AGENT, report_gen_agent_pos.RATE_STOCKHOLDER, report_gen_agent_pos.RATE_AGENT,
                      rate,
                      subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent,
                      rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
                      commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                      moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent
                      );

                   -- 2.1.4.2 累加总额
                   turnover_total := turnover_total + report_gen_agent_pos.TURNOVER;
                   amount_total := amount_total + report_gen_agent_pos.AMOUNT;
                   validAmount_total := validAmount_total + report_gen_agent_pos.VALID_AMOUNT;
                   memberAmount_total := memberAmount_total + report_gen_agent_pos.MEMBER_AMOUNT;
                   memberBackWater_total := memberBackWater_total + memberBackWater;
                   subordinateAmount_total := subordinateAmount_total + subordinateAmount;
                   winBackWater_total := winBackWater_total + winBackWater;
                   realResult_total := realResult_total + realResult;
                   winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
                   paySuperior_total := paySuperior_total + paySuperior;

                   -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
                   commissionBranch_total := commissionBranch_total + report_gen_agent_pos.COMMISSION_BRANCH;
                   commissionGenAgent_total := commissionGenAgent_total + report_gen_agent_pos.COMMISSION_GEN_AGENT;
                   commissionStockholder_total := commissionStockholder_total + report_gen_agent_pos.COMMISSION_STOCKHOLDER;
                   commissionAgent_total := commissionAgent_total + report_gen_agent_pos.COMMISSION_AGENT;
                   commissionMember_total := commissionMember_total + report_gen_agent_pos.COMMISSION_MEMBER;

                   winCommissionBranch_total := winCommissionBranch_total + report_gen_agent_pos.WIN_COMMISSION_BRANCH;
                   winCommissionGenAgent_total := winCommissionGenAgent_total + report_gen_agent_pos.WIN_COMMISSION_GEN_AGENT;
                   winCommissionStockholder_total := winCommissionStockholder_total + report_gen_agent_pos.WIN_COMMISSION_STOCKHOLDER;
                   winCommissionAgent_total := winCommissionAgent_total + report_gen_agent_pos.WIN_COMMISSION_AGENT;
                   winCommissionMember_total := winCommissionMember_total + report_gen_agent_pos.WIN_COMMISSION_MEMBER;

                   rateChief_total := rateChief_total + report_gen_agent_pos.RATE_CHIEF;
                   rateBranch_total := rateBranch_total + report_gen_agent_pos.RATE_BRANCH;
                   rateGenAgent_total := rateGenAgent_total + report_gen_agent_pos.RATE_GEN_AGENT;
                   rateStockholder_total := rateStockholder_total + report_gen_agent_pos.RATE_STOCKHOLDER;
                   rateAgent_total := rateAgent_total + report_gen_agent_pos.RATE_AGENT;

                   -- 实占注额
                   moneyRateChief_total := moneyRateChief_total + moneyRateChief;
                   moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
                   moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
                   moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
                   moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

                   subordinateChief_total := subordinateChief_total + subordinateChief;
                   subordinateBranch_total := subordinateBranch_total + subordinateBranch;
                   subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
                   subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
                   subordinateAgent_total := subordinateAgent_total + subordinateAgent;

                   -- 占成设置值
                   recNum_total := recNum_total + 1;
                   --rate_total := rate_total + rate;
                   --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                   --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                   --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                   --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                   --rateAgentSet_total := rateAgentSet_total + rateAgentSet;

                   -- 2.1.4.3 删除无效临时数据
                   DELETE FROM TEMP_DELIVERYREPORT t WHERE t.PARENT_ID = gen_agent_pos.ID;
              END LOOP;
              END;
          END LOOP;
      END;

      /******** 股东对应的直属会员数据 开始 ********/
      -- 调用直属会员交收报表存储过程
      Delivery_Report_Dir_Member(userID, '4', LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag);
      -- 查询直属会员统计数据
      declare
         cursor dir_total_cursor
         IS
         SELECT * FROM
             TEMP_DELIVERYREPORT report
         WHERE
             report.USER_ID = userID AND report.USER_TYPE = 'Z';
      BEGIN
          -- 累加直属会员统计数据
          FOR dir_total_pos IN dir_total_cursor LOOP
               -- 2.1.4.2 累加总额
               turnover_total := turnover_total + dir_total_pos.TURNOVER;
               amount_total := amount_total + dir_total_pos.AMOUNT;
               validAmount_total := validAmount_total + dir_total_pos.VALID_AMOUNT;
               memberAmount_total := memberAmount_total + dir_total_pos.MEMBER_AMOUNT;
               memberBackWater_total := memberBackWater_total + dir_total_pos.MEMBER_BACK_WATER;
               subordinateAmount_total := subordinateAmount_total + dir_total_pos.SUBORDINATE_AMOUNT;
               winBackWater_total := winBackWater_total + dir_total_pos.WIN_BACK_WATER;
               realResult_total := realResult_total + dir_total_pos.REAL_RESULT;
               winBackWaterResult_total := winBackWaterResult_total + dir_total_pos.WIN_BACK_WATER_RESULT;
               paySuperior_total := paySuperior_total + dir_total_pos.PAY_SUPERIOR;

               -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
               commissionBranch_total := commissionBranch_total + dir_total_pos.COMMISSION_BRANCH;
               commissionGenAgent_total := commissionGenAgent_total + dir_total_pos.COMMISSION_GEN_AGENT;
               commissionStockholder_total := commissionStockholder_total + dir_total_pos.COMMISSION_STOCKHOLDER;
               commissionAgent_total := commissionAgent_total + dir_total_pos.COMMISSION_AGENT;
               commissionMember_total := commissionMember_total + dir_total_pos.COMMISSION_MEMBER;

               winCommissionBranch_total := winCommissionBranch_total + dir_total_pos.WIN_COMMISSION_BRANCH;
               winCommissionGenAgent_total := winCommissionGenAgent_total + dir_total_pos.WIN_COMMISSION_GEN_AGENT;
               winCommissionStockholder_total := winCommissionStockholder_total + dir_total_pos.WIN_COMMISSION_STOCKHOLDER;
               winCommissionAgent_total := winCommissionAgent_total + dir_total_pos.WIN_COMMISSION_AGENT;
               winCommissionMember_total := winCommissionMember_total + dir_total_pos.WIN_COMMISSION_MEMBER;

               rateChief_total := rateChief_total + dir_total_pos.RATE_CHIEF;
               rateBranch_total := rateBranch_total + dir_total_pos.RATE_BRANCH;
               rateGenAgent_total := rateGenAgent_total + dir_total_pos.RATE_GEN_AGENT;
               rateStockholder_total := rateStockholder_total + dir_total_pos.RATE_STOCKHOLDER;
               rateAgent_total := rateAgent_total + dir_total_pos.RATE_AGENT;

               moneyRateChief_total := moneyRateChief_total + dir_total_pos.MONEY_RATE_CHIEF;
               moneyRateBranch_total := moneyRateBranch_total + dir_total_pos.MONEY_RATE_BRANCH;
               moneyRateGenAgent_total := moneyRateGenAgent_total + dir_total_pos.MONEY_RATE_GEN_AGENT;
               moneyRateStockholder_total := moneyRateStockholder_total + dir_total_pos.MONEY_RATE_STOCKHOLDER;
               moneyRateAgent_total := moneyRateAgent_total + dir_total_pos.MONEY_RATE_AGENT;

               subordinateChief_total := subordinateChief_total + dir_total_pos.SUBORDINATE_CHIEF;
               subordinateBranch_total := subordinateBranch_total + dir_total_pos.SUBORDINATE_BRANCH;
               subordinateStockholder_total := subordinateStockholder_total + dir_total_pos.SUBORDINATE_STOCKHOLDER;
               subordinateGenAgent_total := subordinateGenAgent_total + dir_total_pos.SUBORDINATE_GEN_AGENT;
               subordinateAgent_total := subordinateAgent_total + dir_total_pos.SUBORDINATE_AGENT;

               -- 占成设置值
               recNum_total := recNum_total + 1;
               --rate_total := rate_total + dir_total_pos.RATE;
               --rateChiefSet_total := rateChiefSet_total + dir_total_pos.RATE_CHIEF_SET;
               --rateBranchSet_total := rateBranchSet_total + dir_total_pos.RATE_BRANCH_SET;
               --rateStockholderSet_total := rateStockholderSet_total + dir_total_pos.RATE_STOCKHOLDER_SET;
               --rateGenAgentSet_total := rateGenAgentSet_total + dir_total_pos.RATE_GEN_AGENT_SET;
               --rateAgentSet_total := rateAgentSet_total + dir_total_pos.RATE_AGENT_SET;

               -- 2.1.4.3 删除无效临时数据
               DELETE FROM TEMP_DELIVERYREPORT report WHERE report.USER_ID = userID AND report.USER_TYPE = 'Z';
          END LOOP;
      END;
      /******** 股东对应的直属会员数据 结束 ********/

      /******** 补货数据 开始 ********/
      -- 6.2 查询补货数据（补货后续需要区分玩法类型查询）
      sql_replenish := 'SELECT * FROM TB_REPLENISH WHERE REPLENISH_USER_ID = ' || userID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
      -- 只查询结算了的补货数据
      sql_replenish := sql_replenish || ' AND WIN_STATE IN (''1'',''2'',''3'') ';

      IF playType IS NOT NULL THEN
         sql_replenish := sql_replenish || ' AND TYPE_CODE IN (' || playType || ')';
      END IF;

      -- 判断彩票种类
      IF LOTTERY1688Type = 'GDKLSF' THEN
         -- 广东
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''GDKLSF_%'' ';
      END IF;
      IF LOTTERY1688Type = 'HKLHC' THEN
         -- 香港
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''HK_%'' ';
      END IF;
      IF LOTTERY1688Type = 'CQSSC' THEN
         -- 重庆
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''CQSSC_%'' ';
      END IF;

      -- 初始化
      turnover_replenish := 0;                         -- 补货笔数
      amount_replenish := 0;                           -- 补货投注总额
      validAmount_replenish :=0;                       -- 补货有效金额
      amount_win_replenish := 0;                       -- 补货输赢
      backWater_replenish := 0;                        -- 补货退水
      backWaterResult_replenish := 0;                  -- 退水后结果
      backWater_replenish_temp := 0;                   -- 补货输赢（临时）
      amount_win_replenish_temp := 0;                  -- 补货输赢（临时）

      -- 退水相关数据
      commissionBranch := 0;
      commissionStockholder := 0;
      commissionGenAgent := 0;
      commissionAgent := 0;

      winCommissionBranch := 0;
      winCommissionGenAgent := 0;
      winCommissionStockholder := 0;
      winCommissionAgent := 0;

      -- 实占结果
      rateChief := 0;
      rateBranch := 0;
      rateGenAgent := 0;
      rateStockholder := 0;
      rateAgent := 0;

      -- 实占注额
      moneyRateChief := 0;
      moneyRateBranch := 0;
      moneyRateGenAgent := 0;
      moneyRateStockholder := 0;
      moneyRateAgent := 0;

      -- 应收下线
      subordinateChief := 0;
      subordinateBranch := 0;
      subordinateGenAgent := 0;
      subordinateStockholder := 0;
      subordinateAgent := 0;

      -- 执行查询，打开游标
      OPEN cursor_replenish
      FOR
      sql_replenish;

      BEGIN
           LOOP
           FETCH cursor_replenish INTO member_pos_replenish;
               -- 无数据则退出
               IF (cursor_replenish%found = false) THEN
                   EXIT;
               END IF;

               -- 累加补货总额
               amount_replenish := amount_replenish + member_pos_replenish.MONEY;

               -- 累加补货笔数
               turnover_replenish := turnover_replenish + 1;

               -- 补货输赢，对应该代理所有补货的总和(不计退水)
               IF (member_pos_replenish.WIN_STATE = 1) THEN
                  -- 累加“中奖”的投注额
                  amount_win_replenish := amount_win_replenish + member_pos_replenish.WIN_AMOUNT;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100;
                  amount_win_replenish_temp := member_pos_replenish.WIN_AMOUNT;
               END IF;
               IF (member_pos_replenish.WIN_STATE = 2) THEN
                  -- 减去“未中奖”的投注额
                  amount_win_replenish := amount_win_replenish - member_pos_replenish.MONEY;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100;
                  amount_win_replenish_temp := - member_pos_replenish.MONEY;
               END IF;

               -- 补货有效金额，不计算打和
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  validAmount_replenish := validAmount_replenish + member_pos_replenish.MONEY;
               END IF;
               -- TODO 退水需要补全所有级别的退水信息
               -- 补货退水（代理佣金*投注额，除了打和，其他的都要退水）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  backWater_replenish := backWater_replenish + backWater_replenish_temp;
                  --rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;
               END IF;
               -- 打和则不计算佣金（退水）（当做直属会员处理）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  commissionBranch := commissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER) / 100);
                  commissionStockholder := commissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - 0) / 100);

                  winCommissionBranch := winCommissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_replenish.RATE_STOCKHOLDER/100 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);
                  winCommissionStockholder := winCommissionStockholder + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_STOCKHOLDER - member_pos_replenish.COMMISSION_GEN_AGENT) / 100) * (1 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);

                  -- 实占结果（退水后结果，也即是 退水 + 输赢）*占成%
                  rateChief := rateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  rateBranch := rateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * member_pos_replenish.RATE_BRANCH/100;

                  -- 实占注额（有效的投注金额 * 占成%）
                  moneyRateChief := moneyRateChief + (member_pos_replenish.MONEY * member_pos_replenish.RATE_CHIEF / 100);
                  moneyRateBranch := moneyRateBranch + (member_pos_replenish.MONEY * member_pos_replenish.RATE_BRANCH / 100);
                  moneyRateStockholder := moneyRateStockholder + (member_pos_replenish.MONEY * member_pos_replenish.RATE_STOCKHOLDER / 100);
                  moneyRateGenAgent := moneyRateGenAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_GEN_AGENT / 100);
                  moneyRateAgent := moneyRateAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_AGENT / 100);

                  -- 各级应收下线
                  subordinateChief := subordinateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;
                  subordinateBranch := subordinateBranch + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_STOCKHOLDER / 100)) * (1);
               END IF;

           END LOOP;
           CLOSE cursor_replenish;
      END;

      -- 补货退水后结果（退水 + 输赢）
      backWaterResult_replenish := backWater_replenish + amount_win_replenish;

      -- 4. 补货数据插入临时表（处理数据类型）（数据对应的 USER_TYPE 为实际值 + a，即如果实际值为1，则填充值 b，实际值为0，则填充a）
      -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
      IF (turnover_replenish > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
              (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, WIN_BACK_WATER_RESULT)
        VALUES
              (userID, userID, 'e', '1', '补货', '补货', turnover_replenish, amount_replenish, validAmount_replenish, amount_win_replenish, backWater_replenish, backWaterResult_replenish);
      END IF;

      -- 5. 累加总额（增加补货数据）
      turnover_total := turnover_total + turnover_replenish;
      amount_total := amount_total + amount_replenish;
      validAmount_total := validAmount_total + validAmount_replenish;
      memberAmount_total := memberAmount_total + amount_win_replenish;
      memberBackWater_total := memberBackWater_total + backWater_replenish;
      winBackWaterResult_total := winBackWaterResult_total + backWaterResult_replenish;

      -- 6. 累积补货上线所对应的计算后的佣金、占成（总额）
      commissionBranch_total := commissionBranch_total + commissionBranch;
      commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
      commissionStockholder_total := commissionStockholder_total + commissionStockholder;
      commissionAgent_total := commissionAgent_total + commissionAgent;

      winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
      winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
      winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
      winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;

      rateChief_total := rateChief_total + rateChief;
      rateBranch_total := rateBranch_total + rateBranch;
      rateGenAgent_total := rateGenAgent_total + rateGenAgent;
      rateStockholder_total := rateStockholder_total + rateStockholder;
      rateAgent_total := rateAgent_total + rateAgent;

      -- 实占注额
      moneyRateChief_total := moneyRateChief_total + moneyRateChief;
      moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
      moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
      moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
      moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

      subordinateChief_total := subordinateChief_total + subordinateChief;
      subordinateBranch_total := subordinateBranch_total + subordinateBranch;
      subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
      subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
      subordinateAgent_total := subordinateAgent_total + subordinateAgent;
      /******** 补货数据 结束 ********/

      -- 4. 总额数据插入临时表（为方便排序，总额数据对应的 USER_TYPE 为实际值 + A，即如果实际值为1，则填充值 B）
      -- C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
      IF (turnover_total > 0) THEN
         INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
            SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
            RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
            COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
         VALUES
            (userID, userID, 'E', '', '合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, rate,
            subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total,
            rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
            commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;
      -- 5. 从临时表中查询数据
      OPEN statReportGenAgent
      FOR
      SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 6. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/

prompt
prompt Creating procedure DELIVERY_REPORT_BRANCH
prompt =========================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Branch(
/*==============================================================*/
/*                    分公司交收报表存储过程                      */
/*==============================================================*/
     userID IN varchar2,                -- 代理ID
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2,           -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
     statReportStockholder OUT statReportResult.resultRef         -- 返回结果集
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover NUMBER;                   -- 成交笔数
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     sqlStr varchar2(10000);
     type   mycursor   is   ref   cursor;
     his_cursor  mycursor;
     member_pos TB_HKLHC_HIS%rowtype;

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     -- 赚取佣金
     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）

     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值

     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     -- 赚取佣金
     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     recNum_total NUMBER;                     -- 临时变量，有效会员数
     -- 占成设置值只取最后一个值，故总和值无效
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）
BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     -- 初始化占成设置值相关数据
     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 1.3 删除临时表中的数据
     DELETE FROM TEMP_DELIVERYREPORT WHERE PARENT_ID = userID;

     -- 2.1 查询分公司对应的股东信息
     declare
     cursor stockholder_cursor
     IS
     SELECT * FROM
         (TB_STOCKHOLDER_STAFF_EXT ext INNER JOIN TB_FRAME_MANAGER_STAFF managerStaff ON ext.MANAGER_STAFF_ID = managerStaff.ID)
     WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.1.1 循环处理所有的股东信息
          FOR stockholder_pos IN stockholder_cursor LOOP
              -- 2.1.2 填充数据记录
              subordinate := stockholder_pos.ACCOUNT;         -- 下级登陆账号
              userName := stockholder_pos.CHS_NAME;           -- 用户名称
              subID := stockholder_pos.ID;                    -- 记录ID
              -- 2.1.3 初始化数据
              turnover := 0;                  -- 成交笔数
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              rate := 0;                      -- 占成
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              -- 赚取退水
              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;

              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              -- 2.1.4 调用股东交收报表存储过程
              Delivery_Report_Stockholder(subID, LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag, statReportStockholder);
              -- 读取代理交收报表存储过程所形成的数据
              declare
              cursor report_stockholder_cursor
              IS
              -- 过滤掉下级用户对应的补货信息
              -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
              SELECT * FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = stockholder_pos.ID AND t.USER_TYPE NOT IN ('g','f','e');

              BEGIN
              FOR report_stockholder_pos IN report_stockholder_cursor LOOP

                  -- 会员退水
                  memberBackWater := report_stockholder_pos.COMMISSION_STOCKHOLDER + report_stockholder_pos.COMMISSION_GEN_AGENT + report_stockholder_pos.COMMISSION_AGENT + report_stockholder_pos.COMMISSION_MEMBER;
                  -- 应收下线（直接读取下线所计算的本级及上线占成结果，此处不能计算，因为不同的投注占成值不同）
                  -- subordinateAmount := report_stockholder_pos.RATE_CHIEF + report_stockholder_pos.RATE_BRANCH + report_stockholder_pos.RATE_STOCKHOLDER + report_stockholder_pos.RATE_GEN_AGENT;
                  subordinateAmount := report_stockholder_pos.SUBORDINATE_BRANCH;
                  -- 实占结果（直接读取下线所计算的股东占成结果，此处不能计算，因为不同的投注占成值不同）
                  realResult := report_stockholder_pos.RATE_BRANCH;
                  -- 赚取退水
                  winBackWater := report_stockholder_pos.WIN_COMMISSION_BRANCH;
                  -- 赚水后结果（实占结果-赚取退水）
                  winBackWaterResult := winBackWaterResult + realResult - winBackWater;
                  -- 应付上线（应收下线－赚水后结果）
                  paySuperior := subordinateAmount - winBackWaterResult;

                  -- 实占注额
                  moneyRateChief := report_stockholder_pos.MONEY_RATE_CHIEF;
                  moneyRateBranch := report_stockholder_pos.MONEY_RATE_BRANCH;
                  moneyRateGenAgent := report_stockholder_pos.MONEY_RATE_GEN_AGENT;
                  moneyRateStockholder := report_stockholder_pos.MONEY_RATE_STOCKHOLDER;
                  moneyRateAgent := report_stockholder_pos.MONEY_RATE_AGENT;

                  subordinateChief := report_stockholder_pos.SUBORDINATE_CHIEF;
                  subordinateBranch := report_stockholder_pos.SUBORDINATE_BRANCH;
                  subordinateStockholder := report_stockholder_pos.SUBORDINATE_STOCKHOLDER;
                  subordinateGenAgent := report_stockholder_pos.SUBORDINATE_GEN_AGENT;
                  subordinateAgent := report_stockholder_pos.SUBORDINATE_AGENT;

                  -- 占成设置值
                  rateChiefSet := report_stockholder_pos.RATE_CHIEF_SET;
                  rateBranchSet := report_stockholder_pos.RATE_BRANCH_SET;
                  rateStockholderSet := report_stockholder_pos.RATE_STOCKHOLDER_SET;
                  rateGenAgentSet := report_stockholder_pos.RATE_GEN_AGENT_SET;
                  rateAgentSet := report_stockholder_pos.RATE_AGENT_SET;

                  -- 退水设置值
                  commissionBranchSet := report_stockholder_pos.COMMISSION_BRANCH_SET;
                  commissionStockholderSet := report_stockholder_pos.COMMISSION_STOCKHOLDER_SET;
                  commissionGenAgentSet := report_stockholder_pos.COMMISSION_GEN_AGENT_SET;
                  commissionAgentSet := report_stockholder_pos.COMMISSION_AGENT_SET;

                  rate := report_stockholder_pos.RATE_BRANCH_SET;
                  -- 赋值，TODO 考虑如果补货数据在投注数据之前出现是否会有问题
                  turnover := report_stockholder_pos.TURNOVER;          -- 成交笔数
                  amount := report_stockholder_pos.AMOUNT;              -- 投注总额
                  validAmount := report_stockholder_pos.VALID_AMOUNT;   -- 有效金额
                  memberAmount := report_stockholder_pos.MEMBER_AMOUNT; -- 会员输赢

                  -- 2.1.4.1 数据插入临时表
                  INSERT INTO TEMP_DELIVERYREPORT
                      (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                      COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
                      SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
                      RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
                      COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                      MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                  VALUES (
                      userID,
                      stockholder_pos.ID,
                      '4',
                      stockholder_pos.ACCOUNT,
                      stockholder_pos.CHS_NAME,
                      turnover,
                      amount,
                      validAmount,
                      memberAmount,
                      memberBackWater,
                      subordinateAmount,
                      winBackWater,
                      realResult,
                      winBackWaterResult,
                      paySuperior,
                      report_stockholder_pos.COMMISSION_BRANCH, report_stockholder_pos.COMMISSION_GEN_AGENT, report_stockholder_pos.COMMISSION_STOCKHOLDER, report_stockholder_pos.COMMISSION_AGENT, report_stockholder_pos.COMMISSION_MEMBER, report_stockholder_pos.WIN_COMMISSION_BRANCH, report_stockholder_pos.WIN_COMMISSION_GEN_AGENT, report_stockholder_pos.WIN_COMMISSION_STOCKHOLDER, report_stockholder_pos.WIN_COMMISSION_AGENT, report_stockholder_pos.WIN_COMMISSION_MEMBER, report_stockholder_pos.RATE_CHIEF, report_stockholder_pos.RATE_BRANCH, report_stockholder_pos.RATE_GEN_AGENT, report_stockholder_pos.RATE_STOCKHOLDER, report_stockholder_pos.RATE_AGENT,
                      rate,
                      subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent,
                      rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
                      commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                      moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent
                      );

                   -- 2.1.4.2 累加总额
                   turnover_total := turnover_total + report_stockholder_pos.TURNOVER;
                   amount_total := amount_total + report_stockholder_pos.AMOUNT;
                   validAmount_total := validAmount_total + report_stockholder_pos.VALID_AMOUNT;
                   memberAmount_total := memberAmount_total + report_stockholder_pos.MEMBER_AMOUNT;
                   memberBackWater_total := memberBackWater_total + memberBackWater;
                   subordinateAmount_total := subordinateAmount_total + subordinateAmount;
                   winBackWater_total := winBackWater_total + winBackWater;
                   realResult_total := realResult_total + realResult;
                   winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
                   paySuperior_total := paySuperior_total + paySuperior;

                   -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
                   commissionBranch_total := commissionBranch_total + report_stockholder_pos.COMMISSION_BRANCH;
                   commissionGenAgent_total := commissionGenAgent_total + report_stockholder_pos.COMMISSION_GEN_AGENT;
                   commissionStockholder_total := commissionStockholder_total + report_stockholder_pos.COMMISSION_STOCKHOLDER;
                   commissionAgent_total := commissionAgent_total + report_stockholder_pos.COMMISSION_AGENT;
                   commissionMember_total := commissionMember_total + report_stockholder_pos.COMMISSION_MEMBER;

                   winCommissionBranch_total := winCommissionBranch_total + report_stockholder_pos.WIN_COMMISSION_BRANCH;
                   winCommissionGenAgent_total := winCommissionGenAgent_total + report_stockholder_pos.WIN_COMMISSION_GEN_AGENT;
                   winCommissionStockholder_total := winCommissionStockholder_total + report_stockholder_pos.WIN_COMMISSION_STOCKHOLDER;
                   winCommissionAgent_total := winCommissionAgent_total + report_stockholder_pos.WIN_COMMISSION_AGENT;
                   winCommissionMember_total := winCommissionMember_total + report_stockholder_pos.WIN_COMMISSION_MEMBER;

                   rateChief_total := rateChief_total + report_stockholder_pos.RATE_CHIEF;
                   rateBranch_total := rateBranch_total + report_stockholder_pos.RATE_BRANCH;
                   rateGenAgent_total := rateGenAgent_total + report_stockholder_pos.RATE_GEN_AGENT;
                   rateStockholder_total := rateStockholder_total + report_stockholder_pos.RATE_STOCKHOLDER;
                   rateAgent_total := rateAgent_total + report_stockholder_pos.RATE_AGENT;

                   -- 实占注额
                   moneyRateChief_total := moneyRateChief_total + moneyRateChief;
                   moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
                   moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
                   moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
                   moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

                   subordinateChief_total := subordinateChief_total + subordinateChief;
                   subordinateBranch_total := subordinateBranch_total + subordinateBranch;
                   subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
                   subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
                   subordinateAgent_total := subordinateAgent_total + subordinateAgent;

                   -- 占成设置值
                   recNum_total := recNum_total + 1;
                   --rate_total := rate_total + rate;
                   --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                   --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                   --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                   --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                   --rateAgentSet_total := rateAgentSet_total + rateAgentSet;

                   -- 2.1.4.3 删除无效临时数据
                   DELETE FROM TEMP_DELIVERYREPORT t WHERE t.PARENT_ID = stockholder_pos.ID;
              END LOOP;
              END;
          END LOOP;
      END;

      /******** 分公司对应的直属会员数据 开始 ********/
      -- 调用直属会员交收报表存储过程
      Delivery_Report_Dir_Member(userID, '3', LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag);
      -- 查询直属会员统计数据
      declare
         cursor dir_total_cursor
         IS
         SELECT * FROM
             TEMP_DELIVERYREPORT report
         WHERE
             report.USER_ID = userID AND report.USER_TYPE = 'Z';
      BEGIN
          -- 累加直属会员统计数据
          FOR dir_total_pos IN dir_total_cursor LOOP
               -- 2.1.4.2 累加总额
               turnover_total := turnover_total + dir_total_pos.TURNOVER;
               amount_total := amount_total + dir_total_pos.AMOUNT;
               validAmount_total := validAmount_total + dir_total_pos.VALID_AMOUNT;
               memberAmount_total := memberAmount_total + dir_total_pos.MEMBER_AMOUNT;
               memberBackWater_total := memberBackWater_total + dir_total_pos.MEMBER_BACK_WATER;
               subordinateAmount_total := subordinateAmount_total + dir_total_pos.SUBORDINATE_AMOUNT;
               winBackWater_total := winBackWater_total + dir_total_pos.WIN_BACK_WATER;
               realResult_total := realResult_total + dir_total_pos.REAL_RESULT;
               winBackWaterResult_total := winBackWaterResult_total + dir_total_pos.WIN_BACK_WATER_RESULT;
               paySuperior_total := paySuperior_total + dir_total_pos.PAY_SUPERIOR;

               -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
               commissionBranch_total := commissionBranch_total + dir_total_pos.COMMISSION_BRANCH;
               commissionGenAgent_total := commissionGenAgent_total + dir_total_pos.COMMISSION_GEN_AGENT;
               commissionStockholder_total := commissionStockholder_total + dir_total_pos.COMMISSION_STOCKHOLDER;
               commissionAgent_total := commissionAgent_total + dir_total_pos.COMMISSION_AGENT;
               commissionMember_total := commissionMember_total + dir_total_pos.COMMISSION_MEMBER;

               winCommissionBranch_total := winCommissionBranch_total + dir_total_pos.WIN_COMMISSION_BRANCH;
               winCommissionGenAgent_total := winCommissionGenAgent_total + dir_total_pos.WIN_COMMISSION_GEN_AGENT;
               winCommissionStockholder_total := winCommissionStockholder_total + dir_total_pos.WIN_COMMISSION_STOCKHOLDER;
               winCommissionAgent_total := winCommissionAgent_total + dir_total_pos.WIN_COMMISSION_AGENT;
               winCommissionMember_total := winCommissionMember_total + dir_total_pos.WIN_COMMISSION_MEMBER;

               rateChief_total := rateChief_total + dir_total_pos.RATE_CHIEF;
               rateBranch_total := rateBranch_total + dir_total_pos.RATE_BRANCH;
               rateGenAgent_total := rateGenAgent_total + dir_total_pos.RATE_GEN_AGENT;
               rateStockholder_total := rateStockholder_total + dir_total_pos.RATE_STOCKHOLDER;
               rateAgent_total := rateAgent_total + dir_total_pos.RATE_AGENT;

               moneyRateChief_total := moneyRateChief_total + dir_total_pos.MONEY_RATE_CHIEF;
               moneyRateBranch_total := moneyRateBranch_total + dir_total_pos.MONEY_RATE_BRANCH;
               moneyRateGenAgent_total := moneyRateGenAgent_total + dir_total_pos.MONEY_RATE_GEN_AGENT;
               moneyRateStockholder_total := moneyRateStockholder_total + dir_total_pos.MONEY_RATE_STOCKHOLDER;
               moneyRateAgent_total := moneyRateAgent_total + dir_total_pos.MONEY_RATE_AGENT;

               subordinateChief_total := subordinateChief_total + dir_total_pos.SUBORDINATE_CHIEF;
               subordinateBranch_total := subordinateBranch_total + dir_total_pos.SUBORDINATE_BRANCH;
               subordinateStockholder_total := subordinateStockholder_total + dir_total_pos.SUBORDINATE_STOCKHOLDER;
               subordinateGenAgent_total := subordinateGenAgent_total + dir_total_pos.SUBORDINATE_GEN_AGENT;
               subordinateAgent_total := subordinateAgent_total + dir_total_pos.SUBORDINATE_AGENT;

               -- 占成设置值
               recNum_total := recNum_total + 1;
               --rate_total := rate_total + dir_total_pos.RATE;
               --rateChiefSet_total := rateChiefSet_total + dir_total_pos.RATE_CHIEF_SET;
               --rateBranchSet_total := rateBranchSet_total + dir_total_pos.RATE_BRANCH_SET;
               --rateStockholderSet_total := rateStockholderSet_total + dir_total_pos.RATE_STOCKHOLDER_SET;
               --rateGenAgentSet_total := rateGenAgentSet_total + dir_total_pos.RATE_GEN_AGENT_SET;
               --rateAgentSet_total := rateAgentSet_total + dir_total_pos.RATE_AGENT_SET;

               -- 2.1.4.3 删除无效临时数据
               DELETE FROM TEMP_DELIVERYREPORT report WHERE report.USER_ID = userID AND report.USER_TYPE = 'Z';
          END LOOP;
      END;
      /******** 分公司对应的直属会员数据 结束 ********/

      /******** 补货数据 开始 ********/
      -- 6.2 查询补货数据（补货后续需要区分玩法类型查询）
      sql_replenish := 'SELECT * FROM TB_REPLENISH WHERE REPLENISH_USER_ID = ' || userID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
      -- 只查询结算了的补货数据
      sql_replenish := sql_replenish || ' AND WIN_STATE IN (''1'',''2'',''3'') ';

      IF playType IS NOT NULL THEN
         sql_replenish := sql_replenish || ' AND TYPE_CODE IN (' || playType || ')';
      END IF;

      -- 判断彩票种类
      IF LOTTERY1688Type = 'GDKLSF' THEN
         -- 广东
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''GDKLSF_%'' ';
      END IF;
      IF LOTTERY1688Type = 'HKLHC' THEN
         -- 香港
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''HK_%'' ';
      END IF;
      IF LOTTERY1688Type = 'CQSSC' THEN
         -- 重庆
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''CQSSC_%'' ';
      END IF;

      -- 初始化
      turnover_replenish := 0;                         -- 补货笔数
      amount_replenish := 0;                           -- 补货投注总额
      validAmount_replenish :=0;                       -- 补货有效金额
      amount_win_replenish := 0;                       -- 补货输赢
      backWater_replenish := 0;                        -- 补货退水
      backWaterResult_replenish := 0;                  -- 退水后结果
      backWater_replenish_temp := 0;                   -- 补货输赢（临时）
      amount_win_replenish_temp := 0;                  -- 补货输赢（临时）

      -- 退水相关数据
      commissionBranch := 0;
      commissionStockholder := 0;
      commissionGenAgent := 0;
      commissionAgent := 0;

      winCommissionBranch := 0;
      winCommissionGenAgent := 0;
      winCommissionStockholder := 0;
      winCommissionAgent := 0;

      -- 实占结果
      rateChief := 0;
      rateBranch := 0;
      rateGenAgent := 0;
      rateStockholder := 0;
      rateAgent := 0;

      -- 实占注额
      moneyRateChief := 0;
      moneyRateBranch := 0;
      moneyRateGenAgent := 0;
      moneyRateStockholder := 0;
      moneyRateAgent := 0;

      -- 应收下线
      subordinateChief := 0;
      subordinateBranch := 0;
      subordinateGenAgent := 0;
      subordinateStockholder := 0;
      subordinateAgent := 0;

      -- 执行查询，打开游标
      OPEN cursor_replenish
      FOR
      sql_replenish;

      BEGIN
           LOOP
           FETCH cursor_replenish INTO member_pos_replenish;
               -- 无数据则退出
               IF (cursor_replenish%found = false) THEN
                   EXIT;
               END IF;

               -- 累加补货总额
               amount_replenish := amount_replenish + member_pos_replenish.MONEY;

               -- 累加补货笔数
               turnover_replenish := turnover_replenish + 1;

               -- 补货输赢，对应该代理所有补货的总和(不计退水)
               IF (member_pos_replenish.WIN_STATE = 1) THEN
                  -- 累加“中奖”的投注额
                  amount_win_replenish := amount_win_replenish + member_pos_replenish.WIN_AMOUNT;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100;
                  amount_win_replenish_temp := member_pos_replenish.WIN_AMOUNT;
               END IF;
               IF (member_pos_replenish.WIN_STATE = 2) THEN
                  -- 减去“未中奖”的投注额
                  amount_win_replenish := amount_win_replenish - member_pos_replenish.MONEY;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100;
                  amount_win_replenish_temp := - member_pos_replenish.MONEY;
               END IF;

               -- 补货有效金额，不计算打和
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  validAmount_replenish := validAmount_replenish + member_pos_replenish.MONEY;
               END IF;
               -- TODO 退水需要补全所有级别的退水信息
               -- 补货退水（代理佣金*投注额，除了打和，其他的都要退水）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  backWater_replenish := backWater_replenish + backWater_replenish_temp;
                  --rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;
               END IF;
               -- 打和则不计算佣金（退水）（当做直属会员处理）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  commissionBranch := commissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - 0) / 100);

                  winCommissionBranch := winCommissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - member_pos_replenish.COMMISSION_STOCKHOLDER)/ 100) * (1 - member_pos_replenish.RATE_STOCKHOLDER/100 - member_pos_replenish.RATE_GEN_AGENT/100 -  member_pos_replenish.RATE_AGENT/100);

                  -- 实占结果（退水后结果，也即是 退水 + 输赢）*占成%
                  rateChief := rateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;

                  -- 实占注额（有效的投注金额 * 占成%）
                  moneyRateChief := moneyRateChief + (member_pos_replenish.MONEY * member_pos_replenish.RATE_CHIEF / 100);
                  moneyRateBranch := moneyRateBranch + (member_pos_replenish.MONEY * member_pos_replenish.RATE_BRANCH / 100);
                  moneyRateStockholder := moneyRateStockholder + (member_pos_replenish.MONEY * member_pos_replenish.RATE_STOCKHOLDER / 100);
                  moneyRateGenAgent := moneyRateGenAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_GEN_AGENT / 100);
                  moneyRateAgent := moneyRateAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_AGENT / 100);

                  -- 各级应收下线
                  subordinateChief := subordinateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * (1);
               END IF;

           END LOOP;
           CLOSE cursor_replenish;
      END;

      -- 补货退水后结果（退水 + 输赢）
      backWaterResult_replenish := backWater_replenish + amount_win_replenish;

      -- 4. 补货数据插入临时表（处理数据类型）（数据对应的 USER_TYPE 为实际值 + a，即如果实际值为1，则填充值 b，实际值为0，则填充a）
      -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
      IF (turnover_replenish > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
              (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, WIN_BACK_WATER_RESULT)
        VALUES
              (userID, userID, 'd', '1', '补货', '补货', turnover_replenish, amount_replenish, validAmount_replenish, amount_win_replenish, backWater_replenish, backWaterResult_replenish);
      END IF;

      -- 5. 累加总额（增加补货数据）
      turnover_total := turnover_total + turnover_replenish;
      amount_total := amount_total + amount_replenish;
      validAmount_total := validAmount_total + validAmount_replenish;
      memberAmount_total := memberAmount_total + amount_win_replenish;
      memberBackWater_total := memberBackWater_total + backWater_replenish;
      winBackWaterResult_total := winBackWaterResult_total + backWaterResult_replenish;

      -- 6. 累积补货上线所对应的计算后的佣金、占成（总额）
      commissionBranch_total := commissionBranch_total + commissionBranch;
      commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
      commissionStockholder_total := commissionStockholder_total + commissionStockholder;
      commissionAgent_total := commissionAgent_total + commissionAgent;

      winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
      winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
      winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
      winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;

      rateChief_total := rateChief_total + rateChief;
      rateBranch_total := rateBranch_total + rateBranch;
      rateGenAgent_total := rateGenAgent_total + rateGenAgent;
      rateStockholder_total := rateStockholder_total + rateStockholder;
      rateAgent_total := rateAgent_total + rateAgent;

      -- 实占注额
      moneyRateChief_total := moneyRateChief_total + moneyRateChief;
      moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
      moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
      moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
      moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

      subordinateChief_total := subordinateChief_total + subordinateChief;
      subordinateBranch_total := subordinateBranch_total + subordinateBranch;
      subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
      subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
      subordinateAgent_total := subordinateAgent_total + subordinateAgent;
      /******** 补货数据 结束 ********/

      -- 4. 总额数据插入临时表（为方便排序，总额数据对应的 USER_TYPE 为实际值 + A，即如果实际值为1，则填充值 B）
      -- C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
      IF (turnover_total > 0) THEN
         INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
            SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
            RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
            COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
         VALUES
            (userID, userID, 'D', '', '合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, rate,
            subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total,
            rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
            commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;
      -- 5. 从临时表中查询数据
      OPEN statReportStockholder
      FOR
      SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 6. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/

prompt
prompt Creating procedure DELIVERY_REPORT_CHIEF
prompt ========================================
prompt
CREATE OR REPLACE PROCEDURE Delivery_Report_Chief(
/*==============================================================*/
/*                    总监交收报表存储过程                      */
/*==============================================================*/
     userID IN varchar2,                -- 代理ID
     LOTTERY1688Type IN varchar2,           -- 彩票种类
     playType IN varchar2,              -- 下注类型
     periodsNum IN varchar2,            -- 期数
     startDate IN varchar2,             -- 开始时间
     endDate IN varchar2,               -- 结束时间
     resultFlag OUT varchar2,           -- 存储执行结果值：0-成功；1-userID为空；2-数据为空; 9-未知错误
     statReportBranch OUT statReportResult.resultRef         -- 返回结果集
) AS
     subordinate varchar2(50);          -- 下级登陆账号
     userName varchar2(50);             -- 用户名称
     turnover NUMBER;                   -- 成交笔数
     amount NUMBER;                     -- 投注总额
     validAmount NUMBER;                -- 有效金额
     memberAmount NUMBER;               -- 会员输赢
     memberBackWater NUMBER;            -- 会员退水
     subordinateAmount NUMBER;          -- 应收下线
     winBackWater NUMBER;               -- 赚取退水
     realResult NUMBER;                 -- 实占结果
     winBackWaterResult NUMBER;         -- 赚水后结果
     paySuperior NUMBER;                -- 应付上级
     subID NUMBER;                      -- 记录ID
     memberAmount_temp NUMBER;          -- 临时变量，存储会员输赢数据
     memberBackWater_temp NUMBER;       -- 临时变量，会员退水
     recNum NUMBER;                     -- 临时变量，记录数

     -- 定义游标
     sqlStr varchar2(10000);
     type   mycursor   is   ref   cursor;
     his_cursor  mycursor;
     member_pos TB_HKLHC_HIS%rowtype;

     sql_replenish varchar2(2000);              -- 补货查询 sql
     cursor_replenish mycursor;                 -- 补货游标
     member_pos_replenish TB_REPLENISH%rowtype; -- 补货数据对象
     turnover_replenish NUMBER;                 -- 补货笔数
     amount_replenish NUMBER;                   -- 补货投注总额
     validAmount_replenish NUMBER;              -- 补货有效金额
     amount_win_replenish NUMBER;               -- 补货输赢
     backWater_replenish NUMBER;                -- 补货退水
     backWaterResult_replenish NUMBER;          -- 退水后结果
     backWater_replenish_temp NUMBER;           -- 补货退水（临时）
     amount_win_replenish_temp NUMBER;          -- 补货输赢（临时）

     -- 总额统计值
     turnover_total NUMBER;             -- 成交笔数（总额）
     amount_total NUMBER;               -- 投注总额（总额）
     validAmount_total NUMBER;          -- 有效金额（总额）
     memberAmount_total NUMBER;         -- 会员输赢（总额）
     memberBackWater_total NUMBER;      -- 会员退水（总额）
     subordinateAmount_total NUMBER;    -- 应收下线（总额）
     winBackWater_total NUMBER;         -- 赚取退水（总额）
     realResult_total NUMBER;           -- 实占结果（总额）
     winBackWaterResult_total NUMBER;   -- 赚水后结果（总额）
     paySuperior_total NUMBER;          -- 应付上级（总额）

     -- 存储上线所对应的计算后的佣金、占成、下线应收
     commissionBranch NUMBER;           -- 分公司佣金
     commissionGenAgent NUMBER;         -- 总代理佣金
     commissionStockholder NUMBER;      -- 股东佣金
     commissionAgent NUMBER;            -- 代理佣金
     commissionMember NUMBER;           -- 会员佣金

     -- 赚取佣金
     winCommissionBranch NUMBER;           -- 分公司赚取佣金
     winCommissionGenAgent NUMBER;         -- 总代理赚取佣金
     winCommissionStockholder NUMBER;      -- 股东赚取佣金
     winCommissionAgent NUMBER;            -- 代理赚取佣金
     winCommissionMember NUMBER;           -- 会员赚取佣金

     rateChief NUMBER;                  -- 总监占成
     rateBranch NUMBER;                 -- 分公司占成
     rateGenAgent NUMBER;               -- 总代理占成
     rateStockholder NUMBER;            -- 股东占成
     rateAgent NUMBER;                  -- 代理占成

     moneyRateChief NUMBER;             -- 总监实占注额
     moneyRateBranch NUMBER;            -- 分公司实占注额
     moneyRateGenAgent NUMBER;          -- 总代理实占注额
     moneyRateStockholder NUMBER;       -- 股东实占注额
     moneyRateAgent NUMBER;             -- 代理实占注额

     subordinateChief NUMBER;           -- 下线应收（总监）
     subordinateBranch NUMBER;          -- 下线应收（分公司）
     subordinateStockholder NUMBER;     -- 下线应收（股东）
     subordinateGenAgent NUMBER;        -- 下线应收（总代理）
     subordinateAgent NUMBER;           -- 下线应收（代理）

     rate NUMBER;                       -- 占成设置值
     rateChiefSet NUMBER;               -- 总监占成设置值
     rateBranchSet NUMBER;              -- 分公司占成设置值
     rateStockholderSet NUMBER;         -- 股东占成设置值
     rateGenAgentSet NUMBER;            -- 总代理占成设置值
     rateAgentSet NUMBER;               -- 代理占成设置值

     commissionBranchSet NUMBER;        -- 分公司退水设置值
     commissionStockholderSet NUMBER;   -- 股东退水设置值
     commissionGenAgentSet NUMBER;      -- 总代理退水设置值
     commissionAgentSet NUMBER;         -- 代理退水设置值

     -- 存储上线所对应的计算后的佣金、占成、下线应收（总额）
     commissionBranch_total NUMBER;           -- 分公司佣金（总额）
     commissionGenAgent_total NUMBER;         -- 总代理佣金（总额）
     commissionStockholder_total NUMBER;      -- 股东佣金（总额）
     commissionAgent_total NUMBER;            -- 代理佣金（总额）
     commissionMember_total NUMBER;           -- 会员佣金（总额）

     -- 赚取佣金
     winCommissionBranch_total NUMBER;           -- 分公司赚取佣金（总额）
     winCommissionGenAgent_total NUMBER;         -- 总代理赚取佣金（总额）
     winCommissionStockholder_total NUMBER;      -- 股东赚取佣金（总额）
     winCommissionAgent_total NUMBER;            -- 代理赚取佣金（总额）
     winCommissionMember_total NUMBER;           -- 会员赚取佣金（总额）

     rateChief_total NUMBER;                  -- 总监占成（总额）
     rateBranch_total NUMBER;                 -- 分公司占成（总额）
     rateGenAgent_total NUMBER;               -- 总代理占成（总额）
     rateStockholder_total NUMBER;            -- 股东占成（总额）
     rateAgent_total NUMBER;                  -- 代理占成（总额）

     moneyRateChief_total NUMBER;                  -- 总监实占注额（总额）
     moneyRateBranch_total NUMBER;                 -- 分公司实占注额（总额）
     moneyRateGenAgent_total NUMBER;               -- 总代理实占注额（总额）
     moneyRateStockholder_total NUMBER;            -- 股东实占注额（总额）
     moneyRateAgent_total NUMBER;                  -- 代理实占注额（总额）

     subordinateChief_total NUMBER;           -- 下线应收（总监）
     subordinateBranch_total NUMBER;          -- 下线应收（分公司）
     subordinateStockholder_total NUMBER;     -- 下线应收（股东）
     subordinateGenAgent_total NUMBER;        -- 下线应收（总代理）
     subordinateAgent_total NUMBER;           -- 下线应收（代理）

     -- 占成设置值只取最后一个值，故总和值无效
     recNum_total NUMBER;                     -- 临时变量，有效会员数
     --rate_total NUMBER;                       -- 占成设置值（总和）
     --rateChiefSet_total NUMBER;               -- 总监占成设置值（总和）
     --rateBranchSet_total NUMBER;              -- 分公司占成设置值（总和）
     --rateStockholderSet_total NUMBER;         -- 股东占成设置值（总和）
     --rateGenAgentSet_total NUMBER;            -- 总代理占成设置值（总和）
     --rateAgentSet_total NUMBER;               -- 代理占成设置值（总和）
BEGIN
     -- 初始化返回结果值
     resultFlag := 0;

     -- 1.1 校验输入参数
     dbms_output.put_line('userID：'||userID);
     IF(userID IS NULL) THEN
         resultFlag := 1;
         return;
     END IF;

     -- 1.2 初始化总额
     turnover_total := 0;
     amount_total := 0;
     validAmount_total := 0;
     memberAmount_total := 0;
     memberBackWater_total := 0;
     subordinateAmount_total := 0;
     winBackWater_total := 0;
     realResult_total := 0;
     winBackWaterResult_total := 0;
     paySuperior_total := 0;

     commissionBranch_total := 0;
     commissionGenAgent_total := 0;
     commissionStockholder_total := 0;
     commissionAgent_total := 0;
     commissionMember_total := 0;

     winCommissionBranch_total := 0;
     winCommissionGenAgent_total := 0;
     winCommissionStockholder_total := 0;
     winCommissionAgent_total := 0;
     winCommissionMember_total := 0;

     rateChief_total := 0;
     rateBranch_total := 0;
     rateGenAgent_total := 0;
     rateStockholder_total := 0;
     rateAgent_total := 0;

     moneyRateChief_total := 0;
     moneyRateBranch_total := 0;
     moneyRateGenAgent_total := 0;
     moneyRateStockholder_total := 0;
     moneyRateAgent_total := 0;

     subordinateChief_total := 0;
     subordinateBranch_total := 0;
     subordinateStockholder_total := 0;
     subordinateGenAgent_total := 0;
     subordinateAgent_total := 0;

     -- 初始化占成设置值相关数据
     recNum_total := 0;
     --rate_total := 0;
     --rateChiefSet_total := 0;
     --rateBranchSet_total := 0;
     --rateStockholderSet_total := 0;
     --rateGenAgentSet_total := 0;
     --rateAgentSet_total := 0;

     -- 1.3 删除临时表中的数据
     DELETE FROM TEMP_DELIVERYREPORT WHERE PARENT_ID = userID;

     -- 2.1 查询总监对应的分公司信息
     declare
     cursor branch_cursor
     IS
     SELECT * FROM
         (TB_BRANCH_STAFF_EXT ext INNER JOIN TB_FRAME_MANAGER_STAFF managerStaff ON ext.MANAGER_STAFF_ID = managerStaff.ID)
     WHERE
         ext.PARENT_STAFF = userID;

     BEGIN
          -- 2.1.1 循环处理所有的分公司信息
          FOR branch_pos IN branch_cursor LOOP
              -- 2.1.2 填充数据记录
              subordinate := branch_pos.ACCOUNT;         -- 下级登陆账号
              userName := branch_pos.CHS_NAME;           -- 用户名称
              subID := branch_pos.ID;                    -- 记录ID
              -- 2.1.3 初始化数据
              turnover := 0;                  -- 成交笔数
              amount := 0;                    -- 投注总额
              validAmount := 0;               -- 有效金额
              memberAmount := 0;              -- 会员输赢
              subordinateAmount := 0;         -- 应收下线
              memberBackWater := 0;           -- 会员退水
              winBackWater := 0;              -- 赚取退水
              realResult := 0;                -- 实占结果
              winBackWaterResult := 0;        -- 赚水后结果
              paySuperior := 0;               -- 应付上级
              rate := 0;                      -- 占成
              recNum := 0;

              -- 3.3 初始化存储上线所对应的计算后的佣金、占成、下级应收
              commissionBranch := 0;
              commissionGenAgent := 0;
              commissionStockholder := 0;
              commissionAgent := 0;
              commissionMember := 0;

              -- 赚取退水
              winCommissionBranch := 0;
              winCommissionGenAgent := 0;
              winCommissionStockholder := 0;
              winCommissionAgent := 0;
              winCommissionMember := 0;

              rateChief := 0;
              rateBranch := 0;
              rateGenAgent := 0;
              rateStockholder := 0;
              rateAgent := 0;

              moneyRateChief := 0;
              moneyRateBranch := 0;
              moneyRateGenAgent := 0;
              moneyRateStockholder := 0;
              moneyRateAgent := 0;

              subordinateChief := 0;
              subordinateBranch := 0;
              subordinateStockholder := 0;
              subordinateGenAgent := 0;
              subordinateAgent := 0;
              rate := 0;
              rateChiefSet := 0;
              rateBranchSet := 0;
              rateStockholderSet := 0;
              rateGenAgentSet := 0;
              rateAgentSet := 0;

              commissionBranchSet := 0;
              commissionStockholderSet := 0;
              commissionGenAgentSet := 0;
              commissionAgentSet := 0;

              -- 2.1.4 调用股东交收报表存储过程
              Delivery_Report_Branch(subID, LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag, statReportBranch);
              -- 读取代理交收报表存储过程所形成的数据
              declare
              cursor report_branch_cursor
              IS
              -- 过滤掉下级用户对应的补货信息
              -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
              SELECT * FROM TEMP_DELIVERYREPORT t WHERE t.USER_ID = branch_pos.ID AND t.USER_TYPE NOT IN ('g','f','e','d');

              BEGIN
              FOR report_branch_pos IN report_branch_cursor LOOP

                  -- 会员退水
                  memberBackWater := report_branch_pos.COMMISSION_BRANCH + report_branch_pos.COMMISSION_STOCKHOLDER + report_branch_pos.COMMISSION_GEN_AGENT + report_branch_pos.COMMISSION_AGENT + report_branch_pos.COMMISSION_MEMBER;
                  -- 应收下线（直接读取下线所计算的本级及上线占成结果，此处不能计算，因为不同的投注占成值不同）
                  -- subordinateAmount := report_branch_pos.RATE_CHIEF + report_branch_pos.RATE_BRANCH + report_branch_pos.RATE_STOCKHOLDER + report_branch_pos.RATE_GEN_AGENT;
                  subordinateAmount := report_branch_pos.SUBORDINATE_CHIEF;
                  -- 实占结果（直接读取下线所计算的股东占成结果，此处不能计算，因为不同的投注占成值不同）
                  realResult := report_branch_pos.RATE_CHIEF;
                  -- 赚取退水
                  winBackWater := 0;
                  -- 赚水后结果（实占结果-赚取退水）
                  winBackWaterResult := winBackWaterResult + realResult - winBackWater;
                  -- 应付上线（应收下线－赚水后结果）
                  paySuperior := subordinateAmount - winBackWaterResult;

                  moneyRateChief_total := moneyRateChief_total + report_branch_pos.MONEY_RATE_CHIEF;
                  moneyRateBranch_total := moneyRateBranch_total + report_branch_pos.MONEY_RATE_BRANCH;
                  moneyRateGenAgent_total := moneyRateGenAgent_total + report_branch_pos.MONEY_RATE_GEN_AGENT;
                  moneyRateStockholder_total := moneyRateStockholder_total + report_branch_pos.MONEY_RATE_STOCKHOLDER;
                  moneyRateAgent_total := moneyRateAgent_total + report_branch_pos.MONEY_RATE_AGENT;

                  subordinateChief := report_branch_pos.SUBORDINATE_CHIEF;
                  subordinateBranch := report_branch_pos.SUBORDINATE_BRANCH;
                  subordinateStockholder := report_branch_pos.SUBORDINATE_STOCKHOLDER;
                  subordinateGenAgent := report_branch_pos.SUBORDINATE_GEN_AGENT;
                  subordinateAgent := report_branch_pos.SUBORDINATE_AGENT;

                  -- 占成设置值
                  rateChiefSet := report_branch_pos.RATE_CHIEF_SET;
                  rateBranchSet := report_branch_pos.RATE_BRANCH_SET;
                  rateStockholderSet := report_branch_pos.RATE_STOCKHOLDER_SET;
                  rateGenAgentSet := report_branch_pos.RATE_GEN_AGENT_SET;
                  rateAgentSet := report_branch_pos.RATE_AGENT_SET;

                  -- 退水设置值
                  commissionBranchSet := report_branch_pos.COMMISSION_BRANCH_SET;
                  commissionStockholderSet := report_branch_pos.COMMISSION_STOCKHOLDER_SET;
                  commissionGenAgentSet := report_branch_pos.COMMISSION_GEN_AGENT_SET;
                  commissionAgentSet := report_branch_pos.COMMISSION_AGENT_SET;

                  rate := report_branch_pos.RATE_CHIEF_SET;
                  -- 赋值，TODO 考虑如果补货数据在投注数据之前出现是否会有问题
                  turnover := report_branch_pos.TURNOVER;          -- 成交笔数
                  amount := report_branch_pos.AMOUNT;              -- 投注总额
                  validAmount := report_branch_pos.VALID_AMOUNT;   -- 有效金额
                  memberAmount := report_branch_pos.MEMBER_AMOUNT; -- 会员输赢

                  -- 2.1.4.1 数据插入临时表
                  INSERT INTO TEMP_DELIVERYREPORT
                      (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
                      COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
                      SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
                      RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
                      COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
                      MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
                  VALUES (
                      userID,
                      branch_pos.ID,
                      '3',
                      branch_pos.ACCOUNT,
                      branch_pos.CHS_NAME,
                      turnover,
                      amount,
                      validAmount,
                      memberAmount,
                      memberBackWater,
                      subordinateAmount,
                      winBackWater,
                      realResult,
                      winBackWaterResult,
                      paySuperior,
                      report_branch_pos.COMMISSION_BRANCH, report_branch_pos.COMMISSION_GEN_AGENT, report_branch_pos.COMMISSION_STOCKHOLDER, report_branch_pos.COMMISSION_AGENT, report_branch_pos.COMMISSION_MEMBER, report_branch_pos.WIN_COMMISSION_BRANCH, report_branch_pos.WIN_COMMISSION_GEN_AGENT, report_branch_pos.WIN_COMMISSION_STOCKHOLDER, report_branch_pos.WIN_COMMISSION_AGENT, report_branch_pos.WIN_COMMISSION_MEMBER, report_branch_pos.RATE_CHIEF, report_branch_pos.RATE_BRANCH, report_branch_pos.RATE_GEN_AGENT, report_branch_pos.RATE_STOCKHOLDER, report_branch_pos.RATE_AGENT,
                      rate,
                      subordinateChief, subordinateBranch, subordinateStockholder, subordinateGenAgent, subordinateAgent,
                      rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
                      commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
                      moneyRateChief, moneyRateBranch, moneyRateStockholder, moneyRateGenAgent, moneyRateAgent
                      );

                   -- 2.1.4.2 累加总额
                   turnover_total := turnover_total + report_branch_pos.TURNOVER;
                   amount_total := amount_total + report_branch_pos.AMOUNT;
                   validAmount_total := validAmount_total + report_branch_pos.VALID_AMOUNT;
                   memberAmount_total := memberAmount_total + report_branch_pos.MEMBER_AMOUNT;
                   memberBackWater_total := memberBackWater_total + memberBackWater;
                   subordinateAmount_total := subordinateAmount_total + subordinateAmount;
                   winBackWater_total := winBackWater_total + winBackWater;
                   realResult_total := realResult_total + realResult;
                   winBackWaterResult_total := winBackWaterResult_total + winBackWaterResult;
                   paySuperior_total := paySuperior_total + paySuperior;

                   -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
                   commissionBranch_total := commissionBranch_total + report_branch_pos.COMMISSION_BRANCH;
                   commissionGenAgent_total := commissionGenAgent_total + report_branch_pos.COMMISSION_GEN_AGENT;
                   commissionStockholder_total := commissionStockholder_total + report_branch_pos.COMMISSION_STOCKHOLDER;
                   commissionAgent_total := commissionAgent_total + report_branch_pos.COMMISSION_AGENT;
                   commissionMember_total := commissionMember_total + report_branch_pos.COMMISSION_MEMBER;

                   winCommissionBranch_total := winCommissionBranch_total + report_branch_pos.WIN_COMMISSION_BRANCH;
                   winCommissionGenAgent_total := winCommissionGenAgent_total + report_branch_pos.WIN_COMMISSION_GEN_AGENT;
                   winCommissionStockholder_total := winCommissionStockholder_total + report_branch_pos.WIN_COMMISSION_STOCKHOLDER;
                   winCommissionAgent_total := winCommissionAgent_total + report_branch_pos.WIN_COMMISSION_AGENT;
                   winCommissionMember_total := winCommissionMember_total + report_branch_pos.WIN_COMMISSION_MEMBER;

                   rateChief_total := rateChief_total + report_branch_pos.RATE_CHIEF;
                   rateBranch_total := rateBranch_total + report_branch_pos.RATE_BRANCH;
                   rateGenAgent_total := rateGenAgent_total + report_branch_pos.RATE_GEN_AGENT;
                   rateStockholder_total := rateStockholder_total + report_branch_pos.RATE_STOCKHOLDER;
                   rateAgent_total := rateAgent_total + report_branch_pos.RATE_AGENT;

                   -- 实占注额
                   moneyRateChief_total := moneyRateChief_total + moneyRateChief;
                   moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
                   moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
                   moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
                   moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

                   subordinateChief_total := subordinateChief_total + subordinateChief;
                   subordinateBranch_total := subordinateBranch_total + subordinateBranch;
                   subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
                   subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
                   subordinateAgent_total := subordinateAgent_total + subordinateAgent;

                   -- 占成设置值
                   recNum_total := recNum_total + 1;
                   --rate_total := rate_total + rate;
                   --rateChiefSet_total := rateChiefSet_total + rateChiefSet;
                   --rateBranchSet_total := rateBranchSet_total + rateBranchSet;
                   --rateStockholderSet_total := rateStockholderSet_total + rateStockholderSet;
                   --rateGenAgentSet_total := rateGenAgentSet_total + rateGenAgentSet;
                   --rateAgentSet_total := rateAgentSet_total + rateAgentSet;

                   -- 2.1.4.3 删除无效临时数据
                   DELETE FROM TEMP_DELIVERYREPORT t WHERE t.PARENT_ID = branch_pos.ID;
              END LOOP;
              END;
          END LOOP;
      END;

      /******** 总监对应的直属会员数据 开始 ********/
      -- 调用直属会员交收报表存储过程
      Delivery_Report_Dir_Member(userID, '2', LOTTERY1688Type, playType, periodsNum, startDate, endDate, resultFlag);
      -- 查询直属会员统计数据
      declare
         cursor dir_total_cursor
         IS
         SELECT * FROM
             TEMP_DELIVERYREPORT report
         WHERE
             report.USER_ID = userID AND report.USER_TYPE = 'Z';
      BEGIN
          -- 累加直属会员统计数据
          FOR dir_total_pos IN dir_total_cursor LOOP
               -- 2.1.4.2 累加总额
               turnover_total := turnover_total + dir_total_pos.TURNOVER;
               amount_total := amount_total + dir_total_pos.AMOUNT;
               validAmount_total := validAmount_total + dir_total_pos.VALID_AMOUNT;
               memberAmount_total := memberAmount_total + dir_total_pos.MEMBER_AMOUNT;
               memberBackWater_total := memberBackWater_total + dir_total_pos.MEMBER_BACK_WATER;
               subordinateAmount_total := subordinateAmount_total + dir_total_pos.SUBORDINATE_AMOUNT;
               winBackWater_total := winBackWater_total + dir_total_pos.WIN_BACK_WATER;
               realResult_total := realResult_total + dir_total_pos.REAL_RESULT;
               winBackWaterResult_total := winBackWaterResult_total + dir_total_pos.WIN_BACK_WATER_RESULT;
               paySuperior_total := paySuperior_total + dir_total_pos.PAY_SUPERIOR;

               -- 2.1.5. 读取上线所对应的计算后的佣金、占成（总额）
               commissionBranch_total := commissionBranch_total + dir_total_pos.COMMISSION_BRANCH;
               commissionGenAgent_total := commissionGenAgent_total + dir_total_pos.COMMISSION_GEN_AGENT;
               commissionStockholder_total := commissionStockholder_total + dir_total_pos.COMMISSION_STOCKHOLDER;
               commissionAgent_total := commissionAgent_total + dir_total_pos.COMMISSION_AGENT;
               commissionMember_total := commissionMember_total + dir_total_pos.COMMISSION_MEMBER;

               winCommissionBranch_total := winCommissionBranch_total + dir_total_pos.WIN_COMMISSION_BRANCH;
               winCommissionGenAgent_total := winCommissionGenAgent_total + dir_total_pos.WIN_COMMISSION_GEN_AGENT;
               winCommissionStockholder_total := winCommissionStockholder_total + dir_total_pos.WIN_COMMISSION_STOCKHOLDER;
               winCommissionAgent_total := winCommissionAgent_total + dir_total_pos.WIN_COMMISSION_AGENT;
               winCommissionMember_total := winCommissionMember_total + dir_total_pos.WIN_COMMISSION_MEMBER;

               rateChief_total := rateChief_total + dir_total_pos.RATE_CHIEF;
               rateBranch_total := rateBranch_total + dir_total_pos.RATE_BRANCH;
               rateGenAgent_total := rateGenAgent_total + dir_total_pos.RATE_GEN_AGENT;
               rateStockholder_total := rateStockholder_total + dir_total_pos.RATE_STOCKHOLDER;
               rateAgent_total := rateAgent_total + dir_total_pos.RATE_AGENT;

               moneyRateChief_total := moneyRateChief_total + dir_total_pos.MONEY_RATE_CHIEF;
               moneyRateBranch_total := moneyRateBranch_total + dir_total_pos.MONEY_RATE_BRANCH;
               moneyRateGenAgent_total := moneyRateGenAgent_total + dir_total_pos.MONEY_RATE_GEN_AGENT;
               moneyRateStockholder_total := moneyRateStockholder_total + dir_total_pos.MONEY_RATE_STOCKHOLDER;
               moneyRateAgent_total := moneyRateAgent_total + dir_total_pos.MONEY_RATE_AGENT;

               subordinateChief_total := subordinateChief_total + dir_total_pos.SUBORDINATE_CHIEF;
               subordinateBranch_total := subordinateBranch_total + dir_total_pos.SUBORDINATE_BRANCH;
               subordinateStockholder_total := subordinateStockholder_total + dir_total_pos.SUBORDINATE_STOCKHOLDER;
               subordinateGenAgent_total := subordinateGenAgent_total + dir_total_pos.SUBORDINATE_GEN_AGENT;
               subordinateAgent_total := subordinateAgent_total + dir_total_pos.SUBORDINATE_AGENT;

               -- 占成设置值
               recNum_total := recNum_total + 1;
               --rate_total := rate_total + dir_total_pos.RATE;
               --rateChiefSet_total := rateChiefSet_total + dir_total_pos.RATE_CHIEF_SET;
               --rateBranchSet_total := rateBranchSet_total + dir_total_pos.RATE_BRANCH_SET;
               --rateStockholderSet_total := rateStockholderSet_total + dir_total_pos.RATE_STOCKHOLDER_SET;
               --rateGenAgentSet_total := rateGenAgentSet_total + dir_total_pos.RATE_GEN_AGENT_SET;
               --rateAgentSet_total := rateAgentSet_total + dir_total_pos.RATE_AGENT_SET;

               -- 2.1.4.3 删除无效临时数据
               DELETE FROM TEMP_DELIVERYREPORT report WHERE report.USER_ID = userID AND report.USER_TYPE = 'Z';
          END LOOP;
      END;

      /******** 总监对应的直属会员数据 结束 ********/

      /******** 补货数据 开始 ********/
      -- 6.2 查询补货数据（补货后续需要区分玩法类型查询）
      sql_replenish := 'SELECT * FROM TB_REPLENISH WHERE REPLENISH_USER_ID = ' || userID || ' AND BETTING_DATE BETWEEN to_date(''' || startDate || ''',''yyyy-mm-dd hh24:mi:ss'') AND to_date(''' || endDate || ''',''yyyy-mm-dd hh24:mi:ss'')';
      -- 只查询结算了的补货数据
      sql_replenish := sql_replenish || ' AND WIN_STATE IN (''1'',''2'',''3'') ';

      IF playType IS NOT NULL THEN
         sql_replenish := sql_replenish || ' AND TYPE_CODE IN (' || playType || ')';
      END IF;

      -- 判断彩票种类
      IF LOTTERY1688Type = 'GDKLSF' THEN
         -- 广东
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''GDKLSF_%'' ';
      END IF;
      IF LOTTERY1688Type = 'HKLHC' THEN
         -- 香港
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''HK_%'' ';
      END IF;
      IF LOTTERY1688Type = 'CQSSC' THEN
         -- 重庆
         sql_replenish := sql_replenish || ' AND TYPE_CODE LIKE ''CQSSC_%'' ';
      END IF;

      -- 初始化
      turnover_replenish := 0;                         -- 补货笔数
      amount_replenish := 0;                           -- 补货投注总额
      validAmount_replenish :=0;                       -- 补货有效金额
      amount_win_replenish := 0;                       -- 补货输赢
      backWater_replenish := 0;                        -- 补货退水
      backWaterResult_replenish := 0;                  -- 退水后结果
      backWater_replenish_temp := 0;                   -- 补货输赢（临时）
      amount_win_replenish_temp := 0;                  -- 补货输赢（临时）

      -- 退水相关数据
      commissionBranch := 0;
      commissionStockholder := 0;
      commissionGenAgent := 0;
      commissionAgent := 0;

      winCommissionBranch := 0;
      winCommissionGenAgent := 0;
      winCommissionStockholder := 0;
      winCommissionAgent := 0;

      -- 实占结果
      rateChief := 0;
      rateBranch := 0;
      rateGenAgent := 0;
      rateStockholder := 0;
      rateAgent := 0;

      -- 实占注额
      moneyRateChief := 0;
      moneyRateBranch := 0;
      moneyRateGenAgent := 0;
      moneyRateStockholder := 0;
      moneyRateAgent := 0;

      -- 应收下线
      subordinateChief := 0;
      subordinateBranch := 0;
      subordinateGenAgent := 0;
      subordinateStockholder := 0;
      subordinateAgent := 0;

      -- 执行查询，打开游标
      OPEN cursor_replenish
      FOR
      sql_replenish;

      BEGIN
           LOOP
           FETCH cursor_replenish INTO member_pos_replenish;
               -- 无数据则退出
               IF (cursor_replenish%found = false) THEN
                   EXIT;
               END IF;

               -- 累加补货总额
               amount_replenish := amount_replenish + member_pos_replenish.MONEY;

               -- 累加补货笔数
               turnover_replenish := turnover_replenish + 1;

               -- 补货输赢，对应该代理所有补货的总和(不计退水)
               IF (member_pos_replenish.WIN_STATE = 1) THEN
                  -- 累加“中奖”的投注额
                  amount_win_replenish := amount_win_replenish + member_pos_replenish.WIN_AMOUNT;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_CHIEF / 100;
                  amount_win_replenish_temp := member_pos_replenish.WIN_AMOUNT;
               END IF;
               IF (member_pos_replenish.WIN_STATE = 2) THEN
                  -- 减去“未中奖”的投注额
                  amount_win_replenish := amount_win_replenish - member_pos_replenish.MONEY;
                  backWater_replenish_temp := member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_CHIEF / 100;
                  amount_win_replenish_temp := - member_pos_replenish.MONEY;
               END IF;

               -- 补货有效金额，不计算打和
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  validAmount_replenish := validAmount_replenish + member_pos_replenish.MONEY;
               END IF;
               -- TODO 退水需要补全所有级别的退水信息
               -- 补货退水（代理佣金*投注额，除了打和，其他的都要退水）
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  backWater_replenish := backWater_replenish + backWater_replenish_temp;

                  --rateAgent := rateAgent + (memberAmount_temp + (member_pos_gdklsf.MONEY * member_pos_gdklsf.COMMISSION_MEMBER / 100)) * member_pos_gdklsf.RATE_AGENT/100;
               END IF;

               -- 补货实占注额
               IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  -- 实占注额（有效的投注金额 * 占成%）
                  moneyRateChief := moneyRateChief + (member_pos_replenish.MONEY * member_pos_replenish.RATE_CHIEF / 100);
                  moneyRateBranch := moneyRateBranch + (member_pos_replenish.MONEY * member_pos_replenish.RATE_BRANCH / 100);
                  moneyRateStockholder := moneyRateStockholder + (member_pos_replenish.MONEY * member_pos_replenish.RATE_STOCKHOLDER / 100);
                  moneyRateGenAgent := moneyRateGenAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_GEN_AGENT / 100);
                  moneyRateAgent := moneyRateAgent + (member_pos_replenish.MONEY * member_pos_replenish.RATE_AGENT / 100);
               END IF;

               -- 打和则不计算佣金（退水）（当做直属会员处理）
               -- IF (member_pos_replenish.WIN_STATE = 1 OR member_pos_replenish.WIN_STATE = 2) THEN
                  -- commissionBranch := commissionBranch + (member_pos_replenish.MONEY * (member_pos_replenish.COMMISSION_BRANCH - 0) / 100);

                  -- 实占结果（退水后结果，也即是 退水 + 输赢）*占成%
                  -- rateChief := rateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * member_pos_replenish.RATE_CHIEF/100;

                  -- 各级应收下线
                  -- subordinateChief := subordinateChief + (amount_win_replenish_temp + (member_pos_replenish.MONEY * member_pos_replenish.COMMISSION_BRANCH / 100)) * (1);
               -- END IF;

           END LOOP;
           CLOSE cursor_replenish;
      END;

      -- 补货退水后结果（退水 + 输赢）
      backWaterResult_replenish := backWater_replenish + amount_win_replenish;

      -- 4. 补货数据插入临时表（处理数据类型）（数据对应的 USER_TYPE 为实际值 + a，即如果实际值为1，则填充值 b，实际值为0，则填充a）
      -- c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
      IF (turnover_replenish > 0) THEN
        INSERT INTO TEMP_DELIVERYREPORT
              (PARENT_ID, USER_ID, USER_TYPE, RECORD_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, WIN_BACK_WATER_RESULT)
        VALUES
              (userID, userID, 'c', '1', '补货', '补货', turnover_replenish, amount_replenish, validAmount_replenish, amount_win_replenish, backWater_replenish, backWaterResult_replenish);
      END IF;

      -- 5. 累加总额（增加补货数据）
      turnover_total := turnover_total + turnover_replenish;
      amount_total := amount_total + amount_replenish;
      validAmount_total := validAmount_total + validAmount_replenish;
      memberAmount_total := memberAmount_total + amount_win_replenish;
      memberBackWater_total := memberBackWater_total + backWater_replenish;
      winBackWaterResult_total := winBackWaterResult_total + backWaterResult_replenish;

      -- 6. 累积补货上线所对应的计算后的佣金、占成（总额）
      commissionBranch_total := commissionBranch_total + commissionBranch;
      commissionGenAgent_total := commissionGenAgent_total + commissionGenAgent;
      commissionStockholder_total := commissionStockholder_total + commissionStockholder;
      commissionAgent_total := commissionAgent_total + commissionAgent;

      winCommissionBranch_total := winCommissionBranch_total + winCommissionBranch;
      winCommissionGenAgent_total := winCommissionGenAgent_total + winCommissionGenAgent;
      winCommissionStockholder_total := winCommissionStockholder_total + winCommissionStockholder;
      winCommissionAgent_total := winCommissionAgent_total + winCommissionAgent;

      rateChief_total := rateChief_total + rateChief;
      rateBranch_total := rateBranch_total + rateBranch;
      rateGenAgent_total := rateGenAgent_total + rateGenAgent;
      rateStockholder_total := rateStockholder_total + rateStockholder;
      rateAgent_total := rateAgent_total + rateAgent;

      -- 实占注额
      moneyRateChief_total := moneyRateChief_total + moneyRateChief;
      moneyRateBranch_total := moneyRateBranch_total + moneyRateBranch;
      moneyRateGenAgent_total := moneyRateGenAgent_total + moneyRateGenAgent;
      moneyRateStockholder_total := moneyRateStockholder_total + moneyRateStockholder;
      moneyRateAgent_total := moneyRateAgent_total + moneyRateAgent;

      subordinateChief_total := subordinateChief_total + subordinateChief;
      subordinateBranch_total := subordinateBranch_total + subordinateBranch;
      subordinateGenAgent_total := subordinateGenAgent_total + subordinateGenAgent;
      subordinateStockholder_total := subordinateStockholder_total + subordinateStockholder;
      subordinateAgent_total := subordinateAgent_total + subordinateAgent;
      /******** 补货数据 结束 ********/

      -- 4. 总额数据插入临时表（为方便排序，总额数据对应的 USER_TYPE 为实际值 + A，即如果实际值为1，则填充值 B）
      -- C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
      IF (turnover_total > 0) THEN
         INSERT INTO TEMP_DELIVERYREPORT
            (PARENT_ID, USER_ID, USER_TYPE, SUBORDINATE, USER_NAME, TURNOVER, AMOUNT, VALID_AMOUNT, MEMBER_AMOUNT, MEMBER_BACK_WATER, SUBORDINATE_AMOUNT, WIN_BACK_WATER, REAL_RESULT, WIN_BACK_WATER_RESULT, PAY_SUPERIOR,
            COMMISSION_BRANCH, COMMISSION_GEN_AGENT, COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, WIN_COMMISSION_BRANCH, WIN_COMMISSION_GEN_AGENT, WIN_COMMISSION_STOCKHOLDER, WIN_COMMISSION_AGENT, WIN_COMMISSION_MEMBER, RATE_CHIEF, RATE_BRANCH, RATE_GEN_AGENT, RATE_STOCKHOLDER, RATE_AGENT, RATE,
            SUBORDINATE_CHIEF, SUBORDINATE_BRANCH, SUBORDINATE_STOCKHOLDER, SUBORDINATE_GEN_AGENT, SUBORDINATE_AGENT,
            RATE_CHIEF_SET, RATE_BRANCH_SET, RATE_STOCKHOLDER_SET, RATE_GEN_AGENT_SET, RATE_AGENT_SET,
            COMMISSION_BRANCH_SET, COMMISSION_STOCKHOLDER_SET, COMMISSION_GEN_AGENT_SET, COMMISSION_AGENT_SET,
            MONEY_RATE_CHIEF, MONEY_RATE_BRANCH, MONEY_RATE_STOCKHOLDER, MONEY_RATE_GEN_AGENT, MONEY_RATE_AGENT)
         VALUES
            (userID, userID, 'C', '', '合计：', turnover_total, amount_total, validAmount_total, memberAmount_total, memberBackWater_total, subordinateAmount_total, winBackWater_total, realResult_total, winBackWaterResult_total, paySuperior_total,
            commissionBranch_total, commissionGenAgent_total, commissionStockholder_total, commissionAgent_total, commissionMember_total, winCommissionBranch_total, winCommissionGenAgent_total, winCommissionStockholder_total, winCommissionAgent_total, winCommissionMember_total, rateChief_total, rateBranch_total, rateGenAgent_total, rateStockholder_total, rateAgent_total, rate,
            subordinateChief_total, subordinateBranch_total, subordinateStockholder_total, subordinateGenAgent_total, subordinateAgent_total,
            rateChiefSet, rateBranchSet, rateStockholderSet, rateGenAgentSet, rateAgentSet,
            commissionBranchSet, commissionStockholderSet, commissionGenAgentSet, commissionAgentSet,
            moneyRateChief_total, moneyRateBranch_total, moneyRateStockholder_total, moneyRateGenAgent_total, moneyRateAgent_total);
      END IF;
      -- 5. 从临时表中查询数据
      OPEN statReportBranch
      FOR
      SELECT * FROM TEMP_DELIVERYREPORT t ORDER BY t.USER_TYPE;

      -- 6. 设置结果（成功，正常结束）
      resultFlag := 0;
END;
/


spool off
