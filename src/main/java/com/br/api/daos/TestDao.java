package com.br.api.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.br.api.models.m3.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TestDao {

	protected static final Logger logger = LogManager.getLogger(TestDao.class);

	@Value("${spring.datasource.m3.schema}")
	private String schemaM3;

	@Value("${spring.datasource.addon.schema}")
	private String schemaAddon;

	private JdbcTemplate jdbcTemplate;

	private ObjectMapper objectMapper;

	public TestDao(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.objectMapper = objectMapper;
	}

	public String getTest() throws JsonProcessingException {
		logger.info("Repository: getTest");

		try {
			String sql = "SELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI, TRIM(CCROW3) AS CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCROW3) AS COMPANY\n"
					+ "FROM M3FDBPRD.CMNDIV \n"
					+ "WHERE CCDIVI != '' \n"
					+ "ORDER BY CCCONO";
			List<Company> company = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));

			return objectMapper.writeValueAsString(company);

		} catch (DataAccessException e) {
			logger.error("Database error occurred: {}", e.getMessage());
			throw e;
		} catch (JsonProcessingException e) {
			logger.error("Json error occurred: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error in executeQuery: {}", e.getMessage());
			throw e;
		}

	}

}
