Author: Fatima Iqbal
Reg. No: 01137

*****	Implementation of a Basic Web Server Java	*****

I have created a simple Web Server in Java which implements the HTTP/1.1 protocol correctly.I have tested it using Chrome but
it should work properly if used with some other browser.My server reads and writes the correct request and response messages.
It does proper error handling along with correct HTTP codes.Lastly it transfers the requested file from the server to client as well.


Java classes
There is only one java class for this project that is:
-myHTTPServer

How to run code?
For running these apps, follow the following instructions:
-Create a new java project in eclipse
-create a new class in this project and copy paste the given code in it
-Create some file in the folder of this eclipse project(this will be the file you would request the server)
-Save the changes
-Compile ad execute the code
-after that open your browser preferably chrome and type localhost:5000/filename (here filename will be the complete name of the file
 along with its extension that you created in this projects folder)

Format of sample file to be used for testing the presence and transfering of file:
-You can use any text file for transfering.
Note: The file should be stored in the eclipse folder

Whats in unit_test class?
-Basically i have created my unit tests in this class
-I'm checking whether my program is working correctly or not

Whats in the myHTTPServer.java file?
-myHTTPServer class basically listen for a client at the said port to make a request
-after a client and specifies a file a connection is established between the two
-then the server checks the HTTP type request whether its a GET,POST or HEAD standard request
-it then communicates using that header
-it also checks if the file specified is present at the server end or not
-on the basis of that it displays an error message along with the HTTP code(incase the file is not present or something else is wrong)
-if file is present it displays that file's contents on the browser
-it then transfers the file to the client
-after transfering the file the connection between the two is closed 


Following is the link of the source code on github
https://github.com/FatimaIqbal/lab4.git
