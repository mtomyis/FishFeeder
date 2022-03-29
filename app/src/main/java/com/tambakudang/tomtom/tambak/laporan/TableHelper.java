package com.tambakudang.tomtom.tambak.laporan;

import android.content.Context;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableHelper {
    //DECLARATIONS
    Context c;
    private String[] spaceProbeHeaders={"Time","Temperatur","Ph","Oxygen",};
    private String[][] spaceProbes;

    //CONSTRUCTOR
    public TableHelper(Context c) {
        this.c = c;
    }

    //RETURN TABLE HEADERS
    public String[] getSpaceProbeHeaders()
    {
        return spaceProbeHeaders;
    }

    //RETURN TABLE ROWS
    public  String[][] getSpaceProbes(ArrayList<Spacecraft> spacecrafts)
    {
        Spacecraft s;

        spaceProbes= new String[spacecrafts.size()][4];

        for (int i=0;i<spacecrafts.size();i++) {

            s=spacecrafts.get(i);

            spaceProbes[i][0]=s.getWaktu();
            spaceProbes[i][1]=s.getSuhu();
            spaceProbes[i][2]=s.getPh();
            spaceProbes[i][3]=s.getOksigen();
        }

        return spaceProbes;

    }
}
