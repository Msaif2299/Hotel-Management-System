import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
@SuppressWarnings("unused")
public class Main {

	public static void clrscr() {
		for(int i = 0; i < 50; i++)
			System.out.println('\n');
	}
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/hotel";
	static final String USERNAME = "root";
	static final String PASSWORD = "senbonsakura";
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USERNAME,PASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < 50; i++) System.out.println('\n');
			char c = 'f';
			System.out.println("Welcome to a Hotel Management System!\n\n");
			while(c == 'F' || c == 'f') {
				System.out.println("What do you wish to do? Enter the category:");
				System.out.println("1. Partners and Ownership");
				System.out.println("2. Building and Rooms");
				System.out.println("3. Customers and Bookings");
				System.out.println("4. Transactions");
				System.out.println("5. Exit");
				int option;
				System.out.println("Enter your option: ");
				option = sc.nextInt();
				sc.nextLine();
				switch(option) {
				case 1:
					while(true) {
						System.out.println("Partnership and Ownership Menu\n");
						System.out.println("1. View Partnership Details");
						System.out.println("2. View Ownership Details");
						System.out.println("3. Back");
						System.out.println("Enter your option: ");
						int partnerOption = sc.nextInt();
						sc.nextLine();
						switch(partnerOption) {
						case 1:
							System.out.println("\n");
							System.out.println("Partnership Details\n");
							String sql = "SELECT * FROM partner;";
							Statement stmt1 = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql);
							while(rs.next()) {
								int PID = rs.getInt("PID");
								String address = rs.getString("Address");
								System.out.println("Partner's ID: " + PID);
								System.out.println("Partner's Address: " + address);
								sql = "select * from ph_no where PID = '" + PID + "';";
								ResultSet rs1 = stmt1.executeQuery(sql);
								int count = 1;
								while(rs1.next()) {
									System.out.println("Phone " + count + ": " + rs1.getInt("ph_no"));
									count++;
								}
								System.out.println("\n");
							}
							sc.nextLine();
							System.out.println("\n");
							break;
						case 2:
							System.out.println("Ownership Details\n");
							sql = "SELECT distinct PID FROM ownership;";
							rs = stmt.executeQuery(sql);
							while(rs.next()) {
								System.out.println("Partner's ID: " + rs.getInt("PID"));
							}
							System.out.println("\n");
							System.out.println("Enter the Partner's ID: ");
							int PID = sc.nextInt();
							sc.nextLine();
							rs = stmt.executeQuery("select bid from ownership where PID = " + PID + ";");
							int count = 1;
							while(rs.next()) 
							{
								System.out.println("Building " + count + " ID: " + rs.getInt("BID"));
								count++;
							}
							System.out.println("\n");
							break;
						case 3:
							break;
							default:
								System.out.println("Wrong option. Moving back to menu...");
								continue;
						}
						break;
					}
					break;
				case 2:
					while(true) {
						System.out.println("Buildings and Rooms Menu");
						System.out.println("1. View all buildings details");
						System.out.println("2. View specific building details");
						System.out.println("3. View available room details");
						System.out.println("4. View specific room details");
						System.out.println("5. Back");
						System.out.println("Enter your option: ");
						int buildingOption = sc.nextInt();
						ResultSet rs;
						switch(buildingOption) {
						case 1:
							System.out.println("All Building Details\n");
							rs = stmt.executeQuery("select * from building;");
							while(rs.next()) {
								System.out.println("Building ID: " + rs.getInt("BID"));
								System.out.println("Building Address: " + rs.getString("street") + ", " + rs.getString("city") + ", " + rs.getString("state") + ", " + rs.getString("country"));
								System.out.println("Building Net Worth: " + rs.getInt("net_worth"));
								System.out.println("\n");
							}
							break;
						case 2:
							System.out.println("Specific Building Details\n");
							rs = stmt.executeQuery("select bid from building;");
							while(rs.next()) {
								System.out.println("Building ID: " + rs.getInt("BID"));
							}
							System.out.println("Enter the Building ID: ");
							int BID = sc.nextInt();
							rs = stmt.executeQuery("select * from building where BID = " + BID + ";");
							rs.next();
							System.out.println("Building ID: " + BID);
							System.out.println("Building Address: " + rs.getString("street") + ", " + rs.getString("city") + ", " + rs.getString("state") + ", " + rs.getString("country"));
							System.out.println("Building Net Worth: " + rs.getInt("net_worth"));
							System.out.println("\n");
							break;
						case 3:
							Statement stmt1 = conn.createStatement();
							System.out.println("Available Rooms\n");
							ResultSet rs1;
							rs = stmt.executeQuery("select * from rooms where status = 'Unoccupied' group by type;");
							while(rs.next()) {
								System.out.println("Room No.: " + rs.getInt("floor_no") + rs.getInt("order_on_floor"));
								int k=rs.getInt("BID");
							    rs1 = stmt1.executeQuery("select * from building where BID = " + k + ";");
								rs1.next();
							    //System.out.println(rs1.getString("street"));
								//System.out.println("Hello");
                                System.out.println("Price of building:"+rs1.getInt("net_worth"));
							    System.out.println("Building Id: " + rs.getInt("BID"));// + ", " + rs.getString("city") + ", " + rs.getString("state") + ", " + rs.getString("country"));
								System.out.println("Room Type: " + rs.getString("type"));             
								System.out.println("Room Price: " + rs.getInt("price"));
								System.out.println("\n");
							}
							break;
						case 4:
							System.out.println("Specific Room Details\n");
							rs = stmt.executeQuery("select floor_no, order_on_floor, BID from rooms group by BID;");
							while(rs.next()) {
								System.out.println("Building ID: " + rs.getInt("BID"));
								System.out.println("Floor No.: " + rs.getInt("floor_no"));
								System.out.println("Room No.: " + rs.getInt("order_on_floor"));
								
							}
							System.out.println("Enter the Building ID: ");
							BID = sc.nextInt();
							sc.nextLine();
							System.out.println("Enter the Floor No.: ");
							int FloorNo = sc.nextInt();
							sc.nextLine();
							System.out.println("Enter the Room No.: ");
							int RoomNo = sc.nextInt();
							sc.nextLine();
							clrscr();
							Statement stmt11 = conn.createStatement();
							rs = stmt.executeQuery("select * from rooms where BID = " + BID + " and floor_no = " + FloorNo + " and order_on_floor = " + RoomNo + ";");
							rs1 = stmt11.executeQuery("select * from building where BID = " + rs.getInt("BID") + ";");
							System.out.println("Building Address: " + rs1.getString("street") + ", " + rs1.getString("city") + ", " + rs1.getString("state") + ", " + rs1.getString("country"));
							System.out.println("Building ID: " + rs.getInt("BID"));
							System.out.println("Room No.: " + rs.getInt("floor_no") + rs.getInt("order_on_floor"));
							System.out.println("Room Type: " + rs.getString("type"));
							System.out.println("Room Price: " + rs.getInt("price"));
							System.out.println("\n");
							stmt11.close();
							break;
						case 5:
							break;
							default:
								System.out.println("Wrong option. Moving back to menu...");
								continue;
						}
						break;
					}
					break;
				case 3:
					while(true) {
						System.out.println("Customer and Booking Menu\n");
						System.out.println("1. Customer details");
						System.out.println("2. Booking details");
						System.out.println("3. Back");
						System.out.println("Enter your option: ");
						int customerOption = sc.nextInt();
						sc.nextLine();
						ResultSet rs;
						switch(customerOption) {
						case 1:
							while(true) {
								System.out.println("Customer details\n");
								System.out.println("1. View all customer details");
								System.out.println("2. View specific customer details");
								System.out.println("3. Edit customer details");
								System.out.println("4. Back");
								System.out.println("Enter your option: ");
								int customerSpecificOption = sc.nextInt();
								switch(customerSpecificOption) {
								case 1:
									System.out.println("All Customer Details\n");
									rs = stmt.executeQuery("select * from customer;");
									while(rs.next())
									{
										System.out.println("Customer Aadhar: " + rs.getString("aadhar"));
										System.out.println("Customer ID: " + rs.getInt("CID"));
									}
									break;
								case 2:
									System.out.println("Specific Customer Details\n");
									System.out.println("Enter the Customer ID: ");
									int CID = sc.nextInt();
									sc.nextLine();
									rs = stmt.executeQuery("select * from customer where CID = " + CID + ";");
									rs.next();
									if(rs!=null) {
									try
									{
										System.out.println("Customer Aadhar: " + rs.getString("aadhar"));
									    System.out.println("Customer ID: " + rs.getInt("CID"));
									}
							  		   catch (NullPointerException e)
								       {
										   System.out.println("Invalid Details!");
									   }
								    }
									break;
								case 3:
									System.out.println("Edit Customer Details\n");
									System.out.println("Enter the Customer ID: ");
									CID = sc.nextInt();
									sc.nextLine();
									rs = stmt.executeQuery("select * from customer where CID = " + CID + ";");
									rs.next();
									if(rs != null) {
										try
										{
											System.out.println("Do you wish to change the customer Aadhar Number? Enter yes to change or type anything else to ignore: ");
             								String input = sc.nextLine();
										    if("yes".toUpperCase().equals(input.toUpperCase())) {
											System.out.println("Enter the new Aadhar number: ");
											int newAadhar = sc.nextInt();
											PreparedStatement p = conn.prepareStatement("update customer set aadhar = ? where CID = ?");
											p.setInt(1, newAadhar);
											p.setInt(2, CID);
											p.executeQuery();
										    }
										}
									    catch(NullPointerException e)
										{
											 System.out.println("Invalid details!");
											 clrscr();
											 clrscr();
										}
									}
									break;
								case 4:
									break;
								default:
									System.out.println("Wrong option. Moving back to menu...");
									continue;
								}
								break;
							}
							continue;
						case 2:
							while(true) {
								System.out.println("Booking specific details\n");
								System.out.println("1. View current day bookings");
								System.out.println("2. View archived bookings");
								System.out.println("3. Add new booking");
								System.out.println("4. Back");
								int bookingSpecificOption = sc.nextInt();
								switch(bookingSpecificOption) {
								case 1:
									System.out.println("Current Bookings\n");
									rs = stmt.executeQuery("select * from booking where check_out is null;");
									if(rs == null)
										System.out.println("No current day bookings!");
									else
										while (rs.next())
										{
											String date = rs.getString("check_out");
											if(rs.wasNull())
											{System.out.println("Customer ID: "+rs.getInt("CID"));
											System.out.println("Building ID: "+rs.getInt("BID"));
											System.out.println("Floor: "+rs.getInt("floor_no"));
											System.out.println("Room No.: "+rs.getString("order_on_floor"));
											System.out.println("Booking Date: "+rs.getString("booking_date"));
											System.out.println("No. of Members: "+rs.getInt("number_of_people"));
											//System.out.println("Check Out Time="+rs.getString("check_in"));
											System.out.println("Check in Time:"+rs.getString("check_in"));
											System.out.println("Total Cost: "+rs.getInt("amount"));
											System.out.println("Transaction ID: "+rs.getInt("TID"));}
								}
									break;
								case 2:
									System.out.println("Archived Bookings\n");
									rs=stmt.executeQuery("select * from booking where check_out is not null;");
									while (rs.next())
									{
										System.out.println("Customer ID: "+rs.getInt("CID"));
										System.out.println("Building ID: "+rs.getInt("BID"));
										System.out.println("Floor: "+rs.getInt("floor_no"));
										System.out.println("Room No.: "+rs.getString("order_on_floor"));
										System.out.println("Booking Date"+rs.getString("booking_date"));
										System.out.println("No. of Members: "+rs.getInt("number_of_people"));
										System.out.println("Check In Time="+rs.getString("check_in"));
										System.out.println("Check Out Time="+rs.getString("check_out"));
										System.out.println("Total Cost="+rs.getInt("amount"));
										System.out.println("Transaction ID: "+rs.getInt("TID"));
									}
									break;
								case 3:
									System.out.println("Add New Booking \n");
									System.out.println("Enter values for external booking:");
									System.out.println("Enter the Building ID: ");
									int bid=sc.nextInt();
									System.out.println("Enter the Customer ID: ");
									int cid=sc.nextInt();
									System.out.println("Enter the Floor Number: ");
									int floor=sc.nextInt();
									System.out.println("Enter the Room Number: ");
									int roomno=sc.nextInt();
									sc.nextLine();
									System.out.println("Enter the Booking Date(YYYY/MM/DD)");
									String bookingdate=sc.nextLine();
									java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingdate);
									java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
									System.out.println("Enter the Number of People: ");
									int mem=sc.nextInt();
									System.out.println("Enter the Check-in Date: ");
									String checkin=sc.next();
									java.util.Date utilDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(checkin);
									java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
									System.out.println("Enter the Transaction ID to be used later on: ");
									int TID = sc.nextInt();
									sc.nextLine();
								    PreparedStatement p1 = conn.prepareStatement("insert into booking (cid,bid,floor_no,order_on_floor,booking_date,number_of_people,check_in,tid)values("+cid+","+bid+","+floor+","+roomno+",?,"+mem+",?,"+TID+");");
								    p1.setDate(1, sqlDate);
								    p1.setDate(2, sqlDate1);
								    p1.executeUpdate();
								    PreparedStatement p = conn.prepareStatement("update rooms set status = 'Occupied' where (bid,floor_no,order_on_floor) = ("+bid+","+floor+","+roomno+");");
									p.executeUpdate();
								    break;
								case 4:
									break;
								default:
									System.out.println("Wrong option. Moving back to menu...");
									clrscr();
									continue;
								}
								break;
							}
							continue;
						case 3:
							break;
							default:
								System.out.println("Wrong option. Moving back to menu...");
								clrscr();
								continue;
						}
						break;
					}
					break;
				case 4:
					while(true) {
						System.out.println("Transaction Menu\n");
						System.out.println("1. Enter transaction");
						System.out.println("2. View previous transaction");
						System.out.println("3. Exit");
						int transactionOption = sc.nextInt();
						switch(transactionOption) {
						case 1:
							System.out.println("Transaction Entering\n");
							String date;
							Statement stmt1 = conn.createStatement();
							int tid,amount;
							System.out.println("Enter tid and date for transaction:");
							tid=sc.nextInt();
							sc.nextLine();
							date=sc.nextLine();
							java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
							java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
							System.out.println("Enter the checkout date: ");
							String checkout = sc.nextLine();
							java.util.Date utilDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(checkout);
							java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime()); 
							System.out.println("Enter type of transaction(cash/card/net banking):");
							String s=sc.next();
							if (s.toUpperCase().equals("cash".toUpperCase()))
							{
								System.out.println("Enter the amount given: ");
								int amount_given = sc.nextInt();
								System.out.println("Enter the change given: ");
								int change=sc.nextInt();
								try {
									PreparedStatement p1 = conn.prepareStatement("update booking set check_out = ? where tid = ?");
									p1.setInt(2, tid);
									p1.setDate(1, sqlDate1);
									p1.executeUpdate();
									ResultSet rs1 = stmt1.executeQuery(" select (to_days(check_out) - to_days(check_in)) * price as total_amount from booking join rooms on (booking.floor_no, booking.order_on_floor, booking.BID) = (rooms.floor_no, rooms.order_on_floor, rooms.BID) where check_out is not null and TID = " + tid);
								    rs1.next();
									amount = rs1.getInt("total_amount");
									PreparedStatement p = conn.prepareStatement("insert into cash_transaction values("+tid+","+amount_given+","+change+",?,"+amount+");");
									p.setDate(1, sqlDate);
									p.executeUpdate();
									}
								catch(NullPointerException e) {
									System.out.println("The given TID does not exist!");
								}
								}
							else if (s.toUpperCase().equals("card".toUpperCase()))
							{
								int cardno;
								System.out.println("Enter card no:");
								cardno=sc.nextInt();
								try {
									PreparedStatement p1 = conn.prepareStatement("update booking set check_out = ? where tid = ?");
									p1.setInt(2, tid);
									p1.setDate(1, sqlDate1);
									p1.executeUpdate();
									ResultSet rs1 = stmt1.executeQuery(" select (to_days(check_out) - to_days(check_in)) * price as total_amount from booking join rooms on (booking.floor_no, booking.order_on_floor, booking.BID) = (rooms.floor_no, rooms.order_on_floor, rooms.BID) where check_out is not null and TID = " + tid);
								    rs1.next();
									amount = rs1.getInt("total_amount");
									PreparedStatement p = conn.prepareStatement("insert into card_transaction values("+tid+","+cardno+",?,"+amount+")");
									p.setDate(1, sqlDate);
									p.executeUpdate();
									}
								catch(NullPointerException e) {
									System.out.println("The given TID does not exist!");
								}
								}
							else if (s.toUpperCase().equals("net banking".toUpperCase()))
							{
								int trans_no;
								System.out.println("Enter net banking transaction number:");
								trans_no=sc.nextInt();
								try {
									PreparedStatement p1 = conn.prepareStatement("update booking set check_out = ? where tid = ?");
									p1.setInt(2, tid);
									p1.setDate(1, sqlDate1);
									p1.executeUpdate();
									ResultSet rs1 = stmt1.executeQuery(" select (to_days(check_out) - to_days(check_in)) * price as total_amount from booking join rooms on (booking.floor_no, booking.order_on_floor, booking.BID) = (rooms.floor_no, rooms.order_on_floor, rooms.BID) where check_out is not null and TID = " + tid);
								    rs1.next();
									amount = rs1.getInt("total_amount");
									PreparedStatement p = conn.prepareStatement("insert into net_banking values("+tid+","+trans_no+",?,"+amount+")");
									p.setDate(1, sqlDate);
									p.executeUpdate();
								}
								catch(NullPointerException e) {
									System.out.println("The given TID does not exist!");
								}
							}
							else {
								System.out.println("Wrong option entered!");
								continue;
							}
							break;
						case 2:
							System.out.println("Previous Transaction Details\n");
							ResultSet rs=stmt.executeQuery("select * from cash_transaction;");
							while (rs.next())
							{
								System.out.println("Transaction ID: "+rs.getInt("TID"));
								System.out.println("Amount Given: "+rs.getInt("amount_paid"));
								System.out.println("Change: "+rs.getInt("change_given"));
								System.out.println("Date of Transaction: "+rs.getString("dateoftrans"));
								System.out.println("Amount: " + rs.getInt("amount"));
							}
							rs=stmt.executeQuery("select * from card_transaction;");
							while (rs.next())
							{
								System.out.println("Transaction ID: "+rs.getInt("TID"));
								System.out.println("Amount: "+rs.getInt("amount_paid"));
								System.out.println("Date of Transaction: "+rs.getString("dateoftrans"));
								System.out.println("Card number: "+rs.getInt("card_no"));
							}
							rs=stmt.executeQuery("select * from net_banking;");
							while (rs.next())
							{
								System.out.println("Transaction ID: "+rs.getInt("TID"));
								System.out.println("Amount: "+rs.getInt("amount_paid"));
								System.out.println("Net Banking Transaction ID: "+rs.getInt("trans_net_id"));
								System.out.println("Date of Transaction: "+rs.getString("dateoftrans"));								
							}
							break;
						case 3:
							break;
							default:
								System.out.println("Wrong option. Moving back to menu...");
								continue;
						}
						break;
					}
					break;
				case 5:
					System.out.println("Exiting...");
					Thread.sleep(2000);
					c = 'g';
					break;
					default:
						System.out.println("Wrong option. Moving back to menu");
						for(int i = 0; i < 3; i++) {
							Thread.sleep(2000);
							System.out.print('.');
						}
						clrscr();
						continue;
				}
			}
		}
		catch(SQLException se) 
		{
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(stmt != null)
					stmt.close();
			}
			catch(SQLException se) 
			{
				se.printStackTrace();
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
		sc.close();
	}
}
