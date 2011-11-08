using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

//http://connect.microsoft.com/VisualStudio/feedback/details/102416/partial-classes-do-not-work-in-asp-net-when-the-code-file-is-outside-of-the-app-code
public partial class BbWsTestPage : BbWsTest
{
    protected void Page_Load(object sender, EventArgs e)
    {
        base.RunTest();
    }
}
