package woowa.team4.bff.domain.common.utils;

import java.util.UUID;

public final class PrefixedUuidConverter {

    private PrefixedUuidConverter() {
    }

    public static UUID extractUUID(String prefixedUUID) {
        if (prefixedUUID == null || prefixedUUID.isEmpty()) {
            throw new IllegalArgumentException("입력된 UUID 문자열이 null이거나 비어 있습니다.");
        }

        String[] parts = prefixedUUID.split("_");
        if (parts.length < 2) {
            throw new IllegalArgumentException("유효하지 않은 접두사가 붙은 UUID 형식입니다: " + prefixedUUID);
        }

        String uuidString = parts[parts.length - 1];

        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 UUID 형식입니다: " + uuidString, e);
        }
    }

    public static String addPrefix(String prefix, UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID가 null입니다.");
        }
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("접두사가 null이거나 비어 있습니다.");
        }
        return prefix + "_" + uuid;
    }
}
