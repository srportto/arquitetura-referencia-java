package dev.danvega.meetup.group;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/groups")
class GroupController {

    private final List<Group> groups = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping
    List<Group> findAll() {
        return groups;
    }

    @GetMapping("/{id}")
    Group findById(@PathVariable Long id) {
        return groups.stream()
                .filter(g -> g.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Group create(@RequestBody Group group) {
        Group newGroup = new Group(
                idCounter.getAndIncrement(),
                group.name(),
                group.description(),
                group.city(),
                group.organizer(),
                group.createdDate() != null ? group.createdDate() : LocalDate.now()
        );
        groups.add(newGroup);
        return newGroup;
    }

    @PutMapping("/{id}")
    Group update(@PathVariable Long id, @RequestBody Group group) {
        Group existing = findById(id);
        Group updatedGroup = new Group(
                id,
                group.name(),
                group.description(),
                group.city(),
                group.organizer(),
                group.createdDate()
        );
        groups.remove(existing);
        groups.add(updatedGroup);
        return updatedGroup;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        Group existing = findById(id);
        groups.remove(existing);
    }

    @PostConstruct
    private void init() {
        groups.add(new Group(
                idCounter.getAndIncrement(),
                "Cleveland Java User Group",
                "A community of Java developers in the Cleveland area sharing knowledge and best practices",
                "Cleveland",
                "Dan Vega",
                LocalDate.of(2010, 3, 15)
        ));

        groups.add(new Group(
                idCounter.getAndIncrement(),
                "Cleveland React Meetup",
                "Monthly meetup for React and JavaScript developers in Northeast Ohio",
                "Cleveland",
                "Sarah Johnson",
                LocalDate.of(2017, 6, 1)
        ));

        groups.add(new Group(
                idCounter.getAndIncrement(),
                "Cleveland Python User Group",
                "Python enthusiasts meeting to discuss Python programming, data science, and automation",
                "Cleveland",
                "Mike Chen",
                LocalDate.of(2012, 9, 20)
        ));

        groups.add(new Group(
                idCounter.getAndIncrement(),
                "Cleveland Tech Slack",
                "General technology community connecting developers, designers, and tech professionals in Cleveland",
                "Cleveland",
                "Community Led",
                LocalDate.of(2015, 1, 10)
        ));
    }
}
