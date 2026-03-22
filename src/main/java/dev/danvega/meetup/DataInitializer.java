package dev.danvega.meetup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import dev.danvega.meetup.group.Group;
import dev.danvega.meetup.group.GroupRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(GroupRepository groupRepository) {
        return args -> {
            if (groupRepository.count() == 0) { // Only add if empty
                groupRepository.save(new Group(null, "Cleveland Java User Group",
                        "A community of Java developers in the Cleveland area sharing knowledge and best practices",
                        "Cleveland", "Dan Vega", LocalDate.of(2010, 3, 15)));

                groupRepository.save(new Group(null, "Cleveland React Meetup",
                        "Monthly meetup for React and JavaScript developers in Northeast Ohio",
                        "Cleveland", "Sarah Johnson", LocalDate.of(2017, 6, 1)));

                groupRepository.save(new Group(null, "Cleveland Python User Group",
                        "Python enthusiasts meeting to discuss Python programming, data science, and automation",
                        "Cleveland", "Mike Chen", LocalDate.of(2012, 9, 20)));

                groupRepository.save(new Group(null, "Cleveland Tech Slack",
                        "General technology community connecting developers, designers, and tech professionals in Cleveland",
                        "Cleveland", "Community Led", LocalDate.of(2015, 1, 10)));
            }
        };
    }
}