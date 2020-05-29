/*
In this assignment you shall implement a rudimentary flight trip planner. 
The input is the set of flights between various cities. It is given as a file. 
Each line of the file contains "city1 city2 departure-time arrival-time flight-no. price". 
This means that there is a flight called "flight-no" (which is a string of the form XY012) from city1 to city2 which leaves city1 at time 
"departure-time" and arrives city2 at time "arrival-time". Further the price of this flight is "price" which is a poitive integer. 
All times are given as a string of 4 digits in the 24hr format e.g. 1135, 0245, 2210. Assume that all city names are integers between 1 
and a number N (where N is the total number of cities).

Note that there could be multiple flights between two cities (at different times).

The query that you have to answer is: given two cities "A" and "B", times "t1", "t2", where t1 < t2, find the cheapest trip which 
leaves city "A" after time "t1" and arrives at city "B" before time "t2". A trip is a sequence of flights which starts at A after time t1 
and ends at B before time t2.  Further, the departure time from any transit (intermediate) city C is at least 30 mins after the arrival 
at C.

Your algorithm should be as efficient as possible.

*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



 class Heap {
    node array[];
    int EndPointer;
    Heap(int NoOfCities){
        array=new node[NoOfCities];
        EndPointer=0;
    }
    node [] array(){
        return array;
    }
    void heapify(int i){
       
        
        while(true){
            if (array[(2*i)+1]==null){
                if (array[2*i]==null){
                    break;
                }
                else{
                    if (array[i].minimum_fare<=array[(2*i)].minimum_fare){
                    break;
                    }
                    else{
                        node j=array[i];
                        array[i]=array[(2*i)];
                        array[(2*i)]=j;
                        break;
                    }
                }
            }
            else if(array[2*i].minimum_fare==array[(2*i)+1].minimum_fare){
                if (array[i].minimum_fare<=array[(2*i)+1].minimum_fare){
                    break;
                }
                else{
                    node j=array[i];
                    array[i]=array[(2*i)+1];
                    array[(2*i)+1]=j;
                    i=(2*i)+1;
                }
            }
            else if (array[2*i].minimum_fare>array[(2*i)+1].minimum_fare){
                if (array[i].minimum_fare<=array[(2*i)+1].minimum_fare){
                    break;
                }
                else{
                    node j=array[i];
                    array[i]=array[(2*i)+1];
                    array[(2*i)+1]=j;
                    i=(2*i)+1;
                }
            }
            else if (array[2*i].minimum_fare<array[(2*i)+1].minimum_fare){
                 if (array[i].minimum_fare<=array[(2*i)].minimum_fare){
                    break;
                }
                else{
                    node j=array[i];
                    array[i]=array[(2*i)];
                    array[(2*i)]=j;
                    i=(2*i);
                }
            }
        }
    }
    void  insertion(int i){
        if (i==1){
        }
        else if(array[i].minimum_fare>=array[i/2].minimum_fare){
        }
        else if(array[i].minimum_fare<array[i/2].minimum_fare){
            node j=array[i];
            array[i]=array[i/2];
            array[i/2]=j;
            insertion(i/2);
        }
    }
    void insert(node i){
        EndPointer=EndPointer+1;
        array[EndPointer]=i;
        insertion(EndPointer);
    }
    void delete(){
        array[1]=array[EndPointer];
        array[EndPointer]=null;
        EndPointer=EndPointer-1;
//        System.out.println("in delete");
        heapify(1);
    }
    void updatearray(node heapofcities[]){
        int p=1;
        while(array[p]!=null){
            array[p]=heapofcities[p];
            p=p+1;
        }
    }
    
    node min(){
        return array[1];
    }
    int city(String s){
        int i=1;
        while(true){
            if (i>EndPointer+1){
                return i;
            }
            else if (array[i].city.equals(s)){
                return i;
            }
            else{
                i=i+1;
            }
        }
    }
    void deleteAll(String city1,int i){
        int j=1;
        while(array[j]!=null){
            if (array[j].city.equals(city1)){
                makeFare(j,0);
                delete();
                j=j+1;
            }
            else{
                j=j+1;
            }
        }
    }
    void makeFare(int k,int price){
        int previousfare=array[k].minimum_fare;
        array[k].minimum_fare=price;
        node a=array[k];
        if (price<previousfare){
            insertion(k);
        }
        else if (price>previousfare){
            heapify(k);
        }
    }
    node nodeat(int k){
        return array[k];
    }
    void UpdateNeighboursOf(String s,node1 array1[],int s_minimum_fare,int t2,int t1,int arrivaltime){
        int S=1;
        while(true){
            if (array1[S].city.equals(s)){
                break;
            }
            else{
                S=S+1;
            }
        }
        node2 a[]= array1[S].cities_connected_by_planes;
        int j=0;
        while(a[j]!=null){
            int q=search(a[j]);
                if (q>EndPointer){
                    break;
                }
//            System.out.println("neighbour "+a[j].city2 + " of "+s+"updating ");
//            System.out.println(" minimum fare already present : "+array[q].minimum_fare);
//            System.out.println("fare through the processing city : "+s_minimum_fare+a[j].price);
            if (array[q].minimum_fare>s_minimum_fare+a[j].price){
                
                if (array[q].arrival_time>t2){
//                    System.out.println(" not updating because array[q].arrival_time>t2 ::: arrival time :  "+array[q].arrival_time+" t2 : "+t2);
                    array[q].minimum_fare=1;
                    insertion(q);
                    delete();
                }
                else if (array[q].departure_time<arrivaltime+30){
//                    System.out.println(" not updating because array[q].departure_time<arrivaltime+30 ::: departure_time : "+array[q].departure_time+" arrivaltime "+arrivaltime+30);
                    array[q].minimum_fare=1;
                    insertion(q);
                    delete();
                }
                else{
                array[q].minimum_fare=s_minimum_fare+a[j].price;
//                System.out.println(" updating minimum fare ");
//                System.out.println(array[q].city+" minimum fare set to "+array[q].minimum_fare);
                insertion(q);
                }
            }
            j=j+1;
        }
        
    }
    int search(node2 a){
        int p=1;
        while(array[p]!=null){
            if((array[p].city==a.city2)&&(array[p].arrival_time==a.arrival_time)&&(array[p].departure_time==a.departure_time)&&(array[p].price==a.price)){
                break;
            }
            else{
                p=p+1;
            }
        }
        return p;
    }
}


class node{
    String city;
    int departure_time;
    int arrival_time;
    int minimum_fare;
    int price;
    {
        city=null;
        departure_time=0;
        minimum_fare=10000000;
        arrival_time=0;
        price=0;
    }
}

class node1{
    String city;
    node2 cities_connected_by_planes[];
    int endpointernode1;
    int minimum_fare;
    node1 previous=null;
    
    node1(int i){
        minimum_fare=10000000;
        endpointernode1=-1;
        cities_connected_by_planes=new node2[i*2];
    }
}

class node2{
    String city2;
    int departure_time;
    int arrival_time;
    int price;
    String flight_no;
    node2(){
        city2=null;
        departure_time=0;
        arrival_time=0;
        price=0;
        flight_no=null;
    }
}

public class Assignment5 {
    
    static int IsInside(String w,node1 array[],int end_pointer){
        int i=1;
        while(i<=end_pointer){
            if (array[i].city.equals(w)){
                return i;
            }
            else{
                i=i+1;
            }
        }
        return i;
    }
    static int cheaptrip(String city1,String city2,int t1,int t2,Heap h1,int j,node1 array[]){
//        System.out.println(" ");
//        System.out.println(" ");
//        System.out.println(" ");
//        System.out.println(" starting city : "+city1);
//        System.out.println(" ending city : "+city2);
//        System.out.println(" heap minimum : "+h1.min().city);
//        System.out.println(" is heapminimum city = ending city : "+h1.min().city.equals(city2));
//        System.out.println(" ");
//        System.out.println(" ");
        if (city1.equals(city2)){
//            System.out.println(" ==/ in 1 starting city and ending city are same ");
            return 0;
        }
        else if (j==1){
//                System.out.println(" ==/ in 2 removing starting city to initialise djkstra ");
                int city1_minimum_fare=0;
                h1.deleteAll(city1,0);
//                System.out.println(" while initialising updating neighbour of "+city1);
                h1.UpdateNeighboursOf(city1,array,city1_minimum_fare,t2,t1,t1-30);
                j=j+1;
                return cheaptrip(city1,city2,t1,t2,h1,j,array);
        }
        else if (h1.min().city.equals(city2)){
//            System.out.println("==/ in heap minimum == ending city ");
//            System.out.println(" ##################################### ending here ");
            if (h1.min().minimum_fare==10000000){
                return -1;
            }
            else{
                return h1.min().minimum_fare;
            }
        }
        else{
//            System.out.println("==/ else simply taking smallest element in heap and updating its neighbours ");
            node a=h1.nodeat(1);
            String s=a.city;
            int city1_minimum_fare=a.minimum_fare;
            int arrivaltime=h1.array[1].arrival_time;
            h1.delete();
//            System.out.println(" updating neighbour of "+s);
            h1.UpdateNeighboursOf(s,array,city1_minimum_fare,t2,t1,arrivaltime);
            j=j+1;
            return cheaptrip(city1,city2,t1,t2,h1,j,array);
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
         File text = new File("f:/assignment5.txt");
         Scanner scnr = new Scanner(text);
         int queries=0;
         int i=0;
         int k=0;
        String totalcities=scnr.next();
         int cities=Integer.parseInt(totalcities);
         String totalflights=scnr.next();
         int numberofflights=Integer.parseInt(totalflights);
         Heap h1= new Heap(numberofflights+4);
         node heapofcities[]=new node[numberofflights+4];
         int endOfHeapOfCities=0;
        node1 array[]=new node1 [cities+1];
        int r=1;
        while(r<=cities){
            node1 b=new node1(cities);;
            String s=String.valueOf(r);
            b.city=s;
            array[r]=b;
            r=r+1;
        }
        int end_pointer=0;
        
            while (scnr.hasNext()){
                if (i==numberofflights){
                    String NoOfQueries=scnr.next();
                    queries=Integer.parseInt(NoOfQueries);
                    break;
                }
                else{
                    node2 a=new node2();
                    String word=scnr.next();
                    String word1=scnr.next();
                    a.city2=word1;
                    String word2=scnr.next();
                    int departuretime=Integer.parseInt(word2);
                    a.departure_time=departuretime;
                    String word3=scnr.next();
                    int arrivaltime=Integer.parseInt(word3);
                    a.arrival_time=arrivaltime;
                    String word4=scnr.next();
                    a.flight_no=word4;
                    String word5=scnr.next();
                    int price=Integer.parseInt(word5);
                    a.price=price;
                    node c=new node();
                    c.city=word1;
                    c.arrival_time=arrivaltime;
                    c.departure_time=departuretime;
                    c.price=price;
                    h1.insert(c);
                    heapofcities[endOfHeapOfCities+1]=c;
                    endOfHeapOfCities=endOfHeapOfCities+1;
                        int q=IsInside(word,array,end_pointer);
                        if (q>end_pointer){
                            end_pointer=end_pointer+1;
                            node1 b=new node1(cities);
                            b.city=word;
                            (b.endpointernode1)++;
                            b.cities_connected_by_planes[b.endpointernode1]=a;
                            array[end_pointer]=b;
                        }
                        else{
                            array[q].endpointernode1++;
                            array[q].cities_connected_by_planes[array[q].endpointernode1]=a;
                        }
                    i=i+1;
                }
        }
        while (scnr.hasNext()){
            if (k>queries){
                break;
            }
            else{
                if (k==0){
                    String city1st=scnr.next();
                    String city2nd=scnr.next();
                    String c=scnr.next();
                    int t1=Integer.parseInt(c);
                    String d=scnr.next();
                    int t2=Integer.parseInt(d);
//                    System.out.println("question city1 : "+city1st);
//                    System.out.println("question  city2 : "+city2nd);
//                    System.out.println("question t1 : "+t1);
//                    System.out.println("question t2 : "+t2);
                    System.out.println(cheaptrip(city1st,city2nd,t1,t2,h1,1,array));
                    k=k+1;
                    while(h1.array[1]!=null){
                        h1.delete();
                    }
                    int p=1;
                    while(heapofcities[p]!=null){
                        heapofcities[p].minimum_fare=10000000;
                        h1.insert(heapofcities[p]);
                        p=p+1;
                    }
                }
                else{
                    String city1st=scnr.next();
                    String city2nd=scnr.next();
                    String c=scnr.next();
                    int t1=Integer.parseInt(c);
                    String d=scnr.next();
                    int t2=Integer.parseInt(d);
//                    System.out.println("question city1 : "+city1st);
//                    System.out.println("question  city2 : "+city2nd);
//                    System.out.println("question t1 : "+t1);
//                    System.out.println("question t2 : "+t2);
                    System.out.println(cheaptrip(city1st,city2nd,t1,t2,h1,1,array));
                    k=k+1;
                    while(h1.array[1]!=null){
                        h1.delete();
                    }
                    int p=1;
                    while(heapofcities[p]!=null){
                        heapofcities[p].minimum_fare=10000000;
                        h1.insert(heapofcities[p]);
                        p=p+1;
                    }
                }
            }
        }
    }
}
