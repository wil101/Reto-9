package co.edu.udea.reto9;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.List;

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
        //leerPorLineasYEscribir(rutaArchivoEtherium, rutaArchivoNuevo);
        desviacionEstandar(rutaArchivoEtherium);
        System.out.println(promedio(rutaArchivoEtherium));
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
    
    public static double promedio (String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta);
        double promedio = 0.0;
        double sumatoria = 0.0;
        for(int j = 1; j < lineasArchivo.size(); j++){
            String close = lineasArchivo.get(j);
            
            switch (j) {
                case 221:
                    close = close.substring(46,56);
                    break;
                case 222:
                    close = close.substring(45,56);
                    break;
                default:
                    close = close.substring(47,58);
                    break;
            }
            double valor = Double.parseDouble(close);
            sumatoria += valor;
            //System.out.println(valor);
        }
        promedio = sumatoria/(lineasArchivo.size()- 1);
        return promedio;
    }
    
    public static void desviacionEstandar(String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta);
        double varianza = 0.0;
        double desviacion = 0.0;
        for(int i = 1; i < lineasArchivo.size(); i++){
            String close = lineasArchivo.get(i);
            switch (i) {
                case 221:
                    close = close.substring(46,56);
                    break;
                case 222:
                    close = close.substring(45,56);
                    break;
                default:
                    close = close.substring(47,58);
                    break;
            }
            double valor = Double.parseDouble(close);
            double rango = Math.pow(valor - promedio(ruta), 2);
            varianza = varianza + rango;
        }
        varianza = varianza / (lineasArchivo.size() - 1);
        desviacion = Math.sqrt(varianza);
        System.out.println(desviacion);
    }
    
    public static void escribirPorLineas(Path ruta, String cadena) throws IOException{
        FileChannel fileChannel = FileChannel.open(ruta, APPEND);
        ByteBuffer buffer = ByteBuffer.wrap(cadena.getBytes());
        while(buffer.hasRemaining()){
            fileChannel.write(buffer);
        }
    }
}
