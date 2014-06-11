import java.io.File;

import com.meterware.httpunit.*;

public class InstallB2 {

  public InstallB2() {
  }

  public static void main(String[] args) {

    if (args.length >= 2) {
      String server = args[0];
      String filename = args[1];
      String domain = "BBLEARN";
      boolean clean = false;
      boolean available = false;
      String key = "";
      if (args.length >= 3) {
        domain = args[2];
      }
      if (args.length >= 4) {
        clean = args[3].toLowerCase().equals("true");
      }
      if (args.length >= 5) {
        available = args[4].toLowerCase().equals("true");
      }
      if (args.length >= 6) {
        key = args[5];
      }
      System.out.println("Installing BB to " + server + " (" + domain + ")");
      WebConversation wc = new WebConversation();
      WebRequest req = new GetMethodWebRequest(server + "/webapps/spv-install-b2-" + domain + "/upload.jsp");
      WebResponse resp = null;
      try {
        resp = wc.getResponse(req);
        WebForm form = resp.getForms()[0];
        WebRequest formSubmit = form.getRequest();
        formSubmit.selectFile("warFile", new File(filename));
        if (clean) {
          formSubmit.setParameter("clean", "true");
        }
        if (available) {
          formSubmit.setParameter("available", "true");
        }
        formSubmit.setParameter("key", key);
        WebResponse status = wc.getResponse(formSubmit);
        if (status.getResponseCode() == 200) {
          System.out.print(status.getText());
        } else {
          System.out.println("An error occurred");
        }
      } catch(Exception e) {
        System.out.println("Exception: " + e.getMessage());
      }
    } else {
      System.out.println("ERROR: Too few parameters");
      System.out.println("Expected: serverURL warFile domain clean available");
    }

  }

}
