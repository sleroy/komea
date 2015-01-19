/**
 *
 */
package org.komea.connectors.sdk.std.impl;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Messages;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * @author sleroy
 *
 */
public class DateOptionHandler extends OptionHandler {
	public DateOptionHandler(final CmdLineParser parser,
			final OptionDef option, final Setter<? super String> setter) {
		super(parser, option, setter);
	}

	@Override
	public String getDefaultMetaVariable() {
		return Messages.DEFAULT_META_STRING_OPTION_HANDLER.format();
	}

	@Override
	public int parseArguments(final Parameters params) throws CmdLineException {
		final DateTimeFormatter formatter = DateTimeFormat
				.forPattern("dd/MM/yyyy");
		DateTime result = null;
		result = formatter.parseDateTime(params.getParameter(0));
		setter.addValue(result);
		return 1;
	}
}
