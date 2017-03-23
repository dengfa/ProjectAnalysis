/**
 * Copyright (C) 2014 The KnowBoxTeacher-android Project
 */
package com.vincent.projectanalysis.bean.user;

import java.io.Serializable;

/**
 * 当前登录用户信息
 * @author yangzc
 *
 */
public class UserItem extends BaseItem implements Serializable {
	
	//用户ID
	public String userId;
	//学生ID
	public String studentId;
	//用户登录名
	public String loginName;
	//用户昵称
	public String userName;
	//任职学校
	public String school;
	//密码
	public String password;
	//唯一token
	public String token;
	//头像
	public String headPhoto;
	//性别
	public String sex;
	//生日
	public String birthday;
	//积分
	public int integral;

	@Override
	public String toString() {
		return "UserItem [userId=" + userId + ", loginName=" + loginName
				+ ", userName=" + userName + ", school=" + school
				+ ", password=" + password + ", token=" + token
				+ ", headPhoto=" + headPhoto + ", sex=" + sex + ", birthday="
				+ birthday + "]";
	}
	
	public BasicUserInfo toBasicUserInfo(){
		BasicUserInfo info = new BasicUserInfo();
		info.token = token;
		info.userId = userId;
		info.userName = userName;
		return info;
	}
}
