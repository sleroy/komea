/**
 * 
 */

package org.komea.product.backend.service;

import java.io.PrintStream;
import java.util.List;

import jodd.io.StringOutputStream;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * This class defines the administration actions offered through the Admin GUI.
 * An action of administration offers basically a command to be executed by the
 * server.
 * 
 * @author sleroy
 */
@Service
public class AdminActionServices implements IAdminActionServices {

	private static class StringProgressListener implements ProgressListener {

		private StringBuilder stringBuilder;

		public StringProgressListener() {
			stringBuilder = new StringBuilder();
		}

		public StringBuilder getStringBuilder() {

			return stringBuilder;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.komea.product.backend.service.ProgressListener#progressFinish
		 * (org.komea.product.backend.service.ProgressEvent)
		 */
		@Override
		public void progressFinish(final ProgressEvent _arg0) {

			stringBuilder.append(_arg0.getEventMessage() + "\n");

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.komea.product.backend.service.ProgressListener#progressStart(
		 * org.komea.product.backend.service.ProgressEvent)
		 */
		@Override
		public void progressStart(final ProgressEvent _arg0) {

			stringBuilder.append(_arg0.getEventMessage() + "\n");

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.komea.product.backend.service.ProgressListener#progressUpdate
		 * (org.komea.product.backend.service.ProgressEvent)
		 */
		@Override
		public void progressUpdate(final ProgressEvent _arg0) {

			stringBuilder.append(_arg0.getEventMessage() + "\n");

		}

	}

	/**
     * 
     */
	public static final String ACTION_FINISHED_WITH_SUCCESS = "Action finished with success";

	private static final Logger LOGGER = LoggerFactory
			.getLogger("admin-actions");

	private final List<IAdminAction> adminActions = Lists.newArrayList();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.IAdminActionServices#executeAction(
	 * org.komea.product.backend.api.IAdminAction)
	 */
	@Override
	public String executeAction(final IAdminAction _adminAction) {

		try {
			final StringProgressListener progressListener = new StringProgressListener();
			_adminAction.execute(progressListener);
			return progressListener.getStringBuilder().toString();
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			final StringOutputStream stringOutputStream = new StringOutputStream();
			final PrintStream printStream = new PrintStream(stringOutputStream);
			e.printStackTrace(printStream);
			return stringOutputStream.toString();

		}

	}

	/**
	 * Returns the list of admin actions.
	 */
	public List<IAdminAction> getActions() {

		return adminActions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.IAdminActionServices#registerAdminAction
	 * (org.komea.product.backend.api.IAdminAction)
	 */
	@Override
	public void registerAdminAction(final IAdminAction _adminAction) {

		Validate.notNull(_adminAction);
		adminActions.add(_adminAction);

	}
}
