package com.company.client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {


    public static void main(String[] args) throws IOException {

        System.out.println("Client: First Question");
       Socket s = new Socket("localhost",4999);

       PrintWriter pr = new PrintWriter(s.getOutputStream());
       pr.println("83,1.7");
       pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());


        BufferedReader bf = new BufferedReader(in);

        // Getting the weight component
        String bmiString = bf.readLine();
        System.out.println("Server: " + bmiString);

        // Second Question
        System.out.println("Client: Second Question");

        try
        {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s2 = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s2.getInputStream());
            DataOutputStream dos = new DataOutputStream(s2.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s2);
                    s2.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing BMI body mass as requested by client
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
