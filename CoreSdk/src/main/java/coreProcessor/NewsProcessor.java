package coreProcessor;

import java.util.List;

import coreModel.NssCoreContext;
import coreModel.NssPacket;
import coreModel.Processor;

public class NewsProcessor extends Processor {
    public static final String id = "news";
    // NewsDecodeDispatcher newsDecodeDispatcher;

    public NewsProcessor() {
        // newsDecodeDispatcher = new NewsDecodeDispatcher();
    }

    @Override
    public void setContext(NssCoreContext context) {
        // newsDecodeDispatcher.setContext(context);
    }

    @Override
    public Object process(NssPacket nssPacket) {
        // TODO implement process
        return null;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        List<Object> _tempData = (List<Object>) data;
        this.fireNewsUpdate(_tempData);
    }

    void fireNewsUpdate(List<Object> news) {
        try {
            // this.newsDecodeDispatcher.decode(null, null, news);
        } catch (Exception e) {
        }
    }
}
