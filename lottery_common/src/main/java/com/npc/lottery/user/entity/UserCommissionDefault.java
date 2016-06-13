
package com.npc.lottery.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserCommissionDefault implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7841338872316304122L;

    // fields
    private Long ID;
    private Long userId; // 用户ID
    private String userType; // 用户类型
    private String playType; // 玩法类型
    private String playFinalType; // 下注类型
    private Double commissionA; // 佣金A
    private Double commissionB; // 佣金b
    private Double commissionC; // 佣金c
    private Integer bettingQuotas; // 单注限额
    private Integer itemQuotas; // 单项（号）限额
    private Long createUser; // 创建人员
    private Date createTime; // 创建时间
    private Long modifyUser; // 编辑人员
    private Date modifyTime;
    private String playFinalTypeName; // 类型转换
    private Integer totalQuatas; // 公司总受注额实货
    private Integer lowestQuatas; // 单注最低
    private Integer winQuatas; // 最高派彩
    private Integer loseQuatas; // 负值超额警示

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getPlayFinalType() {
        return playFinalType;
    }

    public void setPlayFinalType(String playFinalType) {
        this.playFinalType = playFinalType;
    }

    public Double getCommissionA() {
        return commissionA;
    }

    public void setCommissionA(Double commissionA) {
        this.commissionA = commissionA;
    }

    public Double getCommissionB() {
        return commissionB;
    }

    public void setCommissionB(Double commissionB) {
        this.commissionB = commissionB;
    }

    public Double getCommissionC() {
        return commissionC;
    }

    public void setCommissionC(Double commissionC) {
        this.commissionC = commissionC;
    }

    public Integer getBettingQuotas() {
        return bettingQuotas;
    }

    public void setBettingQuotas(Integer bettingQuotas) {
        this.bettingQuotas = bettingQuotas;
    }

    public Integer getItemQuotas() {
        return itemQuotas;
    }

    public void setItemQuotas(Integer itemQuotas) {
    	this.itemQuotas = itemQuotas;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPlayFinalTypeName() {

        if (playFinalType.trim().equals("GD_ONE_BALL")) {
            return "第一球";
        } else if (playFinalType.trim().equals("GD_TWO_BALL")) {
            return "第二球";
        } else if (playFinalType.trim().equals("GD_THREE_BALL")) {
            return "第三球";
        } else if (playFinalType.trim().equals("GD_FOUR_BALL")) {
            return "第四球";
        } else if (playFinalType.trim().equals("GD_FIVE_BALL")) {
            return "第五球";
        } else if (playFinalType.trim().equals("GD_SIX_BALL")) {
            return "第六球";
        } else if (playFinalType.trim().equals("GD_SEVEN_BALL")) {
            return "第七球";
        } else if (playFinalType.trim().equals("GD_EIGHT_BALL")) {
            return "第八球";
        } else if (playFinalType.trim().equals("GD_OEDX_BALL")) {
            return "第1-8大小";
        } else if (playFinalType.trim().equals("GD_OEDS_BALL")) {
            return "第1-8單雙";
        } else if (playFinalType.trim().equals("GD_OEWSDX_BALL")) {
            return "第1-8尾數大小";
        } else if (playFinalType.trim().equals("GD_HSDS_BALL")) {
            return "第1-8合數單雙";
        } else if (playFinalType.trim().equals("GD_FW_BALL")) {
            return "第1-8方位";
        } else if (playFinalType.trim().equals("GD_ZF_BALL")) {
            return "第1-8中發白";
        } else if (playFinalType.trim().equals("GD_B_BALL")) {
            return "第1-8白";
        } else if (playFinalType.trim().equals("GD_ZHDX_BALL")) {
            return "總和大小";
        } else if (playFinalType.trim().equals("GD_ZHDS_BALL")) {
            return "總和單雙";
        } else if (playFinalType.trim().equals("GD_ZHWSDX_BALL")) {
            return "總和尾數大小";
        } else if (playFinalType.trim().equals("GD_LH_BALL")) {
            return "龍虎";
        } else if (playFinalType.trim().equals("GD_RXH_BALL")) {
            return "任選二";
        } else if (playFinalType.trim().equals("GD_RELZ_BALL")) {
            return "選二連直";
        } else if (playFinalType.trim().equals("GD_RTLZ_BALL")) {
            return "選二連組 ";
        } else if (playFinalType.trim().equals("GD_RXS_BALL")) {
            return "任選三";
        } else if (playFinalType.trim().equals("GD_XSQZ_BALL")) {
            return "選三前直 ";
        } else if (playFinalType.trim().equals("GD_XTQZ_BALL")) {
            return "選三前組";
        } else if (playFinalType.trim().equals("GD_RXF_BALL")) {
            return "任選四";
        } else if (playFinalType.trim().equals("GD_RXW_BALL")) {
            return "任選五";
        } else if (playFinalType.trim().equals("CQ_ONE_BALL")) {
            return "第一球";
        } else if (playFinalType.trim().equals("CQ_TWO_BALL")) {
            return "第二球";
        } else if (playFinalType.trim().equals("CQ_THREE_BALL")) {
            return "第三球";
        } else if (playFinalType.trim().equals("CQ_FOUR_BALL")) {
            return "第四球";
        } else if (playFinalType.trim().equals("CQ_FIVE_BALL")) {
            return "第五球";
        } else if (playFinalType.trim().equals("CQ_OFDX_BALL")) {
            return "第1-5大小";
        } else if (playFinalType.trim().equals("CQ_OFDS_BALL")) {
            return "第1-5單雙";
        } else if (playFinalType.trim().equals("CQ_ZHDX_BALL")) {
            return "總合大小";
        } else if (playFinalType.trim().equals("CQ_ZHDS_BALL")) {
            return "總合單雙";
        } else if (playFinalType.trim().equals("CQ_LH_BALL")) {
            return "龍虎和";
        } else if (playFinalType.trim().equals("CQ_QS_BALL")) {
            return "前三 ";
        } else if (playFinalType.trim().equals("CQ_ZS_BALL")) {
            return "中三";
        } else if (playFinalType.trim().equals("CQ_HS_BALL")) {
            return "後三";
        } else if (playFinalType.trim().equals("BJ_BALL_FIRST")) {
            return "冠軍";
        } else if (playFinalType.trim().equals("BJ_BALL_SECOND")) {
            return "亞軍";
        } else if (playFinalType.trim().equals("BJ_BALL_THIRD")) {
            return "第三名 ";
        } else if (playFinalType.trim().equals("BJ_BALL_FORTH")) {
            return "第四名";
        } else if (playFinalType.trim().equals("BJ_BALL_FIFTH")) {
            return "第五名";
        } else if (playFinalType.trim().equals("BJ_BALL_SIXTH")) {
            return "第六名";
        } else if (playFinalType.trim().equals("BJ_BALL_SEVENTH")) {
            return "第七名";
        } else if (playFinalType.trim().equals("BJ_BALL_EIGHTH")) {
            return "第八名";
        } else if (playFinalType.trim().equals("BJ_BALL_NINTH")) {
            return "第九名";
        } else if (playFinalType.trim().equals("BJ_BALL_TENTH")) {
            return "第十名";
        } else if (playFinalType.trim().equals("BJ_1-10_DX")) {
            return "1-10大小";
        } else if (playFinalType.trim().equals("BJ_1-10_DS")) {
            return "1-10單雙";
        } else if (playFinalType.trim().equals("BJ_1-5_LH")) {
            return "1-5龍虎";
        } else if (playFinalType.trim().equals("BJ_DOUBLSIDE_DX")) {
            return "冠亞軍和大小";
        } else if (playFinalType.trim().equals("BJ_DOUBLSIDE_DS")) {
            return "冠亞軍和單雙";
        } else if (playFinalType.trim().equals("BJ_GROUP")) {
            return "冠亞軍和";
        } else if (playFinalType.trim().equals("K3_DX")) {
	        return "大小";
	    } else if (playFinalType.trim().equals("K3_SJ")) {
	        return "三軍";
	    } else if (playFinalType.trim().equals("K3_WS")) {
	        return "圍骰";
	    } else if (playFinalType.trim().equals("K3_QS")) {
	        return "全骰";
	    } else if (playFinalType.trim().equals("K3_DS")) {
	        return "點數";
	    } else if (playFinalType.trim().equals("K3_CP")) {
	        return "長牌";
	    } else if (playFinalType.trim().equals("K3_DP")) {
	        return "短牌";
	    } else if (playFinalType.trim().equals("NC_ONE_BALL")) {
            return "第一球";
        } else if (playFinalType.trim().equals("NC_TWO_BALL")) {
            return "第二球";
        } else if (playFinalType.trim().equals("NC_THREE_BALL")) {
            return "第三球";
        } else if (playFinalType.trim().equals("NC_FOUR_BALL")) {
            return "第四球";
        } else if (playFinalType.trim().equals("NC_FIVE_BALL")) {
            return "第五球";
        } else if (playFinalType.trim().equals("NC_SIX_BALL")) {
            return "第六球";
        } else if (playFinalType.trim().equals("NC_SEVEN_BALL")) {
            return "第七球";
        } else if (playFinalType.trim().equals("NC_EIGHT_BALL")) {
            return "第八球";
        } else if (playFinalType.trim().equals("NC_OEDX_BALL")) {
            return "第1-8大小";
        } else if (playFinalType.trim().equals("NC_OEDS_BALL")) {
            return "第1-8單雙";
        } else if (playFinalType.trim().equals("NC_OEWSDX_BALL")) {
            return "第1-8尾數大小";
        } else if (playFinalType.trim().equals("NC_HSDS_BALL")) {
            return "第1-8合數單雙";
        } else if (playFinalType.trim().equals("NC_FW_BALL")) {
            return "第1-8方位";
        } else if (playFinalType.trim().equals("NC_ZF_BALL")) {
            return "第1-8中發白";
        } else if (playFinalType.trim().equals("NC_B_BALL")) {
            return "第1-8白";
        } else if (playFinalType.trim().equals("NC_ZHDX_BALL")) {
            return "總和大小";
        } else if (playFinalType.trim().equals("NC_ZHDS_BALL")) {
            return "總和單雙";
        } else if (playFinalType.trim().equals("NC_ZHWSDX_BALL")) {
            return "總和尾數大小";
        } else if (playFinalType.trim().equals("NC_LH_BALL")) {
            return "龍虎";
        } else if (playFinalType.trim().equals("NC_RXH_BALL")) {
            return "任選二";
        } else if (playFinalType.trim().equals("NC_RELZ_BALL")) {
            return "選二連直";
        } else if (playFinalType.trim().equals("NC_RTLZ_BALL")) {
            return "選二連組 ";
        } else if (playFinalType.trim().equals("NC_RXS_BALL")) {
            return "任選三";
        } else if (playFinalType.trim().equals("NC_XSQZ_BALL")) {
            return "選三前直 ";
        } else if (playFinalType.trim().equals("NC_XTQZ_BALL")) {
            return "選三前組";
        } else if (playFinalType.trim().equals("NC_RXF_BALL")) {
            return "任選四";
        } else if (playFinalType.trim().equals("NC_RXW_BALL")) {
            return "任選五";
        } 
        /*else if (playFinalType.trim().equals("HK_TA")) {
            return "特A";
        } else if (playFinalType.trim().equals("HK_TB")) {
            return "特B";
        } else if (playFinalType.trim().equals("HK_TM_DS")) {
            return "特碼單雙";
        } else if (playFinalType.trim().equals("HK_TM_DX")) {
            return "特碼大小";
        } else if (playFinalType.trim().equals("HK_TMHS_DS")) {
            return "特碼合數單雙 ";
        } else if (playFinalType.trim().equals("HK_TMWS_DX")) {
            return "特碼尾數大小 ";
        } else if (playFinalType.trim().equals("HK_TM_SB")) {
            return "特碼色波 ";
        } else if (playFinalType.trim().equals("HK_ZM")) {
            return "正碼";
        } else if (playFinalType.trim().equals("HK_ZT")) {
            return "正特";
        } else if (playFinalType.trim().equals("HK_ZM1TO6_DS")) {
            return "正碼1-6單雙";
        } else if (playFinalType.trim().equals("HK_ZM1TO6_DX")) {
            return "正碼1-6大小";
        } else if (playFinalType.trim().equals("HK_ZM1TO6_HSDS")) {
            return "正碼1-6合數單雙";
        } else if (playFinalType.trim().equals("HK_ZM1TO6_SB")) {
            return "正碼1-6色波";
        } else if (playFinalType.trim().equals("HK_LM")) {
            return "连碼";
        } else if (playFinalType.trim().equals("HK_TM_SX")) {
            return "特碼生肖";
        } else if (playFinalType.trim().equals("HK_SXWS_SX")) {
            return "生肖尾數(生肖)";
        } else if (playFinalType.trim().equals("HK_SXWS_WS")) {
            return "生肖尾數(尾數)";
        } else if (playFinalType.trim().equals("HK_BB")) {
            return "半波";
        } else if (playFinalType.trim().equals("HK_LX")) {
            return "六肖";
        } else if (playFinalType.trim().equals("HK_SXL")) {
            return "生肖連";
        } else if (playFinalType.trim().equals("HK_WSL")) {
            return "尾数連";
        } else if (playFinalType.trim().equals("HK_WBZ")) {
            return "五不中";
        } else if (playFinalType.trim().equals("HK_GG")) {
            return "過關";
        }else if (playFinalType.trim().equals("HK_ZHDS")) {
            return "總和單雙";
        }else if (playFinalType.trim().equals("HK_ZHDX")) {
            return "總和大小";
        }*/
        return "";
    }

    public void setPlayFinalTypeName(String playFinalTypeName) {
        this.playFinalTypeName = playFinalTypeName;
    }

    public Integer getTotalQuatas() {
        return totalQuatas;
    }

    public Integer getLowestQuatas() {
        return lowestQuatas;
    }

    public Integer getWinQuatas() {
        return winQuatas;
    }

    public Integer getLoseQuatas() {
        return loseQuatas;
    }

    public void setTotalQuatas(Integer totalQuatas) {
        this.totalQuatas = totalQuatas;
    }

    public void setLowestQuatas(Integer lowestQuatas) {
        this.lowestQuatas = lowestQuatas;
    }

    public void setWinQuatas(Integer winQuatas) {
        this.winQuatas = winQuatas;
    }

    public void setLoseQuatas(Integer loseQuatas) {
        this.loseQuatas = loseQuatas;
    }

}
