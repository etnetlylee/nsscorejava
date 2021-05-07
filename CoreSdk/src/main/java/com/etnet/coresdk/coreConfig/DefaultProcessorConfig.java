package com.etnet.coresdk.coreConfig;

import java.util.HashMap;
import java.util.Map;

import com.etnet.coresdk.constants.ProcessorId;

import com.etnet.coresdk.coreModel.ProcessorInfo;
import com.etnet.coresdk.coreProcessor.BrokerProcessor;
import com.etnet.coresdk.coreProcessor.HeartbeatProcessor;
import com.etnet.coresdk.coreProcessor.LoginProcessor;
import com.etnet.coresdk.coreProcessor.NewsProcessor;
import com.etnet.coresdk.coreProcessor.QuoteProcessor;
import com.etnet.coresdk.coreProcessor.ReconnectProcessor;
import com.etnet.coresdk.coreProcessor.SortProcessor;

public class DefaultProcessorConfig {
    public static final Map<String, ProcessorInfo> DefaultProcessorConfig = new HashMap<String, ProcessorInfo>() {{
        put(ProcessorId.PID_0, new ProcessorInfo("csv", HeartbeatProcessor.id, new HeartbeatProcessor()));
        put(ProcessorId.PID_1, new ProcessorInfo("csv", QuoteProcessor.id, new QuoteProcessor()));
        put(ProcessorId.PID_2, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_3, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_4, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_5, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_6, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_7, new ProcessorInfo("csv", BrokerProcessor.id, new BrokerProcessor()));
        put(ProcessorId.PID_8, new ProcessorInfo("csv", LoginProcessor.id, new LoginProcessor()));
        put(ProcessorId.PID_9, new ProcessorInfo("csv", NewsProcessor.id, new NewsProcessor()));
        put(ProcessorId.PID_10, new ProcessorInfo("csv", ReconnectProcessor.id, new ReconnectProcessor()));
        put(ProcessorId.PID_14, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_15, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_16, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_17, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_20, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_21, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));
        put(ProcessorId.PID_22, new ProcessorInfo("csv", SortProcessor.id, new SortProcessor()));

    }};
}
