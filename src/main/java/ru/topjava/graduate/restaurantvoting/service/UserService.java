package ru.topjava.graduate.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final VoteService voteService;
    private final UserRepository userRepository;

    @Transactional
    public void delete(int id) {
        voteService.deleteAllByUserId(id);
        userRepository.deleteExisted(id);
    }
}
