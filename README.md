# Coach-Analyst
# Gestor de alineaciones para futbol-sala
# La interfaz se ha hecho con JavaFX, la parte del backend con SpringBoot y se concecta a una base de datos MySQL
# (Incluye el backup de la base de datos SQL, y un fichero .yml para levantar con docker-compose)

COMO HACERLO FUNCIONAR

1-Descargar la carpeta 'App JAR'

2-Tenemos que montar un servicio mysql con docker-compose.(o crear una base de datos con el backup.sql)
  Hay que tener docker-desktop funcionando
  Ejecutamos el fichero docker-compose de la carpeta App JAR
  El comando: "docker-compose up" desde CMD en el directorio donde se encuentran los archivos
  Esto creará un servicio MySQL en el puerto 3306 y cargará una copia del backup SQL (una base con datos de muestra)
  
3-Después, si tenemos java y javafx en nuestro equipo, debería de funcionar la app Coach.jar con un doble clic
  Si nos da alguna excepción al iniciar, podemos lanzarlo con el comando "java -jar Coach.jar"
  nota: springboot utiliza por defecto el puerto 8080 para tomcat,conviene desocupar ese puerto si queremos lanzar la app
