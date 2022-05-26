package ru.yandex.practicum.filmorate.service.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long id, Long friendId) {
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(friendId);
        user.getFriends().add(friendId);
        userFriend.getFriends().add(id);
        log.debug("У пользователя друзей было {} теперь стало{} друзей!", userStorage.findById(id).getName(),
                userStorage.findById(friendId).getFriends().size());
    }

    public void deleteFriend(Long id, Long friendId) {
        userStorage.findById(id).deleteFriend(friendId);
        userStorage.findById(friendId).deleteFriend(id);
        log.debug("У пользователя друзей было {} теперь стало{} друзей!", userStorage.findById(id).getName(),
                userStorage.findById(friendId).getFriends().size());
    }

    public Set<User> getFriends(Long id) {
        Set<User> friends = new HashSet<>();
        for (Long l : userStorage.findById(id).getFriends()) {
            if (userStorage.findById(l) != null)
                friends.add(userStorage.findById(l));
        }
        return friends;
    }

    public List<User> makeFriends(Long id, Long friendId) {
        List<User> allFriends = new ArrayList<>();
        Set<Long> user1Friends = userStorage.findById(id).getFriends();
        Set<Long> user2Friends = userStorage.findById(friendId).getFriends();
        for (Long user : user1Friends) {
            if (user2Friends.contains(user)) {
                allFriends.add(userStorage.findById(user));
            }
        }
        return allFriends;
    }
}