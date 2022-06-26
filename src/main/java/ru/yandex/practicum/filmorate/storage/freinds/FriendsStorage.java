package ru.yandex.practicum.filmorate.storage.freinds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
@Slf4j
public class FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendsStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void addFriend (Long id, Long friendId) {
        if (isUserExists(id) && isUserExists(friendId)) {
            String sql = "INSERT INTO FRIENDSHIPS (user_id, friend_id, is_approved) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, id, friendId, false);
            log.info("user {} добавить пользователя {} к друзьям", id, friendId);
        } else throw new UserException("Попытка добавить неизвестного пользователя в друзья");
    }

    public Collection<User> findFriends(Long id) {
        String sql = "SELECT FRIEND_ID, EMAIL, LOGIN, NAME, BIRTHDAY FROM FRIENDSHIPS JOIN USERS U " +
                "on FRIENDSHIPS.FRIEND_ID = U.USER_ID WHERE " +
                "FRIENDSHIPS.USER_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                        rs.getLong("friend_id"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("name"),
                        rs.getDate("birthday").toLocalDate()),
                id
        );
    }

    private boolean isUserExists(Long id) {
        String sql = "SELECT * FROM USERS WHERE user_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        return userRows.next();
    }

    public void deleteFriend(Long id, Long friendId) {
        if (isUserExists(id) && isUserExists(friendId)) {
            String sql = "DELETE FROM FRIENDSHIPS WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, id, friendId);
        }  else throw new UserException("Попытка удалить неизвестного пользователя из списка друзей");
    }
}