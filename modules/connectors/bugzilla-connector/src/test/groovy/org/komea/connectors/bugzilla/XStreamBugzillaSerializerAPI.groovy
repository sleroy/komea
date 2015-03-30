package org.komea.connectors.bugzilla

import org.joda.time.DateTime
import org.komea.connectors.bugzilla.proxy.IBugzillaAPI
import org.komea.connectors.bugzilla.proxy.impl.BugzillaAPI

import com.j2bugzilla.base.Bug
import com.j2bugzilla.base.BugzillaException
import com.j2bugzilla.base.ConnectionException
import com.j2bugzilla.base.Product
import com.j2bugzilla.rpc.GetLegalValues.Fields
import com.thoughtworks.xstream.XStream

public class XStreamBugzillaSerializerAPI implements IBugzillaAPI {

	private final BugzillaAPI	bugzillaAPI	= new BugzillaAPI()
	private  XStream	  xstream

	public XStreamBugzillaSerializerAPI() {
		super()
		this.xstream = new XStream()
	}

	@Override
	public List<Bug> getBugList(final String _productName) throws BugzillaException {
		final List<Bug> bugList = this.bugzillaAPI.getBugList(_productName)
		final File file = new File("src/test/resources/data/buglist-" + _productName + ".xml")
		try {
			this.xstream.toXML(bugList, new FileWriter(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return bugList
	}

	@Override
	public DateTime getCreationTime(final Bug _bug) {

		return this.bugzillaAPI.getCreationTime(_bug)
	}

	@Override
	public Set<String> getLegalValues(final Fields _field) throws BugzillaException {
		final Set<String> legalValues = this.bugzillaAPI.getLegalValues(_field)

		final File file = new File("src/test/resources/data/legalvalue-" + _field.name() + ".xml")
		try {
			this.xstream.toXML(legalValues, new FileWriter(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return legalValues
	}

	@Override
	public Product getProductDefinition(final String _productName) throws BugzillaException {
		final Product productDefinition = this.bugzillaAPI.getProductDefinition(_productName)

		final File file = new File("src/test/resources/data/productdef-" + _productName + ".xml")
		try {
			this.xstream.toXML(productDefinition, new FileWriter(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return productDefinition
	}

	@Override
	public List<Product> getProducts() throws BugzillaException {
		final List<Product> productDefinitions = this.bugzillaAPI.getProducts()

		final File file = new File("src/test/resources/data/productlist.xml")
		try {
			this.xstream.toXML(productDefinitions, new FileWriter(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return productDefinitions
	}

	@Override
	public DateTime getUpdatedTime(final Bug _bug) {

		return this.bugzillaAPI.getUpdatedTime(_bug)
	}

	@Override
	public void initConnection(final String _serverURL) throws ConnectionException {
		this.bugzillaAPI.initConnection(_serverURL)
	}

	@Override
	public void login(final String _user, final String _password) throws BugzillaException {
		this.bugzillaAPI.login(_user, _password)
	}

	@Override
	public List<String> obtainProductList() throws BugzillaException {
		final List<String> productDefinitions = this.bugzillaAPI.obtainProductList()

		final File file = new File("src/test/resources/data/productlistname.xml")
		try {
			this.xstream.toXML(productDefinitions, new FileWriter(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return productDefinitions
	}
}
