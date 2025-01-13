package com.sorryisme.fmarket.mapper

import com.sorryisme.fmarket.domain.Inventory
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InventoryMapperTest extends Specification {

    @Autowired
    private InventoryMapper inventoryMapper

    List<Inventory> inventories = DomainFixture.createInventories()

    def "productOptionId가 제공되면 Inventory 데이터가 조회된다"() {

        when:
        List<Inventory> inventory = inventoryMapper.findStockQuantityForUpdate(inventories)

        then:
        inventory.size() >= 1
        inventory.get(0).getProductOptionId() == 1L
        inventory.get(0).getQuantity() > 0
    }


    def "변경된 수량이 제공되면 정상적으로 증가 된다"() {

        given:
        int newQuantity = 10
        Inventory newInventory = DomainFixture.createInventory(1L, newQuantity)

        when:
        List<Inventory> beforeInventory = inventoryMapper.findStockQuantityForUpdate(inventories)
        int result = inventoryMapper.increaseStockQuantity(newInventory)
        List<Inventory> updatedInventory = inventoryMapper.findStockQuantityForUpdate(inventories)

        then:
        result == 1
        beforeInventory.get(0).getQuantity() + 10 == updatedInventory.get(0).getQuantity()
    }

    def "productOptionId와 quantity 리스트를 전달하면 inventory 테이블의 수량이 업데이트된다"() {

        given:
        List<Inventory> inventoryList = DomainFixture.createInventories()

        when:
        int result = inventoryMapper.updateStockQuantity(inventoryList)
        List<Inventory> updatedInventory = inventoryMapper.findStockQuantityForUpdate(inventoryList)

        then:
        result == inventoryList.size()
        inventoryList.get(0).getQuantity() == updatedInventory.get(0).getQuantity()
        inventoryList.get(1).getQuantity() == updatedInventory.get(1).getQuantity()
    }



}
