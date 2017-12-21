package at.am.friedman.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.commons.utils.DebugFlagHelper;
import at.am.friedman.data.enums.DatabaseState;
import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.shared.DataAccessorInterface;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;
import at.am.common.logging.LogFactory;

public class DerbyDataAccessor implements DataAccessorInterface {

	private static String url = "jdbc:derby:myDB;create=true";

	private static Logger logger = LogFactory.makeLogger();

	private DatabaseState dbState = DatabaseState.NO_CONNECTED;

	private Connection connection;

	private final List<GraveInterface> gravesToDelete = new ArrayList<GraveInterface>();

	private final String logLevel = DebugFlagHelper.getStringDebugFlag(Constants.DEBUG_DBACCESS_LOG_LEVEL, "INFO");

	private final int ACTUALVERSION = 1;

	private Connection getConnection() {
		return this.connection;
	}

	@Override
	public void saveGraveOwner(GraveOwnerInterface owner) {
		String sql;
		PreparedStatement statement = null;
		if (objectExists("owner", owner.getId())) {
			sql = "update owner set firstname=?, surname=?, street=?, housenr=?, postalcode=? , town=?, telephon=? where id = ?";
		} else {
			sql = "insert into owner ( firstname, surname, street , housenr , postalcode , town, telephon, id) values(?,?,?,?,?,?,?,?)";
		}

		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(8, owner.getId());
			statement.setString(1, owner.getFirstName());
			statement.setString(2, owner.getSurname());
			statement.setString(3, owner.getStreet());
			statement.setString(4, owner.getHouseNr());
			statement.setString(5, owner.getPostalCode());
			statement.setString(6, owner.getTown());
			statement.setString(7, owner.getTelephon());
			statement.execute();
			logger.log(Level.parse(logLevel), "saveGraveOwner: " + sql + "  " + owner.toString());
		} catch (SQLException e) {
			logger.warning("Error saveGraveOwner: " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}

	}

