import java.util.ArrayList;

/**
 * Runs all threads
 */
public class BridgeRunner {
	public static void main(String[] args) {
		// We always want exact 2 arguments
		if (args.length != 2) {
			System.out.println("Usage: java BridgeRunner <bridge limit> <num cars>");
			return;
		}

		int bridgeLimit, numCars;
		try {
			bridgeLimit = Integer.parseInt(args[0]);
			numCars = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Error: bridge limit and/or num cars must be integers.");
			return;
		}
		// Both must be positive
		if (bridgeLimit < 1 || numCars < 1) {
			System.out.println("Error: bridge limit and/or num cars must be positive.");
			return;
		}

		// Create the bridge
		Bridge bridge = new OneLaneBridge(bridgeLimit);

		// Create the car threads
		ArrayList<Thread> cars = new ArrayList<>();
		for (int i = 0; i < numCars; i++) {
			cars.add(new Thread(new Car(i, bridge)));
		}

		// Start the threads
		for (int i = 0; i < numCars; i++) {
			cars.get(i).start();
		}

		// Join all the threads, handle the exceptions
		for (int i = 0; i < numCars; i++) {
			try {
				cars.get(i).join();
			} catch (InterruptedException e) {
				System.err.println("Car " + i + " interrupted!");
			}
		}

		System.out.println("All cars have crossed!!");
	}

}
