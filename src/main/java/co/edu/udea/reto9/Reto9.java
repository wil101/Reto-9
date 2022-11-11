package co.edu.udea.reto9;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author emanu
 */
public class Reto9 {

    public static void main(String[] args) throws IOException {
        
        String rutaArchivoEtherium = "D:\\NetBeansProjects\\Reto9\\ETH-USD.csv";
        String rutaArchivoNuevo = "D:\\NetBeansProjects\\Reto9\\nuevoArchivo.txt";
//        try{
//            escribirPrimeraLinea(rutaArchivoNuevo);
//        }
//        catch(IOException e){
//                System.out.println("Error: " + e.getMessage());
//        }
        leerPorLineas(rutaArchivoEtherium, rutaArchivoNuevo);

    }
    
    
    public static void escribirPrimeraLinea(String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        String cadena = "fecha y concepto\n";
        
        byte[] bytesCadena = cadena.getBytes();
        Files.write(miRuta, bytesCadena);
        
    }
    
    public static void leerPorLineas(String rutaLectura, String rutaEscritura) throws IOException {
        Path rutaRead = Paths.get(rutaLectura);
        List<String> lineasArchivo;
        FileChannel fileChannel = FileChannel.open(rutaRead, READ);
        try {
            lineasArchivo = Files.readAllLines(rutaRead);
            int j = 42;
            for(int lineaActual = 1; lineaActual < 8/*lineasArchivo.size()*/; lineaActual++){
                int k = 63 * lineaActual;
                if(lineaActual == 1){
                    fileChannel.position(j);
                } else{
                    fileChannel.position(k);  
                }
                ByteBuffer buffer = ByteBuffer.allocate(10);
                do {
                    fileChannel.read(buffer);
                    String cadena = new String(buffer.array());
                    System.out.println(cadena);
                } while (buffer.hasRemaining());
                //System.out.println(lineasArchivo.get(lineaActual).getBytes().length);

            }
            
        } catch (IOException e) {
            System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
        }
    }
     
     
}
