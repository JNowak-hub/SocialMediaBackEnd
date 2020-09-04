package pl.surf.web.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.repository.EventRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {
    public static final String DESCRIPTION = "des";
    public static final String LOCATION = "place";
    public static final String TITLE = "title";
    public static final LocalDate DATE = LocalDate.of(2100, 9, 9);
    public static final LocalTime TIME = LocalTime.of(15,0);
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private EventService eventService;

    @AfterEach
    void tearDown() {
        eventRepo.deleteAll();
    }

    @Test
    void shouldAddEvent() {

        final Event event = new Event(TITLE, DESCRIPTION, LOCATION, DATE, TIME, null,7);
        eventService.addEvent(event);

        final List<Event> allEvents = eventRepo.findAll();

        assertThat(allEvents.get(0).getDescription()).isEqualTo(DESCRIPTION);
        assertThat(allEvents.get(0).getLocation()).isEqualTo(LOCATION);
        assertThat(allEvents.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(allEvents.get(0).getDate()).isEqualTo(DATE);
    }

    @Test
    void shouldGetCurrentEvents() {
        final Event event = new Event(TITLE, DESCRIPTION, LOCATION, DATE,TIME, null,7);
        eventRepo.save(event);

        final List<Event> allCurrentEvents = eventService.getCurrentEvents();
        assertEquals(1, allCurrentEvents.size());
        assertThat(allCurrentEvents.get(0).getDescription()).isEqualTo(DESCRIPTION);
        assertThat(allCurrentEvents.get(0).getLocation()).isEqualTo(LOCATION);
        assertThat(allCurrentEvents.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(allCurrentEvents.get(0).getDate()).isEqualTo(DATE);
    }
}