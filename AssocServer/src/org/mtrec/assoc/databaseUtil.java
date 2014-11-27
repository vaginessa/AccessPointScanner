package org.mtrec.assoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

class databaseUtil {

	static String ip = "localhost";
	static String port = "5432";
	static String db = "network_optimizer";
	static String table = "ap_table";
	static String user = "postgres";
	static String pass = "1";
	static Connection conn;

	static {
		{
			try {
				Class.forName("org.postgresql.Driver");

				String DbUrl = "jdbc:postgresql://" + ip + ":" + port + "/"
						+ db;

				conn = DriverManager.getConnection(DbUrl, user, pass);

				AP tmpAP = new AP(121, "WWGRH", 9, 8);

				// insert(tmpAP);
				//
				// query();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static void insert(AP tmpAP) throws SQLException {
		Statement stmt = conn.createStatement();
		insert(stmt, tmpAP);
	}

	static void query() throws SQLException {
		Vector<AP> APInfos = new Vector<AP>();

		String sql = "select * from " + table;

		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			AP an_ap = new AP(rs.getInt("ap_id"), rs.getString("mac"),
					rs.getInt("channel_num"), rs.getInt("power_num"));

			APInfos.add(an_ap);
		}

		rs.close();
		ps.close();

		// System.out
		// .printf("id  mac user_count packet_queue_length channel_num\n");

		for (int i = 0; i < APInfos.size(); i++) {

			System.out.printf(APInfos.get(i).getId() + "  "
					+ APInfos.get(i).getMAC() + "  "

					+ APInfos.get(i).getChannel() + "  "

					+ APInfos.get(i).getPower() + "\n");

		}
	}

	private static void insert(Statement stmt, AP tmpAP) throws SQLException {
		String sqlInsert = "INSERT INTO " + table
				+ "( ap_id, mac, power_num, channel_num) " + " VALUES ("
				+ tmpAP.getId() + ", '" + tmpAP.getMAC() + "', "
				+ tmpAP.getChannel() + ", " + tmpAP.getPower() + ");";

		stmt.executeUpdate(sqlInsert);
	}
}
