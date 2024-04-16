package com.example.advanced.trace.logtrace;

import com.example.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FiledLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();

    @Test
    public void begin_end_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }
    
    @Test
    public void begin_exception_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}