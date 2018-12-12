package common;

import com.hazelcast.query.extractor.ValueCollector;
import com.hazelcast.query.extractor.ValueExtractor;

public class PunterIdExtractor extends ValueExtractor<SessionData, Void> {
    @Override
    public void extract(SessionData target, Void argument, ValueCollector collector) {
        collector.addObject(target.get(SessionData.PUNTER_ID_KEY));
    }
}
