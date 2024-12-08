package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Store;
import com.sorryisme.fmarket.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    int insertUser(User user);
    boolean isExistUser(String name, String phoneNumber);
    int insertStore(Store store);
    boolean isExistUserById(long userId);
    int updateUser(User user);

    @Select("SELECT * FROM \"USER\" WHERE id = #{id}")
    User findUserById(long id);

    @Select("SELECT id, salt, password FROM \"USER\" WHERE login_id = #{loginId}" )
    User findUserByLoginId(String loginId);
}
