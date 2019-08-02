分布式session解决方案：
    登录成功后，生成Token（类似关于sessionId）用来标识用户，写到cookie中传到客户端，客户端在之后的访问中都在cookie中上传这个token，服务端拿到该cookie后根据token取到用户对应的session信息
商品ID解决方案：
    商品ID一般不使用自增或者UUID，可以使用snowflake
    
    
    秒杀倒计时一般不在服务端做，而是在客户端进行倒计时，所以倒计时时间可能不准确
    
    
redis压测：
    redis-benchmark -h 127.0.0.1 -p 6379 -c 100 -n 100000
    -c 并发数  -n 请求数   100个并发处理100000个请求
    
    redis-benchmark -h 127.0.0.1 -p 6379 -q -d 100
    存取大小为100字节的数据包 -q 简单输出
    
    redis-benchmark -t set,lpush -q -n 100000 
    只测试某些操作的性能 -t set,lpush 只做set和lpush测试
    
    redis-benchmark -n 100000 -q script load "redis.call('set','foo','bar')"
    只测试某些数值存取的性能