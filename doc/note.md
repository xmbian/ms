分布式session解决方案：
    登录成功后，生成Token（类似关于sessionId）用来标识用户，写到cookie中传到客户端，客户端在之后的访问中都在cookie中上传这个token，服务端拿到该cookie后根据token取到用户对应的session信息