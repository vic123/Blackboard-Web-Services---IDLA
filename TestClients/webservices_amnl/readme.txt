Copied from:
http://projects.oscelot.org/gf/project/webservices/wiki/?pagename=VisualStudio2005Example

    * Click File > New Web Site
    * Select an ASP.NET Web Site, set the location and language to your preference, I tend to locally store my files and use C# as my language.
    * Click OK
    * Right click the top level node of the project in the "Solution explorer" and select "Add Web Reference", copy and paste the url of the endpoint of your choice into the text box, (See: How To Find The End Points), so for example if I wanted the User Web service I would browse to the endpoints page and copy the link location marked beside "WSDL:", then paste this into the "URL:" text box in VS2005. If all goes well VS2005 should present you with a list of methods for this web service. (For this example I'm using the BBAnnouncementsWebService)
    * Type in a Web Reference name: "bbaws"
    * Click "Add Reference"
    * VS2005 will scroll for a bit and then present you with a new node in your solutions explorer under your project called "App_WebReferences", under that is a folder with your chosen Web Reference name and under that are the various files relating the chosen web service and methods.
    * For the purposes of simplicity we won't bother with using "code behind" in this example, so in your solution explorer delete Default.aspx
    * Right click the top level node of your project and select Add New Item > Web Form, accept the standard Default.aspx filename and make sure "Place code in separate file" is unticked.
    * Click Add
    * In the editor for Default.aspx copletely replace the content with the content of Example 1
    * Click "Build > Build Page" on the menu at the top of VS2005, and it should build succesfully
    * Click "Debug > Start Debugging" on the menu at the top of VS2005, and it should deploy to an IIS server and then open your default browser and browse to the page, you should see something along the lines of a whole mess of output interspersed with system announcement details. If not then try adding a system announcement in blackboard first.
