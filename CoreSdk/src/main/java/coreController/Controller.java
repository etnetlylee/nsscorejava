package coreController;

import api.ContextProvider;
import coreModel.NssCoreContext;

public class Controller extends ContextProvider {
    NetworkController _networkController;
    ProcessorController _processorController;
    RequestController _requestController;
    CommandController _commandController;
    SubscriberController _subscriberController;
    CultureController _cultureController;
    GroupingController _groupingController;
    // StaticDataController _staticDataController;
    // PortfolioController _portfolioController;
    // LimitAlarmController _limitAlarmController;
    HeartbeatController _heartbeatController;

    public Controller() {
        _processorController = new ProcessorController();
        _networkController = new NetworkController();
        _commandController = new CommandController();
        _subscriberController = new SubscriberController();
        _requestController = new RequestController();
        _cultureController = new CultureController();
        _groupingController = new GroupingController();
        // _staticDataController = new StaticDataController();
        // _portfolioController = new PortfolioController();
        // _limitAlarmController = new LimitAlarmController();
        _heartbeatController = new HeartbeatController();
    }

    @Override
    public void setContext(NssCoreContext context) {
        _processorController.setContext(context);

        _networkController.setContext(context);
        _commandController.setContext(context);
        _subscriberController.setContext(context);
        _requestController.setContext(context);
        _cultureController.setContext(context);
        _groupingController.setContext(context);

        // _staticDataController.setContext(context);
        // _portfolioController.setContext(context);
        // _limitAlarmController.setContext(context);

        _heartbeatController.setContext(context);
    }

    public NetworkController getNetworkController() {
        return _networkController;
    }

    public ProcessorController getProcessorController() {
        return _processorController;
    }

    public CommandController getCommandController() {
        return _commandController;
    }

    // StaticDataController getStaticDataController() {
    //   return _staticDataController;
    // }

    public SubscriberController getSubscriberController() {
        return _subscriberController;
    }

    public RequestController getRequestController() {
        return _requestController;
    }

    public CultureController getCultureController() {
        return _cultureController;
    }

    public GroupingController getGroupingController() {
        return _groupingController;
    }

    // PortfolioController getPortfolioController() {
    //   return _portfolioController;
    // }

    // LimitAlarmController getLimitAlarmController() {
    //   return _limitAlarmController;
    // }

    public HeartbeatController getHeartbeatController() {
        return _heartbeatController;
    }
}
