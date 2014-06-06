import org.komea.eventory.api.cache.*;
import org.komea.product.plugins.bugzilla.core.*;
import org.komea.product.backend.kpi.search.*;
//def statusList = ["new","unconfirmed", "onhold", "accepted", "assigned", "opened", "reopened"];
def closure = { it -> it.getStatus() == Status.OPENED};
   

IssueProjectKPI kpi = new IssueProjectKPI(BackupDelay.DAY, "bugzilla-source");
kpi.setClosure(closure);
return kpi;

