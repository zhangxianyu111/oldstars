package com.demo.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/**
 *
 * @author zje
 *
 */
public class MySqlSessionFactory {
	private static  SqlSessionFactory sqlSessionFactory =null;
	static {
		InputStream is;
		try {
			is = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��̬��ȡsqlSession
	 * @return sqlSession����
	 */
	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * ��̬��ȡSQLSessionFactory
	 * @return SQLSessionFactory����
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		
		return sqlSessionFactory;
	}
}
