/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;

import com.sun.codemodel.ClassType;
import wsgen.regoverride.WsgRegGradableItem_Ovr;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

import java.io.File;

/**
 * <h1>Project overview:</h1>
 * <ul>
 * <li> scans through Bb jars; </li>
 * <li> helps with analysis of collected methods accessing Bb data entities
 * and their properties; </li>
 * <li> allows tuning of automated analysis (as hard-coded settings); </li>
 * <li> generates all necessary Ws code except bodies of Ws procedures that
 * perform actual calls to Bb API; </li>
 * <li> provides code-unit structure/hierarchy allowing comfortable mixing of
 * generated and human written code; </li>
 * <li> converts collected through Bb code analysis data (it is in the
 * form of calls filling in-memory structures) to C#. </li>
 * </ul>
 *
 * <h1>Global flow of processing:</h1>
 * <ol>
 * <li>
 * Developer creates initial wsgen.regoverride.WsgReg{BbDataEntity}_Ovr classs
 * (i.e. like WsgRegGradableItem_Ovr)
 * </li>
 * <li> Running program generates several classes in \generated subdirectory,
 * including the one named wsgen.reggenerated.WsgRegGradableItem_Ovr (WsgReg{BbDataEntity}_Gen) class.
 * Project's build.xml file contain post-build steps that run project and copy files from
 * \generated subdirectory into src folders of WSGen, BbWebservices and ATCGen projects.
 * <li> Developer reviews automated analysis of API stored in this file.
 * And copies/corrects parts of it into WsgRegGradableItem_Ovr.
 * <li>
 * This steps are repeated until basic initial satisfaction.
 * </li>
  * <li> Developer checks for errors with compilation of files generated for
 * BbWebservices project and repeat steps above in case of errors. </li>
 *
 * <h1>Generated code:</h1>
 * All generated classes are supplied with suffix "_Gen".
 * <br> Classes in the package wsgen.reggenerated.* are result of automated
 * analysis of Bb API available for accessing of particular data entity.
 *  <br> wsgen.reggenerated.WsgRegGradableItem_Gen.java - is for GradableItem.
 * The one for the User would be called WsgRegUser_Gen.java.
 * <br> The code generated in these files is never called directly, but used for
 * partial copy/pasting and "tuning" in wsgen.regoverride.* package.
 * <br> WsgRegGradableItem_Ovr.java is an example of it.
 * <br> When WsgRegGradableItem_Gen.java is generated, data filled
 * in WsgRegGradableItem_Ovr.java takes precedence and is only complemented but
 * not overwritten.
 * <br> Classes generated in bbwscommon.* and bbgbws.* (bbuws.*, etc.) are
 * for the use in BbWebservices project.
 * <br> _Gen suffixes indicate files for direct use as ancestors for final
 * implementations, _4CopyPaste_Gen - for copy/paste into those final implementations
 * so that regenerated code would not occasionally overwrite manual coding.
 * <p>
 * Generated files:
 * <ul>
 * <li>
 * bbgbws\GradableItemAccessPack_GB2.java - this file should not be copied to
 * BbWebservices src, it is created in order CodeModel to be able to inherit
 * from its inner classes in GradableItemAccessPack_GB2_Gen class, i.e. <br>
 * {@code     public static class DeleteListById} extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack}
 * <br> GradableItemAccessPack_GB2 is planned name of the class created and edited manually
 * inside BbWebservices project. It will include finalizing human-written code that
 * is not reasonable/effective to be generated.
 * </li>
 * <li>
 * bbgbws\GradableItemAccessPack_GB2_4CopyPaste_Gen.java - this file is mock-up
 * for direct copy/pasting of its contents into GradableItemAccessPack_GB2.
 * </li>
 * <li>
 * bbgbws\GradableItemAccessPack_GB2_Gen.java - this file automatically
 * generated base of GradableItemAccessPack code.
 * </li>
 * <li>
 * bbgbws\GradableItemDetails_Gen.java - data entity bean
 * </li>
 * <li>
 * bbwscommon\IBbWebservices_Gen.java - an interface used as a base for
 * IBbWebservices that provide web services end point interface. IBbWebservices
 * is complemented with non-generic methods missing in IBbWebservices_Gen
 * </li>
 * <li>
 * BbWebservices_Gen.java - base class for BbWebservices containing generated
 * implementations of endpoint methods.
 * </li>
 * </ul>
 * </ol>
 * </p>
 * <h1> Key project classes:</h1>
 * <ul>
 * <li> WsgMain.main() - loops over defined WsgReg{BbDataEntity}_Ovr classes,
 * generates code and finally outputs results into ./generated subdirectory.
 * </li>
 * <li> Class WsgDataRegulationGenerator analyses data oriented Bb code.
 * It generates initialization of data structures that will control following on
 * generation of code related
 * to data entity (GradableItemDetails_Gen and part of GradableItemAccessPack_GB2).
 * </li>
 * <li> Class WsgApiRegulationGenerator analyses Bb code accessing (operating over)
 * data entity. And generates initialization of data structures that
 * will be used for actual generation of WS code.
 * </li>
 * <li> Class WsgApiWebservicesGenerator uses data provided by
 * WsgApiRegulationGenerator and performs actual generation of IBbWebservices_Gen,
 * BbWebservices_Gen GradableItemAccessPack_GB2_4CopyPaste_Gen and
 * part of GradableItemAccessPack_GB2_Gen.
 * </li>
 * </ul>
 *  <h1> Project data structures:</h1>
 * <li> WsgApiRegulationStruct - attempts to formalize characteristics
 * of data accessing (manipulation) methods.
 * </li>
 * <li> WsgBbToucherRegulationStruct is ancestor of WsgBbGetterRegulationStruct
 * and WsgBbSetterRegulationStruct - these structures formalize methods available
 * for setting and getting values of data entity fields.
 * </li>
 * <li> WsgDataRegulationStruct - it is derivative from WsgBbGetterRegulationStruct
 * and WsgBbSetterRegulationStruct focused on simplification of WS code generation.
 * </li>
 * </ul>
 * @author vic123
 */


