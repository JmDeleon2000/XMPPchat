package redes;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jxmpp.jid.EntityBareJid;

public class direct_chat implements  Runnable{

    Chat dm_chat;
    public direct_chat(EntityBareJid jid, ChatManager manager)
    {
        dm_chat = manager.chatWith(jid);

    }
    @Override
    public void run() {

    }
}
