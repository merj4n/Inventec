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


public class SftpConnection extends AsyncTask<Void, Void, Void> {

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
            session.setPassword("Merjan81**");
            session.setConfig(config);
            session.connect();
            conStatus = session.isConnected();
            Log.i("Session", "is" + conStatus);
            channel = session.openChannel("sftp");
            while (!conStatus) {
                conStatus = session.isConnected();
                channel.connect();
                ChannelSftp sftp = (ChannelSftp) channel;
                sftp.put(MainActivity.RUTA_FILE_DB, MainActivity.RUTA_FILE_UPLOAD);
            }
        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("Session", "is" + conStatus);
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("Session", "is" + conStatus);
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


