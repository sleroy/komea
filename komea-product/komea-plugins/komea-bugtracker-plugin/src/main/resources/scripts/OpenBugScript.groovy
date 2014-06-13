IssueFilterKPI kpi = new IssueFilterKPI(BackupDelay.DAY, "bugzilla-source");
kpi.setClosure({ it.getStatus() == Status.OPENED});
kpi.setGroupFunction({ it.getProduct() });
