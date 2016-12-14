package com.swifta.onerecharge.util;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by moyinoluwa on 9/27/16.
 */

public class InstantTimeFormatter {

    public static String formatInstantTime(String time) {
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT;
        Instant dateInstant = Instant.from(isoFormatter.parse(time));
        LocalDateTime date = LocalDateTime.ofInstant(dateInstant, ZoneId.of(ZoneOffset.UTC.getId()));
        return date.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm a"));
    }
}
