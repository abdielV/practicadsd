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
package cliente.networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    private HttpClient client;//Objeto tipo httpclient (unico objeto) provisto por librerias de java

    public WebClient() {//Crear el constructor elobjeto httpclient
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)//Usa el protocolo http version 1
                .build();
    }

    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) { //Metodo recibe la direccion de la conexion y los datos a enviar
        HttpRequest request = HttpRequest.newBuilder()//Crea una solicitud
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))//Metodo post
                .uri(URI.create(url))//Dirreccion de destino 
		.header("X-Debug", "true")// *** Cabecera de depurado, obtener tiempo de transmisión" ***
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())//Llamamos al metodo sendAsync para enviar la solicitud request (de forma asincrona)
                .thenApply(respuesta -> {return respuesta.body() + "Cabeceras: " + respuesta.headers() + "\nVersión de HTML: " + respuesta.version() + "\nURI: " + respuesta.uri() + "\n";}); //  *** Recibe la respuesta del cuerpo, luego de los headers, versión html y uri. ***
    }
}
