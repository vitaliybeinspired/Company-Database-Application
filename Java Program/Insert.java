import java.sql.*;
import java.io.*;


public class Insert {
    
    private static void printMenu() {
        	System.out.println("\n        INSERT OPTIONS ");
            System.out.println("(1) Add Employee. ");
            System.out.println("(2) Add Equipment. ");
            System.out.println("(3) Add Service. ");
            System.out.println("(4) Add Customer. ");
            System.out.println("(q) Main Menu. \n");
	}
	
	public static void insertMenu(Connection conn) throws SQLException, IOException {
		 if (correctLogin(conn)) {
			 boolean done = false;
		         do {
		        	 printMenu();
		             System.out.print("Type in your option: ");
		             System.out.flush();
		             String ch = readLine();
		             System.out.println();
		             switch (ch.charAt(0)) {
		                 case '1': addEmployee(conn);
		                 	break;
		                 case '2': addEquipment(conn);
		                 	break;
		                 case '3': addService(conn);
		                 	break;
		                 case '4': addCustomer(conn);
		                 	break;
		                 case 'q': done = true;
		                     break;
		                 default:
		                     System.out.println(" Not a valid option ");
		             } 
		         } while (!done);
		 }
	}
	
    private static boolean correctLogin(Connection conn)throws SQLException, IOException {
        System.out.println("Administration login required");
    	String user = readEntry("userid : ");
        String pass = readEntry("password: ");
        String url = "jdbc:mysql://localhost:3306/CleanandGo?serverTimezone=UTC&useSSL=TRUE";
        conn = DriverManager.getConnection(url, user, pass);
    	return true;
    }
    
    // inserts a row in the Employee entity by asking user for inputs
    private static void addEmployee(Connection conn) throws SQLException, IOException {        	
			String insertIntoEmployee = "Insert into Employee values(?,?,?,?,?,?,?,?,?,?)";
    		PreparedStatement inserIntoEmployeeStatement = conn.prepareStatement(insertIntoEmployee);
                		
     		String SSN = readEntry("Enter employee SSN: ");
     		inserIntoEmployeeStatement.setString(1, SSN); 
     		String Fname = readEntry("Enter First Name: ");
     		inserIntoEmployeeStatement.setString(2, Fname);            
     		String Minitial = readEntry("Enter Middle Initial: ");
     		inserIntoEmployeeStatement.setString(3, Minitial);
     		String Lname = readEntry("Enter Last Name: ");
     		inserIntoEmployeeStatement.setString(4, Lname);
     		String gender = readEntry("Enter Gender: ");
     		inserIntoEmployeeStatement.setString(5, gender);
    		String Date = readEntry("Enter Date Hired: ");
    		inserIntoEmployeeStatement.setString(6, Date); 
     		String position = readEntry("Enter Position: ");
     		inserIntoEmployeeStatement.setString(7, position);
     		String salary = readEntry("Enter Salary: ");
     		inserIntoEmployeeStatement.setString(8, salary);
     		String address = readEntry("Enter Address: ");
     		inserIntoEmployeeStatement.setString(9, address);
     		String telephone = readEntry("Enter Telephone: ");
     		inserIntoEmployeeStatement.setString(10, telephone);
    
     		inserIntoEmployeeStatement.executeUpdate();
        }
    
    
 // Adds a row in assets Entity and a row in Equipment entity from user inputs 
    private static void addEquipment(Connection conn) throws SQLException, IOException {        		
    	
    	// gets row count from assets Entity
    	String AssetsCount = "SELECT COUNT(*) FROM Assets";
    	Statement AssetsCountStatement = conn.createStatement();
		ResultSet rs = AssetsCountStatement.executeQuery(AssetsCount);			
		
		// inserts into assets by incrementing by one
    	String insertIntoAssets = "Insert into Assets values(?)";
		PreparedStatement insertIntoAssetsStatement = conn.prepareStatement(insertIntoAssets);

		
		// inserts into equipment with the value of the assets count and user inputs
    	String insertIntoEquipment = "Insert into Equipment values(?,?,?,?,?)";
		PreparedStatement insertIntoEquipmentStatement = conn.prepareStatement(insertIntoEquipment);
    	
		while(rs.next()){
		    int count = rs.getInt("COUNT(*)");
		    insertIntoAssetsStatement.setInt(1, count + 1);
		    insertIntoAssetsStatement.executeUpdate();
    		insertIntoEquipmentStatement.setInt(2, count); 
		  }

 		String E_ID = readEntry("Enter Equipment ID: ");
 		insertIntoEquipmentStatement.setString(1, E_ID);            
 		String Brand_Name = readEntry("Enter Brand Name: ");
 		insertIntoEquipmentStatement.setString(3, Brand_Name);
 		String E_Type = readEntry("Enter Equipment Type: ");
 		insertIntoEquipmentStatement.setString(4, E_Type);
 		String MaintPrice = readEntry("Enter Maintaince Price: ");
 		insertIntoEquipmentStatement.setString(5, MaintPrice);    	

 		insertIntoEquipmentStatement.executeUpdate();
}
    
    // adds a row in Service entity by asking user for inputs
    private static void addService(Connection conn) throws SQLException, IOException {        	
      	String ServiceCount = "SELECT COUNT(*) FROM Service";
    	Statement ServiceCountStatement = conn.createStatement();
		ResultSet rs = ServiceCountStatement.executeQuery(ServiceCount);
		
		String insertIntoService = "Insert into Service values(?,?,?,?,?)";
		PreparedStatement insertIntoServiceStatement = conn.prepareStatement(insertIntoService);
		
		while(rs.next()){
		    int count = rs.getInt("COUNT(*)");
    		insertIntoServiceStatement.setInt(1, count + 1); // increments service_ID
		}
		
 		String S_Name = readEntry("Enter Service Name: ");
 		insertIntoServiceStatement.setString(2, S_Name);            
 		String Description = readEntry("Enter Description: ");
 		insertIntoServiceStatement.setString(3, Description);
 		String Rate = readEntry("Enter Rate: ");
 		insertIntoServiceStatement.setString(4, Rate);
 		String Duration = readEntry("Enter Duration: ");
 		insertIntoServiceStatement.setString(5, Duration);    	
		
 		insertIntoServiceStatement.executeUpdate();
   }
    
    private static void addCustomer(Connection conn) throws SQLException, IOException {        	
		String CID, Fst,Mnt,Lst,addr,email,phone,cc;
		CID = readEntry("CID: ");
		Fst = readEntry("First Name: ");
		Mnt = readEntry("Middle Initial: ");
		Lst = readEntry("Last Name: ");
		addr = readEntry("Address: ");
		email = readEntry("Email: ");
		phone = readEntry("Phone Number: ");
		cc = readEntry("Credit Card Info: ");
    	
		String stmt = "CALL newCustomer(?,?,?,?,?,?,?,?)";
    	CallableStatement cstmt = conn.prepareCall(stmt);
    	
    	cstmt.setString(1, CID);
    	cstmt.setString(2, Fst);
    	cstmt.setString(3, Mnt);
    	cstmt.setString(4, Lst);
    	cstmt.setString(5, addr);
    	cstmt.setString(6, email);
    	cstmt.setString(7, phone);
    	cstmt.setString(8, cc);
    	
    	cstmt.executeUpdate();
    }
    
    public static String readEntry(String prompt) {
        try {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }
    
    private static String readLine() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr, 1);
        String line = "";

        try {
            line = br.readLine();
        } catch (IOException e) {
            System.out.println("Error in SimpleIO.readLine: " +
                    "IOException was thrown");
            System.exit(1);
        }
        return line;
    
}
}
    