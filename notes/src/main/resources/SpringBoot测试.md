# SpringBoot 测试

## 单元测试的粒度
1. DAO层的单元测试： 对于基本CRUD，可以考虑跳过这一部分单元测试，而一些比较复杂的动态更新、查询等操作，建议用使用H2去做模拟单元测试。
2. Service层的单元测试：基本上一个Service里面肯定会依赖很多其他的service（此处也建议将成员变量通过构造方法进行注入，以便于单元测试去Mock），此时建议我们将依赖其他service的方法用Mock替代，Service里面的一些数据库的操作也进行Mock。这样可以保证service测试的独立性，不过对于逻辑复杂的方法可能要花很多时间在Mock上面。 如果发现需要Mock的方法过多，那么可能就需要考虑将要测试的方法是不是需要重构。
3. Controller（API）层的单元测试：主要着重测试HTTP status在 200,400,500 等情况下的异常处理，request及response的转换等。由于其余部分的代码测试都已经在其对应的单元测试覆盖，那么此时可以Mock绝大部分Serivce层中的方法。
4. 一般工具类的单元测试：一些工具类里面包含了比较多的逻辑，所以需要尽可能考虑多种情况下测试用例。



## 单元测试常见工具
JUnit：
AssertJ: 断言工具
Hamcrest: 
Mockito:
PowerMock: 一个比较好用的单元测试三方库，功能强大，可以对静态方法，构造方法，私有方法及Final方法进行模拟
JSONassert: JSON断言工具
JsonPath:
Karate + Cucumber 可以做基于WEB-API的自动化测试框架，也可以作为Controller部分的测试补充。
---
SpringBoot而言可以直接引入spring-boot-starter-test ， 它将会引入JUnit、Spring Test、AssertJ、Hamcrest（匹配对象）、Mockito、JSONassert、JsonPath等工具库


## 常见注解类
### RunWith
@RunWith就是一个运行器
@RunWith(JUnit4.class)就是指用JUnit4来运行
@RunWith(SpringRunner.class),让测试运行于Spring测试环境

@RunWith(SpringRunner.class) ：在junit 测试期间@Autowire，您需要此注释来启用spring boot 功能，例如，@MockBean等等
用于在 Spring Boot 测试特性和 JUnit 之间提供桥梁。每当我们在 JUnit 测试中使用任何 Spring Boot 测试功能时，都需要此注释。
@SpringBootTest ：此注解用于加载完整的应用程序上下文以进行端到端集成测试

当我们需要引导整个容器时，可以使用 @SpringBootTest 注解。注释通过创建将在我们的测试中使用的 ApplicationContext 来工作。

### @ContextConfiguration
@ContextConfiguration Spring整合JUnit4测试时，使用注解引入多个配置文件

单个文件
@ContextConfiguration(Locations=”../applicationContext.xml”)
@ContextConfiguration(classes = SimpleConfiguration.class)

多个文件时，可用{}
@ContextConfiguration(locations = { “classpath*:/spring1.xml”, “classpath*:/spring2.xml” })


### Test
    @Test注解可以添加返回期望的异常，异常处理的测试也是需要考虑的地方。
    @Test(expected = IndexOutOfBoundsException.class)

### SpringBootTest
   SpringBoot的单元测试可以使用@SpringBootTest 注解。其中可以在此注解参数中指定需要加载的类，这样有助于Spring快速启动并完成测试。
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapRepository.class, CarService.class})
public class CarServiceWithRepoTest {

    @Autowired
    private CarService carService;

    @Test
    public void shouldReturnValidDateInTheFuture() {
        Date date = carService.schedulePickup(new Date(), new Route());
        assertTrue(date.getTime() > new Date().getTime());
    }
}
```

### DataJpaTest

对于JPA，Repository进行测试的时候可以使用@DataJpaTest 注解，有了这个注解，Spring在启动的时候就只会加载@Entity和@Repository注解 相关的class。同样可以提高测试的效率。
```java
@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

   @Autowired
   UserRepository userRepository;

   @Test
   public void findByUserIdReturnUser() {
       User user1 = new User("javaNorth",100l,"指北君");
       Optional<User> userOptional = userRepository.findById(100l);
       assertThat(user1).isEqualTo(userOptional.get());
   }
}
```

### WebMvcTest
测试WEB层可以使用@WebMvcTest 注解，使用此注解可以测试controller部分并且不用把整个服务都跑起来。也是一种提高测试速度的方法
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void retrieveUserByUserId() throws Exception {
        User user1 = new User("javaNorth","100","指北君");
        given(this.userService.retrieveUserById("100"))
           .willReturn(user1);
         this.mvc.perform(MockMvcRequestBuilders.get("/javanorth/user/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
```

### @AutoConfigureTestDatabase
    会自动配置一个基于内存的数据库，只要依赖中含有一个可用的基于内存的嵌入式数据库。结果会自动回滚，不会发生数据库变化。若使用实际数据库可设置 replace值为Replace.NONE

### @MybatisTest
    com.baomidou.mybatis-spring-boot-starter-test
### @MybatisPlusTest
    com.baomidou.mybatis-spring-boot-starter-test