package com.npc.lottery.monitoring.dao.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.npc.lottery.monitoring.dao.interf.IMonitoringInfoDao;
import com.npc.lottery.monitoring.entity.MonitoringInfo;

public class MonitoringInfoDao implements IMonitoringInfoDao {
	public static final String driverClassName = "com.mysql.jdbc.Driver";
	static {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private String url;
	private String userName;
	private String password;

	public static final String SQL = "insert into tb_monitoring( `play_type`, `shops`, `period`, `tran_type`, `status`, `describe`, `create_date`, `when`) values(?,?,?,?,?,?,?,?)";

	@Override
	public void saveMonitoringInfo(MonitoringInfo monInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection conn = null;
		PreparedStatement pst = null;
			try {
	
				conn = DriverManager.getConnection(url, userName, password);
				pst = conn.prepareStatement(SQL);
				pst.setString(1, monInfo.getPlayType());
				pst.setString(2, monInfo.getShops());
				pst.setString(3, monInfo.getPeriods());
				pst.setString(4, monInfo.getTranType());
				pst.setInt(5, monInfo.getStatus());
				pst.setString(6, monInfo.getDescribe());
				pst.setString(7, sdf.format(monInfo.getCreateDate()));
				pst.setInt(8, monInfo.getWhen());
				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pst != null) {
					try {
						pst.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
