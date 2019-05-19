package com.agranibank.NewsDirectory.dao;


import com.agranibank.NewsDirectory.model.Agency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sayed Mahmud Raihan on 12/06/18.
 */

@Slf4j
@Repository
public class AgencyDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Agency add(Agency agency) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("agency")
                .usingGeneratedKeyColumns("id");

        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("agency_name", agency.getAgencyName());
        parameterMap.put("description", agency.getDescription());
        parameterMap.put("agency_type", agency.getAgencyType());
        parameterMap.put("created_at", agency.getCreateAt());
        parameterMap.put("updated_at", agency.getUpdatedAt());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterMap);
            if(autoGenId !=null){
                agency.setId(autoGenId.intValue());
                log.info("Agency Added With ID {}", autoGenId);
                return agency;
            }

        }catch (DataAccessException dae){
            log.error(dae.getMessage());
            log.info("Agency did not added: {}", dae.getLocalizedMessage());
            return agency;
        }
        return agency;
    }

    public List<Agency> findAll() {
        String query = "SELECT * FROM agency";
        try {
            return jdbcTemplate.query(query, new AgencyRowMapper());
        } catch (DataAccessException e) {
            log.error("Agency query execution failed: {}. Error: {}", query, e.getMessage());
            return new ArrayList<>();
        }
    }

    public Agency findById(Integer id) {
        String query = "SELECT * from agency where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new AgencyRowMapper());
        } catch (DataAccessException dae) {
            log.error("Agency Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new Agency();
    }

    public boolean update(Agency agency) {
        String query = "UPDATE agency SET  agency_name = ?, " +
                "description = ?, agency_type = ?, " +
                "updated_at = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(query, agency.getAgencyName(), agency.getDescription(),
                    agency.getAgencyType().name(), agency.getUpdatedAt(), agency.getId()) == 1;
        } catch (DataAccessException e) {
            log.error("Update failed for Agency: {}. Error: {}", agency, e.getLocalizedMessage());
            return false;
        }
    }

    public boolean delete(Integer agencyId) {
        log.info("Deleting Agency Of agency id: {} ", agencyId);
        try {
            return jdbcTemplate.update("DELETE FROM agency WHERE id = ?", agencyId) == 1;
        } catch (DataAccessException dae) {
            log.error("Error : {} Deleting Agency id: {} " , dae.getLocalizedMessage(), agencyId);
        }
        return false;
    }

    class AgencyRowMapper implements RowMapper<Agency> {

        @Override
        public Agency mapRow(ResultSet rs, int i) throws SQLException {
            Agency agency = new Agency();
            agency.setId(rs.getInt("id"));
            agency.setAgencyName(rs.getString("agency_name"));
            agency.setDescription(rs.getString("description"));
            agency.setAgencyType(Agency.AgencyType.valueOf(rs.getString("agency_type")));
            agency.setCreateAt(rs.getDate("created_at"));
            agency.setUpdatedAt(rs.getDate("updated_at"));
            return agency;
        }
    }
}
