package co.edu.udea.reto9;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import java.util.Objects;
import java.util.stream.DoubleStream;


/**
 *
 * @author emanu
 */
public class Reto9 {

    public static void main(String[] args) throws IOException {
        
        String rutaArchivoEtherium = "C:\\Users\\WilmarOS\\Documents\\NetBeansProjects\\Reto-9\\ETH-USD.csv";
        String rutaArchivoNuevo = "C:\\Users\\WilmarOS\\Documents\\NetBeansProjects\\Reto-9\\nuevoArchivo.txt";
List<String> lineasArchivo;
        Path rutaRead = Paths.get(rutaArchivoEtherium);
        Path rutaWrite = Paths.get(rutaArchivoNuevo);
        lineasArchivo = Files.readAllLines(rutaRead);
        double[] lista = {53.5, 155.5, 15.5, 10, 15.9};
        String[] cadena =  {"jose","jaime","gloria","jaime","calpado"};   
        
        //raizLista(lista);
        System.out.println(raizLista(lista));
        System.out.println(listaSet(cadena));
//      try{
//            crearNuevoArchivo(rutaArchivoNuevo);
//        }
//        catch(IOException e){
//                System.out.println("Error: " + e.getMessage());
//        }
//        try{
//            for(int i = 1; i < lineasArchivo.size(); i++){
//                escribirPorLineas(rutaWrite,leerPorLineas(rutaArchivoEtherium, i));
//            }
//            desviacionEstandar(rutaArchivoEtherium);
//            System.out.println(promedio(rutaArchivoEtherium));
//            System.out.println(volumenMayor(rutaArchivoEtherium));
//            System.out.println(volumenMenor(rutaArchivoEtherium));
//        } catch(IOException e){
//            System.out.println("Error: " + e.getMessage());
//        }
        
        
    }
    
    public static void crearNuevoArchivo(String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        String cadena = "";
        byte[] bytesCadena = cadena.getBytes();
        Files.write(miRuta, bytesCadena);
    }
   
    public static String leerPorLineas(String rutaLectura, Integer indice) throws IOException {
        Path rutaRead = Paths.get(rutaLectura);
        List<String> lineasArchivo;
        String concat = "";
        try {
            lineasArchivo = Files.readAllLines(rutaRead);
            String cadena = lineasArchivo.get(indice);
            String cadena1 = cadena.substring(0, 10);
            String tab = "        ";
            String conceptoValor = concepto(rutaLectura, indice);
            concat = cadena1.concat(tab + conceptoValor + "\n");
        } catch (IOException e) {
            System.out.println("Hubo un error al acceder el archivo: " + e.getMessage());
        }
        return concat;
    }
    
    public static String concepto (String ruta, int indice)throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta);
        String open = lineasArchivo.get(indice);
        int inicioConcept = 11;
        int finalConcept = 22;
        if(indice == 222){
            open = open.substring(inicioConcept, finalConcept - 1);
        }else{
            open = open.substring(inicioConcept, finalConcept);
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
        int inicioCierre = 47;
        int finalCierre = 58;
        double promedio = 0.0;
        double sumatoria = 0.0;
        for(int j = 1; j < lineasArchivo.size(); j++){
            String close = lineasArchivo.get(j);
            
            switch (j) {
                case 221:
                    close = close.substring(inicioCierre - 1,finalCierre - 2);
                    break;
                case 222:
                    close = close.substring(inicioCierre - 2,finalCierre - 2);
                    break;
                default:
                    close = close.substring(inicioCierre,finalCierre);
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
        int inicioCierre = 47;
        int finalCierre = 58;
        double varianza = 0.0;
        double desviacion = 0.0;
        for(int i = 1; i < lineasArchivo.size(); i++){
            String close = lineasArchivo.get(i);
            switch (i) {
                case 221:
                    close = close.substring(inicioCierre - 1,finalCierre - 2);
                    break;
                case 222:
                    close = close.substring(inicioCierre - 2,finalCierre - 2);
                    break;
                default:
                    close = close.substring(inicioCierre,finalCierre);
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
    
    public static long volumenMayor (String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        int inicioVolumen = 71;
        long mayor = 0;
        lineasArchivo = Files.readAllLines(miRuta);
        for(int i = 1; i < lineasArchivo.size();i++){ 
            String volumen = lineasArchivo.get(i);
            switch (i) {
                case 221:
                    volumen = volumen.substring(inicioVolumen - 3,volumen.length());
                    break;
                case 222:
                    volumen = volumen.substring(inicioVolumen - 2, volumen.length());
                    break;
                default:
                    volumen = volumen.substring(inicioVolumen, volumen.length());
                    break;
            }
            long comparadorMayor = Long.parseLong(volumen);
            if(comparadorMayor >= mayor){
                mayor = comparadorMayor;
            }
        }
        return mayor; 
    }
    
    public static long volumenMenor (String ruta) throws IOException{
        Path miRuta = Paths.get(ruta);
        List<String> lineasArchivo;
        lineasArchivo = Files.readAllLines(miRuta); 
        int inicioVolumen = 71;
        BigInteger meno = new BigInteger("45743399154");
        long menor = meno.longValue();
        for(int i = 1; i < lineasArchivo.size();i++){
            String volumen = lineasArchivo.get(i);
            switch (i) {
                case 221:
                    volumen = volumen.substring(inicioVolumen - 3,volumen.length());
                    break;
                case 222:
                    volumen = volumen.substring(inicioVolumen - 2, volumen.length());
                    break;
                default:
                    volumen = volumen.substring(inicioVolumen, volumen.length());
                    break;
            }
            long comparadorMenor = Long.parseLong(volumen);
            if(comparadorMenor < menor){
                menor = comparadorMenor;
            }
        }
        return menor;
    }
    
    public static List<Double> raizLista(double[] lista){
        DoubleStream streamDoubles = Arrays.stream(lista);
        List<Double> raiz = new ArrayList<>();
        streamDoubles
                .map(x -> Math.sqrt(x))
                .forEach(x -> raiz.add(x));
        return raiz;
    }
    
    public static HashSet listaSet(String[] cadena){
        Stream<String> streamcaracteres = Arrays.stream(cadena);
        HashSet caracteres = new HashSet();
//        List<String> caracteres = new ArrayList<>();
        streamcaracteres
                .map(x -> x.length())
                .filter(x -> x > 4)
                .forEach(x -> caracteres.add(x));
        return caracteres;  
    }
}


