import org.komea.product.plugins.bugzilla.core.*;
import org.komea.product.backend.kpi.search.*;


BZBugCountKPI.create(Search.create(Filter.create("status", true, "new",
             "unconfirmed", "onhold", "accepted", "assigned", "opened", "reopened"), Filter.create("severity", true,
             "#severity#")))