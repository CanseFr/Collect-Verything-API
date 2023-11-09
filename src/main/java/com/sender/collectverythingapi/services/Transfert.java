package com.sender.collectverythingapi.services;

import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Transfert {

    @Value("${server-io.password}")
    private String password;

    @Value("${server-io.username}")
    private String username ;

    @Value("${server-io.host}")
    private String host ;

    @Value("${server-io.port}")
    private int port ;

    public void transferFile(String localFilePath, String remoteFilePath) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            // Authentification sur le serveur distant
            session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            // Connexion SFTP
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            // Transfert du fichier
            sftpChannel.put(localFilePath, remoteFilePath);

            sftpChannel.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transferDirectoryRecursively(ChannelSftp sftpChannel, String localPath, String remotePath) throws SftpException {
        File localFile = new File(localPath);
        if (localFile.isDirectory()) {
            // Créer le dossier sur le serveur distant s'il n'existe pas
            try {
                sftpChannel.cd(remotePath);
            } catch (SftpException e) {
                sftpChannel.mkdir(remotePath);
            }

            // Obtenir la liste des fichiers du dossier local
            File[] files = localFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    String remoteFilePath = remotePath + "/" + file.getName();
                    if (file.isDirectory()) {
                        // Si c'est un répertoire, nous devons créer le répertoire sur le serveur distant
                        try {
                            sftpChannel.mkdir(remoteFilePath);
                        } catch (SftpException e) {
                            // Si le dossier existe déjà, nous ignorons l'exception
                        }
                        // Appel récursif pour transférer le contenu du dossier
                        transferDirectoryRecursively(sftpChannel, file.getAbsolutePath(), remoteFilePath);
                    } else {
                        // Si c'est un fichier, transférez-le directement
                        sftpChannel.put(file.getAbsolutePath(), remoteFilePath);
                    }
                }
            }
        } else {
            // S'il s'agit d'un fichier et non d'un répertoire, transférez-le.
            sftpChannel.put(localPath, remotePath);
        }
    }



    private final String REMOTEFILEPATH = "../var/www/html/";
    private final List<String> filePaths = new ArrayList<>(Arrays.asList(
            "build-front/asset-manifest.json",
            "build-front/favicon.ico",
            "build-front/index.html",
            "build-front/manifest.json",
            "build-front/robots.txt"
    ));
    public Integer transfertAllFilesAndFolder() throws Exception {
        System.out.println("Start Upload");

        filePaths.forEach(filePath -> transferFile(filePath, REMOTEFILEPATH));

        try{

        ScriptExecutor.createAllFolder(username,password,host,port);
        Thread.sleep(2000);
        transferFile("build-front/static/css/main.7c4ba1e8.css", "../var/www/html/static/css");
        Thread.sleep(2000);
        transferFile("build-front/static/js/main.cf32a032.js", "../var/www/html/static/js");
        Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            System.out.println(e);
        } finally {

            System.out.println("FINISHH");
        }


        return 1;
    }


}
