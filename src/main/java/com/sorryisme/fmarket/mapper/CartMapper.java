
package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Cart;
import com.sorryisme.fmarket.domain.CartDetail;
import com.sorryisme.fmarket.domain.Store;
import com.sorryisme.fmarket.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartMapper {

    @Select("SELECT id FROM CART WHERE user_id = #{userId}")
    Long findCartIdByUserId(Long userId);
    int insertCart(Cart cart);
    int insertCartDetail(CartDetail cartDetail);

    @Delete("DELETE FROM cart_detail WHERE id = #{id} ")
    int deleteCartDetailById(Long cartDetailId);
    boolean isExistCartDetailById(Long cartDetailId);
}