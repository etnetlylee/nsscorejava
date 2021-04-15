package coreModel;

public class ProcessorInfo {
    String _id;
    String _parser;
    Processor _ifAbsent;

    public ProcessorInfo(String parser, String id, Processor ifAbsent) {
        this._parser = parser;
        this._ifAbsent = ifAbsent;
        this._id = id;
    }

    public String getId() {
        return this._id;
    }

    public String getParser() {
        return this._parser;
    }

    public Processor ifAbsent() {
        return this._ifAbsent;
    }
}
