package codiceinsicuro;
//aa
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class CodiceInsicuro {
	
	private static final List<String> WHITELISTED_IPS = Arrays.asList("8.8.8.8", "8.8.4.4", "192.168.1.1");

	public static void executeCommand(String userInput) {
		if(!WHITELISTED_IPS.contains(userInput)) {
			throw new IllegalArgumentException("IP " + userInput + " non presente nella white");
		}
		String inputSanificato = sanificaInput(userInput);
		try {
            // Esegui il ping tramite la API InetAddress di Java, senza usare la shell
            InetAddress address = InetAddress.getByName(inputSanificato);
            boolean reachable = address.isReachable(5000); // Timeout di 5 secondi

            if (reachable) {
                System.out.println("L'host " + inputSanificato + " è raggiungibile.");
            } else {
                System.out.println("L'host " + inputSanificato + " non è raggiungibile.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static String sanificaInput(String userInput) {
		String[] parti = userInput.split("\\."); // Dividiamo l'input in base ai punti

		if (parti.length != 4) { // Un indirizzo IP deve avere 4 parti
			throw new IllegalArgumentException("Input non valido: deve essere un IP con 4 numeri separati da punti.");
		}

		for (String parte : parti) {
			try {
				int num = Integer.parseInt(parte); // Converte la stringa in numero
				if (num < 0 || num > 255) { // Controlla che sia nel range 0-255
					throw new IllegalArgumentException("Input non valido: ogni numero deve essere tra 0 e 255.");
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Input non valido: deve contenere solo numeri.");
			}
		}

		return userInput; // Se tutti i controlli sono superati, restituisce l'IP
	}

	public static void main(String[] args) {
		String input = "8.8.8.8";
		if (input.length() > 0) {
			executeCommand(input);
		} else {
			System.out.println("Usage: java InsecureCommandExecutor <IP>");
		}
	}
}