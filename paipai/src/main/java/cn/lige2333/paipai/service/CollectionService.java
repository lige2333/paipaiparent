package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.CollectionMapper;
import cn.lige2333.paipai.entity.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionMapper collectionMapper;
    public Boolean isExist(Integer id, Integer proId) {
        Example example = new Example(Collection.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("proid", proId);
        criteria.andEqualTo("userid",id);
        example.and(criteria);
        return collectionMapper.selectCountByExample(example)>0;
    }

    public void collect(Collection collection) {
        collectionMapper.insert(collection);
    }

    public List<Collection> getCollectionByUser(Integer id) {
        Example example = new Example(Collection.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",id);
        example.and(criteria);
        List<Collection> collections = collectionMapper.selectByExample(example);
        return collections;
    }

    public void delCollect(Collection collection) {
        Example example = new Example(Collection.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("proid", collection.getProid());
        criteria.andEqualTo("userid",collection.getUserid());
        example.and(criteria);
        collectionMapper.deleteByExample(example);
    }

    public List<Collection> getCollectionByProductId(Integer id) {
        Example example = new Example(Collection.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("proid",id);
        example.and(criteria);
        List<Collection> collections = collectionMapper.selectByExample(example);
        return collections;
    }
}
