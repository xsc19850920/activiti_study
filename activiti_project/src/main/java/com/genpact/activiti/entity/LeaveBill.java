package com.genpact.activiti.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity(name = "LEAVE_BILL")
public class LeaveBill implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GenericGenerator(name="generator",strategy="auto")
	// @GeneratedValue(generator="generator")
	private Long id;
	
	/**
	 * 请假人信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 请假原因
	 */
	@Column(name = "reason")
	private String reason;
	
	/**
	 * 备注信息
	 */
	@Column(name = "remark")
	private String remark;
	
	
	/**
	 * 请假天数
	 */
	@Column(name = "leave_days")
	private int leaveDays;
	
	
	/**
	 * 流程状态
	 * 0 草稿
	 * 1 审批中
	 * 2 审批完成
	 */
	@Column(name = "state")
	private int state = 0;
	
	/**
	 * delete flag  default = 0;
	 */
	@Column(name = "delete_flag")
	private int deleteFlag = 0;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getLeaveDays() {
		return leaveDays;
	}


	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public int getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	@Override
	public String toString() {
		return "LeaveBill [id=" + id + ", user=" + user + ", reason=" + reason + ", remark=" + remark + ", leaveDays=" + leaveDays + ", state=" + state + "]";
	}


	public LeaveBill( User user, String reason, String remark, int leaveDays) {
		this.user = user;
		this.reason = reason;
		this.remark = remark;
		this.leaveDays = leaveDays;
	}
	
	public LeaveBill() {
	}
	
	
	
}
