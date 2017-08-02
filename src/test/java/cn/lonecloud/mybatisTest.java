package cn.lonecloud;

import cn.lonecloud.dao.UserDataMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.sql.Connection;

/**
 * Created by lonecloud on 2017/8/2.
 */
public class mybatisTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setUp() throws Exception
    {
        // create a SqlSessionFactory
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        reader.close();

//        // populate in-memory database
        SqlSession session = sqlSessionFactory.openSession();
//        Connection conn = session.getConnection();
//        reader = Resources.getResourceAsReader("config/mybatis/files/hqlbd.sql");
//        ScriptRunner runner = new ScriptRunner(conn);
//        runner.setLogWriter(null);
//        runner.runScript(reader);
//        reader.close();
        session.close();
    }

    @Test
    public void shouldGetAUser()
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try
        {
            UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
            System.out.println(mapper);
        } finally
        {
            sqlSession.close();
        }
    }
}
