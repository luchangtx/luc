# luc
Spring Cloud 微服务权限管理系统
mrl-register = 8001 #注册中心端口
mrl-auth = 8101 #授权服务端口
mrl-gateway = 8201 #网关端口
mrl-server-system = 8081 #系统服务提供者端口
mrl-server-normal = 8082 #一般服务提供者端口

2.配置类说明
mrl-auth中 AuthWebSecurityConfigure 和 AuthResourceServerConfigure 都对请求进行了过滤，
web安全配置对 /oauth/** 进行了过滤；资源服务器器配置对 /** 所有的请求进行了过滤
那么当一个请求进来，哪个过滤器会生效呢，其实Spring Security中没有谁生效的说法，而是定义了优先级，根据优先级来判断先进入哪个过滤器

查看 AuthWebSecurityConfigure 继承的 WebSecurityConfigurerAdapter 的源码发现  @Order(100)
查看 AuthResourceServerConfigure 类的 注解 EnableResourceServer 中 ResourceServerConfiguration 类中定义了 int order = 3
可以看出资源服务器过滤器优先匹配，我们希望的是 /oauth/** 请求由 AuthWebSecurityConfigure 处理，而剩下的所有请求由 AuthResourceServerConfigure 处理
所以我们只需要再 AuthWebSecurityConfigure 配置类上增加注解 @Order(2)即可

AuthWebSecurityConfigure：spring cloud oauth2内部定义的 获取令牌、刷新令牌 都是以/oauth/开头的，所以此类处理和令牌相关的请求
AuthResourceServerConfigure：主要用于资源保护，客户端需要根据Oauth2协议方法的令牌来请求受保护的资源

3.配置文件说明
zuul.ribbon.eager-load.enabled：
Zuul内部通过Ribbon按照一定的负载均衡算法来获取服务，
Ribbon进行客户端负载均衡的Client并不是在服务启动的时候就初始化好的，
而是在调用的时候才会去创建相应的Client，
所以第一次调用的耗时不仅仅包含发送HTTP请求的时间，
还包含了创建RibbonClient的时间，这样一来如果创建时间速度较慢，
同时设置的超时时间又比较短的话，第一次请求很容易出现超时的情况。
设置为true的时候表示开启Ribbon的饥饿加载模式，
即在应用启动的时候就去获取相应的Client备用

搭建通用模块搭建——各服务间的通用模块
  >资源服务器异常（401：令牌不正确；403：无权限）
  >feign拦截器设置请求token
  >配合网关zuul过滤器，校验zuul token，对直接访问微服务的请求进行拦截
搭建服务注册中心——所有微服务都注册到这里
搭建认证服务器——oauth2发放令牌
  >提供对外接口（资源服务）
  >资源保护（必须提供token访问资源、权限认证）
  >异常翻译
  >feign手动设置token
搭建微服务网关——负责转发路由
  >zuul网关异常（请求超时、服务不可用）
  >zuul过滤器，设置zuul token，配合common模块对微服务进行防护
  >跨域处理

登录未设计到数据库表交互，需要完善用户登录流程
>表结构设计
>完善登录
>整合图形验证码
>Sentinel验证码限流


Spring Security本质是一长串的过滤器链，处理用户密码登录的过滤器为UsernamePasswordAuthenticationFilter
要集成图形验证码校验只需要在这个过滤器前增加图形验证校验码过滤器即可
