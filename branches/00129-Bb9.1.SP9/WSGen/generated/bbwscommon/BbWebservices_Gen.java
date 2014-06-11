
package bbwscommon;

import java.util.List;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;
import bbgbws.GradableItemAccessPack_GB2_Gen;
import bbgbws.GradableItemDetails;
import bbgbws.GradableItemParams;

public class BbWebservices_Gen {


    public List<GradableItemDetails> gradableItemDeleteListById(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemDeleteListById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "DeleteListById", null);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public GradableItemDetails gradableItemDeleteRecordById(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemDeleteRecordById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "DeleteRecordById", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public List<GradableItemDetails> gradableItemInsertListById(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemInsertListById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "InsertListById", null);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public GradableItemDetails gradableItemInsertRecordById(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemInsertRecordById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "InsertRecordById", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public List<GradableItemDetails> gradableItemLoadListByCourseId(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadListByCourseId()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadListByCourseId", gradableItemParams);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public List<GradableItemDetails> gradableItemLoadListByGradingPeriodId(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadListByGradingPeriodId()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadListByGradingPeriodId", gradableItemParams);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public List<GradableItemDetails> gradableItemLoadListByGradingPeriodIdAndBookVersion(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadListByGradingPeriodIdAndBookVersion()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadListByGradingPeriodIdAndBookVersion", gradableItemParams);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public List<GradableItemDetails> gradableItemLoadListById(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadListById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadListById", null);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public GradableItemDetails gradableItemLoadRecordByAssessmentId(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadRecordByAssessmentId()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadRecordByAssessmentId", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public GradableItemDetails gradableItemLoadRecordByCourseContentId(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadRecordByCourseContentId()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadRecordByCourseContentId", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public GradableItemDetails gradableItemLoadRecordById(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemLoadRecordById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "LoadRecordById", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public List<GradableItemDetails> gradableItemPersistListById(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemPersistListById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "PersistListById", null);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public GradableItemDetails gradableItemPersistRecordById(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemPersistRecordById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "PersistRecordById", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    public List<GradableItemDetails> gradableItemUpdateListById(BbWsParams params,
        @WebParam(name = "inputList")
        List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemUpdateListById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputList, GradableItemAccessPack_GB2_Gen.class.getName(), "UpdateListById", null);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

    public GradableItemDetails gradableItemUpdateRecordById(BbWsParams params,
        @WebParam(name = "inputRecord")
        GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    {
        BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "Entered gradableItemUpdateRecordById()", this);
        bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2 args = new bbgbws.GradableItemAccessPack_GB2.GradableItemArguments_GB2();
        args.initialize(GradableItemDetails.class, params, inputRecord, GradableItemAccessPack_GB2_Gen.class.getName(), "UpdateRecordById", null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

}
