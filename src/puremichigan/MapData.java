/*******************************************
 * Class: MapData
 * Description: Provides several public service methods to handle interacting
 *              with the map data, including names and distances of cities.
 * Project: PureMichigan
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package puremichigan;

import java.io.*;

public class MapData {
    
    private static int n = 0;
    private static int MAX_N = 200;
    private static int MaxValue = 30000; //basically infinity
    private static int[][] roadDistance = new int[MAX_N][MAX_N];
    private static String[] cityNameList = new String[MAX_N];
    private static String[] upNameList = new String[MAX_N];
    
    //************************CONSTRUCTOR************************//
    
    public MapData(){
        int r, c;
        
        //initializes roadDistance to infinities
        for(r=0;r<MAX_N - 1;r++){
            for(c=0;c<MAX_N - 1;c++){
                if(r == c) roadDistance[r][c] = 0;
                else roadDistance[r][c] = MaxValue;
            }
        }
        
        
    } //end constructor 
    
    //************************PUBLIC METHODS************************//
    
    //loads the map data from a file into memory
    public static void LoadMapData() throws IOException {
        FileReader michiganRoads = new FileReader("MichiganRoads.txt");
        BufferedReader inFile = new BufferedReader(michiganRoads);
        
        String line; 
        int i;
        
        while((line = inFile.readLine()) != null){
            if(line.equals("")) line = "%"; //so the switch can disregard the empty lines
            switch (line.charAt(0)){
                case 'u': //reading cities that are in the UP  
                    String[] cities = line.split(","); //splits cities up
                    //even works with cities that are 1 char
                    for(i = 0;i<cities.length;i++){ //formats all cities
                        if(cities[i].charAt(cities[i].length() - 1) == '.')
                            cities[i] = cities[i].substring(1, cities[i].length() - 4);
                            //removes trailing
                        else if(cities[i].charAt(0) == ' ') 
                            cities[i] = cities[i].substring(1); //removes space
                        else if(cities[i].charAt(0) == 'u') 
                            cities[i] = cities[i].substring(5); //removes up([
                    }
                    
                    upNameList = cities;
                    
                    break;
                    
                case 'd': //reading the distance between two cities  
                    String dist[] = line.split(","); //splits the line up
                    
                    //formatting
                    dist[0] = dist[0].substring(5); //removes dist(
                    dist[1] = dist[1].substring(1); //removes space
                    dist[2] = dist[2].substring(1, dist[2].length() - 2); //removes trailing
                    
                    roadDistance[StoreCityName(dist[0])][StoreCityName(dist[1])] 
                            = Integer.parseInt(dist[2]);
                    
                    //stores it both ways
                    roadDistance[StoreCityName(dist[1])][StoreCityName(dist[0])] 
                            = Integer.parseInt(dist[2]);
                    
                    break;
                    
                case '%':  //comments
                default:    
                    break;
            }
        } //end while
        
        inFile.close();
    } //end LoadMapData
    
    //gets the number of the city name "name"
    //uses a linear search, as requested by Dr. Kaminski
    public int GetCityNumber(String name){
        int i;
        
        for(i=0;i<n;i++){
            if(name.equals(cityNameList[i])) return i;
        }
        
        return -1;
    } //end GetCityNumber
    
    //returns "LP" if name is not in "UP" list, and "UP" if it is
    public String GetCityPeninsula(String name){
        int i;
        
        for(i=0;i<upNameList.length;i++){
            if(name.equals(upNameList[i])) return "UP";
        }
        
        return "LP";
    } //end GetCityPeninsula
    
    //Gets the name of the city that has the input number
    public String GetCityName(int number){
        return cityNameList[number];
    } //end GetCityName
    
    //returns the distance between 2 city numbers
    public int GetRoadDistance(int cityNumA, int cityNumB){
        return roadDistance[cityNumA][cityNumB];
    } //end GetRoadDistance
    
    //returns n
    public int getN(){
        return n;
    } //end getN
    
    //returns maxValue
    public int getMaxValue(){
        return MaxValue;
    } //end getMaxValue
    
    //************************PRIVATE METHODS************************//
    
    //finds out of the name is already in city name list, and returns it's value
    //OR ADDS it to the list if not on the list
    private static int StoreCityName(String name){
        int i;
        
        for(i=0;i<n;i++){
            if(name.equals(cityNameList[i])) return i;
        }
        
        cityNameList[n] = name;
        return n++;
    }
    
    //test method that prints the UP cities array
    private void printUPCities(){
        int i;
        for(i = 0;i<upNameList.length;i++){
            System.out.println("City " + i + ": " + upNameList[i]);
        }
    } //end printUPCities
    
    //test method to print the distance list
    private void printDistances(){
        int r, c, i;
        for(r=0;r<n - 1;r++){
            for(c=0;c<n - 1;c++){
                System.out.print(roadDistance[r][c]);
                System.out.print(" ");
            }
            
            System.out.println();
        }
    } //end print Distances
    
} //end MapData class
