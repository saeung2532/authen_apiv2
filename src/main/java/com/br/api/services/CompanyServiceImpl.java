package com.br.api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.br.api.daos.CompanyDao;
import com.br.api.exceptions.CustomServiceException;

@Service
public class CompanyServiceImpl implements CompanyService {

	protected static final Logger logger = LogManager.getLogger(CompanyServiceImpl.class);

	private CompanyDao companyDao;

	public CompanyServiceImpl(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Override
	public String getAllCompany() {
		logger.info("Service: getAllCompany");
		try {
			return companyDao.getAllCompany();
			
		} catch (DataAccessException e) {
            logger.error("Data access error occurred in service layer: {}", e.getMessage());
            throw new CustomServiceException("Failed to perform operation", e);
        } catch (Exception e) {
            logger.error("Unexpected error in performOperation: {}", e.getMessage());
            throw new CustomServiceException("Unexpected error in service layer", e);
        }
				
	}

}
