/* reference: https://github.com/prakashnarkhede/UnixForTestersCourse_UnixOperationsUsingJsch */
package remoteDB;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RemoteDBwriter {
    public void execute()
    {
        System.out.println("Sending File to Remote");
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;

        String privateKeyPath = "D:\\puttyprivatekey.ppk";
        try {
            jsch.addIdentity(privateKeyPath);
            session = jsch.getSession("pathikpatel", "34.121.111.237", 22);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        }
        catch (JSchException e) {
            throw new RuntimeException("Failed to create Jsch Session object.", e);
        }
        try {
            session.connect();
            channel = session.openChannel("sftp");

            channel.connect();

            sftp = (ChannelSftp) channel;

            sftp.put("D:\\5408-Database-group-project\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\LocalSite\\TEST1\\input.txt", "/home/pathikpatel/DDBMS");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

            System.out.println("File Sent");

            if (sftp != null) {
                sftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
