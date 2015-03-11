package lab4;


import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPServer  extends Thread{
    
	Socket client_connected = null;
    BufferedReader inputfromclient = null;
    DataOutputStream outputtoclient = null;
    
    static final String HTML_START =
    "<html>" +
    "<title>HTTP Server in java</title>" +
    "<body>";
    
    static final String HTML_END =
    "</body>" +
    "</html>";
    
    
    public myHTTPServer(Socket client) {
        client_connected = client;
    }
    
    public void run() {
        
        try {
            
            System.out.println( "The Client "+
            client_connected.getInetAddress() + ":" + client_connected.getPort() + " is connected");
            
            inputfromclient = new BufferedReader(new InputStreamReader (client_connected.getInputStream()));
            outputtoclient = new DataOutputStream(client_connected.getOutputStream());
            
            String string_requested = inputfromclient.readLine();
            String line_header = string_requested;
            System.out.println("this is the request "+line_header);
            StringTokenizer tokenizer = new StringTokenizer(line_header);
            String method_http = tokenizer.nextToken();
                        System.out.println("this is the method "+method_http);
            String httpQueryString = tokenizer.nextToken();
            System.out.println("this is the query string "+httpQueryString);
            StringBuffer responseBuffer = new StringBuffer();
            responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
            responseBuffer.append("The HTTP Client request is ....<BR>");
            
            System.out.println("The HTTP request string is ....");
            while (inputfromclient.ready())
            {
                // Read the HTTP complete HTTP Query
                responseBuffer.append(string_requested + "<BR>");
                System.out.println(string_requested);
                string_requested = inputfromclient.readLine();
            }
            
            if (method_http.equals("GET")) {
                          
                if (httpQueryString.equals("/")) {
                    // The default home page
                    sendResponse(200, responseBuffer.toString(), false);
                } else {
                    //This is interpreted as a file name
                    String fileName = httpQueryString.replaceFirst("/", "");
                    fileName = URLDecoder.decode(fileName);
                                        System.out.println("file name is \t"+fileName);
                                        System.out.println(new File(fileName).isFile());
                    if (new File(fileName).isFile()){
                                            String msg=new String();
                                            msg.concat("200 ok!\n");
                                            System.out.println("yes its a file \t");
                                            
                        sendResponse(200, fileName, true);
                    
                                        }
                    else {
                                                //outputtoclient.writeChars(".");
                        sendResponse(404, "<b> Status 404,Sorry ,the page you requested could not be found!...." +
                        "</b>", false);
                    }
                }
            }
            else sendResponse(404, "<b>The Requested resource not found ...." +
                "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception {
        
        String statusLine = null;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine = null;
        String fileName = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        FileInputStream fin = null;
        
        if (statusCode == 200)
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        else
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
        
        if (isFile) {
            fileName = responseString;
            fin = new FileInputStream(fileName);
            contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
            if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
                contentTypeLine = "Content-Type: \r\n";
        }
        else {
            responseString = myHTTPServer.HTML_START + responseString + myHTTPServer.HTML_END;
            contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
        }
        
        outputtoclient.writeBytes(statusLine);
        outputtoclient.writeBytes(serverdetails);
        outputtoclient.writeBytes(contentTypeLine);
        outputtoclient.writeBytes(contentLengthLine);
        outputtoclient.writeBytes("Connection: close\r\n");
        outputtoclient.writeBytes("\r\n");
        
        if (isFile){
               
                    sendFile(fin, outputtoclient);
                }
        else 
                    outputtoclient.writeBytes(responseString);
            
        outputtoclient.close();
    }
    
    public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
        byte[] buffer = new byte[1024] ;
        int bytesRead;
        
        while ((bytesRead = fin.read(buffer)) != -1 ) {
                    
            out.write(buffer, 0, bytesRead);
        }
        fin.close();
    }
    
    public static void main (String args[]) throws Exception {
        
        ServerSocket Server = new ServerSocket (5000, 10, InetAddress.getByName("127.0.0.1"));
        System.out.println ("TCPServer Waiting for client on port 5000");
            
        while(true) {
            Socket connected = Server.accept();
            (new myHTTPServer(connected)).start();
        }
    }
}
