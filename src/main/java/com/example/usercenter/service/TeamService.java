package com.example.usercenter.service;

import com.example.usercenter.bean.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.bean.User;
import com.example.usercenter.bean.dto.TeamQuery;
import com.example.usercenter.bean.request.TeamExitRequest;
import com.example.usercenter.bean.request.TeamJoinRequest;
import com.example.usercenter.bean.request.TeamUpdateRequest;
import com.example.usercenter.bean.vo.TeamUserVO;

import java.util.List;

/**
* @author 16247
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2024-10-04 10:54:18
*/
public interface TeamService extends IService<Team> {

    /**
     * 添加队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 查询队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamExitRequest
     * @param loginUser
     * @return
     */
    boolean ExitTeam(TeamExitRequest teamExitRequest, User loginUser);

    /**
     * 删除队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
