package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCT = Mappers.getMapper(UserMapper.class);
    UserVO entity2VO(User user);
}
