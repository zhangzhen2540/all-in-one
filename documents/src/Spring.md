# Spring SPI
    Spring SPI 读取 META-INF/spring.factories 文件, 文件内容 类名=实现类[,实现类]
    Spring 通过 SpringFactoriesLoader.loadFactories 来读取配置文件

    SpringBoot 自动装载机制就是通过 SPI 来提供的, 接口为 org.springframework.boot.autoconfigure.EnableAutoConfiguration
    实现是一个 @Configuration bean
    