package com.learn.springboot.redis.service.service.problem;

import com.google.common.collect.Sets;
import com.learn.springboot.redis.model.entity.Problem;
import com.learn.springboot.redis.model.mapper.ProblemMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/30
 * comment:验证的问题业务逻辑类
 */
@Service
@Slf4j
public class ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init(){
        initDBToRedisCache();
    }

    private void initDBToRedisCache(){
        try{
            SetOperations<String,Problem> setOperations = redisTemplate.opsForSet();
            Set<Problem> problemSet = problemMapper.getAll();
            if(problemSet!=null && !problemSet.isEmpty()){
//                problemSet.forEach(problem -> setOperations.add(RedisKeyConstants.RedisSetProblemKey,problem));
                problemSet.forEach(problem -> setOperations.add(RedisKeyConstants.RedisSetProblemsKey,problem));
            }
        }catch (Exception e){
            log.error("项目启动拉取出数据库中的问题库，并塞入缓存Set集合中-发生异常：",e.fillInStackTrace());
        }
    }


    /**
     * 随机获取一个问题
     * @return
     */
    public Problem getRandomProblemEntity(){
        Problem problem = null;
        try{
            SetOperations<String,Problem> setOperations = redisTemplate.opsForSet();
            Long size = setOperations.size(RedisKeyConstants.RedisSetProblemsKey);
            if(size>0){
                problem = setOperations.pop(RedisKeyConstants.RedisSetProblemsKey);
            }else{
                this.initDBToRedisCache();
                problem = setOperations.pop(RedisKeyConstants.RedisSetProblemsKey);
            }
        }catch (Exception e){
            log.error("从缓存中获取随机的问题-发生异常：",e.fillInStackTrace());
        }
        return problem;
    }

    /**
     * 从缓存中获取随机的乱序试题
     * 所有的均会被返回，只是顺序会自动随机
     * @param total
     * @return
     */
    public Set<Problem> getRandomEntitys(Integer total){
        Set<Problem> problems=Sets.newHashSet();
        try {
            SetOperations<String,Problem> setOperations=redisTemplate.opsForSet();
            problems=setOperations.distinctRandomMembers(RedisKeyConstants.RedisSetProblemsKey,total);

            //SetOperations<String,Problem> setOperations=redisTemplate.opsForSet();
            //problems=setOperations.members(Constant.RedisSetProblemsKey);

            //List subMenuList=Collections.shuffle(redisTemplate.opsForList().range());

        }catch (Exception e){
            log.error("从缓存中获取随机的、乱序的试题列表-发生异常：",e.fillInStackTrace());
        }
        return problems;
    }
}
