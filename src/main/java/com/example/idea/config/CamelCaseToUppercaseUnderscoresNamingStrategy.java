package com.example.idea.config;

import java.util.Locale;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @Author : Timothy Lee
 * Java의 카멜케이스(예: myHome)를 DB의 대문자_대문자(예: MY_HOME)으로 매핑하는 커스컴 전략 클래스
 */
public class CamelCaseToUppercaseUnderscoresNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
    @Override
    protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
        if (this.isCaseInsensitive(jdbcEnvironment)) {
            name = name.toUpperCase(Locale.ROOT);
        }

        return new Identifier(name, quoted);
    }
}
