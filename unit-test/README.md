# 单元测试


## DAO 测试
1. 通过启动Spring容器,并且指定加载需要测试的dao类
2. @MybatisTest注解需要SqlSession和SqlFactory，在使用spring自动配置机制时候
3. 不启动容器,代码配置来直接创建dao

SpringBootTest指定加载配置方式
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    ICiticMainAccountDao.class,
    CiticMainAccountDaoImpl.class,
    CiticMainAccountMapper.class,
    DataSourceAutoConfiguration.class,
    MybatisPlusAutoConfiguration.class,
    DataSource.class,
    SqlSessionFactory.class
})
//@TestPropertySource(locations = "classpath:application.yml")
@MapperScan(basePackages = {"cn.da156.dywl.account.tunnel.mysql.mapper"})
public class MainAccountDaoTest {
    @Autowired
    private ICiticMainAccountDao mainAccountDao;
    @Test
    public void test() {
        CiticMainAccountDO citicMainAccountDO = mapper.selectById(1L);
        Assert.assertEquals(1L, (long) citicMainAccountDO.getCompanyId());
        Assert.assertEquals("大易科技母公司", citicMainAccountDO.getCompanyName());
    }
}
```


@MybatisTest方式
```xml
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter-test</artifactId>
      <version>1.3.2</version>
      <scope>test</scope>
    </dependency>
```
```java

```

不启动容器方式
```java

import cn.da156.dywl.account.tunnel.mysql.dao.impl.GonghangAccountDaoImpl;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DaoWithoutSpringTestSupport {
    protected static SqlSessionFactory sqlSessionFactory;
    protected static SqlSession session;
    public static List<Class<?>> mapperfile = new ArrayList<Class<?>>();
    public static List<Class<IService>> daoList = new ArrayList<>();

    /**
     * 返回默认的配置文件，如果文件名称不一样，则在测试用例中复写本方法。
     * 仍然使用spring的配置文件，但是不启动spring容器。
     *
     * @return
     */
    protected static String getPropertyFile() {
        return "application.properties";
    }
    
    public static void setUpDatabse() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(getPropertyFile()));
        String user = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
        String url = properties.getProperty("spring.datasource.url");
        String driver = properties.getProperty("spring.datasource.druid.driver-class-name");
        DataSource dataSource = new org.apache.ibatis.datasource.pooled.PooledDataSource(driver, url, user, password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new org.apache.ibatis.mapping.Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
        Configuration configuration = new MybatisConfiguration(environment);
        for (Class clazz : mapperfile) {
            configuration.addMapper(clazz);
        }

//        configuration.addMappers("cn.da156.dywl.account.tunnel.mysql.mapper");
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        session = sqlSessionFactory.openSession();
    }

    @SneakyThrows
    protected static <T extends IService> T getDao(Class<T> daoCls) {
        Constructor<T> constructor = ReflectionUtils.accessibleConstructor(daoCls);
        T dao = constructor.newInstance();

        BaseMapper mapper = null;
        Type genericSuperclass = daoCls.getGenericSuperclass();
        Type mapperType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        if (mapperType instanceof Class) {
            Class<?> mt = (Class<?>) mapperType;
            mapper = (BaseMapper) session.getMapper(mt);
        } else {
            throw new RuntimeException("Nosupport " + mapperType);
        }
        Field baseMapper = ReflectionUtils.findField(GonghangAccountDaoImpl.class, "baseMapper");
        baseMapper.setAccessible(true);
        ReflectionUtils.setField(baseMapper, dao, mapper);

        return dao;
    }


    @AfterClass
    public static void close() {
        session.close();
    }
}

// ------------------------------------------------------------- //
@Slf4j
@RunWith(JUnit4ClassRunner.class)
public class GonghangAccountDaoTest extends DaoWithoutSpringTestSupport {

    @BeforeClass
    public static void setUp() throws IOException {
        mapperfile.add(GonghangAccountMapper.class);

        setUpDatabse();
        log.info("started>>>>>>>>>>>>>>>>>>>");
    }


    @Test
    public void find() {
        GonghangAccountMapper mapper = session.getMapper(GonghangAccountMapper.class);
        log.info("mapper account count: {}", mapper.selectCount(new QueryWrapper<>()));

        IGonghangAccountDao dao = getDao(GonghangAccountDaoImpl.class);
        log.info("dao account count: {}", dao.count());
    }
}

```

