import org.komea.product.plugins.bugzilla.core.*;
import org.komea.product.backend.kpi.search.*;


final BZBugCountKPI query = BZBugCountKPI.create(Search.create(Filter.create("status", false, "closed",
             "delivered", "resolved")));
