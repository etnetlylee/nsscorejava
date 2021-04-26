package events;

public class AsaProgressStatus {
    String _code;
    int _finishedTasks;
    int _totalDataTasks;
    int _percent;

    public AsaProgressStatus(String code, int finishedTasks,  int totalDataTasks, int percent) {
        this._code = code;
        this._finishedTasks = finishedTasks;
        this._totalDataTasks = totalDataTasks;
        this._percent = percent;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public String getCode() {
        return this._code;
    }

    public void setFinishedTasks(int finishedTasks) {
        this._finishedTasks = finishedTasks;
    }

    public int getFinishedTasks() {
        return this._finishedTasks;
    }

    public void setTotalDataTasks(int totalDataTasks) {
        this._totalDataTasks = totalDataTasks;
    }

    public int getTotalDataTasks() {
        return this._totalDataTasks;
    }

    public void setPercent(int percent) {
        this._percent = percent;
    }

    public int getPercent() {
        return this._percent;
    }

}
