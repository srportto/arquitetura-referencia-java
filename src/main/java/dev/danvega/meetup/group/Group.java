package dev.danvega.meetup.group;

import java.time.LocalDate;

record Group(
        Long id,
        String name,
        String description,
        String city,
        String organizer, // will be a member later
        LocalDate createdDate
) {
}
