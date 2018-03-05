# zxyw_server
主要有3个模块：
##用户模块
    表现层：controller包下UserController
    业务层：service包下impl包下的UserServiceImpl
    数据提供:dao包下UserMapper
    SQL语句：resources包下mappers包下UserMapper.xml
###主要接口
*   
   登录接口
*
    用户退出接口
*   
   注册的接口
*
    校验接口
*   
   登录情况下获取用户信息接口
*
    查询忘记密码的问题是什么接口
*   
   校验找回密码问题
*
    密码问题重置密码接口
*   
   登录状态下重置密码接口
*
    更新用户信息接口
*   
   获取用户信息接口
##好友模块
    表现层：controller包下FriendController
    业务层：service包下impl包下的FriendServiceImpl
    数据提供:dao包下FriendMapper
    SQL语句：resources包下mappers包下FriendMapper.xml
###主要接口
*   
   添加好友接口
*
    删除好友接口
*   
   同意or加入黑名单好友申请接口
*
    修改好友备注接口
*   
   模糊查询某个好友接口
*
    好友列表接口
##融云即时通讯模块
    io.rong包内
*   Example为融云API的各种实例demo



