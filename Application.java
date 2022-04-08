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

import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String WORKER_ADDRESS_1 = "http://localhost:8080/task";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator();
        Demo obj = new Demo(2022, "Prueba serializacion y deserializacion");
        obj.imprimirObjeto();
        byte[] objserial = SerializationUtils.serialize(obj);
        
	List<byte[]> results;
	while(true){
        	results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1),objserial);
		obj = (Demo)SerializationUtils.deserialize(results.get(0));
		if(obj instanceof Demo)
			System.out.println("Si soy Demo");
		else
			System.out.println("No soy Demo");
		obj.imprimirObjeto();
		objserial = SerializationUtils.serialize(obj);
	}
    }
}
