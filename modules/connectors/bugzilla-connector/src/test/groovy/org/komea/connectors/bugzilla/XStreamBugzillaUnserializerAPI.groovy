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

public class XStreamBugzillaUnserializerAPI implements IBugzillaAPI {

	private final BugzillaAPI	bugzillaAPI	= new BugzillaAPI()
	private  XStream	  xstream = new XStream()


	@Override
	public List<Bug> getBugList(final String _productName) throws BugzillaException {
		final File file = new File("src/test/resources/data/buglist-" + _productName + ".xml")
		try {
			return this.xstream.fromXML(new FileReader(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return null
	}

	@Override
	public DateTime getCreationTime(final Bug _bug) {

		return this.bugzillaAPI.getCreationTime(_bug)
	}

	@Override
	public Set<String> getLegalValues(final Fields _field) throws BugzillaException {

		final File file = new File("src/test/resources/data/legalvalue-" + _field.name() + ".xml")
		try {
			return this.xstream.fromXML(new FileReader(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return null
	}

	@Override
	public Product getProductDefinition(final String _productName) throws BugzillaException {

		final File file = new File("src/test/resources/data/productdef-" + _productName + ".xml")
		try {
			return this.xstream.fromXML(new FileReader(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return null
	}

	@Override
	public List<Product> getProducts() throws BugzillaException {

		final File file = new File("src/test/resources/data/productlist.xml")
		try {
			return this.xstream.fromXML(new FileReader(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return null
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

		final File file = new File("src/test/resources/data/productlistname.xml")
		try {
			return this.xstream.fromXML(new FileReader(file))
		} catch (final IOException e) {
			e.printStackTrace()
		}
		return null
	}
}
