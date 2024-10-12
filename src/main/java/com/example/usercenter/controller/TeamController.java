package com.example.usercenter.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenter.bean.Team;
import com.example.usercenter.bean.User;
import com.example.usercenter.bean.UserTeam;
import com.example.usercenter.bean.dto.TeamQuery;
import com.example.usercenter.bean.request.TeamAddRequest;
import com.example.usercenter.bean.request.TeamExitRequest;
import com.example.usercenter.bean.request.TeamJoinRequest;
import com.example.usercenter.bean.request.TeamUpdateRequest;
import com.example.usercenter.bean.vo.TeamUserVO;
import com.example.usercenter.commons.BaseResponse;
import com.example.usercenter.commons.ErrorCode;
import com.example.usercenter.commons.ResultUtils;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.service.TeamService;
import com.example.usercenter.service.UserService;
import com.example.usercenter.service.UserTeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
@Tag(name="队伍接口")
@Slf4j
@CrossOrigin(origins = {"http://localhost:5173","http://yupao-backend.globalusercenter.fun"},allowCredentials = "true")
public class TeamController {
    @Resource
    private TeamService teamService;
    @Resource
    private UserService userService;
    @Resource
    private UserTeamService userTeamService;



    //退出队伍
    @PostMapping("/exit")
    public BaseResponse<Boolean> ExitTeam(@RequestBody TeamExitRequest teamExitRequest, HttpServletRequest request){
        if (teamExitRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.ExitTeam(teamExitRequest, loginUser);
        return ResultUtils.success(result);
    }
    //加入队伍
    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request){
        if (teamJoinRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.success(result);
    }
    //添加队伍
    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request){
        //team是否为空
        if(teamAddRequest==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //保存
        User loginUser=userService.getLoginUser(request);
        Team team=new Team();
        BeanUtils.copyProperties(teamAddRequest,team);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }
    //删除队伍
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(@RequestBody TeamExitRequest teamExitRequest,HttpServletRequest request) {
        if (teamExitRequest==null|| teamExitRequest.getTeamId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id=teamExitRequest.getTeamId();
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.deleteTeam(id,loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    //更新队伍
    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest,HttpServletRequest request){
        //为空
        if(teamUpdateRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result=teamService.updateTeam(teamUpdateRequest,loginUser);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(true);
    }

    //查询队伍
    @GetMapping("/get")
    public BaseResponse<Team> getTeamById(long id){
        //id小于0
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //查询
        Team team = teamService.getById(id);
        //没有
        if(team==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }

    //获取队伍列表
    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> listTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isAdmin = userService.isAdmin(request);
        // 1、查询队伍列表
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        teamList=listJoinNum(teamList,request);
        return ResultUtils.success(teamList);
    }

    //分页查询队伍列表
    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamsByPage( TeamQuery teamQuery){
        //为空
        if(teamQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team=new Team();
        //赋值
        BeanUtils.copyProperties(teamQuery,team);
        //初始化分页对象,第一个是第几面,第二是页面包含多少个
        Page<Team> page=new Page<>(teamQuery.getPageNum(),teamQuery.getPageSize());
        QueryWrapper<Team> queryWrapper=new QueryWrapper<>(team);
        Page<Team> teamPage = teamService.page(page, queryWrapper);
        return ResultUtils.success(teamPage);
    }

    /**
     * 获取我创建的队伍
     *
     * @param teamQuery
     * @param request
     * @return
     */
    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVO>> listMyCreateTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        teamQuery.setUserId(loginUser.getId());
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, true);
        if(CollectionUtils.isEmpty(teamList)){
            return ResultUtils.success(new ArrayList<>());
        }
        teamList=listJoinNum(teamList,request);
        return ResultUtils.success(teamList);
    }

    /**
     * 获取我加入的队伍
     *
     * @param teamQuery
     * @param request
     * @return
     */
    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVO>> listMyJoinTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", loginUser.getId());
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);
        if(CollectionUtils.isEmpty(userTeamList)){
            return ResultUtils.success(new ArrayList<>());
        }
        // 取出不重复的队伍 id
        // teamId userId
        Map<Long, List<UserTeam>> listMap = userTeamList.stream()
                .collect(Collectors.groupingBy(UserTeam::getTeamId));
        List<Long> idList = new ArrayList<>(listMap.keySet());
        teamQuery.setIdList(idList);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, true);
        teamList=listJoinNum(teamList,request);
        return ResultUtils.success(teamList);
    }

    //获取加入队伍的人数
    private List<TeamUserVO> listJoinNum(List<TeamUserVO> teamList,HttpServletRequest request){
        final List<Long> teamIdList = teamList.stream().map(TeamUserVO::getId).collect(Collectors.toList());
        // 2、判断当前用户是否已加入队伍
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        try {
            User loginUser = userService.getLoginUser(request);
            userTeamQueryWrapper.eq("userId", loginUser.getId());
            if(teamIdList.size()>0){
                userTeamQueryWrapper.in("teamId", teamIdList);
            }

            List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
            // 已加入的队伍 id 集合
            Set<Long> hasJoinTeamIdSet = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
            teamList.forEach(team -> {
                boolean hasJoin = hasJoinTeamIdSet.contains(team.getId());
                team.setHasJoin(hasJoin);
            });
        } catch (Exception e) {}
        // 3、查询已加入队伍的人数
        QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
        if(teamIdList.size()>0){
            userTeamJoinQueryWrapper.in("teamId", teamIdList);
        }
        List<UserTeam> userTeamList = userTeamService.list(userTeamJoinQueryWrapper);
        // 队伍 id => 加入这个队伍的用户列表
        Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        teamList.forEach(team -> team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(), new ArrayList<>()).size()));
        return teamList;
    }
}
