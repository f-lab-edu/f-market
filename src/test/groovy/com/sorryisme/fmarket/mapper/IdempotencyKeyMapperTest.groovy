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

    def "UUID가 제공되면 정상적으로 INSERT된다"() {

        when:
        int result = idempotencyKeyMapper.insertIdempotencyKey(UUID)

        then:
        result > 0

    }

    def "UUID가 조회가 되면 true를 리턴한다"() {

        given:
        idempotencyKeyMapper.insertIdempotencyKey(UUID);

        when:
        boolean isExistIdempotencyKey = idempotencyKeyMapper.isExistIdempotencyKeyForUpdate(UUID);

        then:
        isExistIdempotencyKey

    }

}
