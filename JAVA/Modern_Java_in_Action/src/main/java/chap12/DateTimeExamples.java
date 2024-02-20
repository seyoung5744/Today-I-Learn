package chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeExamples {

    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    public static void main(String[] args) {
        useOldDate();
        useLocalDate();
        useTemporalInterface();
        useTemporalAdjuster();
        useDateFormatter();
        useVariousTimeZoneAndCalendar();
    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        LocalDate date = LocalDate.of(2017, 9, 21); // 2017-09-21
        int year = date.getYear(); // 2017
        Month month = date.getMonth(); // SEPTENMBER
        int day = date.getDayOfMonth(); // 21
        DayOfWeek dow = date.getDayOfWeek(); // THURSDAY
        int len = date.lengthOfMonth(); // 31 (3월의 일 수)
        boolean leap = date.isLeapYear(); // false (윤년이 아님)

        System.out.println(date);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(dow);
        System.out.println(len);
        System.out.println(leap);
        System.out.println("========================\n");

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println(y);
        System.out.println(m);
        System.out.println(d);
        System.out.println("========================\n");

        y = date.getYear();
        m = date.getMonthValue();
        d = date.getDayOfMonth();
        System.out.println(y);
        System.out.println(m);
        System.out.println(d);
        System.out.println("========================\n");

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println(hour);
        System.out.println(minute);
        System.out.println(second);
        System.out.println("========================\n");

        date = LocalDate.parse("2024-02-20");
        System.out.println(date);
        time = LocalTime.parse("20:24:45");
        System.out.println(time);
        System.out.println("========================\n");

        // 2024-02-20T20:31:30
        LocalDateTime dt1 = LocalDateTime.of(2024, Month.FEBRUARY, 20, 20, 31, 30);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt1);
        System.out.println(dt2);
        System.out.println(dt3);
        System.out.println(dt4);
        System.out.println(dt5);
        System.out.println("========================\n");

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);
        System.out.println("========================\n");

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        System.out.println(instant);
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println("========================\n");

        Duration d1 = Duration.between(LocalTime.of(20, 0, 0), LocalTime.now());
        System.out.println(d1);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d2);
        System.out.println("========================\n");

        Duration threeMinutes1 = Duration.ofMinutes(3);
        System.out.println(threeMinutes1);
        Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes2);
        System.out.println("========================\n");

        Period days = Period.between(LocalDate.of(2024, 2, 10), LocalDate.now());
        System.out.println(days);

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSizMonthsOneDay = Period.of(2, 5, 1);
        System.out.println(tenDays);
        System.out.println(threeWeeks);
        System.out.println(twoYearsSizMonthsOneDay);
        System.out.println("========================\n");

    }

    private static void useTemporalInterface() {
        LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
        LocalDate date2 = date1.withYear(2011); //  2011-09-21
        LocalDate date3 = date2.withDayOfMonth(25); // 2011-09-25
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-25
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
        System.out.println("========================\n");

        date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
        date2 = date1.plusWeeks(1); // 2017-09-28
        date3 = date2.minusYears(6); // 2011-09-28
        date4 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
        System.out.println("========================\n");

    }

    private static void useTemporalAdjuster() {
        LocalDate date = LocalDate.of(2024, 2, 20); // 2024-02-20
        date = date.with(nextOrSame(DayOfWeek.SUNDAY)); // 2024-02-25
        System.out.println(date);
        date = date.with(lastDayOfMonth()); // 2024-02-29
        System.out.println(date);
        System.out.println("========================\n");

        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);
        System.out.println("========================\n");

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);

        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
            temporal -> {
                DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                int dayToAdd = 1;
                if (dow == DayOfWeek.FRIDAY) {
                    dayToAdd = 3;
                }
                if (dow == DayOfWeek.SATURDAY) {
                    dayToAdd = 2;
                }
                return temporal.plus(dayToAdd, ChronoUnit.DAYS);
            });

        date = date.with(nextWorkingDay);
        System.out.println(date);
        System.out.println("========================\n");
    }

    private static class NextWorkingDay implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK)); // 현재 날짜 읽기
            int dayToAdd = 1; // 보통은 하루 추가
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3; // 오늘이 금요일이면 3일 추가
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2; // 토요일이면 2일 추가
            }

            return temporal.plus(dayToAdd, ChronoUnit.DAYS); // 적정한 날 수만큼 추가된 날짜를 반환
        }
    }

    private static void useDateFormatter() {
        LocalDate date = LocalDate.of(2024, 2, 20); // 2024-02-20
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(s1 + ", " + s1.getClass()); // 20240220
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s2+ ", " + s2.getClass()); // 2024-02-20
        System.out.println("========================\n");

        LocalDate date1 = LocalDate.parse("20240220", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(date1);
        LocalDate date2 = LocalDate.parse("2024-02-20", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(date2);
        System.out.println("========================\n");

        date = LocalDate.of(2024, 2, 20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        System.out.println(formattedDate);
        date = LocalDate.parse(formattedDate, formatter);
        System.out.println(date);
        System.out.println("========================\n");

        date = LocalDate.of(2024, 2, 20);
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        String formattedDate2 = date.format(italianFormatter);
        System.out.println(formattedDate2);
        LocalDate date3 = LocalDate.parse(formattedDate2, italianFormatter);
        System.out.println(date3);
        System.out.println("========================\n");

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_MONTH)
            .appendLiteral(". ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendLiteral(" ")
            .appendText(ChronoField.YEAR)
            .parseCaseInsensitive()
            .toFormatter(Locale.ITALIAN);

        System.out.println(date.format(complexFormatter));
        System.out.println("========================\n");
    }

    private static void useVariousTimeZoneAndCalendar() {
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();

        LocalDate date = LocalDate.of(2024, Month.FEBRUARY, 20);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.FEBRUARY, 20, 21, 45, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);
        System.out.println(zdt1);
        System.out.println(zdt2);
        System.out.println(zdt3);
        System.out.println("====================\n");

        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        LocalDateTime dateTime2 = LocalDateTime.of(2024, Month.FEBRUARY, 20, 21, 45, 45);
        
    }
}
