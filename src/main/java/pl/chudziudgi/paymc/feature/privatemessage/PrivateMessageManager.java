package pl.chudziudgi.paymc.feature.privatemessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrivateMessageManager {

    private final Map<UUID, UUID> lastSenderMap = new HashMap<>();

    public void setLastSender(UUID recipient, UUID sender) {
        lastSenderMap.put(recipient, sender);
    }

    public UUID getLastSender(UUID recipient) {
        return lastSenderMap.get(recipient);
    }

    public boolean hasLastSender(UUID recipient) {
        return lastSenderMap.containsKey(recipient);
    }
}