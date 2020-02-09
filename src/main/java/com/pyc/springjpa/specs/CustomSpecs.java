//IntelliJ IDEA
//springjpa
//CustomSpecs
//2020/2/9
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.springjpa.specs;

import static com.google.common.collect.Iterables.toArray;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomSpecs {
    // Define a method that returns a Specification and named byAuto, use generic as return type of this method
    // so that this method can affect any entity class,the parameter of this method is entityManager
    // and a query condition with current parameter value.
    // 定义一个返回值为 Specification 名称为byAuto的方法，使用泛型 T，表明这个Specification 可用于
    // 任何实体类；参数为 entityManager 和包含当前值的查询条件
    public static <T> Specification<T> byAuto(final EntityManager entityManager,
                                              final T example) {
        //获得当前实体类对象的类型
        final Class<T> type = (Class<T>) example.getClass();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                // create a Predicate list to stores the constructed query conditions
                List<Predicate> predicates = new ArrayList<>(); //新建 Predicate 列表存储构造的查询条件
                // get the type of entity so,and then can get the attributes of entity by using entity type
                //获得实体类的 EntityType，从而获得实体类的属性
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                // Doing loop on all attributes of entity
                // 对实体类的所有属性做循环
                for (Attribute<T, ?> attr : entity.getDeclaredAttributes()) {
                    // get the value of an attribute of entity
                    // 获得实体类对象某一个属性的值
                    Object attrValue = getValue(example, attr);
                    if (attrValue != null) {
                        // if the value type of current attribute is character type
                        //当前属性值为字符类型的时候
                        if (attr.getJavaType() == String.class) {
                            // if current character value not null
                            // 当前字符不为空
                            if (!StringUtils.isEmpty(attrValue)) {
                                // construct a query condition of current attribute by using 'like'  expression
                                // and add to condition list
                                // 构造当前属性 like（前后%）属性值查询条件，并添加到条件列表
                                predicates.add(criteriaBuilder.like(root.get(attribute(entity,
                                        attr.getName(), String.class)), pattern((String) attrValue)));
                            }
                        } else {
                        // other happening,construct a query condition of current attribute by using 'equal' expressing
                            // 其他条件，构造属性和属性值 equal 查询条件并添加到条件列表
                            predicates.add(criteriaBuilder.equal(root.get(attribute(entity,
                                    attr.getName(), attrValue.getClass())), attrValue));
                        }
                    }
                }
                // Transform the query condition list to Predicate
                //将条件列表转换成 Predicate
                return predicates.isEmpty() ? criteriaBuilder
                        .conjunction() : criteriaBuilder.and(toArray(predicates, Predicate.class));
            }

            private <T> Object getValue(T example, Attribute<T, ?> attr) {
                // get the attribute value of the corresponding attribute of entity object by reflect function
                // 通过反射获得实体类对象对应属性的属性值
                return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
            }
            // Get the SingularAttribute of the current attribute of the entity class,
            //the SingularAttribute contains a single attribute of the entity class
                // 获得实体类的当前属性的 SingularAttribute，SingularAttribute 包含实体类某个单独属性
            private <E, T> SingularAttribute<T, E> attribute(EntityType<T> entity,
                                                             String fieldName,
                                                             Class<E> fieldClass) {
                return entity.getDeclaredSingularAttribute(fieldName, fieldClass);
            }
        };
    }
    // 构造 like 查询模式
    static private String pattern(String str) {
        return "%" + str + "%";
    }
}
