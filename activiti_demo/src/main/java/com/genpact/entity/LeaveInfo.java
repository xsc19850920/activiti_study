package com.genpact.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeaveInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int leaveDays;
	private Date leaveDate;
	private String reason;
	public int getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LeaveInfo() {
	}
	public LeaveInfo(int leaveDays, Date leaveDate, String reason) {
		this.leaveDays = leaveDays;
		this.leaveDate = leaveDate;
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "LeaveInfo [leaveDays=" + leaveDays + ", leaveDate=" + leaveDate + ", reason=" + reason + "]";
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.roll(Calendar.MONTH, 1);
		d = c.getTime();
		System.out.println(format.format(d));
	}

}
