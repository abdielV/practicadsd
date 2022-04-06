################################################################################
# definiendo variables a usar en el makefile
################################################################################

# banderas para compilar
JFLAGS = -d
FLAGS = -cf

# compiladores
JC = javac
JR = jar

# otras variables
RUTA = ./
APP = Application
APPJR = Application.jar
APPCL = Application.class
AGG = Aggregator.class
NET = networking/WebClient.class
CLASSES = 	WebServer.java \
		networking/WebClient.java \
		Application.java \
		Aggregator.java

################################################################################
# definiendo sufijos y reglas de sufijos
################################################################################

# definiendo sufijos que van a utilizarse en el makefile
.SUFFIXES: .java .class .jar

# empleando el sufijo "-d" para compilar archivo del WebClient
# NOTA: ES NECESARIO DEJAR EL SALTO DE LÍNEA O MARCA ERROR
.java.class:
	$(JC) $(JFLAGS) $(RUTA) $*.java

# empleando el sufijo "-cf" para compilar archivo Application
# NOTA: ES NECESARIO DEJAR EL SALTO DE LÍNEA O MARCA ERROR
.class.jar:
	$(JR) $(FLAGS) $(APPJR) $(APPCL) $(AGG) $(NET) Demo.class SerializationUtils.class

################################################################################
# definiendo los comandos posibles de usar por el usuario
################################################################################

# comando para compilar códigos si el usuario escribe "make"
default: $(CLASSES:.java=.class) $(APPCL:.class=.jar)

# comando para eliminar los archivos ".class", ".jar" y directorio "networking"
# NOTA: ES NECESARIO DEJAR EL SALTO DE LÍNEA O MARCA ERROR
clean:
	$(RM) *.class
	$(RM) *.jar
	
# comando para ejecutar código principal del cliente
# NOTA: ES NECESARIO DEJAR EL SALTO DE LÍNEA O MARCA ERROR
run:
	java -cp $(APPJR) $(APP)











