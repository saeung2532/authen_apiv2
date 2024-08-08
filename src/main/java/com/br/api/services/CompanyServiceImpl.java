package com.br.api.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.br.api.daos.CompanyDao;
import com.br.api.models.m3.Company;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	protected static final Logger logger = LogManager.getLogger(CompanyServiceImpl.class);
	
	private CompanyDao companyDao;

	public CompanyServiceImpl(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	public List<Company> getAllCompany() {
		logger.info("Service: getAllCompany");
		return companyDao.getAllCompany();
	}
	
//	@Override
//	public List<Company> getAllCompany() {
//		logger.info("Service: getAllCompany");
//		
//		try {
//			List<Company> company =  companyDao.getAllCompany();
//			return company;
//			
//		} catch (DatabaseException e) {
//			throw new DatabaseException(e.getMessage(), e);
//		}
//		
//		
//	}

}
