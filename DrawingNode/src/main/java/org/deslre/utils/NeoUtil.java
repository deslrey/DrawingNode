package org.deslre.utils;

import lombok.extern.slf4j.Slf4j;
import org.deslre.config.NeoDatabaseConfig;
import org.deslre.handler.SpringContextHandler;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import static org.deslre.utils.StringUtil.isEmpty;

@Slf4j
@Component
public class NeoUtil {


    private static final NeoDatabaseConfig neoDatabaseConfig;

    private final static Driver driver;
    private final static Session session;

    static {
        neoDatabaseConfig = SpringContextHandler.getBean(NeoDatabaseConfig.class);
        driver = GraphDatabase.driver(neoDatabaseConfig.getUrl(), AuthTokens.basic(neoDatabaseConfig.getUserName(), neoDatabaseConfig.getPassWord()));
        session = driver.session();
    }


    public <T> Results<String> addSingleNode(T premise) {
        if (isEmpty(premise)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }

        String clause = buildClause(premise);
        String title = getLevel(premise);
        try {
            String cql = "MERGE (:`" + title + "`{" + clause + "})";
            log.info("cql = {}", cql);
            session.run(cql);
        } catch (Exception e) {
            return Results.fail(e.getMessage());
        }
        return Results.ok();
    }

    /**
     * 删除所有数据
     */
    public void deleteAll() {
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

    private static <T> String getLevel(T type) {
        Field[] fields = type.getClass().getDeclaredFields();
        String fieldName = "";
        for (Field field : fields) {
            field.setAccessible(true);
            fieldName = field.getName();
            if ("level".equals(fieldName)) {
                try {
                    fieldName = (String) field.get(type);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fieldName;
    }


    public static <T> void print(T t) {
        System.err.println(t);
    }

    public static <T, R> void print(T t, R r) {
        System.err.println(t + " = " + r);
    }

    public static <R> void print(Collection<? extends R> list) {
        if (isEmpty(list)) {
            print("传入的lis为空");
            return;
        }
        list.forEach(NeoUtil::print);
    }

    public static <K, V> void print(Map<? extends K, ? extends V> map) {
        if (isEmpty(map)) {
            print("传入的map为空");
            return;
        }
        map.forEach(NeoUtil::print);
    }


}

