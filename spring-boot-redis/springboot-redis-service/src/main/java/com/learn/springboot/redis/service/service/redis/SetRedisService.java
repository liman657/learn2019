package com.learn.springboot.redis.service.service.redis;

import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.User;
import com.learn.springboot.redis.model.mapper.UserMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/29
 * comment:redis操作set的service
 */
@Slf4j
@Service
public class SetRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存中设置user的email
     * @param user
     */
    public void setUserEmail(User user){
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add(RedisKeyConstants.RedisSetKey+"_"+user.getId(),user.getEmail());
    }

    /**
     * 判断对应的用户邮箱是否存在
     * @param user
     * @return
     */
    public boolean isUserExistEmail(User user){
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        Boolean isExist = setOperations.isMember(RedisKeyConstants.RedisSetKey+"_"+user.getId(), user.getEmail());
        return isExist;
    }


    /**
     * 取出缓存中用户的邮箱
     * @param userId
     * @return
     * @throws Exception
     */
    public Set<String> getEmails(int userId) throws Exception{
        /*SetOperations<String,String> setOperations=redisTemplate.opsForSet();
        return setOperations.members(Constant.RedisSetKey);*/
        return redisTemplate.opsForSet().members(RedisKeyConstants.RedisSetKey+"_"+userId);

        //return setOperations.randomMembers(Constant.RedisSetKey,setOperations.size(Constant.RedisSetKey));
    }

}
