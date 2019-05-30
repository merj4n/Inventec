package net.iesochoa.germanbelda.proyect.inventec.Comunication;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.iesochoa.germanbelda.proyect.inventec.Activities.MainActivity;

public class SftpConnectionDownload  extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        boolean conStatus = false;
        Session session;
        Channel channel;
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        Log.i("Session", "is" + conStatus);
        try {
            JSch ssh = new JSch();
            session = ssh.getSession("root", "www.teammarro.com", 22);
            config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword("Merjan81**");

            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;
            //Me ubico en la ruta donde se encuentra el fichero
            sftp.cd(MainActivity.RUTA_FILE_PATH);
            // If you need to display the progress of the upload, read how to do it in the end of the article

            // Indico el fichero que voy a descargar y donde lo voy a descargar
            sftp.get(MainActivity.RUTA_FILE_DOWNLOAD,MainActivity.RUTA_FILE_DB_DOWNLOAD);


            Boolean success = true;

            if(success){
                // The file has been succesfully downloaded
            }

            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}