package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.bean.UserTeam;
import com.example.usercenter.service.UserTeamService;
import com.example.usercenter.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author 16247
* @description 针对表【user_team(用户-队伍关系表)】的数据库操作Service实现
* @createDate 2024-10-04 10:54:48
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




