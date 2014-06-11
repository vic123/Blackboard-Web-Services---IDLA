
package bbwscommon;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;
import bbgbws.GradableItemDetails;
import bbgbws.GradableItemParams;

public interface IBbWebservices_Gen {


    @WebMethod
    public List<GradableItemDetails> gradableItemDeleteListById(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemDeleteRecordById(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemInsertListById(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemInsertRecordById(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemLoadListByCourseId(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemLoadListByGradingPeriodId(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemLoadListByGradingPeriodIdAndBookVersion(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList,
        @WebParam(name = "gradableItemParams")
        GradableItemParams gradableItemParams)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemLoadListById(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemLoadRecordByAssessmentId(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemLoadRecordByCourseContentId(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemLoadRecordById(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemPersistListById(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemPersistRecordById(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public List<GradableItemDetails> gradableItemUpdateListById(
        @WebParam(name = "params")
        BbWsParams params, List<GradableItemDetails> inputList)
        throws BbWsFault, WebServiceException
    ;

    @WebMethod
    public GradableItemDetails gradableItemUpdateRecordById(
        @WebParam(name = "params")
        BbWsParams params, GradableItemDetails inputRecord)
        throws BbWsFault, WebServiceException
    ;

}
