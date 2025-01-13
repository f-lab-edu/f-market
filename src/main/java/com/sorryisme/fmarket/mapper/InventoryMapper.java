package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Inventory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<Inventory> findStockQuantityForUpdate(List<Inventory> inventories);
    int increaseStockQuantity(Inventory inventory);
    int updateStockQuantity(List<Inventory> inventory);
}
