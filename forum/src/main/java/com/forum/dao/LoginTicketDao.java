package com.forum.dao;

import com.forum.entity.LoginTicket;
import com.forum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * user持久层
 */
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "user";
    String INSERT_FILDS = "tel, ticket, status, expired ";
    String SELECT_FILEDS = "id, " + INSERT_FILDS;

    /**
     * 查找手机号是否唯一
     * @param tel
     * @return
     */
    @Select({"select", SELECT_FILEDS, "form ", TABLE_NAME, " where tel = #{tel}"})
    User getUserByTel(String tel);

    /**
     *添加令牌
     * @param loginTicket
     */
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILDS,") values (#{tel},#{ticket},#{status},#{expired})"})
    void addLoginTicket(LoginTicket loginTicket);
}
