/* reference: https://github.com/prakashnarkhede/UnixForTestersCourse_UnixOperationsUsingJsch */
package remoteDB;

import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RemoteDbExecute {
    public void execute()
    {
        JSch jsch = new JSch();
        Session session = null;

        String privateKeyPath = "D:\\puttyprivatekey.ppk";
        try {
            jsch.addIdentity(privateKeyPath);
            session = jsch.getSession("pathikpatel", "34.121.111.237", 22);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        } catch (
                JSchException e) {
            throw new RuntimeException("Failed to create Jsch Session object.", e);
        }
        String command = "./script.sh";
        try {
            session.connect();
            Channel channel = session.openChannel("exec");
            List<String> result = new ArrayList<String>();
            ((ChannelExec) channel).setCommand(command);
            ((ChannelExec) channel).setPty(false);
            ((ChannelExec) channel).setErrStream(System.err);

            channel.connect();

            waitForChannelClosure(channel, 45555);
            int exitStatus = channel.getExitStatus();
            System.out.println("Exit status code: "+exitStatus);

            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (JSchException e) {
            throw new RuntimeException("Error durring SSH command execution. Command: " + command);
        }
    }

    private static void waitForChannelClosure(Channel channel, long maxwaitMs) {

        final long until = System.currentTimeMillis() + maxwaitMs;

        try {
            while (!channel.isClosed() && System.currentTimeMillis() < until) {
                Thread.sleep(250);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }

        if (!channel.isClosed()) {
            throw new RuntimeException("Channel not closed in timely manner!");
        }
    };
}
