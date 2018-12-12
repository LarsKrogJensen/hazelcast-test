package common;

import com.hazelcast.core.IMap;

import java.util.Collection;

public interface QueryProvider {
    Collection<SessionData> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId);
}
