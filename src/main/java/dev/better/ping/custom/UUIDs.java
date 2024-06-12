package dev.better.ping.custom;

import com.google.common.base.Strings;
import com.google.common.primitives.UnsignedLongs;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public final class UUIDs {

    private UUIDs() {}

    public static final UUID EMPTY = new UUID(0, 0);

    public interface Variant {

        UUID parse(String uuid);

        String toString(UUID uuid);
    }

    public static final Variant STANDARD = new Variant() {

        @Override
        public UUID parse(String uuid) {
            return UUID.fromString(uuid);
        }

        @Override
        public String toString(UUID uuid) {
            return uuid.toString();
        }

    };

    private static String toHexString(long unsigned) {
        return Strings.padStart(UnsignedLongs.toString(unsigned, 16), 16, '0');
    }

    public static final Variant NO_DASHES = new Variant() {

        @Override
        public UUID parse(String uuid) {
            checkArgument(uuid.length() == 32, "Not an UUID: " + uuid);
            return new UUID(UnsignedLongs.parseUnsignedLong(uuid.substring(0, 16), 16),
                    UnsignedLongs.parseUnsignedLong(uuid.substring(16), 16));
        }

        @Override
        public String toString(UUID uuid) {
            return toHexString(uuid.getMostSignificantBits()) + toHexString(uuid.getLeastSignificantBits());
        }

    };

    public static UUID parse(String uuid) {
        if (uuid.indexOf('-') == -1) {
            return NO_DASHES.parse(uuid);
        }
        return STANDARD.parse(uuid);
    }

    public static UUID parseLenient(String uuid) {
        if (uuid.indexOf('-') == -1) {
            return NO_DASHES.parse(uuid);
        }

        try {
            return STANDARD.parse(uuid);
        } catch (IllegalArgumentException ignored) {}

        return NO_DASHES.parse(uuid.replace("-", ""));
    }
}
