package com.wavelabs.service;

import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.PropertyAttributes;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.model.Television;
import com.wavelabs.service.PersistanceService;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * Checks {@link PersistanceService} methods using unit test cases.
 * 
 * @author gopikrishnag
 * @since 2017-02-04
 */
public class PersistenceServiceTest {

	private HbmFileMetaData tvHbm = null;
	private CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 */
	@BeforeTest
	public void intillization() {
		XmlDocumentBuilder xdb = new XmlDocumentBuilder();
		XmlDocument xd = xdb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Tv.hbm.xml");
		tvHbm = new HbmFileMetaData(xd, Helper.getSessionFactory());
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * <p>
	 * Test update attribute value of cost property
	 * </p>
	 * <ul>
	 * <li>{@code update="true"} test case will pass
	 * </li>
	 * <li>{@code update="false"} test case will fail</li>
	 * </ul>
	 */
	@Test(priority = 1, description = "checks update attribute value of cost property in hbm file")
	public void testForUpdate() {
		Assert.assertEquals(tvHbm.getPropertyAttributeValue("cost", PropertyAttributes.update), "false",
				"cost property attriubte value in mapping not meets condition");
	}

	/**
	 * <p>
	 * Test insert attribute value of warranty property
	 * </p>
	 * <ul>
	 * <li>{@code insert="true"} test case will pass</li>
	 * <li>{@code insert="false"} test case will fail</li>
	 * </ul>
	 * 
	 */
	@Test(priority = 2, description = "verifies warranty attribute value for warranty property")
	public void testForInsert() {
		Assert.assertEquals(tvHbm.getPropertyAttributeValue("warranty", PropertyAttributes.insert), "false",
				"insert attribute value for warranty property in hbm file fails");
	}

	/**
	 * Provides input to
	 * {@link PersistenceServiceTest#testTypes(String, String)}
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "propertyTypes")
	public Object[][] dbOfPropertyTypes() {
		Object obj[][] = { { "name", "string" }, { "cost", "double" }, { "warranty", "int" } };
		return obj;
	}

	/**
	 * takes input from {@link #dbOfPropertyTypes()} and checks properties
	 * types. If type is not mapped as per requirement test will fails.
	 * 
	 * @param propertyName
	 *            of {@linkplain Television}
	 * @param type
	 *            of {@linkplain Television}
	 */
	@Test(priority = 3, dataProvider = "propertyTypes", description = "verifies property type as per requirment or not")
	public void testTypes(String propertyName, String type) {
		Assert.assertEquals(tvHbm.getPropertyAttributeValue(propertyName, PropertyAttributes.type), type,
				" property type in mapping not mapped as per requirement");
	}

	/**
	 * Provides intput to {@link #testColumnName(String, String)} .
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "columnNames")
	public Object[][] dbOfColumnNames() {
		Object obj[][] = { { "name", "NAME" }, { "cost", "COST" }, { "warranty", "WARRANTY" } };
		return obj;
	}

	/**
	 * takes input form {@link #dbOfColumnNames()}, Tests properties column
	 * names. If Column names are not same as in requirement, then test case
	 * will fail.
	 * 
	 * @param propertyName
	 *            of {@linkplain Television}
	 * @param columnName
	 *            of {@linkplain Television}
	 */
	@Test(priority = 4, dataProvider = "columnNames", description = "verifies column names as per requirment or not")
	public void testColumnName(String propertyName, String columnName) {
		Assert.assertEquals(tvHbm.getPropertyAttributeValue(propertyName, PropertyAttributes.column), columnName,
				"Column name is not matched for " + propertyName);
	}

	/**
	 * Checks {@link PersistanceService#createTelevision(int, String, int, int)}
	 * persist record or not for given input.
	 * <p>
	 * If createTelevision method fails to insert record in table then test will
	 * fail
	 * </p>
	 */
	@Test(priority = 5, dependsOnMethods = { "testForUpdate", "testForInsert", "testTypes",
			"testColumnName" }, description = "verifies createTelevison method record inserting or not")
	public void testCreateTv() {
		PersistanceService.createTelevision(1, "TOSHIBA", 25000, 2);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Television.class, 1), true,
				"CreateTelevision method fails to insert record in table");
	}

	/**
	 * Checks
	 * {@link PersistanceService#updateTelevisonProperties(int, Integer, double)}
	 * update record for given id.
	 */
	@Test(priority = 6, dependsOnMethods = "testCreateTv", description = "verifies updateTelevisionProperties method update persisted object or not")
	public void testUpdateTv() {
		PersistanceService.updateTelevisonProperties(1, new Integer(4), 5000);
		crud.setSession(Helper.getSession());
		Assert.assertNotEquals(crud.isColumnUpdated(Television.class, "cost", 5000.0, 1), true);
	}

	/**
	 * Closes the SessionFactory
	 */
	@AfterTest
	public void closeResources() {
		try {
			Helper.getSessionFactory().close();
		} catch (Exception e) {

		}
	}
}
