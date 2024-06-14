package plugins;


import athlonix.Plugin;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.scene.Scene;

import java.time.LocalTime;

public class AthlonixPlugin implements Plugin {

    private final String NAME = "Calendar Plugin";
    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void run() {

    }

    @Override
    public Scene getScene() {
        CalendarView calendarView = new CalendarView(); // (1)

        Calendar birthdays = new Calendar("Birthdays"); // (2)
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Calendar.Style.STYLE1); // (3)
        holidays.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); // (4)
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

        calendarView.getCalendarSources().addAll(myCalendarSource); // (5)

        calendarView.setRequestedTime(LocalTime.now());

        return new Scene(calendarView);
    }

    @Override
    public String getName() {
        return NAME;
    }
}