public class WsgMain {

    WsgRegulation genRegulation;

    public static void main(String[] args) {
        try {
            WsgMain wsGen = new WsgMain();
            JCodeModel code_model = new JCodeModel();
            JDefinedClass webservices_jdc = code_model._class("bbwscommon.BbWebservices_Gen");
            JDefinedClass iwebservices_jdc = code_model._class("bbwscommon.IBbWebservices_Gen", ClassType.INTERFACE);
            WsgRegulation genRegulationsArray[] = {new WsgRegGradableItem_Ovr(code_model, webservices_jdc, iwebservices_jdc)};


            for (WsgRegulation reg: genRegulationsArray) {
                wsGen.genRegulation = reg;
                wsGen.genRegulation.regulationJDC = code_model._class(wsGen.genRegulation.getRegulationClassName());
                wsGen.genRegulation.regulationJDC._extends(WsgRegulation.class);
                wsGen.generate();
            }
            wsGen.genRegulation.codeModel.build(new File("./generated"));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //System.out.println(e.getStackTrace());
            System.out.println(WsgUtil.constructExceptionMessage(e));
            e.printStackTrace(System.out);
        }
    }
    void generate() throws Exception {
        WsgDataRegulationGenerator drg = new WsgDataRegulationGenerator(genRegulation);
        drg.generate();
        WsgApiRegulationGenerator apirg = new WsgApiRegulationGenerator(genRegulation);
        apirg.initApiRegulationLists();
        apirg.generateApiRegulation();
        WsgApiWebservicesGenerator apiwsg = new WsgApiWebservicesGenerator(genRegulation);
        apiwsg.generate();
    }

    


}
