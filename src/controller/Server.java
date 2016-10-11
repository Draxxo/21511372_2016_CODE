package controller;

import model.MoteurBlackjack;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.InterruptedException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import model.Player;

public class Server {
    
    static ArrayList<Client> allClient;
    static MoteurBlackjack mBJ;
    static int port;
    final static int NB_MAX_CLIENT = 5;
    final static int NB_CLIENT = 1;
    
    public Server(int port) {
        this.port = port;
        this.mBJ = new MoteurBlackjack(NB_CLIENT);
        this.allClient = new ArrayList<Client>();
    }
    
	public static void main(String[] args) throws IOException {

        Server server = new Server(1234);
		ServerSocket ss = new ServerSocket(1234);
        
        Thread acceptConnexion = new Thread(new AcceptConnexion(ss, NB_MAX_CLIENT));
        acceptConnexion.start();
        
       
		while(true) {   
            //System.out.println(allClient.size());
            try {
                Thread.sleep(10);
                
                /* bet */
                for(int i = 0; i < allClient.size(); i++) {
                    Client client = allClient.get(i);
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    
                    //System.out.println(allClient.size());
                    Thread bet = new Thread(new Bet(client, mBJ));
                    bet.start();
                    bet.join();
                } 
                
                //System.out.println(checkBets());
                if(!checkBets()) continue;
                /* ----------- GAME ----------- */
                sendToAll("Game started", null);
                /* initialisation */
                /*for(Player p: arrayToTab()) {
                    System.out.println(p.getName());
                }*/                
                mBJ.setNbPlayers(allClient.size());
                mBJ.initAll(clientToPlayer());
                mBJ.distribution();
                for(int i = 0; i < allClient.size();i++) {
                    mBJ.setBetTable(i+1, allClient.get(i).getBet());
                }
                
                for(int i = 0; i < allClient.size(); i++) {
                    Client client = allClient.get(i);
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    
                    Thread play = new Thread(new Play(client, mBJ));
                    play.start();
                    play.join();
                }
                
                /* end of the game */
                /*reinitiliasitaion of bets */
                for(Client client : allClient) {
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    client.setBet(0);
                }
                
             } catch (InterruptedException e) {
                System.err.println(e);
            }
        
		}       

	}
    
    public static boolean checkBets() {
        if(allClient.size() == 0) return false;
        
        for(Client client: allClient) {
            if(client.getBet() == 0) return false;
        }      
        return true;
    }
    
    public static MoteurBlackjack getMoteur() { return mBJ; }
    
    public static void sendToAll(String message, Socket currentSocket) {
        Thread sendToAll = new Thread(new SendToAll(message, currentSocket));
        sendToAll.start();
    }        
    
    public static ArrayList<Client> getAllClient() {
        return allClient;
    }
    
    public static void addClient(Client c) {
        allClient.add(c);
        //mBJ.setNbPlayers(allClient.size());
        //mBJ.addPlayer(c);
    }
    
    public static boolean delClient(Client c) {
        return allClient.remove(c);
    }
    
    public static ArrayList<Player> clientToPlayer() {
        ArrayList<Player> allPlayer = new ArrayList<Player>();
        for(Client client : allClient) {
            allPlayer.add((Player) client);
        }
        
        return allPlayer;
    }
    
/*    public static Player[] arrayToTab() {
        Player[] allPlayers = new Player[allClient.size()+1];
        allPlayers[0] = banquier;
        for(int i = 1; i < allClient.size(); i++) {
            System.out.println(allClient.get(i));
            allPlayers[i] = allClient.get(i);
        }
        
        return allPlayers;
    }*/
    
    
}