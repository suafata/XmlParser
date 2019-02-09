package com.suafata.xmlParser.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariadb.jdbc.MariaDbPoolDataSource;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceConfigTest {



    @Test
    public void testConfig() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        ReflectionTestUtils.setField(dataSourceConfig, "url" , "jdbc:mysql://localhost:3306/db_example");
        ReflectionTestUtils.setField(dataSourceConfig, "username" , "springuser");
        ReflectionTestUtils.setField(dataSourceConfig, "degree" , Integer.valueOf(20));
        MariaDbPoolDataSource dataSource = (MariaDbPoolDataSource) dataSourceConfig.dataSourceMariaDb();
        assertEquals("springuser", dataSource.getUser());
        assertEquals(20L, dataSource.getMaxPoolSize());

    }

}
