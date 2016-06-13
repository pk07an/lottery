prompt PL/SQL Developer import file
prompt Created on 2013年9月8日 by Administrator
set feedback off
set define off
prompt Loading TB_FRAME_FUNCTION...
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (112, 'BUSSINESS_USER_MANAGE', '业务用户管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 60, '管理总监、总代理、会员等用户信息');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (116, 'BRANCH_MANAGE_MODIFY', '编辑', '0', '/sysmenu/content.jsp', 113, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (118, 'STOCKHOLDER_USER_MANAGER_CREATE', '创建', '0', '/sysmenu/content.jsp', 117, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (119, 'STOCKHOLDER_USER_MANAGER_VIEW', '查看', '0', '/sysmenu/content.jsp', 117, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (120, 'STOCKHOLDER_USER_MANAGER_MODIFY', '修改', '0', '/sysmenu/content.jsp', 117, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (122, 'COMMISSION_MANAGE_SET', '设置', '0', '/sysmenu/content.jsp', 121, '/sysmenu/images/pub_3_1.gif', null, '/user/queryCommission.action;;/user/savaCommission.action');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (123, 'COMMISSION_MANAGE_MODIFY', '修改', '0', '/sysmenu/content.jsp', 121, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (127, 'SHOPS_INFO_MANAGE_MODIFY', '修改', '0', '/sysmenu/content.jsp', 124, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (128, 'LOTTERY', '开奖结果管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 80, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (129, 'LOTTERY_GD_VIEW', '广东快乐十分查看', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (134, 'MANAGER_STAFF_MANAGE', '总管用户管理', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (135, 'MANAGER_STAFF_MANAGE_MEMBER_VIEW', '直属会员查看', '0', '/sysmenu/content.jsp', 134, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (136, 'MEBER_LOTTERY', '会员投注', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 90, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (137, 'MEBER_LOTTERY_XGLHC', '香港六合彩', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (138, 'LOTTERY', '下注', '0', '/sysmenu/content.jsp', 137, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (139, 'MEBER_LOTTERY_GDKLSF', '广东快乐十分', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (355, 'SUBACCOUNT_DEL', '子帳號刪除', '0', '/sysmenu/content.jsp', 352, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (10, 'MANAGER_USER_MANAGE', '基础用户管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 40, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (33, 'MANAGER_USER_MANAGE_M_PWD', '修改密码', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (84, 'PARAM_MANAGE_VIEW_VALUE', '参数值查看', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (114, 'BRANCH_MANAGE_CREATE', '创建', '0', '/sysmenu/content.jsp', 113, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (121, 'COMMISSION_MANAGE', '退水设置', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (117, 'STOCKHOLDER_USER_MANAGER', '股东管理', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (125, 'SHOPS_INFO_MANAGE_CREATE', '注册', '0', '/sysmenu/content.jsp', 124, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (126, 'SHOPS_INFO_MANAGE_VIEW', '查看', '0', '/sysmenu/content.jsp', 124, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (141, 'MEBER_LOTTERY_CQSSC', '重庆时时彩', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (142, 'LOTTERY', '下注', '0', '/sysmenu/content.jsp', 141, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (143, 'SEARCH', '统计查询', '0', '/sysmenu/content.jsp', 139, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (144, 'SEARCH', '统计查询', '0', '/sysmenu/content.jsp', 141, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (153, 'GENAGENT_MANAGER_CREATE', '创建', '0', '/sysmenu/content.jsp', 152, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (154, 'GENAGENT_MANAGER_MODIFY', '修改', '0', '/sysmenu/content.jsp', 152, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (155, 'GENAGENT_MANAGER_VIEW', '查看', '0', '/sysmenu/content.jsp', 152, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (217, '重慶時時彩', '重慶時時彩', '0', '/sysmenu/content.jsp', 214, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (312, 'LOTTERY_HK_VIEW ', '香港六合彩查看', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (332, 'LOTTERY_MANAGER', '總管盤口設定', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (333, 'USER_PASSWORD_UPDATE', '后台密碼修改', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (353, 'SUBACCOUNT_CREATE', '子帳號添加', '0', '/sysmenu/content.jsp', 352, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (392, 'ROLE_MANAGE_DEL', '删除', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (472, 'STAT_REPORT', '报表统计', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (152, 'GENAGENT_MANAGER', '总代理', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (254, 'MEMBER_USER_MANAGE_PRIVATE', '私有授权', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (218, '補貨', '補貨', '1', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (253, 'MEMBER_USER_MANAGE_ROLES', '角色授权', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (255, 'MEMBER_USER_MANAGE_VIEW', '查看授权', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (256, 'MEMBER_USER_MANAGE_M_PWD', '修改密码', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (257, 'MEMBER_USER_MANAGE_VIEW', '查看', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (258, 'MEMBER_USER_MANAGE_LIST', '列表', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (272, 'PERIODS_MANAGER', '總監盘口管理', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (356, 'LOTTERY_MANAGER_修改', '總管盤口修改', '0', '/sysmenu/content.jsp', 332, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (452, 'BALANCE_MANAGER', '结算报表', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (473, 'JIAO_SHOU_BAO_BIAO', '交收报表', '0', '/sysmenu/content.jsp', 472, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5050, '进入管理页面', '进入管理页面', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5070, '开盘赔率', '开盘赔率', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5071, '进入开盘赔率页面', '进入开盘赔率页面', '0', '/sysmenu/content.jsp', 5070, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5072, '修改开盘赔率', '修改开盘赔率', '0', '/sysmenu/content.jsp', 5070, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5073, '管理二級菜單', '管理二級菜單', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5090, '北京赛车查看', '北京赛车查看', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5091, '修改北京賽車开奖结果', '修改北京賽車开奖结果', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5112, '实时赔率设置', '实时赔率设置', '0', '/sysmenu/content.jsp', 5111, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5130, '北京賽車', '北京賽車', '0', '/sysmenu/content.jsp', 214, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5170, '出貨會員', '出貨會員', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5171, '查看', '查看', '0', '/sysmenu/content.jsp', 5170, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5210, '擁金設定', '擁金設定', '0', '/sysmenu/content.jsp', 5170, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5230, '删除', '删除', '0', '/sysmenu/content.jsp', 5170, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5250, '北京赛车', '北京赛车', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5251, '统计查询', '统计查询', '0', '/sysmenu/content.jsp', 5250, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5252, '框架', '框架', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5253, '下注', '下注', '0', '/sysmenu/content.jsp', 5250, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5254, '总监', '总监', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5255, '出货会员', '出货会员', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5270, '备份', '备份', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5370, '快3开奖结果管理', '快3开奖结奖结果管理', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (492, 'CREDITINFORMATION', '后台信用资料', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (11, 'MEMBER_USER_MANAGE', '会员用户管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 50, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (12, 'PARAM_MANAGE', '参数维护', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 30, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (15, 'MANAGER_USER_MANAGE_LIST', '列表', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (32, 'MANAGER_USER_MANAGE_VIEW', '查看', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (34, 'FUNCTION_INFO_VIEW', '查看', '0', '/sysmenu/content.jsp', 13, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (77, 'ROLE_MANAGE_VIEW', '查看', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (79, 'PARAM_MANAGE_CREATE', '参数新增', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (85, 'PARAM_MANAGE_DEL_VALUE', '参数值删除', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (156, 'AGENT_MANAGER', '代理', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (157, 'AGENT_MANAGER_CREATE ', '创建', '0', '/sysmenu/content.jsp', 156, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (158, 'AGENT_MANAGER_UPDATE', '修改', '0', '/sysmenu/content.jsp', 156, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (159, 'AGENT_MANAGER_VIEW', '查看', '0', '/sysmenu/content.jsp', 156, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (172, 'MEMBER_MANAGER', '会员', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (175, 'MEMBER_MANAGER_CREATE', '创建', '0', '/sysmenu/content.jsp', 172, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (493, 'MANAGER_USER_DELETE', '删除', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (274, 'PERIODS_CREATE', '盘口设定', '0', '/sysmenu/content.jsp', 272, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4680, '帳單', 'HKLHC帳單', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4700, '总管日志', '总管日志', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, '总管日志');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4720, '注單搜索', '注單搜索', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4760, '香港六合彩賠率設置', '香港六合彩賠率設置', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4780, '系統初始設定-自動降賠', '系統初始設定-自動降賠', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4781, '操盤記錄', '操盤記錄', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4800, '補貨。。', '補貨。。', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4801, '廣東快樂十分賠率', '廣東快樂十分賠率', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4802, '重慶賠率', '重慶賠率', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4820, 'Temp', 'Temp', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4900, 'SHOPSDECLARATTON_POPUPMENUS', '是否弹出公告', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
commit;
prompt 100 records committed...
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4901, 'SHOPSDECLARATTON_MANAGER_VIEW', '查看', '0', '/sysmenu/content.jsp', 4500, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4940, '登录日志', '登录日志', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4960, 'warningStatement', '会员弹出是否同意页面', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (474, 'FEN_LEI_BAO_BIAO', '分类报表', '0', '/sysmenu/content.jsp', 472, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (1, 'root', '彩票投注系统', '0', null, 1, '/sysmenu/images/pub_3_1.gif', 0, '根节点，此数据在系统初始化时生成');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (14, 'DEMO', 'DEMO', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (133, 'LOTTERY_CQ_MODIFY', '重庆时时彩修改', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (73, 'ROLE_MANAGE', '角色管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 20, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (124, 'SHOPS_INFO_MANAGE', '商铺管理', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 70, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (131, 'LOTTERY_CQ_VIEW', '重庆时时彩查看', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (173, 'MEMBER_MANAGER_VIEW', '查看', '0', '/sysmenu/content.jsp', 172, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (213, 'MEMBER_MANAGER_IMMEDIATE_MEMBER ', '添加直属会员', '0', '/sysmenu/content.jsp', 172, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (214, '管理后台交易設定', '管理后台交易設定', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (215, '廣東快樂十分', '廣東快樂十分', '0', '/sysmenu/content.jsp', 214, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (273, 'xglhc', '香港六合彩', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (292, 'PERIODS_UPDATE', '盘口修改', '0', '/sysmenu/content.jsp', 272, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (313, 'LOTTERY_HK_UPDATE', '香港六合彩修改', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (432, 'MEMBER_PASSWORD_UPDATE ', '前台用户密码修改', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4540, 'MANAGER_SHOPSDECLARATTON', '总管后台公告管理', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5020, '总管报表查询', '总管报表查询', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5030, '補貨里的賠率操作', '補貨里的賠率操作', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, '補貨里的賠率操作');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5290, '未结算分类报表', '未结算分类报表', '0', '/sysmenu/content.jsp', 472, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5350, '快3', '快3', '0', '/sysmenu/content.jsp', 5110, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5110, '实时赔率', '实时赔率', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5111, '北京赛车', '北京赛车', '0', '/sysmenu/content.jsp', 5110, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5150, '北京賽車', '北京賽車', '0', '/sysmenu/content.jsp', 5072, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5190, '修改', '修改', '0', '/sysmenu/content.jsp', 5170, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5310, '快3', '快3', '0', '/sysmenu/content.jsp', 214, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5330, 'K3', 'K3', '0', '/sysmenu/content.jsp', 5072, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4580, 'LOTTERY_HISTORY_MANAGER', '总管历史数据修改', '0', '/sysmenu/content.jsp', 332, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4640, 'MANAGER_SHOPSDECLARATTON_DELETE ', '总管后台公告删除', '0', '/sysmenu/content.jsp', 4540, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4860, '交收报表-未结算', '交收报表-未结算', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4920, '自动补货', '自动补货', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4500, 'SHOPSDECLARATTON_MANAGER', '总监及管理者公告', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4560, '注單搜索', '注單搜索', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4620, '管理開獎結果查詢', '管理開獎結果查詢', '0', '/sysmenu/content.jsp', 100, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4660, 'MANAGER_SHOPSDECLARATTON_UPDATE', '总管后台公告修改', '0', '/sysmenu/content.jsp', 4540, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4740, '规则', '规则', '0', '/sysmenu/content.jsp', 136, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4840, 'marquee', '后台跑马灯', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, '后台跑马灯');
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4880, 'SHOPSDECLARATTON_MANAGER_UPDATE ', '总监修改', '0', '/sysmenu/content.jsp', 4500, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4881, 'SHOPSDECLARATTON_MANAGER_CREATE ', '总监创建', '0', '/sysmenu/content.jsp', 4500, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4882, 'SHOPSDECLARATTON_MANAGER_DELETE', '总监删除', '0', '/sysmenu/content.jsp', 4500, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4980, 'TOTALCREDITLINE', '总管后台可用信用额度', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5000, '自動補貨', '自動補貨', '0', '/sysmenu/content.jsp', 273, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4520, '總管會員管理', '總管會員管理', '0', '/sysmenu/content.jsp', 124, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (4600, 'MANAGER_SHOPSDECLARATTON_CREATE ', '总管后台公告创建', '0', '/sysmenu/content.jsp', 4540, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (140, 'LOTTERY', '下注', '0', '/sysmenu/content.jsp', 139, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (174, 'MEMBER_MANAGER_UPDATE', '修改', '0', '/sysmenu/content.jsp', 172, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (252, 'MEMBER_USER_MANAGE_CREATE', '新增', '0', '/sysmenu/content.jsp', 11, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (352, 'SUBACCOUNT＿MANAGER', '子帳號管理', '0', '/sysmenu/content.jsp', 101, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (354, 'SUBACCOUNT_UPDATE', '子帳號修改', '0', '/sysmenu/content.jsp', 352, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (372, 'monitorInfo', '监控信息', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (373, 'SESSION', '在线信息', '0', '/sysmenu/content.jsp', 372, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (412, '香港六合彩', '香港六合彩', '0', '/sysmenu/content.jsp', 214, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (52, 'FUNCTION_INFO_VIEW_MODIFY', '新增', '0', '/sysmenu/content.jsp', 13, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (75, 'ROLE_MANAGE_CREATE', '新增', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (74, 'ROLE_MANAGE_LIST', '列表', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (101, 'Lucky', 'ZWJ', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (99, 'temp', '临时使用', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (100, 'Eric', 'Eric', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (13, 'FUNCTION_INFO', '功能信息', '0', '/sysmenu/content.jsp', 1, '/sysmenu/images/pub_3_1.gif', 10, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (102, 'James', 'WYF', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (53, 'FUNCTION_INFO_MODIFY', '修改', '0', '/sysmenu/content.jsp', 13, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (113, 'BRANCH_MANAGE', '分公司管理', '0', '/sysmenu/content.jsp', 112, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (130, 'LOTTERY_GD_MODIFY', '广东快乐十分修改', '0', '/sysmenu/content.jsp', 128, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (54, 'FUNCTION_INFO_DEL', '删除', '0', '/sysmenu/content.jsp', 13, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (72, 'MANAGER_USER_MANAGE_CREATE', '新增', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (76, 'ROLE_MANAGE_MODIFY', '编辑', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (78, 'PARAM_MANAGE_LIST', '参数列表', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (80, 'PARAM_MANAGE_VIEW', '参数查看', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (81, 'PARAM_MANAGE_MODIFY', '参数修改', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (82, 'PARAM_MANAGE_CREATE_VALUE', '参数值新增', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (83, 'PARAM_MANAGE_MODIFY_VALUE', '参数值编辑', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (86, 'PARAM_MANAGE_DEL', '参数删除', '0', '/sysmenu/content.jsp', 12, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (87, 'USER_MANAGE_AUTHORIZ_ROLES', '角色授权', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (88, 'ROLE_MANAGE_AUTHORIZ', '角色授权', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (89, 'ROLE_MANAGE_AUTHORIZ_VIEW', '查看角色授权', '0', '/sysmenu/content.jsp', 73, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (90, 'USER_MANAGE_AUTHORIZ_PRIVATE', '私有授权', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (91, 'USER_MANAGE_AUTHORIZ_VIEW', '查看授权', '0', '/sysmenu/content.jsp', 10, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (103, 'Xavier', 'LXF', '0', '/sysmenu/content.jsp', 99, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (115, 'BRANCH_MANAGE_VIEW', '查看', '0', '/sysmenu/content.jsp', 113, '/sysmenu/images/pub_3_1.gif', null, null);
insert into TB_FRAME_FUNCTION (ID, FUNC_CODE, FUNC_NAME, FUNC_STATE, FUNC_URL, PARENT_FUNC, ICON_PATH, SORT_NUM, FUNC_DESC)
values (5390, '生成报表', '生成报表', '0', '/sysmenu/content.jsp', 472, '/sysmenu/images/pub_3_1.gif', null, null);
commit;
prompt 182 records loaded
prompt Loading TB_FRAME_ORG...
insert into TB_FRAME_ORG (ORGID, UPORGID, ADOUNAME, ORGNAM, SHORTNAME, SHOWORDER, ORGTYPE, ORGAREA, SUBORGNUM, SAPID, ISREALORG, CREATEDATE)
values (360000100, 360000100, '全球彩票中心', '全球彩票中心', '全球彩票中心', null, null, null, null, null, null, to_date('05-10-2012 18:18:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into TB_FRAME_ORG (ORGID, UPORGID, ADOUNAME, ORGNAM, SHORTNAME, SHOWORDER, ORGTYPE, ORGAREA, SUBORGNUM, SAPID, ISREALORG, CREATEDATE)
values (360000101, 360000100, '技术部', '技术部', '技术部', null, null, null, null, null, null, to_date('05-10-2012 18:18:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into TB_FRAME_ORG (ORGID, UPORGID, ADOUNAME, ORGNAM, SHORTNAME, SHOWORDER, ORGTYPE, ORGAREA, SUBORGNUM, SAPID, ISREALORG, CREATEDATE)
values (360000102, 360000100, '市场部', '市场部', '市场部', null, null, null, null, null, null, to_date('05-10-2012 18:18:03', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 3 records loaded
prompt Loading TB_FRAME_RESOURCE...
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6045, 119, 'queryStockcommission', 'queryStockcommission', '0', '0', null, '/user/queryStockcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6046, 120, 'saveStockcommission', 'saveStockcommission', '0', '0', null, '/user/saveStockcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6047, 155, 'queryGenAgentcommission', 'queryGenAgentcommission', '0', '0', null, '/user/queryGenAgentcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6048, 154, 'saveGenAgentcommission', 'saveGenAgentcommission', '0', '0', null, '/user/saveGenAgentcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6049, 159, 'queryAgentcommission', 'queryAgentcommission', '0', '0', null, '/user/queryAgentcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6052, 174, 'saveMembercommission', 'saveMembercommission', '0', '0', null, '/user/saveMembercommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6053, 112, 'userTree', 'userTree', '0', '0', null, '/user/userTree.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6054, 112, 'ajaxUpdateUserStatus', 'ajaxUpdateUserStatus', '0', '0', null, '/user/ajaxUpdateUserStatus.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6055, 112, 'ajaxparentCommission', 'ajaxparentCommission', '0', '0', null, '/user/ajaxparentCommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6071, 4720, 'queryBillAdmin', '注單搜索 总监', '0', '0', null, '/admin/queryBillAdmin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6072, 4720, 'ajaxDeleteBillSubmit', '注單搜索  删除注單', '0', '0', null, '/admin/ajaxDeleteBillSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6132, 133, 'modifyCqsscHistoryResult.action ', '修改重庆开奖结果', '0', '0', null, '/boss/modifyCqsscHistoryResult.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5932, 218, 'twoSideAndDragonRank.action', '補貨界面的兩面長龍統計', '0', '0', null, '/replenish/twoSideAndDragonRank.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5951, 218, 'backupDetail.action', '备份', '0', '0', null, '/replenish/backupDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6092, 5290, 'unClassList.action', '分类报表未结算报表', '0', '0', null, '/classreport/unClassList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6094, 5290, 'unClassRelenishDetailed', '补货明细', '0', '0', null, '/classreport/unClassRelenishDetailed.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6095, 473, 'settledListEric', '交收报表未结算报表', '0', '0', null, '/statreport/settledListEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6096, 473, 'settledDetailedListEric', '下注明细', '0', '0', null, '/statreport/settledDetailedListEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6112, 5073, 'loginFailureManager.action', 'loginFailureManager.action', '0', '0', null, '/sysmge/loginFailureManager.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6251, 5370, 'queryK3LotResult.action', '查看快3开奖结果', '0', '0', null, '/boss/queryK3LotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5811, 5190, 'updateUserOutReplenish.ation', '保存修改出货会员', '0', '0', null, '/user/updateUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5871, 218, 'ajaxFindReplenishPet.action', '查询某个用户有没有补货记录', '0', '0', null, '/replenish/ajaxFindReplenishPet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5873, 5230, 'deleteUserOutReplenish.action', '删除出货会员', '0', '0', null, '/user/deleteUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6073, 4720, 'ajaxUpdateBillSubmit', '注單搜索  修改注單', '0', '0', null, '/admin/ajaxUpdateBillSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6151, 5310, '快3交易设定修改', 'updateK3TradingSet.action ', '0', '0', null, '/admin/updateK3TradingSet.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6171, 5330, 'updateK3OpenOdds.action ', '更新K3开盘赔率', '0', '0', null, '/admin/updateK3OpenOdds.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6253, 5370, 'modifyK3HistoryResult.action', '修改快3历史开奖结果', '0', '0', null, '/boss/modifyK3HistoryResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5771, 5171, 'queryUserOutReplenish.ation', '查看出貨會員列表', '0', '0', null, '/user/queryUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5891, 218, 'replenishSetContent.action', '用于补货的内容页面刷新', '0', '0', null, '/replenish/replenishSetContent.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6039, 5254, 'chiefStaffRegister', 'chiefStaffRegister', '0', '0', null, '/member/chiefStaffRegister.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6074, 5270, 'backupDetailCheck', '备份-点击菜单时的判断是否要转圈', '0', '0', null, '/replenish/backupDetailCheck.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6191, 218, 'k3ReplenishEnter.action ', '快3补货界面菜单', '0', '0', null, '/replenish/k3ReplenishEnter.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6231, 5254, 'ajaxDeleteBillSubmitForChief', 'ajaxDeleteBillSubmitForChief', '0', '0', null, '/admin/ajaxDeleteBillSubmitForChief.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6271, 5390, 'queryReport.action', '生成报表', '0', '0', null, '/boss/queryReport.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6272, 5254, '注单注销', 'ajaxCancelBillSubmitForChief.action', '0', '0', null, '/admin/ajaxCancelBillSubmitForChief.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (90, 114, 'ACTION', 'ACTION', '0', '0', null, '/user/saveBranchStaff.action;;/user/saveBranch.action;;/user/ajaxQueryUserName.action;;/user/ajaxQueryAccountName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (91, 115, 'ACTION', 'ACTION', '0', '0', null, '/user/queryBranchStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (93, 118, 'ACTION', 'ACTION', '0', '0', null, '/user/saveStockholder.action;;/user/saveStock.action;;/user/ajaxQueryUserName.action;;/user/saveFindStockholder.action;;/user/queryStockholder.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (94, 119, 'ACTION', 'ACTION', '0', '0', null, '/user/queryStockholder.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (95, 120, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindByStockholder.action;;/user/updateByStockholder.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (96, 122, 'ACTION', 'ACTION', '0', '0', null, '/boss/queryCommissionDefault.action;;/boss/saveCommissionDefault.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (101, 127, 'ACTION', 'ACTION', '0', '0', null, '/boss/modifyShop.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (102, 128, 'ACTION', 'ACTION', '0', '0', null, '/boss/enterLotResultHistoryBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (107, 135, 'ACTION', 'ACTION', '0', '0', null, '/boss/viewTreeList.action;;/boss/goToUserList.action;;/boss/queryChiefStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (108, 136, 'XYZL_VIEW', '信用资料查询 ', '0', '0', null, '/member/enterCreditInformation.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (109, 136, 'XZMXYM', '下注明细页面', '0', '0', null, '/member/enterPlayDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (110, 136, 'LSKJ_VIEW', '历史开奖查询', '0', '0', null, '/member/enterLotResultHistory.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (111, 136, 'RULE', '规则页面', '0', '0', null, '/member/enterLotResultHistory.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (112, 138, 'TMXZTJ', '特碼下注提交', '0', '0', null, '/member/ajaxhkTMSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (113, 138, 'ZMXZTJ', '正碼下注提交', '0', '0', null, '/member/ajaxhkZMSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (114, 138, 'ZTMXZTJ', '正特碼下注提交', '0', '0', null, '/member/ajaxhkZTMSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (115, 138, 'ZM16XZTJ', '正碼1-6的下注提交', '0', '0', null, '/member/ajaxhkZM1To6Sub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (116, 138, 'LMXZTJ', '连码的下注提交', '0', '0', null, '/member/ajaxhkLMSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (117, 138, 'TMSXXZTJ', '特碼生肖的下注提交', '0', '0', null, '/member/ajaxhkTMSXSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (118, 138, 'SXWSXZTJ', '生肖尾數的下注提交', '0', '0', null, '/member/ajaxhkSXWSSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (119, 138, 'BBXZTJ', '半波的下注提交', '0', '0', null, '/member/ajaxhkBBSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (31, 33, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/manageruser/modifyPassword.action;;/sysmge/manageruser/modifyPasswordSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (10, 15, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/manageruser/list.action;;/sysmge/manageruser/search.action', '基础用户管理功能中的列表');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (51, 53, 'FUN_ACTION', '功能信息编辑', '0', '0', null, '/sysmge/function/modifyInfo.action;;/sysmge/function/functionSave.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (52, 53, 'RESOURCE_ACTION', '资源管理', '0', '0', null, '/sysmge/resource/addResource.action;;/sysmge/resource/saveResource.action;;/sysmge/resource/viewResource.action;;/sysmge/resource/modifyResource.action;;/sysmge/resource/saveModifyResource.action;;/sysmge/resource/delResource.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (70, 72, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/manageruser/create.action;;/sysmge/manageruser/createSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (74, 77, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/roles/viewRoles.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (76, 79, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/addParam.action;;/sysmge/param/saveParam.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (77, 80, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/viewParam.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (78, 81, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/modifyParam.action;;/sysmge/param/saveParam.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (79, 82, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/addParamValue.action;;/sysmge/param/saveParamValue.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (82, 85, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/delParamValue.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (84, 14, 'ACTION', 'ACTION', '0', '0', null, '/demo/list.action;;/demo/view.action;;/demo/modify.action;;/demo/modifySubmit.action;;/demo/create.action;;/demo/createSubmit.action;;/demo/orgExtTest.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (30, 32, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/manageruser/view.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (50, 52, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/function/addSame.action;;/sysmge/function/addNext.action;;/sysmge/function/submitAddSame.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (53, 54, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/function/del.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (71, 74, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/roles/list.action;;/sysmge/roles/searchRoles.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (72, 75, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/roles/addRoles.action;;/sysmge/roles/submitAddRoles.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (103, 129, 'ACTION', 'ACTION', '0', '0', null, '/boss/queryGdklsfLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (122, 138, 'WBZXZTJ', '無不中的下注提交', '0', '0', null, '/member/ajaxhkWBZSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (138, 100, 'queryShop', '查看单个商铺', '0', '0', null, '/boss/queryShop.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (163, 100, 'updateCqOpenOdds', '更新重庆开盘赔率', '0', '0', null, '/admin/updateCqOpenOdds.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (155, 158, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindByAgent.action;;/user/updateAgent.action;;/user/queryAgentStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (157, 100, 'enterOpenOdds', '進入開盤賠率頁面', '0', '0', null, '/admin/enterOpenOdds.action', 'enterOpenOdds');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (164, 100, 'updateHkOpenOdds', '更新香港开盘赔率', '0', '0', null, '/admin/updateHkOpenOdds.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (171, 175, 'ACTION', 'ACTION', '0', '0', null, '/user/saveInitMember.action;;/user/saveMember.action;;/user/queryMemberStaff.action;;/user/ajaxQueryMemberName.action;;/user/saveFindMember.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (191, 102, 'PLSZTJ', '賠率設置提交action', '0', '0', null, '/admin/ajaxgdBalloddsUpdate.action', '賠率設置提交action');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (371, 353, 'ACTION', 'ACTION', '0', '0', null, '/user/saveSubAccount.action;;/user/querySubAccount.action;;/user/ajaxQueryMemberName.action;;/user/savefindSubAccount.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5340, 4520, 'ZGHYCXFGS', '总管会员查询分公司', '0', '0', null, '/boss/queryBranchBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5360, 4520, 'ZGHYCXGD', '总管会员查询股东', '0', '0', null, '/boss/queryStockholderBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5361, 4520, 'ZGHYCXZDL', '总管会员查询总代理', '0', '0', null, '/boss/queryGenAgentStaffBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (97, 123, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateCommissionDefault.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (104, 130, 'ACTION', 'ACTION', '0', '0', null, '/boss/modifyGdklsfLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (106, 133, 'ACTION', 'ACTION', '0', '0', null, '/boss/modifyCqsscLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (123, 138, 'GGXZTJ', '過關的下注提交', '0', '0', null, '/member/ajaxhkGGSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (162, 100, 'ajaxQueryChsName', 'AJAX查詢用戶名稱', '0', '0', null, '/user/ajaxQueryChsName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (170, 173, 'ACTION', 'ACTION', '0', '0', null, '/user/queryMemberStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (192, 102, 'PLSZZTTJ', '賠率設置整體提交action', '0', '0', null, '/admin/balloddsUpdate.action', '賠率設置整體提交action');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (290, 102, 'PLSZTJ', '賠率設置提交_HK action', '0', '0', null, '/admin/ajaxHKoddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (314, 292, 'ACTION', 'ACTION', '0', '0', null, '/periods/updateFindPeriods.action;;/periods/updatePeriods.action;;/periods/queryHKPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (299, 102, 'XGPL', '修改赔率', '0', '0', null, '/admin/updateOddsThree.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5362, 4520, 'ZGHYCXDL', '总管会员查询代理', '0', '0', null, '/boss/queryAgentStaffBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5401, 5020, 'MX', '明细', '0', '0', null, '/boss/srDetailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5402, 5020, 'MX', '明细', '0', '0', null, '/boss/srQueryPetReport.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (450, 412, 'BCXGXGLHCJYSD', '保存修改香港六合彩交易設定', '1', '0', null, '/admin/updateHkTradingSet.action ', null);
commit;
prompt 100 records committed...
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5403, 5020, 'BHMX', '补货明细', '0', '0', null, '/boss/srReplenishDetailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (550, 218, 'BHTJ', '補貨提交', '0', '0', null, '/replenish/ajaxReplenishSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (591, 493, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/manageruser/deleteInfo.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (592, 102, 'LXTJ', '六肖ajax 提交', '0', '0', null, '/member/ajaxhkLXSub.action', '六肖ajax 提交');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4500, 218, 'ajaxReplenishLM', '連碼補貨頁面刷新', '0', '0', null, '/replenish/ajaxReplenishLM.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5404, 5020, 'MX', '明细', '0', '0', null, '/boss/srDetailedReport.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4540, 218, 'CXBHJGLM', '查詢補貨結果連碼', '0', '0', null, '/replenish/ajaxFinishReplenishLM.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4541, 4500, 'ACTION', 'ACTION', '0', '0', null, '/admin/queryAllMessage.action;;' || chr(13) || '' || chr(10) || '/user/queryAllMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4563, 4520, 'CXZJ', '查詢總監', '0', '0', null, '/boss/queryStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4561, 4520, 'ZGHYGL', '總管會員管理', '0', '0', null, '/boss/goToUserList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4562, 4520, 'SCK', '樹查看', '0', '0', null, '/boss/viewTreeMemberList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4580, 4540, 'ACTION', 'ACTION', '0', '0', null, '/boss/queryManagerMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4600, 4560, 'ZDSS', '注單搜索', '0', '0', null, '/statreport/queryPetReport.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4620, 4580, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateHistoryResultPeriods.action;;/boss/updateFindHistoryResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4680, 4640, 'ACTION', 'ACTION', '0', '0', null, '/boss/deleteMessage.action;;/boss/queryManagerMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4720, 4680, 'TMHZ', '特碼匯總', '0', '0', null, '/replenish/queryZhangdan.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4740, 4700, 'YCRZ', '異常日志', '0', '0', null, '/boss/queryBossLog.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4780, 4520, 'ZGXGYHMM', '总管修改用户密码', '0', '0', null, '/boss/ajaxUpdatePwdBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4820, 130, 'KLSFTK', '快樂十分ajax 停開', '0', '0', null, '/boss/ajaxDelPriod.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4840, 4720, 'ZDSS', '注單搜索', '0', '0', null, '/admin/searchBill.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4880, 4760, 'ZMDH', '正碼-封號', '0', '0', null, '/admin/updateOddsStatusZMA.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5492, 5073, 'getInManageMenu.action', '內部管理二級菜單', '0', '0', null, '/admin/getInManageMenu.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5452, 5030, 'ajaxFengHao.action', '補貨里的封號操作', '0', '0', null, '/admin/ajaxFengHao.action', '補貨里的封號操作');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5471, 5050, 'enterManage.action', '进入管理首页', '0', '0', null, '/admin/enterManage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5491, 5071, 'enterOpenOdds.action ', '进入开盘赔率页面', '0', '0', null, '/admin/enterOpenOdds.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5511, 5073, 'getPersonalAdminMenu.action', '個人管理二級菜單', '0', '0', null, '/admin/getPersonalAdminMenu.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5512, 5073, 'getUserMenu.action', '用戶管理二級菜單', '0', '0', null, '/admin/getUserMenu.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5531, 332, 'queryBJResultPeriods.action ', '北京赛车盘口管理', '0', '0', null, '/boss/queryBJResultPeriods.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5551, 332, 'bjPeriodsInfoEnter.action', '手动生成北京赛车期數的界面', '0', '0', null, '/boss/bjPeriodsInfoEnter.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5552, 332, 'bjPeriodsInfo.action', '手动生成北京赛车期數', '0', '0', null, '/boss/bjPeriodsInfo.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5571, 218, 'gdReplenishEnter.action', '广东补货菜单入口', '0', '0', null, '/replenish/gdReplenishEnter.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5572, 218, 'cqReplenishEnter.action', '重庆补货菜单入口', '0', '0', null, '/replenish/cqReplenishEnter.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5591, 218, 'bjReplenishEnter.action ', '北京補貨菜單入口', '0', '0', null, '/replenish/bjReplenishEnter.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5611, 218, 'replenishDetail.action', '補貨明細', '0', '0', null, '/replenish/replenishDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5631, 218, 'ajaxReplenishLmMain.action', '广东连码显示下方列表', '0', '0', null, '/replenish/ajaxReplenishLmMain.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5671, 5112, 'ajaxBJBalloddsUpdate.action', '北京实时赔率设置', '0', '0', null, '/admin/ajaxBJBalloddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5652, 5090, 'queryBJSCLotResult.action', '北京赛车查看', '0', '0', null, '/boss/queryBJSCLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5653, 5091, 'modifyBJSCLotResult.action', '修改北京賽車开奖结果', '0', '0', null, '/boss/modifyBJSCLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5691, 5130, 'updateBjTradingSet.action', '更新北京賽車交易設定', '0', '0', null, '/admin/updateBjTradingSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5711, 5150, 'updateBjOpenOdds.action', '修改北京賽車賠率設定', '0', '0', null, '/admin/updateBjOpenOdds.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5731, 218, 'realTimeDetail.action', '实时滚单', '0', '0', null, '/replenish/realTimeDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (211, 174, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindByMember.action;;/user/updateMember.action;;/user/queryMemberStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (254, 218, 'replenishSet.action', '進入補貨頁面', '0', '0', null, '/replenish/replenishSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (291, 272, 'ACTION', 'ACTION', '0', '0', null, '/periods/queryHKPeriods.action;;/periods/queryHKResultPeriods', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (334, 312, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateFindResultPeriods', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (335, 313, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateFindResultPeriods.action;;/boss/updateResultPeriods.action;;/boss/queryHKResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5000, 4860, 'WJSJSBBMX', '未结算交收报表明细', '0', '0', null, '/statreport/unsettledList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5060, 4720, 'QXZD', '取消注單', '0', '0', null, '/admin/ajaxCancelBillSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5081, 4901, 'ACTION', 'ACTION', '0', '0', null, '/admin/queryAllMessage.action;;' || chr(13) || '' || chr(10) || '/user/queryAllMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5141, 218, 'BHSCXSSPL', '补货时查询实时赔率', '0', '0', null, '/replenish/ajaxQueryRealOdds.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (370, 352, 'ACTION', 'ACTION', '0', '0', null, '/user/querySubAccount.action;;/user/ajaxQueryMemberName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (374, 356, 'ACTION', 'ACTION', '0', '0', null, '/boss/saveNewPeriods.action;;' || chr(13) || '' || chr(10) || '/boss/savePeriods.action;;/boss/queryHKResultPeriods.action;;/user/ajaxQueryPeriodsNum.action' || chr(13) || '' || chr(10) || ';;/boss/updateMangerPeriods.action;;/boss/updateFindPeriods.action;;/boss/cqPeriodsInfo.action;;/boss/gdPeriodsInfo.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (390, 373, 'SESSIONLIST', 'SESSION列表', '0', '0', null, '/sysmge/monitor/sessionList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (470, 432, 'ACTION', 'ACTION', '0', '0', null, '/user/ajaxQueryQianPassword.action;;/member/enterChangePassword.action;;/member/changePassword.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (490, 452, 'ACTION', 'ACTION', '0', '0', null, '/member/enterSettleReport.action;;/member/queryCqReportCurrent.action;;/member/queryGdReportCurrent.action;;/member/queryGdAndCQReportCurrent.action;;/member/queryHkReportCurrent.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (530, 102, 'CQ', '重慶', '1', '0', null, '/admin/cqsscoddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (590, 492, 'ACTION', 'ACTION', '0', '0', null, '/user/queryCreditInformation.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (32, 34, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/function/modifyMain.action;;/sysmge/function/modifyLocation.action;;/sysmge/function/modifyTreeList.action;;/sysmge/function/modifyDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (75, 78, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/list.action;;/sysmge/param/searchParam.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (80, 83, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/modifyParamValue.action;;/sysmge/param/saveParamValue.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (81, 84, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/viewParamValue.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (83, 86, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/param/delParam.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (105, 131, 'ACTION', 'ACTION', '0', '0', null, '/boss/queryCqsscLotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (153, 154, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindByGenAgent.action;;/user/updateGenAgent.action;;/user/queryGenAgentStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (154, 159, 'ACTION', 'ACTION', '0', '0', null, '/user/queryAgentStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (156, 157, 'ACTION', 'ACTION', '0', '0', null, '/user/saveAgentStaff.action;;/user/ajaxQueryUserName.action;;/user/saveFindAgent.action;;/user/saveAgent.action;;/user/queryAgentStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (159, 100, 'ajaxFindShopsName', 'AJAX查詢商鋪名稱', '0', '0', null, '/boss/ajaxFindShopsName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (210, 100, 'enterTradingSet', '進入交易設定頁面', '0', '0', null, '/admin/enterTradingSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (452, 102, 'HQSSPL', '获取实时赔率', '0', '0', null, '/member/ajaxGetRealOdds.action', '获取实时赔率');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (120, 138, 'SXLXZTJ', '生肖連的下注提交', '0', '0', null, '/member/ajaxhkSXLSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (121, 138, 'WSLXZTJ', '尾數連的下注提交', '0', '0', null, '/member/ajaxhkWSLSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4960, 4820, 'action', 'action', '0', '0', null, '/user/queryMemberStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4980, 4840, 'marquee', '后台跑马灯', '0', '0', null, '/admin/marquee.action ', '后台跑马灯');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5020, 4860, 'WJSJSBBXZMX', '未结算交收报表下注明细', '0', '0', null, '/statreport/unsettledDetailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (670, 218, 'ajaxFinishReplenish', '查詢補貨的結果', '0', '0', null, '/replenish/ajaxFinishReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (92, 116, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindByBranchStaff.action;;/user/updateByBranchStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (98, 125, 'ACTION', 'ACTION', '0', '0', null, '/boss/shopRegister.action;;/boss/shopRegisterSubmit.action;;/boss/shopRegisterSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (73, 76, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/roles/modifyRoles.action;;/sysmge/roles/submitModifyRoles.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (99, 124, 'login', '登陆', '0', '0', null, '/boss/bossLogin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (100, 126, 'ACTION', 'ACTION', '0', '0', null, '/boss/shopManager.action;;/boss/shopRegisterSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (124, 140, 'QLXZTJ', '球类下注提交', '0', '0', null, '/member/ajaxklsfBallSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (125, 140, 'LMPXZTJ', '两面盘的下注提交', '0', '0', null, '/member/ajaxklsfDoubleSideSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (126, 140, 'ZHLHXZTJ', '總和龍虎的下注提交', '0', '0', null, '/member/ajaxklsfSumDragonTigerSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (127, 140, 'LZXZTJ', '連直的下注提交', '0', '0', null, '/member/ajaxklsfStraightthroughSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (128, 142, 'QLXZTJ', '球类下注提交', '0', '0', null, '/member/ajaxcqsscBallSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (129, 136, 'SYCPWFRK', '所有彩票玩法的入口', '0', '0', null, '/member/member.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (130, 143, 'QLTJCX', '球类统计查询', '0', '0', null, '/member/gdklsfBallStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (131, 143, 'LMTJCX', '两面统计查询', '0', '0', null, '/member/gdklsfDoubleStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (132, 144, 'QLTJCX', '球类统计查询', '0', '0', null, '/member/cqsscStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (430, 392, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/roles/del.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (252, 215, 'updateGdTradingSet', '保存', '0', '0', null, '/admin/updateGdTradingSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (253, 217, 'updateCqTradingSet', '保存', '0', '0', null, '/admin/updateCqTradingSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (273, 256, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/memberuser/modifyPassword.action;;/sysmge/memberuser/modifyPasswordSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5040, 4881, 'ACTION', 'ACTION', '0', '0', null, '/user/saveFindMessage.action;;' || chr(13) || '' || chr(10) || '/user/saveChiefMessage.action;;' || chr(13) || '' || chr(10) || '/user/queryAllMessage.action;;', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5080, 4900, 'ACTION', 'ACTION', '0', '0', null, '/user/queryPopupMenus.action;;/member/queryMemberPopupMenus.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5200, 4960, 'ACTION', 'ACTION', '0', '0', null, '/member/queryMemberWarmomgStatent.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5260, 5000, 'XGHGDCQSZ', '香港貨廣東重慶的設置', '0', '0', null, '/replenishauto/changeReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5280, 5020, 'ZGBBCXRK', '总管报表查询入口', '0', '0', null, '/boss/statReportBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (350, 332, 'ACTION', 'ACTION', '0', '0', null, '/boss/queryHKResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (354, 333, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindPassword.action;;' || chr(13) || '' || chr(10) || '/user/updatePassword.action;;/user/ajaxQueryPassword.action', null);
commit;
prompt 200 records committed...
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5300, 4940, 'CXDLRZ', '查询登录日志', '0', '0', null, '/admin/queryLoginLog.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (372, 354, 'ACTION', 'ACTION', '0', '0', null, '/user/updateSubAccount.action;;/user/querySubAccount.action;;/user/updateFindSubAccount.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (373, 355, 'ACTION', 'ACTION', '0', '0', null, '/user/delSubAccount.action;;/user/querySubAccount.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (451, 102, 'AJAXQZGPC', 'Ajax取最高派彩', '0', '0', null, '/member/ajaxTopWinPrice.action', 'ajax取最高派彩');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (133, 136, 'TZYLLMTJCX', '投注右栏两面的统计查询', '0', '0', null, '/member/cqsscStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (134, 141, 'TB', '头部', '0', '0', null, '/member/cqsscTop.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (135, 139, 'TB', '头部', '0', '0', null, '/member/klsfTop.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (136, 137, 'TB', '头部', '0', '0', null, '/member/hkTop.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (137, 112, 'INDEX', '首页入口', '0', '0', null, '/user/queryBranchStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (151, 155, 'ACTION', 'ACTION', '0', '0', null, '/user/queryGenAgentStaff.action' || chr(13) || '' || chr(10) || '', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (152, 153, 'ACTION', 'ACTION', '0', '0', null, '/user/saveGenAgentStaff.action;;/user/ajaxQueryUserName.action;;/user/saveFindGenAgent.action;;/user/saveGenAgent.action;;/user/queryGenAgentStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (158, 100, 'updateGdOpenOdds', '更新廣東開盤賠率', '0', '0', null, '/admin/updateGdOpenOdds.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (160, 100, 'ajaxFindShopsCode', 'AJAX查詢商鋪號', '0', '0', null, '/boss/ajaxFindShopsCode.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (161, 100, 'ajaxQueryUserName', 'AJAX查詢用戶登錄號', '0', '0', null, '/user/ajaxQueryUserName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (190, 102, 'PLSZCSACTION', '賠率設置初始action', '0', '0', null, '/admin/oddsSet.action', '賠率設置初始action');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (193, 103, 'memberuserList', '会员列表', '0', '0', null, '/sysmge/memberuser/list.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (251, 213, 'ACTION', 'ACTION', '0', '0', null, '/user/savaImmediateMember.action;;/user/savaFindImmediateMember.action;;/user/queryMemberStaff.action;;/user/ajaxQueryMemberName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (270, 252, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/memberuser/create.action;;/sysmge/memberuser/createSubmit.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (271, 258, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/memberuser/list.action;;/sysmge/memberuser/search.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (272, 257, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/memberuser/view.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (274, 255, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5322, 332, 'GDPKGL', '广东盘口管理', '0', '0', null, '/boss/queryGDResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (275, 253, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserRoleMain.action;;/sysmge/authoriz/userRoleData.action;;/sysmge/authoriz/userRoleSelectList.action;;/sysmge/authoriz/authorizUserRoleSubmit.action;;/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (276, 254, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserPrivate.action;;/sysmge/authoriz/userPrivateSelectFunc.action;;/sysmge/authoriz/authorizUserPrivateSubmit.action;;/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4940, 4720, 'CXLIST', '查詢list', '0', '0', null, '/admin/queryBill.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (294, 102, 'XGDGPL', '修改单个赔率', '0', '0', null, '/admin/updateOddsOne.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5323, 332, 'CQPKGL', '重庆盘口管理', '0', '0', null, '/boss/queryCQResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (298, 274, 'ACTION', 'ACTION', '0', '0', null, '/periods/queryHKPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5100, 473, 'SEARCH', '查询', '0', '0', null, '/statreport/statReportSearch.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5101, 473, 'LIST', '列表', '0', '0', null, '/statreport/list.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5102, 473, 'DETAIL', '投注明细', '0', '0', null, '/statreport/detailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5103, 473, 'REPLENISH_DETAILED', '补货明细', '0', '0', null, '/statreport/replenishDetailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5140, 473, 'DETAILED_REPORT', '报表明细', '0', '0', null, '/statreport/detailedReport.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5240, 5000, 'GXSZ', '更新設置', '0', '0', null, '/replenishauto/updateAutoReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4700, 4660, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateFindMessage.action;;/boss/updateMessage.action;;/boss/queryManagerMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4860, 4740, 'PlayRule', '规则', '0', '0', null, '/member/enterPlayRule.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4881, 4760, 'ZM1_6', '正碼1-6', '0', '0', null, '/admin/updateOddsZM16.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4882, 4760, 'LM', '連碼', '0', '0', null, '/admin/updateOddsOneLM.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4883, 4760, 'LX', '六肖', '0', '0', null, '/admin/updateOddsOneLX.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4884, 4760, 'SXL1', '生肖連1', '0', '0', null, '/admin/ajaxSetOddsByRealCodeTypeX.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4885, 4760, 'WSL', '尾數連', '0', '0', null, '/admin/updateOddsOneWSL.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4886, 4780, 'ZDJP', '自動降賠', '0', '0', null, '/admin/systemConfig.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4887, 4760, 'ZM', '正碼', '0', '0', null, '/admin/updateOddsZMAONE.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4888, 4760, 'LMJJ', '連碼加減', '0', '0', null, '/admin/ajaxSetOddsByTypeX.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4889, 4760, 'BB', '半波', '0', '0', null, '/admin/updateOddsOneBB.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4890, 4781, 'CPJL', '操盤記錄', '0', '0', null, '/admin/playOddsLogAction.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4891, 4760, 'TMSX', '特碼生肖', '0', '0', null, '/admin/updateOddsTMSX.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4892, 4760, 'SXWS', '生肖尾數', '0', '0', null, '/admin/updateOddsSXWS.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4893, 4760, 'SXL2', '生肖連2', '0', '0', null, '/admin/updateOddsOneSXL.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4894, 4760, 'WBZ', '五不中', '0', '0', null, '/admin/updateOddsOneWBZ.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4900, 4780, 'ZDJP2', '自動降賠2', '0', '0', null, '/admin/updateAutoOddsDoubleSide.action  ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4901, 4800, 'AJAXCKFPZT', 'Ajax查看封盤狀態', '0', '0', null, '/replenish/getTypeCodeState.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4902, 4760, 'TMPL', '特码赔率', '0', '0', null, '/admin/updateOddsStatus.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4903, 4760, 'ZM2', '正碼2', '0', '0', null, '/admin/updateOddsZMATWO.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4904, 4760, 'ZTM1', '正特碼1', '0', '0', null, '/admin/updateOddsZTONE.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4905, 4760, 'ZTM2', '正特碼2', '0', '0', null, '/admin/updateOddsZTTWO.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4906, 4760, 'ZTM3', '正特碼3', '0', '0', null, '/admin/updateOddsStatusZT.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4907, 4760, 'GG', '過關', '0', '0', null, '/admin/updateOddsOneGG.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4908, 4801, 'DOUBLESIDE  ', '两面盘赔率更新', '0', '0', null, '/admin/doubleSideOddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4909, 4801, 'ZHLHPLSD', '总和龍虎赔率设定', '0', '0', null, '/admin/zhlhOddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4910, 4801, 'LM', '連碼', '0', '0', null, '/admin/lmOddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4911, 4760, 'TM', '特碼-', '0', '0', null, '/admin/updateOddsOne.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4912, 4802, 'AJAX_UPDATE', 'Ajax赔率更新校验', '0', '0', null, '/admin/ajaxCQBalloddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4920, 102, 'JCPLSFYBD', '检查赔率是否有变动', '0', '0', null, '/member/ajaxCheckOddsChanged.action', '检查赔率是否有变动');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5041, 4880, 'ACTION', 'ACTION', '0', '0', null, '/user/updateFindMessage.action;;/user/updateChiefMessage.action;;/user/queryAllMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5042, 4882, 'ACTION', 'ACTION', '0', '0', null, '/user/deleteMessage.action;;/user/queryAllMessage.action;;', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5120, 4860, 'WJSJSBBBHXZMX', '未结算交收报表补货下注明细', '0', '0', null, '/statreport/unsettledRelenishDetailed.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4640, 4600, 'ACTION', 'ACTION', '0', '0', null, '/boss/saveFindMessage.action;;/boss/saveMessage.action;;/boss/queryManagerMessage.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (4660, 4620, 'GLKRJGCX', '管理開然結果查詢', '0', '0', null, '/admin/enterLotResultHistoryAdmin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5160, 4920, 'ZDBHRK', '自动补货入口', '0', '0', null, '/replenish/autoReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5180, 4920, 'ZDBHRZ', '自动补货日志', '0', '0', null, '/replenish/autoReplenishLog.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5181, 4940, 'DLRZCX', '登录日志查询', '0', '0', null, '/admin/loginLog.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5220, 4980, 'ACTION', 'ACTION', '0', '0', null, '/boss/updateMemberTotalCreditLine.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5261, 5000, 'XGSZCSH', '香港設置初始化', '0', '0', null, '/replenishauto/autoReplenishHK.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5380, 4520, 'ZGHYCXSYHY', '总管会员查询所有会员', '0', '0', null, '/boss/queryMemberStaffBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5400, 5020, 'BBLB', '报表列表', '0', '0', null, '/boss/srList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5420, 5020, 'WJSJSBBLB', '未结算交收报表列表', '0', '0', null, '/boss/unsettledBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5421, 5020, 'ZGWJSJSBBMX', '总管未结算交收报表的明细', '0', '0', null, '/boss/unsettledDeBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5422, 5020, 'ZGWJSJSBBBHMX', '总管未结算交收报表补货明细', '0', '0', null, '/boss/unsettledRelenishDeBoss.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5440, 5000, 'ZDBH', '自動補貨', '0', '0', null, '/replenishauto/autoReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5441, 5000, 'ZDBHRZ', '自動補貨日誌', '0', '0', null, '/replenishauto/autoReplenishLog.action', '自動補貨日誌');
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (85, 87, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserRoleMain.action;;/sysmge/authoriz/userRoleData.action;;/sysmge/authoriz/userRoleSelectList.action;;/sysmge/authoriz/authorizUserRoleSubmit.action;;/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (86, 88, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizRoleMain.action;;/sysmge/authoriz/selectFunc.action;;/sysmge/authoriz/authorizRoleSubmit.action;;/sysmge/authoriz/authorizRoleView.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (87, 89, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizRoleView.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (88, 90, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserPrivate.action;;/sysmge/authoriz/userPrivateSelectFunc.action;;/sysmge/authoriz/authorizUserPrivateSubmit.action;;/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (89, 91, 'ACTION', 'ACTION', '0', '0', null, '/sysmge/authoriz/authorizUserViewMain.action;;/sysmge/authoriz/userViewFunc.action;;/sysmge/authoriz/userViewRole.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5751, 218, 'ajaxRealTimeDetail.ation', '获取实时滚单信息', '0', '0', null, '/replenish/ajaxRealTimeDetail.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5772, 5171, 'registerUserOutReplenish.action', '出貨會員注冊頁面', '0', '0', null, '/user/registerUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5831, 5210, 'findUserOutCommission.action', '進入出貨會員擁金設定界面', '0', '0', null, '/user/findUserOutCommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5851, 5210, 'ajaxQueryCommission/action', 'AJAX查询出货会员的设定框', '0', '0', null, '/user/ajaxQueryCommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5911, 218, 'queryZhangdanReplenish.action', '帐单', '0', '0', null, '/replenish/queryZhangdanReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5991, 4920, 'ajaxAutoReplenishSet.action', '自动补货提交前校验', '0', '0', null, '/replenishauto/ajaxAutoReplenishSet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6019, 5252, 'enterBet', '内容', '0', '0', null, '/member/enterBet.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6017, 5252, 'leftUser', '会员投注界面的左边栏', '0', '0', null, '/member/leftUser.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6018, 5252, 'topMenu', '头部', '0', '0', null, '/member/topMenu.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6026, 140, 'cqajaxTodayWin', 'cqajaxTodayWin', '0', '0', null, '/member/cqajaxTodayWin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6024, 140, 'cqajaxRealOdd', 'cqajaxRealOdd', '0', '0', null, '/member/cqajaxRealOdd.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6025, 140, 'cqajaxSomeOtherAssist', 'cqajaxSomeOtherAssist', '0', '0', null, '/member/cqajaxSomeOtherAssist.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6027, 5253, 'bjajaxRealOdd', 'bjajaxRealOdd', '0', '0', null, '/member/bjajaxRealOdd.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6031, 138, 'hkajaxSomeOtherAssist', 'hkajaxSomeOtherAssist', '0', '0', null, '/member/hkajaxSomeOtherAssist.action', null);
commit;
prompt 300 records committed...
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6032, 138, 'hkajaxTodayWin', 'hkajaxTodayWin', '0', '0', null, '/member/hkajaxTodayWin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6033, 142, 'ajaxcqsscDoubleSideSub', '进入下注的入口页面 快乐十分两面盘的下注提交action', '0', '0', null, '/member/ajaxcqsscDoubleSideSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6034, 5253, 'ajaxBJSCSub', '进入下注的入口页面', '0', '0', null, '/member/ajaxBJSCSub.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6035, 136, 'findShopsCode', 'findShopsCode', '0', '0', null, '/member/findShopsCode.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6036, 136, 'ajaxGetPlayTypeInfo', 'ajaxGetPlayTypeInfo', '0', '0', null, '/member/ajaxGetPlayTypeInfo.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6056, 119, 'ajaxQueryStockholerName', 'ajaxQueryStockholerName', '0', '0', null, '/user/ajaxQueryStockholerName.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6093, 5290, 'unClassDetailedList', '下注明细', '0', '0', null, '/classreport/unClassDetailedList.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6133, 5091, 'modifyBJSCHistoryResult.action ', '修改北京开奖结果', '0', '0', null, '/boss/modifyBJSCHistoryResult.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5971, 218, 'replenishRightBar.action', '补货统计--总额和遗漏', '0', '0', null, '/replenish/replenishRightBar.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6021, 136, 'ajaxSomeOtherAssist', 'ajaxSomeOtherAssist', '0', '0', null, '/member/ajaxSomeOtherAssist.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6050, 158, 'saveAgentcommission', 'saveAgentcommission', '0', '0', null, '/user/saveAgentcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6051, 173, 'queryMembercommission', 'queryMembercommission', '0', '0', null, '/user/queryMembercommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6097, 473, 'settledRelenishDetailedEric', '补货明细', '0', '0', null, '/statreport/settledRelenishDetailedEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6098, 474, 'classListEric', '已结算分类报表', '0', '0', null, '/classreport/classListEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6099, 474, 'classDetailedListEric', '下注明细', '0', '0', null, '/classreport/classDetailedListEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6100, 474, 'classRelenishDetailedEric', '补货明细', '0', '0', null, '/classreport/classRelenishDetailedEric.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6111, 5073, 'loginFailureMember.action', 'loginFailureMember.action', '0', '0', null, '/sysmge/loginFailureMember.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6131, 130, 'modifyGdklsfHistoryResult.action ', '修改广东开奖结果', '0', '0', null, '/boss/modifyGdklsfHistoryResult.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6172, 332, 'queryK3ResultPeriods.action', '快3盘口管理', '0', '0', null, '/boss/queryK3ResultPeriods.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6211, 5350, 'ajaxK3BalloddsUpdate.action', '快3实时赔率设置', '0', '0', null, '/admin/ajaxK3BalloddsUpdate.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6252, 5370, 'modifyK3LotResult.action', '修改快3开奖结果', '0', '0', null, '/boss/modifyK3LotResult.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5791, 5190, 'findUserOutReplenish.action', '进入修改出货会员界面', '0', '0', null, '/user/findUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6011, 143, 'ajaxgdklsfBallStatistics.action', '球类统计查询', '0', '0', null, '/member/ajaxgdklsfBallStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5931, 218, 'ajaxtwoSideAndDragonRank.action', '補貨界面的兩面長龍統計', '0', '0', null, '/replenish/ajaxtwoSideAndDragonRank.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6012, 143, 'ajaxgdklsfDoubleStatistics', '球类统计查询', '0', '0', null, '/member/ajaxgdklsfDoubleStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6013, 144, 'ajaxcqsscStatistics', '球类统计查询', '0', '0', null, '/member/ajaxcqsscStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6014, 5251, 'bjscStatistics', '统计查询', '0', '0', null, '/member/bjscStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6015, 5251, 'ajaxbjscStatistics', '统计查询', '0', '0', null, '/member/ajaxbjscStatistics.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6022, 136, 'ajaxTodayWin', 'ajaxTodayWin', '0', '0', null, '/member/ajaxTodayWin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6028, 5253, 'bjajaxSomeOtherAssist', 'bjajaxSomeOtherAssist', '0', '0', null, '/member/bjajaxSomeOtherAssist.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6029, 5253, 'bjajaxTodayWin', 'bjajaxTodayWin', '0', '0', null, '/member/bjajaxTodayWin.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6030, 138, 'hkajaxRealOdd', 'hkajaxRealOdd', '0', '0', null, '/member/hkajaxRealOdd.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6041, 115, 'findByBranchStaff', 'findByBranchStaff', '0', '0', null, '/member/findByBranchStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6042, 5255, 'saveUserOutReplenish', 'saveUserOutReplenish', '0', '0', null, '/user/saveUserOutReplenish.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6173, 332, 'k3PeriodsInfo.action', '手动生成快3盘期', '0', '0', null, '/boss/k3PeriodsInfo.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6174, 332, 'delK3PeriodsInfo.action ', '删除快3当天盘期', '0', '0', null, '/boss/delK3PeriodsInfo.action ', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (5852, 5210, 'updateOutReplenishCommission.action', '修改提交出货会员的设定框', '0', '0', null, '/user/updateOutReplenishCommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6020, 136, 'ajaxRealOdd', '获取赔率', '0', '0', null, '/member/ajaxRealOdd.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6037, 5254, 'queryChiefStaff', 'queryChiefStaff', '0', '0', null, '/member/queryChiefStaff.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6038, 5254, 'userChiefStaffRegister', 'userChiefStaffRegister', '0', '0', null, '/member/userChiefStaffRegister.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6043, 115, 'queryBranchcommission', 'queryBranchcommission', '0', '0', null, '/user/queryBranchcommission.action', null);
insert into TB_FRAME_RESOURCE (ID, FUNC_ID, RES_CODE, RES_NAME, RES_STATE, RES_TYPE, SORT_NUM, URL, RES_DESC)
values (6044, 116, 'saveBranchcommission', 'saveBranchcommission', '0', '0', null, '/user/saveBranchcommission.action', null);
commit;
prompt 342 records loaded
prompt Loading TB_FRAME_ROLES...
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (912, 'CHIEF_SUB_ROLE_OUT_USER_MANAGER', '总监子账号授权角色', '1', '3', null, 1, '出貨會員管理');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (913, 'CHIEF_SUB_ROLE_ODD_LOG', '总监子账号授权角色', '1', '3', null, 1, '每期彩票管理、操盤記錄查詢');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (914, 'CHIEF_SUB_ROLE_SYS_INIT', '总监子账号授权角色', '1', '3', null, 1, '系統初始設定');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (922, 'STOCKHOLDER_SUB_ROLE_JSZD', '股東子账号授权角色', '1', '3', null, 1, '即時注單');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (923, 'GEN_AGENT_SUB_ROLE_JSZD', '總代理子账号授权角色', '1', '3', null, 1, '即時注單');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (924, 'AGENT_SUB_ROLE_JSZD', '代理子账号授权角色', '1', '3', null, 1, '即時注單');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (919, 'CHIEF_SUB_ROLE_CANCEL_BILL', '总监子账号授权角色', '1', '3', null, 1, '注單取消權限');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (920, 'CHIEF_SUB_ROLE_JSZD', '总监子账号授权角色', '1', '3', null, 1, '即時注單');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (911, 'CHIEF_SUB_ROLE_OUT_REPLENISH', '总监子账号授权角色', '1', '3', null, 1, '補貨（外補做帳）');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (921, 'BRANCH_SUB_ROLE_JSZD', '分公司子账号授权角色', '1', '3', null, 1, '即時注單');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (918, 'CHIEF_SUB_ROLE_BACKSYS_ROLE', '总监子账号授权角色', '1', '3', null, 1, '系統后臺維護權限');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (21, 'CHIEF_SUB_ROLE_OFFLINE', '总监子账号授权角色', '1', '3', null, 1, '下线账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (22, 'CHIEF_SUB_ROLE_SUB', '总监子账号授权角色', '1', '3', null, 1, '子账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (23, 'CHIEF_SUB_ROLE_DELIVERY', '总监子账号授权角色', '1', '3', null, 1, '总监交收报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (24, 'CHIEF_SUB_ROLE_CLASSIFY', '总监子账号授权角色', '1', '3', null, 1, '总监分类报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (30, 'BRANCH_SUB_ROLE_REPLENISH', '分公司子账号授权角色', '1', '3', null, 1, '手工补货、自动补货设定（及变更记录），无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (31, 'BRANCH_SUB_ROLE_OFFLINE', '分公司子账号授权角色', '1', '3', null, 1, '下线账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (32, 'BRANCH_SUB_ROLE_SUB', '分公司子账号授权角色', '1', '3', null, 1, '子账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (33, 'BRANCH_SUB_ROLE_DELIVERY', '分公司子账号授权角色', '1', '3', null, 1, '分公司交收报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (34, 'BRANCH_SUB_ROLE_CLASSIFY', '分公司子账号授权角色', '1', '3', null, 1, '分公司分类报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (40, 'STOCKHOLDER_SUB_ROLE_REPLENISH', '股东子账号授权角色', '1', '3', null, 1, '手工补货、自动补货设定（及变更记录），无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (41, 'STOCKHOLDER_SUB_ROLE_OFFLINE', '股东子账号授权角色', '1', '3', null, 1, '下线账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (42, 'STOCKHOLDER_SUB_ROLE_SUB', '股东子账号授权角色', '1', '3', null, 1, '子账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (900, '1', '私有角色', '0', '2', null, 1, '用户【系统管理员（1）】的私有功能角色');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (43, 'STOCKHOLDER_SUB_ROLE_DELIVERY', '股东子账号授权角色', '1', '3', null, 1, '股东交收报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (44, 'STOCKHOLDER_SUB_ROLE_CLASSIFY', '股东子账号授权角色', '1', '3', null, 1, '股东分类报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (50, 'GEN_AGENT_SUB_ROLE_REPLENISH', '总代理子账号授权角色', '1', '3', null, 1, '手工补货、自动补货设定（及变更记录），无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (51, 'GEN_AGENT_SUB_ROLE_OFFLINE', '总代理子账号授权角色', '1', '3', null, 1, '下线账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (52, 'GEN_AGENT_SUB_ROLE_SUB', '总代理子账号授权角色', '1', '3', null, 1, '子账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (53, 'GEN_AGENT_SUB_ROLE_DELIVERY', '总代理子账号授权角色', '1', '3', null, 1, '总代理交收报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (54, 'GEN_AGENT_SUB_ROLE_CLASSIFY', '总代理子账号授权角色', '1', '3', null, 1, '总代理分类报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (60, 'AGENT_SUB_ROLE_REPLENISH', '代理子账号授权角色', '1', '3', null, 1, '手工补货、自动补货设定（及变更记录），无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (61, 'AGENT_SUB_ROLE_OFFLINE', '代理子账号授权角色', '1', '3', null, 1, '下线账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (62, 'AGENT_SUB_ROLE_SUB', '代理子账号授权角色', '1', '3', null, 1, '子账号管理，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (63, 'AGENT_SUB_ROLE_DELIVERY', '代理子账号授权角色', '1', '3', null, 1, '代理交收报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (64, 'AGENT_SUB_ROLE_CLASSIFY', '代理子账号授权角色', '1', '3', null, 1, '代理分类报表，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (9, 'MANAGER_SUB_DEFAULT', '总管子账号默认角色', '1', '3', null, 1, '总管子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (20, 'CHIEF_SUB_ROLE_REPLENISH', '总监子账号授权角色', '1', '3', null, 1, '手工补货、自动补货设定（及变更记录），无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (10, 'CHIEF_SUB_DEFAULT', '总监子账号默认角色', '1', '3', null, 1, '总监子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (11, 'BRANCH_SUB_DEFAULT', '分公司子账号默认角色', '1', '3', null, 1, '分公司子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (12, 'STOCKHOLDER_SUB_DEFAULT', '股东子账号默认角色', '1', '3', null, 1, '股东子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (13, 'GEN_AGENT_SUB_DEFAULT', '总代理子账号默认角色', '1', '3', null, 1, '总代理子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (8, 'MEMBER_DEFAULT', '会员默认角色', '2', '3', null, 1, '会员默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (7, 'AGENT_DEFAULT', '代理默认角色', '1', '3', null, 1, '代理默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (14, 'AGENT_SUB_DEFAULT', '代理子账号默认角色', '1', '3', null, 1, '代理子账号默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (6, 'GEN_AGENT_DEFAULT', '总代理默认角色', '1', '3', null, 1, '总代理默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (1, 'SYS_DEFAULT', '系统管理员', '0', '3', null, 1, '系统管理员默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (2, 'MANAGER_DEFAULT', '总管默认角色', '1', '3', null, 1, '总管默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (3, 'CHIEF_DEFAULT', '总监默认角色', '1', '3', null, 1, '总监默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (4, 'BRANCH_DEFAULT', '分公司默认角色', '1', '3', null, 1, '分公司默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (5, 'STOCKHOLDER_DEFAULT', '股东默认角色', '1', '3', null, 1, '股东默认角色，系统初始化创建，无法删除');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (910, 'CHIEF_SUB_ROLE_ODD', '总监子账号授权角色', '1', '3', null, 1, null);
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (915, 'CHIEF_SUB_ROLE_TRADING_SET', '总监子账号授权角色', '1', '3', null, 1, '交易設定、賠率設定');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (916, 'CHIEF_SUB_ROLE_MESSAGE', '总监子账号授权角色', '1', '3', null, 1, '站內消息管理');
insert into TB_FRAME_ROLES (ID, ROLE_CODE, ROLE_NAME, ROLE_LEVEL, ROLE_TYPE, PARENT_ROLE, SORT_NUM, REMARK)
values (917, 'CHIEF_SUB_ROLE_SEARCH_BILL', '总监子账号授权角色', '1', '3', null, 1, '注單搜索');
commit;
prompt 55 records loaded
prompt Loading TB_FRAME_ROLE_FUNC...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6170, 2, 5390, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5521, 6, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5217, 4, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5218, 4, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5219, 4, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5220, 4, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5312, 51, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5231, 8, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5313, 51, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5314, 52, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5180, 3, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5181, 4, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5315, 52, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5316, 53, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5317, 53, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5318, 54, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5319, 54, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5320, 60, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5321, 60, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5360, 7, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5361, 6, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5362, 5, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5363, 4, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5364, 3, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5441, 60, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4800, 1, 4700, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4801, 1, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4802, 2, 4700, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4803, 2, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4640, 2, 4600, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4760, 3, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4920, 10, 4820, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5292, 31, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5182, 5, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5183, 6, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5184, 7, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5185, 20, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5186, 21, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5187, 22, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5203, 7, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5204, 7, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5205, 7, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5206, 7, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5212, 5, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5213, 5, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5214, 5, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5215, 5, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5222, 3, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5293, 31, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5294, 32, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5295, 32, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5296, 33, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5297, 33, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5298, 34, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5299, 34, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5300, 40, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5301, 40, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5302, 41, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5303, 41, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5304, 42, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5305, 42, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5308, 44, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5309, 44, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5796, 44, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5341, 24, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4560, 2, 4520, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4620, 2, 4580, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5342, 33, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5343, 43, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5344, 53, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5381, 3, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5382, 4, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5400, 8, 4960, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5502, 2, 5020, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5572, 3, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5596, 6, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5597, 7, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5650, 3, 5190, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5651, 3, 5230, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5652, 3, 5210, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5670, 3, 5255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5795, 43, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5797, 50, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5935, 11, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5936, 12, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5937, 13, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5938, 14, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5939, 11, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5940, 12, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5941, 13, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5680, 3, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5681, 3, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5682, 3, 103, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5683, 3, 5130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5684, 3, 4960, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5719, 10, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5720, 10, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5721, 10, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5722, 10, 122, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5723, 10, 123, '1', null);
commit;
prompt 100 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5724, 10, 5255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5725, 10, 5254, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5726, 10, 116, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5727, 10, 114, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5728, 10, 115, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5729, 10, 5190, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5730, 10, 5230, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5731, 10, 5210, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5732, 10, 5171, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5733, 10, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5734, 10, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5735, 10, 5090, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5736, 10, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5737, 10, 312, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5738, 10, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5739, 10, 5150, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5740, 10, 5071, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5741, 10, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5742, 10, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5743, 10, 5130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5744, 10, 215, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5745, 10, 412, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5746, 10, 217, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5747, 10, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5748, 10, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5749, 10, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5750, 10, 5030, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5751, 10, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5752, 10, 5112, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5753, 20, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5754, 20, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5942, 14, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5950, 10, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5951, 10, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5952, 10, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5953, 10, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5954, 10, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5955, 10, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5956, 10, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5957, 10, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5958, 10, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5959, 10, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5960, 10, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5961, 10, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5962, 10, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5963, 10, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5964, 10, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5965, 10, 274, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5966, 10, 292, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5968, 11, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5969, 11, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5970, 11, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5971, 11, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6004, 10, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6005, 11, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6006, 12, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6007, 13, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6008, 14, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6009, 11, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6010, 12, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6011, 13, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6012, 14, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6030, 3, 5310, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6050, 3, 5350, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6070, 3, 5254, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6090, 2, 5370, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5530, 3, 5030, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5594, 4, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5595, 5, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5610, 3, 5112, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5685, 4, 5090, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5767, 30, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5768, 30, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5769, 31, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5770, 31, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5798, 51, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5799, 52, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5800, 53, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5693, 4, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5694, 4, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5695, 4, 4781, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5696, 4, 4720, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5697, 4, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5698, 4, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5699, 4, 4960, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5700, 3, 5150, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5701, 3, 4660, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5702, 3, 4640, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5703, 3, 4600, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5714, 7, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5715, 7, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5716, 7, 4801, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5717, 7, 4720, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5718, 7, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5762, 23, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5763, 23, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5801, 60, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5802, 61, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5803, 62, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5804, 63, 5073, '1', null);
commit;
prompt 200 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5805, 64, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5810, 911, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5811, 911, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5812, 911, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5813, 912, 5255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5814, 912, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5815, 913, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5816, 913, 5090, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5817, 913, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5818, 913, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5819, 913, 4781, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5820, 914, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5821, 914, 4780, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5822, 915, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5823, 915, 5150, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5824, 915, 5071, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5825, 915, 4801, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5826, 915, 4802, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5827, 915, 5130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5828, 915, 215, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5829, 915, 217, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5847, 922, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5848, 922, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5849, 922, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5850, 922, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5851, 923, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5852, 923, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5853, 923, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5854, 924, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5855, 924, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5856, 924, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5857, 2, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5858, 2, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5859, 2, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5860, 2, 5255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5861, 2, 5254, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5862, 2, 116, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5863, 2, 114, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5864, 2, 115, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5865, 2, 5190, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5866, 2, 5230, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5867, 2, 5210, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5868, 2, 5171, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5869, 2, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5870, 2, 5091, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5871, 2, 5090, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5872, 2, 138, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5873, 2, 140, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5874, 2, 143, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5875, 2, 144, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5876, 2, 142, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5877, 2, 4740, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5878, 2, 5252, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5879, 2, 5253, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5880, 2, 5251, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5881, 2, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5882, 2, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5883, 2, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5884, 2, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5885, 2, 5150, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5886, 2, 5071, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5887, 2, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5888, 2, 4801, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5889, 2, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5890, 2, 4781, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5891, 2, 4780, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5892, 2, 4760, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5893, 2, 4720, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5894, 2, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5895, 2, 4802, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5896, 2, 103, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5897, 2, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5898, 2, 5130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5899, 2, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5900, 2, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5901, 2, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5902, 2, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5903, 2, 5030, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5904, 2, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5905, 2, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5906, 2, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5907, 2, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5908, 2, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5909, 2, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5910, 2, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5911, 2, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5912, 2, 4960, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5913, 2, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5914, 2, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5915, 2, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5916, 2, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5917, 2, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5918, 2, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5919, 2, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5920, 2, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5921, 2, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5922, 2, 5112, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5923, 2, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5924, 2, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5925, 2, 4880, '1', null);
commit;
prompt 300 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5926, 2, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5927, 2, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5928, 2, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5929, 2, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5972, 11, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5973, 11, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5974, 12, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5975, 12, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5976, 12, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5977, 12, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5978, 12, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5979, 12, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5980, 13, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5981, 13, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5982, 13, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5983, 13, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5984, 13, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5985, 13, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5986, 14, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5987, 14, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5988, 14, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5989, 14, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5990, 14, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5991, 14, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5992, 41, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5993, 41, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5994, 41, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5995, 41, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5996, 51, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5997, 51, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5998, 51, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5999, 51, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6000, 61, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6001, 61, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6002, 61, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6003, 61, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6032, 3, 5330, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6051, 910, 5350, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5550, 3, 5050, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5755, 21, 5255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5756, 21, 5190, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5757, 21, 5230, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5758, 21, 5210, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5759, 21, 5171, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5760, 21, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5761, 22, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5764, 24, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5765, 24, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5766, 24, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5790, 32, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5791, 33, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5792, 34, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5793, 40, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5794, 41, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5806, 910, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5807, 910, 4801, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5808, 910, 4802, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5809, 910, 5112, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5830, 916, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5831, 916, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5832, 916, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5833, 916, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5834, 916, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5835, 916, 4840, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5836, 916, 4960, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5837, 916, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5838, 917, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5839, 917, 4720, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5840, 920, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5841, 920, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5842, 920, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5843, 921, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5844, 921, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5845, 921, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5846, 921, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5930, 8, 5252, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5931, 8, 5253, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5932, 8, 5251, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5933, 8, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5934, 8, 103, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5943, 921, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5944, 921, 4920, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5945, 922, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5967, 21, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6031, 915, 5310, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6110, 6, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5570, 3, 5071, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5590, 4, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5591, 5, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5592, 6, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5593, 7, 5073, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5630, 3, 5171, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5704, 5, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5705, 5, 5090, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5706, 5, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5707, 5, 312, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5708, 5, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5709, 5, 5270, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5710, 5, 4781, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5711, 5, 4720, '1', null);
commit;
prompt 400 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5712, 5, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5713, 5, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4720, 2, 4640, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4600, 3, 4560, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5480, 3, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4680, 900, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4820, 3, 4720, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4821, 3, 4700, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4880, 3, 4760, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4881, 3, 4802, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4882, 3, 4801, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4883, 3, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4884, 3, 4781, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4885, 3, 4780, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4886, 7, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4887, 6, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4891, 6, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4892, 7, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4940, 10, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5005, 33, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5006, 32, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5007, 32, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5008, 31, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5009, 31, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5010, 31, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5011, 30, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5440, 20, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5013, 23, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5014, 22, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5015, 22, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5016, 21, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5017, 21, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5018, 21, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5243, 23, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5242, 22, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5244, 24, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5245, 30, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5120, 7, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5143, 3, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5160, 3, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5161, 3, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5162, 3, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5265, 60, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5345, 63, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5031, 40, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5040, 51, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5041, 51, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5042, 51, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5043, 41, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5044, 41, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5045, 41, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5046, 41, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5047, 41, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5048, 41, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5049, 31, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5050, 31, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5051, 31, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5052, 31, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5053, 31, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5054, 31, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5055, 31, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5056, 31, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5057, 31, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5058, 21, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5059, 21, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5060, 21, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5061, 21, 114, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5062, 21, 116, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5063, 21, 115, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5064, 21, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5065, 21, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5066, 21, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5067, 21, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5068, 21, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5069, 21, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4500, 7, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4501, 7, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4660, 4, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4661, 3, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4662, 5, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4740, 2, 4660, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4761, 4, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4780, 3, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4860, 8, 4740, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5019, 20, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5241, 21, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5140, 6, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5141, 5, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5240, 20, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5284, 22, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5285, 22, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5286, 23, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5287, 23, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5288, 24, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5289, 24, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5290, 30, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5291, 30, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5380, 7, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5420, 6, 4940, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5421, 5, 4940, '1', null);
commit;
prompt 500 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5460, 2, 4980, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4520, 2, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4522, 4, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1078, 4, 130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1079, 4, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1080, 4, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1081, 4, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1082, 4, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1083, 4, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1084, 4, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1085, 4, 274, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1086, 4, 292, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1087, 4, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1088, 4, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1089, 4, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1090, 4, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1091, 4, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1092, 4, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1093, 4, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1094, 4, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1095, 4, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1096, 4, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1097, 4, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1098, 4, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1099, 4, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1100, 4, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1101, 3, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1102, 3, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1106, 5, 123, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1107, 5, 122, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1111, 5, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1112, 5, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1113, 5, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1114, 5, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1115, 5, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1116, 5, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1119, 5, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1120, 5, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1121, 5, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1122, 5, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1123, 5, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1124, 5, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1125, 5, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1126, 5, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1127, 5, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1128, 5, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1129, 5, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1130, 5, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1131, 5, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1132, 5, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1133, 6, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1134, 6, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1135, 6, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1136, 6, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1137, 6, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1138, 6, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1139, 6, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1140, 6, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1141, 6, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1142, 6, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1143, 6, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1144, 6, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1145, 6, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1146, 6, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1147, 6, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1148, 6, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1149, 6, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1150, 6, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1151, 6, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1152, 6, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1153, 7, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1154, 7, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1155, 7, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1156, 7, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1157, 7, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1158, 7, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1000, 1, 53, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1001, 1, 54, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1002, 1, 52, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1003, 1, 34, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1004, 1, 76, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1005, 1, 88, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1006, 1, 89, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1007, 1, 75, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1008, 1, 392, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1009, 1, 77, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1010, 1, 74, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1011, 1, 78, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1012, 1, 80, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1013, 1, 81, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1014, 1, 82, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1015, 1, 83, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1016, 1, 86, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1017, 1, 85, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1018, 1, 84, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1019, 1, 79, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1020, 1, 72, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1021, 1, 87, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1022, 1, 90, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1023, 1, 91, '1', null);
commit;
prompt 600 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1024, 1, 493, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1025, 1, 33, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1026, 1, 32, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1027, 1, 15, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1028, 1, 252, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1029, 1, 258, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1030, 1, 257, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1031, 1, 256, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1032, 1, 255, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1033, 1, 253, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1034, 1, 254, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1035, 1, 373, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1036, 1, 14, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4888, 5, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1175, 3, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1176, 3, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1177, 3, 353, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1178, 3, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1179, 3, 274, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1180, 3, 292, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1181, 3, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1182, 3, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1183, 3, 213, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1184, 3, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1185, 3, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1186, 3, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1187, 3, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1188, 3, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1189, 3, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1190, 3, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1191, 3, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1192, 3, 155, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1193, 3, 154, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1194, 3, 153, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1202, 7, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1203, 6, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1204, 5, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1205, 4, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1220, 2, 274, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1221, 2, 292, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1040, 3, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1041, 3, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1042, 3, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1043, 3, 123, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1044, 3, 122, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1045, 3, 114, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1046, 3, 116, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1047, 3, 115, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1048, 3, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1049, 3, 313, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1050, 3, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1051, 3, 312, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1052, 3, 133, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1053, 3, 356, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1054, 3, 130, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1055, 3, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1057, 3, 412, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1058, 3, 215, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1059, 3, 217, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1060, 2, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1061, 2, 412, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1062, 2, 215, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1063, 2, 217, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1064, 4, 120, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1065, 4, 119, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1066, 4, 118, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1067, 4, 123, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1068, 4, 122, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1069, 4, 114, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1070, 4, 116, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1071, 4, 115, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1072, 4, 135, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1073, 4, 313, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1074, 4, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1075, 4, 312, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1076, 4, 133, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1077, 4, 356, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1159, 7, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1160, 7, 159, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1161, 7, 158, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1162, 7, 157, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1163, 7, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1164, 7, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1165, 7, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1166, 7, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1167, 8, 144, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1168, 8, 142, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1169, 8, 143, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1170, 8, 140, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1171, 8, 138, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4889, 4, 4800, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1173, 8, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1200, 2, 123, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1201, 2, 122, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1222, 2, 313, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1223, 2, 131, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1224, 2, 312, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1225, 2, 133, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1226, 2, 356, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1227, 2, 130, '1', null);
commit;
prompt 700 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1228, 2, 129, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1240, 8, 432, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1241, 8, 452, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1037, 2, 126, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1038, 2, 125, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (1039, 2, 127, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4663, 6, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4664, 7, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4890, 5, 4680, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4701, 1, 102, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4980, 64, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4981, 63, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4982, 62, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4983, 62, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4984, 61, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4985, 61, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4986, 61, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4987, 60, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4988, 54, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4989, 53, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4990, 52, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4991, 52, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4992, 51, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4993, 51, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4994, 51, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4995, 50, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4996, 44, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4997, 43, 473, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4998, 42, 354, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (4999, 42, 355, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5000, 41, 174, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5001, 41, 175, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5002, 41, 173, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5003, 40, 218, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5004, 34, 474, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5209, 60, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5207, 53, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5197, 42, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5195, 40, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5196, 41, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5193, 33, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5194, 34, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5191, 31, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5192, 32, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5189, 24, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5190, 30, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5188, 23, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5142, 4, 4860, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5198, 43, 492, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5199, 44, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5200, 50, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5201, 51, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5202, 52, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5208, 54, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5211, 64, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5216, 62, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5221, 61, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5223, 3, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5224, 4, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5225, 5, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5226, 6, 4880, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5227, 6, 4881, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5228, 6, 4882, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5229, 6, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5230, 7, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5246, 31, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5247, 32, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5248, 33, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5249, 34, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5250, 40, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5251, 41, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5252, 42, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5253, 43, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5254, 44, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5255, 50, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5256, 51, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5257, 52, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5258, 53, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5259, 54, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5260, 64, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5261, 63, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5262, 63, 4900, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5263, 62, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5264, 61, 4901, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5280, 20, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5281, 20, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5282, 21, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5283, 21, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5306, 43, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5307, 43, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5310, 50, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5311, 50, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5322, 61, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5323, 61, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5324, 62, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5325, 62, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5326, 63, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5327, 63, 4620, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5328, 64, 333, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5329, 64, 4620, '1', null);
commit;
prompt 800 records committed...
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (5520, 7, 5000, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6150, 6, 5290, '1', null);
insert into TB_FRAME_ROLE_FUNC (ID, ROLE_ID, FUNC_ID, AUTHORIZ_TYPE, REMARK)
values (6130, 10, 5330, '1', null);
commit;
prompt 803 records loaded
set feedback on
set define on
prompt Done.
