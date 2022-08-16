package redes;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.roster.Roster;
import  org.jivesoftware.smack.roster.RosterEntry;

import java.util.Collection;

public class ui {
    Roster roster;
    ui()
    {
        roster = Roster.getInstanceFor(chat.sech.con);
        if (!roster.isLoaded())
            try {
                roster.reloadAndWait();
            }
        catch (SmackException.NotConnectedException e)
        {
            print("Noy yet connected");
        }
        catch (SmackException.NotLoggedInException e)
        {
            print("User isn't logged in");
        }
        catch (InterruptedException e)
        {
            print("Connection interrupted");
        }

        Collection<RosterEntry> entries = roster.getEntries();

        for (RosterEntry entry : entries) {
            print(entry);
        }
    }

    void print(Object a)
    {
        System.out.println(a);
    }
}
