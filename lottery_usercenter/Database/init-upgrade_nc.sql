--3张基础表start
--TB_DEFAULT_PLAY_ODDS
insert into TB_DEFAULT_PLAY_ODDS
  (ID,PLAY_TYPE_CODE,ODDS,ODDS_TYPE,ODDS_TYPE_X,AUTO_ODDS_QUOTAS,AUTO_ODDS,LOWEST_ODDS,BIGEST_ODDS,CUT_ODDS_B,CUT_ODDS_C,UPDATE_DATE,UPDATE_USER,REMARK)
SELECT seq_TB_DEFAULT_PLAY_ODDS.nextval,REPLACE( PLAY_TYPE_CODE, 'GDKLSF', 'NC'),ODDS,REPLACE( ODDS_TYPE, 'GDKLSF', 'NC'),REPLACE( ODDS_TYPE_X, 'GDKLSF', 'NC'),AUTO_ODDS_QUOTAS,
   AUTO_ODDS,LOWEST_ODDS,BIGEST_ODDS,CUT_ODDS_B,CUT_ODDS_C,UPDATE_DATE,UPDATE_USER,REMARK from TB_DEFAULT_PLAY_ODDS A WHERE A.PLAY_TYPE_CODE like 'GDKLSF%';

--TB_PLAY_TYPE
insert into TB_PLAY_TYPE
  (ID,TYPE_CODE,TYPE_NAME,PLAY_TYPE,PLAY_SUB_TYPE,PLAY_FINAL_TYPE,ODDS_TYPE,STATE,SUB_TYPE_NAME,FINAL_TYPE_NAME,COMMISSION_TYPE,REMARK,AUTO_REPLENISH_TYPE,DISPLAY_ORDER,COMMISSION_TYPE_DISPLAY_ORDER)
SELECT seq_tb_play_type.nextval,REPLACE( TYPE_CODE, 'GDKLSF', 'NC'),'幸运农场','NC',PLAY_SUB_TYPE,REPLACE( PLAY_FINAL_TYPE, 'GDKLSF', 'NC'),REPLACE( ODDS_TYPE, 'GDKLSF', 'NC'),
   STATE,SUB_TYPE_NAME,FINAL_TYPE_NAME,REPLACE( COMMISSION_TYPE, 'GD', 'NC'),REMARK,REPLACE( AUTO_REPLENISH_TYPE, 'GDKLSF', 'NC'),DISPLAY_ORDER,COMMISSION_TYPE_DISPLAY_ORDER
from TB_PLAY_TYPE A WHERE A.PLAY_TYPE='GDKLSF';

--TB_PLAY_WIN_INFO
insert into TB_PLAY_WIN_INFO
  (ID, TYPE_CODE, PLAY_TYPE, PERIODS_NUM, WIN, UPDATE_TIME)
SELECT seq_TB_PLAY_WIN_INFO.nextval,REPLACE( TYPE_CODE, 'GDKLSF', 'NC'),REPLACE( PLAY_TYPE, 'GDKLSF', 'NC'),PERIODS_NUM, WIN, UPDATE_TIME
from TB_PLAY_WIN_INFO A WHERE A.TYPE_CODE like 'GDKLSF%';
--3张基础表end

--tb_user_commission_default
--注意：这里的XXXXXX是总监ID
insert into tb_user_commission_default
  (ID, USER_ID, USER_TYPE, PLAY_TYPE, PLAY_FINAL_TYPE, COMMISSION_A, COMMISSION_B, COMMISSION_C, BETTING_QUOTAS, ITEM_QUOTAS, TOTAL_QUOTAS, LOWEST_QUOTAS, WIN_QUOTAS, LOSE_QUOTAS, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME)
SELECT seq_tb_user_commission_default.nextval,USER_ID, USER_TYPE, '6', REPLACE( PLAY_FINAL_TYPE, 'GD', 'NC'), COMMISSION_A, COMMISSION_B, COMMISSION_C, BETTING_QUOTAS, ITEM_QUOTAS, TOTAL_QUOTAS, LOWEST_QUOTAS, WIN_QUOTAS, LOSE_QUOTAS, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME
from tb_user_commission_default t where t.play_type=1 and t.user_id=XXXXXX;

--tb_play_amount
Insert into tb_play_amount(id,type_code,play_type,shops_Code,money_amount) 
     select seq_tb_play_amount.nextval,REPLACE(type_code, 'GDKLSF', 'NC'), 'NC','9876', 0 from tb_play_type
     where play_type='NC';

--tb_shops_play_odds
Insert into tb_shops_play_odds a(id,shops_code,play_type_code,odds_type_x,odds_type,real_odds,real_update_date,real_update_user,state)
        select seq_tb_shops_play_odds.nextval,'9876',REPLACE(play_type_code, 'GDKLSF', 'NC'), REPLACE(odds_type_x, 'GDKLSF', 'NC'),REPLACE(odds_type, 'GDKLSF', 'NC'),
        b.odds,sysdate,'800001020',0 from tb_default_play_odds b  where b.play_type_code like 'NC%';
         
--tb_open_play_odds
Insert into tb_open_play_odds(id,shops_code,opening_update_date,opening_update_user,create_user,create_time,
        auto_odds_quotas,auto_odds,odds_type,lowest_odds,opening_odds,bigest_odds,cut_odds_b,cut_odds_c)
         select seq_tb_open_play_odds.nextval,'9876',sysdate,800001020,800001020, sysdate,d.*
         from (select distinct b.auto_odds_quotas, b.auto_odds,b.odds_type,b.lowest_odds,b.odds,b.bigest_odds,b.cut_odds_b,b.cut_odds_c
         from tb_default_play_odds b where b.play_type_code like 'NC%') d         
         
--关于自动补货设置的初始化，注意781的商铺ID要根据实际修改
Insert into tb_replenish_auto(ID, SHOPS_ID, TYPE, MONEY_LIMIT, CREATE_USER, CREATE_TIME, TYPE_CODE, MONEY_REP, STATE, CREATE_USER_TYPE) 
     select seq_tb_replenish_auto.nextval,SHOPS_ID, 'NC', 0, CREATE_USER, CREATE_TIME,REPLACE(TYPE_CODE, 'GDKLSF', 'NC'), 2, '0', '3' from tb_replenish_auto t
     where t.shops_id=781 and t.type like 'GD%' ;
         
         