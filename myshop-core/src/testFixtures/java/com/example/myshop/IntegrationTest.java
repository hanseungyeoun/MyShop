package com.example.myshop;

import com.example.myshop.constant.Constant;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.lang.annotation.*;

/**
 * 통합테스트용 애노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag(Constant.TEST_TAG_INTEGRATION)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles({"db","db-test", Constant.PROFILE_TEST})
@SpringBootTest
public @interface IntegrationTest {
    /**
     * Alias for {@link #properties()}.
     *
     * @return the properties to apply
     */
    @AliasFor(annotation = SpringBootTest.class)
    String[] value() default {};

    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     *
     * @return the properties to add
     */
    @AliasFor(annotation = SpringBootTest.class)
    String[] properties() default {};

    /**
     * Application arguments that should be passed to the application under test.
     *
     * @return the application arguments to pass to the application under test.
     * @see ApplicationArguments
     * @see SpringApplication#run(String...)
     * @since 2.2.0
     */
    @AliasFor(annotation = SpringBootTest.class)
    String[] args() default {};

    /**
     * The <em>component classes</em> to use for loading an
     * {@link org.springframework.context.ApplicationContext ApplicationContext}. Can also
     * be specified using
     * {@link ContextConfiguration#classes() @ContextConfiguration(classes=...)}. If no
     * explicit classes are defined the test will look for nested
     * {@link Configuration @Configuration} classes, before falling back to a
     * {@link SpringBootConfiguration @SpringBootConfiguration} search.
     *
     * @return the component classes used to load the application context
     * @see ContextConfiguration#classes()
     */
    @AliasFor(annotation = SpringBootTest.class)
    Class<?>[] classes() default {};

    /**
     * The type of web environment to create when applicable. Defaults to
     * {@link SpringBootTest.WebEnvironment#MOCK}.
     *
     * @return the type of web environment
     */
    @AliasFor(annotation = SpringBootTest.class)
    SpringBootTest.WebEnvironment webEnvironment() default SpringBootTest.WebEnvironment.MOCK;
}
