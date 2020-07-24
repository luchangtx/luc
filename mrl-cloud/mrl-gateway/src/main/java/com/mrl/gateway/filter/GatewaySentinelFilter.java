package com.mrl.gateway.filter;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.mrl.gateway.fallback.GatewayBlockFallbackProvider;
import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author luc
 * @date 2020/7/2314:37
 */
@Configuration
public class GatewaySentinelFilter {
    @Bean
    public ZuulFilter sentinelZuulPreFilter(){
        return new SentinelZuulPreFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulPostFilter(){
        return new SentinelZuulPostFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulErrorFilter(){
        return new SentinelZuulErrorFilter();
    }

    /**
     * PostConstruct 和 PreDestroy 两个作用于 Seervlet生命周期的注解
     *   修饰的方法不能有参数；返回值为void、不能抛出异常、只执行一次
     * PostConstruct：实现Bean初始化之前的自定义操作
     * PreDestroy：实现Bean销毁之前的自定义操作
     * 此处需求应该为 在上述三个过滤器初始化之前 将限流规则设置进去
     */
    @PostConstruct
    public void doInit(){
        //注册自定义限流异常
        ZuulBlockFallbackManager.registerProvider(new GatewayBlockFallbackProvider());
        initGatewayRules();
    }

    /**
     * 定义验证码请求限流 限流规则
     * 60s内同一个IP同一个key最多访问10次
     */
    private void initGatewayRules(){
        //ApiDefinition 用户自定义的API定义分组，可以看作一些URL匹配的组合
        Set<ApiDefinition> definitions=new HashSet<>();
        Set<ApiPredicateItem> predicateItems=new HashSet<>();
        predicateItems.add(new ApiPathPredicateItem().setPattern("/auth/captcha"));
        //定义各一个API分组，名为captcha，匹配的url为/auth/capthca，然后通过GatewayFlowRule指定限流
        ApiDefinition definition=new ApiDefinition("captcha").setPredicateItems(predicateItems);
        definitions.add(definition);

        GatewayApiDefinitionManager.loadApiDefinitions(definitions);

        //网关限流规则，可以针对不同route或自定义API分组进行限流，支持针对请求中的参数、Header、来源IP等进行定制化限流
        Set<GatewayFlowRule> rules=new HashSet<>();
        /**
         * GatewayFlowRule 字段意思如下
         * source：route（网关yml配置中自定义的转发规则）名称或自定义的API分组名 captcha
         * resourceMode：规则针对的对象，默认route（RESOURCE_MODE_ROUTE_ID），指定为API分组名
         * grade：限流指标维度
         * count：限流阈值 最多访问10次，count就=10
         * intervalSec：统计时间窗口，单位s，默认1s
         * controlBehavior：流量整形控制效果：支持快速失败、匀速排队两种模式，默认快速失败
         * burst：应对突发请求时额外允许的请求数目
         * maxQueueingTimeoutMs：匀速排队模式下的最长排队时间
         * paramItem：参数限流配置，不配置表示不针对参数限流，该网关将会被转换成普通流控规则，否则回转换成热点规则，其中字段意思如下
         *   parseStrategy：从请求中提取参数的策略，目前支持IP、host、任意header、url参数四种
         *   fieldName：提取策略为header或url参数模式时，需要指定的参数名称
         *   pattern：参数值的匹配模式，只有匹配该模式的请求，属性值才会被纳入统计和流控，若为空则统计该请求属性的所有值
         *   matchStrategy：参数值匹配策略，精准匹配EXACT、字串匹配CONTAINS、正则匹配REGEX
         */
        rules.add(new GatewayFlowRule("captcha")
                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
                .setParamItem(
                        new GatewayParamFlowItem()
                            .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
                            .setFieldName("key")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_EXACT)
                            .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                )
                .setCount(10)
                .setIntervalSec(60)
        );
        //手动加载网关规则，推荐使用  GatewayRuleManager.register2Property(property) 注册动态规则源动态推送
        GatewayRuleManager.loadRules(rules);
    }
}
