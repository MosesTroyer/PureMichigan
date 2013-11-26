/*******************************************
 * Class: UserApp
 * Description: This project uses a modified Dijkstra's algorithm to find the 
 *              shortest path between two cities in Michigan, reading in from
 *              provided files for city name and distances, and for which
 *              routes to find. This class handles loading the map data 
 *              (using MapData Class) and processing the transaction file.
 * Project: PureMichigan
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package puremichigan;

import java.io.*;

public class UserApp {

    public static void main(String[] args) throws IOException {
        DeleteFile("log.txt"); //starts clean log

        Logger log = new Logger();
        MapData md = new MapData();
        RouteFinder rf = new RouteFinder();
        
        FileReader cityPairs = new FileReader("CityPairs.txt");
        BufferedReader inFile = new BufferedReader(cityPairs);
        
        String line;
        String[] pair;
        String[] peninsulas = new String[2];
        int[] cNumbers = new int[2];
        
        md.LoadMapData();
    
        //while there's still lines
        while((line = inFile.readLine()) != null){
            pair = line.split(" ");
            
            cNumbers[0] = md.GetCityNumber(pair[0]);
            cNumbers[1] = md.GetCityNumber(pair[1]);
            
            log.writeln("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
            
            log.write("START:  " + pair[0] + " ");
            
            //checks to make sure city exists
            if(cNumbers[0] != -1){
                peninsulas[0] = md.GetCityPeninsula(pair[0]);
                log.writeln("(" + cNumbers[0] + ") " + peninsulas[0]);
                
                log.write("DESTINATION:  " + pair[1] + " ");
                
                //checks to make sure city exists
                if(cNumbers[1] != -1){
                    peninsulas[1] = md.GetCityPeninsula(pair[1]);
                    log.writeln("(" + cNumbers[1] + ") " + peninsulas[1]);
                    
                    if(peninsulas[0].equals(peninsulas[1])){
                        rf.FindShortestRoute(cNumbers[0], cNumbers[1]);
                    }
                    else{
                        log.writeln("[***** 2 different peninsulas, so 2 "
                                + "partial routes *****]");
                        
                        //splits up routes
                        rf.FindShortestRoute(cNumbers[0], md.GetCityNumber("theBridge"));
                        rf.FindShortestRoute(md.GetCityNumber("theBridge"), cNumbers[1]);
                    }
                    
                }
                else log.writeln("\nERROR:  destination city not in Michigan Map Data");
            }
            else log.writeln("\nERROR:  start city not in Michigan Map Data");
            
        } //end while
        
        log.writeln("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        
        inFile.close();
        log.close();
    } //end main
    
    //from Dr. Kaminski, World Data Project 1
    private static boolean DeleteFile(String fileName) {
        boolean delete = false;
        File f = new File(fileName);
        if (f.exists()) {
            delete = f.delete();
        }
        return delete;
    } 
    
} //end UserApp class
