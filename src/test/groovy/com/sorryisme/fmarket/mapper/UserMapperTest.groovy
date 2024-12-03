package com.sorryisme.fmarket.mapper

import com.sorryisme.fmarket.domain.Store
import com.sorryisme.fmarket.domain.User
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest extends Specification {

    @Autowired
    private UserMapper userMapper;

    def "유저 정보가 넘어오면 정상적으로 저장되고 id 생성한다"() {
        given:
        User saveUser = createUser()

        when:
        int result = userMapper.insertUser(saveUser)

        then:
        result >= 1
        saveUser.id != 0L
    }

    def "중복된 유저가 있으면 true 리턴한다"() {
        given:
        User saveUser = createUser()
        userMapper.insertUser(saveUser)

        when:
        boolean isUserExist = userMapper.isExistUser("테스트유저", "01012345678")

        then:
        isUserExist
    }

    def "상점 정보가 넘어오면 상점 정보가 저장되고 id가 생성된다"() {
        given:
        User saveUser = createUser()
        userMapper.insertUser(saveUser)

        Store store = Store.builder()
                .storeName("테스트 상점")
                .logoUrl("https://naver.com/test.jpg")
                .description("테스트 상점 설명")
                .businessNumber("123-89-12345")
                .userId(saveUser.getId())
                .build()

        when:
        int result = userMapper.insertStore(store)

        then:
        result >= 1
        store.storeId != 0L
    }

    def "유저 ID가 존재하면 true를 반환한다"() {
        given:
        User saveUser = createUser()
        userMapper.insertUser(saveUser)

        when:
        boolean isUserExistById = userMapper.isExistUserById(saveUser.getId())

        then:
        isUserExistById
    }

    def "유저 정보를 업데이트하면 정상적으로 반영된다"() {
        given:
        User saveUser = createUser()
        userMapper.insertUser(saveUser)

        User updateUser = createUpdateUser(saveUser.getId())

        when:
        int updateResult = userMapper.updateUser(updateUser)
        User updatedUser = userMapper.findUserById(saveUser.getId())

        then:
        updateResult == 1
        updatedUser.name == "업데이트된 이름"
        updatedUser.email == "updated_email@test.com"
        updatedUser.phoneNumber == "01199999999"
    }

    private static User createUser() {
        return User.builder()
                .loginId("testUser")
                .password("xptmxmqlalfqjsgh!")
                .salt("testSalt")
                .name("테스트유저")
                .email("test@naver.com")
                .phoneNumber("01012345678")
                .build()
    }

    private static User createUpdateUser(long id) {
        return User.builder()
                .id(id)
                .name("업데이트된 이름")
                .email("updated_email@test.com")
                .phoneNumber("01199999999")
                .build()
    }

}
