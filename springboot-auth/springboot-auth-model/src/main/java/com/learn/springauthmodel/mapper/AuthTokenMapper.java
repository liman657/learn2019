package com.learn.springauthmodel.mapper;

import com.learn.springauthmodel.entity.AuthToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthToken record);

    AuthToken selectByPrimaryKey(Integer id);

    List<AuthToken> selectAll();

    int updateByPrimaryKey(AuthToken record);

    int insertSelective(AuthToken record);

    void invalidateTokenByUser(@Param("userId") Integer userId);

    AuthToken selectByAccessToken(@Param("accessToken") String accessToken);

    int invalidateByToken(@Param("accessToken") String accessToken);

    int deleteUnactiveToken();
}