/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorclock;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class Vectorclock{
     static Scanner sc=new Scanner(System.in);
     static int d;
    public static void main(String[] args) {
        
        int p;
        int e;
        System.out.println("Enter the value of d:");
        d=sc.nextInt();
        System.out.println("Enter the no. of processes:");
        p=sc.nextInt();
        System.out.println("Enter the max. no of events:");
        e=sc.nextInt();
        int eventarr[]=new int[p];
        for(int i=0;i<p;i++){
            System.out.println("Enter the event for process "+(i+1)+":");
            eventarr[i]=sc.nextInt();
        }
        String arr[][]=new String[p][e+1];
        initialisation(arr,p,e+1);
        HashMap<String,String>map=new HashMap<>();
        hbr(map, arr);
        System.out.println("HBR are:-");
        System.out.println(map);
        Computation(arr,map,p,e+1,0);
        display(p,e+1,arr,eventarr);


        
    }
    
    static void initialisation(String arr[][],int p,int e){
        String z="";
        for(int i=0;i<p;i++)
            z+="0";
        
        for(int i=0;i<p;i++){
            for(int j=0;j<e;j++){
                arr[i][j]=z;
            }
        }
    }
    
    static void hbr(HashMap<String,String>map,String[][] arr){
        System.out.println("Enter total no. of hbr:");
        int line=sc.nextInt();
        String sp,rp,se,re,sender="",reciever="";
        while(line-->0){
            System.out.println("Enter the sender process:");
            sp=sc.next();
            System.out.println("Enter the sender event:");
            se=sc.next();
            sender=sp+se;
            System.out.println("Enter the reciever process:");
            rp=sc.next();
            System.out.println("Enter the reciever event:");
            re=sc.next();
            reciever=rp+re;
            map.put(sender,reciever);            
        }
    }


    
    static void update(String[][]arr,int i,int j,String sender){
       //ir2
        int se=Integer.valueOf(sender);
        int event=se%10;
        int process=se/10;
        String timestamp=arr[process-1][event];
        String previous=arr[i][j-1];
        char[] carr1=new char[previous.length()];
        char[] carr2=new char[previous.length()];
        carr1=timestamp.toCharArray();
        carr2=previous.toCharArray();
        String fin="";
        for(int z=0;z<carr1.length;z++){
            char c1=carr1[z];
            char c2=carr2[z];
            int i1=Character.getNumericValue(c1);//timestamp bit
            int i2=Character.getNumericValue(c2);//previous bit
            if(z==i)
                i2+=d;
            int i3=Math.max(i1,i2);
            fin+=String.valueOf(i3);
            
        }
        arr[i][j]=fin;
        
 
    }
    
     static void Computation(String[][] arr, HashMap<String, String> map,int p,int e,int process) {
       
        Set<String> keys=map.keySet();
        ArrayList<String>recset=new ArrayList<>();
        ArrayList<String>senderset=new ArrayList<>();
        String fp="";
        for(int z=0;z<p;z++)
            fp+="0";
        for(String ke:keys)
        {recset.add(map.get(ke));
         senderset.add(ke);
        }
         for(int i=1;i<e;i++){
            String rec=String.valueOf(process+1)+String.valueOf(i);
                if(recset.contains(rec)){                 //if event is a reciever
                    String sender=senderset.get(recset.indexOf(rec));
                   // System.out.println("Reciever:"+rec);
                     int se=Integer.valueOf(sender);
                     int event=se%10;
                     int sender_pro=se/10;
                     if(arr[sender_pro-1][event].equals(fp)){
                       //  System.out.println("sender:"+sender_pro);
                         Computation(arr,map,p,e,sender_pro-1);
                         update(arr,process,i,sender);
                     }
                     else{
                         update(arr,process,i,sender);
                     }
                }
                else{
                    //IR1 implementation
                    String temp=arr[process][i-1];
                    char temparr[]=new char[p];
                    temparr=temp.toCharArray();
                    char ci= temparr[process];
                    int val=Character.getNumericValue(ci)+d;
                    ci=(char)(val+'0');
                    temparr[process]=ci;
                    String f="";
                    for(int z=0;z<temparr.length;z++)
                       f+=String.valueOf(temparr[z]);
                    arr[process][i]=f;
                   
                   
                }
            
         }
     }

     static void display(int p,int e,String[][]arr,int[]eventarr) {
        for(int i=0;i<p;i++){
          System.out.println("");  
          System.out.print("P"+(i+1)+":");
            for(int j=1;j<=eventarr[i];j++)
                System.out.print(" "+arr[i][j]);
        }
    }

}
