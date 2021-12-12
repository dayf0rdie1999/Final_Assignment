package com.company.server;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.text.*;

public class server {

    public static void main(String[] args) throws IOException {

        // Answer Question 3
        Integer[] list = {3, 4, 5, 1, -3, -5, -1};
        System.out.println("The index of the found value is (if not will be -1) = "+ linearSearch(list, 2));
        System.out.println("The index of the found value is (if not will be -1) = "+ linearSearch(list, 5));

        System.out.println("Server: First Question");
        ServerSocket ss = new ServerSocket(4999);

        Socket sconnected = ss.accept();

        InputStreamReader in = new InputStreamReader(sconnected.getInputStream());


        BufferedReader bf = new BufferedReader(in);

        // Getting the weight component
        String line = bf.readLine();

        String[] arrayOfdata = line.split(",");

        // convert string to integer
        int weightInt = Integer.parseInt(arrayOfdata[0]);


        // Converting string to integer
        double heightInt = Double.parseDouble(arrayOfdata[1]);

        // Calculating the BMI
        double bmi = Math.round(weightInt/(heightInt + heightInt));

        // Converting bmi to string
        String bmiString= String.valueOf(bmi);

        PrintWriter pr = new PrintWriter(sconnected.getOutputStream());
        pr.println(bmiString);
        pr.flush();

        System.out.println("Server: Second Question");

        // server is listening on port 5056
        ServerSocket ss2 = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss2.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }


        }


    }

    public static <E extends Comparable<E>> int linearSearch(E[] list, E
            key) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == key) {
                return i;
            }
        }
        return -1;
    }

}

// ClientHandler class
class ClientHandler extends Thread
{

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        while (true)
        {
            try {

                // Ask user what he wants
                dos.writeUTF("Type: Weight,Height to calculate BMI \n"+
                        "Type Exit to terminate connection.");

                // receive the answer from client
                received = dis.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }


                // write on output stream based on the
                // answer from the client
                String[] arrayOfdata = received.split(",");

                // convert string to integer
                int weightInt = Integer.parseInt(arrayOfdata[0]);


                // Converting string to integer
                double heightInt = Double.parseDouble(arrayOfdata[1]);

                // Calculating the BMI
                double bmi = Math.round(weightInt/(heightInt + heightInt));

                // Converting bmi to string
                String bmiString= String.valueOf(bmi);

                //Write to the client
                dos.writeUTF(bmiString +"\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
