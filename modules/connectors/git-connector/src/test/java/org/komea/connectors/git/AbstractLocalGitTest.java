package org.komea.connectors.git;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.komea.connectors.git.impl.GitRepository;

public abstract class AbstractLocalGitTest
{
	
	protected final File	 folder	= new File(
	                                        "src/test/resources/github-gmail/git/");
	protected IGitRepository	repository;
	
	@After
	public void end() {
		
		repository.close();
	}
	
	@Before
	public void init() {
		
		assertTrue(folder.exists());
		repository = new GitRepository(folder,
		        "https://github.com/muan/github-gmail.git");
	}
	
	protected int getExpectedNumberOfCommits() {
		
		return 139;
	}
	
}
