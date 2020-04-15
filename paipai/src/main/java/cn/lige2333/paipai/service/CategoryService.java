package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.CategoryMapper;
import cn.lige2333.paipai.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> findAll() {
        List<Category> categories = categoryMapper.selectAll();
        return categories;
    }
}
