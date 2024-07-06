package org.deslre.utils;

import lombok.extern.slf4j.Slf4j;
import org.deslre.config.NeoDatabaseConfig;
import org.deslre.handler.SpringContextHandler;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.lang.reflect.Field;

@Slf4j
public class NeoUtil {


    private static final NeoDatabaseConfig neoDatabaseConfig;

    private final static Driver driver;
    private final static Session session;

    static {
        neoDatabaseConfig = SpringContextHandler.getBean(NeoDatabaseConfig.class);
        driver = GraphDatabase.driver(neoDatabaseConfig.getUrl(), AuthTokens.basic(neoDatabaseConfig.getUserName(), neoDatabaseConfig.getPassWord()));
        session = driver.session();
    }

    /**
     * 删除所有数据
     */
    public static void DeleteAll() {
        try {
            String cql = "match (n) detach delete n";
            session.run(cql);
            log.error("全部删除成功");
        } catch (Exception e) {
            log.error("全部删除失败: {}", e.getMessage());
        }
    }

    private static <T> String buildClause(T entity) {
        Class<?> clazz = entity.getClass();
        StringBuilder whereClause = new StringBuilder();
        // 获取实体类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true); // 设置字段可访问
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        if (field.getType() == String.class) {
                            whereClause.append(field.getName()).append(" : '").append(value).append("' , ");
                        } else {
                            whereClause.append(field.getName()).append(" : ").append(value).append(" , ");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        // 移除最后一个AND
        if (whereClause.length() > 0) {
            whereClause.setLength(whereClause.length() - 3);
        }
        return whereClause.toString();
    }

    private static <T> String buildWhereClause(T entity) {
        Class<?> clazz = entity.getClass();
        StringBuilder whereClause = new StringBuilder();
        // 获取实体类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true); // 设置字段可访问
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        if (field.getType() == String.class) {
                            whereClause.append("n.").append(field.getName()).append(" = '").append(value).append("' AND ");
                        } else {
                            whereClause.append("n.").append(field.getName()).append(" = ").append(value).append(" AND ");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        // 移除最后一个AND
        if (whereClause.length() > 0) {
            whereClause.setLength(whereClause.length() - 5);
        }
        return whereClause.toString();
    }


    public static void main(String[] args) {
        DeleteAll();
    }

}

