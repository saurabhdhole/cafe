package com.mm.ms.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.UserRepository;
import com.mm.ms.entity.User;

public class UserBean extends BaseAbstractBean<User, Long>{
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	UserRepository userRepository;

	@Override
	public User create(String logstr, User entity) {
		logger.debug(logstr + "create appUser " + entity.fetchLogDetails());	
		User appUser = userRepository.save(entity);
		logger.debug(logstr + "created appUser " + entity.fetchLogDetails());	
		return appUser;
	}

	@Override
	public User read(String logstr, Long id) {
		logger.debug(logstr + "get appUser at id " + id);	
		User appUser = userRepository.findOne(id);
		logger.debug(logstr + "got appUser " + appUser.fetchLogDetails());	
		return appUser;
	}

	@Override
	public Iterable<User> readAll(String logstr) {
		logger.debug(logstr + "get all appUser records");				
		Iterable<User> appUserRecords = userRepository.findAll();
		return appUserRecords;
	}

	@Override
	public Iterable<User> readAll(String logstr, Pageable pageable) {
		logger.debug(logstr + "get all appUser records pageable " + logdetails(pageable));				
		Iterable<User> appUserRecords = userRepository.findAll(pageable);
		return appUserRecords;
	}

	@Override
	public User update(String logstr, User tobemerged) {
		logger.debug(logstr + "update appUser " + tobemerged.fetchLogDetails());	
		User appUserOrig = userRepository.findOne(tobemerged.getId());
		User appUser = userRepository.save(appUserOrig.mergeUpdates(tobemerged));
		logger.debug(logstr + "updated appUser " + appUser.fetchLogDetails());	
		return appUser;
	}

	@Override
	public Boolean delete(String logstr, Long id) {
		logger.debug(logstr + "delete appUser at id " + id);	
		userRepository.delete(id);		
		logger.debug(logstr + "deleted appUser at id " + id);	
		return true;
	}	
}
