package com.br.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.api.daos.ItemDao;
import com.br.api.models.Item;

@Service
public class ItemServiceImpl implements ItemService {
	
	private ItemDao itemDao;

	public ItemServiceImpl(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	@Override
	public List<Item> getAllItem() {
		return itemDao.getAllItem();
	}


}
