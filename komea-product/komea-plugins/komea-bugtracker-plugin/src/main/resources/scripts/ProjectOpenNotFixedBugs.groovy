import org.komea.product.plugins.bugtracking.model.*;
IssueFilterKPI kpi = new IssueFilterKPI(BackupDelay.DAY);
kpi.setClosure({ it.getStatus() == IssueStatus.OPENED && it.getResolution() == IssueResolution.NOT_FIXED});
kpi.setGroupFunction({ it.getProduct() });
kpi;