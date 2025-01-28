package com.sorryisme.fmarket.mapper


import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IdempotencyKeyMapperTest extends Specification {

    @Autowired
    private IdempotencyKeyMapper idempotencyKeyMapper

    private static final String UUID = "166f9067-2e5f-4932-e314-f438ae846d24"

    def "같은 UUID로 2번 INSERT 시 첫번째만 저장되고 두번 째는 무시된다"() {

        when:
        int expectedInsert = idempotencyKeyMapper.insertIgnoreIdempotencyKey(UUID)
        int expectedIgnore = idempotencyKeyMapper.insertIgnoreIdempotencyKey(UUID)

        then:
        expectedInsert > 0
        expectedIgnore <= 0

    }

}
