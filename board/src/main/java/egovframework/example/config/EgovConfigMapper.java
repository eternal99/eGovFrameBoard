package egovframework.example.config;

import java.io.IOException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "egovframework")
public class EgovConfigMapper {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(pmrpr.getResource("classpath:/egovframework/sqlmap/example/sql-mapper-config.xml"));
        
        // 두 경로의 리소스를 결합하여 하나의 배열로 전달
        Resource[] mainMappers = pmrpr.getResources("classpath:/egovframework/sqlmap/main/MainMapper.xml");
        Resource[] exampleMappers = pmrpr.getResources("classpath:/egovframework/sqlmap/example/mappers/*.xml");
        Resource[] allMappers = new Resource[mainMappers.length + exampleMappers.length];
        
        System.arraycopy(mainMappers, 0, allMappers, 0, mainMappers.length);
        System.arraycopy(exampleMappers, 0, allMappers, mainMappers.length, exampleMappers.length);

        sqlSessionFactoryBean.setMapperLocations(allMappers);
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
