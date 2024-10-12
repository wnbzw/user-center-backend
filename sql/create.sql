create database user_center;
use user_center;
create table team
(
    id          bigint auto_increment comment '主键' primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint                             null comment '创建人Id',
    status      int      default 0                 null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '队伍表';



create table user_team
(
    id          bigint auto_increment comment '主键' primary key,
    userId bigint comment '用户Id',
    teamId bigint comment '队伍id',
    joinTime datetime null comment '加入时间',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '用户-队伍关系表';

-- auto-generated definition
create table user
(
    id           bigint auto_increment comment '主键' primary key,
    username     varchar(256)                       null comment '昵称',
    userAccount  varchar(256)                       null comment '登录账号',
    avatarUrl    varchar(1024)                      null comment '头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       null comment '密码',
    phone        varchar(256)                       null comment '电话',
    email        varchar(1024)                      null comment '邮箱',
    userStatus   int      default 0                 null comment '用户状态',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                 null comment '是否删除',
    userRole     int      default 0                 not null comment '默认0 管理人员 1',
    planetCode   varchar(512)                       null comment '星球编号'
)
    comment '用户表';

-- auto-generated definition
create table tag
(
    id         bigint auto_increment comment '主键'
        primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户 Id',
    parentId   bigint                             null comment '父标签 id',
    isParent   tinyint                            null comment '是否为父标签 0 不是 ,1 是',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 null comment '是否删除'
)
    comment '标签';

