# sisyphus-auth-web
* **鉴权授权服务中心**  
[参考](https://gitee.com/micai/springboot-springsecurity-jwt-demo)
#### 说明
> 该项目是采用SpringSecurity+oauth2.0的方式，通过token校验获取最终验证其中token分为如下两种
 - grant_type：password:后台管理使用，登录后使用
 - grant_type：client_credentials,client的方式，对外开放api的方式使用，即openApi   
  
 
    
#### password模式
> 1、获取token
```$xslt

http://localhost:9999/oauth/token?grant_type=password&username=18310811659&password=12597758&client_id=web&client_secret=web
```
响应结果如下
```$xslt

{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODMxMDgxMTY1OSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJyb2xlcyI6ImFkbWluIiwidGVuYW50SWQiOjEwMDEsImV4cCI6MTUyNzI3NzkxMywidXNlcklkIjoxMDAxLCJqdGkiOiIxOTA3NDEyZC00Y2FiLTQ1MTMtOWE2Ni03YmRjYTYxZmMwZmYiLCJjbGllbnRfaWQiOiJ3ZWIifQ.kGrHpCqwAmN2BDczgieTNUMuoqSE1bzhHLNyFhr2ODo",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODMxMDgxMTY1OSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJyb2xlcyI6ImFkbWluIiwiYXRpIjoiMTkwNzQxMmQtNGNhYi00NTEzLTlhNjYtN2JkY2E2MWZjMGZmIiwidGVuYW50SWQiOjEwMDEsImV4cCI6MTUyOTgyNjcxMywidXNlcklkIjoxMDAxLCJqdGkiOiJjMDIyNzIxMS1kM2VmLTQxN2EtYjZkMi00N2QwNzU2NTkzYjIiLCJjbGllbnRfaWQiOiJ3ZWIifQ.FW0s8GQ2Yags9WxNFDSQwID-CT-3ha54GQHL3NEC70M",
    "expires_in": 43199,
    "scope": "read write",
    "roles": "admin",
    "tenantId": 1001,
    "userId": 1001,
    "jti": "1907412d-4cab-4513-9a66-7bdca61fc0ff"
}
```
> 2、根据token获取相关信息
```$xslt
 http://localhost:9999/users/userList?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODMxMDgxMTY1OSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJyb2xlcyI6ImFkbWluIiwidGVuYW50SWQiOjEwMDEsImV4cCI6MTUyNzI3NzkxMywidXNlcklkIjoxMDAxLCJqdGkiOiIxOTA3NDEyZC00Y2FiLTQ1MTMtOWE2Ni03YmRjYTYxZmMwZmYiLCJjbGllbnRfaWQiOiJ3ZWIifQ.kGrHpCqwAmN2BDczgieTNUMuoqSE1bzhHLNyFhr2ODo
```
响应结果如下
```$xslt
{
    "code": 1,
    "message": "OK",
    "data": {
        "id": 6,
        "tenantId": 1002,
        "username": "18310811659",
        "firstName": null,
        "lastName": "liangliang",
        "email": null,
        "imageUrl": null,
        "phoneNumber": "18310811659",
        "authorities": [],
        "gmtCreate": 1526982957000,
        "gmtModified": 1526982957000
    }
}
```
#### client_credentials模式