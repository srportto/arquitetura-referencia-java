package dev.danvega.meetup.group;

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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    Group findById(@PathVariable Long id) {
        try {
            return groupService.findById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Group create(@RequestBody Group group) {
        Group newGroup = new Group(
                null, // id will be generated
                group.getName(),
                group.getDescription(),
                group.getCity(),
                group.getOrganizer(),
                group.getCreatedDate() != null ? group.getCreatedDate() : LocalDate.now()
        );
        return groupService.save(newGroup);
    }

    @PutMapping("/{id}")
    Group update(@PathVariable Long id, @RequestBody Group group) {
        Group existing = findById(id);
        existing.setName(group.getName());
        existing.setDescription(group.getDescription());
        existing.setCity(group.getCity());
        existing.setOrganizer(group.getOrganizer());
        existing.setCreatedDate(group.getCreatedDate());
        return groupService.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        try {
            groupService.deleteById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
