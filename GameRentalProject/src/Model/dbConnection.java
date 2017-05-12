package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Entity.Game;

public class dbConnection {
	
	private static String dbName = "GameRentalDb";

//	public static void main(String[] args) {
//        // Wywo�anie metody connect, kt�ra zwraca obiekt typu Connection
//
//        try{
//        	int newId = Game.getFirstFreeId();
//        	System.out.println(newId);
//        }catch (Exception e) {
//            System.out.println("jakis blad " + e.getMessage());
//        }
//        
//    }
    
    /**
     * Metoda odpowiedzialna za po��czenie z baz�
     * je�li bazy nie ma to zostaje utworzona
     */
    public static Connection connect() {
        Connection polaczenie = null;
        try {
            // Wskazanie jaki rodzaj bazy danych b�dzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");           
            // Po��czenie, wskazujemy rodzaj bazy i jej nazw�
            polaczenie = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
            System.out.println("Po��czy�em si� z baz� "+dbName);
        } catch (Exception e) {
            System.err.println("B��d w po��czeniu z baz�: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }
	
    //funkcja tworz�ca wszystkie tabele bazy (puste) -> #TODO doda� jakiego� ifa, �eby nie wykonywa� wszystkich instrukcji, je�li jest co� w bazie
    public static void createDb(Connection polaczenie) {
        // Obiekt odpowiadaj�cy za wykonanie instrukcji
            Statement stat = null;
            try {
                stat = polaczenie.createStatement();
                // polecenie SQL tworz�ce tabel� game
                String tabelaSQL = "CREATE TABLE IF NOT EXISTS " + "game"
                        + " (ID INT PRIMARY KEY     NOT NULL,"
                        + " name           CHAR(50)    NOT NULL, "
                        + " worth          INT) ";
                // wywo�anie polecenia
                stat.executeUpdate(tabelaSQL);
                // zamykanie wywo�ania i po��czenia
                stat.close();
                polaczenie.close();
            } catch (Exception e) {
                System.out.println("Nie mog� stworzy� tabeli " + e.getMessage());
            }
        }
    	
    //Uwaga, przy wielu danych i bazach na serwerach, efektywniej jest robi� kilka insert�w na jednym po��czeniu, zamiast co chwila otwiera� i zamyka� kolejne po��czenia

        

}
