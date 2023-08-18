import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 *
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the 
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);
		// instantiate the lotDesign and occupancy variables!
		// WRITE YOUR CODE HERE!
		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}
	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 *
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 *
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		// WRITE YOUR CODE HERE!
		return null; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD

	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 *
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		if(i<0|| i>=numRows||j<0||j>=numSpotsPerRow ){
			//is out of bounds
			return false;
		}

		for(int x=0; x<occupancy.length; x++) {
			for(int y=0; y<occupancy[x].length; y++) {
				if (occupancy[x][y] == null) {
					CarType cartype=c.getType();

					if (cartype.equals(CarType.NA) && (CarType.NA.equals(lotDesign[x][y]))){
						return false;
					}
					if((	cartype.equals(CarType.ELECTRIC) && CarType.ELECTRIC.equals(lotDesign[x][y]))||
							(cartype.equals(CarType.SMALL) && (lotDesign[x][y].equals(CarType.ELECTRIC))!=true)||
							(cartype.equals(CarType.REGULAR)&& ( lotDesign[x][y].equals(CarType.REGULAR) || lotDesign[x][y].equals(CarType.LARGE) ))||
							(cartype.equals(CarType.LARGE) && (lotDesign[x][y].equals(CarType.LARGE)))){
						return true;
					}

				}
				}
				}
		return false;
	}





	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int subvar=0;

		for(int i=0; i<occupancy.length; i++) {
			for(int j=0; j<occupancy[i].length; j++) {
				if (occupancy[i][j] == null){
				subvar+=1;
				}
			}
		}
		return (numRows*numSpotsPerRow)-subvar; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		int subvar=0;

		for(int i=0; i<occupancy.length; i++) {
			for(int j=0; j<occupancy[i].length; j++) {
				if (occupancy[i][j] != null){
					subvar+=1;
				}
			}
		}
		return (numRows*numSpotsPerRow)-subvar; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD		return -1; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	private void calculateLotDimensions(String strFilename) throws Exception {
		String str = "";
		String hold = "";
		Scanner scanner = new Scanner(new File(strFilename));
		while (scanner.hasNext()) {
			str = scanner.nextLine();
			if (str.contains("##") == false && str.length() != 0) {
				numRows++;
				hold = str;
			} else if (str.contains("##") == true) {
				break;
			}
		}
		String[] holder = hold.split(",");
		numSpotsPerRow = holder.length;
		scanner.close();
	}

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		String holder[] = new String[numSpotsPerRow];
		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!

		// while loop for reading the lot design
		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Car[numRows][numSpotsPerRow];
		int counter = 0;
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			if (str.contains("##") == false && str.length() != 0) {
				holder = str.split(",");
				for (int i = 0; i < numSpotsPerRow; i++) {
					holder[i] = holder[i].trim();
					lotDesign[counter][i] = Util.getCarTypeByLabel(holder[i]);
				}
				counter++;
			} else if (str.contains("##") == true) {
				break;
			}
		}
		holder = new String[4];
		// while loop for reading occupancy data
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			if (str.length() > 0) {
				System.out.println("yes");
				holder = str.split(",");
				for (int i = 0; i < holder.length; i++) {
					holder[i] = holder[i].trim();
				}
				int num1 = Integer.parseInt(holder[0]);
				int num2 = Integer.parseInt(holder[1]);
				occupancy[num1][num2] = new Car(Util.getCarTypeByLabel(holder[2]), holder[3]);
			}
		}
		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 *
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 *
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}