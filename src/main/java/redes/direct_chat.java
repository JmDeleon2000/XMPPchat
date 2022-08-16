package redes;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.util.ArrayList;
import java.util.Scanner;

public class direct_chat implements  Runnable{

    Chat dm_chat;
    String chattingWith;
    EntityBareJid myJID;
    chatListener listener;
    static  direct_chat active;
    static ArrayList<EntityBareJid> openJIDs;
    static ArrayList<direct_chat> openChats;

    private direct_chat(EntityBareJid jid, ChatManager manager)
    {
        dm_chat = manager.chatWith(jid);
        myJID = jid;
        chattingWith = jid.getLocalpart().asUnescapedString();
        active = this;
        listener = new chatListener(this);
        manager.addIncomingListener(listener);
    }

    public static direct_chat getChat(EntityBareJid jid, ChatManager manager)
    {
        if (openJIDs.contains(jid))
            for (direct_chat chat : openChats)
                if (chat.myJID == jid)
                    return chat;
        return  new direct_chat(jid, manager);
    }
    @Override
    public void run()
    {

        Scanner scan = new Scanner(System.in);
        print("Type -q to quit current chat, -h for help. Any other input will be sent as messages.");
        print("--------------------------------" + chattingWith + "--------------------------------");
        String userInput;
        boolean running = true;
        while (running)
        {
            System.out.print(listener.msgQueue);

            userInput = scan.nextLine();

            switch (userInput)
            {
                case "-h":
                    print("Type -q to quit current chat, -h for help. Any other input will be sent as messages.");
                    break;
                case "-q":
                    running = false;
                    break;
                default:
                    try {
                        dm_chat.send(userInput);
                    }catch (SmackException.NotConnectedException e) {print("Unexpectedly disconnected");}
                    catch (InterruptedException ignored){}
                    break;
            }

        }
        active = null;
    }

    //Enserio me cae mal lo verboso que es Java
    void print(Object a)
    {
        System.out.println(a);
    }
}

class chatListener implements IncomingChatMessageListener {

    direct_chat myChat;
    public String msgQueue = "";

    public  chatListener(direct_chat chat)
    {
        myChat = chat;
    }

    @Override
    public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
        if (myChat == direct_chat.active)
            System.out.println("\n" + myChat.chattingWith + ": " + message.getBody());
        else
        {
            msgQueue += message.getBody() + "\n";
        }
    }
}
