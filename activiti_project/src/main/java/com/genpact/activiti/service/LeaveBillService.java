package com.genpact.activiti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genpact.activiti.dao.LeaveBillDao;
import com.genpact.activiti.entity.LeaveBill;

@Service
public class LeaveBillService {
	@Autowired
	private LeaveBillDao leaveBillDao ;

	public List<LeaveBill> findByUserId(Long id) {
		return leaveBillDao.findByUserIdAndDeleteFlag(id,0);
			
	}

	public boolean save(LeaveBill leaveBill) {
		LeaveBill obj =   leaveBillDao.save(leaveBill);
		if(obj != null){
			return true;
		}else{
			return false;
		}
	}

	public boolean delete(Long id) {
		LeaveBill leaveBill = leaveBillDao.findOne(id);
		leaveBill.setDeleteFlag(1);
		return leaveBillDao.save(leaveBill)== null?false:true;
	}

	public LeaveBill findById(Long id) {
		return leaveBillDao.findOne(id);
		
	}
	
}
