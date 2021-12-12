package com.company;
import java.net.*;
import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException{

        ServerSocket ss = new ServerSocket(4999);

        Socket sconnected = ss.accept();

        InputStreamReader in = new InputStreamReader(sconnected.getInputStream());

        BufferedReader bf = new BufferedReader(in);

        for (String line = bf.readLine(); line != null; line = bf.readLine()) {
            System.out.println(line);
        }

        bf.close();

    }
}
