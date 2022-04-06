/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.Random;
import java.io.ByteArrayOutputStream;
public class WebServer {
    private static final String TASK_ENDPOINT = "/task"; //endpint task
    private static final String STATUS_ENDPOINT = "/status"; //endpint status
    private static final String SEARCHIPN_ENDPOINT = "/searchipn"; //endpint searchipn

    private final int port; //puerto 
    private HttpServer server; //servidor http

    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]); //asignacion de puerto
        }

        WebServer webServer = new WebServer(serverPort); 
        webServer.startServer(); //iniciamos la configuracion del servidor

        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0); //creamos socket tcp vinculada a una ip y al puerto   
        } catch (IOException e) {
            e.printStackTrace(); 
            return;
        }

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT); //mapeo de identificador uniforme de recursos y 
        HttpContext taskContext = server.createContext(TASK_ENDPOINT); //un httphandler para cada endpint
        HttpContext searchipnContext = server.createContext(SEARCHIPN_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest); //vincula el handler con el contexto 
        taskContext.setHandler(this::handleTaskRequest);
        searchipnContext.setHandler(this::handleSearhIPNRequest);

        server.setExecutor(Executors.newFixedThreadPool(8)); //ocho hilos 
        server.start(); //se inicia servidor 
    }

    private void handleSearhIPNRequest(HttpExchange exchange) throws IOException{
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close(); 
            return;
        }
        Headers headers = exchange.getRequestHeaders(); //se recupera los headers
        if (headers.containsKey("X-searchipn") && headers.get("X-searchipn").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n"; 
            sendResponse(dummyResponse.getBytes(), exchange); // se envia respuesta
            return;
        }
        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }
        //se devuelve la cantidad de tiempo que tomo el procesamiento de informacion de depuracion 
        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //almacenan los datos enviados
        byte[] responseBytes = searchResponse(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime); //calcula tiempo 
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //se almacena respuesta en el header 
        }

        sendResponse(responseBytes, exchange); //se envia respuesta
    }

    private byte[] searchResponse(byte[] requestBytes) { //multiplicacion de dos big integer
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        int n = Integer.parseInt(stringNumbers[0]);
        StringBuilder cadenota = new StringBuilder();
        Random aleatorio = new Random();
        int ocurrencias = 0;

        for (int i = 0; i < n; i++) {
            if((i)%4 == 0){
                cadenota.append(' ');
                int inde = cadenota.indexOf(stringNumbers[1]);
                if(inde !=-1){
                    ocurrencias ++;
                    cadenota.setCharAt(inde,'P');
                }
            }else {
                cadenota.append((char) (aleatorio.nextInt(26)+'A'));
            }
        }
        return String.format("Se encontraron: " + ocurrencias+"\n").getBytes();
    }
    

    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close(); 
            return;
        }

        Headers headers = exchange.getRequestHeaders(); //se recupera los headers
        // if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
        //     String dummyResponse = "123\n"; 
        //     sendResponse(dummyResponse.getBytes(), exchange); // se envia respuesta
        //     return;
        // }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }
        //se devuelve la cantidad de tiempo que tomo el procesamiento de informacion de depuracion 
        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //almacenan los datos enviados
        byte[] responseBytes=null;
        showObject(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime); //calcula tiempo 
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //se almacena respuesta en el header 
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] timeBytes = String.format("La operacion tomo %d nanosegundos", finishTime - startTime).getBytes();
            outputStream.write(timeBytes);
            responseBytes = outputStream.toByteArray();
            sendResponse(responseBytes, exchange); //se envia respuesta    
        }
        
    }

    private void showObject(byte[] requestBytes){
        Demo obj = null;
        obj = (Demo)SerializationUtils.deserialize(requestBytes);
        System.out.println("Se recibio el objeto con los elementos:");
        System.out.println("a: "+obj.a);
        System.out.println("b: "+obj.b);
        //return String.format("Se recibio el objeto con los elementos: \na = " + obj.a+"\nb = " + obj.b).getBytes();
    }

    private byte[] calculateResponse(byte[] requestBytes) { //multiplicacion de dos big integer
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;

        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException { //si metodo get, respuesta -> esta vivo
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException { 
        exchange.sendResponseHeaders(200, responseBytes.length); //longitud de respuesta
        OutputStream outputStream = exchange.getResponseBody(); //se escribe 
        outputStream.write(responseBytes); //se escribe en el stream
        outputStream.flush(); //limpieza de stream 
        outputStream.close(); //cerrar stream
        exchange.close();
    }
}
