/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

/**
 *
 * @author Alberto
 */
public class FtpServidor {

    private FtpServerFactory factory;
    private ListenerFactory listener;
    private FtpServer server;
    private ArrayList<User> usersList;

    public FtpServidor() {
    }

    public ArrayList<Paquete.User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<Paquete.User> usersList) {
        this.usersList = usersList;
    }

    public FtpServerFactory getFactory() {
        return factory;
    }

    public void setFactory(FtpServerFactory factory) {
        this.factory = factory;
    }

    public ListenerFactory getListener() {
        return listener;
    }

    public void setListener(ListenerFactory listener) {
        this.listener = listener;
    }

    public FtpServer getServer() {
        return server;
    }

    public void setServer(FtpServer server) {
        this.server = server;
    }

    public boolean iniciar() {
        boolean levantado = false;
        factory = new FtpServerFactory();
        listener = new ListenerFactory();
        listener.setPort(2221);
        factory.addListener("default", listener.createListener());
        //PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        //userManagerFactory.setFile(new File("/ftp/data/config.xml"));//choose any. We're telling the FTP-server where to read it's user list
        /*userManagerFactory.setPasswordEncryptor(new PasswordEncryptor() {//We store clear-text passwords in this example
            @Override
            public String encrypt(String password) {
                return password;
            }

            @Override
            public boolean matches(String passwordToCheck, String storedPassword) {
                return passwordToCheck.equals(storedPassword);
            }
        });
        for (int i = 0; i < usersList.size(); i++) {
            User current = usersList.get(i);
            BaseUser user = new BaseUser();
            user.setName(current.getUsername());
            user.setPassword(current.getPassword());
            user.setHomeDirectory("/ftp/data/" + current.getUsername());
            List<Authority> authorities = new ArrayList();
            authorities.add(new WritePermission());
            user.setAuthorities(authorities);
            UserManager um = userManagerFactory.createUserManager();
            try {
                um.save(user);
            } catch (FtpException e1) {
            }
            factory.setUserManager(um);
        }*/
        Map<String, Ftplet> m = new HashMap<String, Ftplet>();
        m.put("miaFtplet", new Ftplet() {
            @Override
            public void init(FtpletContext ftpletContext) throws FtpException {
            }

            @Override
            public void destroy() {
            }

            @Override
            public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException {
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
                return FtpletResult.DEFAULT;//...or return accordingly
            }
        });
        factory.setFtplets(m);
        server = factory.createServer();
        try {
            server.start();
        } catch (FtpException ex) {
        }
        return levantado;
    }
}