	@Override
	public void saveGrave(GraveInterface grave) {
		String sql;
		if (objectExists("grave", grave.getId())) {
			sql = "update grave set row=?, place=?, zone=?, typ=?, ownerid=?, offset=?, starttime=?, runtime=? where id = ?";

		} else {
			sql = "insert into grave (row, place, zone, typ, ownerid, offset, starttime, runtime, id) values(?,?,?,?,?,?,?,?,?)";

		}
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, grave.getRow());
			statement.setString(2, grave.getPlace());
			statement.setInt(3, grave.getZone().ordinal());
			statement.setInt(4, grave.getType().ordinal());
			statement.setInt(5, grave.getOwnerId());
			statement.setInt(6, grave.getOffset());
			statement.setLong(7, grave.getStarttime());
			statement.setInt(8, grave.getRuntime());
			statement.setInt(9, grave.getId());
			statement.execute();
			logger.log(Level.parse(logLevel), "saveGrave: " + sql + "  " + grave.toString());
		} catch (Exception e) {
			logger.warning("Error saveGrave: " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}

	}

	private boolean objectExists(String tableName, int id) {
		String checkIdSQL = "Select id from " + tableName + " where id=" + id;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery(checkIdSQL);
			if (result.next()) {
				return true;
			}
		} catch (Exception e) {
			// do nothing
			logger.fine("no result " + e.toString());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		return false;

	}

	@Override
	public void saveDiedPerson(DiedPersonInterface diedPerson) {
		String sql;
		PreparedStatement statement = null;
		if (objectExists("diedperson", diedPerson.getId())) {
			sql = "update diedperson set firstname=?, surname=?, interment=?, deathday=?, graveid=?, internalposition=?, age=?, dayofinterment=?, description=? where id =?";
		} else {
			sql = "insert into diedperson (firstname, surname, interment, deathday, graveid, internalposition, age, dayofinterment, description, id) values(?,?,?,?,?,?,?,?,?,?)";
		}

		try {
			statement = getConnection().prepareStatement(sql);

			statement.setString(1, diedPerson.getFirstName());
			statement.setString(2, diedPerson.getSurname());
			statement.setLong(3, diedPerson.getDayOfInterment());
			statement.setLong(4, diedPerson.getDeathday());
			statement.setInt(5, diedPerson.getGraveId());
			statement.setInt(6, diedPerson.getInternalPosition());
			statement.setInt(7, diedPerson.getAge());
			statement.setLong(8, diedPerson.getDayOfInterment());
			statement.setString(9, diedPerson.getDescription());
			statement.setInt(10, diedPerson.getId());
			statement.execute();
			logger.log(Level.parse(logLevel), "save died person: " + sql + "  " + diedPerson.toString());

		} catch (SQLException e) {
			logger.warning("Error saveDiedPerson: " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}

	}

	@Override
	public void initDataAccessor() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connection = DriverManager.getConnection(url);
			checkSchema(connection);
			if (getDBState() == DatabaseState.CONNECTED_NO_SCHEMA) {
				createSchema(connection);
			}
			if (getDBState() == DatabaseState.CONNECTED_OLD_SCHEMA) {
				updateSchema(connection);
			}

		} catch (Exception e) {
			logger.severe("not possible co connect to DB " + e.toString());
			if(e instanceof SQLException){
				logger.severe("errorcode: " + ((SQLException) e).getErrorCode());
				logger.severe("sqlstate: " + ((SQLException) e).getSQLState());
			}
			logger.severe(e.getStackTrace().toString());
		}

	}

	private void updateSchema(Connection con) {
		logger.info("updateSchema");
		Statement statement = null;
		try {

			// statement.execute("update version set version=" + ACTUALVERSION);
		} catch (Exception e) {

		}

	}

	private void createSchema(Connection con) {
		Statement statement = null;
		try {
			logger.info("Create Schema ....");
			statement = con.createStatement();
			dropTable(con, "grave");
			dropTable(con, "owner");
			dropTable(con, "diedperson");
			String createGravesSQL = "create table grave (id integer primary key, row varchar(20), place varchar(20), zone integer, typ integer, ownerId integer, offset integer default 0, starttime bigint, runtime int)";
			statement.execute(createGravesSQL);

			String createOwnerSQL = "create table owner ( id integer primary key, firstname varchar(100), surname varchar(100), street varchar(100), housenr varchar(100), postalcode varchar(100) , town varchar(100), telephon varchar(100)  )";
			statement.execute(createOwnerSQL);

			String createDiedPersonSQL = "create table diedperson (id integer primary key, firstname varchar(100), surname varchar(100), interment bigint, deathday bigint, graveid integer, internalposition integer, age integer, dayofinterment bigint, description varchar(500) )";
			statement.execute(createDiedPersonSQL);

			String createMultiGraveSQL = "create table multigrave ( id integer primary key, graveid1 integer, graveid2 integer, graveid3 integer, graveid4 integer, graveid5 integer)";
			statement.execute(createMultiGraveSQL);

			String createVersionSQL = "create table version (version integer)";
			statement.execute(createVersionSQL);

			String insertVersionSQL = "insert into version (version) values(" + ACTUALVERSION + ")";
			statement.execute(insertVersionSQL);

			logger.info("Schema created!");
			setDBState(DatabaseState.CONNECTED);

		}

		catch (SQLException e) {
			logger.severe("Cannot create Schema: " + e.toString());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e1) {
					logger.severe("Cannot close stamenent " + e1.toString());
				}
			}
		}

	}

	private void dropTable(Connection con, String tableName) {
		Statement statement = null;
		try {
			statement = con.createStatement();
			statement.execute("drop table " + tableName);
		} catch (SQLException e) {
			logger.severe("Cannot drop table " + tableName + " " + e.toString());
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e1) {
					logger.severe("Cannot close stamenent " + e1.toString());
				}
			}
		}
	}

	private void checkSchema(Connection con) {
		Statement statement = null;
		try {
			statement = con.createStatement();
			ResultSet result = statement.executeQuery("Select * from version");
			result.next();
			int version = result.getInt(1);
			if (version < ACTUALVERSION) {
				setDBState(DatabaseState.CONNECTED_OLD_SCHEMA);
			} else {
				setDBState(DatabaseState.CONNECTED);
			}
		} catch (SQLException e) {
			logger.info("no correct Schema " + e.toString());
			setDBState(DatabaseState.CONNECTED_NO_SCHEMA);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}

	}

	private void setDBState(DatabaseState state) {
		if (dbState != state) {
			logger.info("DBState changed from " + dbState + " to " + state);
		}
		dbState = state;
	}

	private DatabaseState getDBState() {
		return dbState;
	}

	@Override
	public List<GraveInterface> getAllGraves() {
		List<GraveInterface> ret = new ArrayList<>();
		Statement statement = null;

		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery("select id, row , place , zone , typ , ownerId, offset, starttime, runtime  from grave");
			while (result.next()) {
				GraveInterface grave = null;
				try {
					grave = new GraveImpl();
					grave.setId(result.getInt(1));
					grave.setRow(result.getString(2));
					grave.setPlace(result.getString(3));

					grave.setZone(GraveZone.values()[result.getInt(4)]);

					grave.setType(GraveType.values()[result.getInt(5)]);
					grave.setOwnerId(result.getInt(6));
					grave.setOffset(result.getInt(7));
					grave.setStarttime(result.getLong(8));
					grave.setRuntime(result.getInt(9));
				} catch (Exception e1) {
					gravesToDelete.add(grave);
				}
				if (grave != null) {
					ret.add(grave);
				}
			}

		} catch (SQLException e) {
			logger.severe("Error: getAllGraves " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
					for (GraveInterface grave : gravesToDelete) {
						deleteGrave(grave);
					}
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		logger.log(Level.parse(logLevel), "getAllGraves: size " + ret.size());
		return ret;
	}

	@Override
	public List<GraveOwnerInterface> getAllOwners() {
		List<GraveOwnerInterface> ret = new ArrayList<>();
		Statement statement = null;

		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery("select  id , firstname , surname , street , housenr , postalcode  , town, telephon  from owner");
			while (result.next()) {
				GraveOwnerInterface owner = new GraveOwnerImpl();
				owner.setId(result.getInt(1));
				owner.setFirstName(result.getString(2));
				owner.setSurname(result.getString(3));
				owner.setStreet(result.getString(4));
				owner.setHouseNr(result.getString(5));
				owner.setPostalCode(result.getString(6));
				owner.setTown(result.getString(7));
				owner.setTelephon(result.getString(8));
				ret.add(owner);
			}

		} catch (SQLException e) {
			logger.severe("Error: getAllOwners " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		logger.log(Level.parse(logLevel), "getAllOwner: size " + ret.size());
		return ret;
	}

	@Override
	public List<DiedPersonInterface> getAllDiedPersons() {
		List<DiedPersonInterface> ret = new ArrayList<>();
		Statement statement = null;

		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery("select id , firstname , surname , interment , deathday , graveid , internalposition, age, dayofinterment, description  from diedperson");
			while (result.next()) {
				DiedPersonInterface diedPerson = new DiedPersonImpl();
				diedPerson.setId(result.getInt(1));
				diedPerson.setFirstName(result.getString(2));
				diedPerson.setSurname(result.getString(3));
				diedPerson.setDayOfInterment(result.getLong(4));
				diedPerson.setDeathday(result.getLong(5));
				diedPerson.setGraveId(result.getInt(6));
				diedPerson.setInternalPosition(result.getInt(7));
				diedPerson.setAge(result.getInt(8));
				diedPerson.setDayOfInterment(result.getLong(9));
				diedPerson.setDescription(result.getString(10));
				ret.add(diedPerson);
			}

		} catch (SQLException e) {
			logger.severe("Error: getAllDiedPersons " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		logger.log(Level.parse(logLevel), "getAllDiedPerson: size " + ret.size());
		return ret;
	}

	@Override
	public void deleteDiedPerson(DiedPersonInterface diedPerson) {
		deleteEntryById("diedperson", diedPerson.getId());

	}

	@Override
	public void deleteGrave(GraveInterface grave) {
		deleteEntryById("grave", grave.getId());

	}

	@Override
	public void deleteGraveOwner(GraveOwnerInterface owner) {
		deleteEntryById("owner", owner.getId());

	}

	private void deleteEntryById(String tableName, int id) {
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement("delete from " + tableName + " where id =?");
			statement.setInt(1, id);
			statement.execute();

		} catch (SQLException e) {
			logger.severe("Error: deleteEntryById " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
	}

	@Override
	public int getMaxIdForDiedPerson() {
		return getMaxIdForTable("diedperson");
	}

	@Override
	public int getMaxIdForGrave() {

		return getMaxIdForTable("grave");
	}

	@Override
	public int getMaxIdForOwner() {
		return getMaxIdForTable("owner");
	}

	private int getMaxIdForTable(String table) {
		Statement statement = null;
		int id = 1;

		try {
			statement = getConnection().createStatement();
			ResultSet res = statement.executeQuery("Select max(id) from " + table);
			res.next();
			id = res.getInt(1);
			id++;
		} catch (SQLException e) {
			logger.warning("Error: getMaxIdForTable " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		return id;
	}

	@Override
	public void dump() {
		logger.info("#### DUMP OF DERBY DATABASE ####");
		logger.info("####DIEDPERSON####");
		for (DiedPersonInterface person : getAllDiedPersons()) {
			logger.info(person.toString());
		}

		logger.info("################################");
	}

	@Override
	public void saveMultiGrave(MultiGraveInterface grave) {
		String sql;
		PreparedStatement statement = null;
		if (objectExists("multigrave", grave.getId())) {
			sql = "update multigrave set graveid1=?, graveid2=?, graveid3=?, graveid4=?, graveid5=? where id = ?";
		} else {
			sql = "insert into multigrave ( graveid1, graveid2, graveid3 , graveid4 , graveid5 , id) values(?,?,?,?,?,?)";
		}

		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(6, grave.getId());
			statement.setInt(1, grave.getGraveIds().get(0));
			statement.setInt(2, grave.getGraveIds().get(1));
			statement.setInt(3, grave.getGraveIds().size() > 2 ? grave.getGraveIds().get(2) : 0);
			statement.setInt(4, grave.getGraveIds().size() > 3 ? grave.getGraveIds().get(3) : 0);
			statement.setInt(5, grave.getGraveIds().size() > 4 ? grave.getGraveIds().get(4) : 0);

			statement.execute();
			logger.log(Level.parse(logLevel), "saveMultiGrave: " + sql + "  " + grave.toString());
		} catch (SQLException e) {
			logger.warning("Error: saveMultiGrave " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}

	}

	@Override
	public void deleteMultiGrave(MultiGraveInterface multiGrave) {
		deleteEntryById("multigrave", multiGrave.getId());

	}

	@Override
	public List<MultiGraveInterface> getAllMultiGraves() {
		List<MultiGraveInterface> ret = new ArrayList<MultiGraveInterface>();
		Statement statement = null;

		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery("select id, graveid1, graveid2, graveid3, graveid4, graveid5 from multigrave");
			while (result.next()) {
				MultiGraveInterface multigrave = new MultiGraveImpl();
				multigrave.setId(result.getInt(1));
				multigrave.addGraveId(result.getInt(2));
				multigrave.addGraveId(result.getInt(3));
				if (result.getInt(4) > 0) {
					multigrave.addGraveId(result.getInt(4));
					if (result.getInt(5) > 0) {
						multigrave.addGraveId(result.getInt(5));
						if (result.getInt(6) > 0) {
							multigrave.addGraveId(result.getInt(6));
						}
					}
				}

				ret.add(multigrave);
			}

		} catch (SQLException e) {
			logger.severe("Error: getAllMultiGraves " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe("Cannot close stamenent " + e.toString());
				}
			}
		}
		return ret;
	}

	@Override
	public int getMaxIdForMultiGrave() {
		return getMaxIdForTable("multigrave");
	}
}
