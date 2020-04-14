package com.forum.dao;

import com.forum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * user持久层
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FILDS = "tel, password, salt, name, headLink";
    String SELECT_FILEDS = "id, " + INSERT_FILDS;

    /**
     * 查找手机号是否唯一
     * @param tel
     * @return
     */
    @Select({"select", SELECT_FILEDS, "form ", TABLE_NAME, " where tel = #{tel}"})
    User getUserByTel(String tel);

    /**
     *添加用户
     * @param user
     */
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILDS,") values (#{tel},#{password},#{salt},#{name},#{headLink})"})
    void addUser(User user);
}
