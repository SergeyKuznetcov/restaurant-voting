package ru.topjava.graduate.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.graduate.restaurantvoting.model.Vote;

import java.util.List;

@RestController
@Slf4j
@RequestMapping()
@AllArgsConstructor
public class AdminVoteController {

    @GetMapping()
    public List<Vote> getResults() {
        return null;
    }
}
