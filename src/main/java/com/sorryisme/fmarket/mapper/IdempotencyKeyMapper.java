
package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Cart;
import com.sorryisme.fmarket.domain.CartDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IdempotencyKeyMapper {

    @Select("SELECT EXISTS (SELECT id FROM idempotency_keys WHERE idempotency_key = #{idempotencyKey} FOR UPDATE)")
    boolean isExistIdempotencyKeyForUpdate(String idempotencyKey);

    @Insert("INSERT IGNORE INTO idempotency_keys(idempotency_key, created_at) values (#{idempotencyKey}, now())")
    int insertIgnoreIdempotencyKey(String idempotencyKey);

}