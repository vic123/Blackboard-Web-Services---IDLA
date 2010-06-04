using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Collections.Specialized;

namespace bbIDLA
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //string courseName = "DEMO101.1.JUN9.09";
            //string courseName = "TestClass_003_ID"; // Victor test
            //string courseName = "test.gc4";
            //string courseName = "test.algebra";
            //string courseName = "tech.gc5";

            //string userName = "00120310051";

            //string columnName = "U8DB1 - Buying and Selling";
            //string columnName = "PR2";
            //string columnName = "CalcCol_001";  // Victor test

            //bbGradebookIDLA bbGP = new bbGradebookIDLA();

            //bbGP.getStudentScore(userName, courseName, columnName);
            //bbGP.getAllStudentScores(courseName, columnName);

            //bbGP.getCalculatedColumns(courseName);

            //BlackBoardWebServices bbws = new BlackBoardWebServices();
            //BbCourse course = bbws.GetCourseDetails("ENG101.1.JUN9.09");
            //txtOut.Text = String.Format("Course Title: {0}\r\nCourse Code: {1}\r\nInternal BbID: {2}",
            //    course.Title, course.Code, course.BbID);
            //txtOut.Text += String.Format("\r\nDesc: {0}\r\nMod: {1}\r\nCreated: {2}\r\nAvailable: {3}\r\n",
            //    course.Description, course.ModifiedDate, course.CreationDate, course.Available);

          BbCourse bs = new BbCourse();
          var data = bs.GetAllCourseIDsNoDetailsXML();
          Response.Write(data);
        }
    }
}
