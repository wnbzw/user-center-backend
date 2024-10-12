package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.bean.Tag;
import com.example.usercenter.service.TagService;
import com.example.usercenter.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 16247
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2024-09-28 22:37:45
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




