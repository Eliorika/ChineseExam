package ru.rsreu.ChineseCourse.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.rsreu.ChineseCourse.model.Variant;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VariantRowMapper implements RowMapper<Variant> {
    @Override
    public Variant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Variant variant = new Variant();
        variant.setId(rs.getLong("id"));
        variant.setVariant(rs.getString("variant"));
        return variant;
    }
}
