package io.zipcoder.tc_spring_poll_application.controllers;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VoteController {

    private VoteRepository voteRepository;


    @Autowired
    public VoteController(VoteRepository voteRepository) {

        this.voteRepository = voteRepository;
    }

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote
            vote) {

        vote = voteRepository.save(vote);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());

        return new ResponseEntity<>(vote, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getVote(@PathVariable Long pollId) {

        return voteRepository.findVotesByPoll(pollId);
    }

    @GetMapping("/polls/votes")
    public Iterable<Vote> getAllVotes() {

        return voteRepository.findAll();
    }
}
