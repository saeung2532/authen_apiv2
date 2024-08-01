package com.br.api.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.br.api.models.Item;

@Repository
public class ItemDao {

	@Value("${spring.datasource.m3.schema}")
	private String schemaM3;

	@Value("${spring.datasource.addon.schema}")
	private String schemaAddon;

	private JdbcTemplate jdbcTemplate;

	public ItemDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Item> getAllItem() {
		return jdbcTemplate.query("SELECT MMCONO, MMITNO, MMFUDS, MMUNMS, MMSTAT \n"
				+ "FROM " + schemaM3 + ".MITMAS \n"
				+ "WHERE MMCONO = '10' \n"
				+ "AND MMSTAT = '20' \n"
				+ "ORDER BY MMITNO",
				BeanPropertyRowMapper.newInstance(Item.class));
	}

}
