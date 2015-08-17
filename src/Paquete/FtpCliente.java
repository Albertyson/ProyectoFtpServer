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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.*;
import org.apache.ftpserver.listener.ListenerFactory;

public class FtpCliente {

    /**
     * Atributos de Clase
    *
     */
    private FTPClient client;
    private User user;
    private FtpServidor server;

    public FtpCliente() {
    }

    public FtpCliente(User user, FtpServidor server) {
        client = new FTPClient();
        this.server=server;
        this.user = user;
    }

    /**
     * Método que establece la conexión con el ftp
     *
     * @return boolean
     * @exception IOException
     */
    public boolean conectar() {
        boolean connected = false;
        try {
            // Connect to an FTP server on port 21
            /*client.connect("ftp.proyectoftp.comoj.com", 21);
            if (client.login("4lbertyson@gmail.com", "Hola123!")) {
                connected=true;
            }*/
            
            System.out.println("d"+server.getListener().getServerAddress());
            System.out.println("s "+server.getListener().getPort());
            client.connect(server.getListener().getServerAddress(), server.getListener().getPort());
            if (client.login(user.getUsername(), user.getPassword())) {
                connected=true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("IOException =" + e.getMessage());
        }
        return connected;
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que cambia de directorio ,siendo este en el que queremos trabajar
     *
     * @param String directorio
     * @return boolean
     * @exception IOException
     */
    public boolean cd(String directorio) {
        try {
            if (client.changeWorkingDirectory(directorio)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
        }
        return false;
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que crea un fichero en el ftp
     *
     * @param String rutaFicheroFtp (ruta remota)
     * @param String rutaFichero (ruta local donde esta el fichero)
     * @return boolean
     * @exception IOException
     */
    public boolean crearFichero(String rutaFicheroFtp, String rutaFichero) {
        try {
            client.storeFile(rutaFicheroFtp, new FileInputStream(new File(rutaFichero)));
            return true;
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
        }
        return false;
    }

    public boolean crearDirectorio(String ruta) {
        try {
            if (client.makeDirectory(ruta)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            System.out.println("IOException =" + ex.getMessage());
        }
        return false;
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que elimina un fichero del FTP
     *
     * @param String rutaFichero = ruta del fichero en el FTP
     * @return boolean
     * @exception IOException
     */
    public boolean eliminarFichero(String rutaFichero) {
        try {
            client.deleteFile(rutaFichero);
            return true;
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
        }
        return false;
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que cierra la conexion con el FTP
     *
     * @return boolean
     * @exception IOException
     */
    public boolean desconectar() {
        try {
            client.disconnect();
            client = null;
            return true;
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
            return false;
        }
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que obtiene el directorio actual, comando pwd
     *
     * @return String
     * @exception IOException
     */
    public String directorioActual() {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
            return null;
        }
    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método que obtiene un fichero del FTP y lo crea en un otro directorio
     * local
     *
     * @param String rutaFichero = ruta del fichero en el FTP
     * @param String rutaLocal = ruta en donde se creara el fichero
     * @return boolean
     * @exception IOException
     */
    public boolean getFichero(String rutaFichero, String rutaLocal) {
        try {
            InputStream in = client.retrieveFileStream(rutaFichero);
            byte[] b = new byte[in.available()];
            in.read(b);
            FileOutputStream file = new FileOutputStream(new File(rutaLocal));
            file.write(b);
            file.close();
            in.close();
            return true;
        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
            return false;
        }

    }
    /*---------------------------------------------------------------------------*/

    /**
     * Método lista los nombres de los archivos y directorios en el directorio
     * actual, comando ls
     *
     * @return String
     * @exception IOException
     */
    public String ls() {
        String Lista;
        try {
            Lista = client.listDirectories().toString() + client.listFiles().toString();
            return Lista;

        } catch (IOException e) {
            System.out.println("IOException =" + e.getMessage());
            return null;
        }

    }
}
