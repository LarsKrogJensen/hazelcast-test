package common;

import com.hazelcast.core.IMap;

import java.util.Collection;

public interface IQueryProvider {
    Collection<String> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId);
}
