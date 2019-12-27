## 1.GitHub命令
###查看状态
git status 
###添加仓库
git remote add origin git@github.com:HywelShi/community.git
###添加文件
git add . 
###提交代码
git commit -m "提交说明" 
###远程提交
git push

##2.GitHub授权登陆时序
###前提
注册GitHub账户
创建OAuthAPP应用
得到client_id与client_secret
设置homepage url主页与Authorization callback url回调地址

####1.登陆 获取authorize
####2.回调redirect-uri 携带code
####3.access_token 携带code
####4.返回access_token
####5.user携带access_token
####6.返回user信息
####7.存入数据,更新登录状态

###2.1 请求用户的GitHub身份
GET请求
https://github.com/login/oauth/authorize
需要传入
client_id(OAuthAPP中)
scope(设置为user)
state(不可猜测的随机字符串，为了安全，可以先设置为1)
###2.2 GitHub将用户重定向回站点
POST请求 利用okhttp实现
倒入okhttp3和fastjson的jar包
五个重要属性
    client_id;
    client_secret;
    code;
    redirect_uri;
    state;
https://github.com/login/oauth/access_token
###2.3 使用访问令牌访问API
GET请求
https://api.github.com/user
获取GitHub用户对象
三个重要属性
name id bio

##集成flyway数据库版本控制
更新数据库命令
mvn flyway:migrate