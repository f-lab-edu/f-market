package com.sorryisme.fmarket.service

import com.sorryisme.fmarket.domain.Store
import com.sorryisme.fmarket.domain.User
import com.sorryisme.fmarket.dto.request.SellerRequestDto
import com.sorryisme.fmarket.dto.request.UserRequestDto
import com.sorryisme.fmarket.dto.request.UserUpdateRequestDto
import com.sorryisme.fmarket.exception.DuplicateDataException
import com.sorryisme.fmarket.exception.NotFoundDataException
import com.sorryisme.fmarket.exception.UpdateFailException
import com.sorryisme.fmarket.mapper.UserMapper
import com.sorryisme.fmarket.utils.PasswordCipher
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserMapper userMapper = Mock()


    def "유저 생성 시 중복된 유저를 생성하고자 하면 에러를 발생시킨다"() {
        given:
        userMapper.isExistUser(_ as String, _ as String) >> true
        UserService userService = new UserService(userMapper)

        when:
        def requestDto = createUserRequestDto()
        userService.createUser(requestDto)

        then:
        def e = thrown(DuplicateDataException.class);
        e.getMessage() == "이미 등록된 유저입니다."
    }

    def "유저 생성 시 정상적으로 저장되면 저장된 정보가 리턴된다"() {
        given:
        userMapper.isExistUser(_ as String, _ as String) >> false
        userMapper.insertUser(_ as User) >> 1
        UserService userService = new UserService(userMapper)

        when:
        def requestDto = createUserRequestDto()
        def result = userService.createUser(requestDto)

        then:
        result.getLoginId() == requestDto.getLoginId()
        result.getEmail() == requestDto.getEmail()
        result.getName() == requestDto.getName()
    }


    def "판매자 생성 시 정상적으로 저장되면 저장된 정보가 리턴된다"() {
        given:
        userMapper.isExistUser(_ as String, _ as String) >> false
        userMapper.insertUser(_ as User) >> 1
        userMapper.insertStore(_ as Store ) >> 1
        UserService userService = new UserService(userMapper)

        when:
        def requestDto = createSellerRequestDto()
        def result = userService.createSeller(requestDto)

        then:
        result.getLoginId() == requestDto.getLoginId()
        result.getEmail() == requestDto.getEmail()
        result.getName() == requestDto.getName()
        result.getStoreName() == requestDto.getStoreName()
        result.getBusinessNumber() == requestDto.getBusinessNumber()
    }

    def "유저 업데이트 시 존재하지 않는 유저라면 예외를 발생시킨다"() {
        given:
        userMapper.isExistUserById(_ as Long) >> false
        UserService userService = new UserService(userMapper)

        when:
        def userId = 1L
        def updateRequestDto = createUpdateRequestDto()
        userService.updateUser(updateRequestDto, userId)

        then:
        def e = thrown(NotFoundDataException)
        e.getMessage() == "찾을 수 없는 유저입니다"
    }

    def "유저 업데이트 시 정상적으로 수정되면 수정된 유저 ID를 반환한다"() {
        given:
        userMapper.isExistUserById(_ as Long) >> true
        userMapper.updateUser(_ as User) >> 1
        UserService userService = new UserService(userMapper)

        when:
        def userId = 1L
        def updateRequestDto = createUpdateRequestDto()
        def result = userService.updateUser(updateRequestDto, userId)

        then:
        result == userId
    }

    def "유저 업데이트 시 수정이 실패하면 예외를 발생시킨다"() {
        given:
        userMapper.isExistUserById(_ as Long) >> true
        userMapper.updateUser(_ as User) >> 0
        UserService userService = new UserService(userMapper)

        when:
        def userId = 1L
        def updateRequestDto = createUpdateRequestDto()
        userService.updateUser(updateRequestDto, userId)

        then:
        def e = thrown(UpdateFailException)
        e.getMessage() == "정보 수정에 실패했습니다."
    }

    def "로그인 시 유저가 없는 경우 에러를 발생시킨다"() {
        given:
        userMapper.findUserByLoginId(_ as String) >> null
        UserService userService = new UserService(userMapper)

        when:
        userService.login("null", "1234")

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "찾을 수 없는 유저입니다."
    }

    def "로그인 시 비밀번호 틀린 경우 에러를 발생시킨다"() {
        given:
        User user = createUser()
        userMapper.findUserByLoginId(_ as String) >> user
        UserService userService = new UserService(userMapper)

        when:
        userService.login("testUser", "1234567")

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "로그인정보가 일치하지 않습니다."
    }

    def "로그인 성공 시 id를 반환한다"() {
        given:
        User user = createUser()
        userMapper.findUserByLoginId(_ as String) >> user
        UserService userService = new UserService(userMapper)

        when:
        Long id = userService.login("testUser", "xptmxm")

        then:
        id == 1L
    }



    private static UserRequestDto createUserRequestDto() {
        return UserRequestDto.builder()
                .loginId("testUser")
                .password("xptmxmqlalfqjsgh!")
                .name("테스트유저")
                .email("test@naver.com")
                .phoneNumber("01012345678")
                .build()
    }

    private static SellerRequestDto createSellerRequestDto() {
        return SellerRequestDto.builder()
                .loginId("testUser")
                .password("xptmxmqlalfqjsgh!")
                .name("테스트유저")
                .email("test@naver.com")
                .phoneNumber("01012345678")
                .storeName("테스트 상점")
                .businessNumber("012-12-34567")
                .logoUrl("https://naver.com/test.jpg")
                .description("설명테스트")
                .build()
    }

    private static UserUpdateRequestDto createUpdateRequestDto() {
        return UserUpdateRequestDto.builder()
                .name("변경된 이름")
                .email("updated_email@naver.com")
                .phoneNumber("01098765432")
                .build()
    }

    private static User createUser() {
        var salt = PasswordCipher.getSalt();
        return User.builder()
                .id(1L)
                .loginId("testUser")
                .password(PasswordCipher.encrypt("xptmxm", salt))
                .salt(salt)
                .name("테스트유저")
                .email("test@naver.com")
                .phoneNumber("01012345678")
                .build()
    }


}
