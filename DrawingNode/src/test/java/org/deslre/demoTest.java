package org.deslre;

import org.deslre.nodeModule.vo.RelationVo;
import org.deslre.utils.FinalUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class demoTest {

    @Test
    void getField() {
        RelationVo relationVo = new RelationVo();
        relationVo.setName("李四");
        relationVo.setTitle("张三");
        String clause = buildClause(relationVo);
        System.out.println("clause = " + clause);
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
}
