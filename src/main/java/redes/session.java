package redes;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jxmpp.jid.parts.Localpart;


class session
{
    public AbstractXMPPConnection con;

    public session(String domain, String user, String pw, boolean inBandReg) throws Exception
    {
        if (inBandReg)
        {
            XMPPTCPConnectionConfiguration conConf =
                    XMPPTCPConnectionConfiguration.builder()
                            .setXmppDomain(domain)
                            .setHost(domain)
                            .setSendPresence(true)
                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                            .build();
            con = new XMPPTCPConnection(conConf);


            con.connect();

            AccountManager acctManager = AccountManager.getInstance(con);

            acctManager.sensitiveOperationOverInsecureConnection(true);
            acctManager.createAccount(Localpart.from(user), pw);

            con.login(user, pw);

        }
        else
        {
            XMPPTCPConnectionConfiguration conConf =
                    XMPPTCPConnectionConfiguration.builder()
                            .setXmppDomain(domain)
                            .setHost(domain)
                            .setSendPresence(true)
                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                            .setUsernameAndPassword(user, pw)
                            .build();
            con = new XMPPTCPConnection(conConf);

            con.connect();
            con.login();
        }

    }
}
