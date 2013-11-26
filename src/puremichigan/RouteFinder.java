/*******************************************
 * Class: RouteFinder
 * Description: Contains the search algorithm, and finds/prints the distances
 *              between two specified cities.
 * Project: PureMichigan
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package puremichigan;

import java.io.*;

public class RouteFinder {
    
    private MapData md = new MapData();
    private Logger log = new Logger();
    
    private boolean[] included;
    private int[] distance;
    private int[] path;
    
    //************************CONSTRUCTOR************************//
    
    public RouteFinder(){
        
    } //end constructor
    
    //************************PUBLIC METHODS************************//
    
    //finds the route between two cities
    public void FindShortestRoute(int cityA, int cityB) throws IOException{
        log.writeln();
        
        InitializeArrays(cityA);
        SearchForPath(cityA, cityB);
        ReportAnswers(cityA, cityB);
        
    } //end FindShortestRoute
    
    //************************PRIVATE METHODS************************//
    
    //makes the arrays ready for the algorithm 
    private void InitializeArrays(int start){
        included = new boolean[md.getN()];
        distance = new int[md.getN()];
        path = new int[md.getN()];
        
        int i, d;
        
        for(i = 0;i<md.getN();i++){
            included[i] = false;        
            
            distance[i] = md.GetRoadDistance(start, i);  
            
            if(distance[i] < md.getMaxValue()) path[i] = start;
            else path[i] = -1;
        }
        
        included[start] = true;      
    } //end InitializeArrays
    
    //the "search" part of Dijkstra's algorith. Also prints the trace of targets
    private void SearchForPath(int start, int dest) throws IOException {
        int i, smallest, target, s = 1; //s is for incrementing stack
        
        log.write("TRACE OF TARGETS:  " + md.GetCityName(start) + " ");
        
        while(!(included[dest])){ //while destination is NOT yet included
            smallest = md.getMaxValue();
            target = -1;
            
            for(i = 0;i<md.getN();i++)
                if(distance[i] < smallest && !included[i]){
                    smallest = distance[i];
                    target = i;
                }              
            
            included[target] = true;
            log.write(md.GetCityName(target) + " ");
            
            for(i = 0;i<md.getN();i++){
                if(!included[i]){
                    if(md.GetRoadDistance(target, i) != md.getMaxValue()){
                        if(distance[target] + md.GetRoadDistance(target, i) < distance[i]){
                            distance[i] = distance[target] + md.GetRoadDistance(target, i);
                            path[i] = target;
                        }
                    }
                }
            } //end for
            
        } //end while
    } //end SearchForPath
    
    //prints total distance and shortest route
    private void ReportAnswers(int start, int destination) throws IOException {
        int d = destination, i = 0;
        int stack[] = new int[md.getN()];        
        
        log.writeln("\n\nTOTAL DISTANCE:  " + distance[destination]);
        log.write("SHORTEST ROUTE:  ");
        
        while(d != start){
            stack[i++] = d;
            d = path[d];
        }

        log.write(md.GetCityName(start));
        i--;
        while(i > -1){
            log.write(" > " + md.GetCityName(stack[i--]));
        }

        log.writeln();
    } //end reportAnswers
    
} //end RouteFinder class
