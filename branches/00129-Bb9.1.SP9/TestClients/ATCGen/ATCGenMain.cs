using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.CodeDom;
using Microsoft.CSharp;
using System.CodeDom.Compiler;
using System.IO;
using System.Web;

namespace ATCGen {
    class ATCGenMain {
        public static void Main() {
            WsgRegulation wsg_reg = new WsgRegGradableItem_Gen();
            wsg_reg.fillRegulationStructs();
            wsg_reg.dataClassSimpleName = "GradableItem";
            String dataClassSimpleNameFLLC = WsgUtil.firstLetterLCase(wsg_reg.dataClassSimpleName);


            //head
            CodeCompileUnit atc_gen_ccu = new CodeCompileUnit();
            CodeCompileUnit atc_4cp_ccu = new CodeCompileUnit();
            CodeNamespace atc_gen_cn = new CodeNamespace("IDLA.Webservices.AutoTestClient");//Create a namespace
            atc_gen_ccu.Namespaces.Add(atc_gen_cn);
            CodeNamespace atc_4cp_cn = new CodeNamespace("IDLA.Webservices.AutoTestClient");//Create a namespace
            atc_4cp_ccu.Namespaces.Add(atc_4cp_cn);
            atc_gen_cn.Imports.Add(new CodeNamespaceImport("System"));//Add references
            atc_4cp_cn.Imports.Add(new CodeNamespaceImport("System"));//Add references
            CodeTypeDeclaration test_gen_ctd = new CodeTypeDeclaration("BbWsTest");//Create class
            test_gen_ctd.IsPartial = true;
            test_gen_ctd.Attributes = MemberAttributes.Public;
            //test_ctd.BaseTypes.Add(new CodeTypeReference("System.Web.UI.Page"));
            test_gen_ctd.BaseTypes.Add(new CodeTypeReference(typeof(System.Web.UI.Page)));
            atc_gen_cn.Types.Add(test_gen_ctd);//Add the class to namespace defined above

            CodeTypeDeclaration test_4cp_ctd = new CodeTypeDeclaration("BbWsTest");//Create class
            test_4cp_ctd.IsPartial = true;
            test_4cp_ctd.Attributes = MemberAttributes.Public;
            //test_ctd.BaseTypes.Add(new CodeTypeReference("System.Web.UI.Page"));
            test_4cp_ctd.BaseTypes.Add(new CodeTypeReference(typeof(System.Web.UI.Page)));
            atc_4cp_cn.Types.Add(test_4cp_ctd);//Add the class to namespace defined above

            //BbWsTest.RunCourseTest()
            CodeMemberMethod run_test_cmm = new CodeMemberMethod();
            run_test_cmm.Attributes = MemberAttributes.Family | MemberAttributes.Final;
            run_test_cmm.ReturnType = new CodeTypeReference(typeof(void));
            //runCourseTest_cmm.Comments.Add(new CodeCommentStatement(new CodeComment("TestComment")));
            run_test_cmm.Name = "Run" + wsg_reg.dataClassSimpleName + "Test";
            CodeVariableReferenceExpression testArgs_cvre = new CodeVariableReferenceExpression("testArgs");
            run_test_cmm.Statements.Add(new CodeMethodInvokeExpression(testArgs_cvre, "ClearAllTestData"));
            CodeFieldReferenceExpression testArgs_course_cfre
                = new CodeFieldReferenceExpression(testArgs_cvre, dataClassSimpleNameFLLC);
            foreach (WsgApiRegulationStruct apir in wsg_reg.apiRegulationList) {
                String webm_name = dataClassSimpleNameFLLC + apir.getDataAccessPackNestedClassName();
                    /*
                    apir.apiOperation.ToString();
                webm_name += apir.apiResultKind + apir.apiBySuffix;*/
                CodeFieldReferenceExpression testArgs_course_wmethod_cfre
                    = new CodeFieldReferenceExpression(testArgs_course_cfre, webm_name);
                run_test_cmm.Statements.Add(new CodeMethodInvokeExpression(testArgs_course_wmethod_cfre, "execute"));
            }
            test_gen_ctd.Members.Add(run_test_cmm);

            //BbWsTest._courseTestArgs
            CodeTypeDeclaration dataTestArgs_gen_ctd = new CodeTypeDeclaration("_" + dataClassSimpleNameFLLC + "TestArgs_Gen");
            CodeTypeDeclaration dataTestArgs_4cp_ctd = new CodeTypeDeclaration("_" + dataClassSimpleNameFLLC + "TestArgs_4cp");
            //test_args_ctd.Attributes &= ~MemberAttributes.Public;
            dataTestArgs_gen_ctd.Attributes = MemberAttributes.Private;//?? can't remove public attribute
            CodeTypeReference baseTestArgs_ctr = new CodeTypeReference("TestArgs");
            baseTestArgs_ctr.TypeArguments.Add(dataClassSimpleNameFLLC + "Details");
            dataTestArgs_gen_ctd.BaseTypes.Add(baseTestArgs_ctr);
            dataTestArgs_4cp_ctd.BaseTypes.Add(new CodeTypeReference(dataTestArgs_gen_ctd.Name));

            //public _courseLoadRecordById courseMembershipLoadRecordById;
            foreach (WsgApiRegulationStruct apir in wsg_reg.apiRegulationList) {
                String field_name = dataClassSimpleNameFLLC + apir.getDataAccessPackNestedClassName();
                String class_name = "_" + field_name;
                CodeMemberField testcase_cmf = new CodeMemberField(class_name, field_name);
                testcase_cmf.Attributes = MemberAttributes.Public;
                dataTestArgs_gen_ctd.Members.Add(testcase_cmf);
            }

            //_courseTestArgs.getDataLogArray()
            CodeMemberMethod getDataLogArray_cmm = new CodeMemberMethod();
            getDataLogArray_cmm.Name = "getDataLogArray";
            getDataLogArray_cmm.Attributes = MemberAttributes.Public | MemberAttributes.Override;
            getDataLogArray_cmm.ReturnType = new CodeTypeReference("bbWsDataLogRecord[]");
            getDataLogArray_cmm.Statements.Add(new CodeMethodReturnStatement(
                new CodeFieldReferenceExpression(new CodeVariableReferenceExpression("wsResultRecord"),
                                                    "bbWsDataLog")
                                                )
            );
            dataTestArgs_gen_ctd.Members.Add(getDataLogArray_cmm);

            //public override String getBbWsBoolResult() {
            //return wsResultRecord.bbWsBoolResult;
            //}
            CodeMemberMethod getBbWsBoolResult_cmm = new CodeMemberMethod();
            getBbWsBoolResult_cmm.Name = "getBbWsBoolResult";
            getBbWsBoolResult_cmm.Attributes = MemberAttributes.Public | MemberAttributes.Override;
            getBbWsBoolResult_cmm.ReturnType = new CodeTypeReference(typeof(String));
            getBbWsBoolResult_cmm.Statements.Add(new CodeMethodReturnStatement(
                new CodeFieldReferenceExpression(new CodeVariableReferenceExpression("wsResultRecord"),
                                                    "bbWsBoolResult")
                                                )
            );
            dataTestArgs_gen_ctd.Members.Add(getBbWsBoolResult_cmm);

            //public override String getBbWsRecordResultId() {
            //return wsResultRecord.bbId;
            //}
            CodeMemberMethod getBbWsRecordResultId_cmm = new CodeMemberMethod();
            getBbWsRecordResultId_cmm.Name = "getBbWsRecordResultId";
            getBbWsRecordResultId_cmm.Attributes = MemberAttributes.Public | MemberAttributes.Override;
            getBbWsRecordResultId_cmm.ReturnType = new CodeTypeReference(typeof(String));
            getBbWsRecordResultId_cmm.Statements.Add(new CodeMethodReturnStatement(
                new CodeFieldReferenceExpression(new CodeVariableReferenceExpression("wsResultRecord"),
                                                    "bbId")
                                                )
            );
            dataTestArgs_gen_ctd.Members.Add(getBbWsRecordResultId_cmm);





            //_courseTestArgs.init()
            CodeMemberMethod init_cmm = new CodeMemberMethod();
            init_cmm.Name = "init";
            init_cmm.Attributes = MemberAttributes.Public | MemberAttributes.Override;
            init_cmm.ReturnType = new CodeTypeReference(typeof(void));
            CodeParameterDeclarationExpression testArgs_cpde
                = new CodeParameterDeclarationExpression("TestArgsStruct", "testArgs");
            init_cmm.Parameters.Add(testArgs_cpde);
            CodeBaseReferenceExpression base_cbre = new CodeBaseReferenceExpression();
            //CodeVariableReferenceExpression testArgs_cvre = new CodeVariableReferenceExpression("testArgs");
            init_cmm.Statements.Add(new CodeMethodInvokeExpression(base_cbre, "init", new[] { testArgs_cvre }));
            foreach (WsgApiRegulationStruct apir in wsg_reg.apiRegulationList) {
                String field_name = dataClassSimpleNameFLLC + apir.getDataAccessPackNestedClassName();
                String class_name = "_" + field_name;
                CodeVariableReferenceExpression field_cvre = new CodeVariableReferenceExpression(field_name);
                CodeObjectCreateExpression new_coce = new CodeObjectCreateExpression(class_name);
                init_cmm.Statements.Add(new CodeAssignStatement(field_cvre, new_coce));
                CodeFieldReferenceExpression testArgs_data_cfre
                    = new CodeFieldReferenceExpression(testArgs_cvre, dataClassSimpleNameFLLC);

                init_cmm.Statements.Add(
                    new CodeMethodInvokeExpression(field_cvre, "init", new[] { testArgs_data_cfre })
                );

            }
            dataTestArgs_gen_ctd.Members.Add(init_cmm);



            test_gen_ctd.Members.Add(dataTestArgs_gen_ctd);//Add method to the class
            test_4cp_ctd.Members.Add(dataTestArgs_4cp_ctd);//Add method to the class

            //BbWsTest._courseTestCase_RecordResult
            CodeTypeDeclaration dataTestCase_Record_ctd = new CodeTypeDeclaration("_" + dataClassSimpleNameFLLC + "TestCase" + "_RecordResult");
            CodeTypeReference  dataTestCase_Record_ctr = new CodeTypeReference("TestCase_SuccessRecord");
            dataTestCase_Record_ctr.TypeArguments.Add(dataTestArgs_gen_ctd.Name);
            dataTestCase_Record_ctr.TypeArguments.Add(dataClassSimpleNameFLLC + "Details");
            dataTestCase_Record_ctd.BaseTypes.Add(dataTestCase_Record_ctr);
            dataTestCase_Record_ctd.BaseTypes.Add("ITestAction");
            test_gen_ctd.Members.Add(dataTestCase_Record_ctd);

            //BbWsTest._courseTestCase_ListResult
            CodeTypeDeclaration dataTestCase_List_ctd = new CodeTypeDeclaration("_" + dataClassSimpleNameFLLC + "TestCase" + "_ListResult");
            CodeTypeReference  dataTestCase_List_ctr = new CodeTypeReference("TestCase_SuccessList");
            dataTestCase_List_ctr.TypeArguments.Add(dataTestArgs_gen_ctd.Name);
            dataTestCase_List_ctr.TypeArguments.Add(dataClassSimpleNameFLLC + "Details");
            dataTestCase_List_ctd.BaseTypes.Add(dataTestCase_Record_ctr);
            dataTestCase_List_ctd.BaseTypes.Add("ITestAction");
            test_gen_ctd.Members.Add(dataTestCase_List_ctd);

            //class _courseLoadRecordById : _courseMembershipTestCase_RecordResult, ITestAction {
            foreach (WsgApiRegulationStruct apir in wsg_reg.apiRegulationList) {
                String class_name = "_" + dataClassSimpleNameFLLC + apir.getDataAccessPackNestedClassName();
                CodeTypeDeclaration tescase_ctd = new CodeTypeDeclaration(class_name);
                if (apir.apiResultKind == WsgApiDataKind.ListByTemplate || apir.apiResultKind == WsgApiDataKind.ListById) 
                    tescase_ctd.BaseTypes.Add(dataTestCase_List_ctd.Name);
                    
                else tescase_ctd.BaseTypes.Add(dataTestCase_Record_ctd.Name);
                tescase_ctd.BaseTypes.Add("ITestAction");
                foreach (String mname in new[] { "preAction", "executeImp", "postAction" }) {
                    CodeMemberMethod preAction_cmm = new CodeMemberMethod();
                    preAction_cmm.Name = mname;
                    preAction_cmm.Attributes = MemberAttributes.Public | MemberAttributes.Override;
                    preAction_cmm.ReturnType = new CodeTypeReference(typeof(void));
                    tescase_ctd.Members.Add(preAction_cmm);
                }
                test_gen_ctd.Members.Add(tescase_ctd);
            }





            //CodeConstructor derivedClassConstructor = new CodeConstructor();//Create constructor
            //test_ctd.Members.Add(derivedClassConstructor);//Add constructor to class

            //CodeMemberMethod derivedMethod = new CodeMemberMethod();
            //derivedMethod.Attributes = MemberAttributes.Public | MemberAttributes.Override;
            //derivedMethod.Comments.Add(new CodeCommentStatement(new CodeComment("TestComment")));
            //derivedMethod.Name = "Method";
            //derivedMethod.ReturnType = new CodeTypeReference(typeof(void));

            //CodeSnippetStatement code = new CodeSnippetStatement("base.Method();");
            //derivedMethod.Statements.Add(code);
            //test_ctd.Members.Add(derivedMethod);//Add method to the class

            CSharpCodeProvider codeProvider = new CSharpCodeProvider();
            //ICodeGenerator codeGenerator = codeProvider.CreateGenerator();
            StringBuilder generatedCode = new StringBuilder();
            StringWriter codeWriter = new StringWriter(generatedCode);

            CodeGeneratorOptions options = new CodeGeneratorOptions();
            //options.BracingStyle = "C";
            options.BlankLinesBetweenMembers = false;
            //Keep the braces on the line following the statement or 
            //declaration that they are associated with
            codeProvider.GenerateCodeFromCompileUnit(atc_gen_ccu, codeWriter, options);
            //codeGenerator.GenerateCodeFromCompileUnit(unit, codeWriter, options);

            System.IO.File.WriteAllText(@"c:\work\Bb\ATCGen\ATCGen\generated\BbWsTest." 
                + wsg_reg.dataClassSimpleName  + "_Gen.aspx.cs", generatedCode.ToString());

            generatedCode = new StringBuilder();
            codeWriter = new StringWriter(generatedCode);
            codeProvider.GenerateCodeFromCompileUnit(atc_4cp_ccu, codeWriter, options);
            System.IO.File.WriteAllText(@"c:\work\Bb\ATCGen\ATCGen\generated\BbWsTest."
                + wsg_reg.dataClassSimpleName + "_4cp.aspx.cs", generatedCode.ToString());

            //return BuildGeneratedCode();
        }
    }
}
