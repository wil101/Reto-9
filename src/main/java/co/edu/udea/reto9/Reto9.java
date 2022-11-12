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
        
        String rutaArchivoEtherium = "D:\\NetBeansProjects\\Reto9\\ETH-USD.csv";
        String rutaArchivoNuevo = "D:\\NetBeansProjects\\Reto9\\nuevoArchivo.txt";
//        try{
//            crearNuevoArchivo(rutaArchivoNuevo);
//        }
//        catch(IOException e){
//                System.out.println("Error: " + e.getMessage());
//        }
        leerPorLineasYEscribir(rutaArchivoEtherium, rutaArchivoNuevo);
        //leerPorLineasSeparando(rutaArchivoEtherium);
    }
    public static void crearNuevoArchivo(String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        String cadena = "";
        byte[] bytesCadena = cadena.getBytes();
        Files.write(miRuta, bytesCadena);
    }
   
    public static void leerPorLineasYEscribir(String rutaLectura, String rutaEscritura) throws IOException {
        Path rutaRead = Paths.get(rutaLectura);
        Path rutaWrite = Paths.get(rutaEscritura);
        List<String> lineasArchivo;
        try {
            lineasArchivo = Files.readAllLines(rutaRead);
            for(int i = 1; i < lineasArchivo.size(); i++){
                String cadena = lineasArchivo.get(i);
                String cadena1 = cadena.substring(0, 10);
                String tab = "        ";
                String conceptoValor = concepto(rutaLectura, i);
                String concat = cadena1.concat(tab + conceptoValor +"\n");
                escribirPorLineas(rutaWrite, concat);
            }
        } catch (IOException e) {
            System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
        }
    }
    
    public static String concepto (String ruta, int indice)throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta);
        String open = lineasArchivo.get(indice);
        if(indice == 222){
            open = open.substring(11, 21);
        }else{
            open = open.substring(11, 22);
        }
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
    
    public static void escribirPorLineas(Path ruta, String cadena) throws IOException{
        FileChannel fileChannel = FileChannel.open(ruta, APPEND);
        ByteBuffer buffer = ByteBuffer.wrap(cadena.getBytes());
        while(buffer.hasRemaining()){
            fileChannel.write(buffer);
        }
    }
}
