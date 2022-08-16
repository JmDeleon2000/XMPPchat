package redes;


import org.jivesoftware.smack.SmackException;

import org.jivesoftware.smack.roster.Roster;
import  org.jivesoftware.smack.roster.RosterEntry;


import java.util.Collection;
import java.util.Scanner;

public class ui {
    Roster roster;
    ui() throws SmackException.NotConnectedException, SmackException.NotLoggedInException
    {
        roster = Roster.getInstanceFor(chat.sech.con);

        if (!roster.isLoaded())
        {
            print("Loading roster...");
            try {
                roster.reloadAndWait();
            }
            catch (InterruptedException e){}
        }
        Collection<RosterEntry> entries = roster.getEntries();
        if(entries.size() == 0)
            print("Looks like your roster is empty!");
        else {
            for (RosterEntry entry : entries) {
                System.out.println(entry);
            }
        }

        Scanner scan = new Scanner(System.in);
        boolean running = true;
        String userInput;
        while (running)
        {
            userInput = scan.nextLine();
            switch (userInput)
            {
                case "-h":
                    print("-h:\tDisplay this message");
                    print("-r:\tShow your roster");
                    print("-c: [username]\tChat with a user");
                    print("-cg: new\tCreate a group chat");
                    print("-cg [Group chat name]\tJoin a group chat");
                    print("-dc:\tDisconnect (log out)");
                    print("-rmacc:\tDeletes current account from the server");
                    print("-st:\tChange status");
                    break;
                default:
                    print("Usage error. Use -h for reference.");
                    break;
            }


        }
        if(chat.sech.con != null)
            chat.sech.con.disconnect();
    }



    void print(Object a)
    {
        System.out.println(a);
    }
}


