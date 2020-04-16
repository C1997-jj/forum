package com.forum.dao;

import com.forum.entity.Essay;
import com.forum.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * user持久层
 */
@Mapper
public interface EssayDao {
    String TABLE_NAME = "essay";
    String INSERT_FILDS = " userID, type, content, images, PV, likeCount, commentCount, status, createDate ";
    String SELECT_FILDS = " id, " + INSERT_FILDS;

    /**
     * 查找手机号是否唯一
     * @param tel
     * @return
     */
    @Select({"select", SELECT_FILDS, "from ", TABLE_NAME, " where tel = #{tel}"})
    LoginTicket getLoginTicketByTel(String tel);

    /**
     * 通过令牌值查找ticket
     * @param ticket
     * @return
     */
    @Select({"select", SELECT_FILDS, "from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket getLoginTicketByTicket(String ticket);

    /**
     *添加内容
     * @param essay
     */
    @Insert({"insert into ",TABLE_NAME, "(", INSERT_FILDS, ") values(#{userID},#{type},#{content},#{images}),#{PV},#{likeCount},#{commentCount},#{status},#{createDate})"})
    void addEssay(Essay essay);

    /**
     * 更新令牌
     * @param tel
     */
    @Update({"update ",TABLE_NAME, " set status = #{status} where tel = #{tel}"})
    void updateLoginTicketStatus(@Param("tel") String tel, @Param("status") Integer status);

    /**
     * 更新令牌时间
     * @param tel
     * @param expired
     */
    @Update({"update ",TABLE_NAME, " set expired = #{expired} where tel = #{tel}"})
    void updateLoginTicketExpired(@Param("tel") String tel, @Param("expired") Date expired);
}
