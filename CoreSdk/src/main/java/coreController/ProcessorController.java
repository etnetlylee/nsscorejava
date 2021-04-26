package coreController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import api.ContextProvider;
import coreModel.NssCoreContext;
import coreModel.NssPacket;
import coreModel.Processor;
import coreModel.ProcessorInfo;

public class ProcessorController extends ContextProvider {
    final Logger log = Logger.getLogger("ProcessorController");

    Map<String, Processor> processorPool = new HashMap<String, Processor>(); // Id => Processor
    // todo: may need fix and test in future
//    FirstOccurrenceSettingsDetector _fosd;
    NssCoreContext _context;

    ProcessorController() {
        /** set 'com.opencsv:opencsv:4.6' here
         * use JAVA csv instead of dart csv converter
         * */

//        _fosd = new FirstOccurrenceSettingsDetector(
//                eols: ['\r\n', '\n'],
//        textDelimiters: ['"', "'"],
//    );
    }

    @Override
    public void setContext(NssCoreContext context) {
        _context = context;
    }

    public void process(String data) {
        if (data != null && data.length() > 0 && data.charAt(data.length() - 1) == '\n') {
            data = data.substring(0, data.length() - 1);
        }
        dispatch(data);
    }

    public void dispatch(String prepared) {
        final NssPacket nssPacket = decode(prepared);
        final String reqId = String.valueOf(nssPacket.getReqId());

        final Map<String, ProcessorInfo> _processors =
                _context.getProcessorConfig();

        if (nssPacket != null && _processors.containsKey(reqId)) {
            final ProcessorInfo processorInfo = _processors.get(reqId);
            final String parser = processorInfo.getParser();
            final String pid = processorInfo.getId();
            final String body = nssPacket.getBody();

            if (parser == "csv") {
                /** set 'com.opencsv:opencsv:4.6' here
                 * use JAVA csv instead of dart csv converter
                 * convert csv to List
                 * */
                CSVReader reader = null;
                List res = null;
                File csvfile = new File(body);
                try {
                    reader = new CSVReader(new FileReader(csvfile));
                    res = reader.readAll();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nssPacket.setParsedBody(res);
            } else if (parser == "json") {
                final int headerPos = body.indexOf('\n');
                String structs = '{' + body.substring(headerPos + 1) + '}';
                structs = structs.replaceAll("\"", "\"");

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> response = new HashMap<String, Object>();
                try {
                    response = mapper.readValue(structs, Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nssPacket.setParsedBody(response);
            } else if (parser == "spliter") {
                nssPacket.setParsedBody(body.split("\n"));
            } else {
                log.info("cannot determine parser reqId=" + reqId);
            }
            Processor processor;
            if (processorPool.containsKey(pid)) {
            } else {
                final Processor newProcessor = processorInfo.ifAbsent();
                newProcessor.setContext(_context);
                processorPool.put(pid, newProcessor);
            }
            processor = processorPool.get(pid);
            Object data = processor.process(nssPacket);
            if (data != null) {
                processor.notify(nssPacket, data);
            } else {
                log.info("unable to process data body:" + body);
            }
        }
    }

    public NssPacket decode(String data) {
        if (data != null) {
            final int headerPos = data.indexOf("\n");
            final String headerString = data.substring(0, headerPos);
            final String body = data.substring(headerPos + 1);
            final List<String> headers = Arrays.asList(headerString.split(","));

            final int length = Integer.parseInt(headers.get(0));
            final int reqId = Integer.parseInt(headers.get(1));
            int seqNo = -1;
            int size = 0;
            if (headers.size() > 2 && headers.get(2).length() > 0) {
                if (headers.get(2) != "null") {
                    seqNo = Integer.parseInt(headers.get(2));
                }
            }

            if (headers.size() > 3 && headers.get(3).length() > 0) {
                size = Integer.parseInt(headers.get(3));
            }

            return new NssPacket(length, reqId, seqNo, size, body, data);
        } else {
            return null;
        }
    }
}
