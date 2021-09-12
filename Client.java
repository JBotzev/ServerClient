import netscape.javascript.JSObject;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

public class Client {

    public static void main(String[] args) throws IOException {

    }

    public void run() throws IOException {

        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected to server");
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "super1337h4xx0r");
        jsonObject.put("password", "supersecurepassword");
        writer.write(jsonObject.toString() + "\n");
        writer.flush();

        String line = reader.readLine();
        jsonObject = new JSONObject(line);
        System.out.println("Recieved from Server:\n" + jsonObject.toString(2));
        socket.close();
    }
}
