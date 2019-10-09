package com.learn.springboot_mq.mapper;


import com.learn.springboot_mq.entity.ProductRobbingRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRobbingRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductRobbingRecord record);

    int insertSelective(ProductRobbingRecord record);

    ProductRobbingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductRobbingRecord record);

    int updateByPrimaryKey(ProductRobbingRecord record);
}