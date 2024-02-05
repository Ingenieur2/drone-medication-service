package com.company.core.repository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * {@code DbRepository} class connected with database.
 * <p>
 * After calling all methods log with SQL script is written
 */

@Repository
public class DbRepository {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DbRepository.class);
    @Autowired
    @Qualifier("jdbc_template")
    public JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getDbData(String sql) {
        log.info("Script for getting information: {}", sql);
        return jdbcTemplate.queryForList(sql);
    }

    public Double getDbValue(String sql) {
        log.info("Script for getting aggregated value: {}", sql);
        Double result = jdbcTemplate.queryForObject(sql, Double.class);
        return Objects.requireNonNullElse(result, 0.0);
    }

    public int putDbData(String sql) {
        log.info("Script for putting information: {}", sql);
        return jdbcTemplate.update(sql);
    }
}