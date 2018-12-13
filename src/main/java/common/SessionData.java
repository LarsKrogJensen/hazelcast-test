package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionData implements Serializable {
    public final static String PUNTER_ID_KEY = "punterId";
    public final static String ROUTING_KEY_KEY = "routingKey";

    private final String id;
    private final long expires;
    private final Map<String, Object> attributes = new HashMap<>();

    public SessionData(String id, long expires) {
        this.id = id;
        this.expires = expires;
    }

    String getId() {
        return id;
    }

    public long getExpires() {
        return expires;
    }

    void put(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    static SessionData create(int punterId, int multiplier) {
        SessionData sessionData = new SessionData(composeSessionId(punterId, multiplier), 1234567L);
        sessionData.put("someData", new SomeData());
        sessionData.put("otherData", new OtherData());
        sessionData.put(ROUTING_KEY_KEY, composeRoutingKey(punterId, multiplier));
        sessionData.put(PUNTER_ID_KEY, punterId);
        return sessionData;
    }

    static String composeSessionId(int punterId, int multiplier) {
        return "session-" + punterId + "-" + multiplier;
    }

    static String composeRoutingKey(int punterId, int multiplier) {
        return "routingKey-" + punterId + "-" + multiplier;
    }
}
