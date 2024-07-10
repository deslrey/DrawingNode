package org.deslre.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.deslre.config.NeoDatabaseConfig;
import org.deslre.handler.SpringContextHandler;
import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String title = getField(premise, FinalUtil.LEVEL);
        try {
            String cql = "MERGE (:`" + title + "`{" + clause + "})";
            log.info("cql = {}", cql);
            session.run(cql);
        } catch (Exception e) {
            return Results.fail(e.getMessage());
        }
        return Results.ok();
    }

    public <R, S> Results<R> addCaseRelationships(R StartNode, S Relation, R EndNode) {
        if (isEmpty(StartNode) || isEmpty(Relation) || isEmpty(EndNode)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }

        String startNode = buildClause(StartNode);
        String relation = buildClause(Relation);
        String endNode = buildClause(EndNode);
        String StartLevel = getField(StartNode, FinalUtil.LEVEL);
        String EndLevel = getField(EndNode, FinalUtil.LEVEL);
        String title = getField(Relation, FinalUtil.TITLE);
        try {
            String cql = "MERGE  (a:`" + StartLevel + "`{" + startNode + "}) MERGE  (b:`" + EndLevel + "`{" + endNode + "}) MERGE  (a) - [:`" + title + "`{" + relation + "}] -> (b)";
            log.info("cql = {}", cql);
            session.run(cql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Results.ok();
    }

    public Results<List<RelationshipNode>> getAllRelationships(String caseNumber) {
        if (isEmpty(caseNumber)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        List<RelationshipNode> list = new ArrayList<>();
        RelationshipNode node;
        try {
            String cql = "match (a) - [c:`" + caseNumber + "`] ->  (b) return a,c,b";
            print("getLeftRelation()", cql);
            org.neo4j.driver.Result result = session.run(cql);
            Node startNode;
            Node endNode;
            Record record;
            Relationship relationship;
            while (result.hasNext()) {
                node = new RelationshipNode();
                record = result.next();
                startNode = record.get("a").asNode();
                endNode = record.get("b").asNode();
                relationship = record.get("c").asRelationship();

                node.setStartNode(startNode.asMap().toString());
                node.setRelationship(relationship.asMap().toString());
                node.setEndNode(endNode.asMap().toString());
                list.add(node);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.ok(list);
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

    public static <T, U> U convert(T source, Class<U> targetClass) {
        U target = null;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            target = BeanUtil.toBean(source, targetClass);
            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                if (targetField.getName().equals("nodeId")) {
                    for (Field sourceField : sourceFields) {
                        sourceField.setAccessible(true);
                        if (sourceField.getName().equals("id")) {
                            targetField.set(target, sourceField.get(source));
                            break;
                        }
                    }
                } else if (targetField.getName().equals("id")) {
                    targetField.setAccessible(true);
                    targetField.set(target, null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }


    private static <T> String buildClause(T entity) {
        Class<?> clazz = entity.getClass();
        StringBuilder whereClause = new StringBuilder();
        // 获取实体类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (field.getName().equals(FinalUtil.TITLE))
                    continue;
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

    private static <T> String getField(T type, String fieldName) {
        Field[] fields = type.getClass().getDeclaredFields();
        String name = "";
        for (Field field : fields) {
            field.setAccessible(true);
            name = field.getName();
            if (fieldName.equals(name)) {
                try {
                    name = (String) field.get(type);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return name;
    }

    public static String getValueFromJsonString(String jsonString) {
        Pattern pattern = Pattern.compile("=(.*?)}");
        Matcher matcher = pattern.matcher(jsonString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static ResultDto parseProduct(String jsonString) {
        JSONObject jsonObject = new JSONObject(true);
        for (String entry : jsonString.substring(1, jsonString.length() - 1).split(", ")) {
            String[] keyValue = entry.split("=");
            jsonObject.put(keyValue[0], keyValue[1]);
        }
        return jsonObject.toJavaObject(ResultDto.class);
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

