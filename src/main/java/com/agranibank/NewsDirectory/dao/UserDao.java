package com.agranibank.NewsDirectory.dao;

import com.agranibank.NewsDirectory.model.Role;
import com.agranibank.NewsDirectory.model.User;
import com.agranibank.NewsDirectory.utils.Helper;
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
 * Created by Sayed Mahmud Raihan on 12/05/18.
 */

@Slf4j
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User add(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        if(user.getUserName() == null){
            return user;
        }
        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("fullname", user.getFullName());
        parameterMap.put("username",user.getUserName());
        parameterMap.put("password",Helper.bCryptEncoder.encode(user.getPassword()));
        parameterMap.put("role_id", user.getRole().getId());
        parameterMap.put("is_active", user.getIsActive());
        parameterMap.put("gender", user.getGender());
        parameterMap.put("address", user.getAddress());
        parameterMap.put("phone", user.getPhone());
        parameterMap.put("email", user.getEmail());
        parameterMap.put("created_at", user.getCreatedAt());
        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterMap);

            if(autoGenId !=null){
                user.setId(autoGenId.intValue());
                log.info("User Added With ID {}",autoGenId);
                return user;
            }

        }catch (DataAccessException dae){
            log.error(dae.getMessage());
            log.info("User did not added: {}",dae.getLocalizedMessage());
            return user;
        }
        return user;
    }

    public List<User> findAll() {
        String query = "SELECT user.*, role.role_name FROM user INNER JOIN role " +
                "ON user.role_id=role.id";
        try {
            return jdbcTemplate.query(query, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFullName(resultSet.getString("fullname"));
                    user.setUserName(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    Role role = new Role();
                    role.setId(resultSet.getInt("role_id"));
                    role.setName(Role.RoleName.valueOf(resultSet.getString("role_name")));
                    user.setRole(role);
                    user.setIsActive(resultSet.getBoolean("is_active"));
                    user.setGender(User.Gender.valueOf(resultSet.getString("gender")));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setEmail(resultSet.getString("email"));
                    user.setCreatedAt(resultSet.getDate("created_at"));
                    user.setUpdatedAt(resultSet.getDate("updated_at"));
                    return user;
                }
            });
        } catch (DataAccessException e) {
            log.error("User query execution failed: {}. Error: {}", query, e.getMessage());
            return new ArrayList<>();
        }
    }

    public User findById(Integer id) {
        String query = "SELECT * from user where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new UserRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new User();
    }


    public User findUserByUserName(String userName) {
        String query = "SELECT * FROM user \n" +
                "WHERE username = ?";
        try{
            return jdbcTemplate.queryForObject(query,new Object[]{userName},new UserRowMapper());
        }catch (DataAccessException dae){
            log.error("User Data Not Found, Error: {}",dae.getLocalizedMessage());
            return new User();
        }
    }

    public boolean update(User user) {
        String query = "UPDATE user SET  fullname = ?, " +
                "username = ?, role_id=?,is_active=?, gender=?, address=?, " +
                "phone=?, email=?, updated_at = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(query, user.getFullName(), user.getUserName(),
                    user.getRole().getId(), user.getIsActive(), user.getGender().name(),
                    user.getAddress(), user.getPhone(), user.getEmail(), user.getUpdatedAt() , user.getId()) == 1;
        } catch (DataAccessException e) {
            log.error("Update failed for User: {}. Error: {}", user, e.getLocalizedMessage());
            return false;
        }
    }
    public boolean delete(Integer userId) {
        log.info("Deleting User Of UserId: {} ", userId);
        try {
            return jdbcTemplate.update("DELETE FROM user WHERE id = ?", userId) == 1;
        } catch (DataAccessException dae) {
            log.error("Error : {} Deleting User id: {} " , dae.getLocalizedMessage(), userId);
        }
        return false;
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFullName(resultSet.getString("fullname"));
            user.setUserName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            Role role = new Role();
            role.setId(resultSet.getInt("role_id"));
            user.setRole(role);
            user.setIsActive(resultSet.getBoolean("is_active"));
            user.setGender(User.Gender.valueOf(resultSet.getString("gender")));
            user.setAddress(resultSet.getString("address"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setCreatedAt(resultSet.getDate("created_at"));
            user.setUpdatedAt(resultSet.getDate("updated_at"));
            return user;
        }
    }

}
