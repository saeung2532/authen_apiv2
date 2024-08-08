package com.br.api.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.br.api.models.m3.Company;

@Repository
public class CompanyDao {

	protected static final Logger logger = LogManager.getLogger(CompanyDao.class);

	@Value("${spring.datasource.m3.schema}")
	private String schemaM3;

	@Value("${spring.datasource.addon.schema}")
	private String schemaAddon;

	private JdbcTemplate jdbcTemplate;

	public CompanyDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Company> getAllCompany() {
		logger.info("Repository: getAllCompany");
		return jdbcTemplate.query(
				"SELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI, TRIM(CCROW3) AS CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCROW3) AS COMPANY\n"
						+ "FROM M3FDBPRD.CMNDIV \n"
						 + "WHERE CCDIVI != '' \n"
//						+ "WHERE CCDIVI = '111111111' \n"
						+ "ORDER BY CCCONO",
				BeanPropertyRowMapper.newInstance(Company.class));
	}

	// public List<Company> getAllCompany() {
	// logger.info("Repository: getAllCompany");
	//
	// jdbcTemplate.query(
	// "SELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI,
	// TRIM(CCROW3) AS CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' ||
	// TRIM(CCROW3) AS COMPANY\n"
	// + "FROM M3FDBPRD.CMNDIV \n"
	// + "WHERE CCDIVI != '' \n"
	// + "ORDER BY CCCONO",
	// BeanPropertyRowMapper.newInstance(Company.class));
	//
	// try {
	// List<Company> company = jdbcTemplate.query(
	// "sSELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI,
	// TRIM(CCROW3) AS CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' ||
	// TRIM(CCROW3) AS COMPANY\n"
	// + "FROM M3FDBPRD.CMNDIV \n"
	// + "WHERE CCDIVI != '' \n"
	// + "ORDER BY CCCONO",
	// BeanPropertyRowMapper.newInstance(Company.class));
	//
	// return company;
	// } catch (DataAccessException e) {
	// throw new DatabaseException(e.getMessage(), e);
	// }
	// }

}
