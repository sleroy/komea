IssueFilterKPI kpi = new IssueFilterKPI(BackupDelay.DAY)
kpi.setClosure({ true })
kpi.setGroupFunction({ it.getProduct() })
kpi