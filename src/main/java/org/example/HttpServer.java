package org.example;

import java.net.*;
import java.io.*;
import java.lang.

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine = "";
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if(inputLine.contains("/consulta?comando")){
                reflectiveChatGPT(outputLine, inputLine);
            }
            if (!in.ready()) {break; }
        }
        outputLine = salida();

        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static String reflectiveChatGPT(String outputLine, String inputLine) throws ClassNotFoundException {
        String comando = inputLine.split("=")[1];

        if(comando.startsWith("Class")){
            String datos = (String) comando.subSequence(6, -2);
            Class.forName(datos);

            return  "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Class :)</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Campos y metodos declarados</h1>\n"
                    + Class.forName(datos)
                    + "</body>\n"
                    + "</html>\n";

        }
        if(comando.startsWith("invoke")){
            String datos = (String) comando.subSequence(7, -2);

        }
        if(comando.startsWith("unaryInvoke")) {
            String datos = comando.substring(12, -2);
            String[] dato = datos.split(",");
            String clase = dato[0].split(".")[2];
            String metodo = dato[1];
            String tipoParametro = dato[2];
            String valorParametro = dato[3];
            int respuesta = 0;

            if (clase == "Math"){
                if (metodo == "abs"){
                    respuesta = Math.abs(Integer.parseInt(valorParametro));
                }

            if (clase == "Integer") {
                if (metodo == "valueOf"){
                    respuesta = Integer.valueOf(valorParametro);
                }
            }
            }
            return  "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Class :)</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Resultado de la invocación del método. paramtype = int | double | String </h1>\n"
                    + respuesta
                    + "</body>\n"
                    + "</html>\n";

        }
        
        if(comando.startsWith("binaryInvoke")){
            String datos = comando.substring(12, -2);
            String[] dato = datos.split(",");
            String clase = dato[0].split(".")[2];
            String metodo = dato[1];
            String tipoParametro = dato[2];
            String valorParametro1 = dato[3];
            String valorParametro2 = dato[5];


        }

        return salida();
    }

    public static String salida() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Parcial 1 AREP :)</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Ingresa la url adecuada para realizar la debida invocacion</h1>\n"
                + "</body>\n"
                + "</html>\n";
    }
}