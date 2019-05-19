package com.agranibank.NewsDirectory.dao;

import com.agranibank.NewsDirectory.model.Agency;
import com.agranibank.NewsDirectory.model.News;
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
public class NewsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public News add(News news) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("news")
                .usingGeneratedKeyColumns("id");

        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("title", news.getTitle());
        parameterMap.put("description", news.getDescription());
        parameterMap.put("amount", news.getAmount());
        parameterMap.put("agency_id", news.getAgency().getId());
        parameterMap.put("status", news.getStatus());
        parameterMap.put("quantity", news.getQuantity());
        parameterMap.put("created_at", news.getCreatedAt());
        parameterMap.put("updated_at", news.getUpdatedAt());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterMap);
            if(autoGenId !=null){
                news.setId(autoGenId.intValue());
                log.info("News Added With ID {}", autoGenId);
                return news;
            }

        }catch (DataAccessException dae){
            log.error(dae.getMessage());
            log.info("News did not added: {}", dae.getLocalizedMessage());
            return news;
        }
        return news;
    }

    public List<News> findAll() {
        String query = "SELECT news.*,agency_name FROM news INNER JOIN agency " +
                "ON news.agency_id = agency.id";
        try {
            return jdbcTemplate.query(query, new RowMapper<News>() {
                @Override
                public News mapRow(ResultSet rs, int i) throws SQLException {
                    News news = new News();
                    news.setId(rs.getInt("id"));
                    news.setTitle(rs.getString("title"));
                    news.setDescription(rs.getString("description"));
                    news.setAmount(rs.getBigDecimal("amount"));
                    Agency agency = new Agency();
                    agency.setId(rs.getInt("agency_id"));
                    agency.setAgencyName(rs.getString("agency_name"));
                    news.setAgency(agency);
                    news.setStatus(News.Status.valueOf(rs.getString("status")));
                    news.setQuantity(rs.getInt("quantity"));
                    news.setCreatedAt(rs.getDate("created_at"));
                    news.setUpdatedAt(rs.getDate("updated_at"));
                    return news;
                }
            });
        } catch (DataAccessException e) {
            log.error("News query execution failed: {}. Error: {}", query, e.getMessage());
            return new ArrayList<>();
        }
    }

    public News findById(Integer id) {
        String query = "SELECT * from news where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new NewsRowMapper());
        } catch (DataAccessException dae) {
            log.error("News Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new News();
    }

    public boolean update(News news) {
        String query = "UPDATE news SET  title = ?, " +
                "description = ?, amount = ?, agency_id=?, status=?, quantity=?, " +
                "updated_at = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(query, news.getTitle(), news.getDescription(),
                    news.getAmount(), news.getAgency().getId(),news.getStatus().name(),
                    news.getQuantity(), news.getUpdatedAt(), news.getId()) == 1;
        } catch (DataAccessException e) {
            log.error("Update failed for News: {}. Error: {}", news, e.getLocalizedMessage());
            return false;
        }
    }

    public boolean delete(Integer newsId) {
        log.info("Deleting News Of NewsId: {} ", newsId);
        try {
            return jdbcTemplate.update("DELETE FROM news WHERE id = ?", newsId) == 1;
        } catch (DataAccessException dae) {
            log.error("Error : {} Deleting News id: {} " , dae.getLocalizedMessage(), newsId);
        }
        return false;
    }

    class NewsRowMapper implements RowMapper<News> {

        @Override
        public News mapRow(ResultSet rs, int i) throws SQLException {
            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setDescription(rs.getString("description"));
            news.setAmount(rs.getBigDecimal("amount"));
            Agency agency = new Agency();
            agency.setId(rs.getInt("agency_id"));
            news.setAgency(agency);
            news.setStatus(News.Status.valueOf(rs.getString("status")));
            news.setQuantity(rs.getInt("quantity"));
            news.setCreatedAt(rs.getDate("created_at"));
            news.setUpdatedAt(rs.getDate("updated_at"));
            return news;
        }
    }
}
