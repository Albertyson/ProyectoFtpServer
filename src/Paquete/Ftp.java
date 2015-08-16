/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete;

/**
 *
 * @author emerson
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
public class Ftp {
    /**
     *Atributos de Clase
    **/
private FTPClient ftp;
    /**
* Método que establece la conexión con el ftp
* @return boolean
* @exception IOException
*/
public boolean conectar(){
        boolean conect=true;
        ftp = new FTPClient();
     try {
        // Connect to an FTP server on port 21
        ftp.connect("host",21);
        ftp.login("user", "password");
    } catch (IOException e) {
        // TODO Auto-generated catch block
        System.out.println("IOException ="+e.getMessage());
        conect=false;
    }
    return conect; 
}     
/*---------------------------------------------------------------------------*/
/**
* Método que cambia de directorio ,siendo este en el que queremos trabajar
* @param String directorio
* @return boolean
* @exception IOException
*/
public boolean cd(String directorio){
      try {
         ftp.changeWorkingDirectory(directorio);
         return true;
      } catch (IOException e) {
         System.out.println("IOException ="+e.getMessage());
      }
      return false;
}
/*---------------------------------------------------------------------------*/
/**
* Método que crea un fichero en el ftp
* @param String rutaFicheroFtp (ruta remota)
* @param String rutaFichero (ruta local donde esta el fichero)
* @return boolean
* @exception IOException
*/
public boolean crearFichero(String rutaFicheroFtp,String rutaFichero){
      try {
         ftp.storeFile(rutaFicheroFtp,new FileInputStream(new File(rutaFichero)));
         return true;
      } catch (IOException e) {
         System.out.println("IOException ="+e.getMessage());
      }
      return false;
}
/*---------------------------------------------------------------------------*/
/**
* Método que elimina un fichero del FTP
* @param String rutaFichero = ruta del fichero en el FTP
* @return boolean
* @exception IOException
*/
public boolean eliminarFichero(String rutaFichero){
      try {
         ftp.deleteFile(rutaFichero);
         return true;
      } catch (IOException e) {
         System.out.println("IOException ="+e.getMessage());
      }
      return false;
}
/*---------------------------------------------------------------------------*/
/**
* Método que cierra la conexion con el FTP
* @return boolean
* @exception IOException
*/
public boolean desconectar(){
      try {
        ftp.disconnect();
        ftp = null;
        return true;
      } catch (IOException e) {
         System.out.println("IOException ="+e.getMessage());
         return false;
      }
}
/*---------------------------------------------------------------------------*/
/**
* Método que obtiene el directorio actual, comando pwd
* @return String
* @exception IOException
*/
public String directorioActual(){
      try {
        return ftp.printWorkingDirectory();
      } catch (IOException e) {
        System.out.println("IOException ="+e.getMessage());
        return null;
      }

}
/*---------------------------------------------------------------------------*/
/**
* Método que obtiene un fichero del FTP y lo crea en un otro directorio local
* @param String rutaFichero = ruta del fichero en el FTP
* @param String rutaLocal = ruta en donde se creara el fichero
* @return boolean
* @exception IOException
*/
public boolean getFichero(String rutaFichero, String rutaLocal){
      try {
        InputStream in = ftp.retrieveFileStream(rutaFichero);
        byte[] b = new byte[in.available()];
        in.read(b);
        FileOutputStream file = new FileOutputStream(new File(rutaLocal));
        file.write(b);
        file.close();
        in.close();
        return true;
      } catch (IOException e) {
         System.out.println("IOException ="+e.getMessage());
         return false;
      }

}
/*---------------------------------------------------------------------------*/
}
