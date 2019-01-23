package io.zipcoder.tc_spring_poll_application.controllers;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.exception.ResourceNotFoundException;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PollController {

    private PollRepository pollRepository;

    @Autowired
    public PollController(PollRepository pollRepository) {

        this.pollRepository = pollRepository;
    }


    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {

        Iterable<Poll> allPolls = pollRepository.findAll();

        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @Valid
    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {

        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(newPollUri);

        poll = pollRepository.save(poll);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {

        verifyPoll(pollId);

        Poll p = pollRepository.findOne(pollId);

        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @Valid
    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {

        verifyPoll(pollId);

        Poll p = pollRepository.save(poll);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {

        pollRepository.delete(pollId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public void verifyPoll(Long pollId) {

        try {

            if (pollRepository.findOne(pollId) == null) {

                throw new ResourceNotFoundException();
            }

        } catch (ResourceNotFoundException rnfe) {

            rnfe.printStackTrace();
        }
    }
}
