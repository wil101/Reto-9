package co.edu.udea.reto9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
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
        
        String rutaArchivoEtherium = "C:\\Users\\WilmarOS\\Documents\\NetBeansProjects\\Reto-9\\ETH-USD.csv";
        String rutaArchivoNuevo = "C:\\Users\\WilmarOS\\Documents\\NetBeansProjects\\Reto-9\\nuevoArchivo.txt";
//        try{
//            crearNuevoArchivo(rutaArchivoNuevo);
//       }
//        catch(IOException e){
//                System.out.println("Error: " + e.getMessage());
//        }
        leerPorLineas2(rutaArchivoEtherium, rutaArchivoNuevo);
        //leerPorLineasSeparando(rutaArchivoEtherium);
        concepto(rutaArchivoEtherium, 10);
    }
    public static void crearNuevoArchivo(String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        String cadena = "";
        byte[] bytesCadena = cadena.getBytes();
        Files.write(miRuta, bytesCadena);
    }
    
    public static String concepto (String ruta, int indice)throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta);
        String open = lineasArchivo.get(indice);
        open = open.substring(11, 22);
        double valor = Double.parseDouble(open);   
        String resultado = "";
        if(valor >= 4600){
            resultado = "muy alto";
        }else if(valor >= 3100 && valor < 4600){
            resultado = "alto";
        }else if(valor >= 2100 && valor < 3100){
            resultado = "medio";
        }else if(valor >= 1200 && valor < 2100){
            resultado = "bajo";
        }else if(valor < 1200){
            resultado = "muy bajo";
        }
        return resultado;
    }
   
    public static void leerPorLineas2(String rutaLectura, String rutaEscritura) throws IOException {
        Path rutaRead = Paths.get(rutaLectura);
        Path rutaWrite = Paths.get(rutaEscritura);
        FileChannel fileChannelWrite = FileChannel.open(rutaWrite, WRITE);
        List<String> lineasArchivo;
        //Charset charset = Charset.forName("UTF-8");
        try {
            lineasArchivo = Files.readAllLines(rutaRead);
            for(int i = 1; i < lineasArchivo.size(); i++){
                String cadena = lineasArchivo.get(i);
                String cadena1 = cadena.substring(0, 10);
                String tab = "        ";
                String concat = cadena1.concat(tab + "\n");
                ByteBuffer buffer = ByteBuffer.wrap(concat.getBytes());
                while(buffer.hasRemaining()){
                    fileChannelWrite.write(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
        }
    }
    
    
    public static void leerPorLineas(String rutaLectura, String rutaEscritura) throws IOException {
        Path rutaRead = Paths.get(rutaLectura);
        Path rutaWrite = Paths.get(rutaEscritura);
        List<String> lineasArchivo;
        FileChannel fileChannelRead = FileChannel.open(rutaRead, READ);
        FileChannel fileChannelWrite = FileChannel.open(rutaWrite, WRITE);
        try {
            lineasArchivo = Files.readAllLines(rutaRead);
            
            int j = lineasArchivo.get(0).length();
            for(int lineaActual = 1; lineaActual < lineasArchivo.size(); lineaActual++){
                int k = lineasArchivo.get(lineaActual).length() * (lineaActual);
                if(lineaActual == 1){
                    fileChannelRead.position(j + 1);
                } else{
                    fileChannelRead.position(k - j + lineaActual);
                }
                ByteBuffer buffer = ByteBuffer.allocate(10);
                do {
                    fileChannelRead.read(buffer);
                    //escribirPorLineas(rutaWrite, buffer);
                    String cadena = new String(buffer.array());
                    String concat = cadena.concat("\n");
                    buffer = ByteBuffer.wrap(concat.getBytes());  //Wilmar va a organizar los métodos
                    while(buffer.hasRemaining()){
                        fileChannelWrite.write(buffer);
                    }
                } while (buffer.hasRemaining());
            }
            
        } catch (IOException e) {
            System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
        }
    }
    public static void escribirPorLineas(Path ruta, ByteBuffer buffer) throws IOException{
        FileChannel fileChannel = FileChannel.open(ruta, WRITE);
        String cadena = new String(buffer.array());
        String concat = cadena.concat("\n");
        buffer = ByteBuffer.wrap(concat.getBytes());
        while(buffer.hasRemaining()){
            fileChannel.write(buffer);
        }
    }
}
