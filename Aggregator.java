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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import networking.WebClient;

public class Aggregator {
    private WebClient webClient; //Objeto webclient (Solo lo tenemos como dato)

    public Aggregator() { 
        this.webClient = new WebClient();//Instancia de nuestro webclient
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, byte[] task) {//Metodo (unico) "sendTasksToWorkers", sirve para que se reciba la lista de los trabajadores y tareas
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()];//Empleamos la clase CompletableFuture. para el manejo de la comunicacion asincrona
	//Permitiendo continuar con la ejecucion de codigo bloqueante. En el arreglo se guardaran las respuestas futuras de los dos servidores
        for (int i = 0; i < workersAddresses.size(); i++) {//iteramos sobre todos los elementos
            String workerAddress = workersAddresses.get(i);//Obtencion de las direcciones de los trabajadores
            
            //String task = tasks.get(i);//Tambien de las tareas

            byte[] requestPayload = task;//Almacenamos las tareas
            futures[i] = webClient.sendTask(workerAddress, requestPayload);//Enviamos las tareas asincronas (usanod sendtask)
        }

        List<String> results = Stream.of(futures).map(CompletableFuture::join).collect(Collectors.toList());//Declaramos lista de resultados

        return results;
    }
}
