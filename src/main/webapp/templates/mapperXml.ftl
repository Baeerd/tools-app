<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${mapperClass}" >

    <resultMap id="BaseResultMap" type="${entityClass}" >
    <#list properlist as prop>
        <id column="${prop.dbaName}" property="${prop.fieldName}" jdbcType="${prop.mapperType}" />
    </#list>
    </resultMap>

    <sql id="Base_Column_List" >
    <#list properlist as prop>
    <#if prop_has_next>
        ${prop.dbaName},
    <#else>
        ${prop.dbaName}
    </#if>
    </#list>
    </sql>

    <select id="${find}" resultMap="BaseResultMap" parameterType="java.util.Map">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        <trim prefix="WHERE" prefixOverrides="AND |OR ">
        <#list properlist as prop>
            <if test="${prop.fieldName} != null" >
                AND ${prop.dbaName} = <#noparse>#{</#noparse>${prop.fieldName},jdbcType=${prop.mapperType}}
            </if>
        </#list>
        </trim>
        ORDER BY
        <if test="order != null">
        <#noparse>${</#noparse>order}
        </if>
        <if test="order == null">
            SID DESC
        </if>
    </select>

    <delete id="${delete}" parameterType="${entityClass}" >
        delete from ${tableName} where SID = <#noparse>#{</#noparse>sid,jdbcType=DECIMAL}
    </delete>

    <insert id="${insert}" parameterType="${entityClass}" >
        <selectKey resultType="java.lang.Long" keyProperty="sid" order="BEFORE" >
            SELECT ${insertSeq}.nextval FROM DUAL
        </selectKey>
        insert into ${tableName} (
        <#list properlist as prop>
        <#if prop_has_next>
           ${prop.dbaName},
        <#else>
           ${prop.dbaName}
        </#if>
        </#list>
        )
        values (
        <#list properlist as prop>
        <#if prop_has_next>
            <#noparse>#{</#noparse>${prop.fieldName},jdbcType=${prop.mapperType}},
        <#else>
            <#noparse>#{</#noparse>${prop.fieldName},jdbcType=${prop.mapperType}}
        </#if>
        </#list>
        )
    </insert>

    <update id="${update}" parameterType="${entityClass}" >
        update ${tableName} set version = version + 1,
        <trim suffixOverrides=",">
        <#list properlist as prop>
            <#if (prop.fieldName != 'sid' && prop.fieldName != 'version')>
            <if test="${prop.fieldName} != null" >
                ${prop.dbaName} = <#noparse>#{</#noparse>${prop.fieldName},jdbcType=${prop.mapperType}},
            </if>
            </#if>
        </#list>
        </trim>
        where SID = <#noparse>#{</#noparse>sid,jdbcType=DECIMAL}
    </update>
</mapper>